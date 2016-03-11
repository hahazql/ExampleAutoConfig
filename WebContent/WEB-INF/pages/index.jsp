<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>${project}</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="assets/css/main-min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="assets/js/jquery-1.8.1.min.js"></script>
</head>
<body>

<div class="header">

    <div class="dl-title">
            <span class="lp-title-port">${project}</span><span class="dl-title-text"></span>
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
            <li class="nav-item"><div class="nav-item-inner nav-order">配置管理</div></li>
            <li class="nav-item"><div class="nav-item-inner nav-inventory">搜索页</div></li>
            <li class="nav-item"><div class="nav-item-inner nav-supplier">详情页</div></li>
            <li class="nav-item"><div class="nav-item-inner nav-marketing">图表</div></li>
        </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>
</div>
<script type="text/javascript" src="./assets/js/bui.js"></script>
<script type="text/javascript" src="./assets/js/config.js"></script>

<script>
    BUI.use('common/main',function(){
        var config = [{
            id:'menu',
            homePage : 'code',
            menu:[{
                text:'首页内容',
                items:[
                    {id:'code',text:'首页代码',href:'main/code.html',closeable : false},
                    {id:'main-menu',text:'顶部导航',href:'main/menu.html'},
                    {id:'second-menu',text:'右边菜单',href:'main/second-menu.html'},
                    {id:'dyna-menu',text:'动态菜单',href:'main/dyna-menu.html'}
                ]
            },{
                text:'页面操作',
                items:[
                    {id:'operation',text:'页面常见操作',href:'main/operation.html'},
                    {id:'quick',text:'页面操作快捷方式',href:'main/quick.html'}
                ]
            },{
                text:'文件结构',
                items:[
                    {id:'resource',text:'资源文件结构',href:'main/resource.html'},
                    {id:'loader',text:'引入JS方式',href:'main/loader.html'}
                ]
            }]
        },{
            id:'config',
            menu:[{
                text:'配置项目管理',
                items:[
                    {id:'showProject',text:'显示项目情况',href:'config/showProject'},
                    {id:'createProject',text:'创建项目',href:'config/createProject'},
                ]
            },{
                text:'配置管理',
                items:[
                    {id:'showConfig',text:'配置显示',href:'config/showConfig'},
                ]
            }]
        },{
            id:'search',
            menu:[{
                text:'搜索页面',
                items:[
                    {id:'code',text:'搜索页面代码',href:'search/code.html'},
                    {id:'example',text:'搜索页面示例',href:'search/example.html'},
                    {id:'example-dialog',text:'搜索页面编辑示例',href:'search/example-dialog.html'},
                    {id:'introduce',text:'搜索页面简介',href:'search/introduce.html'},
                    {id:'config',text:'搜索配置',href:'search/config.html'}
                ]
            },{
                text : '更多示例',
                items : [
                    {id : 'tab',text : '使用tab过滤',href : 'search/tab.html'}
                ]
            }]
        },{
            id:'detail',
            menu:[{
                text:'详情页面',
                items:[
                    {id:'code',text:'详情页面代码',href:'detail/code.html'},
                    {id:'example',text:'详情页面示例',href:'detail/example.html'},
                    {id:'introduce',text:'详情页面简介',href:'detail/introduce.html'}
                ]
            }]
        },{
            id : 'chart',
            menu : [{
                text : '图表',
                items:[
                    {id:'code',text:'引入代码',href:'chart/code.html'},
                    {id:'line',text:'折线图',href:'chart/line.html'},
                    {id:'area',text:'区域图',href:'chart/area.html'},
                    {id:'column',text:'柱状图',href:'chart/column.html'},
                    {id:'pie',text:'饼图',href:'chart/pie.html'},
                    {id:'radar',text:'雷达图',href:'chart/radar.html'}
                ]
            }]
        }];
        new PageUtil.MainPage({
            modulesConfig : config
        });
    });
</script>
<div style="display:none"><script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script></div>
</body>
</html>