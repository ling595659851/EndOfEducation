<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html;charset=UTF-8" language="java" errorPage=""%>
<%request.setCharacterEncoding("utf-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>类型规则详情页</title>
</head>
<body>
	<form action="./update" method="POST" id="type">
		<input type="hidden" value="" name="id"/>
		类型：<input type="text" value="" name="name"/>
		<br />
		名称：<input type="text" value="" name="value"/>
		<br />
		类型：<input type="text" value="" name="type"/>
		<br />
		<input type="submit" value="提交"/>
	</form>
</body>
</html>