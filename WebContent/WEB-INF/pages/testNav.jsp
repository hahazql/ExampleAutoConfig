<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>King5管理系统</title>
  <link href="assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
  <link href="assets/css/bui-min.css" rel="stylesheet" type="text/css" />
  <link href="assets/css/main-min.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="header">

  <div class="dl-title">
    <a href="http://sc.chinaz.com" title="文档库地址" target="_blank"><!-- 仅仅为了提供文档的快速入口，项目中请删除链接 -->
      <span class="lp-title-port">King5</span><span class="dl-title-text">管理后台</span>
    </a>
  </div>

  <div class="dl-log">欢迎您，<span class="dl-log-user">**.**@alibaba-inc.com</span><a href="###" title="退出系统" class="dl-log-quit">[退出]</a><a href="http://http://sc.chinaz.com/" title="文档库" class="dl-log-quit">文档库</a>
  </div>
</div>
<div class="content">
  <div class="dl-main-nav">
    <div class="dl-inform"><div class="dl-inform-title">贴心小秘书<s class="dl-inform-icon dl-up"></s></div></div>
    <ul id="J_Nav"  class="nav-list ks-clear">
      <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">首页</div></li>
      <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">你猜</div></li>
    </ul>
  </div>
  <ul id="J_NavContent" class="dl-tab-conten">

  </ul>
</div>
<script type="text/javascript" src="assets/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="assets/js/bui.js"></script>
<script type="text/javascript" src="assets/js/config.js"></script>

<script>
  BUI.use('common/main',function(){
    //获取json
    $.getJSON('/nav',function(data){

      new PageUtil.MainPage({
        modulesConfig : data
      });

    });
  });
</script>
</body>
</html>