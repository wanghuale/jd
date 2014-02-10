<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#goodsListsave").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#goodsListForm").submit();
        	return false;	
        }
        
        function updateSellPrice() {
           if(!$("#goodsListsave").validate().form()){
               return false;
            }
			document.getElementById("gId").value=document.getElementById("goodsId").value;
			document.getElementById("gName").value=document.getElementById("goodsName").value;
			document.getElementById("gType").value=document.getElementById("goodsType").value;
			document.getElementById("gCatalogName").value=document.getElementById("goodsCatalogName").value;
			loading('正在提交，请稍等...');
	    	$("#goodsListsave").attr("action", "${ctx}/kamen/goods/updateSellPrice");
	    	$("#goodsListsave").submit();
    	}
    	
    	var shjutb=true;
    	function tongbucp(){
    		$("#synchroSubmit").attr("disabled","disabled");
    	    if(!shjutb){
    	      return ;
    	    }
    	    shjutb=false;
    	    var url = "${ctx}/kamen/goods/updategoodfromkamen";
             $.ajax({url: url,cache: false,success: function(html){
	    		    if(html==true){
	    		    	top.$.jBox.tip("数据同步成功");
	    			 
	    			   $("#synchroSubmit").removeAttr("disabled");
	    			   $("#goodsListForm").submit();
	    			}
	    			shjutb=true;
           	    }
           	 }); 
    	}
    	
	</script>
	<style type="text/css">
	.input-medium {
		width: 90px;
	}
	 .input100{width:100px;}
	 select {width: 100px;}
	 .Wdate{width:170px;}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">商品列表</a></li>
	</ul>
	<form:form id="goodsListForm" modelAttribute="goods" action="${ctx}/kamen/goods/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div style="line-height: 40px">
			<label>商品编号 ：</label>
			<form:input path="goodsId" htmlEscape="false" maxlength="50" class="input-medium"/>
			<label>商品名称 ：</label>
			<form:input path="goodsName" htmlEscape="false" maxlength="50" class="input-medium"/>
			<label>类目名称 ：</label>
			<form:input path="goodsCatalogName" htmlEscape="false" maxlength="50" class="input-medium"/>
			<label>商品分类 ：</label>
			<form:select path="goodsType" cssStyle="width: 200px;">
				<form:option value="" label="全部"/>
				<form:option value="1001" label="网络游戏点卡"/>
				<form:option value="1171" label="腾讯QQ专区"/>
				<form:option value="2660" label="网页游戏"/>
				<form:option value="4425" label="中国移动"/>
				<form:option value="4505" label="中国联通"/>
				<form:option value="4515" label="中国电信"/>
				<form:option value="2002" label="行业服务软件类"/>
				<form:option value="1866" label="视频娱乐棋牌类平台专区"/>
				<form:option value="1208" label="游戏道具及激活码"/>
				<form:option value="1839" label="更多精彩,更多欢乐"/>
			</form:select>
			<input id="searchSubmit" class="btn btn-primary" type="submit" value="查询"/>
			<input id="synchroSubmit" class="btn btn-primary" type="button" value="同步商品" onclick="tongbucp()"/>
		</div>
	</form:form>
	<tags:message content="${message}"/>
	<form:form id="goodsListsave" modelAttribute="goods" action="${ctx}/kamen/goods/updateSellPrice" method="post">
	<input id="gId" name="goodsId" type="hidden"/>
	<input id="gName" name="goodsName" type="hidden"/>
	<input id="gType" name="goodsType" type="hidden"/>
	<input id="gCatalogName" name="goodsCatalogName" type="hidden"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width: 60px;">商品编号</th>
				<th>商品名称</th>
				<th style="width: 80px;">商品面值(元)</th>
				<th style="width: 80px;">采购价(元)</th>
				<th style="text-align:center;">销售价(元)</th>
				<th style="width: 400px;">类目名称(编号)</th>
			</tr>
		</thead>
	  <tbody>
		<c:forEach items="${page.list}" var="goods" varStatus="vs">
		<c:choose>
			<c:when test="${goods.spreadprice lt 0}">
				<tr style="color: red;">
			</c:when>
			<c:otherwise>
				<tr>
			</c:otherwise>
		</c:choose>
			
			<td>${goods.goodsId}</td>
			<td>${goods.goodsName}</td>
			<td>${goods.goodsParvalue}</td>
			<td>${goods.purchasePrice}</td>
			<td style="text-align:center;">
			   <input type="hidden" name="goodlist[${vs.index}].id" value="${goods.id}"/>
			   <input name="goodlist[${vs.index}].sellPrice" type="text" value="${goods.sellPrice}" style="width:50px;margin:0;padding:0;text-align:center;"   class="number required" maxlength="10" min="0"> 
			</td>
		    <td>${goods.goodsCatalogName}&nbsp;(${goods.goodsCatalogId})</td>
			</tr>
		</c:forEach>
	  </tbody>
	</table>
	<div class="pagination">${page}</div>
	<div class="form-actions"><input id="btnSubmit" class="btn btn-primary" type="button" value="保存销售价" onclick="updateSellPrice();"/></div>
	</form:form>

</body>
</html>
