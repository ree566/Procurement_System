<%-- 
    Document   : product
    Created on : 2015/8/5, 下午 05:14:04
    Author     : Wei.Cheng
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="product" class="com.ps.bean.ProductBean" scope="application" />
<jsp:useBean id="catagorized" class="com.ps.bean.CatagorizedBean" scope="application" />
<jsp:useBean id="unit" class="com.ps.bean.UnitBean" scope="application" />
<jsp:useBean id="company" class="com.ps.bean.CompanyBean" scope="application" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="style/jquery.dataTables.css">
        <style>
            /*div{padding:10px 3px 10px 42px;}*/
            table{
                text-align: center;
            }
            .error{
                color: red;
            }

        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <!--<script src="js/jquery-tablepage-1.0.js"></script>-->
        <script src="js/jquery.dataTables.js"></script>
        <script src="js/jquery.blockUI.js"></script>
        <script src="js/jquery.validate_1.js"></script> 
        <script src="js/product-min.js"></script> 
        <script>
            $(document).ready(function () {
                $(":button,:submit").addClass("btn btn-default");
                $("input[type='number'],:text,select").addClass("form-control");
            });
        </script>
        <title>採購系統</title>
    </head>
    <body>
        <c:if test="${sessionScope.Jobnumber != \"root\"}">
            <c:redirect url="Home"/>
        </c:if>
        <script>
            $.blockUI({
                css: {
                    border: 'none',
                    padding: '15px',
                    backgroundColor: '#000',
                    '-webkit-border-radius': '10px',
                    '-moz-border-radius': '10px',
                    opacity: .5,
                    color: '#fff'
                },
                fadeIn: 0
                , overlayCSS: {
                    backgroundColor: '#FFFFFF',
                    opacity: .3
                }
            });
        </script>
        <jsp:include page="pages/head.jsp"></jsp:include>
            <div>
                <div style="width:95%">
                    <form action="AltProduct.do" method="post">
                        <table id="myTable" class="table-striped table" align="center" cellpadding="1" cellspacing="1">
                            <thead>
                                <tr>
                                    <th><input type="checkbox" id="del">刪除</th>
                                    <th>編號</th>
                                    <th>商品名稱</th>
                                    <th>單價</th>
                                    <th>MOQ</th>
                                    <th>廠商</th>
                                    <th>類別</th>
                                    <th>量詞單位</th>
                                    <th>動作</th>
                                    <th>狀態</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="p" items="${product.product}">
                                <tr id="tr${p.id}">
                                    <td><input type="checkbox" name="product" value="${p.id}"></td>
                                    <td>${p.id}</td>
                                    <td class="name">${p.name}</td>
                                    <td class="price">${p.price}</td>
                                    <td class="moq">${p.moq}</td>
                                    <td class="company">
                                        <select disabled="disabled">
                                            <c:forEach var="comp" items="${company.company}">
                                                <%--<c:if test="${comp.id == p.comp}">--%>
                                                <option value="${comp.id}" ${comp.id == p.comp ? "selected":""}>${comp.name}</option>
                                                <%--</c:if>--%>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td class="cata">
                                        <select disabled="disabled">
                                            <c:forEach var="cata" items="${catagorized.catagorized}">
                                                <%--<c:if test="${cata.id == p.type}">--%>
                                                <option value="${cata.id}" ${cata.id == p.type ? "selected":""}>${cata.name}</option>
                                                <%--</c:if>--%>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td class="unit">
                                        <select disabled="disabled">
                                            <c:forEach var="u" items="${unit.unit}">
                                                <%--<c:if test="${u.id == p.unit}">--%>
                                                <option value="${u.id}" ${u.id == p.unit ? "selected":""}>${u.name}</option>
                                                <%--</c:if>--%>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td><input type="button" name="tr${p.id}" class="edit" value="編輯"></td>
                                    <td>
                                        <select class="pstatus" id="${p.id}">
                                            <option value="0">鎖定</option>
                                            <option value="1" ${p.status == 1 ? "selected":""}>開放</option>
                                        </select>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th><input type="submit" value="刪除" id="delbtn"></th>
                                <th>編號</th>
                                <th>商品名稱</th>
                                <th>單價</th>
                                <th>MOQ</th>
                                <th>廠商</th>
                                <th>類別</th>
                                <th>量詞單位</th>
                                <th>動作</th>
                                <th>狀態</th>
                            </tr>
                        </tfoot>
                    </table>
                </form>
            </div>
        </div>
        <!--<span id="table_page"></span>-->
        <div>
            <div class="form-inline">
                <form id="createp" action="AltProduct.do" method="post">
                    <input type='text' name="name" placeholder='商品名稱'>
                    <input type='number' min='1' name="price" placeholder='商品單價'>
                    <input type='number' min='1' name="moq" placeholder='MOQ'>
                    <select name="company">
                        <c:forEach var="comp" items="${company.company}">
                            <option value="${comp.id}">${comp.name}</option>
                        </c:forEach>
                    </select>
                    <select name="cata">
                        <c:forEach var="cata" items="${catagorized.catagorized}">
                            <option value="${cata.id}">${cata.name}</option>
                        </c:forEach>
                    </select>
                    <select name="unit">
                        <c:forEach var="u" items="${unit.unit}">
                            <c:if test="${u.id != 1}">
                                <option value="${u.id}">${u.name}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <!--<input type='text' min='0.1' max='1' step='0.1' name='discount' id="new_pdiscount" placeholder='折扣'>-->
                </form>
            </div>
            <div id="formmsg"></div>
        </div>
        <jsp:include page="pages/footer.jsp"></jsp:include>
                <script>             $(window).load(function () {
        $.unblockUI();
    //            $('#loading').hide(1000);
    });
        </script>
    </body>
</html>
