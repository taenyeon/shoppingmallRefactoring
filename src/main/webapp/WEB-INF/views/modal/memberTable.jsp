<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
/*모달*/   

		function fnModuleInfo(memberId) {
			let url = "/admin/" + memberId + "/detail";
			$('.modal-content').load(url);
		}

</script>
<style>
	#memTable{
		overflow-x: scroll;
    	overflow-y: auto;
    	width: 100%;
	}
    .tableContent {
        display: table;
    }

    .tableContent span {
        display: table-cell;
        vertical-align: middle;
    }

</style>
<div id="memTable">
	<table class="table text-center m-auto" style="width: 1800px;">
		<thead>
			<tr>
				<th style="width: auto;">ID</th>
				<th style="width: auto;">권한</th>
				<th style="width: auto;">이름</th>
				<th style="width: auto;">E-Mail</th>
				<th style="width: auto;">주소</th>
				<th style="width: auto;">TEL</th>
				<th style="width: auto;">생년월일</th>
				<th style="width: auto;">사업자번호</th>
				<th style="width: auto;">사업자명</th>
				<th style="width: auto;">사업자설명</th>
				<th style="width: auto;">탈퇴여부</th>
			</tr>
		</thead>
		<tbody>
			<c:set var="i" value="${0}" scope="page" />
			<c:forEach items="${members }" var="member">
				<tr>
					<td><a href="" data-bs-toggle="modal" data-bs-target="#MoaModal" role="button" data-backdrop="static"
					 onclick="fnModuleInfo('${member.memberId }')">
					${member.memberId }</a></td>
					<td>${member.memberLevel.getLast() }</td>
					<td>${member.memberName }</td>
					<td>${member.memberEmail }</td>
					<td>${member.memberAddress} ${member.memberDetailAddress }</td>
					<td>${member.memberTel }</td>
					<td>${member.memberBirth }</td>
					<td>${member.businessRegistrationNo }</td>
					<td>${member.businessName }</td>
					<td>${member.businessInfo }</td>
					<c:choose>

					<c:when test="${member.isDelete ==0 }">
					<td>X</td>
					</c:when>
						<c:otherwise>
							<td>O</td>
						</c:otherwise>
					</c:choose>
				</tr>
				<c:set var="i" value="${i+1}" scope="page" />
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="modal fade" id="MoaModal" tabindex="-1" role="dialog"
	aria-labelledby="historyModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-md" role="document">
		<div class="modal-content"></div>
	</div>
</div>

