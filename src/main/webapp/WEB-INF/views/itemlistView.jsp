<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/pagination.css">
</head>
<body>
	<h3>샵 메인 이미지 영역</h3>
					
	
	<table border="" style="width:100%;">
	<c:forEach items="${itemlist }" var="dto">
		<tr>
		 	<td style="width: 33%;">${dto.item_image }</td>
		 	<td style="width: 33%;">${dto.item_name }</td>
		 	<td style="width: 33%;">${dto.item_cost }</td>
		</tr>
		
	<%-- 	링크 걸리는 부분>이미지,텍스트,가격
		 <a href="itemdetail_view?icode=${dto.item_code }">${dto.item_image }</a>	
		<a href="itemdetail_view?icode=${dto.item_code }">${dto.item_name }</a>	
		<a href="itemdetail_view?icode=${dto.item_code }">${dto.item_cost }</a>	 --%>
	</c:forEach>
	
	</table>


<!-- pagination 처리부분 -->
<form id="pagination" name="pagination" action="itemviewlist" method="post">

		<div class="paging">
			<c:if test="${searchVO.totPage>1 }">
				<c:if test="${searchVO.page>1 }">
					<a href="list?page=1" class="btn_first"></a>
					<a href="list?page=${searchVO.page-1 }" class="btn_pre"></a>
				</c:if>
				<span class="paging_num"> 
				<c:forEach begin="${searchVO.pageStart }" end="${searchVO.pageEnd }" var="i">
						<!-- 현재 선택된 페이지는 링크가 걸리지 않도록 하는 부분 -->
						<c:choose>
							<c:when test="${i eq searchVO.page }">
								<span><a href="javascript:" class="on"><span>${i }</span></a></span>
							</c:when>
							<c:otherwise>
								<span><a href="itemviewlist }"><span>${i }</span></a></span>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</span>
				<c:if test="${searchVO.totPage>searchVO.page }">
					<a href="itemviewlist?page=${searchVO.page+1 }" class="btn_next"></a>
					<a href="itemviewlist?page=${searchVO.totPage }" class="btn_last"></a>
				</c:if>
				
			</c:if>
		</div>
		</form> 


</body>
</html>