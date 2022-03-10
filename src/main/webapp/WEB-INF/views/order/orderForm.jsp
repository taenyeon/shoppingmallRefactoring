<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
    <title>Title</title>
    <script type="text/javascript">
        $(document).ready(function (e) {
            $("[id='getMemberInfo']").change(function () {
                if ($("[id='getMemberInfo']").is(":checked")) {
                    $.ajax({
                        url: "/members/" + "${sessionScope.member.memberId}",
                        method: "GET",
                        success: function (data) {
                            let buyerName = data.memberName;
                            let buyerAddr = data.memberAddress;
                            let buyerDetailAddr = data.memberDetailAddress;
                            let buyerTel = data.memberTel;
                            let buyerEmail = data.memberEmail;

                            $("#buyerName").val(buyerName);
                            $("#buyerAddr").val(buyerAddr +" "+ buyerDetailAddr);
                            $("#buyerTel").val(buyerTel);
                            $("#buyerEmail").val(buyerEmail);
                        },
                        error: function () {
                            alert("회원 정보를 가져오는데 실패하였습니다.")
                        }
                    });
                } else {
                    $("#buyerName").val("");
                    $("#buyerAddr").val("");
                    $("#buyerTel").val("");
                }
            });

            // IamPort 결제 모듈
            IMP.init("imp21641442");

            $(document).on("click", "button[id='request_pay']", function () {
                let order = addOrder();
                // IMP.request_pay(param, callback) 결제창 호출
                IMP.request_pay({ // param
                    pg: "html5_inicis",
                    pay_method: "card",
                    merchant_uid: 'merchant_' + new Date().getTime(),
                    name: "test",
                    amount: order.totalPay,
                    buyer_email: order.buyerEmail,
                    buyer_name: order.buyerName,
                    buyer_tel: order.buyerTel,
                    buyer_addr: order.buyerAddr,
                    buyer_postcode: order.buyerPostCode
                }, function (rsp) {
// callback
                    let msg="";
                    if (rsp.success) {
                        jQuery.ajax({
                            url: "/order/" + order.orderCode + "/validate",
                            method: "POST",
                            // header : {"Content-Type" : "application/json"},
                            data: {
                                "imp_uid": rsp.imp_uid,
                                "orderCode": order.orderCode
                            },
                            statusCode: {
                                200: function () {
                                    alert('결제에 성공하였습니다.')
                                    location.href = "/order/myOrder";
                                },
                                402: function () {
                                    alert('위조된 결제로 인해 실패하였습니다.')
                                }
                            }

                        })
                    } else {
                        $.ajax({
                            url: "/order/" + order.orderCode + "/cancel",
                            method: "POST",
                            success: function () {
                                msg += "다시 주문 해주세요. \n";
                            }
                        });
                        msg += '결제에 실패하였습니다. \n';
                        msg += '에러내용 : ' + rsp.error_msg;
                        alert(msg)
                    }
                });
            });

            function addOrder() {
                let form = $("#form")[0];
                let formData = new FormData(form);
                let returnData;
                $.ajax({
                    url: "/order/add",
                    method: "POST",
                    data: formData,
                    async: false,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        returnData = data;
                    }
                });
                return returnData;
            }
        });
    </script>
</head>
<body >
<div class="container">
    <h4>주문 정보</h4>
    <div class="text-end">
        <label for="getMemberInfo">아이디 정보 불러오기</label>
        <input type="checkbox" name="getMemberInfo" id="getMemberInfo">
    </div>
    <form action="/order/insertOrder" method="post" id="form">
        <div class="mb-3">
            <label for="buyerName">받는 이</label>
            <input class="form-control" type="text" name="buyerName" id="buyerName" placeholder="받는 이 이름을 입력해주세요."
                   required>
        </div>
        <div class="mb-3">
            <label for="buyerTel">전화번호</label>
            <input class="form-control" type="tel" name="buyerTel" id="buyerTel" placeholder="전화번호를 입력해주세요." required>
        </div>
        <div class="mb-3">
            <label for="buyerEmail">이메일</label>
            <input class="form-control" type="email" name="buyerEmail" id="buyerEmail" placeholder="이메일을 입력해주세요."
                   required>
        </div>
        <div class="mb-3">
            <label for="buyerPostCode">주소 번호</label>
            <input class="form-control" type="text" name="buyerPostCode" id="buyerPostCode" placeholder="주소 번호를 입력해주세요."
                   required>
        </div>
        <div class="mb-3">
            <%--  api 사용 필요    --%>
            <label for="buyerAddr">주소</label>
            <input class="form-control" type="text" name="buyerAddr" id="buyerAddr" placeholder="주소를 입력해주세요." required>
        </div>

        <table class="table text-center">
            <thead>
            <tr>
                <th style="width: 50%">상품</th>
                <th style="width: 16.66%">가격</th>
                <th style="width: 8.33%">갯수</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="i" value="0" scope="page"/>
            <c:forEach items="${carts}" var="cart">
                <c:set var="total" value="${total+(cart.itemOption.item.itemPrice * cart.amount) + (cart.itemOption.item.country.countryPostPrice * cart.amount)}" scope="page"/>
                <tr>
                    <td class="content text-start">
                        <input type="hidden" name="cartCodes[${pageScope.i}]" value="${cart.cartId}">
                        <div style="width:10%; height:100%; float:left;">
                            <img style="width: 100%;" src="${cart.itemOption.item.itemImage}" alt="">
                        </div>
                        <div style="width:90%; height:100%; float:left;">
                            <b style="font-size: medium" class="itemName">${cart.itemOption.item.itemName} <span style="color: #0f74a8">(${cart.itemOption.item.country.countryName})</span></b>
                            <p>${cart.itemOption.optionName}size</p>
                        </div>
                    </td>
                    <td id="price_${cart.cartId}">${cart.itemOption.item.itemPrice}</td>
                    <td id="amount_${cart.cartId}">${cart.amount}</td>
                </tr>
                <c:set var="i" value="${i+1}" scope="page"/>
            <tr>
                <td style="border: none; text-align: left">
                    <p>배송비 : <b id="post_${cart.cartId}">${cart.itemOption.item.country.countryPostPrice}</b>원</p>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
        <span>총 결제 금액 : </span><span id="total" style="font-size: xx-large">
        ${pageScope.total}
    </span>원
    </form>
    <button class="btn btn-secondary btn-sm" id="request_pay">결제하기</button>
</div>
</body>
</html>
