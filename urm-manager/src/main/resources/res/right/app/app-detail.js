
var appTemp,funcTemp,operTemp;

$(function(){
	appTemp = new EJS({url:ctx+"/res/template/app/tree-app-show.ejs",type:"[",cache:false});
	funcTemp = new EJS({url:ctx+"/res/template/app/tree-func-show.ejs",type:"[",cache:false});
	operTemp = new EJS({url:ctx+"/res/template/app/tree-oper-show.ejs",type:"[",cache:false});
	showTree();
	
});
//添加的data
var treeData = null;
//选中的data
var chooseData = null;

function showTree(){
	//展示权限tree
	$.ajaxPost("/app/func/oper/tree",{appId:$("#appId").val()},function(result){
		treeData = result.data;
		$('#tree').treeview({data: treeData,levels:5}); 
		$('#tree').on('nodeSelected',function(event, data) {
			chooseData = data;
			var type = data.type;
			switch (type) {
			case '1':
				renderShowApp();
				break;
			case '2':
				renderShowFunc();
				break;
			case '3':
				renderShowOper();
				break;	
			default:
				$("#show").text("");
				break;
			}
		});
		
		$('#tree').on('nodeUnselected',function(event, data) {
			$("#show").html("");
		});
		
		if(chooseData){
			chooseOperSelected();
		}else{
			firstOperSelected();
		}
	});
}


//1，默认选中第一个 oper 选中
function firstOperSelected(){
	var nodes = $('#tree').treeview('getEnabled',null);
	for (var i = 0; i < nodes.length; i++) {
		var node = nodes[i];
		if(node.type=='3'){
			$('#tree').treeview('toggleNodeSelected',[node.nodeId,{ silent: false}]);
			break;
		}
	}
}

function chooseOperSelected(){
	var nodes = $('#tree').treeview('getEnabled',null);
	for (var i = 0; i < nodes.length; i++) {
		var node = nodes[i];
		if(node.type==chooseData.type &&node.id==chooseData.id){
			$('#tree').treeview('toggleNodeSelected',[node.nodeId,{ silent: false}]);
			break;
		}
	}
}



function renderShowApp(){
	$("#show").html(appTemp.render(chooseData));
	//绑定新增功能操作
	$("#btn-add-func").unbind("click").click(function(){
		$("#addFuncModal").modal("show");
		$("#addFuncForm")[0].reset();
		$("#addFuncForm").find("input[name=appId]").val($("#appId").val());	
		$("#addFuncForm").find("input[name=appName]").val($("#appName").val());
		$("#addFuncForm").find("input[name=parentFunctionId]").val('');	
		$("#addFuncForm").find("input[name=parentFunctionName]").val('');
		
		var addFuncVali = $("#addFuncForm").validate();
		$('#btn-add-submit-func').unbind("click").click(function(){
	    	if(addFuncVali.form()){
	    		//提交请求
	    		var data = $("#addFuncForm").serializeObject();
	    		var sendData = JSON.stringify(data);
	    		console.log(sendData);
	    		$.ajaxPostJson(ctx+"/func/add",sendData,function(ret){
	    			addFuncVali.resetForm();
	    			$("#addFuncModal").modal("hide");
	    			showTree();
	    		})
	    	}
	    });
		$('#btn-add-reset-func').unbind("click").click(function(){
			$("#addFuncForm").find("input[name=functionCode]").val('');	
			$("#addFuncForm").find("input[name=functionName]").val('');
			$("#addFuncForm").find("input[name=functionDesc]").val('');
	    });
		
	});
	
}


function renderShowFunc(){
	$("#show").html(funcTemp.render(chooseData));
	//绑定新增功能按钮
	$("#btn-add-func").unbind("click").click(function(){
		$("#addFuncModal").modal("show");
		$("#addFuncForm")[0].reset();
		$("#addFuncForm").find("input[name=appId]").val($("#appId").val());	
		$("#addFuncForm").find("input[name=appName]").val($("#appName").val());
		$("#addFuncForm").find("input[name=parentFunctionId]").val(chooseData['id']);	
		$("#addFuncForm").find("input[name=parentFunctionName]").val(chooseData['text']);
		
		
		var addFuncVali = $("#addFuncForm").validate();
		$('#btn-add-submit-func').unbind("click").click(function(){
	    	if(addFuncVali.form()){
	    		//提交请求
	    		var data = $("#addFuncForm").serializeObject();
	    		var sendData = JSON.stringify(data);
	    		console.log(sendData);
	    		$.ajaxPostJson(ctx+"/func/add",sendData,function(ret){
	    			addFuncVali.resetForm();
	    			$("#addFuncModal").modal("hide");
	    			showTree();
	    		})
	    	}
	    });
		
		$('#btn-add-reset-func').unbind("click").click(function(){
			$("#addFuncForm").find("input[name=functionCode]").val('');	
			$("#addFuncForm").find("input[name=functionName]").val('');
			$("#addFuncForm").find("input[name=functionDesc]").val('');
	    });
	});
	
	//绑定添加操作按钮
	$("#btn-add-oper").unbind("click").click(function(){
		$("#addOperModal").modal("show");
		$("#addOperForm")[0].reset();
		$("#addOperForm").find("input[name=appId]").val($("#appId").val());	
		$("#addOperForm").find("input[name=appName]").val($("#appName").val());
		$("#addOperForm").find("input[name=functionId]").val(chooseData['id']);	
		$("#addOperForm").find("input[name=functionName]").val(chooseData['text']);
		
		
		var addFuncVali = $("#addOperForm").validate();
		$('#btn-add-submit-oper').unbind("click").click(function(){
	    	if(addFuncVali.form()){
	    		//提交请求
	    		var data = $("#addOperForm").serializeObject();
	    		var sendData = JSON.stringify(data);
	    		console.log(sendData);
	    		$.ajaxPostJson(ctx+"/oper/add",sendData,function(ret){
	    			addFuncVali.resetForm();
	    			$("#addOperModal").modal("hide");
	    			showTree();
	    		})
	    	}
	    });
		
		$('#btn-add-reset-oper').unbind("click").click(function(){
			$("#addOperForm").find("input[name=operCode]").val('');	
			$("#addOperForm").find("input[name=operName]").val('');
	    });
	});
	
	//绑定删除功能操作
	$("#btn-del-func").unbind("click").click(function(){
		if(chooseData['nodes']){
			layer.msg('功能下还有功能或操作，不能删除');
			return false;
		}
		layer.msg('你确定删除功能['+chooseData.text+"]", {
  		  time: 0 //不自动关闭
  		  ,btn: ['确认', '取消']
  		  ,yes: function(index){
  		    layer.close(index);
  		    $.ajaxPostJson(ctx+"/func/delete",{"deleteIds":chooseData.id},function(ret){
  		    	showTree();
  			})
  		  }
    	});
	});
	
}

function renderShowOper(){
	$("#show").html(operTemp.render(chooseData));
	
	//绑定删除操作
	$("#btn-del-oper").unbind("click").click(function(){
		layer.msg('你确定删除操作['+chooseData.text+"]", {
  		  time: 0 //不自动关闭
  		  ,btn: ['确认', '取消']
  		  ,yes: function(index){
  		    layer.close(index);
  		    $.ajaxPostJson(ctx+"/oper/delete",{"deleteIds":chooseData.id},function(ret){
  		    	showTree();
  			})
  		  }
    	});
	});
	
}




