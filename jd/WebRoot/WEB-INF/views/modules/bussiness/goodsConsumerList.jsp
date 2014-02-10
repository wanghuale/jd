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
			$("#searchForm").submit();
        	return false;
        }
        
        function gogackpage() {
          window.location.href="${ctx}/bussiness/consumer"
        }
       
        function addgoods() {
          window.location.href="${ctx}/bussiness/goodsConsumer/addgoods?consumer.id=${consumer.id}"
        } 
        
        function deletethegoods(id) {
	        var url = "${ctx}/bussiness/goodsConsumer/delete?gcid=${consumer.id}&id="+id;
	        confirmx('确认要移除商品吗？', url);
    	}
    	
        function deletetall() {
	        var url="${ctx}/bussiness/goodsConsumer/deleteall?gcid=${consumer.id}";
	        confirmx('确认要移除全部商品？', url);
    	}
        
        function updateSellPrice() {
            if(!$("#goodsListsave").validate().form()){
                     return false;
            }
			loading('正在提交，请稍等...');
	    	$("#goodsListsave").attr("action", "${ctx}/bussiness/goodsConsumer/save");
	    	$("#goodsListsave").submit();
    	}
    	
    	function addgoodsbydiv(){
    	   var url="${ctx}/bussiness/goodsConsumer/goodstree?consumer.id=${consumer.id}";
    	   top.$.jBox.open("iframe:"+url, "添加商品", 400, 500, {
				buttons:{"确定":"ok", ${allowClear?"\"清除\":\"clear\", ":""}"关闭":true},
			    submit:function(v, h, f){ 
			         if (v=="ok"){
						var tree = h.find("iframe")[0].contentWindow.tree;//h.find("iframe").contents();
						var ids = [], nodes = tree.getCheckedNodes(true);
						for(var i=0; i<nodes.length; i++) {
							ids.push(nodes[i].id);
						}
						$("#goodsIds").val(ids);
						 sumbmitaddgoods();
					}
			    }, 
				loaded:function(h){ $(".jbox-content", top.document).css("overflow-y","hidden"); 
				
				}
     	   });
    	}
        
        
        function sumbmitaddgoods() {
			loading('正在提交，请稍等...');
	    	$("#savegoods").attr("action", "${ctx}/bussiness/goodsConsumer/savegoods?consumer.id=${consumer.id}");
	    	$("#savegoods").submit();
    	}
    	
    	
   
	</script>
</head>
<body>
    <ul class="nav nav-tabs">
	    <li><a href="javascript:void(0)">&nbsp;消费者用户名 ：</label> ${consumer.username }</a></li>
	    <li><a href="javascript:void(0)">消费者名称  ：</label> ${consumer.name}</a></li>
	</ul> 
	<form:form id="searchForm" modelAttribute="goodsConsumer" action="${ctx}/bussiness/goodsConsumer/" method="post"  class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<form:hidden path="consumer.id"/>
		 <div style="line-height: 40px;">
	    <label>商品编号 ：</label>
		<form:input path="goods.goodsId" htmlEscape="false" maxlength="50" class="input-medium" cssStyle="width: 100px;"/>
		&nbsp;&nbsp;<label>商品名称 ：</label>
		<form:input path="goods.goodsName" htmlEscape="false" maxlength="50" class="input-medium" cssStyle="width: 100px;"/>
		<label>类目名称 ：</label>
		<form:input path="goods.goodsCatalogName" htmlEscape="false" maxlength="50" class="input-medium" cssStyle="width: 100px;"/>
		<label>商品分类 ：</label>
			<form:select path="goods.goodsType" cssStyle="width: 200px;">
				<form:option value="" label=""/>
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
		&nbsp;<input id="searchSubmit" class="btn btn-primary" type="submit" value="查询"/>
		</div>
	</form:form>
<form:form id="goodsListsave" modelAttribute="goodsConsumer" action="${ctx}/bussiness/goodsConsumer/save" method="post" >
   <input type="hidden" value="${consumer.id}" name="gcid">
	<script type="text/javascript">top.$.jBox.closeTip();</script>
	<script type="text/javascript">if(!top.$.jBox.tip.mess){top.$.jBox.tip.mess=1;top.$.jBox.tip("操作成功","success",{persistent:true,opacity:0});$("#messageBox").show();}</script>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		     <tr>
			     <th style="width: 80px;">商品编号</th>
			     <th style="width: 380px;">商品名称</th>
			     <th style="width: 80px;">商品面值(元)</th>
			     <th style="width: 80px;">采购价(元)</th> 
			     <th style="width: 80px;">销售价(元)</th>
			     <th style="width: 150px;">类目名称(编号)</th>
			     <th>操作</th> 
		     </tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="goodsConsumer" varStatus="vs">
				<c:choose>
					<c:when test="${goodsConsumer.spreadprice lt 0}">
						<tr style="color: red;">
					</c:when>
					<c:otherwise>
						<tr>
					</c:otherwise>
				</c:choose>
					<td>${goodsConsumer.goods.goodsId}</td>
					<td>${goodsConsumer.goods.goodsName}</td>
					<td> 
					   <fmt:formatNumber pattern="#0.0000" value='${goodsConsumer.goods.goodsParvalue}'/>
					</td>
					<td>
					   <fmt:formatNumber pattern="#0.0000" value='${goodsConsumer.goods.purchasePrice}'/>
					</td>
					<td>
                          <input type="hidden" name="goodsConsumerlist[${vs.index}].id" value="${goodsConsumer.id}"/>
			              <input name="goodsConsumerlist[${vs.index}].sell_price" type="text" value="<fmt:formatNumber pattern="#0.0000" value='${goodsConsumer.sell_price}'/>" style="width:50px;margin:0;padding:0;text-align:center;"   class="number required" maxlength="10" min="0"> 
                     </td>
                     <td>${goodsConsumer.goods.goodsCatalogName}&nbsp;(${goodsConsumer.goods.goodsCatalogId})</td>
					<td><a href="javascript:void(0)" onclick="deletethegoods('${goodsConsumer.id}')">移除</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<c:if test="${fn:length(page.list) > 0}">
  	   <div class="pagination">${page}</div>
	 </c:if>
	    <div class="form-actions pagination-left">
			<c:if test="${fn:length(page.list) > 0}">
			    <input type="button" name="buttiont" value="保存修改价格" id="btnSubmit" class="btn btn-primary" onclick="updateSellPrice()">
		        <input type="button" name="buttiont" value="移除全部商品" id="btnSubmi22t" class="btn btn-primary" onclick="deletetall()">
		    </c:if>
	        <input type="button" name="buttiont" value="添加商品" id="btnSubmi22t" class="btn btn-primary" onclick="addgoodsbydiv()">
			<input id="searchSubmit22" class="btn btn-primary" type="button" onclick="gogackpage()" value="返回"/>
		</div>
		<input id="1goodsType" class="btn btn-primary" type="hidden" value="${goodsConsumer.goods.goodsType}" name="goods.goodsType"/>
		<input id="2goodsId" class="btn btn-primary" type="hidden" value="${goodsConsumer.goods.goodsId}" name="goods.goodsId"/>
		<input id="3goodsName" class="btn btn-primary" type="hidden" value="${goodsConsumer.goods.goodsName}" name="goods.goodsName"/>
		<input id="4goodsName" class="btn btn-primary" type="hidden" value="${goodsConsumer.goods.goodsCatalogName}" name="goods.goodsCatalogName"/>
	</form:form>
	<form:form id="savegoods" modelAttribute="goods" action="${ctx}/bussiness/goodsConsumer/list?consumer.id=${consumer.id}" method="post">
	     <input name="goodsIds" value="" id="goodsIds" type="hidden">
	</form:form>
</body>
</html>
