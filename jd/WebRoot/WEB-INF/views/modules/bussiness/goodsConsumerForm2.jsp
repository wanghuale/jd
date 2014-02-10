<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
		<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
			    var tree;
				$(document).ready(function() {
		
		            var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{
					   beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					   }, 
					   onCheck:function(id, node){
					    var arry=new Array();
					    var nodes = tree.getCheckedNodes(true);
					  
						return true;
					   }
					}};
					
					
					
			 	// 用户-菜单
			var zNodes=[   
                     <c:forEach items="${pgoods}" var="vc">
							  {id:-${vc.P_GOOD_ID}, pId:0, name:"${vc.P_GOOD_NAME}"},
					 </c:forEach>
				    <c:forEach items="${treelist}" var="vc">
				              {id:${vc.goodsid}, pId:-${vc.fgoodsid}, name:"${vc.goodsid} ${vc.goodsname}" },
				    </c:forEach>
				    
				    <c:forEach items="${goodsalll}" var="vc">
			              {id:${vc.id}, pId:${vc.goodsCatalogId}, name:"${vc.goodsId} ${vc.goodsName}" },
			       </c:forEach>
            ];
			// 初始化树结构
		    tree = $.fn.zTree.init($("#servicesTree"), setting, zNodes);
			// 默认选择节点
			var ids = "${goodsids}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, true);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(false);
		});
		
	</script>
</head>
<body>
		<div class="control-group">
			<div class="controls">
				<div id="servicesTree" class="ztree" style="margin-top:3px;float:left;"></div>
			</div>
		</div>
</body>
</html>
