/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.servlet;

import com.ps.bean.OrderListBean;
import com.ps.bean.ProductBean;
import com.ps.entity.OrderList;
import com.ps.entity.Product;
import com.ps.test.mailsender;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

/**
 *
 * @author Wei.Cheng
 */
@WebServlet(
        name = "Change",
        urlPatterns = {"/Change.do"},
        initParams = {
            @WebInitParam(name = "subject", value = "訂單完成信件")
        }
)
public class Change extends HttpServlet {

    private String from;
    private String subject;
    private String adminmail;
    private String adminphone;

    @Override
    public void init() throws ServletException {
        from = getServletContext().getInitParameter("mailserver");
        adminmail = getServletContext().getInitParameter("adminmail");
        subject = getServletConfig().getInitParameter("subject");
        adminphone = getServletContext().getInitParameter("adminphone");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        res.setCharacterEncoding("utf-8");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);
        mailsender mailsender = new mailsender();

        String status = req.getParameter("dataString");
        String id = req.getParameter("id");
        String memo = req.getParameter("memo");
        String stat = req.getParameter("stat");
        String mail = req.getParameter("mail");
        String webdir[] = req.getRequestURL().toString().split(req.getServletPath());

        StringBuilder text = new StringBuilder();

        boolean b = false;
        if (status != null && id != null && mail != null) {
            OrderListBean odBean = new OrderListBean();
            if (Integer.parseInt(status) == 5) {
                odBean.updateStatus(Integer.parseInt(status), -1);
            } else {
                odBean.updateStatus(Integer.parseInt(status), Integer.parseInt(id));
                switch (status) {
                    case "1":
                        out.print("success");
                        text.append("<p><font style='color:red'>請勿回覆此信</font></p>");
                        text.append("<p>採購系統訊息:</p>");
                        text.append("<p>您的訂單編號");
                        text.append(id);
                        text.append("已經在 <strong>");
                        text.append(new Date());
                        text.append("</strong> 時間通過了審核</p>");
                        text.append("<p>您可以至<a href='");
                        text.append(webdir[0]);
                        text.append("'>系統中</a>的歷史訂單中查詢訂單商品內容。</p>");
                        text.append("<p>若訂單有問題請<a href='mailto:");
                        text.append(adminmail);
                        text.append("'>寫信給我</a>或是撥打分機:");
                        text.append(adminphone);
                        text.append("</p>");
                        mailsender.sendMail(Change.class, from, mail, subject, text.toString());
                        b = true;
                        break;
                    default:
                        b = false;
                        break;
                }
            }
        }
        if (memo != null && id != null) {
            OrderListBean odBean = new OrderListBean();
            List l = odBean.getOrderList();
            Iterator it = l.iterator();
            int liststat = -1;
            while (it.hasNext()) {
                OrderList ol = (OrderList) it.next();
                if (ol.getId() == Integer.parseInt(id)) {
                    liststat = ol.getStatus();
                }
            }
            l.clear();
            if (liststat != 4) {
                if (odBean.updateMemo(Integer.parseInt(id), memo)) {
                    out.print("success");
                    b = true;
                } else {
                    out.print("fail");
                    b = false;
                }
            } else {
                out.print("訂單已被管理者鎖定，請重新操作。");
            }
            res.addHeader("refresh", "3;URL=Home");
        }
        if (stat != null && id != null) {
            List l = new ArrayList();
            Product p = new Product();
            ProductBean pBean = new ProductBean();
            p.setStatus(Integer.parseInt(stat));
            p.setId(Integer.parseInt(id));
            l.add(p);
            out.print(pBean.updateProductStatus(l));
            l.clear();
        }
    }
}
