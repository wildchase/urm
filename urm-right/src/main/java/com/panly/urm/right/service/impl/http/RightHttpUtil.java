package com.panly.urm.right.service.impl.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.panly.urm.right.exception.RightException;
import com.panly.urm.web.JsonResult;

public class RightHttpUtil {
	
	private final static Logger logger  = LoggerFactory.getLogger(RightHttpUtil.class);
	
	public final static Charset UTF8 = Charset.forName("UTF-8");
	
	private static RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000)
			.setConnectTimeout(15000).setSocketTimeout(5000).build();
	
	
	public static <T> T httpPostJsonObject(String url, Object req,Class<T> clazz){
		String send = JSON.toJSONString(req);
		logger.info("post json url[{}] send[{}]",url,send);
		StringEntity entity = new StringEntity(send,UTF8);
		HttpUriRequest httpPost = RequestBuilder.post(url).setConfig(requestConfig).addHeader("content-type", "application/json;charset=UTF-8").setEntity(entity).build();
		return httpSendForObject(url, httpPost, clazz);
	}
	
	public static <T> T httpPostParamsObject(String url, Map<String, String> params,Class<T> clazz){
		logger.info("post json url[{}] send[{}]",url,params.toString());
		List<NameValuePair> list = new ArrayList<>();
		Set<String> keys = params.keySet();
		for (String key : keys) {
			list.add(new BasicNameValuePair(key, params.get(key)));
		}
		NameValuePair[] nvps = list.toArray(new NameValuePair[list.size()]);
		HttpUriRequest httpPost = RequestBuilder.post(url).setConfig(requestConfig).addParameters(nvps).build();
		return httpSendForObject(url, httpPost, clazz);
	}
	
	public static <T> List<T> httpPostJsonList(String url, Object req,Class<T> clazz){
		String send = JSON.toJSONString(req);
		logger.info("post json url[{}] send[{}]",url,send);
		StringEntity entity = new StringEntity(send,UTF8);
		HttpUriRequest httpPost = RequestBuilder.post(url).setConfig(requestConfig).addHeader("content-type", "application/json;charset=UTF-8").setEntity(entity).build();
		return httpSendForList(url, httpPost, clazz);
	}
	
	public static <T> List<T> httpPostParamsList(String url, Map<String, String> params,Class<T> clazz){
		logger.info("post json url[{}] send[{}]",url,params.toString());
		List<NameValuePair> list = new ArrayList<>();
		Set<String> keys = params.keySet();
		for (String key : keys) {
			list.add(new BasicNameValuePair(key, params.get(key)));
		}
		NameValuePair[] nvps = list.toArray(new NameValuePair[list.size()]);
		HttpUriRequest httpPost = RequestBuilder.post(url).setConfig(requestConfig).addParameters(nvps).build();
		return httpSendForList(url, httpPost, clazz);
	}
	
	
	private static <T> T httpSendForObject(String url, HttpUriRequest request,Class<T> clazz){
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			// 创建默认的httpClient实例.
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(request);
			HttpEntity respEntity = response.getEntity();
			String responseContent = EntityUtils.toString(respEntity, UTF8);
			logger.debug("auth ret:{}", responseContent);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RightException("校验请求报错:" + response.getStatusLine().getStatusCode());
			}
			JsonResult ret = JSON.parseObject(responseContent, JsonResult.class);
			if (StringUtils.equals(ret.getStatus(), JsonResult.SUCCESS)) {
				JSONObject j = (JSONObject) ret.getData();
				T resp = j.toJavaObject(clazz);
				return resp;
			} else {
				throw new RightException(ret.getError());
			}
		} catch (ClientProtocolException e) {
			throw new RightException("校验请求报错", e);
		} catch (IOException e) {
			throw new RightException("校验请求报错", e);
		} finally {
			try {
				// 关闭连接,释放资源
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static <T> List<T> httpSendForList(String url, HttpUriRequest request,Class<T> clazz){
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			// 创建默认的httpClient实例.
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(request);
			HttpEntity respEntity = response.getEntity();
			String responseContent = EntityUtils.toString(respEntity, UTF8);
			logger.debug("auth ret:{}", responseContent);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RightException("校验请求报错:" + response.getStatusLine().getStatusCode());
			}
			JsonResult ret = JSON.parseObject(responseContent, JsonResult.class);
			if (StringUtils.equals(ret.getStatus(), JsonResult.SUCCESS)) {
				JSONArray j = (JSONArray) ret.getData();
				List<T> resp = j.toJavaList(clazz);
				return resp;
			} else {
				throw new RightException(ret.getError());
			}
		} catch (ClientProtocolException e) {
			throw new RightException("校验请求报错", e);
		} catch (IOException e) {
			throw new RightException("校验请求报错", e);
		} finally {
			try {
				// 关闭连接,释放资源
				if (response != null) {
					response.close();
				}
				if (httpClient != null) {
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
