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
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author Wei.Cheng
 */
@WebServlet(name = "AddProduct", urlPatterns = {"/AddProduct.do"})
public class AddProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setCharacterEncoding("utf-8");
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        String listid = req.getParameter("id");
        String altlistid = req.getParameter("listid");
        String product[] = req.getParameterValues("product");
        String count[] = req.getParameterValues("count");
        OrderContentBean ocBean = new OrderContentBean();
        OrderListBean olBean = new OrderListBean();
        OrderContent oc = null;
        List l = new ArrayList();
        List ollist = olBean.getOrderList();

        if (listid != null) {
            Iterator it = ollist.iterator();
            int status = -1;
            while (it.hasNext()) {
                OrderList ol = (OrderList) it.next();
                if (ol.getId() == Integer.parseInt(listid)) {
                    status = ol.getStatus();
                    break;
                }
            }
            if (status != 4) {
                req.setAttribute("listid", listid);
                req.getRequestDispatcher("AlterAfford").forward(req, res);
            }else{
                out.print("訂單已被管理者鎖定，請重新操作。");
                res.addHeader("refresh", "3;URL=Home?viewlistid=" + listid);
            }
        }

        if (altlistid != null && product != null && count != null) {
            Iterator it = ollist.iterator();
            int status = -1;
            while (it.hasNext()) {
                OrderList ol = (OrderList) it.next();
                if (ol.getId() == Integer.parseInt(altlistid)) {
                    status = ol.getStatus();
                    break;
                }
            }

            if (status != 4) {
                for (int i = 0; i < product.length; i++) {
                    oc = new OrderContent();
                    oc.setProduct(Integer.parseInt(product[i]));
                    oc.setQuantity(Integer.parseInt(count[i]));
                    l.add(oc);
                }
                ocBean.insertOrderContent(Integer.parseInt(altlistid), l);
            } else {
                out.print("訂單已被管理者鎖定，請重新操作。");
            }
            res.sendRedirect("Home?viewlistid=" + altlistid);
        }
        l.clear();
        ollist.clear();
    }
}
