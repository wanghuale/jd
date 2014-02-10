<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润管理</title>
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
        
        function resetMethod(){
	 		$("#searchForm")[0].reset();
		}
		/*
        function resetMethod(){
           window.location.src="${ctx}/operation/profitSet/";
        }
        */
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/operation/profitSet/">分润列表</a></li>
		<shiro:hasPermission name="operation:profitSet:edit"><li><a href="${ctx}/operation/profitSet/form">分润添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="profitSet" action="${ctx}/operation/profitSet/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>业务类型 ：</label> 
			    <form:select path="businessType" class="input-medium" id="businessType">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('bussiness_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
	    <label>分润方式 ：</label> 
			    <form:select path="distriType" class="input-medium" id="distriType">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('distri_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
&nbsp;<input id="btnExport" class="btn btn-primary" type="button" value="重置" onclick="resetMethod();"/>		
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>业务类型</th>
				<th>分润方式</th>
				<shiro:hasPermission name="operation:profitSet:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="profitSet">
				<tr>
					<td>
					  ${fns:getDictLabel(profitSet.businessType, 'bussiness_type',profitSet.businessType)}
					</td>
					<td> 
					    ${fns:getDictLabel(profitSet.distriType, 'distri_type',profitSet.distriType)}
					</td>
					<shiro:hasPermission name="operation:profitSet:edit">
						<td><a
							href="${ctx}/operation/profitSet/form?id=${profitSet.id}">修改</a>
							<a href="${ctx}/operation/profitSet/delete?id=${profitSet.id}"
							onclick="return confirmx('确认要删除该分润 吗？', this.href)">删除</a></td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
