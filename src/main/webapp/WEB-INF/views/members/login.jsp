<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
          crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<script>
	function checkCapsLock(event) {
		if (event.getModifierState("CapsLock")) {
			document.getElementById("msg").innerText 
			= "Caps Lock이 켜져 있습니다."
		} else {
			document.getElementById("msg").innerText 
			= ""
		}
	}
</script>
</head>
<body class="pt-5">
<div class="container">
    <h2>로그인</h2>
    <form method="POST" action="/members/login" >
    <div class="mb-3">
         ID <br />
         <input class="form-control" type="text" name="memberId" />
    </div>
    <div class="mb-3">
             비밀번호 <br />
         <input class="form-control" type="password" name="memberPwd" onkeyup="checkCapsLock(event);"/>
         <div id="msg" style="color: red"></div>   
    </div>
         <input class="form-control" type="submit" value="로그인">
    </form>
</div>
</body>
</html>