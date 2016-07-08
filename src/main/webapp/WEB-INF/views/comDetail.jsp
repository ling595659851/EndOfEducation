<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html;charset=UTF-8" language="java" errorPage=""%>
<%request.setCharacterEncoding("utf-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>域名规则详情页</title>
</head>
<body>
	<form action="./update" method="POST" id="com">
		<input type="hidden" value="${com.id}" readonly="readonly" name="id"/>
		域名：<input type="text" value="${com.company}" readonly="readonly" name="company"/>
		<br />
		名称：<input type="text" value="${com.name}" name="name"/>
		<br />
		类型：<input type="text" value="${com.type}" name="type"/>
		<br />
		出现次数：<input type="text" value="${com.count}" readonly="readonly" name="count"/>
		<br />
		<input type="submit" value="更改"/>
	</form>
</body>
</html>