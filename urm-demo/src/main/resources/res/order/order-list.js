var _dataTables = null;
	
$(function(){
	
	_dataTables = $('#orderList').DataTable({
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
        "lengthMenu": [[10, 15, 30],[10, 15, 30]],
        "lengthChange":true,   //
        "serverSide": true,  	// 是否服务端分页
        "ajax": {// ajax 配置
            "url": ctx+"/order/page",
            "type":"POST",
        	"data": function (d) {
        		var data = $("#queryForm").serializeObject();
        		d = $.extend(d,data);
            }
        },
        "columns": [// 每页展示什么
             { "data": "orderId"},
             { "data": "orderType" },
             { "data": "orderAreaId"},
             { "data": "sellerId" },
             { "data": "mallId" },
             { "data": "buyerId" },
             { "data": "orderAmount" },
             { "data": "createTime" },
             { 
            	 "render": function (data, type, full, meta) {
                     return "<div class='btn-group'>"+
                     "<button id='editRow' title='修改价格' class='btn btn-primary btn-sm' type='button'><i class='fa fa-edit'></i></button>"+
                     "<button id='delRow'  title='删除' class='btn btn-primary btn-sm' type='button'><i class='fa fa-trash-o'></i></button>"+
                     "</div>";
                 }
             }
        ]
    });
  
	$(".checkall").click(function () {
		 if ($(this).prop("checked") === true) {
	            $(".checkchild").prop("checked", $(this).prop("checked"));
	            $('#orderList tbody tr').addClass('selected');
	     } else {
	            $(".checkchild").prop("checked", false);
	            $('#orderList tbody tr').removeClass('selected');
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
    
    
    var editVali = $("#editForm").validate();
    
    $("#btn-re").click(function(){
    	_dataTables.draw(false);
    });
    
    $('#btn-edit-submit').click(function(){
    	if(editVali.form()){
    		//提交请求
    		var data = $("#editForm").serializeObject();
    		var sendData = JSON.stringify(data);
    		console.log(sendData);
    		$.ajaxPostJson(ctx+"/order/update/amount",sendData,function(ret){
    			editVali.resetForm();
    			$("#editModal").modal("hide");
    			_dataTables.draw(false);
    		})
    	}
    });
    
    //修改
    $('#orderList tbody').on('click', '#editRow', function () {
		var data = _dataTables.row($(this).parents('tr')).data();
		$("#editModal").find("input[name=orderId]").val(data.orderId);
		$("#editModal").find("input[id=orderAmount]").val(data.orderAmount);
		$("#editModal").find("input[name=orderAmount]").val(data.orderAmount);
		$("#editModal").modal("show");
    });
    
    //单个删除
    $('#orderList tbody').on('click', '#delRow', function () {
    	var data = _dataTables.row($(this).parents('tr')).data();
		var sendData = {"orderId":data.orderId};
    	layer.msg('你确定删除订单['+data.orderId+"]", {
    		  time: 0 //不自动关闭
    		  ,btn: ['确认', '取消']
    		  ,yes: function(index){
    		    layer.close(index);
    		    $.ajaxPostJson(ctx+"/order/delete",sendData,function(ret){
    				_dataTables.draw(false);
    			})
    		  }
    	});
    });
  
	
});	






