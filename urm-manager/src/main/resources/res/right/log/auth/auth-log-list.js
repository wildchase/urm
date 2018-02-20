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
            "url": ctx+"/auth/log/page",
            "type":"POST",
        	"data": function ( d ) {
        		var data = $("#queryForm").serializeObject();
        		d = $.extend(d,data);
        		console.log(d);
            }
        },
        "columns": [// 每页展示什么
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
                     "<button id='editRow' title='查看' class='btn btn-primary btn-sm' type='button'><i class='fa fa-edit'></i></button>"+
                     "</div>";
                 }
             }
        ]
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
    
    
    $("#btn-export").click(function(){
    	var data = $("#querySearch").serializeObject();
    	$.download(ctx+"/auth/log/download",data);
    });
    
    $("#btn-re").click(function(){
    	_dataTables.draw(false);
    });
    
    
    //修改
    $('#acctList tbody').on('click', '#editRow', function () {
		var data = _dataTables.row($(this).parents('tr')).data();
    });
    
    
    $("#startCreateTime").datepicker({
        autoclose: true,
        todayHighlight: true,
        language:"zh-CN", 
        format:"yyyy-mm-dd"
    });
    
    $("#endCreateTime").datepicker({
        autoclose: true,
        todayHighlight: true,
        language:"zh-CN", 
        format:"yyyy-mm-dd"
    });
    
    
    
    
});	






