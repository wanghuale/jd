<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/dangpu/ticket/">用户列表</a></li>
		<li class="active"><a href="${ctx}/dangpu/ticket/form?id=${user.id}">用户<shiro:hasPermission name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:user:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="ticket" action="${ctx}/dangpu/ticket/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">贷款人:</label>
			<div class="controls">
                <form:input path="name" htmlEscape="false" readonly="readonly" maxlength="20" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证:</label>
			<div class="controls">
                <form:input path="card" htmlEscape="false" readonly="readonly" maxlength="20" class="required card"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号:</label>
			<div class="controls">
                <form:input path="phone" htmlEscape="false" readonly="readonly" maxlength="20" class="required mobile"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">金额:</label>
			<div class="controls">
                <form:input path="money" htmlEscape="false" readonly="readonly" maxlength="20" class="required number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入当日期:</label>
			<div class="controls">
               <input id="startTime" name="startTime" type="text" readonly="readonly" class="input-medium Wdate"
				value="${ticket.startTime}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">利率:</label>
			<div class="controls">
                <form:input path="interest" htmlEscape="false" readonly="readonly" maxlength="20" class="required number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
                <form:textarea path="remark" htmlEscape="false" readonly="readonly" maxlength="20" class="required"/>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>