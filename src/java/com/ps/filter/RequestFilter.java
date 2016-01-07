package com.ps.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@WebFilter("*.jsp")
public class RequestFilter implements Filter {

//    @SuppressWarnings("unused")
//    private FilterConfig fc;
    private ArrayList urlList;

    @Override
    public void init(FilterConfig config) throws ServletException {
        // Read the URLs to be avoided for authentication check (From web.xml)
        String urls = config.getInitParameter("avoid-urls");
        StringTokenizer token = new StringTokenizer(urls, ",");
        urlList = new ArrayList();
        while (token.hasMoreTokens()) {
            urlList.add(token.nextToken());
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpReq = (HttpServletRequest) req;
        HttpServletResponse httpRes = (HttpServletResponse) res;
        HttpSession session = ((HttpServletRequest) req).getSession(false);// don't create if it doesn't exist
        String url = httpReq.getServletPath();

        boolean allowedRequest = false;
        String strURL = "";

        // To check if the url can be excluded or not
        for (Object urlList1 : urlList) {
            strURL = urlList1.toString();
            if (url.startsWith(strURL)) {
                allowedRequest = true;
            }
        }
        if (!allowedRequest) {
            if (session == null || session.getAttribute("id") == null || session.getAttribute("state") == null) {//&& !session.isNew() && 
                httpReq.getRequestDispatcher("Login").forward(httpReq, httpRes);   
            } 
        }
        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
    }
}
