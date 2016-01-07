<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.apache.log4j.*"%> 
<%@ page isErrorPage="true" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>Error</title>
    </head>
    <body>
        <% Logger logger = Logger.getLogger("errorpage.jsp");
            logger.error(exception);
        %>
        <div style="text-align:center">
            <img src="http://i.imgur.com/ovQADHi.jpg?1"></img>
            <p>
            <h2>Oops，系統出錯了。</h2>
            <font style="color:red">
                <p>伺服器錯誤訊息: </p>
                <p>${ pageContext.exception }</p>
                <p>${ pageContext.response.getStatus() }</p>
            </font>
        </p>
        您可以選擇 <a href="mailto:Wei.Cheng@advantech.com.tw?Subject=程式出現錯誤訊息&body=請貼上錯誤訊息 或者 貼圖:">回報錯誤訊息給管理員</a> 或者 <a href="javascript:history.back()">返回上頁</a>
    </div>
</body>
</html>
