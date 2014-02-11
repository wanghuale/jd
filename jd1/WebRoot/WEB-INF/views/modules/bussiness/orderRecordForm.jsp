<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	.form-horizontal .controls {
text-align: left;
padding-top: 3px;
}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/bussiness/orderRecord/">订单列表</a></li>
		<li class="active"><a href="${ctx}/bussiness/orderRecord/form?id=${orderRecord.id}">订单查看</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="orderRecord" action="${ctx}/bussiness/orderRecord/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">供应者:</label>
			<div class="controls">
				<span>${orderRecord.provider.name}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">供应者订单号:</label>
			<div class="controls">
				<span>${orderRecord.orderId}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消费者:</label>
			<div class="controls">
				<span>${orderRecord.consumer.name}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">外部订单号:</label>
			<div class="controls">
				<span>${orderRecord.payOrderId}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">业务类型:</label>
			<div class="controls">
				<span>${fns:getDictLabel(orderRecord.service.bussinessType, 'bussiness_type', '0')}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品编号:</label>
			<div class="controls">
				<span>${orderRecord.productid}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称:</label>
			<div class="controls">
				<span>${orderRecord.productname}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数量:</label>
			<div class="controls">
				<span><fmt:formatNumber value="${orderRecord.nums}" pattern="#0"></fmt:formatNumber></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品售价:</label>
			<div class="controls">
				<span><fmt:formatNumber value="${orderRecord.goodsprice}" pattern="#0.00"></fmt:formatNumber></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品面值:</label>
			<div class="controls">
				<span><fmt:formatNumber value="${orderRecord.goodsparvalue}" pattern="#0.00"></fmt:formatNumber></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品进价:</label>
			<div class="controls">
				<span>
				   <fmt:formatNumber value="${orderRecord.purchaserprice}" pattern="#0.00"></fmt:formatNumber>
				</span>
			</div>
		</div>
			<div class="control-group">
			<label class="control-label">支付金额:</label>
			<div class="controls">
				<span>
				   <fmt:formatNumber value="${orderRecord.amount}" pattern="#0.00"></fmt:formatNumber>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">顾客信息:</label>
			<div class="controls">
				<span>${orderRecord.dispaly}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">交易时间:</label>
			<div class="controls">
			    <span><fmt:formatDate value="${orderRecord.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理时间:</label>
			<div class="controls">      
			           <c:choose>
				         <c:when test="${not empty orderRecord.updateDate }">
							    <span><fmt:formatDate value="${orderRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
				         </c:when>
				         <c:otherwise>--</c:otherwise>
				       </c:choose>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态:</label>
			<div class="controls"  style="cursor: pointer;color: ${orderRecord.status eq 2 ? 'red' :  'green'}" >
				       ${fns:getDictLabel(orderRecord.status , 'order_status', orderRecord.status)}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回调URL:</label>
			<div class="controls">
				<span>${orderRecord.callBackUrl}</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">回调报文:</label>
			<div class="controls">
			  <span>${orderRecord.remarks}</span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>
