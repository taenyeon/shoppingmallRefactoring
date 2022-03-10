<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
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
    <title>Title</title>
    <script type="text/javascript">
        $(document).ready(function (e) {

        });
    </script>
</head>
<body>
<div class="container pt-5">
<table class="table text-center">
    <thead>
    <tr>
        <th style="width: 8.33%">주문번호</th>
        <th>주문날짜</th>
        <th style="width: 16.66%">주문상태</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="order">
        <tbody>
<tr>
    <td><a class="itemLink" href="/order/${order.orderCode}">${order.orderCode}</a></td>
    <c:if test="${not empty order.impUid}">
    <td>${order.paidAtToString()}</td>
    </c:if>
    <td>${order.isPaid}</td>
</tr>
</tbody>
    </c:forEach>
</table>
</div>
</body>
</html>
