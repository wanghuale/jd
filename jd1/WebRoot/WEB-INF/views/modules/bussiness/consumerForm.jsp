<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务消费者管理</title>
	<meta name="decorator" content="default"/>
		<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#username").focus();
			$("#inputForm").validate({
			    rules: {
					username: {remote: "${ctx}/bussiness/consumer/checkUsername?oldUserName=" + encodeURIComponent('${consumer.username}')}
				},
				messages: {
					username: {remote: "用户登录名已存在"}
				},
				submitHandler: function(form){
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#servicesIds").val(ids);
					
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			

			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{
					   beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					   }, 
					   onCheck:function(id, node){
					    var arry=new Array();
					    var nodes = tree.getCheckedNodes(true);
					    for(var i=0;i<nodes.length; i++){
					      if(nodes[i].id>0){
						      for(var ari=0;ari<arry.length;ari++){
						        if(arry[ari]==nodes[i].name.substring(0,3)){
						           alert(arry[ari]+"业务类型只能开通一个接口!");
						           tree.checkNode(nodes[i], false, true, true);
						        }
						      }
						      arry.push(nodes[i].name.substring(0,3))
					      }
					    }
						return true;
					   }
					}};
					
					
					
			
			// 用户-菜单
			var zNodes=[   
			                <c:forEach items="${providers}" var="provider">
							    {id:-${provider.id}, pId:0, name:"${provider.name}"},
					        </c:forEach>
							<c:forEach items="${serviceList}" var="service">
							    {id:${service.id}, pId:-${service.provider.id}, name:"${service.bussinessType} ${service.name}" },
				            </c:forEach>
			            ];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#servicesTree"), setting, zNodes);
			// 默认选择节点
			var ids = "${consumer.servicesIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, true);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(true);

			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/bussiness/consumer/">服务消费者列表</a></li>
		<li class="active"><a href="${ctx}/bussiness/consumer/form?id=${consumer.id}">服务消费者<shiro:hasPermission name="bussiness:consumer:edit">${not empty consumer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="bussiness:consumer:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="consumer" action="${ctx}/bussiness/consumer/save" method="post" class="form-horizontal">
		<form:hidden path="id" />
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">用户名:</label>
			<div class="controls">
			    <input id="oldUserName" name="oldUserName" type="hidden" value="${consumer.username}">
				<form:input path="username" htmlEscape="false" maxlength="20" class="required username userName"/>(初始密码888888)
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="40" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">IP:</label>
			<div class="controls">
				<form:input path="ip" htmlEscape="false" maxlength="50" class="required"/>
		    </div>
		</div>
		<div class="control-group">
			<label class="control-label">启用:</label>
			<div class="controls">
				<form:radiobuttons path="isused" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回调地址:</label>
			<div class="controls">
				<form:input path="notifyurl" htmlEscape="false" maxlength="1000" class="required"/>
		    </div>
		</div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="3" maxlength="500" class="input-xlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">服务授权:</label>
			<div class="controls">业务类型/接口名称
				<div id="servicesTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<form:hidden path="servicesIds"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="bussiness:consumer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
