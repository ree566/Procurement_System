/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.servlet;

import com.ps.bean.OrderContentBean;
import com.ps.bean.OrderListBean;

import com.ps.entity.OrderContent;
import com.ps.entity.OrderList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Wei.Cheng
 */
@WebServlet(urlPatterns = {"/Test.do"})
public class Test extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/plain");
        res.setCharacterEncoding("utf-8");
        PrintWriter out = res.getWriter();
        String id[] = req.getParameterValues("ocid[]");
        String quantity[] = req.getParameterValues("quantity[]");
        String listid = req.getParameter("qlistid");

        OrderListBean odBean = new OrderListBean();
        List list = odBean.getOrderList();
        Iterator it = list.iterator();
        int liststat = -1;
        while (it.hasNext()) {
            OrderList ol = (OrderList) it.next();
            if (ol.getId() == Integer.parseInt(listid)) {
                liststat = ol.getStatus();
            }
        }
        list.clear();

        if (liststat != 4) {

            OrderContent oc = null;
            OrderContentBean ocBean = new OrderContentBean();
            List l = new ArrayList();
            if (quantity != null) {
                for (int i = 0; i < id.length; i++) {
                    oc = new OrderContent();
                    oc.setId(Integer.parseInt(id[i]));
                    oc.setQuantity(Integer.parseInt(quantity[i]));
                    l.add(oc);
                }
                if (ocBean.updateOrderContent(l)) {
                    out.print("update success");
                } else {
                    out.print("update fail");
                }
                l.clear();
            } else {
                out.print("no data");
            }
        }else{
            out.print("訂單已被管理者鎖定，請重新操作。");
        }
    }
}
