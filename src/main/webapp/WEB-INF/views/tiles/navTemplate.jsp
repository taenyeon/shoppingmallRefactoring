<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<style>
    /* 검색창*/
    #custom-search-input {
        margin:  0;
        padding: 0;
    }

    .back-to-top {
        cursor: pointer;
        position: fixed;
        bottom: 10px;
        right: 10px;
        display: none;
    }

    #custom-search-input .search-query {
        padding-right: 3px;
        padding-right: 4px \9;
        padding-left: 3px;
        padding-left: 4px \9;

        margin-bottom: 0;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        border-radius: 3px;
    }

    #custom-search-input button {
        border: 0;
        background: none;
        padding: 2px 5px;
        margin-top: 5.5px;
        position: relative;
        left: -28px;
        -webkit-border-radius: 3px;
        -moz-border-radius: 3px;
        border-radius: 3px;
        color: black;
    }

    .search-query:focus + button {
        z-index: 3;
    }
    #footerLink{
        text-decoration: none;
    }
    #footerLink:hover{
        text-decoration: none;
    }
</style>
<script>
    $(function () {
        $(window).scroll(function () {
            if ($(this).scrollTop() > 250) {
                $('#back-to-top').fadeIn();
                $('#back-to-top').css('left');
            } else {
                $('#back-to-top').fadeOut();
            }
        });
        $("#back-to-top").click(function () {
            $('html, body').animate({scrollTop: 0}, 800);
            return false;
        });
    });
</script>
<nav class="navbar navbar-expand-lg navbar-light fixed-top" style="border-bottom: 1px solid #cccccc; background-color: white">
    <div class="container-fluid">
        <a class="navbar-brand text-primary" style="font-weight: bolder;" href="/">AraseoSajo</a>
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/item">전 세계 상품 쇼핑하기</a>
                </li>
            </ul>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon btn-sm"></span>
        </button>
        <div class="collapse navbar-collapse flex-grow-0" id="navbarSupportedContent">
            <ul class="navbar-nav mb-2 mb-lg-0 text-right">
            <form action="/item/search" method="GET">
                <div id="custom-search-input" class="col-12">
                    <div class="input-group col-md-12">
                        <input type="text" name="q" class="search-query form-control rounded-pill" placeholder="Search"/>
                        <input type="hidden" name="page" value="${pagination.page}">
                        <span class="input-group-btn">
                                    <button class="btn btn-danger" type="submit">
                                        <i class="fas fa-search"></i>
                                    </button>
                                </span>
                    </div>
                </div>
            </form>
                <c:choose>
                <c:when test="${not empty sessionScope.member}">
                <li class="nav-item">
                    <span class="nav-link"><b class="text-primary">${sessionScope.member.memberId}</b>&nbsp;님</span>

                </li>
                    <c:if test="${sessionScope.member.memberLevel.getLast() == 'USER'}">
                        <a class="nav-link" href="/member/${sessionScope.member.memberId }/info">개인정보</a>
                        <a class="nav-link" href="/cart/myCart">장바구니</a>
                        <a class="nav-link" href="/order/myOrder">주문조회</a>
                    </c:if>
                    <c:if test="${sessionScope.member.memberLevel.getLast() == 'SELLER'}">
                        <a class="nav-link" href="/member/${sessionScope.member.memberId}/info">개인정보</a>
                        <a class="nav-link" href="/seller/${sessionScope.member.memberId}">상점관리</a>
                    </c:if>
                    <c:if test="${sessionScope.member.memberLevel.getLast() == 'ADMIN'}">
                        <a class="nav-link" href="/admin/list">관리자모드</a>
                        <a class="nav-link" href="/admin/chart">주문토탈차트</a>
                    </c:if>
                    <a class="nav-link" href="/member/logout">로그아웃</a>
                </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link text-primary" aria-current="page" href="/member/login">로그인</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link text-primary" href="/member/join">회원가입</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>

        </div>
    </div>
</nav>

<a id="back-to-top" href="#" class="btn btn-secondary btn-sm back-to-top" role="button"
   title="Click to return on the top page" data-toggle="tooltip" data-placement="left">TOP<span
        class="glyphicon glyphicon-chevron-up"></span></a>