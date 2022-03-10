<%--
  Created by IntelliJ IDEA.
  User: gimtaeyeon
  Date: 2022/02/18
  Time: 11:54 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&display=swap" rel="stylesheet">
</head>
<style>
    p, h5 {
        font-size: small
    }

    .itemLink {
        color: #333333;
    }

    .itemLink:hover {
        color: black;
    }

    .tableContent {
        display: table;
    }

    .tableContent span {
        display: table-cell;
        vertical-align: middle;
    }

</style>

<script>
    $(function () {
        let total = 0;
        $("[name^='cartCodes']").change(function () {
            let box = this;
            let value = box.value;
            let price = $('#price_' + value).text();
            let amount = $('#amount_' + value).text();
            let post = $('#post_' + value).text();
            if (box.checked === true) {
                total += (price * amount) + (post * amount);
            } else {
                total -= (price * amount) + (post * amount);
            }
            $("#total").text(total);
        });

        $(document).on("click", "button[id^='deleteCart']", function () {
            let id = this.id;
            let str = id.split("_")[1];
            $.ajax({
                url: "/cart/" + str + "/delete",
                type: "POST",
                cache: false,
                data: {},
                success: function () {
                    location.reload();
                }
            });

        });

        $(document).on("click", "button[id^='plus']", function () {
            let id = this.id;
            let str = id.split("_")[1];
            $.ajax({
                url: "/cart/" + str + "/setAmount",
                type: "POST",
                cache: false,
                data: {"mathSign": "+"},
                success: function () {
                    location.reload();
                }
            });
        });
        $(document).on("click", "button[id^='minus']", function () {
            let id = this.id;
            let str = id.split("_")[1];
            $.ajax({
                url: "/cart/" + str + "/setAmount",
                type: "POST",
                cache: false,
                data: {"mathSign": "-"},
                success: function () {
                    location.reload();
                }
            });
        });
    });
</script>
<body class="pt-5">
<br>
<div class="container">
    <div class="row">
        <form action="/order/addForm" method="post">
            <table class="table text-center m-auto">
                <thead>
                <tr>
                    <th style="width: 8.33%">선택</th>
                    <th style="width: 30%">상품</th>
                    <th style="width: 16.66%">가격</th>
                    <th style="width: 20.33%">갯수</th>
                    <th style="width: 10.33%">주문관리</th>
                </tr>
                </thead>
                <tbody>
                <c:set var="i" value="${0}" scope="page"/>
                <c:forEach items="${carts}" var="cart">
                    <tr>
                        <td><input type="checkbox" name="cartCodes[${i}]" value="${cart.cartId}"></td>
                        <td class="tableContent text-start">
                            <a class="itemLink" href="/item/${cart.itemOption.item.itemCode}">
                                <div style="width:15%; height:100%; float:left;">
                                    <img style="width: 100%;" src="${cart.itemOption.item.itemImage}" alt="">
                                </div>
                                <div style="width:80%; height:100%; float:left;">
                                    <b style="font-size: medium">${cart.itemOption.item.itemName} <span style="color: #0f74a8;font-size: medium">(${cart.itemOption.item.country.countryName})</span></b>
                                    <p>${cart.itemOption.optionName}size</p>
                                </div>
                            </a>
                        </td>
                        <td id="price_${cart.cartId}">${cart.itemOption.item.itemPrice}</td>
                        <td>
                            <button class="btn btn-secondary btn-sm" type="button" id="minus_${cart.cartId}">-</button>
                            <span id="amount_${cart.cartId}">${cart.amount}</span>
                            <button class="btn btn-secondary btn-sm" type="button" id="plus_${cart.cartId}">+</button>
                        </td>
                        <td>
                            <button class="btn btn-sm btn-secondary" type="button" id="deleteCart_${cart.cartId}">삭제
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td style="border: none">
                        <p>배송비 : <b id="post_${cart.cartId}">${cart.itemOption.item.country.countryPostPrice}</b>원</p>
                        </td>
                    </tr>
                    <c:set var="i" value="${i+1}" scope="page"/>
                </c:forEach>
                </tbody>
            </table>
            <div>
                <span>총 결제 금액 : </span><span></span><span id="total" style="font-size: xx-large">0</span>원
                <input class="btn btn-secondary pull-right" type="submit" value="결제하기">
            </div>
        </form>
    </div>
</div>
</body>
</html>
