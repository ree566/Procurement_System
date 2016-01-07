/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Wei.Cheng
 */
@WebFilter(
        urlPatterns = {"/*"},
        initParams = {
            @WebInitParam(name = "ENCODING", value = "UTF-8")
        }
)
public class EncodingFilter implements Filter {

    private String ENCODING;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ENCODING = filterConfig.getInitParameter("ENCODING");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if ("GET".equals(req.getMethod())) {
            req = new EncodingWrapper(req, ENCODING);
        }else{
            req.setCharacterEncoding(ENCODING);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        
    }
}
