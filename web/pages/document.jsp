<%-- 
    Document   : document
    Created on : 2015/7/20, 下午 04:11:44
    Author     : Wei.Cheng
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>說明文件</title>
        <style>
            img{
                width: 800px;
            }
            a:hover{

            }
            .piclink{
                color: red;
                cursor: pointer;
            }
            font{
                color: red;
            }
        </style>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                $("img").hide();
                $("a.piclink").click(function () {
                    $(this).next().show();
                    $(this).hide();
                });
                $("#openallpic").click(function () {
                    $("img").show();
                    $(".piclink").hide();
                });
            });
        </script>
    </head>
    <body>  
        <h1 id="top">採購系統使用手冊</h1>
        <ul>
            <li>
                <p>此系統某些效果不支援 IE9以下 瀏覽器。</p>
                <p><a href="http://windows.microsoft.com/zh-tw/internet-explorer/download-ie">下載最新版IE</a></p>
            </li>
        </ul>
        <hr>
        <h2>登入</h2>
        <ul>
            <li>密碼預設和工號相同。</li>
        </ul>
        <hr>
        <h2>首頁</h2>
        <b class="piclink" id="openallpic">開啟全部圖片</b>
        <ul>
            <li>登入後導向的頁面，能夠看到目前使用者正在進行中的訂單。
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/1.jpg" /></p>
            </li>
            <li>
                <p>訂單目前的狀態會依顏色來給予提示。</p>
                <div>
                    <div style="width:100px; background-color: white; border-style:solid; float: left;">&nbsp;未審核&nbsp;</div>
                    <div style="width:100px; background-color: red; border-style:solid; float: left;">&nbsp;審核失敗&nbsp;</div>
                    <div style="width:100px; background-color: green; border-style:solid; float: left;">&nbsp;已完成&nbsp;</div>
                </div>
                <div style="clear:both;"></div>
            </li>
            <li>
                使用者自訂單申請期間(尚未轉為已完成狀態)時，仍可對訂單做<font>修改申請原因</font>以及<font>刪除訂單</font>功能。
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/2.jpg" /></p>
            </li>
            <li>
                選擇訂單左側的展開收合會顯示該筆訂單的商品內容。
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/3.png" /></p>
            </li>
            <li>點選網站標題可返回進行中的訂單列表。
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/13.jpg" /></p>
            </li>
        </ul>
        <hr>
        <h2>商品訂購</h2>
        <ul>
            <li>
                選擇下拉式選單可以看到所有能訂購商品以及不能訂購商品，以及商品的量詞單位和最低moq。
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/4.png" /></p>
            </li>
            <li>
                選擇完您要的商品數量後，按下+鍵把商品放入以選擇商品欄位，點兩次可取消該商品。
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/5.png" /></p>
            </li>
            <li>
                <font>※申請事由是必填欄位，請務必確實填寫。若事後要修改可在首頁中點選訂單申請原因，再彈出視窗做編輯。</font>
            </li>
        </ul>
        <hr>
        <h2>歷史訂單查詢</h2>
        <p><a class="piclink">點我開啟圖片</a>
            <img src="pages/images/pic/6.png" />
        <ul>
            <li>
                此處儲存已經通過審核的訂單，可用右上方搜尋框座資料的搜尋。(訂單日期...等相關資訊)
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/10.png" /></p>
            </li>
            <li>
                審核通過使用者會先收到如下信件。
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/9.png" /></p>
            </li>
        </ul>
        <hr>
        <h2>商品狀態查詢</h2>
        <ul>
            <li>
                此處供使用者以及管理者查看目前商品的訂購狀態。
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/7.png" /></p>
            </li>
        </ul>
        <hr>
        以下功能為管理者所使用，一般使用者無法進入
        <h2>管理者能夠使用的功能</h2>
        <ul>
            <li>
                訂單狀態變更
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/8.png" /></p>
            </li>
            <li>
                所有商品狀態變更
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/11.png" /></p>
                <ol>
                    <li>勾選要刪除的商品之後選擇刪除可刪除商品。</li>
                    <li>編輯鈕可針對單一商品作細項更動。</li>
                    <li>鎖定商品可對商品進行鎖定。</li>
                    <li><font>※若鎖定後的商品不幸還是被訂購完成，可以使用退簽鈕。</font></li>
                </ol>
            </li>
            <li>
                商品退簽
                <p><a class="piclink">點我開啟圖片</a>
                    <img src="pages/images/pic/12.png" /></p>
                <ol>
                    <li>身分為管理人員時，商品狀態會多一欄退簽功能。</li>
                    <li>輸入想要退簽的商品編號，多數時請以逗號隔開。</li>
                    <li>輸入退簽理由。</li>
                    <li>完成後會寄信給訂單擁有者，並將商品從訂單內刪除。(歷史訂單不受影響)</li>
                    <li><font>※兩個欄位為必填，此動作為不可逆。</font></li>
                </ol>
            </li>
        </ul>
        <a href="#top" style="font-size: 36px">top</a> / <a href="javascript:history.back()" style="font-size: 36px">back</a>
    </body>
</html>
