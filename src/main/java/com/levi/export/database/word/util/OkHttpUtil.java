package com.levi.export.database.word.util;

import okhttp3.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {
    /**
     * get方法
     */
    private static final String METHOD_GET = "GET";
    /**
     * post 方法
     */
    private static final String METHOD_POST = "POST";
    /**
     * json格式设置
     */
    private static final String APPLICATION_JSON = "application/json; charset=utf-8";

    private OkHttpClient okHttpClient;

    private static class OkHttpClientHolder {
        public static OkHttpUtil instance = new OkHttpUtil();
    }

    public static OkHttpUtil newInstance() {
        return OkHttpClientHolder.instance;
    }

    private OkHttpUtil() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public Response getResponseByGet(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * form表单 post请求
     *
     * @param url
     * @param params
     * @return
     */
    public Response getResponseByPost(String url, Map<String, String> params) {
        //1构造RequestBody
        RequestBody body = setRequestBody(params);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.post(body).url(url).build();
        //3 将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //4 执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 带header的form表单post请求
     *
     * @param url
     * @param params
     * @param header
     * @return
     */
    public Response getResponseByPostWithHeader(String url, Map<String, String> params, Map<String, String> header) {
        //1构造RequestBody
        RequestBody body = setRequestBody(params);
        //2 构造Request
        Request.Builder requestBuilder = new Request.Builder();
        if (header != null) {
            for (String key : header.keySet()) {
                requestBuilder.addHeader(key, header.get(key));
            }
        }
        Request request = requestBuilder.post(body).url(url).build();
        //3 将Request封装为Call
        Call call = okHttpClient.newCall(request);
        //4 执行Call，得到response
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * post的请求参数，构造RequestBody
     *
     * @param params
     * @return
     */
    private RequestBody setRequestBody(Map<String, String> params) {
        RequestBody body = null;
        FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        if (params != null) {
            Iterator<String> iterator = params.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                formEncodingBuilder.add(key, params.get(key));
            }
        }
        body = formEncodingBuilder.build();
        return body;

    }

	/**
	 * 发送请求
	 * @param url
	 * @param param
	 * @param headers
	 * @param httpMethod
	 * @param contentType
	 * @return
	 * @throws IOException
	 */
    public String sendRequest(String url, String param, Map<String, String> headers, String httpMethod, String contentType){
        httpMethod = (httpMethod == null || httpMethod.isEmpty()) ? METHOD_POST : httpMethod;
        MediaType JSON_TYPE = (contentType == null || contentType.isEmpty()) ? MediaType.parse(APPLICATION_JSON) : MediaType.parse(contentType);
        if (param==null){
            param="";
        }
        RequestBody body = RequestBody.create(JSON_TYPE, param);
        Request.Builder builder = new Request.Builder();
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                builder.addHeader(key, headers.get(key));
            }
        }
        if (METHOD_GET.equalsIgnoreCase(httpMethod)) {
            body = null;
        }
        try {
			Request request = builder.url(url).method(httpMethod, body).build();
			try (Response response = okHttpClient.newCall(request).execute()) {
				if (response.body() == null) {
					return null;
				}
				return response.body().string();
			}
		}catch (Exception e){
        	e.printStackTrace();
		}
        return null;
    }


}
