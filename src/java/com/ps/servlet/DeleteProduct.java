/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.servlet;

import com.ps.bean.OrderListBean;
import com.ps.bean.OrderTotalBean;
import com.ps.entity.OrderTotal;
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
@WebServlet(name = "DeleteProduct", urlPatterns = {"/DeleteProduct.do"},
        initParams = {
            @WebInitParam(name = "subject", value = "採購系統訊息")
        }
)
public class DeleteProduct extends HttpServlet {

    private String from;
    private String adminmail;
    private String to;
    private String subject;
    private String adminphone;

    @Override
    public void init() throws ServletException {
        from = getServletContext().getInitParameter("mailserver");
        adminmail = getServletContext().getInitParameter("adminmail");
        to = "";
        subject = getServletConfig().getInitParameter("subject");
        adminphone = getServletContext().getInitParameter("adminphone");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        PrintWriter out = res.getWriter();
        String products = req.getParameter("product");
        String cause = req.getParameter("cause");
        String webdir[] = req.getRequestURL().toString().split(req.getServletPath());
        if ("".equals(products.replace(",", ""))) {
            out.print("請輸入正確的格式");
            res.addHeader("refresh", "3;URL=Home");
        } else {
            OrderTotalBean otBean = new OrderTotalBean();
            OrderListBean olBean = new OrderListBean();
            mailsender ms = new mailsender();
            Set set = new TreeSet();
            Set listset = new HashSet();
            Map userMap = new HashMap();

            products = products.trim();
            String prod[] = products.split(",");
            set.addAll(Arrays.asList(prod));//把使用者輸入的字串分割後放入set(取不重複的product id)
            List l = otBean.getOrderTotalByProduct(set);
            Iterator it = l.iterator();

            boolean b = false;
            while (it.hasNext()) {
                OrderTotal ot = (OrderTotal) it.next();
                userMap.put(ot.getApplicant(), ot.getaMail());
                listset.add(ot);
            }
            it = userMap.keySet().iterator();
            while (it.hasNext()) {//總共幾個人要送信
                int key = (int) it.next();
                int id = -1;
                StringBuilder text = new StringBuilder();
                text.append("<p>下列訂單編號</p>");
                to = (String) userMap.get(key);
                Iterator it2 = listset.iterator();
                while (it2.hasNext()) {//總共有哪幾筆訂單
                    OrderTotal ot = (OrderTotal) it2.next();
                    if (ot.getApplicant() == key) {
                        id = ot.getListid();
                        b = olBean.updateStatus(2, id);//先更新狀態到2
                        text.append("<p>訂單編號: ");
                        text.append(id);
                        text.append(" 的以下商品</p><ul>");
                        Iterator it3 = l.iterator();
                        while (it3.hasNext()) {
                            OrderTotal ott = (OrderTotal) it3.next();
                            if (ott.getListid() == ot.getListid()) {
                                String st = "<li>" + ott.getProduct() + " 數量: " + ott.getL_quantity() + "</li>";
                                text.append(st);
                            }
                        }
                        text.append("</ul>");
                    }
                }
                text.append("</ul>");
                text.append("<p>因為 <font style='color:red'>");
                text.append(cause);
                text.append("</font> 被管理者退簽</p>");
                text.append("<p>請您移駕至<a href='");
                text.append(webdir[0]);
                text.append("'>本系統</a>中修改您的訂單，將商品數量補足或刪除商品，謝謝。</p>");
                text.append("<p>如有疑慮請<a href='mailto:");
                text.append(adminmail);
                text.append("'>聯絡我</a>或撥打分機:");
                text.append(adminphone);
                text.append("</p>");
                b = ms.sendMail(DeleteProduct.class, from, to, subject, text.toString());
            }
            if (b) {
                out.print("退簽成功!");
            } else {
                out.print("發生錯誤，請重新操作。");
            }
            l.clear();
            res.addHeader("refresh", "3;URL=Home");
        }
        //刪除內容
        //找出訂單邊號>找到orderlist裏頭的email 設定成to 
        //mail內容 依序訂單編號做set迴圈(不重複)找到商品編號
        //所有商品帶出比對商品編號 附上商品名稱以及編號、數量、訂單編號、目前日期在mailbody
        //使用者假使相同附在同一封mail
        /*
         採購系統訊息:
         您的訂單編號:XXX,XXX,XXXX
         裏頭包含的商品:
         TTT數量Y
         RRR數量B
         VVV數量M
         AAA數量A
         因為(原因:俊良輸入)而遭到了退簽
         訂單內的其他商品(除退簽)不會受到影響
         退簽的商品將會從您的訂單中刪除
         如有疑慮請聯絡我(超連接俊良mail)或撥打分機:0000
         */
        //使用者 > 訂單 > 商品
    }
}
