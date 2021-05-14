<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html><html><head><meta charset="UTF-8">
<title>Insert title here</title></head><body>
<c:if test="${result > 0 }">
	<script type="text/javascript">
		alert("게시글이 수정 되었습니다");
		location.href="list.do?pageNum=${pageNum}";
	</script>
</c:if>
<c:if test="${result == 0 }">
	<script type="text/javascript">
		alert("게시글 수정이 실패되었습니다");
		history.go(-1);
	</script>
</c:if>
<c:if test="${result < 0 }">
	<script type="text/javascript">
		alert("암호가 다릅니다");
		history.go(-1);
	</script>
</c:if>
</body>
</html>