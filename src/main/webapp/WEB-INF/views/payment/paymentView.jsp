<%--
  Created by IntelliJ IDEA.
  User: gimtaeyeon
  Date: 2022/02/08
  Time: 10:42 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<button onclick="requestPay()">결제하기</button>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="https://cdn.iamport.kr/js/iamport.payment-1.2.0.js"></script>
<script>
    IMP.init("imp21641442");

    function requestPay() {
        // IMP.request_pay(param, callback) 결제창 호출
        IMP.request_pay({ // param
            pg: "html5_inicis",
            pay_method: "card",
            merchant_uid: 'merchant_' + new Date().getTime(),
            name: "테스트",
            amount: 100,
            buyer_email: "tae8753@gmail.com",
            buyer_name: "김태연",
            buyer_tel: "010-4025-8753",
            buyer_addr: "한국 어딘가",
            buyer_postcode: "01181"
        }, function (rsp) {
// callback
            let msg = '결제가 완료되었습니다.';
            if (rsp.success) {
                jQuery.ajax({
                    url: "/pay/getPay_sendRequest",
                    method: "POST",
                    // header : {"Content-Type" : "application/json"},
                    data: {
                        "imp_uid": rsp.imp_uid,
                        "merchant_uid": rsp.merchant_uid,
                        "paid_amount": rsp.paid_amount,
                        "apply_num": rsp.apply_num,
                    },
                    success :function (data) {
                        msg = '결제가 완료되었습니다.';
                        msg += '고유ID : ' + data.imp_uid;
                        msg += '상점 거래ID : ' + data.merchant_uid;
                        msg += '결제 금액 : ' + data.paid_amount;
                        msg += '카드 승인번호 : ' + data.apply_num;
                    },
                    statusCode: {
                        400: function () {
                            msg = '위조된 결제입니다.';
                        }
                    }

                })
                alert(msg);
            } else {
                msg = '결제에 실패하였습니다. \n';
                msg += '에러내용 : ' + rsp.error_msg;
            }
        });
    }
</script>
</body>
</html>
