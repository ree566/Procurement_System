package com.ps.filter;  
  
import java.io.*;  
import javax.servlet.*;  
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
  
@WebFilter("/*")
public class EscapeFilter implements Filter {  
  
    @Override
    public void init(FilterConfig config) throws ServletException {  
        // 接收初始化的参数  
    }  
  
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,  
            FilterChain chain) throws IOException, ServletException {  
        //替換request內容，轉換特殊符號
        HttpServletRequest requestWrapper = new EscapeWrapper((HttpServletRequest) request);
        
        //继续执行  
        chain.doFilter(requestWrapper, response);  
//        chain.doFilter(request, response);  
    }  

    @Override
    public void destroy() {
        
    }
}  