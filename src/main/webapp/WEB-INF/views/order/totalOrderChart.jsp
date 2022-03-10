<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>부트스트랩 차트그리기</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<!-- 차트 링크 -->
<script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
</head>
<body class="pt-5">
	<div class="container">
		<div class="row my-3">
			<div class="col">
				<h4>날짜별 총 주문금액</h4>
			</div>
		</div>
		<div class="row my-2">
			<div class="col">
				<div class="card">
					<div class="card-body">
						<canvas id="myChart" height="150vh"></canvas>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- 부트스트랩 -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>
	<!-- 차트 -->
	<script> 
	 var ctx = document.getElementById('myChart').getContext('2d'); 
	 let paidAt = ${paidAt};
	 let totalPay = ${totalPay};
	 
	 var chart = new Chart(ctx, { 
		 // 챠트 종류를 선택 
		 type: 'line', 
		 // 챠트를 그릴 데이타 
		 data: {
			 //라벨에 날짜
			 labels: paidAt, 
			 datasets: [{ 
				label: '날짜별 총 주문금액', 
				backgroundColor: 'transparent', 
		     	borderColor: 'red', 
		     	data: totalPay 
			 }] 
		 }, 
		     	// 옵션 
		     	options: {
		     		responsive: true,
					scales: {
						yAxes: [{
							ticks: {
								beginAtZero: true,
								stepSize : 500
							}
						}]
					}
		     	} 
		 }); 
	 </script>
</body>

</html>