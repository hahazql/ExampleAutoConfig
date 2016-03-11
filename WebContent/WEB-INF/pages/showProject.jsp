<%@ page import="model.bean.ProjectData" %>
<%--
  Created by IntelliJ IDEA.
  User: zql
  Date: 2015/10/15
  Time: 16:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
  <title>项目情况</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script type="text/javascript" src="assets/js/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="table-responsive">
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th>项目ID</th>
                <th>项目名</th>
                <th>项目描述</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="listValue" items="${ProjectList}">
                    <tr>
                        <td>${listValue.id}</td>
                        <td>${listValue.name}</td>
                        <td>${listValue.projectDesc}</td>
                        <td><a href="<%= basePath %>/config/showConfigs/${listValue.id}">显示配置列表</a></td>
                        <td><a href="<%= basePath %>/config/updateJarShow/${listValue.id}">更新JAR包</a></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
<script type="text/javascript" src="../assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="../assets/js/bui-min.js"></script>

<script type="text/javascript" src="../assets/js/config-min.js"></script>
<script type="text/javascript">
  BUI.use('common/page');
</script>

<script type="text/javascript">
  //此处实现 js 代码
</script>

</body>
</html>

