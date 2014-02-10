<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/bussiness/serviceRecord/">服务记录列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="serviceRecord" action="${ctx}/bussiness/serviceRecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>IP ：</label><form:input path="ip" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
		<th>消费者IP</th>
		<th>操作类型</th>
		<th>目标服务</th>
		<th>处理状态</th>
		<th>创建时间</th>
		<shiro:hasPermission name="bussiness:serviceRecord:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="serviceRecord">
			<tr>
				<td><a href="${ctx}/bussiness/serviceRecord/form?id=${serviceRecord.id}">${serviceRecord.ip}</a></td>
				<td>${serviceRecord.reService}</td>
				<td>${serviceRecord.doService.bussinessType} ${serviceRecord.doService.name}</td>
				<td>
				<c:choose>
					<c:when test="${serviceRecord.status eq '1'}">成功</c:when>
					<c:when test="${serviceRecord.status eq '2'}">失败</c:when>
					<c:when test="${serviceRecord.status eq '3'}">返回异常</c:when>
				</c:choose>
				</td>
				<td> 
				   <fmt:formatDate value="${serviceRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> 
				</td>
				<shiro:hasPermission name="bussiness:serviceRecord:edit">
					<td>
	    				<a href="${ctx}/bussiness/serviceRecord/form?id=${serviceRecord.id}">查看详情</a>
					</td>
				</shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
