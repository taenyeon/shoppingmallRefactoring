<%--
  Created by IntelliJ IDEA.
  User: gimtaeyeon
  Date: 2022/02/18
  Time: 11:54 오전
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>


</head>
<style>
    p, h5 {
        font-size: small;
        margin: 0;
        padding: 0;
    }

    a {
        color: black;
        text-decoration-line: none;
    }

    .itemImg {
        overflow: hidden;
    }

    .itemImg img {
        transition: all 0.3s linear;
    }

    .itemImg img:hover {
        transform: scale(1.2);
    }

    .itemLink:hover {
        color: black;
    }

    .cards-box {
        display: flex;
        /*justify-content: space-between;*/
        margin: 65px 70px 0;
        flex-wrap: wrap;
        float: left;
    }

    .txt_line {
        width:100%;
        overflow:hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
    }

</style>
<body>
<div class="container">
<c:if test="${not empty items}">
    <div class="card-colums">
        <div id="card-box" class="cards-box">
            <c:forEach items="${items}" var="item">
                <a class="itemLink" href="/item/${item.itemCode}">
                    <div class="card" style="width: 14rem; margin-bottom:15px; margin-left: 10px;">
                        <div class="itemImg">
                            <img class="card-img-top" src="${item.itemImage}"
                                 style="border-bottom: 1px solid #eee; height: 200px;" alt="">
                        </div>
                        <div class="card-body">
                            <span style="color: #0d6efd; font-size: small">${item.country.countryName}</span>

                            <h5 class="card-title txt_line" ><c:out value="${item.itemName}"/></h5>
                            <span class="card-text"><c:out value="${item.itemPrice}원"/></span> <c:if test="${item.itemStock==0}">
                            &nbsp; &nbsp;<span style="color: red; font-size: small">품절</span>
                        </c:if>
                            <br>
                            <span class="card-text" style="color: #5b5b5b; font-size: x-small" ><c:out value="${item.businessName} 마켓"/></span>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
</c:if>
    <c:if test="${empty items}">
        <div style="text-align: center; margin: 10% auto;">
        <h5 >찾는 상품이 없습니다.</h5>
        </div>
    </c:if>
    <div style="clear: both"></div>

    <div id="paginationBox">
        <ul class="pagination justify-content-center" style="font-size: medium">
            <li class="page-item"><a class="page-link" style="border: none;" href="/item?page=1"> << </a>
            </li>
            <c:choose>
                <c:when test="${pagination.startPage != pagination.endPage}">
                    <c:forEach var="number" begin="${pagination.startPage}" end="${pagination.endPage}">
                        <c:if test="${number == pagination.page}">
                            <li class="page-item active selectedPage"><a href="/item?page=${number}" class="page-link"
                                                                         style="border: none">${number}</a></li>
                        </c:if>
                        <c:if test="${number != pagination.page}">
                        <li class="page-item"><a href="/item?page=${number}" class="page-link"
                                                 style="border: none">${number}</a></li>
                        </c:if>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <li class="page-item active selectedPage"><a href="/item?page=${pagination.startPage}"
                                                                 class="page-link"
                                                                 style="border: none">${pagination.startPage}</a></li>
                </c:otherwise>
            </c:choose>
            <li class="page-item"><a class="page-link" style="border: none" href="/item?page=${pagination.pageCnt}">
                >> </a></li>
        </ul>
    </div>
    <!-- Gallery Item 1 -->
</div>
</body>
</html>
