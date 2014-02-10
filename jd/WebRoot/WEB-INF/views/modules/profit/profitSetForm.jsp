<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分润管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#businessType").focus();
			 choosetype();
			 sumbitdata();
			 
			 			 
			 jQuery.validator.addMethod("businessType", function(b, a ) {
	             var url = "${ctx}/operation/profitSet/checkType?checkdate="+new Date()+"&"+$("#inputForm").serialize();
				 var html = $.ajax({ url: url,async: false}).responseText;
				 if(html=='true'){return true;	 }else{return false; }
			}, "业务类型已经存在分润设置");
			
			
			$("#inputForm").validate({
				submitHandler: function(form){
					sumbitdata();
				    if(!validatemoney()){
				      return false;
				    }
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")){
						error.appendTo(element.parent().parent());
					} else{
						error.insertAfter(element);
					}
				}
			});
		});
		
		
		function  choosetype(){
		     var value = $("#distriType").val();
		     if(value == "1"){
		        $("#fhfddiv").show();
		        $("#fhbldiv").hide();
		        if($('#showdiv').find('.inputstartregion').length==0){
		           adddivx()
		        }
		     }else{
		        $("#fhfddiv").hide();
		        $("#fhbldiv").show();
		     }
		}
		
		function deldivx(obj){
			 if($('#showdiv').find('.inputstartregion').length==1){
		            $("#showerror").html("至少填写一个分段设置!");
		            $("#showerrortable").show();
			 }else{
			    $(obj).parent().parent().remove();
			     sumbitdata();
			 }
		}
		
		function adddivx(){
		    $("#showdiv").html( $("#showdiv").html()+ $("#tmpdiv").html());
		    sumbitdata();
		}
		
		function sumbitdata(){
		    $('#showdiv').find('.inputstartregion').each(function (i) {
                 $(this).attr("name","profitRegions["+i+"].startregion");
                 $(this).attr("id","inputstartregion"+i);
		         $(this).attr("inputindex",i);
            });
            $('#showdiv').find('.inputendregion').each(function (i) {
                 $(this).attr("name","profitRegions["+i+"].endregion");
                 $(this).attr("id","inputendregion"+i);
                 $(this).attr("inputindex",i);
            });
            $('#showdiv').find('.inputmoney').each(function (i) {
                 $(this).attr("name","profitRegions["+i+"].money");
                 $(this).attr("id","inputmoney"+i);
                 $(this).attr("inputindex",i);
            });
		}
		
		
		function setmoney(){
		    var tempvalue=0;
		    $('#showdiv').find('.inputendregion').each(function (i) {
		        var endvalue = $("#inputendregion"+i).val();
		        $("#inputstartregion"+i).val(tempvalue);
		        if(parseInt(endvalue) > parseInt(tempvalue)){
		            tempvalue=endvalue;
		        }else{
		           //$("#inputendregion"+i).val(tempvalue);
		        }
            });
		}
		
		
		function validatemoney(){
		     var bl=true;
		     var value = $("#distriType").val();
		     if(value == "1"){
		          $("#showerrortable").hide();
			      $('#showdiv').find('.inputendregion').each(function (i) {
				        var endvalue = $("#inputendregion"+i).val();
				        var startvalue = $("#inputstartregion"+i).val();
				        var inputmoney = $("#inputmoney"+i).val();
				        if(parseInt(endvalue) == parseInt(startvalue)){
				            $("#showerror").html("区域范围起始值不能相同!");
				            $("#showerrortable").show();
				            bl= false;
				        }
				       	if(parseInt(endvalue) < parseInt(startvalue)){
				            $("#showerror").html("盈利额分段的上限不能低于下限!");
				            $("#showerrortable").show();
				            bl= false;
				        }
				        if(Number(inputmoney)>Number(endvalue)){
				          	$("#showerror").html("分红金额不能大于营业额!");
				            $("#showerrortable").show();
				            bl= false;
				        }
		          });
		          $("#rate").val(0);
		     }else{
		         $("#showdiv").remove();
			     return true;
		     }
            return bl;
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/operation/profitSet/">分润列表</a></li>
		<li class="active"><a href="${ctx}/operation/profitSet/form?id=${profitSet.id}">分润<shiro:hasPermission name="operation:profitSet:edit">${not empty profitSet.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="operation:profitSet:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="profitSet" action="${ctx}/operation/profitSet/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">业务类型:</label>
			<div class="controls">
			    <input id="oldBusinessType" name="oldBusinessType" type="hidden" value="${profitSet.businessType}">
				<form:select path="businessType" class="input-medium required businessType" id="userinfo_cardType" >
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('bussiness_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分润方式:</label>
			<div class="controls">
				<form:select path="distriType" class="input-medium required" id="distriType" onchange="choosetype(this)" onblur="choosetype(this)">
					<form:options items="${fns:getDictList('distri_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group" id="fhbldiv">
			<label class="control-label">分红比例(%):</label>
			<div class="controls">
				<form:input path="rate" htmlEscape="false" maxlength="6" class="input-medium xiaoshu required" min="0"/>
			</div>
		</div>
		<div id="fhfddiv">
				<table style="width: 830px" id="contentTable" class="table table-striped table-bordered table-condensed" >
				    <thead>
						<tr>
							<th>盈利额分段（万）</th><th>分红金额 （万）</th><th><a href="javascript:void(0)" onclick="adddivx()">添加</a></th>
						</tr>
					</thead>
		            <tbody id="showdiv">
			            <c:forEach items="${profitSet.profitRegions}" var="profitRegion" varStatus="vs">
							<tr>
								<td>
									 <input  name="profitRegions[${vs.index}].startregion" value="${profitRegion.startregion}" type="text" onblur="setmoney(this)" class="inputstartregion required digits" style="width: 80px" readonly="readonly" min="0">
									 -
									 <input  name="profitRegions[${vs.index}].endregion" value="${profitRegion.endregion}" type="text" onblur="setmoney(this)" class="inputendregion required digits" style="width: 80px" min="0" maxlength="10">
								</td>
								<td> 
								     <input dname="money"  name="profitRegions[${vs.index}].money" value="${profitRegion.money}" type="text"  class="inputmoney number" min="0">
								</td>
								<td> 
								     <a href="javascript:void(0)" onclick="deldivx(this)">删除</a>
								</td>
							</tr>
						</c:forEach>
		            </tbody>
				</table>
				<table style="width: 530px" id="showerrortable" class="table table-striped table-bordered table-condensed"  >
						<tr>
							<th id="showerror" style="color: red;"></th>
						</tr>
				</table>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="operation:profitSet:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
		  <table class="hide" style="width: 530px"  >
			  	 <tbody id="tmpdiv" >
	                  <tr>
						<td>
							 <input  name="profitRegions[0].startregion" value="0" type="text" onblur="setmoney(this)" class="inputstartregion required digits" style="width: 80px" readonly="readonly" min="0">
							 -
							 <input  name="profitRegions[0].endregion" value="0" type="text" onblur="setmoney(this)" class="inputendregion required digits" style="width: 80px" min="0" maxlength="10">
						</td>
						<td> 
						     <input dname="money"  name="profitRegions[0].money" value="0" type="text"  class="inputmoney number" min="0">
						</td>
						<td> 
						     <a href="javascript:void(0)" onclick="deldivx(this)">删除</a>
						</td>
					</tr>
				</tbody>
			</table>
</body>
</html>
