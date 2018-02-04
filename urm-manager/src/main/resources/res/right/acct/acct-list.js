var _dataTables = null;
	
$(function(){
	
	_dataTables = $('#acctList').DataTable({
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
        "lengthMenu": [[10, 15, 30], [10, 15, 30, "All"]],
        "lengthChange":true,   //
        "serverSide": true,  	// 是否服务端分页
        "ajax": {// ajax 配置
            "url": ctx+"/acct/page",
            "type":"POST",
        	"data": function ( d ) {
                d.status=$('#status').val();
                d.acctName=$('#acctName').val();
            }
        },
        "columns": [// 每页展示什么
             {
                 "sClass": "text-center",
                 "data": "acctId",
                 "render": function (data, type, full, meta) {
                     return '<input type="checkbox"  class="checkchild" name="deleteRow" value="' + data + '"/>';
                 },
                 "bSortable": false
             },
             { 
            	 "data": "acctId",
        		 "render": function (data, type, full, meta) {
                     return '<a href="'+ctx+'/acct/detail?acctId='+data+'">'+data+'</a>';
                 },
            	 "bSortable": false
             },
             { "data": "acctName" },
             { "data": "phone" },
             { "data": "email" },
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
             { "data": "lastLoginTime" },
             { "data": "lastLoginIp" },
             { "data": "createUserName" },
             { "data": "createTime" },
             { 
            	 "render": function (data, type, full, meta) {
                     return "<div class='btn-group'>"+
                     "<button id='editRow' title='修改' class='btn btn-primary btn-sm' type='button'><i class='fa fa-edit'></i></button>"+
                     "<button id='delRow'  title='删除' class='btn btn-primary btn-sm' type='button'><i class='fa fa-trash-o'></i></button>"+
                     "</div>";
                 }
             }
        ]
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
	
    $(document).keydown(function(event){ 
		if(event.keyCode == 13){ // 绑定回车
			_dataTables.ajax.reload();
		} 
    });
    
    $("#btn-query").click(function(){
    	_dataTables.ajax.reload();
    });
    
    $("#btn-query-reset").click(function(){
    	$("#queryForm")[0].reset();
    	_dataTables.ajax.reload();
    });
    
    
    $("#btn-add").click(function(){
    	$("#addModal").modal("show");
    });
    
    var addVali = $("#addForm").validate();
    
    var editVali = $("#editForm").validate();
    
    $('#btn-add-submit').click(function(){
    	if(addVali.form()){
    		//提交请求
    		var data = $("#addForm").serializeObject();
    		var sendData = JSON.stringify(data);
    		console.log(sendData);
    		$.ajaxPostJson(ctx+"/acct/add",sendData,function(ret){
    			addVali.resetForm();
    			$("#addModal").modal("hide");
    			_dataTables.draw(false);
    		})
    	}
    });
    
    //清空add
    $('#btn-add-reset').click(function(){
    	addVali.resetForm();
    	$("#addForm")[0].reset();
    });
    
    $("#btn-delAll").click(function(){
    	var checkRow = $('#acctList tbody').find("input[name=deleteRow]:checked");
    	if(checkRow.length==0){
    		layer.alert("请选中要删除行");
    		return;
    	}
    	layer.msg('你确定删除账户['+checkRow.length+"]个账户", {
  		  time: 0 //不自动关闭
  		  ,btn: ['确认', '取消']
  		  ,yes: function(index){
  		    layer.close(index);
  		    var deleteIds = "";
  		    for (var i = 0; i < checkRow.length; i++) {
  		    	deleteIds = deleteIds+$(checkRow[i]).val();
  		    	if(i!=checkRow.length-1){
  		    		deleteIds = deleteIds+",";
  		    	}
			}
  		    $.ajaxPostJson(ctx+"/acct/delete",{"deleteIds":deleteIds},function(ret){
  				_dataTables.draw(false);
  			})
  		  }
  	});
    	
    });
    
    $("#btn-export").click(function(){
    	var data = $("#querySearch").serializeObject();
    	$.download(ctx+"/acct/download",data);
    });
    
    $("#btn-re").click(function(){
    	_dataTables.draw(false);
    });
    
    
    $('#btn-edit-submit').click(function(){
    	if(editVali.form()){
    		//提交请求
    		var data = $("#editForm").serializeObject();
    		var sendData = JSON.stringify(data);
    		console.log(sendData);
    		$.ajaxPostJson(ctx+"/acct/edit",sendData,function(ret){
    			addVali.resetForm();
    			$("#editModal").modal("hide");
    			_dataTables.draw(false);
    		})
    	}
    });
    
    //修改
    $('#acctList tbody').on('click', '#editRow', function () {
		var data = _dataTables.row($(this).parents('tr')).data();
		$("#editModal").find("input[name=acctId]").val(data.acctId);
		$("#editModal").find("input[name=acctName]").val(data.acctName);
		$("#editModal").find("input[name=phone]").val(data.phone);
		$("#editModal").find("input[name=email]").val(data.email);
		$("#editModal").find("select[name=status]").val(data.status);	
		$("#editModal").modal("show");
    });
    
    //单个删除
    $('#acctList tbody').on('click', '#delRow', function () {
    	var data = _dataTables.row($(this).parents('tr')).data();
		var sendData = {"deleteIds":data.acctId};
    	layer.msg('你确定删除账户['+data.acctName+"]", {
    		  time: 0 //不自动关闭
    		  ,btn: ['确认', '取消']
    		  ,yes: function(index){
    		    layer.close(index);
    		    $.ajaxPostJson(ctx+"/acct/delete",sendData,function(ret){
    				_dataTables.draw(false);
    			})
    		  }
    	});
    });
    
    
    
	
});	






