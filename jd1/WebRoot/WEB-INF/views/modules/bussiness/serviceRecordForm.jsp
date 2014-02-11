<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务交易明细</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/bussiness/serviceRecord/">服务记录列表</a></li>
		<li class="active"><a href="${ctx}/bussiness/serviceRecord/form?id=${serviceRecord.id}">服务记录查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="serviceRecord" action="${ctx}/bussiness/serviceRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">消费者IP:</label>
			<div class="controls">
			    ${serviceRecord.ip}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">操作类型:</label>
			<div class="controls">
			    ${serviceRecord.reService}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">请求报文:</label>
			<div class="controls">
			    <form:textarea path="reData" htmlEscape="false" rows="5"  style="width: 500px;"  maxlength="500" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目标服务:</label>
			<div class="controls">
			  ${serviceRecord.doService.bussinessType}   ${serviceRecord.doService.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">返回报文:</label>
			<div class="controls">
			    <form:textarea path="doData" htmlEscape="false" rows="5"  style="width: 500px;" maxlength="500" class="input-xlarge" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易金额:</label>
			<div class="controls">
			     <fmt:formatNumber value="${serviceRecord.tradeMoney}" pattern="##0.00"></fmt:formatNumber>元
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理状态:</label>
			<div class="controls">
			    <c:choose>
					<c:when test="${serviceRecord.status eq '1'}">成功</c:when>
					<c:when test="${serviceRecord.status eq '2'}">失败</c:when>
					<c:when test="${serviceRecord.status eq '3'}">返回异常</c:when>
				</c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间:</label>
			<div class="controls">
			     <fmt:formatDate value="${serviceRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
			    ${serviceRecord.remarks}
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
