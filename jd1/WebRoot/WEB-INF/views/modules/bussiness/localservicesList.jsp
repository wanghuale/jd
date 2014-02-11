<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>本地服务管理</title>
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
		<li class="active"><a href="${ctx}/bussiness/localservices/">本地服务列表</a></li>
		<shiro:hasPermission name="bussiness:localservices:edit"><li><a href="${ctx}/bussiness/localservices/form">本地服务添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="localservices" action="${ctx}/bussiness/localservices/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>编号 ：</label><form:input path="code" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>描述 ：</label><form:input path="description" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		<th>编号</th>
		<th>名称</th>
		<th>协议类型 </th>
		<th>端口</th>
		<th>URI</th>
		<th>是否启用</th>
		<th>描述</th>
		<shiro:hasPermission name="bussiness:localservices:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="localservices">
			<tr>
				<td><a href="${ctx}/bussiness/localservices/form?id=${localservices.id}">${localservices.code}</a></td>
				<td>${localservices.name}</td>
				<td>${fns:getDictLabel(localservices.protocol, 'sys_protocol', '0')}</td>
				<td>${localservices.port}</td>
				<td> 
				  <tags:cut length="50" value="${localservices.uri}"></tags:cut>
				</td>
				<td>${fns:getDictLabel(localservices.isused, 'yes_no', '0')}</td>
				<td><tags:cut length="30" value="${localservices.description}"></tags:cut></td>
				<shiro:hasPermission name="bussiness:localservices:edit"><td>
    				<a href="${ctx}/bussiness/localservices/form?id=${localservices.id}">修改</a>
					<a href="${ctx}/bussiness/localservices/delete?id=${localservices.id}" onclick="return confirmx('确认要删除该本地服务吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
