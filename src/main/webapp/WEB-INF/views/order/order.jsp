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
            $(document).on("click", "[name='refund']", function () {
                let orderDetailCode = $(this).attr('id');
                fnModuleInfo(orderDetailCode);
            });

            function fnModuleInfo(orderDetailCode) {
                    $.ajax({
                        url : "/orderDetail/"+orderDetailCode+"/cancel",
                        method : "POST",
                        success : function (){
                            alert("환불이 정상처리 되었습니다.")
                            location.href = "/order/myOrder"
                        }
                    });
            }


            function Rating() {
            }
            Rating.prototype.rate = 0;
            Rating.prototype.setRate = function (newrate) {
                //별점 마킹 - 클릭한 별 이하 모든 별 체크 처리
                this.rate = newrate;
                let items = document.querySelectorAll('.star-sel');
                items.forEach(function (item, idx) {
                    if (idx < newrate) {
                        item.checked = true;
                    } else {
                        item.checked = false;
                    }
                });
            }
            let rating = new Rating();//별점 인스턴스 생성
            //별점선택 이벤트 리스너
            document.querySelector('.star-box').addEventListener('click',
                function (e) {
                    let elem = e.target;
                    if (elem.classList.contains('star-sel')) {
                        rating.setRate(parseInt(elem.value));
                        $("#reviewStar").val(parseInt(elem.value))
                    }
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

    .review-list li {
        list-style: none;
        padding: 20px;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    .review-list li:nth-child(2n) {
        background: #eee;
    }

    .review-list li dl dt .re-id {
        font-size: 15px;
    }

    .review-list li dl dt .re-model {
        margin-top: 8px;
        display: flex;
        align-items: center;
    }

    .review-list li dl dt .re-model img {
        width: 60px;
        margin-right: 10px;
    }

    .review-list li dl dt .star-box {
        padding: 0;
        margin: 0;
    }

    .star-box .star-sel {
        position: relative;
        display: inline-block;
        z-index: 20;
        opacity: 0.001;
        width: 30px;
        height: 30px;
        background-color: #fff;
        cursor: pointer;
        vertical-align: top;
        display: none;
    }

    .star-box .star-sel + label {
        position: relative;
        display: inline-block;
        margin-left: -4px;
        z-index: 10;
        width: 30px;
        height: 30px;
        background-image: url("data:image/svg+xml,%3C%3Fxml version='1.0' %3F%3E%3C!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' 'http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd'%3E%3Csvg style='enable-background:new 0 0 512 512;' version='1.1' viewBox='0 0 512 512' width='30px' height='30px' xml:space='preserve' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Cpath d='M480,207H308.6L256,47.9L203.4,207H32l140.2,97.9L117.6,464L256,365.4L394.4,464l-54.7-159.1L480,207z M362.6,421.2 l-106.6-76l-106.6,76L192,298.7L84,224h131l41-123.3L297,224h131l-108,74.6L362.6,421.2z'/%3E%3C/svg%3E");
        cursor: pointer;
    }

    .star-box .star-sel:checked + label {
        background-image: url("data:image/svg+xml,%3C%3Fxml version='1.0' %3F%3E%3C!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' 'http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd'%3E%3Csvg style='enable-background:new 0 0 100 100; fill: %230d6efd;' version='1.1' viewBox='0 0 512 512' width='30px' height='30px' xml:space='preserve' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'%3E%3Cpath d='M480,207H308.6L256,47.9L203.4,207H32l140.2,97.9L117.6,464L256,365.4L394.4,464l-54.7-159.1L480,207z'/%3E%3C/svg%3E");
    }

    .review-list li dl dd {
        padding-top: 20px;
        font-size: 14px;
    }

    .review-textarea textarea {
        width: 100%;
        height: 100px;
        padding: 20px;
    }

    .btn-box {
        display: flex;
        justify-content: flex-end;
    }

    .review-text {
        padding-bottom: 20px;
        line-height: 1.4;
    }

    .btn-box button {
        margin: 0 5px;
        padding: 5px 10px 4px 30px;
        line-height: 30px;
        color: #0d6efd;
        font-weight: 700;
        border: 1px solid #ddd;
        border-radius: 4px;
    }
    .itemLink {
        text-decoration-line: none;
        color: #333333;
    }
    .itemLink:hover{
        text-decoration-line: none;
        color: black;
    }
</style>
<body class="pt-5">
<br>
<div class="container">
    <div class="accordion" id="accordionExample">
        <c:set var="i" value="${0}"/>
        <c:forEach items="${order.orderDetails}" var="orderDetail">
            <div class="accordion-item">
                <h2 class="accordion-header" id="heading${i}">
                    <button class="accordion-button collapsed" type="button"
                            data-bs-toggle="collapse" data-bs-target="#collapse${i}"
                            aria-expanded="false" aria-controls="collapse${i}">
                        <div style="width: 10.66%">${order.orderCode}</div>
                        <div class="tableContent text-start"
                             style="width: 50%; margin-left: 50px">
                            <div style="width: 10%; height: 100%; float: left;">
                                <img style="width: 100%;"
                                     src="${orderDetail.itemOption.item.itemImage}" alt="">
                            </div>
                            <div style="width: 90%; height: 100%; float: left;">
                                <a class="itemLink" href="/item/${orderDetail.itemOption.itemCode}">
                                <span style="font-size: medium; font-weight: bolder">${orderDetail.itemOption.item.itemName}</span><br>
                                <span style="font-size: small">${orderDetail.itemOption.optionName}size</span>
                                </a>
                            </div>
                        </div>
                        <div style="width: 16.66%">가격 : ${orderDetail.orderPrice}원</div>
                        <div style="width: 16.66%">배송비 : ${orderDetail.postPrice}원</div>
                        <div style="width: 8.33%">
                            <span>${orderDetail.amount}개</span>
                        </div>
                        <div style="width: 8.33%">${orderDetail.postedStatus}</div>
                    </button>
                </h2>
                <div id="collapse${i}" class="accordion-collapse collapse"
                     aria-labelledby="heading${i}" data-bs-parent="#accordionExample">
                    <c:if test="${orderDetail.postedStatus != 'Refund' && orderDetail.postedStatus != 'Review'}">
                    <div class="accordion-body">
                        <c:if test="${orderDetail.postedStatus == 'Done'}">
                            <div>
                                <div class="review-textarea">
                                        <div class="star-box">
                                            <input type="checkbox" name="reviewStars" id="star1" value="1"
                                                   class="star-sel" title="1점"> <label for="star1"></label>
                                            <input type="checkbox" name="reviewStars" id="star2" value="2"
                                                   class="star-sel" title="2점"> <label for="star2"></label>
                                            <input type="checkbox" name="reviewStars" id="star3" value="3"
                                                   class="star-sel" title="3점"> <label for="star3"></label>
                                            <input type="checkbox" name="reviewStars" id="star4" value="4"
                                                   class="star-sel" title="4점"> <label for="star4"></label>
                                            <input type="checkbox" name="reviewStars" id="star5" value="5"
                                                   class="star-sel" title="5점"> <label for="star5"></label>
                                        </div>
                                    <form action="/review/add" method="post">
                                        <input type="hidden" name="reviewStar" id="reviewStar">
                                        <label for="reviewContent">리뷰작성하기</label>
                                        <input class="form-control" type="text" name="reviewContent" id="reviewContent">
                                        <input type="hidden" name="itemCode" value="${orderDetail.itemOption.item.itemCode}"/>
                                        <input type="hidden" name="orderDetailCode" value="${orderDetail.orderDetailCode}">
                                        <input class="btn btn-primary" type="submit" value="작성하기"/>
                                    </form>
                                </div>
                            </div>
                        </c:if>
                        <button id="${orderDetail.orderDetailCode}" name="refund" class="btn btn-sm btn-secondary" type="button">환불하기
                        </button>
                        </c:if>
                    </div>
                </div>
            </div>
            <c:set var="i" value="${i+1}"/>
        </c:forEach>
    </div>
    <br>
    <div class="row">
        <div class="col-6">
            <table class="table text-center">
                <tbody>
                <tr>
                    <th>결제번호</th>
                    <td>${order.impUid}</td>
                </tr>
                <tr>
                    <th>결제일</th>
                    <td>${order.paidAtToString()}</td>
                    </th>
                <tr>
                    <th>결제금액</th>
                    <td>${order.totalPay}원</td>
                </tr>
                <tr>
                    <th>환불금액</th>
                    <td>${order.change}원</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="col-6">
            <table class="table text-center">
                <tbody>
                <tr>
                    <th>이름</th>
                    <td>${order.buyerName}</td>
                </tr>
                <tr>
                    <th>연락처</th>
                    <td>${order.buyerTel}</td>
                </tr>
                <tr>
                    <th>배송주소</th>
                    <td>${order.buyerAddr}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
