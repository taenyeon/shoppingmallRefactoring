<%--
  Created by IntelliJ IDEA.
  User: gimtaeyeon
  Date: 2022/02/21
  Time: 11:53 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
    <script>
        $(function () {
            $(document).on("click", "[id^='status']", function () {
                let attr = $(this).attr("id");
                let selectedCode = attr.split("_")[1];
                let val = $("#selectStatus_" + selectedCode).val();
                $.ajax({
                    url: "/orderDetail/" + selectedCode + "/update",
                    method: "POST",
                    data: {
                        "postedStatus": val
                    },
                    statusCode: {
                        200: function () {
                            alert("주문상태 변경에 성공하였습니다.")
                            location.reload();
                        },
                        400: function () {
                            alert("주문상태 변경에 실패하였습니다.\n주문자가 환불했는지 확인해주세요.")
                        }
                    }
                })
            });

        })
    </script>
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
<body class="pt-5">
<div class="container">
    <form action="/shop/${shopName}/order" method="GET">
        <div class="row">
            <div class="col-2 text-center"><h4>검색하기</h4></div>
            <div class="col-2">
                <select class="form-select input-sm" name="type" id="type" required>
                    <option value="name">이름</option>
                    <option value="postedStatus">상태</option>
                </select>
            </div>
            <div id="custom-search-input" class="col-5">
                <div class="input-group col-md-4">
                    <input type="text" name="q" class="search-query form-control rounded-pill"
                           placeholder="Search"/>
                    <span class="input-group-btn">
                    <button class="btn btn-danger" type="submit"><i class="fas fa-search"></i></button>
                </span>
                </div>
            </div>
        </div>
    </form>
    <br>
    <div class="accordion" id="accordionExample">
        <c:set var="i" value="${0}"/>
        <c:forEach items="${orderDetails}" var="orderDetail">
            <div class="accordion-item">
                <h2 class="accordion-header" id="heading${i}">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                            data-bs-target="#collapse${i}" aria-expanded="true" aria-controls="collapse${i}">
                        <div style="width: 8.33%">${orderDetail.order.orderCode}</div>
                        <div class="tableContent text-start" style="width: 30%; margin-left: 50px">
                            <div style="width:10%; height:100%; float:left;">
                                <img style="width: 100%;" src="${orderDetail.itemOption.item.itemImage}"
                                     alt="">
                            </div>
                            <div style="width:90%; height:100%; float:left;">
                                <b style="font-size: medium">${orderDetail.itemOption.item.itemName}</b>
                                <p>${orderDetail.itemOption.optionName}size</p>
                            </div>
                        </div>
                        <div style="width: 16.66%">${orderDetail.itemOption.item.itemPrice}원</div>
                        <div style="width: 8.33%">
                            <span>${orderDetail.amount}개</span>
                        </div>
                        <div style="width: 8.33%">
                                ${orderDetail.postedStatus}
                        </div>
                    </button>
                </h2>
                <div id="collapse${i}" class="accordion-collapse collapse" aria-labelledby="heading${i}"
                     data-bs-parent="#accordionExample">
                    <div class="accordion-body">
                        <div class="row">
                            <div class="col-6">
                                <table class="table text-center">
                                    <tbody>
                                    <tr>
                                        <th>결제번호</th>
                                        <td>${orderDetail.order.impUid}</td>
                                    </tr>
                                    <tr>
                                        <th>결제일</th>
                                        <td>${orderDetail.order.paidAtToString()}</td>
                                    </tr>
                                    <tr>
                                        <th>결제금액</th>
                                        <td>${orderDetail.order.totalPay}원</td>
                                    </tr>
                                    <tr>
                                        <th>환불금액</th>
                                        <td>${orderDetail.order.change}원</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="col-6">
                                <table class="table text-center">
                                    <tbody>
                                    <tr>
                                        <th>이름</th>
                                        <td>${orderDetail.order.buyerName}</td>
                                    </tr>
                                    <tr>
                                        <th>연락처</th>
                                        <td>${orderDetail.order.buyerTel}</td>
                                    </tr>
                                    <tr>
                                        <th>배송주소</th>
                                        <td>${orderDetail.order.buyerAddr}</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <c:if test="${orderDetail.postedStatus != 'Refund' && orderDetail.postedStatus != 'Review'}">
                            <select id="selectStatus_${orderDetail.orderDetailCode}">
                                <option value='Refund'>환불</option>
                                <option value='Ready'>배송준비중</option>
                                <option value='Posted'>배송중</option>
                                <option value='Done'>배송완료</option>
                            </select>
                            <button id="status_${orderDetail.orderDetailCode}"
                                    class="btn btn-sm btn-secondary"
                                    type="button">주문상태 변경
                            </button>
                        </c:if>
                    </div>
                </div>
            </div>
            <c:set var="i" value="${i+1}"/>
        </c:forEach>
    </div>
    <br>
</div>
</body>
</html>
