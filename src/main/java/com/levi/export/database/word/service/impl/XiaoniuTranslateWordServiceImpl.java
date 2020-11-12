package com.levi.export.database.word.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.levi.export.database.word.service.TranslateWordService;
import com.levi.export.database.word.util.CommonUtil;
import com.levi.export.database.word.util.JsonUtil;
import com.levi.export.database.word.util.OkHttpUtil;
import okhttp3.Response;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 小牛翻译
 * https://niutrans.com/documents/develop/develop_text/free#accessMode
 */
public class XiaoniuTranslateWordServiceImpl implements TranslateWordService {
    private static String API_KEY = "小牛翻译的apikey";
//    private static String XIAONIU_URL = "http://free.niutrans.com/NiuTransServer/translation";
    private static String XIAONIU_URL = "http://api.niutrans.com/NiuTransServer/translation";

    private static Map<String, String> cacheMap = new HashMap<String, String>() {
        {
            put("id", "id");
            put("title", "标题");
            put("name", "名称");
            put("is", "是否");
            put("disable", "禁用");
            put("status", "状态");
            put("type", "类型");
            put("url", "链接");
            put("img", "图片");
            put("video", "视频");
            put("audio", "音频");
        }
    };

    @Override
    public String translateWord(String content, String from, String to) {
        if(CommonUtil.isEmpty(content)){
            return null;
        }
        if ("id".equals(content)){
            return "主键";
        }
        StringBuilder res= new StringBuilder();
        String[] s = content.split("_");
        for (String item : s) {
            //查找缓存
            if (cacheMap.containsKey(item)){
                res.append(cacheMap.get(item));
                continue;
            }
            String translateUrl = getTranslateUrl(item, from, to);
            String word=getTranslateWord(translateUrl);
            if (!CommonUtil.isEmpty(word)){
                cacheMap.put(item,word);
            }
            res.append(word);
        }

        return res.toString();
    }


    public String getTranslateUrl(String content, String from, String to) {
        String sb = null;
        try {
            sb = XIAONIU_URL + "?from=" + from +
                    "&to=" + to + "&apikey=" + API_KEY +
                    "&src_text=" + URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sb;
    }

    public String getTranslateWord(String translateUrl) {
        String word=null;
        Response response = OkHttpUtil.newInstance().getResponseByGet(translateUrl);
        if (response != null && response.isSuccessful() && response.body() != null) {
            try {
                String res = response.body().string();
                JSONObject jsonObject = JsonUtil.getJSONObject(res);
                if (jsonObject != null) {
                    word= jsonObject.getString("tgt_text");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("translateUrl=="+translateUrl+" res=="+word);
        return word;
    }

    public static void main(String[] args) throws Exception {
        TranslateWordService translateWordService = new XiaoniuTranslateWordServiceImpl();

        System.out.println(translateWordService.translateWord("user_id", "en", "zh"));
    }
}
