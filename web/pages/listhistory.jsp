<%-- 
    Document   : index
    Created on : 2015/7/23, 上午 09:33:56
    Author     : Wei.Cheng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<jsp:useBean id="orderlist_history" class="com.ps.bean.Orderlist_History_ViewBean" scope="application" />
<jsp:useBean id="orderlist_afford" class="com.ps.bean.OrderListAffordBean" scope="application" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>採購系統</title>
        <link rel="stylesheet" href="style/jquery.dataTables.css">
        <style>
            div.tooltip-inner {
                max-width: 350px;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <!--<script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>-->
        <script src="js/jquery-tablepage-1.0.js"></script>
        <!--<script src="js/sorttable.js"></script>-->
        <script src="js/jquery.dataTables.js"></script>
        <script src="js/jquery.blockUI.js"></script>
        <script>
            $(document).ready(function () {
                $(".progress-bar").each(function () {
                    $(this).css("backgroundColor", '#' + ('00000' + (Math.random() * 0x1000000 << 0).toString(16)).slice(-6));
                });
                $('#myTable').DataTable({
                    "columns": [, , , , {
                            "className": 'details-control',
                            "orderable": false
                        }, {
                            "className": 'details-control',
                            "orderable": false
                        }, {
                            "className": 'details-control',
                            "orderable": false
                        }],
                    "order": [[1, 'desc']]
                });
                $("#myTable").tablepage($("#table_page"), 10);
                $('.tt').tooltip({html: true});
                $("#st").remove();
                if ($("#userid").val() == null) {
                    $("table").hide();
                }
//                $(".progress div").removeAttr("style");
            });
        </script>
    </head>
    <body>
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
        <input type="hidden" id="userid" value="${sessionScope.id}">
        <h1>已完結訂單</h1>
        <div>
            <div style="width:90%">
                <table id="myTable" class="table-striped table display" border="1" table width="100%" align="center" cellpadding="1" cellspacing="1" bordercolor="#CCCCCC">
                    <!--表格有更動時記得改上方DataTable的設定-->
                    <thead>
                        <tr id="title">
                            <th>訂單編號</th>
                            <th>訂單日期</th>
                            <th>申請人</th>
                            <th>所屬單位</th>
                            <th bSortable="false">訂單內容</th>
                            <th bSortable="false">申請原因</th>
                            <th>單位負擔</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="olnum" items="${orderlist_history.getOrderlist_HNumber(sessionScope.id)}">
                            <tr class="success">
                                <td>${olnum.id}</td>
                                <td class="month">${olnum.o_date}</td>
                                <td>${olnum.name}</td>
                                <td>${olnum.aUnit}</td>
                                <td>
                                    <!---->
                                    <!-- Trigger the modal with a button -->
                                    <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal${olnum.id}">點我開啟</button>

                                    <!-- Modal -->
                                    <div id="myModal${olnum.id}" class="modal fade" role="dialog">
                                        <div class="modal-dialog modal-lg">

                                            <!-- Modal content-->
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                    <h4 class="modal-title">訂單編號 ${olnum.id} 商品列表</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <table id="childtable" class="table-striped table" border="1" table width="732" cellpadding="1" cellspacing="1" bordercolor="#CCCCCC">
                                                        <tr>
                                                            <th>商品名稱</th>
                                                            <th>廠商</th>
                                                            <th>訂購數量</th>
                                                            <th>商品單價</th>
                                                            <th>金額</th>
                                                        </tr>
                                                        <c:forEach var="ohv" items="${orderlist_history.orderlist_History_View}">
                                                            <c:if test="${ohv.id == olnum.id}">
                                                                <tr>
                                                                    <td>${ohv.product}</td>
                                                                    <td>${ohv.company}</td>
                                                                    <td>${ohv.quantity}</td>
                                                                    <td>${ohv.price}元</td>
                                                                    <td>${ohv.total}元</td>
                                                                </tr>
                                                                <c:set var="sum" value="${sum + ohv.total}" />
                                                            </c:if>
                                                        </c:forEach>
                                                        <tr>
                                                            <td colspan="5" style="text-align: center;color: red">總金額<c:out value="${sum}"/>元</td>
                                                        </tr>
                                                    </table>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!---->
                                </td>
                                <!--detach-->
                                <td>
                                    <!--Trigger the modal with a button -->
                                    <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#contentModal${olnum.id}">點我開啟</button>

                                    <!-- Modal -->
                                    <div id="contentModal${olnum.id}" class="modal fade mainmodel" role="dialog">
                                        <div class="modal-dialog">

                                            <!-- Modal content-->
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                    <h4 class="modal-title">訂單編號 ${olnum.id} 申請原因</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <p>${olnum.memo}</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-default btn-close" data-dismiss="modal">Close</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div class="progress">
                                        <c:forEach var="olf" items="${orderlist_afford.orderListAffordLog}">
                                            <div class="tt" data-toggle="tooltip"  data-placement="bottom" title="
                                                 <c:if test="${olf.listid == olnum.id}">
                                                     ${olf.unit}負擔比例${olf.aff_percent}%，價錢:${(olf.aff_percent*sum)/100}元
                                                 </c:if>
                                                 "></div>
                                        </c:forEach>
                                    </div>
                                </td>
                            </tr>
                            <c:remove var="sum" />
                        </c:forEach>    
                    </tbody>
                </table>
            </div>
        </div>
        <jsp:include page="pages/footer.jsp"></jsp:include>
        <script>
            $(window).load(function () {
                $.unblockUI();
                //            $('#loading').hide(1000);
            });
        </script>
    </body>
</html>
