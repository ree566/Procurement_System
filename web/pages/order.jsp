<%-- 
    Document   : jsp2
    Created on : 2015/7/29, 下午 01:11:01
    Author     : Wei.Cheng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="catabean" class="com.ps.bean.CatagorizedBean" scope="application" />
<jsp:useBean id="ocbbean" class="com.ps.bean.OrderContentBean" scope="application" />
<jsp:useBean id="olbean" class="com.ps.bean.OrderListBean" scope="application" />
<jsp:useBean id="pbean" class="com.ps.bean.ProductBean" scope="application" />
<jsp:useBean id="ubean" class="com.ps.bean.UnitBean" scope="application" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>採購系統</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
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
        <script>
            var s = new Array();
            $(document).ready(function () {
                $oneprice = 0;
                $total = 0;
                dochange();
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
                        countchange($total);
                        s.push(sum);
                    }
                });
                $("#product_list").on("dblclick", "tr", function () {
                    var text = $(this).children().last().text();
                    text = text.split("元");
                    $total = $total - parseInt(text[0]);
                    countchange($total)
                    $(this).remove();
                });

                $("input[type='reset']").click(function () {
                    $("#product_list").children().remove();
                    $total = 0;
                    countchange(0);
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
                        memo: {required: true} //密码1必填
                    },
                    messages: {
                        memo: "*此欄位必填" //密码1必填
                    },
                    errorPlacement: function (error, element) {                             //错误信息位置设置方法  
                        error.appendTo(element.next());                          //这里的element是录入数据的对象  
                    },
                    submitHandler: function (form) {
                        $.blockUI({message: '<img src="pages/images/loading_spinner.gif" height="42" />', fadeIn: 0});
                        form.submit();
                    }
                });
                $("body").on("click", ".add", function () {   
                    var percentsum = 0;
                    $(".aff_percent").each(function () {
//                        if($(this).val() < 0){
//                            return false;
//                        }
                        percentsum += +$(this).val();
                    });
                    var lastpercent = 100 - percentsum;
                    if (percentsum >= 100) {
                        return false;
                    } else {
                        $obj = $(this).prev().clone();
                        $obj.find(".aff_percent").val(formatFloat(lastpercent, 2));
                        $obj.find(".aff_percent").attr("name","aff_percent");
                        $obj.find(".aff").val(formatFloat($total * lastpercent / 100, 2));
                        $obj.find(":text").val("");
                        $(".aff-obj").last().after($obj);
                    }
                });

                $("body").on("keyup change", ".aff_percent", function () {
                    var val = $(this).val() * $total / 100;
                    $(this).next().val(formatFloat(val, 2));
                });
                $("body").on("keyup change", ".aff", function () {
                    var val = $(this).val() * 100 / $total;
                    $(this).prev().val(formatFloat(val, 2));
                });
                $("body").on("click", ".del", function () {
                    if ($(".aff-obj").size() > 1) {
                        $(".aff-obj").last().remove();
                    }
                });
                $(":submit").click(function () {
                    var sum = 0;
                    $(".aff_unit").each(function () {
                        if ($(this).val() == "") {
                            alert("部門代碼不能為空值");
                            return false;
                        }
                    });
                    $(".aff_percent").each(function () {
                        if ($(this).val() < 0) {
                            return false;
                        }
                        sum += parseInt($(this).val());
                    });
                    if (sum > 100) {
                        alert("趴數總和不能大於100");
                        return false;
                    }
                    if ($("#product_list").children().size() == 0) {
                        alert("請選擇商品");
                        return false;
                    }
//                    return false;
                });
            });
            function countchange(total) {
                $("#count").text(total + "元");
//                $(".aff-obj2 .aff").attr("max",$("#count").val());
            }
            function dochange() {
                $.ajax({
                    type: "POST",
                    url: "SearchTotal.do",
                    data: {dataString: $('#product option:selected').val()},
                    dataType: "html",
                    success: function (response) {
                        var st = response.split(",");
                        $oneprice = st[0];
                    }
                });
                $('#quantity').val("");
            }
            function formatFloat(num, pos)
            {
                var size = Math.pow(10, pos);
                return Math.round(num * size) / size;
            }
            $(document).ajaxSuccess(function (event, xhr, settings) {
                $("#price").text($oneprice + "元");
            });
        </script>
    </head>
    <body>
        <div id="goback">go back</div>
        <!---->
        <form id="myform" action="PostData.do" method="post">
            <table class="table-hover" border="1">
                <tr>
                    <th>申請人:</th>
                    <td>${sessionScope.name}<input type="hidden" name="id" value="${sessionScope.id}"></td>
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
                        <input type="button" value="＋" id="add" name="add" class="formsubmit btn btn-default">
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
                    <th>
                        申請事由:
                    </th>
                    <td>
                        <textarea name="memo" style="width:100%" rows="6" placeholder="若有備註請在此附加"></textarea>
                        <div id="textareamsg"></div>
                    </td>
                </tr>
                <tr>
                    <th>
                        價錢分擔:
                    </th>
                    <td>
                        <div class="aff-obj">
                            <input type="text" name="aff_unit" class="aff_unit" placeholder="請輸入部門代碼">
                            <input type="number" class="aff_percent" name="aff_percent" placeholder="請輸入負擔趴數" min="0.01" step="0.01" max="100" value="100">%
                            <input type="number" class="aff" placeholder="金額" min="1" step="0.01">元
                        </div>
                        <input type="button" class="add formsubmit btn btn-default" value="新增部門">
                        <input type="button" class="del formsubmit btn btn-default" value="刪除部門">
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <div style="float:left;background-color: plum"><font style="color:white">非黑色商品代表目前不可訂購，雙擊訂單內容可取消商品。</font></div>
                        <div class="floating">
                            <input type="submit" value="確定" onclick="return confirm('確定提出申請?')" class="formsubmit btn btn-default">
                            <input type="reset" value="重設" class="formsubmit btn btn-default">
                        </div>
                    </td>
                </tr>
            </table>
            <div id="msg"></div>
        </form>
    </body>
</html>
