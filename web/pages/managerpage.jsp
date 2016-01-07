<%-- 
    Document   : managerpage
    Created on : 2015/8/28, 上午 09:05:41
    Author     : Wei.Cheng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="ordertotal" class="com.ps.bean.OrderTotalBean" scope="application" />
<jsp:useBean id="orderlist" class="com.ps.bean.OrderListBean" scope="application" />
<jsp:useBean id="identit" class="com.ps.bean.IdentitBean" scope="application" />
<jsp:useBean id="pmsBean" class="com.ps.bean.PMoqStatusBean" scope="application" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>採購系統</title>
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
                /*background: #777;*/
            }
            #table_page{
                clear: both;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="js/jquery.blockUI.js"></script>
        <script src="js/jquery-tablepage-1.0.js"></script>
        <script src="js/jquery.validate_1.js"></script> 
        <script>
            $(function () {
                //+展開 -收合
                $(".maintable a").click(function () {
                    var _this = $(this).attr("href");
                    if ($(_this).css("display") == "none") {
                        $(_this).slideDown();
                        $(this).html("－");
                    } else {
                        $(_this).slideUp();
                        $(this).html("＋");
                    }
                    return false;
                });
            });
            $(document).ready(function () {
                $(":button,:submit").addClass("btn btn-default");
                $("select,:text").addClass("form-control");
                $("#myTable1").tablepage($("#table_page"), 10);
                //顯示使用者的觀看紀錄
                $viewid = "#tr" + $("#view").val();
//                alert($viewid);
                $($viewid).show();
                function change(stat, listid, mail) {
                    $.ajax({
                        type: "POST",
                        url: "Change.do",
                        data: {
                            dataString: stat,
                            id: listid,
                            mail: mail
                        },
                        dataType: "html",
                        success: function (response) {
                            alert("訂單狀態已變更");
//                            if (stat != 4) {
                            document.location = "Manage?viewlistid=" + listid;
//                            }
                        }
                    });
                }
                $(".status").each(function () {
                    var string = "";
                    switch ($(this).val()) {
                        case "1":
                            string = "success";
                            break;
                        case "2":
                            string = "danger";
                            break;
                        case "3":
                            string = "info";
                            break;
                        case "4":
//                            $(this).parent().parent().addClass("block");
//                                    .block({message: "訂單鎖定中"});
                            break;
                    }
                    $(this).parent().parent().addClass(string);
                });
                if ($(".maintable").children().length == 0) {
                    //找尋tr class = maintable 底下是否有 children
                    $("#msg").html("查無資料，尚未有任何訂單。");
                    $("tfoot").detach();
//                        alert($(".maintable").children().length);
                }
                $(".status").each(function () {//依照訂單狀態修改CSS  1完成2錯誤3已讀4鎖定
                    var string = "";
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
//                            $(this).parent().parent().addClass("block");
//                                    .block({message: "訂單鎖定中"});
                            string = "info";
                            break;
                    }
                    $(this).parent().parent().addClass(string);
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
                $(".dismiss").click(function () {
                    dismiss();
                });
                $(".mainmodel").on('hidden.bs.modal', function () {
                    dismiss();
                });
                $(".liststatus").change(function () {
//                    alert($(this).next().val());
                    var stat = $(this).val();
                    var listid = $(this).attr("id");
                    var mail = $(this).next().val();
                    if ($(this).val() == -1) {
                        return false;
                    } else {
                        var c = confirm("確定?");
                        if (c) {
                            change(stat, listid, mail);
                            $.blockUI({message: '<img src="pages/images/loading_spinner.gif" height="42" />', fadeIn: 0});
                        }
                    }
                });
                $("input[type='submit']").click(function () {
                    $(this).prev().attr("name", "id");
                });
                $("#cause").change(function () {
                    if ($(this).val() != 0) {
                        $(this).prev().val($("#cause option:selected").text());
                    }
                });//退簽原因範本控制
                $("body").on("click", "#lock", function () {
                    change(5, -1, "");
                    $(".maintable").addClass("info");
                });
                $("body").on("click", "#unlock", function () {
                    change(0, -1, "");
                    $(".maintable").removeClass("info");
                });
            });

            jQuery.validator.addMethod("regex", //addMethod第1个参数:方法名称
                    function (value, element, params) {     //addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
                        var exp = new RegExp(params);     //实例化正则对象，参数为传入的正则表达式
                        return exp.test(value);                    //测试是否匹配
                    }, "格式錯誤");    //addMethod第3个参数:默认错误信息
            var rule = {required: true, regex: "^[0-9,]+$"};
            var msg = {required: "必须填寫", regex: "格式錯誤"};
            $(function () {
                $("#myform1").validate({
                    rules: {
                        product: {
                            required: true,
                            regex: "^[0-9,]+$"
                        }, //密码1必填
                        cause: {required: true}
                    },
                    messages: {
                        product: {required: "必须填寫", regex: "格式錯誤"},
                        cause: {required: "必须填寫"}
                    },
                    errorPlacement: function (error, element) {                             //错误信息位置设置方法  
                        error.appendTo($("#myform1errormsg"));                            //这里的element是录入数据的对象  
                    }
                    //                    debug: true, //如果修改为true则表单不会提交
                    //                    submitHandler: function () {
                    //                        alert("开始提交了");
                    //                    }
                });
            });
        </script>
    </head>
    <body>
        <c:if test="${sessionScope.Jobnumber != \"root\"}">
            <c:redirect url=""/>
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
        <div id="lockgroup"><input type="button" id="lock" value="lock"><input type="button" id="unlock" value="unlock"></div>
        <div id="resmsg"></div>
        <!---------------------------------------------------------------------------------------------------------------------------->
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
                        <form id="myform1" action="DeleteProduct.do" method="post">
                            <!---->
                            <div id="floating" class="form-inline">  
                                商品退簽:
                                <input type="text" placeholder="多數以逗號隔開(編號)" name="product">
                                <input type="text" placeholder="請輸入原因" name="cause">
                                <select id="cause" style="text-align:center">
                                    <option value="0">---退簽原因範本---</option>
                                    <option value="1">商品未達最低MOQ</option>
                                    <option value="2">廠商協談未果</option>
                                </select>
                                <input type="submit" id="send" value="send">
                                <div id="myform1errormsg" style="color:red"></div>
                            </div>
                        </form>  
                    </div>
                    <div class="modal-footer">
                        <button type="button" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <div id="main">
            <div style="width:95%">
                <form action="DeleteData.do" method="post" id="myform">
                    <table id="myTable1" class="table-striped table" border="1" table width="900" align="center" cellpadding="1" cellspacing="1" bordercolor="#CCCCCC">
                        <thead>
                            <tr>
                                <th>展開 / 收合</th>
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
                                <tr class="maintable data${ol.id}">
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
                                        <button type="button" data-toggle="modal" data-target="#myModal${ol.id}">點我開啟</button>

                                        <!-- Modal -->
                                        <div id="myModal${ol.id}" class="modal fade" role="dialog">
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
                                                        <button type="button" data-dismiss="modal">Close</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <td><input type="image" class="delobj" src="pages/images/delete.png" alt="Submit" width="25" height="25"><input type="hidden" value="${ol.id}"></td>
                                </tr>
                                <tr>
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
                                                            <td>${ot.l_quantity}</td>
                                                            <td>${ot.total}元</td>    
                                                        </tr>
                                                        <c:set var="sum" value="${sum + ot.total}" />
                                                        <c:set var="mail" value="${ot.aMail}" />
                                                    </c:if>
                                                </c:forEach>
                                                <tr>
                                                    <td colspan="7" style="text-align: center;color: red">總金額<c:out value="${sum}"/>元</td>
                                                    <td>
                                                        <select id="${ol.id}" class="liststatus">
                                                            <option value="-1">請選擇狀態</option>
                                                            <option value="0">正常</option>
                                                            <option value="1">完成</option>
                                                            <option value="2">失敗</option>
                                                            <option value="4">鎖定</option>
                                                        </select>
                                                        <input type="hidden" class="usermail" value="${mail}">
                                                    </td>
                                                </tr>
                                                <c:remove var="sum" />
                                                <c:forEach var="olf" items="${olfBean.orderListAfford}">
                                                    <c:if test="${olf.listid == ol.id}">
                                                        <c:set var="olfsum" value="${olfsum + olf.aff_percent}" />
                                                        <c:if test="${olfsum != 0}">
                                                            <tr><td>${olf.unit} 部門分攤價錢 ${olf.aff_percent} %</td></tr>
                                                        </c:if>
                                                    </c:if>
                                                </c:forEach>

                                                <tr>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${(100 - olfsum) != 100}">
                                                                <font style="color:blue">此筆訂單尚有 ${100 - olfsum} % 價錢還未被處理</font>
                                                            </c:when>
                                                            <c:otherwise>
                                                                此筆訂單無設定拆帳
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <c:remove var="olfsum" />
                                                    </td>
                                                    <td>
                                                        <a href="mailto:${mail}">我的聯絡方式</a><c:remove var="mail" />
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td> 
                                </tr>
                            </c:forEach>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th></th>
                                <th>訂單編號</th>
                                <th>下單日期</th>
                                <th>申請人</th>
                                <th>申請原因</th>
                                <th>刪除</th>
                            </tr>
                        </tfoot>
                    </table>
                    <div id="msg"></div>
                </form>
            </div>
        </div>
        <span id="table_page"></span>
        <jsp:include page="pages/footer.jsp"></jsp:include>
        <script>
            $(window).load(function () {
                $.unblockUI();
                //        $('#loading').hide(1000);
            });
        </script>
    </body>
</html>
