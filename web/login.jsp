<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>採購系統</title>
        <style>
            fieldset {
                width: 400px;
                margin-top:150px;
            }

            fieldset legend {
                border: 1px solid;
                padding: 5px 5px;
            }

            img {
                position: relative;
                width: 200px;
                float: right;
                bottom: 0px;
            }

            table {
                float: left;
            }
            .error{
                color: red;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="js/jquery.validate_1.js"></script> 
        <script>
            var isCommitted = false;//表单是否已经提交标识，默认为false
            function dosubmit() {
                if (!isCommitted) {
                    isCommitted = true;//提交表单后，将表单是否已经提交标识设置为true
                    return true;//返回true让表单正常提交
                } else {
                    return false;//返回false那么表单将不提交
                }
            }
            jQuery.validator.addMethod("regex", //addMethod第1个参数:方法名称
                    function (value, element, params) {     //addMethod第2个参数:验证方法，参数（被验证元素的值，被验证元素，参数）
                        var exp = new RegExp(params);     //实例化正则对象，参数为传入的正则表达式
                        return exp.test(value);                    //测试是否匹配
                    }, "格式錯誤");    //addMethod第3个参数:默认错误信息
            var rule = {required: true, regex: "^[0-9a-zA-Z-]+$"};
            var msg = {required: "必须填寫", regex: "格式錯誤"};
            $(function () {
                $("#login").validate({
                    rules: {
                        username: rule, //密码1必填
                        password: rule
                    },
                    messages: {
                        username: msg,
                        password: msg
                    },
                    errorPlacement: function (error, element) {                             //错误信息位置设置方法  
                        error.appendTo(element.parent().next());                            //这里的element是录入数据的对象  
                    }
                });
            });
        </script>
    </head>

    <body>
        <c:if test="${sessionScope.Jobnumber != null}">
            <c:redirect url="Home"/>
        </c:if>
        <center>
            <fieldset>
                <legend>採購系統登入:</legend>
                <form action="Login.do" method="post" onsubmit="dosubmit()" id="login">
                    <table>
                        <tr>
                            <td>Name:</td>
                            <td><input type="text" name="username" maxlength="10" autocomplete="off"></td>
                            <td></td>
                        </tr>
                            <td>Password:</td>
                            <td><input type="password" name="password" maxlength="10"></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td><input type="submit" id="submit" value="提交" /><input type="reset" value="取消"/></td>
                            <td></td> 
                        </tr>
                    </table>
                    <img src="logo.jpg" />
                    <div id="errormsg" style="color:red;float:left">
                        <c:out value="${errormsg}" default=""></c:out>
                    </div>
                </form>
            </fieldset>
        </center>
    </body>
</html>
