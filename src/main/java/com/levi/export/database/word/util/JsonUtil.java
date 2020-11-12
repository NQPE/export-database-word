package com.levi.export.database.word.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    public static <T> T getObject(String text, Class<T> clazz) {
        if (text==null){
            return null;
        }
        try{
            return JSON.parseObject(text, clazz);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> getList(String text, Class<T> clazz) {
        if (text==null||text.isEmpty()){
            return null;
        }
        try{
            return JSON.parseArray(text, clazz);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<Map<String, Object>> getListMap(String json) {
        return JSON.parseObject(json, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    public static <T> Map<String, T> getMap(String json) {
        if (json==null||json.isEmpty()){
            return null;
        }
        try {
            return JSON.parseObject(json, new TypeReference<Map<String, T>>() {
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

	public static <T> T getObject(String text, TypeReference<T> type){
        if (text==null||text.isEmpty()){
            return null;
        }
        try {
            return JSON.parseObject(text,type);
        }catch (Exception e){
            e.printStackTrace();
        }
    	return null;
	}

    public static String getJsonString(Object bean) {
        if (bean==null){
            return null;
        }
        return JSON.toJSONString(bean, SerializerFeature.DisableCircularReferenceDetect);
    }

    public static JSONObject getJSONObject(String json) {
        if (json==null){
            return null;
        }
        return JSON.parseObject(json);
    }

    public static JSONArray getJSONArray(String json) {
        return JSON.parseArray(json);
    }

    /**
     * 将javabean转化为map
     * 思路是先转化为json再转化为map 效率不高
     * 不要过度使用
     *
     * @param bean
     * @return
     */
    public static Map<String, Object> getMapByBean(Object bean) {
        String json = JSON.toJSONString(bean);
        Map<String, Object> map = new HashMap<>();
        map = getMap(json);
        return map;
    }

    /**
     * 将list转化为list<map>
     * 思路是先转化为json再转化为list 效率不高
     * 不要过度使用
     *
     * @param list
     * @return
     */
    public static List<Map<String, Object>> getListMapByListBean(List list) {
        String json = JSON.toJSONString(list);
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = getListMap(json);
        return mapList;
    }
}
