<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">@import url("common.css");</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(function() {
		$('#disp').load('list.do?pageNum=${pageNum}');
	});
</script></head><body>
<table><caption>게시글 상세 보기</caption>
	<tr><th width="100">제목</th><td>${board.subject }</td></tr>
	<tr><th>작성자</th><td>${board.writer }</td></tr>
	<tr><th>조회수</th><td>${board.readcount }</td></tr>
	<tr><th>작성일</th><td>${board.reg_date }</td></tr>
	<tr><th>IP</th><td>${board.ip }</td></tr>
	<tr><th>이메일</th><td>${board.email }</td></tr>
	<tr><th>내용</th><td><pre>${board.content }</pre></td></tr>
</table>
<div align="center"><br>
	<button onclick="location.href='updateForm.do?num=${num}&pageNum=${pageNum}'">수정</button> 
	<button onclick="location.href='deleteForm.do?num=${num}&pageNum=${pageNum}'">삭제</button> 
	<button onclick="location.href='writeForm.do?num=${num}&pageNum=${pageNum}'">답변</button> 
	<button onclick="location.href='list.do?pageNum=${pageNum}'">게시글 목록</button><br> 
</div>
<div id="disp"></div>
</body>
</html>