/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.servlet;

import com.ps.bean.OrderListBean;
import com.ps.entity.OrderContent;
import com.ps.entity.OrderList;
import com.ps.entity.OrderListAfford;
import com.ps.test.mailsender;
import java.io.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/**
 *
 * @author Wei.Cheng
 */
@WebServlet(urlPatterns = {"/PostData.do"},
        initParams = {
            @WebInitParam(name = "subject", value = "採購系統:訂單申請通知")
        }
)
public class PostData extends HttpServlet {

    private String subject;
    private String from;
    private String to;

    @Override
    public void init() throws ServletException {
        from = getServletContext().getInitParameter("mailserver");
        to = getServletContext().getInitParameter("adminmail");
        subject = getServletConfig().getInitParameter("subject");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        res.setCharacterEncoding("utf-8");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession(false);
        int user_id = -1;
        String id = req.getParameter("id");
        if (id != null) {
            user_id = Integer.parseInt(id);
        }
        String product[] = req.getParameterValues("product");
        String count[] = req.getParameterValues("count");
        String memo = req.getParameter("memo");
        String webdir[] = req.getRequestURL().toString().split(req.getServletPath());
        String aff_unit[] = req.getParameterValues("aff_unit");
        String aff_percent[] = req.getParameterValues("aff_percent");

        mailsender mailsender = new mailsender();
        if (product != null && count != null && memo != null) {
            OrderListBean olBean = new OrderListBean();
            List ocl = new ArrayList();
            OrderList ol = new OrderList();

            if (user_id != -1) {
                List l = new ArrayList();
                ol.setApplicant(user_id);
                ol.setMemo(memo);
                l.add(ol);

                for (int i = 0; i < product.length; i++) {
                    OrderContent oc = new OrderContent();
                    oc.setProduct(Integer.parseInt(product[i]));
                    oc.setQuantity(Integer.parseInt(count[i]));
                    ocl.add(oc);
                }
                List olflist = new ArrayList();
                for (int j = 0; j < aff_unit.length; j++) {
                    OrderListAfford olf = new OrderListAfford();
                    if (!"".equals(aff_unit[j]) || !"".equals(aff_percent[j])) {
                        olf.setUnit(aff_unit[j].toUpperCase());
                        olf.setAff_percent(Double.parseDouble(aff_percent[j]));
                        olflist.add(olf);
                    }
                }
                if (olBean.insertOrderList(l, ocl, olflist)) {
                    //開啟寄送mail功能
                    out.print("儲存成功!");
                    StringBuilder text = new StringBuilder();
                    text.append("<p><font style='color:red'>請勿回覆此信</font></p>");
                    text.append("<p>採購系統訊息:</p><p>使用者: ");
                    text.append(session.getAttribute("name"));
                    text.append(" 在 <strong>");
                    text.append(new Date());
                    text.append("</strong> 時間申請了新一筆訂單</p>");
                    text.append("<p><a href='");
                    text.append(webdir[0]);
                    text.append("'>您可以至系統中查詢訂單商品內容。</a></p>");
                    mailsender.sendMail(PostData.class, from, to, subject, text.toString());
                } else {
                    out.print("儲存失敗!");
                }
                ocl.clear();
            } else {
                out.print("查無此人 " + user_id);
            }
        } else {
            out.print("發生錯誤，無資料新增。");
        }
        res.addHeader("refresh", "3;URL=Home");
    }
}
