<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="ol" class="com.ps.bean.OrderListBean" scope="application" />
<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<style>
    .active{
        background-color: red;
    }
    a{
        color:#333333;
        text-decoration: none;
        font-size:18px;
        /*display:block;*/
    }
    div{padding:10px 3px 10px 42px;}
    a:link,a:visited{
        color:#999999;
        font-size: 14px;
        text-decoration:none;
    }
</style>
<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script>
    $("body").on("click", "#update", function () {
        $.ajax({
            type: "POST",
            url: "UVS.do"
        });
        $("span.badge").text(0);
        $("span.badge").removeClass("active");
    });
</script>
<input type="hidden" id="state" value="<c:out value="${sessionScope.state}" default="" />" />
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" ${sessionScope.id == 1?"href='Manage'":"href='Home'"}><b>採購系統</b></a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <c:if test="${sessionScope.id != 1}">
                    <li><a href="Order">訂購</a></li>
                    </c:if>
                <li><a href="History">歷史訂單查詢</a></li>
                <!-- Trigger the modal with a button -->
                <li id="st"><a href="" data-toggle="modal" data-target="#status">各項商品狀態</a></li>
                    <c:if test="${sessionScope.id == 1}">
                    <li><a href="ProductManage">商品修改</a></li>
                    </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li id="login">   
                    <c:choose>
                        <c:when test="${sessionScope.name == null}">
                            <a href="Login">
                                <span class="glyphicon glyphicon-log-in"></span> 人員登入
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a>${sessionScope.name}您好。</a>
                        </c:otherwise>
                    </c:choose>
                </li>
                <li id="logout"><a href="Logout.do">登出</a></li>
            </ul>
        </div>
    </div>
</nav>
<!--<a href="SearchFile.do">我是關鍵字</a>-->
<!-- 為了省略include所造成多餘的<html><body>標籤而簡化，encoding會有問題還是要加上開頭 -->