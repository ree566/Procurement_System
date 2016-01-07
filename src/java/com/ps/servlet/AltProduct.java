/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.servlet;

import com.ps.bean.ProductBean;
import com.ps.entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
@WebServlet(name = "AltProduct", urlPatterns = {"/AltProduct.do"})
public class AltProduct extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        res.setCharacterEncoding("utf-8");
        PrintWriter out = res.getWriter();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String moq = req.getParameter("moq");
        String company = req.getParameter("company");
        String cata = req.getParameter("cata");
        String unit = req.getParameter("unit");
        String product[] = req.getParameterValues("product");
        ProductBean pBean = new ProductBean();
        StringBuilder sb = new StringBuilder();
        boolean b = false;
        if (name != null && price != null && moq != null && company != null) {
            Product p = new Product();
            List l = new ArrayList();
            p.setName(name);
            p.setPrice(Integer.parseInt(price));
            p.setMoq(Integer.parseInt(moq));
            p.setComp(Integer.parseInt(company));
            p.setType(Integer.parseInt(cata));
            p.setUnit(Integer.parseInt(unit));
            if (id != null) {
                String st[] = id.split("#tr");
                p.setId(Integer.parseInt(st[1]));
                l.add(p);
                b = pBean.updateProduct(l);
            } else {
                l.add(p);
                b = pBean.insertProduct(l);
            }
            l.clear();
        }
        if (product != null) {
            List l = new ArrayList();
            for (String st : product) {
                Product p = new Product();
                p.setId(Integer.parseInt(st));
                l.add(p);
//                out.print(st);
            }
            l.clear();
            if (!pBean.deleteProduct(l)) {
                sb.append("某筆訂單中包含了此項商品，請先刪除訂單再做商品刪除。\\n");
            } else {
                b = true;
            }
        }
        if (b) {
            sb.append("更改成功");
        } else {
            sb.append("無資料更改");
        }
        out.print("<script>alert('" + sb.toString() + "')</script>");
        res.addHeader("refresh", "0;URL=ProductManage");
    }
}
