<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html;charset=UTF-8" language="java" errorPage=""%>
<%request.setCharacterEncoding("utf-8");%>
<!DOCTYPE HTML>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>域名规则管理</title>
</head>
<body>
<div id="global">
    <h1>规则管理平台</h1>
    <a href="./add">添加</a>
    <div>
    	<h4>搜索条件</h4>
    	<form action="./search" id="" method="GET">
    		<input type="checkbox" id="isUnknown" name="empty">只显示未知数据
    		类型：<input type="text" id="type" name="type"/>
    		数目 >= <input type="text" id="count" name="count" value="0"/>
    		<input type="submit" value="搜索" id="submit">
    	</form>
    </div>
    <br />
    <div>
    	<table>
    		<tr>
    			<th>ID</th>
    			<th>域名</th>
    			<th>名称</th>
    			<th>类型</th>
    			<th>数目</th>
    		</tr>
    		
    		<c:forEach items="${lists}" var="com">
    		<tr>
    			<th>${com.id}</th>
    			<th>${com.company}</th>
    			<th>${com.name}</th>
    			<th>${com.type}</th>
    			<th>${com.count}</th>
    			<th><input type="button" value="更改" onclick="window.open('./getOne?id=${com.id}')"></th>
    		</tr>
    		</c:forEach>
    	</table>
    </div>
</div>
</body>
</html>