<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Insert title here</title>
<!--카카오맵  -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
    <script src="/resources/js/validationSubmit.js"></script>
<script>
    $(function() {
        $("[name='chk_info']").change(
            function () {
                if (this.value === "판매자") {
                    let text = "<div class='mb-3' id='seller'> 사업자명 <br />"
                    text += "<input id='bName' class='form-control' type='text' name=shopName' />"
                    text += " 사업자번호 <br />"
                    text += "<input id='bRegNo' class='form-control' type='text' name='shopRegistrationNo' />"
                    text += "<input type='hidden' value='USER,SELLER' name='memberLevel'/></div>"
                    $("#inputDiv").append(text);
                } else {
                    $("#seller").remove();
                }
            });

        /*  $.getscript('regExp.js', function(){
             alert("겟스크립트~");
             console.log('regExp.js loading!!');
         }); */

    })
    $(document).on("click", "button[id='join']", function () {
        let form = $("#frm")[0];
        let formData = new FormData(form);
        let url = $("#frm").attr("action");
        alert(url);
        postSubmit(url,formData);
    });
    window.onload = function() {
    document.getElementById("address_kakao").addEventListener("click",function() {
                        new daum.Postcode(
                                {
                                    oncomplete : function(data) {
                                        document.getElementById("address_kakao").value = data.address;
                                    }
                                }).open();
                    });
    }
</script>
</head>
<body class="pt-5">
<div class ="container">
    <h2>회원가입</h2>
    <form id="frm" method="POST" action="/member/join">
        <div id="inputDiv">
            <input type="radio" id="userBtn" name="chk_info" value="일반회원" checked />
            <label for="userBtn">일반회원</label>
            <input type="radio" id="sellerBtn" name="chk_info" value="판매자" />
            <label for="sellerBtn">판매자</label> <br />
            <div class="mb-3">
                ID <br />
                <input class="form-control" type="text" name="memberId" id="mId" placeholder="영어, 숫자, '_' 포함 5~11자리로 입력해주세요."/>
            </div>
            <div class="mb-3">
                이름 <br />
                <input class="form-control" type="text" name="memberName" id="mName" placeholder="한글 3~6자리로 입력해주세요."/>
            </div>
            <div class="mb-3">
                이메일 <br />
                <input class="form-control" type="text" name="memberEmail" id="mEmail" placeholder="이메일 형식으로 작성해주세요."/>
            </div>
            <div class="mb-3">
                비밀번호 <br />
                <input class="form-control" type="password" name="memberPwd" id="mPwd" placeholder="영어, 숫자, 특수문자 포함 8~15자리로 입력해주세요."/>
            </div>
            <div class="mb-3">
                비밀번호 재확인 <br />
                <input class="form-control" type="password" name="rePwd" id="rePwd"/> 
            </div>
            <div class="mb-3">
                주소 <br />
                <input class="form-control" type="text" id="address_kakao" name="memberAddress" placeholder="카카오 맵 이용하기" readonly style="background: none; cursor: pointer"/>
            </div>
            <div class="mb-3">
                상세주소 <br />
                <input class="form-control" type="text" name="memberDetailAddress" id="detailAddress" placeholder="상세 주소를 입력해주세요."/>
            </div>
            <div class="mb-3">
                전화번호 <br />
                <input class="form-control" type="tel" name="memberTel" id="mTel" placeholder="예) 010-1234-5678"/>
            </div>
            <div class="mb-3">
                생년월일 <br />
                <input class="form-control" type="date" name="memberBirth" id="mBirth"/>
            </div>
        </div>
        <button class="form-control" type="button" id="join">가입하기</button>
    </form>
</div>
</body>
</html>