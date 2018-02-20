(function($) {
	$.fn.extend({
		serializeObject : function() {
			if (this.length > 1) {
				return false;
			}
			var arr = this.serializeArray();
			var obj = new Object;
			$.each(arr, function(k, v) {
				obj[v.name] = v.value;
			});
			return obj;
		}
	});
	
	/**
	 * 
	 */
	$.ajaxPostJson = function(url, data, successfn, errorfn) {
		if(data instanceof Object){
			var data = JSON.stringify(data);
		}
		$.ajax({
			type : "post",
			cache:false,
			async : true,
			data : data,
			url : url,
			dataType : 'json',
			contentType: 'application/json;charset=utf-8',
			success : function(d) {
				if (d.status == '1') {
					successfn(d);
				} else {
					if (errorfn) {
						errorfn(e);
					} else {
						layer.msg(d.error);
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				var ret = XMLHttpRequest.responseJSON;
				if(ret instanceof Object){
					layer.msg(ret.status+":"+ret.error);
				}else{
					layer.msg(XMLHttpRequest.responseText);
				}
			}
		});
	};
	
	/**
	 * 传入的data是object
	 */
	$.ajaxPost = function(url, data, successfn, errorfn) {
		if(!data instanceof Object){
			alert("data is not a object");
			return false;
		}
		$.ajax({
			type : "post",
			cache:false,
			async : true,
			data : data,
			url : url,
			dataType : 'json',
			success : function(d) {
				if (d.status == '1') {
					successfn(d);
				} else {
					if (errorfn) {
						errorfn(e);
					} else {
						layer.msg(d.error);
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				var ret = XMLHttpRequest.responseJSON;
				if(ret instanceof Object){
					alert(ret.status+":"+ret.error);
				}else{
					alert(XMLHttpRequest.responseText);
				}
			}
		});
	};
	
	$.download=function downloadFile(url, data) {
		try {
			$("#downloadIframe").remove();
			var elemIF = document.createElement("iframe");
			elemIF.id = 'downloadIframe';
			elemIF.src = urlencode(url, data);
			console.log(elemIF.src);
			elemIF.style.display = "none";
			document.body.appendChild(elemIF);
		} catch (e) {
		}
	}

	function urlencode(url, data) {
		if (!data) {
			return url;
		}
		if (url.indexOf('?') == -1) {
			url = url + '?'
		} else {
			url = url + '&'
		}
		var _result = [];
		for ( var key in data) {
			var value = data[key];
			if (value.constructor == Array) {
				value.forEach(function(_value) {
					_result.push(key + "=" + _value);
				});
			} else {
				_result.push(key + '=' + value);
			}
		}
		return url + _result.join('&');
	}
	
})(jQuery);


function log(obj){
	if(window.console){
		console.log(obj);
	}
}

$(function() {

});
