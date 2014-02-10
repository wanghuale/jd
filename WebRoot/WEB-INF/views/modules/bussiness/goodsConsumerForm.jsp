<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#goodsListForm").submit();
        	return false;	
        }
        
        function updateSellPrice() {
           var bl=false;
            $('.choosegoods').each(function (i) {
                if(this.checked){
                  bl= true;
                }
            });
            if(!bl){
              alert("请选择添加的商品");
              return ;
            }
			loading('正在提交，请稍等...');
	    	$("#savegoods").attr("action", "${ctx}/bussiness/goodsConsumer/savegoods?consumer.id=${consumer.id}");
	    	$("#savegoods").submit();
    	}
    
    
         function addALL() {
			loading('正在提交，请稍等...');
	    	$("#savegoods").attr("action", "${ctx}/bussiness/goodsConsumer/addll?consumer.id=${consumer.id}");
	    	$("#savegoods").submit();
    	}	
    	
    	
        function gogackpage() {
          window.location.href="${ctx}/bussiness/goodsConsumer/savegoods?consumer.id=${consumer.id}"
        }
    
    	function checkall(obj){
    	    $('.choosegoods').each(function (i) {
                this.checked=obj.checked;
            });
    	}
	</script>
</head>
<body>
	<form:form id="goodsListForm" modelAttribute="goods" action="${ctx}/bussiness/goodsConsumer/addgoods?consumer.id=${consumer.id}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	    <label>商品编号 ：</label>
		<form:input path="goodsId" htmlEscape="false" maxlength="50" class="input-medium"/>
		<label>商品名称 ：</label>
		<form:input path="goodsName" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="searchSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<form:form id="savegoods" modelAttribute="goods" action="${ctx}/bussiness/goodsConsumer/list?consumer.id=${consumer.id}" method="post" class="breadcrumb form-search">
		<tags:message content="${message}"/>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
				    <th style="width: 30px;cursor: pointer;" title="全选"><input type="checkbox" id="checkalltem" onclick="checkall(this)"></th>
					<th style="width: 60px;">商品编号</th>
					<th style="width: 400px;">商品名称</th>
					<th style="width: 80px;">商品面值(元)</th>
					<th style="text-align:center;width: 80px;">销售价(元)</th>
					<th style="width: 80px;">采购价(元)</th>
					<th >类目名称(编号)</th>
				</tr>
			</thead>
		  <tbody>
			<c:forEach items="${page.list}" var="goods">
				<tr>
				    <td><input type="checkbox" name="goodsid" class="choosegoods" value="${goods.id}"></td>
					<td>${goods.goodsId}</td>
					<td>${goods.goodsName}</td>
					<td>${goods.goodsParvalue}</td>
					<td style="text-align:center;">${goods.sellPrice}</td>
					<td>${goods.purchasePrice}</td>
					<td>${goods.goodsCatalogName}&nbsp;(${goods.goodsCatalogId})</td>
				</tr>
			</c:forEach>
		  </tbody>
		</table>
		<div class="pagination">${page}<br>
		<input id="btnSubmit" class="btn btn-primary" type="button" value="添加选择商品" onclick="updateSellPrice();"/> 
		<input id="btnSubmit" class="btn btn-primary" type="button" value="添加全部商品" onclick="addALL();"/>
			      <input id="searchSubmit22" class="btn btn-primary" type="button" onclick="gogackpage()" value="返回"/>
			      </div>
	</form:form>
</body>
</html>
