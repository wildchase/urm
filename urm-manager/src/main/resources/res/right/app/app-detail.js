var table_have = null;

var table_not_have = null;

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
            "url": ctx+"/acct/role/have",
            "type":"POST",
        	"data": function ( d ) {
                d.acctId=$('#acctId').val();
            }
        },
        "columns": [// 每页展示什么
             { 
            	 "render": function (data, type, full, meta) {
            		 return '<a href="'+ctx+'/role/detail?roleId='+full.roleId+'">'+full.roleCode+'</a>';
                 }
             },
             { "data": "roleName" },
             { "data": "roleDesc" },
             { 
            	 "data": "status",
            	 "render": function (data, type, full, meta) {
                     if( data==1 ){
                    	 return "正常";
                     }else{
                    	 return "禁用";
                     }
                 }
             },
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
	
	//添加角色按钮
	$("#btn-add-role").click(function(){
		$("#roleChooseModal").modal("show");
		table_not_have.ajax.reload();
	});
	
	
	table_not_have = $('#role_not_have_list').DataTable({
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
            "url": ctx+"/acct/role/nothave",
            "type":"POST",
        	"data": function ( d ) {
                d.acctId=$('#acctId').val();
                d.roleName=$('#roleName').val();
            }
        },
        "columns": [// 每页展示什么
             { 
            	 "data": "roleId",
        		 "render": function (data, type, full, meta) {
        			 return '<input type="checkbox"  class="checkchild" name="chooseRow" value="' + data + '"/>';
                 },
            	 "bSortable": false
             },
             { "data": "roleCode" },
             { "data": "roleName" },
             { "data": "roleDesc" },
             { 
            	 "data": "status",
            	 "render": function (data, type, full, meta) {
                     if( data==1 ){
                    	 return "正常";
                     }else{
                    	 return "禁用";
                     }
                 }
             },
             { "data": "createUserName" }
             
        ]
    });
	
	//删除关联关系
	//单个删除
    $('#role_have_list tbody').on('click', '#delRow', function () {
    	var data = table_have.row($(this).parents('tr')).data();
		var sendData = {"deleteIds":data.relaId};
    	layer.msg('确定删除与['+data.roleName+"]角色的关联", {
    		  time: 0 //不自动关闭
    		  ,btn: ['确认', '取消']
    		  ,yes: function(index){
    		    layer.close(index);
    		    $.ajaxPostJson(ctx+"/acct/role/del",sendData,function(ret){
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
		var checkRow = $('#role_not_have_list tbody').find("input[name=chooseRow]:checked");
    	if(checkRow.length==0){
    		layer.alert("请选中要添加的角色");
    		return;
    	}
    	
    	var chooseIds = "";
		    for (var i = 0; i < checkRow.length; i++) {
		    	chooseIds = chooseIds+$(checkRow[i]).val();
		    	if(i!=checkRow.length-1){
		    		chooseIds = chooseIds+",";
		    	}
		}
	    $.ajaxPostJson(ctx+"/acct/role/add",{"acctId":$("#acctId").val(),"roleIds":chooseIds},function(ret){
	    	table_have.draw(false);
	    	table_not_have.draw(false);
	    	$("#roleChooseModal").modal("hide");
		})
	})
	
	$("#btn-add-reset").click(function(){
		$('#role_not_have_list').find("input[type=checkbox]").prop("checked", false);
	})
	
	//展示权限tree
	
	
	
	
});