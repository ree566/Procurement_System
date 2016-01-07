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
    $("#myTable1").tablepage($("#table_page"), 10);
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
    $("#show").click(function () {
        $(".productcontent").slideToggle();
    });
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
        }
        $(this).parent().parent().attr("class", string);
    });
    $(".modal-body").each(function () {
        $(this).html($(this).html().replace(/\r\n|\r|\n/g, "<br>"));
    });
    $("input[type='image']").click(function () {
        $(this).next().attr("name", "deleteid");
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
    function dismiss() {
        $(".form").hide();
        $(".edit").show();
        $word = $word.replace(/\r\n|\r|\n/g, "<br>");
        $("textarea").replaceWith("<p>" + $word + "</p>");
    }
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
    $(".liststatus").change(function () {
//                    alert($(this).next().val());
        if ($(this).val() == -1) {
            return false;
        } else {
            var c = confirm("確定?");
            if (c) {
                $.blockUI({message: '<img src="pages/images/loading_spinner.gif" height="42" />', fadeIn: 0});
                $.ajax({
                    type: "POST",
                    url: "Change.do",
                    data: {
                        dataString: $(this).val(),
                        id: $(this).attr("id"),
                        mail: $(this).next().val()
                    },
                    dataType: "html",
                    success: function (response) {
//                                alert(response);
                        window.setTimeout('location.reload()', 0);
                    }
                });
            }
        }
    });
    $("input[type='submit']").click(function () {
        $(this).prev().attr("name", "id");
    });
    //---------------------------------------------------------------
    $("#send").click(function () {
        $product = $(this).prev().prev().val();
        $cause = $(this).prev().val();
        var reg = /[^0-9,]/g;
        if ($product == "" || $cause == "" || $product.match(reg) || $product.replace(/,/g, "") == "") {
            alert("please insert the correct type");
        } else {
            if (confirm("確定?(確認之後無法返回)")) {
                $.ajax({
                    type: "POST",
                    url: "DeleteProduct.do",
                    data: {
                        product: $product,
                        cause: $cause
                    },
                    dataType: "html",
                    success: function (response) {
//                                    alert(response);
                        alert(response);
                        window.setTimeout('location.reload()', 0);
                    },
                    error: function () {
                        alert("操作失敗，如問題持續請聯絡管理員");
                    }
                });
            }
        }
    });
    $("#clear").click(function () {
        $("#resmsg").html("");
    });
    //---------------------------------------------------------------
});