var table_have = null;

var table_not_have = null;

var operTemp ;

var addDbDataRightFormVali = null;

var updateDbDataRightFormVali = null;

var addValueDataRightFormVali = null;

var updateValueDataRightFormVali = null;

//添加的data
var treeData = null;
//选中的data
var chooseData = null;

var treeObj = null;

var dbShowTemp = null;

//值集
var valueDatas = null;

$(function(){
	table_have = $('#role_have_list').DataTable({
	  	"language": {
          	"url": ctx+"/res/components/datatable.net/zh_CN.js"
     	},
	    "processing" : true, 	// 控制是否在数据加载时出现”Processing”的提示,一般在远程加载并且比较慢的情况下才会出现.
	    "ordering": false, 		// 全局控制列表的所有排序功能.
        "lengthChange": false, // 控制是否能够调整每页的条数,如果设为false,标准的每页条数控制控件也会被隐藏.
        "searching": false,		// 控制控件的搜索功能
        "info": true, 			// 控制总数信息(标准界面右下角显示总数和过滤条数的控件)的显隐
        "autoWidth": false,		// 定义是否由控件自动控制列宽
        "pageLength": 5, 		// 定义初始的页长
        "lengthChange":false,   //
        "serverSide": true,  	// 是否服务端分页
        "ajax": {// ajax 配置
            "url": ctx+"/role/acct/have",
            "type":"POST",
        	"data": function ( d ) {
                d.roleId=$('#roleId').val();
            }
        },
        "columns": [// 每页展示什么
             { 
            	 "render": function (data, type, full, meta) {
            		 return '<a href="'+ctx+'/acct/detail?acctId='+full.acctId+'">'+full.acctId+'</a>';
                 }
             },
             { "data": "acctName" },
             { "data": "phone" },
             { "data": "email" },
             { "data": "createUserName" },
             { "data": "createTime" },
             { 
            	 "render": function (data, type, full, meta) {
                     return "<div class='btn-group'>"+
                     "<button id='delRow'  title='删除关联关系' class='btn btn-primary btn-sm' type='button'><i class='fa fa-trash-o'></i></button>"+
                     "</div>";
                 }
             }
        ]
    });
	
	//添加账户关联按钮
	$("#btn-add-acct").click(function(){
		$("#acctChooseModal").modal("show");
		table_not_have.ajax.reload();
	});
	
	
	table_not_have = $('#acct_not_have_list').DataTable({
	  	"language": {
          	"url": ctx+"/res/components/datatable.net/zh_CN.js"
     	},
	    "processing" : true, 	// 控制是否在数据加载时出现”Processing”的提示,一般在远程加载并且比较慢的情况下才会出现.
	    "ordering": false, 		// 全局控制列表的所有排序功能.
        "lengthChange": false, // 控制是否能够调整每页的条数,如果设为false,标准的每页条数控制控件也会被隐藏.
        "searching": false,		// 控制控件的搜索功能
        "info": true, 			// 控制总数信息(标准界面右下角显示总数和过滤条数的控件)的显隐
        "autoWidth": false,		// 定义是否由控件自动控制列宽
        "pageLength": 10, 		// 定义初始的页长
        "lengthChange":false,   //
        "serverSide": true,  	// 是否服务端分页
        "ajax": {// ajax 配置
            "url": ctx+"/role/acct/nothave",
            "type":"POST",
        	"data": function ( d ) {
                d.roleId=$('#roleId').val();
                d.acctName=$('#acctName').val();
            }
        },
        "columns": [// 每页展示什么
             { 
            	 "data": "acctId",
        		 "render": function (data, type, full, meta) {
        			 return '<input type="checkbox"  class="checkchild" name="chooseRow" value="' + data + '"/>';
                 },
            	 "bSortable": false
             },
             { "data": "acctId" },
             { "data": "acctName" },
             { "data": "phone" },
             { "data": "email" },
             { "data": "createUserName" }
             
        ]
    });
	
	//删除关联关系
	//单个删除
    $('#role_have_list tbody').on('click', '#delRow', function () {
    	var data = table_have.row($(this).parents('tr')).data();
		var sendData = {"deleteIds":data.relaId};
    	layer.msg('确定删除与['+data.acctName+"]账号的关联", {
    		  time: 0 //不自动关闭
    		  ,btn: ['确认', '取消']
    		  ,yes: function(index){
    		    layer.close(index);
    		    $.ajaxPostJson(ctx+"/role/acct/del",sendData,function(ret){
    		    	table_have.draw(false);
    			})
    		  }
    	});
    });
	
	
	$(".checkall").click(function () {
		 if ($(this).prop("checked") === true) {
	            $(".checkchild").prop("checked", $(this).prop("checked"));
	            $('#acctList tbody tr').addClass('selected');
	     } else {
	            $(".checkchild").prop("checked", false);
	            $('#acctList tbody tr').removeClass('selected');
	     }      
	});
	
	
	$("#btn-query").click(function () {
		table_not_have.ajax.reload();
	});
	
	
	$("#btn-add-submit").click(function(){
		var checkRow = $('#acct_not_have_list tbody').find("input[name=chooseRow]:checked");
    	if(checkRow.length==0){
    		layer.alert("请选中要添加的账户");
    		return;
    	}
    	
    	var chooseIds = "";
		    for (var i = 0; i < checkRow.length; i++) {
		    	chooseIds = chooseIds+$(checkRow[i]).val();
		    	if(i!=checkRow.length-1){
		    		chooseIds = chooseIds+",";
		    	}
		}
	    $.ajaxPostJson(ctx+"/role/acct/add",{"roleId":$("#roleId").val(),"acctIds":chooseIds},function(ret){
	    	table_have.draw(false);
	    	table_not_have.draw(false);
	    	$("#roleChooseModal").modal("hide");
		})
	})
	
	$("#btn-add-reset").click(function(){
		$('#acct_not_have_list').find("input[type=checkbox]").prop("checked", false);
	})
	
	
	operTemp = new EJS({url:ctx+"/res/template/role/role-oper.ejs",type:"[",cache:false});	
	dbShowTemp = new EJS({url:ctx+"/res/template/right/right-data-show.ejs",type:"[",cache:false});	
	
	//展示权限tree
	showTree();
	
	addDbDataRightFormVali = $("#addDbDataRightForm").validate();
	updateDbDataRightFormVali = $("#updateDbDataRightForm").validate();
	
	
	addValueDataRightFormVali = $("#addValueDataRightForm").validate();
	updateValueDataRightFormVali = $("#updateValueDataRightForm").validate();
	
	//显示 值集的 select
	$.ajaxPostJson(ctx+"/right/values",null,function(ret){
		valueDatas = ret.data;
		//显示数据
		$("#addValueCode").select2({
			placeholder:'请选择',
			language:"zh-CN",
			data:valueDatas
		});
		
		$("#addValueCode").on("change", addValueChange);
		
		addValueChange();
		
		//显示数据
		$("#updateValueCode").select2({
			placeholder:'请选择',
			language:"zh-CN",
			data:valueDatas
		});
		
		$("#updateValueCode").on("change", updateValueChange);
		
		updateValueChange();
	})
});


function addValueChange(){
	var valueCode = $("#addValueCode").val();
	//生成
	var valueData = getValueData(valueCode);
	if(!valueData) return ;
	var configs = valueData.valueConfig.split(",");
	var html = "";
	for (var i = 0; i < configs.length; i++) {
		var config = configs[i];
		var vs = config.split(":");
		html=html+"<div class='checkbox'>"
						+"<label style='text-align:center'>"
						+	"<input type='checkbox' name='valueConfig'  value='"+ vs[1] +"' required>"+vs[0] 
						+   "<label id='valueConfig-error' class='error' for='valueConfig' style='display: none;'></label> "
						+"</label>"
					+"</div>";
	}
	$("#addValueSets").html(html);
}


function updateValueChange(){
	var valueCode = $("#updateValueCode").val();
	//生成
	var valueData = getValueData(valueCode);
	if(!valueData) return ;
	var configs = valueData.valueConfig.split(",");
	var html = "";
	for (var i = 0; i < configs.length; i++) {
		var config = configs[i];
		var vs = config.split(":");
		html=html+"<div class='checkbox'>"
						+"<label style='text-align:center'>"
						+	"<input type='checkbox' name='valueConfig'  value='"+ vs[1] +"' required>"+vs[0] 
						+   "<label id='valueConfig-error' class='error' for='valueConfig' style='display: none;'></label> "
						+"</label>"
					+"</div>";
	}
	$("#updateValueSets").html(html);
}



function getValueData(valueCode){
	for (var i = 0; i < valueDatas.length; i++) {
		var valueData = valueDatas[i];
		if(valueData.valueCode==valueCode){
			return valueData;
		}
	}
	return null;
}


function showTree(){
	//展示权限tree
	$.ajaxPost("/role/func/oper/tree",{roleId:$("#roleId").val()},function(result){
		treeData = result.data;
		log(treeData);
		treeObj= $('#tree').treeview({data: treeData,levels:5});
		 
		$('#tree').on('nodeSelected',function(event, data) {
			chooseData = data;
			if( data.type == '3'){
				//展示页面
				renderOper();
			}else{
				$("#show").html("");
			}
		});
		
		$('#tree').on('nodeUnselected',function(event, data) {
			if( data.type == '3'){
				$("#show").html("");
			}
		});
		
		if(chooseData){
			chooseOperSelected(chooseData.id);
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
			expandNode(node);
			break;
		}
	}
}


function chooseOperSelected(){
	var nodes = $('#tree').treeview('getEnabled',null);
	for (var i = 0; i < nodes.length; i++) {
		var node = nodes[i];
		if(node.type==chooseData.type&&node.id==chooseData.id){
			$('#tree').treeview('toggleNodeSelected',[node.nodeId,{ silent: false}]);
			expandNode(node);
			break;
		}
	}
}

function expandNode(node){
	if(node!=null){
		var parentNode = $('#tree').treeview('getParent', node);
		$('#tree').treeview('expandNode',[parentNode.nodeId,{silent: true}]);
		expandNode(parentNode);
	}
}



function renderOper(){
	console.log(chooseData);
	$("#show").html(operTemp.render(chooseData));
}

//添加授权
function addRoleOper(){
	layer.msg('确定添加['+chooseData.operName+"]操作的授权", {
		  time: 0 //不自动关闭
		  ,btn: ['确认', '取消']
		  ,yes: function(index){
		    layer.close(index);
		    $.ajaxPostJson(ctx+"/role/oper/add",{roleId:$("#roleId").val(),operId:chooseData.id,operType:"2"},function(ret){
		    	showTree();
			})
		  }
	});
}


function delRoleOper(){
	layer.msg('确定删除['+chooseData.operName+"]操作的授权", {
		  time: 0 //不自动关闭
		  ,btn: ['确认', '取消']
		  ,yes: function(index){
		    layer.close(index);
		    console.log(chooseData);
		    var sendData = {relaId:chooseData.operRelaId,relaType:"2"};
		    console.log(sendData);
		    $.ajaxPostJson(ctx+"/role/oper/del",sendData,function(ret){
		    	showTree();
			})
		  }
	});
}

//删除权限数据
function deleteRight(rightId){
	layer.msg('确定删除该条的数据权限', {
		  time: 0 //不自动关闭
		  ,btn: ['确认', '取消']
		  ,yes: function(index){
		    layer.close(index);
		    var sendData = {"rightId":rightId};
		    console.log(sendData);
		    $.ajaxPostJson(ctx+"/right/del",sendData,function(ret){
		    	showTree();
			})
		  }
	});
}


/**
 * 弹出新增数据权限窗口数据权限
 */
function addRight(){
	$("#addDbDataRightForm")[0].reset();
	$("#addValueDataRightForm")[0].reset();
	$("#addDbSqlShow").html("");
	$("#addValueSqlShow").html("");
	var relaId = chooseData.operRelaId;
	$("#addDataRightModal").find("input[name=relaId]").val(relaId);
	$("#addDataRightModal").modal("show");
}


function execDbSql(){
	if(addDbDataRightFormVali.form()){
		$("#sqlResultShow").html("");
		var sendData = $("#addDbDataRightForm").serializeObject();
		$.ajaxPost(ctx+"/right/exec/sql",sendData,function(ret){
			$("#sqlResultShow").html(dbShowTemp.render(ret.data));
			$("#sqlResultShowModal").modal("show");
		})
	}
}

function saveDbRightSql(){
	if(addDbDataRightFormVali.form()){
		var sendData = $("#addDbDataRightForm").serializeObject();
		$.ajaxPostJson(ctx+"/right/add/db",sendData,function(ret){
			$("#addDataRightModal").modal("hide");
			showTree();
		})
	}
}

function getRight(rightId){
	var rights = chooseData.rights;
	for (var i = 0; i < rights.length; i++) {
		var right = rights[i];
		if(right.rightId==rightId){
			return right;
		}
	}
	return null;
}




/**
 * 修改db数据权限
 */
function modifyDbRight(rightId){
	$("#updateDbSqlShow").html("");
	$("#updateDbDataRightForm")[0].reset();
	var right = getRight(rightId);
	$("#updateDbDataRightForm").find("input[name=rightId]").val(right.rightId);
	$("#updateDbDataRightForm").find("input[name=relaId]").val(right.relaId);
	$("#updateDbDataRightForm").find("select[name=dbCode]").find("option[value="+right.dbCode+"]").attr("selected",true);;	
	$("#updateDbDataRightForm").find("textarea[name=dataRightSql]").val(right.dataRightSql);	
	$("#updateDbDataRightModal").modal("show");
}


function execUpdateDbSql(){
	if(updateDbDataRightFormVali.form()){
		$("#sqlResultShow").html("");
		var sendData = $("#updateDbDataRightForm").serializeObject();
		sendData.dbCode= $("#updateDbDataRightForm").find("select[name=dbCode]").val();
		console.log(sendData);
		$.ajaxPost(ctx+"/right/exec/sql",sendData,function(ret){
			$("#sqlResultShow").html(dbShowTemp.render(ret.data));
			$("#sqlResultShowModal").modal("show");
		})
	}
}

function updateDbRightSql(){
	if(updateDbDataRightFormVali.form()){
		var sendData = $("#updateDbDataRightForm").serializeObject();
		sendData.dbCode= $("#updateDbDataRightForm").find("select[name=dbCode]").val();
		$.ajaxPostJson(ctx+"/right/update/db",sendData,function(ret){
			$("#updateDbDataRightModal").modal("hide");
			showTree();
		})
	}
}


/**
 * 执行值集 查询
 */
function execValueSql(){
	if(addValueDataRightFormVali.form()){
		$("#sqlResultShow").html("");
		var sendData = $("#addValueDataRightForm").serializeObject();
		var valueConfig = '';
		$("#addValueDataRightForm").find("input[name=valueConfig]:checked").each(function(){
			valueConfig = valueConfig + $(this).val()+",";
		});
		sendData.valueConfig = valueConfig;
		log(sendData);
		$.ajaxPost(ctx+"/right/exec/value",sendData,function(ret){
			$("#sqlResultShow").html(dbShowTemp.render(ret.data));
			$("#sqlResultShowModal").modal("show");
		})
	}
}

/**
 * 执行值集的保存
 */
function saveValueRightSql(){
	if(addValueDataRightFormVali.form()){
		var sendData = $("#addValueDataRightForm").serializeObject();
		var valueConfig = '';
		$("#addValueDataRightForm").find("input[name=valueConfig]:checked").each(function(){
			valueConfig = valueConfig + $(this).val()+",";
		});
		valueConfig=valueConfig.substring(0,valueConfig.length-1);
		sendData.valueConfig = valueConfig;
		log(sendData);
		$.ajaxPostJson(ctx+"/right/add/value",sendData,function(ret){
			$("#addDataRightModal").modal("hide");
			showTree();
		})
	}
}



/**
 * 弹出值集弹出框
 */
function modifyValueRight(rightId){
	$("#updateValueSqlShow").html("");
	$("#updateValueDataRightForm")[0].reset();
	var right = getRight(rightId);
	$("#updateValueDataRightForm").find("input[name=rightId]").val(right.rightId);
	$("#updateValueDataRightForm").find("input[name=relaId]").val(right.relaId);
	$("#updateValueDataRightModal").modal("show");
	
	log(right);
	log(right.valueCode);
	//选中 valueCode 
	$("#updateValueCode").val(right.valueCode);
	updateValueChange();
	//设置选中的checkbox 框
	var valueConfig = right.valueConfig;
	
	var configs = valueConfig.split(",");
	$("#updateValueSets").find("input[name=valueConfig]").each(function(){
		var checkboxValue = $(this).val();
		for (var i = 0; i < configs.length; i++) {
			if(configs[i]==checkboxValue){
				$(this).attr("checked","checked");
			}
		}
	})
	
}


/**
 * 值集修改 预览
 */
function execUpdateValueSql(){
	if(updateValueDataRightFormVali.form()){
		$("#sqlResultShow").html("");
		var sendData = $("#updateValueDataRightForm").serializeObject();
		var valueConfig = '';
		$("#updateValueDataRightForm").find("input[name=valueConfig]:checked").each(function(){
			valueConfig = valueConfig + $(this).val()+",";
		});
		sendData.valueConfig = valueConfig;
		log(sendData);
		$.ajaxPost(ctx+"/right/exec/value",sendData,function(ret){
			$("#sqlResultShow").html(dbShowTemp.render(ret.data));
			$("#sqlResultShowModal").modal("show");
		})
	}
}



/**
 * 修改值集
 */
function updateValueRightSql(){
	if(updateValueDataRightFormVali.form()){
		var sendData = $("#updateValueDataRightForm").serializeObject();
		var valueConfig = '';
		$("#updateValueDataRightForm").find("input[name=valueConfig]:checked").each(function(){
			valueConfig = valueConfig + $(this).val()+",";
		});
		valueConfig=valueConfig.substring(0,valueConfig.length-1);
		sendData.valueConfig = valueConfig;
		log(sendData);
		$.ajaxPostJson(ctx+"/right/update/value",sendData,function(ret){
			$("#updateValueDataRightModal").modal("hide");
			showTree();
		})
	}
}



