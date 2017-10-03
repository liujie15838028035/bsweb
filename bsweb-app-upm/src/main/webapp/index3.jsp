﻿<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/jsp/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <title>UPM权限管理系统</title>
<%@ include file="/jsp/common/meta.jsp" %>
<%@ include file="/jsp/common/resource/scripts_all.jsp" %>
 
 <script   type="text/javascript">
		$(function(){
			//获取权限菜单树
			var jsonData = $.ajax({
				          url:"${ctx}/jsp/permission/upmPermissionAction!findPermissionMenuByUserIdApi.action?appId=UPM&upmPermission.type=MENU",
				          async:false,
				          cache:false,
				          dataType:"text"
			}).responseText;
			
			jsonData = "[" + jsonData + "]";
			
			var dataObj=eval("("+jsonData+")");
	        
			 $('#treediv').treeview({
		            data:dataObj,
		            levels: 5,
		            showIcon: true,  
		            multiSelect: false,
		            highlightSelected: true, //是否高亮选中
		            highlightSearchResults:true,
		            showCheckbox:false,
		            showIcon:true,
		            onNodeSelected: function(event, data) {
		            	 var treeNodeId = data.nodeId;
		            }
		        }
			 );
			 
		});
	</script>
	
 </head>
  
  <body>
    <nav class="navbar navbar-defalut navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle show pull-left" data-target="sidebar">
                    <span class="sr-only">导航菜单</span> <span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="index.jsp">统一用户权限流程管理系统</a>
                 <ul class="nav navbar-nav navbar-right hidden-sm">
		            <li>
		          <a href='#'>${sessionScope.securityContext.loginName}:您好！欢迎登录!&nbsp;&nbsp;</a>
		            </li>
		            <li>
		          <a href="${ctx}/loginAction!logout.action">退出</a>
		            </li>
		          </ul>
            </div>
        </div>
    </nav>
    <div class="container-fluid all">
        <div class="sidebar col-sm-3" id="sidebar">
        	<div id="treediv"></div>
        </div>
        <div class="maincontent row" style="margin-top:60px;">
       		 <div class="col-sm-12 pull-right">
               <ul class="nav nav-tabs" id="tabs">
  				</ul>
  				<div class="tab-content"></div>
            </div>
	    </div>
	    
	    </div>
        
        <script type="text/javascript">
    $(document).ready(function () {
         $('#sidebar').BootSideMenu({
            side: "left",
            pushBody:false,
            closeOnClick:false,
            autoClose:false
        }); 
    });

</script> 
</body>
</html>