<%-- 
    Document   : index
    Created on : 2015/7/23, 上午 09:33:56
    Author     : Wei.Cheng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="ordertotal" class="com.ps.bean.OrderTotalBean" scope="application" />
<jsp:useBean id="orderlist" class="com.ps.bean.OrderListBean" scope="application" />
<jsp:useBean id="identit" class="com.ps.bean.IdentitBean" scope="application" />
<jsp:useBean id="pmsBean" class="com.ps.bean.PMoqStatusBean" scope="application" />
<jsp:useBean id="olfBean" class="com.ps.bean.OrderListAffordBean" scope="application" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>採購系統</title>
        <!--<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">-->
        <style>
            
            #loading {
                background-color:#f6f7f8;border:1px solid #606060;font-size:24px;padding:20px;
            }
            nav{
                height: 80px;
                font-size: small;
            }
            #floating{ 
                padding: 10px 15px;    
                /*font-size: 20px;*/
                background: #777;
            }
            .block{
                opacity: 0.5;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="js/jquery.blockUI.js"></script>
        <script src="js/jquery-tablepage-1.0.js"></script>
        <script src="js/jquery.validate_1.js"></script> 
        <!--<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
        <!--<script src="js/index.js"></script>-->
        <script>
            $_this = null;
            $(function () {
                //+展開 -收合
                $(".maintable a").click(function () {
                    $_this = $(this).attr("href");
                    if ($($_this).css("display") == "none") {
                        $($_this).slideDown();
                        $(this).html("－");
//                        $("#viewhistory").val($_this);
                    } else {
                        $($_this).slideUp();
                        $(this).html("＋");
                    }
                    return false;
                });
            });
            $(document).ready(function () {
                $(":button,:submit").addClass("btn btn-default");
                $("#myTable1").tablepage($("#table_page"), 10);
                //顯示使用者的觀看紀錄
                $viewid = "#tr" + $("#view").val();
//                alert($viewid);
                $($viewid).show();
//                $(".productcontent").first().show();
                function dismiss() {
                    $(".form").hide();
                    $(".edit").show();
                    $(".btn-close").show();
                    $word = $word.replace(/\r\n|\r|\n/g, "<br>");
                    $("textarea").replaceWith("<p>" + $word + "</p>");
                }
                //****************************************************//
                $(".pstatus").html(function () {
                    var text = $(this).html();
                    text = text.replace(/\r\n|\r|\n/g, "");
                    $(this).html(text);
                });
                $(".form").hide();
                if ($(".maintable").children().length == 0) {
                    //找尋tr class = maintable 底下是否有 children
                    $("#msg").html("查無資料，您尚未有任何訂單。");
//                        alert($(".maintable").children().length);
                }
                $(".status").each(function () {
                    var string = "";
                    var id = ".data" + $(this).prev().text();
                    switch ($(this).val()) {
                        case "1":
                            string = "success";
                            break;
                        case "2":
                            string = "danger";
                            break;
                        case "3":

                            break;
                        case "4":
                            string = "info";
                            $(id).addClass("block");
//                            $(this).parent().parent().addClass("block");
//                                    .block({message: "訂單鎖定中"});
                            break;
                    }
                    $(this).parent().parent().addClass(string);
                });
                $(".modal-body").each(function () {
                    $(this).html($(this).html().replace(/\r\n|\r|\n/g, "<br>"));
                });
                $(".delobj").click(function () {
                    var id = "#tr" + $(this).next().val();
                    var b = confirm('確定刪除訂單?');
                    if (b) {
                        $(this).next().attr("name", "deleteid");
                        $(id + " .usermail").attr("name", "usermail");
                    } else
                        return b;
                });
                $word = "";
                $(".edit").click(function () {
                    $(this).hide();
                    $(this).next().hide();
                    $(this).prevAll().show();
                    $word = $(this).parent().prev().children("p").html();
                    $word = $word.replace(/\<br>/g, "\n");
                    $(this).parent().prev().children("p").replaceWith("<textarea placeholder='修改您的訂單留言' id='memo' name='memo' style='width:100%;height:200px;resize:none;'>" + $word + "</textarea>");
                });
                $(".dismiss").click(function () {
                    dismiss();
                });
                $(".mainmodel").on('hidden.bs.modal', function () {
                    dismiss();
                });

                $(".submit").click(function () {
                    $.ajax({
                        type: "POST",
                        url: "Change.do",
                        data: {
                            memo: $("#memo").val(),
                            id: $(this).prev().val()
                        },
                        dataType: "html",
                        success: function (response) {
                            window.setTimeout('location.reload()', 0);
                            alert(response);
                        },
                        error: function () {
                            alert("to servlet fail");
                        }
                    });
                });
                $("input[type='submit']").click(function () {
                    $(this).prev().attr("name", "id");
                });
                $("body").on("click", ".contentedit", function () {
                    $id = $(this).attr("id").replace("btn", ".edit");
                    $objarray = [];
                    $($id + " font").each(function () {
                        $(this).after("<input type='number' name='' class='alt-list-c-q' min=1 value=" + $(this).text() + ">");
                        $objarray.push($(this).detach());
                    });
//                    $(this).parent().prev().children().html("<input type='text'>");
                    $(this).parent().prev().html("");
                    $(this).parent().html("<input type='button' id='confirm' value='儲存'><input type='button' id='undo' value='取消'>");
                    $contentedit = $(this).detach();
                    $(".contentedit").attr("disabled", true);
                });
                $("body").on("click", "#undo", function () {
                    $(this).replaceWith($contentedit);
                    $("#confirm").remove();
                    var i = 0;
                    $($id + " input[type='number']").each(function () {
                        $(this).replaceWith($objarray[i]);
                        i++;
                    });
                });
                $("body").on("change", ".alt-list-c-q", function () {
                    $(this).attr("class", "altquantity");
                    $(this).next().attr("class", "ocid");
                });
                $("body").on("click", "#confirm", function () {
                    //記得限制使用者輸入的資料(int)
                    $ids = [];
                    $quantity = [];
                    $qlistid = $id.split(".edit");
//                    $(this).attr("disabled", true);
                    $(".ocid").each(function () {
                        $ids.push($(this).val());
//                        $(this).removeAttr("class");
                    });
                    if ($_this == null) {
                        $_this = $viewid;
                    }
                    $_this = $_this.split("#tr");

                    $(".altquantity").each(function () {
                        var val = $(this).val();
                        if (val.match(/^\d+$/)) {
                            if (val != 0) {
                                $quantity.push(val);
                            } else {
                                return false;
                            }
//                            $(this).removeAttr("class");
                        } else {
                            alert("請輸入數字");
                            $quantity.length = 0;
                            return false;
                        }
                    });
//                    alert(id.length);
                    if ($ids.length == $quantity.length && $quantity.length != 0) {
                        $.ajax({
                            type: "POST",
                            url: "Test.do",
                            data: {
                                ocid: $ids,
                                quantity: $quantity,
                                qlistid: $qlistid[1]
                            },
                            dataType: "html",
                            success: function (response) {
                                alert(response);
                                $(".contentedit").attr("disabled", false);
                                $("#confirm").replaceWith($contentedit);
                                document.location = "Home?viewlistid=" + $_this[1];
                            },
                            error: function () {
                                alert("操作失敗，如問題持續請聯絡管理員");
                            }
                        });
                    } else {
                        $ids.length = 0;
                        $quantity.length = 0;
                        return false;
                    }
                });
                $(".delolf").click(function () {
                    var c = confirm("Y/N?");
                    $obj = $(this).parent().parent();
                    $afflistid = $(this).attr("id").split("affbtn");
                    if (c) {
                        $.ajax({
                            type: "POST",
                            url: "ListAfford.do",
                            data: {
                                olfid: $(this).next().val(),
                                listid: $afflistid[1]
                            },
                            dataType: "html",
                            success: function (response) {
//                                if (response == "success") {
//                                    $obj.remove();
//                                }
                                alert(response);
                                window.setTimeout('location.reload()', 0);
                            },
                            error: function () {
                                alert("操作失敗，如問題持續請聯絡管理員");
                            }
                        });
                    }
                });
                $("body").on("click", ".addafford", function () {
                    $obj = $(this).next().clone();
                    $obj.find("input[type=number],:text").val("");
                    $(this).after($obj);
                });
                $("body").on("keyup change", ".aff_unit", function () {
                    $(this).attr("name", "unit");
                    if ($(this).val() == "") {
                        $(this).removeAttr("name");
                    }
                });
                $("body").on("keyup change", ".aff_percent", function () {
                    var objs = $(this).parents(".percentform").children(".affsum");
                    var val = $(this).val() * objs.val() / 100;
                    $(this).next().val(formatFloat(val, 2));

                    $(this).attr("name", "percent");
                    if ($(this).val() == "") {
                        $(this).removeAttr("name");
                    }
                });

                $("body").on("keyup change", ".aff", function () {
                    var objs = $(this).parents(".percentform").children(".affsum");
                    var val = $(this).val() * 100 / objs.val();
                    $(this).prev().val(formatFloat(val, 2));
                    $(this).prev().attr("name", "percent");
                    if ($(this).val() == "") {
                        $(this).prev().removeAttr("name");
                    }
                });

                $(".addafford").each(function () {
                    if (typeof ($(this).attr("disabled")) != "undefined") {
                        $(this).next().children().attr("disabled", true);
                        $(this).next().next().attr("disabled", true);
                    }
                });

                $("body").on("click", ".formsubmit", function () {
                    var total = $(this).prev().val();
                    var sum = 0;
                    var b = true;
                    $(".aff_unit").each(function () {
                        if ($(this).next().val() != "" && $(this).val() == "") {
                            b = false;
                            return false;
                        }
                    });
                    $(this).parent().children(".aff-obj").each(function () {
                        sum += parseInt($(this).children(".aff_percent").val());
                    });
                    if (total < sum) {
                        alert("輸入的趴數已超過應分攤趴數，請重新輸入。");
                        return false;
                    }
//                    alert(str + "b:" + b + "flag" + $flag);
//                    alert($flag && b ? true : false);
                    return b;
                });
                $(".percentform").each(function () {
                    if ($(this).children(".affordpercent").val() != 0) {
                        $(this).find(":input").removeAttr("disabled");
                    }
                });
            });
            function formatFloat(num, pos)
            {
                var size = Math.pow(10, pos);
                return Math.round(num * size) / size;
            }
        </script>
    </head>
    <body>
        <c:if test="${sessionScope.Jobnumber == \"root\"}">
            <c:redirect url="Manage"/>
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
        <input type="hidden" id="view" value="${param.viewlistid}">
        <div id="resmsg"></div>
        <!----------------------------------------------------商品狀態區------------------------------------------------------------------------>
        <!-- Modal -->
        <div id="status" class="modal fade" role="dialog">
            <div class="modal-dialog modal-lg">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">商品狀態</h4>
                    </div>
                    <div class="modal-body pstatus">
                        <table class="table">
                            <c:choose>
                                <c:when test="${empty pmsBean.PMoqStatus}">
                                    <a href="Order">成為第一位訂購者</a>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <th>商品編號</th>
                                        <th>商品名稱</th>
                                        <th>狀態</th>
                                    </tr>
                                    <c:forEach var="pms" items="${pmsBean.PMoqStatus}">                    
                                        <tr>
                                            <c:if test="${pms.diff != null}">
                                                <td>${pms.pid}</td> 
                                                <td>${pms.product}</td> 
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${pms.diff < 0}"><font style="color:red">商品到達MOQ還差 ${pms.diff * -1} 單位</font></c:when>
                                                        <c:when test="${pms.diff >= 0}"><font style="color:green">商品已經到達MOQ</font></c:when>
                                                        <c:otherwise></c:otherwise>
                                                    </c:choose>
                                                </td>
                                            </c:if>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!---------------------------------------------------------------主要table區------------------------------------------------------------->
        <div>
            <div style="width:90%" >
                <table id="myTable1" class="table-striped table" border="1" table width="900" align="center" cellpadding="1" cellspacing="1" bordercolor="#CCCCCC">
                    <thead>
                        <tr>
                            <th style="width:200px">展開 / 收合</th>
                            <th>訂單編號</th>
                            <th>下單日期</th>
                            <th>申請人</th>
                            <th>申請原因</th>
                            <th>刪除</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!--列出屬於該使用者的訂單-->
                        <c:forEach var="ol" items="${orderlist.orderList}">
                            <c:if test="${ol.applicant == sessionScope.id}">
                                <tr class="maintable data${ol.id}" ${ol.status == 4 ?"title='訂單處理中'":""}>
                                    <td><a href="#tr${ol.id}">＋</a></td>
                                    <td><b>${ol.id}</b><input type="hidden" class="status" value="${ol.status}"></td></td>
                                    <td class="date">${ol.o_date}</td>
                                    <td>
                                        <c:forEach var="i" items="${identit.allUsers}">
                                            <c:if test="${ol.applicant == i.id}">
                                                ${i.name}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <!-- Trigger the modal with a button -->
                                        <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal${ol.id}" ${ol.status == 4 ?"disabled":""}>點我開啟</button>

                                        <!-- Modal -->
                                        <div id="myModal${ol.id}" class="modal fade mainmodel" role="dialog">
                                            <div class="modal-dialog">

                                                <!-- Modal content-->
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                        <h4 class="modal-title">訂單編號 ${ol.id} 申請內容</h4>
                                                    </div>
                                                    <div class="modal-body">
                                                        <p>${ol.memo}</p>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <input type="hidden" value="${ol.id}">
                                                        <button type="button" class="btn btn-default form submit">送出</button>
                                                        <button type="button" class="btn btn-default form dismiss">返回</button>
                                                        <button type="button" class="btn btn-default edit">編輯</button>
                                                        <button type="button" class="btn btn-default btn-close" data-dismiss="modal">Close</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <form action="DeleteData.do" method="post" id="myform">
                                            <input type="image" class="delobj" src="pages/images/delete.png" alt="Submit" width="25" height="25" ${ol.status == 4 ?"disabled":""}>
                                            <input type="hidden" value="${ol.id}">
                                        </form>
                                    </td>
                                </tr>
                                <tr class="data${ol.id}">
                                    <td colspan="5">
                                        <div id="tr${ol.id}" class="productcontent" style="display:none;"> 
                                            <table class="table-striped table" border="1" table width="732" cellpadding="1" cellspacing="1" bordercolor="#CCCCCC">
                                                <tr>
                                                    <th>商品編號</th>
                                                    <th>商品名稱</th>
                                                    <th>單位量詞</th>
                                                    <th>廠商</th>
                                                    <th>MOQ</th>
                                                    <th>商品單價</th>
                                                    <th>訂購數量</th>
                                                    <th>金額</th>   
                                                </tr>        
                                                <c:forEach var="ot" items="${ordertotal.orderTotal}">
                                                    <c:if test="${ot.listid == ol.id}">
                                                        <tr>
                                                            <th>${ot.pid}</th>
                                                            <td>${ot.product}</td>
                                                            <td>${ot.unit}</td>
                                                            <td>${ot.company}</td>   
                                                            <td>${ot.p_moq}</td>
                                                            <td>${ot.price}元</td>
                                                            <td class="edit${ol.id}"><font>${ot.l_quantity}</font><input type="hidden" value="${ot.listcid}"></td>
                                                            <td>${ot.total}元</td>   
                                                        </tr>
                                                        <c:set var="sum" value="${sum + ot.total}" />
                                                    </c:if>
                                                </c:forEach>
                                                <tr>
                                                    <td colspan="6" style="text-align: center;color: red">總金額<c:out value="${sum}"/>元</td>
                                                    <td class="editobj"><input type="button" value="修改商品數量" class="contentedit btn btn-default" id="btn${ol.id}" ${ol.status == 4 ?"disabled":""}></td>
                                                    <td>
                                                        <form action="AddProduct.do" method="post">
                                                            <input type="hidden" value="${ol.id}">
                                                            <input type="submit" class="btn btn-default" value="修改訂單內容" ${ol.status == 4 ?"disabled":""}>
                                                        </form>
                                                    </td>
                                                </tr>
                                            </table>
                                            <table class="table-striped table" border="1" table width="732" cellpadding="1" cellspacing="1" bordercolor="#CCCCCC">
                                                <thead>
                                                    <tr>
                                                        <th>動作</th>
                                                        <th>拆帳內容</th>
                                                        <th>應付金額</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="olf" items="${olfBean.orderListAfford}">
                                                        <c:if test="${olf.listid == ol.id}">
                                                            <c:set var="olfsum" value="${olfsum + olf.aff_percent}" />
                                                            <c:if test="${olfsum != 0}">
                                                                <tr>
                                                                    <td>
                                                                        <input type="button" value="刪除" class="delolf btn btn-default" id="affbtn${ol.id}" ${ol.status == 4 ?"disabled":""}>
                                                                        <input type="hidden" value="${olf.id}">
                                                                    </td>
                                                                    <td>${olf.unit} 部門分攤價錢 ${olf.aff_percent} %</td>
                                                                    <td><fmt:formatNumber value="${(sum * olf.aff_percent)/100}" pattern="#" type="number"/> 元</td>
                                                                </tr>
                                                            </c:if>
                                                        </c:if>
                                                    </c:forEach>

                                                    <tr>
                                                        <td colspan="3">
                                                            <c:choose>
                                                                <c:when test="${(100 - olfsum) <= 100}">
                                                                    <font style="color:blue">
                                                                    此筆訂單尚有 
                                                                    <fmt:formatNumber value="${100 - olfsum}" pattern="#.##" type="number"/> % |
                                                                    <fmt:formatNumber value="${sum * (100 - olfsum) / 100 }" pattern="#" type="number"/>
                                                                    元還未被處理
                                                                    </font>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    此筆訂單無設定拆帳
                                                                </c:otherwise>
                                                            </c:choose> 
                                                            <form action="ListAfford.do" method="post" class="percentform">
                                                                <input type="button" class="addafford btn btn-default" value="增加拆帳部門" disabled="disabled">
                                                                <div class="aff-obj">
                                                                    <input type="text" class="aff_unit" placeholder="請輸入部門代碼" disabled="disabled">
                                                                    <input type="number" class="aff_percent" placeholder="請輸入負擔趴數" min="0.01" step="0.01" max="100" disabled="disabled">%
                                                                    <input type="number" class="aff" placeholder="金額" min="1" step="0.01" disabled="disabled">元
                                                                </div>
                                                                <input type="hidden" value="${sum}" class="affsum">
                                                                <input type="hidden" value="${100 - olfsum}" class="affordpercent">
                                                                <input type="submit" value="確定" class="formsubmit btn btn-default" disabled="disabled">
                                                                <input type="hidden" name="listid" value="${ol.id}">
                                                                上面如不輸入請留空值
                                                            </form>
                                                        </td>
                                                    </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <c:remove var="olfsum" />
                                        <c:remove var="sum" />
                                    </td>      
                                </tr>
                            </c:if>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th></th>
                            <th>訂單編號</th>
                            <th>下單日期</th>
                            <th>申請人</th>
                            <th>申請原因</th>
                            <th></th>
                        </tr>
                    </tfoot>
                </table>
                <div id="msg"></div>
            </div>
        </div>
        <span id="table_page"></span>
        <jsp:include page="pages/footer.jsp"></jsp:include>
        <script>
            $(window).load(function () {
                $.unblockUI();
                //            $('#loading').hide(1000);
            });
        </script>
    </body>
</html>
