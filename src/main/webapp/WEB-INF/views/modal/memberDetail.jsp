<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<!-- histoty Modal-->
<script>
	
	$(function(){
		
		setLevels();
	});
	function setLevels() {
		
		let levelLength = '${member.memberLevel.size()}';
		if(levelLength == 1){
			$("#comboBox option:eq(0)").prop("selected", true);
		}else if(levelLength == 2){
			$("#comboBox option:eq(1)").prop("selected", true);
		}else if(levelLength == 3){
			$("#comboBox option:eq(2)").prop("selected", true);
		}
	}

</script>
<div class="modal-header">
    <h5 class="modal-title" id="historyModalLabel">${member.memberId } 회원 정보수정</h5>
    <button class="close" type="button" data-bs-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">×</span>
    </button>
</div>
	<div class="modal-body">
	    <div class="table-responsive">
	        <div class="container">
	        </div>
	        
<form action="/admin/memberUpdate" method="post" id="updateForm">
	        <table class="table table-hover">
	            <tr class="content">
	                <th class="text-center">
	                    ID
	                </th>
	                <td>
	                	<div class="mb-3">
		                	<input type="text" value="${member.memberId }" name="memberId" readonly/>
	                	</div>
	                </td>
	            </tr>
	            <tr class="content">
	                <th class="text-center">
	                    PWD
	                </th>
	                <td>
	                	<div class="mb-3">
		                	<input type="text" value="${member.memberPwd }" name="memberPwd"/>
	                	</div>
	                </td>
	            </tr>
	            <tr class="content">
	                <th class="text-center">
	                                      권한
	                </th>
	                <td>
	                	<select id="comboBox" class="form-control" name="memberLevel" >
						  <option value="USER">USER</option>
						  <option value="USER,SELLER">SELLER</option>
						  <option value="USER,SELLER,ADMIN">ADMIN</option>
						</select>
	                </td>
	            </tr>
	            <tr class="content">
	                <th class="text-center">
	                    	삭제여부
	                </th>
	                <td>
	                 	<div class="mb-3">
	                		<input type="text" name="isDelete" value="${member.isDelete }"/>
	                	</div>
	                </td>
	            </tr>    
	        </table>
</form>
	    </div>
	</div>
	<div class="modal-footer">
	    <button type="submit" form="updateForm" class="btn btn-secondary" data-bs-dismiss="modal" onclick="getSelectedLevel();">Update</button>
	    <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Cancel</button>
	</div>
