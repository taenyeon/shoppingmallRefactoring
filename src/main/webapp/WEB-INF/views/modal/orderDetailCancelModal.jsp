<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<script>
    $(function () {
        $(document).on("click", "#refundStart", function () {
            $.ajax({
                url : "/orderDetail/${orderDetail.orderDetailCode}/cancel",
                method : "POST",
                success : function (){
                    alert("환불이 정상처리 되었습니다.")
                    location.href = "/order/myOrder"
                }
            })
        });
    });
</script>
<div class="modal-header">
    <h5 class="modal-title" id="historyModalLabel">${orderDetail.itemOption.item.itemName}</h5>
    <button class="close btn btn-secondary" type="button" data-bs-dismiss="modal" aria-label="Close">x
    </button>
</div>
<div class="modal-body">
        <div class="table-responsive">
            <div class="container">
                <p>환불금액</p>
                <span><b>${orderDetail.amount * orderDetail.itemOption.item.itemPrice}</b>원</span>
            </div>
        </div>
        <div class="modal-footer">
            <button class="btn btn-secondary" id="refundStart" type="button">환불하기</button>
            <button class="btn btn-secondary" type="button" data-bs-dismiss="modal">Cancel</button>
        </div>
</div>