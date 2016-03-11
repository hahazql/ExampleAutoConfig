<%--
  Created by IntelliJ IDEA.
  User: zql
  Date: 2015/10/15
  Time: 16:00
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
    <link href="assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/main-min.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="assets/js/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
  <form action="<%= basePath %>/config/uploadJar"  method="POST" id="J_Form" class="form" enctype="multipart/form-data">
      <div  class="form-group">
          <!-- data-tip 中声明提示信息，和提示icon -->
          <label for="projectName">项目名：</label>
          <input type="text" id="projectName" name="projectName" data-tip="{text:'项目名必须填写',iconCls:'icon-ok'}" />
      </div>

      <div  class="form-group">
          <label for="projectDesc">项目描述:</label>
          <textarea type="text"  class="form-control"  style="resize: none; " rows=20 id="projectDesc" name="projectDesc" placeholder="项目描述" >
          </textarea>
      </div>
      <div  class="form-group">
          <label for="uploadJar">上传JAR包:</label>
          <input type="file" id="uploadJar" name="file">
          <p class="help-block">提交你的项目JAR包</p>
      </div>
      <div  class="form-group">
          <label for="uploadJar">上传辅助配置文件:</label>
          <input type="file" id="uploadConfigData" name="configData">
          <p class="help-block">提交辅助配置文件</p>
      </div>
      <button type="submit" class="btn btn-default">创建项目</button>
  </form>
</div>
<script type="text/javascript" src="../assets/js/bui-min.js"></script>
<!-- 如果不使用页面内部跳转，则下面 script 标签不需要,同时不需要引入 common/page -->
<script type="text/javascript" src="../assets/js/config-min.js"></script>

<script type="text/javascript">
  BUI.use('bui/form',function (Form) {
    var form = new Form.HForm({
      srcNode : '#J_Form'
    });
    form.render();
  });
</script>

</body>
</html>
