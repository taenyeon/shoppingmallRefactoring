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
    
    function checkCapsLock(event) {
		if (event.getModifierState("CapsLock")) {
			document.getElementById("msg").innerText 
			= "Caps Lock이 켜져 있습니다."
		} else {
			document.getElementById("msg").innerText 
			= ""
		}
	}
    
    function joinCheck() {
    	let uid = document.getElementById('mId');
    	let name = document.getElementById('mName');
    	let email = document.getElementById('mEmail');
    	let pwd = document.getElementById('mPwd');
    	let rePwd = document.getElementById('rePwd');
    	let addr = document.getElementById('address_kakao');
    	let detailAddr = document.getElementById('detailAddress');
    	let tel = document.getElementById('mTel');
    	let birth = document.getElementById('mBirth');
    	let bName = document.getElementById('bName');
    	let bRegNo = document.getElementById('bRegNo');

    	if (uid.value === "") { // 해당 입력값이 없을 경우 같은말: if(!uid.value)
    		// 비밀번호 영문자+숫자+특수조합(8~25자리 입력) 정규식
    		alert("아이디를 입력하세요.");
    		uid.focus(); // focus(): 커서가 깜빡이는 현상, blur(): 커서가 사라지는 현상
    		return false; // return: 반환하다 return false: 아무것도 반환하지 말아라 아래 코드부터 아무것도
    						// 진행하지 말것
    	}else{
    		var idCheck = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,20}$/;

        	if (!idCheck.test(uid.value)) {
        		alert("아이디는 영문자+숫자 조합으로 6~20자리 사용해야 합니다.");
        		uid.focus();
        		return false;
        	};
    	};

    	if (name.value == "") {
    		alert("이름을 입력하세요.");
    		name.focus();
    		return false;
    	};

    	if (email.value == "") {
    		alert("이메일 주소를 입력하세요.");
    		email.focus();
    		return false;
    	};
    	
    	if (pwd.value == "") {
    		alert("비밀번호를 입력하세요.");
    		pwd.focus();
    		return false;
    	};

    	// 비밀번호 영문자+숫자+특수조합(8~25자리 입력) 정규식
    	var pwdCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;

    	if (!pwdCheck.test(pwd.value)) {
    		alert("비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리 사용해야 합니다.");
    		pwd.focus();
    		return false;
    	};

    	if (rePwd.value !== pwd.value) {
    		alert("비밀번호가 일치하지 않습니다..");
    		rePwd.focus();
    		return false;
    	};

    	var reg = /^[0-9]+/g; // 숫자만 입력하는 정규식

    	if (!reg.test(tel.value)) {
    		alert("전화번호는 숫자만 입력할 수 있습니다.");
    		tel.focus();
    		return false;
    	};

    	if (addr.value == "") {
    		alert("주소를 입력하세요.");
    		addr.focus();
    		return false;
    	};
    	
    	if (detailAddr.value == "") {
    		alert("상세주소를 입력하세요.");
    		detailAddr.focus();
    		return false;
    	};
    	
    	if (birth.value == "") {
    		alert("생년월일을 입력하세요.");
    		birth.focus();
    		return false;
    	};
    	
    	if (bName != null && bName.value == ""){
    		alert("사업자명을 입력하세요.");
    		bName.focus();
    		return false;
    	};
    	
    	if (bRegNo != null && bRegNo.value == ""){
    		alert("사업자번호를 입력하세요.");
    		bRegNo.focus();
    		return false;
    	};
    	// 입력 값 전송
    	document.frm.submit(); // 유효성 검사의 포인트
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