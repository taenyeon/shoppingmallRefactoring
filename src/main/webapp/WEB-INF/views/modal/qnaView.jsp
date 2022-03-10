<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
$(function () {
	  $(document).on("click", "[id^='replyBtn_']", function () {
		  let boardId = $(this).attr('id').split('_')[1];
		  $(this).hide();
		  result = '<div class="accordion-body" id="replyBody_'+boardId+'">'
		  result += '<textarea style="width: 100%; height: 50px;" class="form-control" aria-label="With textarea" id="reply_'+boardId+'" name="boardReply" placeholder="답변을 입력해주세요."></textarea>'
		  result += '<div align="right">'
		  result += '<button type="button" class="btn btn-primary" id="replySummitBtn_'+boardId+'" style="margin-top: 10px; margin-left: 10px;">저장</button>'
		  result +=	'<button type="button" class="btn btn-primary" id="replyCancleBtn_'+boardId+'" style="margin-top: 10px; margin-left: 10px;">취소</button>'
		  result +=	'</div></div>'
		  
		  $("#cBody_"+boardId).append(result);
		 
      });
	  
	  $(document).on("click", "[id^='replyCancleBtn_']", function () {
		  let boardId = $(this).attr('id').split('_')[1];
		  $("#replyBody_"+boardId).remove();
          $("#replyBtn_"+boardId).show();
      });

	$(document).on("click", "[id ^= 'replySummitBtn_']", function () {
		let boardId = $(this).attr('id').split('_')[1];
		let reply = $("#reply_"+boardId).val();
		let itemCode = '${itemCode}';
		$.ajax({
			url : "/qna/reply",
			method : "POST",
			data : { "boardReply" : reply,"boardId" : boardId , "itemCode" : itemCode },
			success : function (){
				callQna();
			}
		})
	});

	$(document).on("click", "[id = 'submitBtn']", function () {
		let boardTitle = $("#boardTitle").val();
		let boardContent = $("#boardContent").val();
		let itemCode = $("#itemCode").val();
		$.ajax({
			url : "/qna/write",
			method : "POST",
			data : {"boardTitle":boardTitle ,
			"boardContent" : boardContent,
			"itemCode" : itemCode},
			success : function (){
				callQna();
			}
		})
	});
   });
</script>
	<c:choose>
	<c:when test="${empty qnaList}">
		<br><br>
		<h4>QNA가 없습니다.</h4>
	</c:when>
	<c:when test="${not empty qnaList}">
	<div class="accordion" id="accordionList">
<c:forEach items="${qnaList}" var="qna">
	  <div class="accordion-item">
		    <h2 class="accordion-header" id="headingOne">
		      <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${qna.boardId}" aria-expanded="true" aria-controls="collapse${qna.boardId}">
		        ${qna.boardTitle}<!--여기가 제목  --> <!-- 답글여부  -->
		      </button>
		    </h2>
		    <div id="collapse${qna.boardId}" class="accordion-collapse collapse" aria-labelledby="headingOne" data-bs-parent="#accordionList">
		      <div class="accordion-body" id="cBody_${qna.boardId}">
		        <!--질문자 id  <--><p align="left" id="bWriter">작성자 : ${qna.memberId}</p>
		        <!-- 여기가 질문 <--><p align="left" id="content">${qna.boardContent}</p>
		        <div align="right">
				<c:if test="${sessionScope.member.memberLevel.getLast().equals('SELLER') and qna.boardReply == null}">
					<button type="button" class="btn btn-primary" id="replyBtn_${qna.boardId }" style="margin-top: 10px;">
					  답글달기
					</button>
				</c:if>
				</div>
		      </div>
				<c:if test="${qna.boardReply != null}">
		      <div class="accordion-body" style="background-color: #F8F8F8;">
		      <div align="left">
		      <p class="text-primary">판매자</p>
		        	${qna.boardReply }
		      </div>
		      </div>
	      </c:if>
	  </div>
	</div>
	      </c:forEach>
	    </div>
<hr />
	      </c:when>
	     </c:choose>
<!--밑으로 질문 달기 버튼 모달로  -->
<!-- Button trigger modal -->
<c:if test="${sessionScope.member.memberLevel.getLast().equals('USER') }">
	<div align="right">
		<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#userWriteModal">
		  질문하기
		</button>
	</div>
</c:if>

<!-- 질문 Modal -->
<div class="modal fade" id="userWriteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLabel">QNA</h5>
        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
      </div>
	      <div class="modal-body">
	      		<div class="mb-3">
				  <h5 align="left">Title</h5>
				  <input type="text" class="form-control" id="boardTitle" name="boardTitle" placeholder="제목을 입력해주세요.">
				</div>
	      		<div class="mb-3">
				  <h5 align="left">Content</h5>
				  <textarea style="width: 100%; height: 300px;" class="form-control" aria-label="With textarea" id="boardContent" name="boardContent" placeholder="내용을 입력해주세요."></textarea>
				</div>
	      <div class="modal-footer">
	      		<input type="hidden" value="${itemCode }" name="itemCode" id="itemCode"/>
		      <button type="button" class="btn btn-primary" id="submitBtn" data-bs-dismiss="modal">쓰기</button>
	        <!-- <button type="button" class="btn btn-primary" >쓰기</button> -->
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
	      </div>
	    </div>
 	 </div>
	</div>
</div>