$(function(){
	$("#menuFrame").height(window.innerHeight);
	
	$(".sidebar-menu").find("a[data!='']").click(function(){
		 var dataUrl = $(this).attr("data");
		 if(dataUrl){
			 log(dataUrl);
			 log(window.location);
			 //window.history.pushState(json,"","data")
			 history.replaceState(null, "", "#"+dataUrl);
			 $("#menuFrame").attr("src",dataUrl);
		 }
	});
	
	
	
	
})


