<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <style>
        /* 캐러셀(이미지슬라이드) 이미지 크기변경 */
        .carousel-inner {
            width: 100%;
            height: 400px; /* 이미지 높이 변경 */
        }

        .carousel-item {
            width: 100%;
            height: 100%;
        }

        .d-block {
            display: block;
            width: 100%;
            height: 100%;
        }

        .txt_line {
            width: 100%;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        a {
            color: #333333;
            text-decoration: none;
        }

        a:hover {
            color: black;
            text-decoration: none;
        }


        .cont-box {
            display: grid;
            grid-template-rows: repeat(2, 1fr);
            grid-template-columns: repeat(3, calc(33% - 10px));
            column-gap: 20px;
            row-gap: 20px;
            justify-content: space-between;
        }

        .cont-box .item {
            height: 100%;
            border: 1px solid #ddd;
        }

        .cont-box .item:nth-child(1) {
            grid-row: 1 / 3;
        }

        .cont-box .item img {
            display: block;
            width: inherit;
            height: inherit;
            margin: auto;
        }

        .cont-box .item a {
            display: flex;
            flex-direction: column;
            width: inherit;
        }

        .cont-box .item .img-box {
            overflow: hidden;
            position: relative;
            width: inherit;
            height: inherit;
        }

        .cont-box .item a .img-box {
            height: 200px;
        }

        .cont-box .item .img-box img {
            position: absolute;
            top: 50%;
            left: 50%;
            display: block;
            width: auto;
            height: inherit;
            transform: translate(-50%, -50%);
        }

        .cont-box .item:not(:nth-child(1)) .img-box img {
            width: 100%;
            height: auto;
        }

        .cont-box .item a .txt-box {
            width: inherit;
            padding: 7px 10px;
            background: #eee;
        }

        .cont-box .item a p {
            margin: 0;
        }

        .fl-cont {
            display: flex;
            justify-content: space-between;
        }

        .cont-box .item a .country {
            color: #0d6efd;
            font-weight: 700;
            font-size: small;
        }

        .cont-box .item a .money {
            color: #f02637;
            font-weight: 700;
        }

        .cont-box .item a .txt_line {
            width: calc(100% - 10px);
        }

        @media (max-width: 993px) {
            .cont-box .item a .img-box {
                height: 155px;
            }
        }

        @media (max-width: 769px) {
            .cont-box .item a .img-box {
                height: 105px;
            }
        }

        @media (max-width: 577px) {
            .cont-box {
                grid-template-rows: repeat(3, 1fr);
                grid-template-columns: repeat(2, 50%);
            }

            .cont-box .item:nth-child(1) {
                grid-row: 1 / 2;
                grid-column: 1 / 3;
            }

            .cont-box .item:nth-child(1) img {
                top: auto;
                bottom: 0;
                width: 100%;
                height: auto;
                transform: translate(-50%, 0);
            }

            .cont-box .item a .img-box {
                height: 150px;
            }
        }

        h3 {
            text-align: center;
            margin-top: 70px;
            margin-bottom: 30px;
        }
    </style>
    <title>Home</title>
</head>
<body>
<div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
    <div class="carousel-indicators">
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active"
                aria-current="true" aria-label="Slide 1"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1"
                aria-label="Slide 2"></button>
        <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2"
                aria-label="Slide 3"></button>
    </div>
    <div class="carousel-inner">
        <div class="carousel-item active">
            <img src="/resources/banner1.png" class="d-block w-100" alt="...">
        </div>
        <div class="carousel-item">
            <img src="/resources/banner2.png" class="d-block w-100" alt="...">
        </div>
        <div class="carousel-item">
            <img src="/resources/banner3.png" class="d-block w-100" alt="...">
        </div>
    </div>
    <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators"
            data-bs-slide="prev">
        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Previous</span>
    </button>
    <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators"
            data-bs-slide="next">
        <span class="carousel-control-next-icon" aria-hidden="true"></span>
        <span class="visually-hidden">Next</span>
    </button>
</div>


<div class="container">
    <h3>따끈따끈 신상품</h3>
    <div class="cont-box">
        <div class="item">
            <div class="img-box">
            <img src="/resources/subBanner/subbanner1.png" alt="">
            </div>
        </div>
        <c:forEach items="${newItems}" var="item">
            <div class="item">
                <a class="itemLink" href="/item/${item.itemCode}">
                    <div class="img-box">
                        <img src="${item.itemImage}" alt="">
                    </div>
                    <div class="txt-box">
                        <div class="fl-cont">
                            <span class="country">${item.country.countryName}</span>
                        <span><c:out value="${item.itemPrice}원"/></span>
                        </div>
                            <p class="txt_line"><c:out value="${item.itemName}"/></p>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
    <h3>조회수 많은 상품</h3>
    <div class="cont-box">
        <div class="item">
            <div class="img-box">
                <img src="/resources/subBanner/subbanner2.png" alt="">
            </div>
        </div>
        <c:forEach items="${hitItems}" var="item">
            <div class="item">
                <a class="itemLink" href="/item/${item.itemCode}">
                    <div class="img-box">
                        <img src="${item.itemImage}" alt="">
                    </div>
                    <div class="txt-box">
                        <div class="fl-cont">
                            <span class="country">${item.country.countryName}</span>
                            <span><c:out value="${item.itemPrice}원"/></span>
                        </div>
                        <p class="txt_line"><c:out value="${item.itemName}"/></p>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
    <h3>많이 팔린 상품</h3>
    <div class="cont-box">
        <div class="item">
            <div class="img-box">
                <img src="/resources/subBanner/subbanner3.png" alt="">
            </div>
        </div>
        <c:forEach items="${sellItems}" var="item">
            <div class="item">
                <a class="itemLink" href="/item/${item.itemCode}">
                    <div class="img-box">
                        <img src="${item.itemImage}" alt="">
                    </div>
                    <div class="txt-box">
                        <div class="fl-cont">
                            <span class="country">${item.country.countryName}</span>
                            <span><c:out value="${item.itemPrice}원"/></span>
                        </div>
                        <p class="txt_line"><c:out value="${item.itemName}"/></p>
                    </div>
                </a>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
