/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.servlet;

import com.ps.bean.ProductBean;
import com.ps.entity.Product;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author Wei.Cheng
 */
@WebServlet(name = "SearchTotal", urlPatterns = {"/SearchTotal.do"})
public class SearchTotal extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setCharacterEncoding("Utf8");
        res.setContentType("text/html");
        String keyword = req.getParameter("dataString");
        if (keyword != null) {
            ProductBean pBean = new ProductBean();
            List l = pBean.getProduct();
            Iterator it = l.iterator();
            while (it.hasNext()) {
                Product p = (Product) it.next();
                if (p.getId() == Integer.parseInt(keyword)) {
                    res.getWriter().print(p.getPrice());
                }
            }
            l.clear();
        }
    }
}
