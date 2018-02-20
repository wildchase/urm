package com.panly.urm.manager.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {
	
	public static String toJsonDataFormat(Object obj){
		return JSON.toJSONString(obj,SerializerFeature.WriteDateUseDateFormat);
	}

}
