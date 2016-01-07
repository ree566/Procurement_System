var s = new Array();
$(document).ready(function () {
    $oneprice = 0;
    $total = 0;
//                $discount = 0;
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
            memo: {required: true} //密码1必填
        },
        messages: {
            memo: "*此欄位必填" //密码1必填
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