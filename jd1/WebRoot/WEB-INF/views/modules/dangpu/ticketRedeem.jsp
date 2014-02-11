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
		<li class="active"><a href="${ctx}/dangpu/ticket/redeemForm?id=${ticket.id}">赎当</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="ticket" action="${ctx}/dangpu/ticket/redeem" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">贷款人:</label>
			<div class="controls">
                <form:input path="name" htmlEscape="false" maxlength="20" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证:</label>
			<div class="controls">
                <form:input path="card" htmlEscape="false" maxlength="20" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号:</label>
			<div class="controls">
                <form:input path="phone" htmlEscape="false" maxlength="20"  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">金额:</label>
			<div class="controls">
                <form:input path="money" htmlEscape="false" maxlength="20"  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">入当日期:</label>
			<div class="controls">
               <input id="startTime" name="startTime" type="text" readonly="readonly" class="input-medium Wdate"
				value="${ticket.startTimeStr}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">赎当日期:</label>
			<div class="controls">
                <input id="endTime" name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'startTime\')}'});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">利率:</label>
			<div class="controls">
                <form:input path="interest" htmlEscape="false" maxlength="20"  readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
                <form:textarea path="remark" htmlEscape="false" maxlength="20" readonly="true"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="dangpu:ticket:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>