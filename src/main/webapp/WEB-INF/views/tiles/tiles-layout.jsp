<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- 공통변수 처리 -->
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title><tiles:insertAttribute name="title" /></title>
    <tiles:insertAttribute name="header" />
</head>
<body>
<div class='wrap'>
    <div class='content'>
        <tiles:insertAttribute name="nav"/>
        <div class="page_content pt-5">
            <tiles:insertAttribute name="body"/>
        </div>
    </div>
    <tiles:insertAttribute name="foot" />
</div>
</body>

</html>