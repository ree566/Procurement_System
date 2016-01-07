/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.servlet;

import com.ps.bean.OrderListAffordBean;
import com.ps.bean.OrderListBean;
import com.ps.entity.OrderList;
import com.ps.entity.OrderListAfford;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wei.Cheng
 */
@WebServlet(name = "ListAfford", urlPatterns = {"/ListAfford.do"})
public class ListAfford extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setContentType("text/plain");
        res.setCharacterEncoding("utf-8");
        PrintWriter out = res.getWriter();
        OrderListAffordBean olfBean = new OrderListAffordBean();
        OrderListAfford olf = new OrderListAfford();

        List l = new ArrayList();
        String olfid = req.getParameter("olfid");
        String listid = req.getParameter("listid");
        String unit[] = req.getParameterValues("unit");
        String percent[] = req.getParameterValues("percent");

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

        boolean b = false;

        if (liststat != 4) {
            if (olfid != null) {
                olf.setId(Integer.parseInt(olfid));
                l.add(olf);
                if (olfBean.deleteOrderListAfford(l)) {
                    out.print("刪除成功");
                    b = true;
                } else {
                    out.print("刪除失敗");
                }
                l.clear();
            }
            if (listid != null && unit != null && !unit.equals("") && percent != null) {
                for (int i = 0; i < unit.length; i++) {
                    olf = new OrderListAfford();
                    olf.setListid(Integer.parseInt(listid));
                    olf.setUnit(unit[i]);
                    olf.setAff_percent(Double.parseDouble(percent[i]));
                    l.add(olf);
                }
                out.print(olfBean.insertOrderListAfford(l) ? "新增成功" : "新增失敗");
                b = true;
                res.addHeader("refresh", "3;URL=Home?viewlistid=" + listid);
            }
        } else {
            out.print("訂單已被管理者鎖定，請重新操作。");
            res.addHeader("refresh", "3;URL=Home");
        }
        if (!b) {
            out.print("無資料異動");
            res.addHeader("refresh", "3;URL=Home");
        }
    }
}
