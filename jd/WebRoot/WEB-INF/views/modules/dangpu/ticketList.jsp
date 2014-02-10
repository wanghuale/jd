<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			// 表格排序
			var orderBy = $("#orderBy").val().split(" ");
			$("#contentTable th.sort").each(function(){
				if ($(this).hasClass(orderBy[0])){
					orderBy[1] = orderBy[1]&&orderBy[1].toUpperCase()=="DESC"?"down":"up";
					$(this).html($(this).html()+" <i class=\"icon icon-arrow-"+orderBy[1]+"\"></i>");
				}
			});
			$("#contentTable th.sort").click(function(){
				var order = $(this).attr("class").split(" ");
				var sort = $("#orderBy").val().split(" ");
				for(var i=0; i<order.length; i++){
					if (order[i] == "sort"){order = order[i+1]; break;}
				}
				if (order == sort[0]){
					sort = (sort[1]&&sort[1].toUpperCase()=="DESC"?"ASC":"DESC");
					$("#orderBy").val(order+" DESC"!=order+" "+sort?"":order+" "+sort);
				}else{
					$("#orderBy").val(order+" ASC");
				}
				page();
			});
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/dangpu/ticket/");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			style="padding-left:20px;text-align:center;"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/dangpu/ticket/">当票列表</a></li>
		<shiro:hasPermission name="dangpu:ticket:add"><li><a href="${ctx}/dangpu/ticket/form">添加当票</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ticket" action="${ctx}/dangpu/ticket/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<div style="margin-top:8px;">
			<label>贷款人：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
			<label>录入人：</label><form:input path="user.name" htmlEscape="false" maxlength="50" class="input-medium"/>
			<label>&#12288;状态：</label>
				<form:select path="status" cssStyle="width: 180px;">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('pawn_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
		</div>
		<div style="margin-top:8px;">
			
			<label>贷款日期：</label>
				<input id="searchStartTime" name="searchStartTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				 value="${ticket.searchStartTimeStr}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,maxDate:'#F{$dp.$D(\'searchEndTime\')}'});"/>
			     至<input id="searchEndTime" name="searchEndTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
			     value="${ticket.searchEndTimeStr}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,minDate:'#F{$dp.$D(\'searchStartTime\')}'});"/>
			&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			<shiro:hasPermission name="dangpu:ticket1:edit">
			&nbsp;<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
			&nbsp;<input id="btnImport" class="btn btn-primary" type="button" value="导入"/>
			</shiro:hasPermission>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>报警</th>
				<th>贷款人</th>
				<th>身份证</th>
				<th>手机号</th>
				<th>贷款金额</th>
				<th>贷款日期</th>
				<th>赎当日期</th>
				<th>利率</th>
				<th>利息</th>
				<th>坏账金</th>
				<th>状态</th>
				<th>录入员</th>
				<!-- <th>贷款实际日期</th>
				<th>赎当实际日期</th> -->
				<th>预警时间</th>
				<th>备注</th>
				<shiro:hasPermission name="dangpu:ticket:edit">
				<th>操作</th></shiro:hasPermission>
			</tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="ticket">
			<tr>
				<td>
				<c:choose>  
					<c:when test="${ticket.warnFlag eq '1'}">
						<img alt="赎当日期不足3天" style="margin: 0px 0px 0px 10px;" src="${ctxStatic}/images/yellow.gif" height="5">
					</c:when>
					<c:when test="${ticket.warnFlag eq '2'}">
						<img alt="赎当日期不足3天" style="margin: 0px 0px 0px 10px;" src="${ctxStatic}/images/red.gif" height="5">
					</c:when>
					<c:otherwise>
						<span style="margin: 0px 0px 0px 10px;">无</span>
						<%-- <img alt="赎当日期不足3天" style="margin: 0px 0px 0px 10px;" src="${ctxStatic}/images/yellow.gif" height="5"> --%>
					</c:otherwise>
				</c:choose>
				</td>
				<td>${ticket.name}</td>
				<td>${ticket.card}</td>
				<td>${ticket.phone}</td>
				<td>${ticket.money}</td>
				<td>${ticket.startTimeStr}</td>
				<c:choose>  
					<c:when test="${ticket.endTime != null}">
						<td>${ticket.endTimeStr}</td>
					</c:when>
					<c:otherwise>
						<td>--</td>
					</c:otherwise>
				</c:choose>
				<td>${ticket.interest}</td>
				<c:choose>  
					<c:when test="${ticket.profit != null}">
						<td>${ticket.profit}</td>
					</c:when>
					<c:otherwise>
						<td>--</td>
					</c:otherwise>
				</c:choose>
				<c:choose>  
					<c:when test="${ticket.badDebt != null}">
						<td>${ticket.badDebt}</td>
					</c:when>
					<c:otherwise>
						<td>--</td>
					</c:otherwise>
				</c:choose>
				<td>${fns:getDictLabel(ticket.status, 'pawn_status', '0')}</td>
				<td>${ticket.user.name}</td>
				<%-- <td>${ticket.createStartTime}</td>
				<td>${ticket.createEndTime}</td> --%>
				<c:choose>  
					<c:when test="${ticket.warnTimeStr eq ''}">
						<td>--</td>
					</c:when>
					<c:otherwise>
						<td>${ticket.warnTimeStr}</td>
					</c:otherwise>
				</c:choose>
				<td>${ticket.remark}</td>
				<td>
				<a href="${ctx}/dangpu/ticket/detail?id=${ticket.id}">详情</a>
					
					<c:if test="${ticket.status == 0}">
						<a href="${ctx}/dangpu/ticket/form?id=${ticket.id}">修改</a>
						<a href="${ctx}/dangpu/ticket/redeemForm?id=${ticket.id}">赎当</a>
					</c:if>
					<shiro:hasPermission name="dangpu:ticket:edit">
					<c:if test="${ticket.status != 0}">
						<a href="${ctx}/dangpu/ticket/form?id=${ticket.id}">修改</a>
					</c:if>
					</shiro:hasPermission>
					<a href="${ctx}/dangpu/ticket/delete?id=${ticket.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
					
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>