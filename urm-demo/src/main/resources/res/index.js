$(function(){
	$("#menuFrame").height(window.innerHeight);
	
	$(".sidebar-menu").find("a[data!='']").click(function(){
		 var dataUrl = $(this).attr("data");
		 showIFrameUrl(dataUrl)
	});
	var dataUrl = getDataUrl();
	showIFrameUrl(dataUrl);
})

function showIFrameUrl(dataUrl){
	 if(dataUrl){
		 history.replaceState(null, "", "#"+dataUrl);
		 $("#menuFrame").attr("src",dataUrl);
		 $.cookie("dataUrl",dataUrl); 
	 }
}


function getDataUrl(){
	//从url 中获取 
	var hash = window.location.hash;
	if(hash&&hash.indexOf('#')!=-1){
		var dataUrl = hash.substring(1);
		return dataUrl;
	}
	//从cookie中获取
	var dataUrl =  $.cookie("dataUrl");
	return dataUrl;
}




