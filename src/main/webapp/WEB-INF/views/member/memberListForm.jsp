<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!--카카오맵  -->
<script
	src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<script>
$(function() {
	selectList();
	
	$('#btnSearch').click(function () {
		selectList();
	});
})
function selectList() {
	let chkInfo = $('[name = chk_info]:checked').val();
		let condition = $('#condition').val();
		$.ajax({
        url: '/admin/list',
        method: 'POST',
        data: {
        	'chkInfo' : chkInfo,
        	'condition' : condition
        },
			success : function(data) {
			
			data = data.trim();
			$('#memberList').html(data);
        }
    })
}
</script>
</head>
<body class="pt-5">
	<div class="container">
		<div>
			<h2>회원관리</h2>
			<input type="radio" name="chk_info" value="member_id" checked /> 
			<label for="idRadio">아이디</label> 
			<input type="radio" name="chk_info" value="member_name" /> 
			<label for="nameRadio">이름</label>
			<input type="radio" name="chk_info" value="member_level" /> 
			<label for="levelRadio">권한</label>
			<span style="margin-left: 30px;"><input id="condition" type="text" /></span>
			<span style="margin-left: 15px;"><input id="btnSearch" type="button" value="검색"/></span>
		</div>
		<!-- <table id="memberList" style="border: 1px;">
		</table> -->
		<div id="memberList">
		
		</div>
	</div>
</body>
</html>