<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <script src="http://code.jquery.com/jquery-latest.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
          integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
          crossorigin="anonymous"></script>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Bebas+Neue&display=swap" rel="stylesheet">
  <title>Title</title>
  <script type="text/javascript" src="<c:url value="/resources/ckeditor/ckeditor.js"/>"></script>
  <script type="text/javascript">
    $(document).ready(function (e) {
      let optionSetting = ${item.itemOptions.size()};
      CKEDITOR.replace("content", {
        uploadUrl: "/file/img/dropUpload"
      }); // 편집기

      function getOptionForm() {
        let result = "<div style='display: inline-block; margin-bottom: 5px' name='option" + optionSetting + "'>";
        result += "사이즈<input type='text' name='itemOptions[" + optionSetting + "].optionName'/> ";
        result += "&nbsp;보유 수량<input onkeyPress='javascript:checkInputNum();' type='number' name='itemOptions[" + optionSetting + "].optionStock'/> ";
        result += "&nbsp;변동액<input onkeyPress='javascript:checkInputNum();'  type='number' name='itemOptions[" + optionSetting + "].optionPriceUd'/> ";
        result += "<input type='hidden' name='itemOptions["+optionSetting+"].isDelete' value='0'>"
        result += "<button class='btn btn-danger btn-sm' type='button' name='deleteOption_"+optionSetting+"'>삭제</button>"
        result += "</div>"
        $("#options").append(result);
        optionSetting += 1;
      }

      $("#createOption").click(function () {
        getOptionForm();
      });

      $(document).on("click","[name^='deleteOption']",function() {
        let optionDiv = $(this).closest("div");
        let divId = optionDiv.attr("name");
        if (divId === 'oldOption'){
        let attr = $(this).attr("name");
        let selectedOption = attr.split("_")[1];
        $("input[name='itemOptions["+selectedOption+"].isDelete']").val('1');
        optionDiv.hide();
        }else {
          optionDiv.remove();
        }
      });

      $(document).on("click","[id='deleteItem']",function() {
        let select = confirm("상품을 삭제하시겠습니까?");
        if(select===true){
        location.href = "/item/${item.itemCode}/delete"
        }
      });

      $(document).on("click","[id='backMain']",function() {
        location.href = "/item"
      });
    });
    function checkInputNum(){
      if ((event.keyCode < 48) || (event.keyCode > 57)){
        event.returnValue = false;
      }
    }
  </script>
</head>
<body class="pt-5">
<div class="container">
  <div class="text-center">
    <h3>상품 수정하기</h3>
  </div>
  <form action="/item/${item.itemCode}/update" method="post" enctype="multipart/form-data" id="addForm">

    <div class="mb-3">
      <div class="mb-3 h-25" style="text-align: center">
        <img src="${item.itemImage}" style="height: 50%; width: 50%; object-fit: cover;" alt="">
      </div>
      <input class="form-control" type="file" name="file" id="file" >
    </div>
    <div class="mb-3">
      <label for="itemName">이름</label>
      <input class="form-control" type="text" name="itemName" id="itemName" value="${item.itemName}" readonly required>
    </div>
    <div class="mb-3">
      <div class="mb-3 border rounded" id="options" style="padding: 5px">
      <button class="btn btn-secondary btn-sm" type="button" id="createOption" style="margin-bottom: 5px">옵션 생성하기</button>
        <br >
        <c:set var="i" value="${0}"/>

        <c:forEach items="${item.itemOptions}" var="itemOption">
          <div style='display: inline-block; margin-bottom: 5px' name='oldOption' id='${itemOption.optionCode}'>
            사이즈<input type='text' value="${itemOption.optionName}" name='itemOptions[${i}].optionName'/>
            &nbsp;보유 수량<input onkeyPress='javascript:checkInputNum();' value="${itemOption.optionStock}" type='number' name='itemOptions[${i}].optionStock'/>
            &nbsp;변동액<input onkeyPress='javascript:checkInputNum();' value="${itemOption.optionPriceUd}"  type='number' name='itemOptions[${i}].optionPriceUd'/>
            <input type='hidden' name='itemOptions[${i}].isDelete' value='${itemOption.isDelete}'>
            <button class='btn btn-danger btn-sm' type='button' name='deleteOption_${i}'>삭제</button>
            <c:set var="i" value="${i+1}"/>
          </div>
        </c:forEach>
      </div>
    </div>
    <div class="mb-3">
      <label for="countryCode">국가코드</label>
      <select class="form-control" name="countryCode" id="countryCode" required>
        <option value="${item.country.countryCode}" selected>${item.country.countryName}</option>
      </select>
    </div>
    <div class="mb-3">
      <label for="itemBrand">브랜드</label>
      <input class="form-control" type="text" name="itemBrand" id="itemBrand" value="${item.itemBrand}" readonly>
    </div>
    <div class="mb-3">
      <label for="itemPrice">가격</label>
      <input class="form-control" onkeyPress="javascript:checkInputNum();"  type="number" name="itemPrice" id="itemPrice" value="${item.itemPrice}"
             required>
    </div>
    <div class="mb-3">
      <label for="content">상세 보기</label>
      <textarea name="itemInfo" id="content" style="width:100%;" required>${item.itemInfo}</textarea>
    </div>
    <input class="btn btn-primary btn-sm" type="submit" value="수정하기">
    <button id="deleteItem" class="btn btn-danger btn-sm" type="button">삭제</button>
    <button id="backMain" class="btn btn-secondary btn-sm" type="button">취소</button>
  </form>
</div>
</body>
</html>
