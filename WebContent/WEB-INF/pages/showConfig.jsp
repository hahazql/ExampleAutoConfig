<%--
  Created by IntelliJ IDEA.
  User: zql
  Date: 2015/10/19
  Time: 18:14
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
  <title>配置列表</title>
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
        <th>配置ID</th>
        <th>配置对应的类</th>
        <th>配置名</th>
        <th>配置描述</th>
        <th></th>
        <th></th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="listValue" items="${configList}">
        <tr>
          <td>${listValue.id}</td>
          <td>${listValue.clazzName}</td>
          <td>${listValue.name}</td>
          <td>${listValue.autoConfig.configDesc}</td>
          <td><a href="<%= basePath %>/config/uploadConfig/${listValue.id}">上传配置</a></td>
          <td><a href="<%= basePath %>/config/downFileChose/${listValue.id}">下载配置</a></td>
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
