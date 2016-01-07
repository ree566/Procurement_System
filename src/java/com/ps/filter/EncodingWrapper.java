/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ps.filter;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Wei.Cheng
 */
public class EncodingWrapper extends HttpServletRequestWrapper {

    private String ENCODING;

    public EncodingWrapper(HttpServletRequest request, String ENCODING) {
        super(request);
        this.ENCODING = ENCODING;
    }

    @Override
    public String[] getParameterValues(String name) {
        //替換request內容，轉換特殊符號
        String[] value = getRequest().getParameterValues(name);
        if (value != null) {
            for (int i = 0; i < value.length; i++) {
                try {
                    byte[] b = value[i].getBytes("ISO-8859-1");
                    value[i] = new String(b, ENCODING);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                value[i] = StringEscapeUtils.escapeXml(value[i]);
            }
        }
        return value;
    }

    @Override
    public String getParameter(String name) {
        //替換request內容，轉換特殊符號
        String value = getRequest().getParameter(name);
        if (value != null) {
            try {
                byte[] b = value.getBytes("ISO-8859-1");
                value = new String(b, ENCODING);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
        return value;
    }
}
