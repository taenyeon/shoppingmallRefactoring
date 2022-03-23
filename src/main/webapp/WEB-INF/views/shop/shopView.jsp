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
<script>
    $(function () {
        $(document).on("click", "[id='update']", function () {
            fnModuleInfo();
        });
        $(document).on("click", "[id='add']", function () {
            location.href = "/item/add";
        });
        $(document).on("click", "[id='order']", function () {
            location.href = "/seller/${seller.memberId}/order";
        });

    })
        function fnModuleInfo() {
            $("#sellerModal .modal-content").load("/seller/${seller.memberId}/update");
        }
</script>
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

    .itemLink{
        color: black;
        text-decoration-line: none;
    }
    .itemLink:hover {
        color: black;
        text-decoration-line: none;
    }

    .cards-box {
        display: flex;
        /*justify-content: space-between;*/
        margin: 65px 70px 0;
        flex-wrap: wrap;
        float: left;
    }

    .txt_line {
        width: 100%;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
    }
    #sellerModal{
        position: fixed;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    #sellerBar {
        background: #2c3e50; /* fallback for old browsers */
        background: -webkit-linear-gradient(to right, #3498db, #2c3e50); /* Chrome 10-25, Safari 5.1-6 */
        background: linear-gradient(to right, #3498db, #2c3e50); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */

    }

</style>
<body>
<div id="sellerBar" class="p-5 mb-4 bg-light rounded-3">
    <div class="row" style="color: #e8e8e8">

        <div class="col-6">

            <h3>
                ${shop.shopName}
            </h3>
            <br>
            <form action="/seller/${shop.shopName}" method="GET">
                <div id="custom-search-input" class="col-7">
                    <div class="input-group col-md-12">
                        <input type="text" name="q" class="search-query form-control rounded-pill"
                               placeholder="Search"/>
                        <input type="hidden" name="page" value="${pagination.page}">
                        <span class="input-group-btn">
                    <button class="btn btn-danger" type="submit"><i class="fas fa-search"></i></button>
                </span>
                    </div>
                </div>
            </form>
            <c:if test="${shop.memberId == sessionScope.member.memberId}">
                <br>
                <button id="update" type="button" data-bs-toggle="modal" data-bs-target="#sellerModal" class="btn btn-sm btn-light shadow col-2">상점 관리</button>
                <button id="add" type="button" class="btn btn-sm btn-light shadow col-2">상품 등록</button>
                <button id="order" type="button" class="btn btn-sm btn-light shadow col-2">주문 관리</button>
            </c:if>
        </div>
        <div class="col-6">
            <h6 style="line-height: 250%;">${shop.shopInfo}</h6>
        </div>
    </div>
</div>
<div class="container">
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
                            <h5 class="card-title txt_line"><c:out value="${item.itemName}"/></h5>
                            <h5 class="card-subtitle"><c:out value="${item.memberId}"/></h5>
                            <br>
                            <span class="card-text mt-5"><c:out value="${item.itemPrice}원"/></span>
                            <button style="display: inline; margin-left: 5px; margin-bottom: 10px"
                                    class="btn btn-sm btn-primary disabled ">${item.country.countryName}</button>
                        </div>
                    </div>
                </a>
            </c:forEach>
        </div>
    </div>
    <div style="clear: both"></div>
    <div id="paginationBox">
        <ul class="pagination justify-content-center" style="font-size: medium">
            <li class="page-item"><a class="page-link" style="border: none;" href="/seller/${shop.shopName}?page=1"> << </a>
            </li>
            <c:choose>
                <c:when test="${pagination.startPage != pagination.endPage}">
                    <c:forEach var="number" begin="${pagination.startPage}" end="${pagination.endPage}">
                        <c:if test="${number == pagination.page}">
                            <li class="page-item active selectedPage"><a href="/seller/${shop.shopName}?page=${number}" class="page-link"
                                                                         style="border: none">${number}</a></li>
                        </c:if>
                        <c:if test="${number != pagination.page}">
                            <li class="page-item"><a href="/seller/${shop.shopName}?page=${number}" class="page-link"
                                                     style="border: none">${number}</a></li>
                        </c:if>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <li class="page-item active selectedPage"><a href="/seller/${shop.shopName}?page=${pagination.startPage}"
                                                                 class="page-link"
                                                                 style="border: none">${pagination.startPage}</a></li>
                </c:otherwise>
            </c:choose>
            <li class="page-item"><a class="page-link" style="border: none" href="/seller/${shop.shopName}?page=${pagination.pageCnt}">
                >> </a></li>
        </ul>
    </div>
</div>
<div class="modal fade" id="sellerModal" tabindex="-1" role="dialog" aria-labelledby="historyModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">
        </div>
    </div>
</div>

</body>
</html>
