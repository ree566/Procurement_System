/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Wei.Cheng
 */
public class EscapeWrapper extends HttpServletRequestWrapper {

    public EscapeWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String[] getParameterValues(String name) {
        //替換request內容，轉換特殊符號
        String[] value = getRequest().getParameterValues(name);
        if (value != null) {
            for (int i = 0; i < value.length; i++) {
                value[i] = StringEscapeUtils.escapeXml(value[i]);
            }
        }
        return value;
    }

    @Override
    public String getParameter(String name) {
        //替換request內容，轉換特殊符號
        String value = getRequest().getParameter(name);
        if(value != null){
            value = StringEscapeUtils.escapeXml(value);
        }
        return value;
    }
}
