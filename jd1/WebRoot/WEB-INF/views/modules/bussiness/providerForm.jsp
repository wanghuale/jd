<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务提供者管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
			    rules: {
					code: {remote: "${ctx}/bussiness/provider/checkUsername?oldUserName=" + encodeURIComponent('${provider.code}')}
				},
				messages: {
					code: {remote: "编号已存在"}
				},
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
		<li><a href="${ctx}/bussiness/provider/">服务提供者列表</a></li>
		<li class="active"><a href="${ctx}/bussiness/provider/form?id=${provider.id}">服务提供者<shiro:hasPermission name="bussiness:provider:edit">${not empty provider.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="bussiness:provider:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="provider" action="${ctx}/bussiness/provider/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">编号:</label>
			<div class="controls">
			    <input id="oldUserName" name="oldUserName" type="hidden" value="${provider.code}">
				<form:input path="code" htmlEscape="false" maxlength="30" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="30" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通讯协议:</label>
			<div class="controls">
				<form:select path="protocol">
					<form:options items="${fns:getDictList('sys_protocol')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">IP:</label>
			<div class="controls">
				<form:input path="ip" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">通讯端口:</label>
			<div class="controls">
				<form:input path="port" htmlEscape="false" maxlength="10" class="required digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">启用:</label>
			<div class="controls">
				<form:radiobuttons path="isused" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="3" maxlength="500" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="bussiness:provider:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
