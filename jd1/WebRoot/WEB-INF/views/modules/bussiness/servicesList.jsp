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
		<li class="active"><a href="${ctx}/bussiness/services/">服务列表</a></li>
		<shiro:hasPermission name="bussiness:services:edit"><li><a href="${ctx}/bussiness/services/form">服务添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="services" action="${ctx}/bussiness/services/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	    <label>名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>提供者 ：</label> 
		        <form:select path="provider.id" style="width: 180px;">
		            <form:option value="" label="全部"/>
					<c:forEach var="vc" items="${providers}">
					    <form:option value="${vc.id}" label="${vc.code} ${vc.name }"/>
					</c:forEach>
				</form:select>
		<label>描述 ：</label><form:input path="description" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th>业务类型</th>
		<th>名称</th>
		<th>提供者编号</th>
		<th>提供者名称</th>
		<th>Handler</th>
		<th>启用</th>
		<th>描述</th>
		<shiro:hasPermission name="bussiness:services:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="services">
			<tr>
				<td>${fns:getDictLabel(services.bussinessType, 'bussiness_type', '0')}</td>
				<td>${services.name}</td>
				<td>${services.provider.code}</td>
				<td>${services.provider.name}</td>
				<td> 
			 	   <tags:cut length="50" value="${services.businessHandler}"></tags:cut>
				</td>
				<td>${fns:getDictLabel(services.isused, 'yes_no', '0')}</td>
				<td><tags:cut length="30" value="${services.description}"></tags:cut></td>
				<shiro:hasPermission name="bussiness:services:edit"><td>
    				<a href="${ctx}/bussiness/services/form?id=${services.id}">修改</a>
					<a href="${ctx}/bussiness/services/delete?id=${services.id}" onclick="return confirmx('确认要删除该服务吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
