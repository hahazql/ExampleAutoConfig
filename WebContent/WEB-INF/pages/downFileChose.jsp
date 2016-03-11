<%--
  Created by IntelliJ IDEA.
  User: zql
  Date: 2015/10/19
  Time: 18:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
  <title>创建项目</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <link href="../assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
  <link href="../assets/css/bui-min.css" rel="stylesheet" type="text/css" />
  <link href="../assets/css/main-min.css" rel="stylesheet" type="text/css" />
  <link href="../assets/css/bootstrap.css" rel="stylesheet" type="text/css"/>
  <script type="text/javascript" src="assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
  <form action="<%= basePath %>/config/downFile"  method="GET" id="J_Form" class="form">
    <div class="form-group">
    <label for="configID">ID：</label>
    <input type="text" id="configID" name="id" value="${ID}" readonly>
    </div>

    <div class="radio">
      <label>
        <input type="radio" name="type" id="optionsRadios2" value="1" >
        EXCEL配置
      </label>
    </div>
    <div class="radio">
      <label>
        <input type="radio" name="type" id="optionsRadios1" value="2" checked>
        XML配置
      </label>
    </div>
    <button type="submit" class="btn btn-default">下载配置</button>
  </form>
</div>
<script type="text/javascript" src="../assets/js/bui-min.js"></script>
<!-- 如果不使用页面内部跳转，则下面 script 标签不需要,同时不需要引入 common/page -->
<script type="text/javascript" src="../assets/js/config-min.js"></script>

<script type="text/javascript">
  BUI.use('common/page'); //页面链接跳转

  BUI.use('bui/form',function (Form) {
    var form = new Form.HForm({
      srcNode : '#J_Form'
    });
    form.render();
  });
</script>

</body>
</html>