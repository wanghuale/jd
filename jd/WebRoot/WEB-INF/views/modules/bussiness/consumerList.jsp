<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>服务消费者管理</title>
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
        
          <shiro:hasPermission name="bussiness:consumer:viewkey">
		        function createKey(key){
			         top.$.jBox.open("iframe:${ctx}/bussiness/consumer/createkey?id="+key, "查看密钥", 420, 300, {
						buttons:{"关闭":true}, submit:function(v, h, f){
							 
						}
					});
		        }
    	  </shiro:hasPermission>
	</script>
	<style type="text/css">
	      .desc_cut{
  			overflow:hidden;/* 多出部分隐藏 */
   			white-space:nowrap;/* 不换行 */
   			text-overflow:ellipsis;
        }
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/bussiness/consumer/">服务消费者列表</a></li>
		<shiro:hasPermission name="bussiness:consumer:edit"><li><a href="${ctx}/bussiness/consumer/form">服务消费者添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="consumer" action="${ctx}/bussiness/consumer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>用户名 ：</label><form:input path="username" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>名称 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>描述 ：</label><form:input path="description" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr> 
		<th>用户名</th>
		<th>名称</th>
		<th>IP</th>
		<th>回调地址</th>
		<th>启用</th>
		<th>描述</th>
		<th>操作</th> </tr></thead>
		<tbody>
 		<c:forEach items="${page.list}" var="consumer">
			<tr>
				<td><a href="${ctx}/bussiness/consumer/form?id=${consumer.id}">${consumer.username}</a></td>
				<td>${consumer.name}</td>
				<td>${consumer.ip}</td>
				<td>${consumer.notifyurl}</td>
				<td>${fns:getDictLabel(consumer.isused, 'yes_no', '0')}</td>
				<td><tags:cut length="30" value="${consumer.description}"></tags:cut></td>
				<td>
    			  <a href="${ctx}/bussiness/goodsConsumer/list?consumer.id=${consumer.id}">设置商品</a>
    			   <shiro:hasPermission name="bussiness:consumer:viewkey">
    				<a href="javascript:createKey('${consumer.id}')">查看密钥</a>
    			  </shiro:hasPermission>
    			  <shiro:hasPermission name="bussiness:consumer:edit">
    				<a href="${ctx}/bussiness/consumer/resetpwd?id=${consumer.id}" onclick="return confirmx('确认要重置密码吗？', this.href)">重置密码</a>
    			  </shiro:hasPermission>
				  <shiro:hasPermission name="bussiness:consumer:edit">
    				<a href="${ctx}/bussiness/consumer/form?id=${consumer.id}">修改</a>
    			  </shiro:hasPermission>
    			  <shiro:hasPermission name="bussiness:consumer:edit">
					<a href="${ctx}/bussiness/consumer/delete?id=${consumer.id}" onclick="return confirmx('确认要删除该服务消费者吗？', this.href)">删除</a>
    			  </shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>
