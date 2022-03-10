<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div class="modal-header">
    <h5 class="modal-title" id="historyModalLabel">${seller.memberId}</h5>
    <button class="close" type="button" data-bs-dismiss="modal" aria-label="Close">
        <span aria-hidden="true">×</span>
    </button>
</div>
<div class="modal-body">
            <form action="/seller/${seller.memberId}/update" method="post">
    <div class="table-responsive">
        <div class="container">

            <label for="businessName">businessName</label
            ><input type="text" id="businessName" name="businessName" value="${seller.businessName}"><br>
            <label for="businessInfo">businessInfo</label>
            <textarea rows="5" cols="50" id="businessInfo" name="businessInfo">${seller.businessInfo}</textarea>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn btn-secondary" type="submit">변경하기</button>
        <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Cancel</button>
    </div>
            </form>
</div>