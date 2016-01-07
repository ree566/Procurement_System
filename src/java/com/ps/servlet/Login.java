package com.ps.servlet;

import com.ps.bean.IdentitBean;
import com.ps.entity.Identit;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = {"/Login.do"},
        initParams = {
            @WebInitParam(name = "LOGIN_VIEW", value = "Login")}
)
public class Login extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String LOGIN_VIEW;

    @Override
    public void init() throws ServletException {
        LOGIN_VIEW = getServletConfig().getInitParameter("LOGIN_VIEW");
    }

    @Override
    @SuppressWarnings("null")
    public void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        String name = req.getParameter("username");
        String password = req.getParameter("password");
        boolean flag = false;
        IdentitBean id = new IdentitBean();
        Identit i = new Identit();
        name = name.trim();
        password = password.trim();
        i = id.login(name, password);
        if (i != null) {
            session.setAttribute("name", i.getName());
            session.setAttribute("id", i.getId());
            session.setAttribute("Jobnumber", name);
            session.setAttribute("state", "login");
            session.setAttribute("mail", i.getEmail());
            flag = true;
        }
        if (flag) {
            if (i.getId() == 1) {
                res.sendRedirect("Manage");
            } else {
                res.sendRedirect("Home");
            }
        } else {
            req.setAttribute("errormsg", "錯誤的帳號或密碼");
            req.getRequestDispatcher(LOGIN_VIEW).forward(req, res);
        }
    }
}
