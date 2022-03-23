<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Insert title here</title>
<!--카카오맵  -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    $(function() {
        $("[name='chk_info']").change(
            function() {
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
    <form name="frm" method="POST" action="/members/join">
        <div id="inputDiv">
            <input type="radio" id="userBtn" name="chk_info" value="일반회원" checked />
            <label for="userBtn">일반회원</label>
            <input type="radio" id="sellerBtn" name="chk_info" value="판매자" />
            <label for="sellerBtn">판매자</label> <br />
            <div class="mb-3">
                ID <br />
                <input class="form-control" type="text" name="memberId" id="mId"/>
            </div>
            <div class="mb-3">
                이름 <br />
                <input class="form-control" type="text" name="memberName" id="mName" />
            </div>
            <div class="mb-3">
                이메일 <br />
                <input class="form-control" type="text" name="memberEmail" id="mEmail"/>
            </div>
            <div class="mb-3">
                비밀번호 <br />
                <input class="form-control" type="password" name="memberPwd" id="mPwd" onkeyup="checkCapsLock(event);"/> 
            </div>
            <div id="msg" style="color: red"></div> 
            <div class="mb-3">
                비밀번호 재확인 <br />
                <input class="form-control" type="password" name="rePwd" id="rePwd"/> 
            </div>
            <div class="mb-3">
                주소 <br />
                <input class="form-control" type="text" id="address_kakao" name="memberAddress" readonly />
            </div>
            <div class="mb-3">
                상세주소 <br />
                <input class="form-control" type="text" name="memberDetailAddress" id="detailAddress"/> 
            </div>
            <div class="mb-3">
                전화번호 <br />
                <input class="form-control" type="phone" name="memberTel" id="mTel"/> 
            </div>
            <div class="mb-3">
                생년월일 <br />
                <input class="form-control" type="date" name="memberBirth" id="mBirth"/>
            </div>
        </div>
        <input class="form-control" type="button" value="가입하기" onclick="joinCheck();">
    </form>
</div>
</body>
</html>