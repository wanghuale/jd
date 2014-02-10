<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		$("#searchForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox"
			});
     	 $("#btnSubmit").click(function(){
					$("#searchForm").attr("action","${ctx}/bussiness/orderRecord/");
						$("#searchForm").submit();
			});
        
        
     	$("#btnExport").click(function(){
			top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
				if(v=="ok"){
					$("#searchForm").attr("action","${ctx}/bussiness/export");
					$("#searchForm").submit();
				}
			},{buttonsFocus:1});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		});
		
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/bussiness/orderRecord/");
			$("#searchForm").submit();
        	return false;
        }
        
 
		
	</script>
	
	<style type="text/css">
	.input-medium {
		width: 90px;
	}
	 .input100{width:100px;}
	 select {width: 100px;}
	 .Wdate{width:170px;}
	 table td{word-break: keep-all;}
	 table td{word-break: keep-all;white-space:nowrap;}
	 .tdright{text-align: right!important;}
	 th{text-align: center}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/bussiness/orderRecord/">订单列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="orderRecord" action="${ctx}/bussiness/orderRecord/export" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<div style="line-height: 40px">
			<label>本站订单号 ：</label><input name="did" type="text" htmlEscape="false" maxlength="50" class="input-medium digits" value="${did}"/>
			<label>产品编号：</label><form:input path="productid" htmlEscape="false" maxlength="100" class="input-medium"/>
			<label>业务类型：</label>
			<form:select path="service.bussinessType" cssStyle="width: 180px;">
				<form:option value="" label=""/>
					<form:options items="${fns:getDictList('bussiness_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<%-- <form:select path="service.bussinessType">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select> --%>
				<!-- <form:input path="service.name" htmlEscape="false" maxlength="100" class="input-medium"/> -->
			<label>提供者：</label>
			<form:select path="provider.name" cssStyle="width: 180px;">
					<form:option value="" label="全部"/>
					<c:forEach items="${plist}" var="a">
					<form:option value="${a.name}" label="${a.name}"/>
					</c:forEach>
			</form:select>
			<label>提供者订单号：</label><form:input path="orderId" htmlEscape="false" maxlength="50" class="input-medium"/>
			</br>
			<label>外部订单号 ：</label><form:input path="payOrderId" htmlEscape="false" maxlength="50" class="input-medium"/>
			<label>产品名称：</label><form:input path="productname" htmlEscape="false" maxlength="100" class="input-medium"/>
			<label>&#12288;消费者：</label>
			<form:select path="consumer.name" cssStyle="width: 180px;">
					<form:option value="" label="全部"/>
					<c:forEach items="${clist}" var="a">
					<form:option value="${a.name}" label="${a.name}"/>
					</c:forEach>
			</form:select>
			<label>&#12288;状态：</label>
				<form:select path="status" cssStyle="width: 180px;">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('order_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<label>&#12288;&#12288;顾客信息：</label><form:input path="dispaly" htmlEscape="false" maxlength="100" class="input-medium"/>
				</br>
				<label>交易时间&#12288;：&nbsp;</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				value="${beginDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,maxDate:'#F{$dp.$D(\'endDate\')}'});"/>至<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				value="${endDate}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'beginDate\')}'});"/>
				&nbsp;<input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>
				<c:if test="${fn:length(page.list)>0 }">
				&nbsp;<!-- <input id="btnExport" class="btn btn-primary" type="button" value="导出"/> -->
				</c:if>
				
		</div>
		
	</form:form>
	<tags:message content="${message}"/>
	<div style="width: 100%; height: 100%;overflow-x: auto;overflow-y: hidden;">
	<table id="contentTable" style="width: auto;" class="table table-striped table-bordered table-condensed">
		<thead><tr>
		     <th title="本站单号">本站订单号</th>
		     <th>提供者</th>
		     <th>提供者订单号</th>
		     <th>消费者</th>
		     <th>外部订单号</th>
		     <th>业务类型</th>
		     <th>产品编号</th>
		     <th>产品名称</th>
		     <th>数量</th>
		     <th>商品售价(元)</th>
		     <th>商品面值(元)</th>
		     <th>商品进价(元)</th>
		     <th>顾客信息</th>
		     <th>交易时间</th>
		     <th>处理时间</th>
		     <th>状态</th>
		    <shiro:hasPermission name="bussiness:orderRecord:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="orderRecord">
			<tr> 
			    <td><a href="${ctx}/bussiness/orderRecord/form?id=${orderRecord.id}">${orderRecord.id}</a></td>
			    <td>${orderRecord.provider.name}</td>
				<td>${orderRecord.orderId}</td>
				<td>${orderRecord.consumer.name}</td>
				<td>${orderRecord.payOrderId}</td>
				<td>${fns:getDictLabel(orderRecord.service.bussinessType, 'bussiness_type', '0')}</td>
				<td>${orderRecord.productid}</td>
				<td title="${orderRecord.productname }" style="cursor: pointer;">
				${orderRecord.productname}
				</td>
				<td><fmt:formatNumber value="${orderRecord.nums}" pattern="#0"></fmt:formatNumber></td>
				<td class="tdright"><fmt:formatNumber value="${orderRecord.goodsprice}" pattern="#0.0000"></fmt:formatNumber></td>
				<td class="tdright"><fmt:formatNumber value="${orderRecord.goodsparvalue}" pattern="#0.0000"></fmt:formatNumber></td>
				<td class="tdright"><fmt:formatNumber value="${orderRecord.purchaserprice}" pattern="#0.0000"></fmt:formatNumber></td>
				<td>${orderRecord.dispaly}</td>
				<td><fmt:formatDate value="${orderRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				<td><fmt:formatDate value="${orderRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				<td style="cursor: pointer;color: ${orderRecord.status eq 2 ? 'red' :  'green'}" title="${orderRecord.remarks}">
				       ${fns:getDictLabel(orderRecord.status , 'order_status', orderRecord.status)}
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	</div>
</body>
</html>
