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
    	levelChk();
        
        $('input[id=mPwd]').click(function(){
        	if($('#mPwd').attr('readonly') != null){
	        	$('#mPwd').removeAttr('readonly');
        	}
    	});
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

    function updateCheck() {
    	let name = document.getElementById('mName');
    	let email = document.getElementById('mEmail');
    	let pwd = document.getElementById('mPwd');
    	let rePwd = document.getElementById('rePwd');
    	let addr = document.getElementById('address_kakao');
    	let detailAddr = document.getElementById('detailAddress');
    	let tel = document.getElementById('mTel');
    	let birth = document.getElementById('mBirth');

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

    	// 비밀번호 영문자+숫자+특수조합(8~25자리 입력) 정규식
    	var pwdCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/;
		if(pwd.value != ""){
	    	if (!pwdCheck.test(pwd.value)) {
	    		alert("비밀번호는 영문자+숫자+특수문자 조합으로 8~25자리 사용해야 합니다.");
	    		pwd.focus();
	    		return false;
	    	};
		}

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
    <h2>정보수정</h2>
    <form name="frm" method="POST" action="/members/${member.memberId }/update">
        <div id="inputDiv">
            <div class="mb-3">
                ID <br />
               <input type="text" name="memberId" value="${member.memberId}" readonly/>
            </div>
            <div class="mb-3">
                이름 <br />
                <input id="mName" class="form-control" type="text" name="memberName" value="${member.memberName }"/>
            </div>
            <div class="mb-3">
                이메일 <br />
                <input id="mEmail" class="form-control" type="text" name="memberEmail" value="${member.memberEmail }"/>
            </div>
            <div class="mb-3">
                비밀번호 <br />
                <input id="mPwd" class="form-control" type="password" name="memberPwd" readonly />
            </div>
            <div class="mb-3">
                주소 <br />
                <input class="form-control" type="text" id="address_kakao" name="memberAddress" readonly value="${member.memberAddress }"/>
            </div>
            <div class="mb-3">
                상세주소 <br />
                <input id="detailAddress" class="form-control" type="text" name="memberDetailAddress" value="${member.memberDetailAddress }"/> 
            </div>
            <div class="mb-3">
                전화번호 <br />
                <input id="mTel" class="form-control" type="phone" name="memberTel" value="${member.memberTel }" /> 
            </div>
            <div class="mb-3">
                생년월일 <br />
                <input id="mBirth" class="form-control" type="date" name="memberBirth" value="${member.memberBirth }"/>
            </div>
        </div>
        <button class="btn btn-primary btn-lg" type="button" onclick="updateCheck();">수정</button>
        <a class="btn btn-primary btn-lg" href="/" role="button">취소</a>
        <a class="btn btn-primary btn-lg" data-bs-toggle="modal" data-bs-target="#deleteChkModal" role="button">회원탈퇴</a>
    </form>
</div>
</body>
<!-- 회원 탈퇴 모달팝업 -->
<div class="modal fade" id="deleteChkModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h3 class="modal-title" id="exampleModalLabel">회원탈퇴</h3>
        <!-- <button type="button" class="close" data-bs-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button> -->
      </div>
      <div class="modal-body">
        <form>
          <div class="form-group">
            <label for="recipient-name" class="col-form-label" style="font-size: 20px;">
            	${member.memberId } 님 정말로 탈퇴하시겠습니까?
            </label>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <a class="btn btn-primary" href="/members/${member.memberId }/delete" role="button">탈퇴</a>
        <button type="button" class="btn btn-primary" data-bs-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>
</html>