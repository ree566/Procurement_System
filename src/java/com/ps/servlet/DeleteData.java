/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.servlet;

import com.ps.bean.OrderListBean;
import com.ps.test.mailsender;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Wei.Cheng
 */
@WebServlet(name = "DeleteData", urlPatterns = {"/DeleteData.do"},
        initParams = {
            @WebInitParam(name = "subject", value = "採購系統:訂單刪除通知")
        }
)
public class DeleteData extends HttpServlet {

    private String subject;
    private String from;

    @Override
    public void init() throws ServletException {
        from = getServletContext().getInitParameter("mailserver");
        subject = getServletConfig().getInitParameter("subject");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String id = req.getParameter("deleteid");
        res.setContentType("text/plain");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);

        OrderListBean olBean = new OrderListBean();
        Set set = new TreeSet();
        mailsender ms = new mailsender();
        StringBuilder text = new StringBuilder();
        String usermail = req.getParameter("usermail");

        if (id != null) {
            set.add(Integer.parseInt(id));
            if (olBean.deleteOrderList(set)) {
                out.print("刪除成功");
                if (usermail != null) {
//                    ms.sendMail(DeleteData.class, from, usermail, subject, text.toString());
                }
                //寄信?
            } else {
                out.print("刪除失敗");
            }
        } else {
            out.print("傳入資料發生錯誤");
        }
        res.addHeader("refresh", "1;URL=Home");
    }
}
