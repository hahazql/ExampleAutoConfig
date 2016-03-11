<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<html>
<head>
  <title>更新项目</title>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="../assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/main-min.css" rel="stylesheet" type="text/css" />
    <link href="../assets/css/bootstrap.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="../assets/js/jquery-1.8.1.min.js"></script>
    <script type="text/javascript" src="../assets/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
  <form action="<%=basePath%>/config/updateJar"  method="POST" id="J_Form" class="form" enctype="multipart/form-data">
      <div  class="form-group">
          <!-- data-tip 中声明提示信息，和提示icon -->
          <label for="projectID">项目ID：</label>
          <input type="text" id="projectID" name="projectID" value="${id}"  readonly/>
      </div>

      <div  class="form-group">
          <label for="projectName">项目名:</label>
          <input type="text" value="${name}" readonly>
          </textarea>
      </div>
      <div  class="form-group">
          <label for="uploadJar">上传JAR包:</label>
          <input type="file" id="uploadJar" name="jarFile">
          <p class="help-block">提交你的项目JAR包</p>
      </div>
      <div  class="form-group">
          <label for="uploadJar">上传辅助配置文件:</label>
          <input type="file" id="uploadConfigData" name="configDataFile">
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