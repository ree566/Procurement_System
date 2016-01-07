<%-- 
    Document   : jsp2
    Created on : 2015/7/29, 下午 01:11:01
    Author     : Wei.Cheng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="catabean" class="com.ps.bean.CatagorizedBean" scope="application" />
<jsp:useBean id="otbean" class="com.ps.bean.OrderTotalBean" scope="application" />
<jsp:useBean id="olbean" class="com.ps.bean.OrderListBean" scope="application" />
<jsp:useBean id="pbean" class="com.ps.bean.ProductBean" scope="application" />
<jsp:useBean id="ubean" class="com.ps.bean.UnitBean" scope="application" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>採購系統</title>
        <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
        <link rel="stylesheet" href="style/jquery.dataTables.css">
        <style>
            .floating{
                float: right;
            }
            td{
                padding: 5px;
            }
            th{
                text-align: center;
            }
            textarea{
                resize:none;
            }
            #goback {
                position: fixed;
                right: 20px;
                bottom: 20px;    
                padding: 10px 15px;    
                font-size: 20px;
                background: #777;
                color: white;
                cursor: pointer;
            }     
            .error{
                color: red;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
        <script src="js/jquery.blockUI.js"></script>
        <script src="js/jquery.validate_1.js"></script> 
        <!--<script src="js/order.js"></script>--> 
        <script>
            var s = new Array();
            $(document).ready(function () {
                $oneprice = 0;
                $total = 0;
//                $discount = 0;
                dochange();
                $(".total").each(function () {
                    $total += parseInt($(this).val());
                });
                $("#count").text($total + "元");
                $("#add").click(function () {
                    if ($('#quantity').val() == 0) {
                        return false;
                    } else {
                        var text = $('#product option:selected').text();
                        var productid = $('#product option:selected').val();
                        var text2 = text.split(" | MOQ:");
                        var sum = $('#quantity').val() * $oneprice;
                        var msg = text2[0] + " X " + $('#quantity').val();
                        $("#product_list").append("<tr><td>" + msg + "<input type='hidden' name='product' value='" + productid + "'><input type='hidden' name='count' value='" + $('#quantity').val() + "'></td><td>" + sum + "元</td></tr>");
                        $total = $total + sum;
                        $("#quantity").val("");
                        $("#count").text($total + "元");
                        s.push(sum);
                    }
                });
                $("#product_list").on("dblclick", "tr", function () {
                    var text = $(this).children().last().text();
                    text = text.split("元");
                    $total = $total - parseInt(text[0]);
                    $("#count").text($total + "元");
                    $(this).remove();
                });

                $("input[type='reset']").click(function () {
                    $("#product_list").children().remove();
                    $total = 0;
                    $("#count").text("0元");
                });
                $("#goback").click(function (event) {
                    event.preventDefault();
                    history.back(1);
                });
                $("option").each(function () {
                    if ($(this).attr("disabled")) {
                        $(this).attr("style", "color:pink");
                    }
                });
                $("#myform").validate({
                    rules: {
//                        memo: {required: true} //密码1必填
                    },
                    messages: {
//                        memo: "*此欄位必填" //密码1必填
                    },
                    errorPlacement: function (error, element) {                             //错误信息位置设置方法  
                        error.appendTo(element.next());                          //这里的element是录入数据的对象  
//                    $("#formmsg").html(error);
//                                $(element).attr("style","color:red");
                    },
                    submitHandler: function (form) {
                        $.blockUI({message: '<img src="pages/images/loading_spinner.gif" height="42" />', fadeIn: 0});
                        form.submit();
                    }
                });
                $(":submit").click(function () {
                    if ($("#product_list").children().size() == 0) {
                        alert("請選擇商品");
                        return false;
                    }
                });
            });
            function dochange() {
                $.ajax({
                    type: "POST",
                    url: "SearchTotal.do",
                    data: {dataString: $('#product option:selected').val()},
                    dataType: "html",
                    success: function (response) {
                        var st = response.split(",");
                        $oneprice = st[0];
//                        $discount = st[1];
                    }
                });
                var num = $('#product option:selected').text();
                var num2 = num.split(" | MOQ:");
//                $("#quantity").attr("min", num2[1]);
                $('#quantity').val("");
            }
            $(document).ajaxSuccess(function (event, xhr, settings) {
                $("#price").text($oneprice + "元");
            });
        </script>
    </head>
    <body>
        <c:if test="${requestScope.listid == null}">
            <c:redirect url="" />
        </c:if>
        <div id="goback">go back</div>
        <!---->
        <h3>修改訂單</h3>
        <form id="myform" action="AddProduct.do" method="post">
            <table class="table-hover" border="1">
                <tr>
                    <th>訂單編號:</th>
                    <td>${requestScope.listid}<input type="hidden" name="listid" value="${requestScope.listid}"></td>
                </tr>
                <tr>
                    <th>
                        商品:
                    </th>
                    <td>         
                        <select id="product" name="products" onchange="dochange()">
                            <c:forEach var="item" items="${catabean.catagorized}">
                                <OPTGROUP label="${item.name}">
                                    <c:forEach var="item2" items="${pbean.product}">
                                        <c:if test="${item.id == item2.type}">
                                            <c:forEach var="u" items="${ubean.getOneUnit(item2.unit)}">
                                                <option value="${item2.id}" ${item2.status == '0' ? "disabled" : ""}>${item2.name} | 單位:${u.name} | MOQ:${item2.moq}</option>
                                            </c:forEach>
                                        </c:if>
                                    </c:forEach>
                                </OPTGROUP>
                            </c:forEach>
                        </select>
                        <input type="number" name="quantity" placeholder="請輸入數量" min="1" id="quantity">
                        <input type="button" value="＋" id="add" name="add">
                    </td>
                </tr>
                <tr>
                    <th>
                        商品單價:
                    </th>
                    <td>
                        <div id="price" style="color:red"></div>
                    </td>
                </tr>
                <tr>
                    <th>
                        已選擇商品:
                    </th>
                    <td>
                        <div style="width: 100%; height:250px; overflow:auto;">
                            <table id="product_list">
                                <c:forEach var="ot" items="${otbean.orderTotal}">
                                    <c:if test="${ot.listid == requestScope.listid}">
                                        <tr>
                                            <td>
                                                ${ot.product} X ${ot.l_quantity}
                                                <input type='hidden' name='product' value='${ot.pid}'>
                                                <input type='hidden' name='count' value='${ot.l_quantity}'>
                                            </td>
                                            <td>
                                                <input type="hidden" value="${ot.total}" class="total">${ot.total}元
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:forEach>
                            </table>
                        </div>
                    </td>
                </tr>
                <tr>
                    <th>
                        項目總金額:
                    </th>
                    <td>
                        <div id="count" style="color:red"></div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div style="float:left;background-color: plum"><font style="color:white">非黑色商品代表目前不可訂購，雙擊訂單內容可取消商品。</font></div>
                        <div class="floating">
                            <input type="submit" value="確定" onclick="return confirm('確定修改內容?')">
                            <input type="reset" value="重設">
                        </div>
                    </td>
                </tr>
            </table>
            <div id="msg"></div>
        </form>
    </body>
</html>
