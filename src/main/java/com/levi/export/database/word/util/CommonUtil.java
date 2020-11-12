package com.levi.export.database.word.util;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtil {
    public static boolean isEmpty(String s) {
        return StringUtils.isEmpty(s);
    }

    public static boolean isEmpty(Object[] s) {
        return s==null||s.length==0;
    }

    public static boolean isEmpty(Collection list) {
        return CollectionUtils.isEmpty(list);
    }

    public static boolean isEmpty(Map map) {
        return MapUtils.isEmpty(map);
    }

    public static boolean equals(Object source, Object target) {
        return Objects.equals(source, target);
    }

    public static String readFileToString(String path){
        File file=new File(path);
        if (!file.exists()){
            return null;
        }
        try {
            return FileUtils.readFileToString(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> readLines(String path){
        File file=new File(path);
        if (!file.exists()){
            return null;
        }
        try {
            return FileUtils.readLines(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeStringToFile(String path,String content){
        writeStringToFile(path,content,false);
    }

    /**
     *
     * @param path
     * @param content
     * @param append 是否追加 true 追加
     */
    public static void writeStringToFile(String path,String content,boolean append){
        File file=new File(path);
        try {
            FileUtils.writeStringToFile(file, content, "UTF-8",append);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    public static InputStream getFileInputStream(String fileName) {
        if (fileName==null){
            return null;
        }
        File file=new File(fileName);
        if (!file.exists()){
            return null;
        }
        try {
            InputStream in = new FileInputStream(file);
            return in;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static OutputStream getFileOutputStream(String fileName) {
        try {
            FileOutputStream out = new FileOutputStream(fileName);
            return out;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Long by string
     * @param str
     * @return
     */
    public static Long getLongByStr(String str){
        if (str==null||str.isEmpty()){
            return null;
        }
        try {
            return Long.valueOf(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取Long by string
     * @param str
     * @return
     */
    public static Integer getIntegerByStr(String str){
        if (str==null||str.isEmpty()){
            return null;
        }
        try {
            return Integer.valueOf(str);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到用户目录
     * @return
     */
    public static String getUserHomeDir(){
        String dir = System.getProperties().getProperty("user.home");
        return dir;
    }

    /**
     * 得到随机数
     * @param start
     * @param end
     * @return
     */
    public static int getRandomInt(int start,int end){
        int min=Math.min(start,end);
        int max=Math.max(start,end);
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

    /**
     * String 转date
     *
     * @param dateStr
     * @param pattern
     * @return
     */
    public static Date str2Date(String dateStr, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间转字符串
     * @param date
     * @param pattern
     * @return
     */
    public static String date2Str(Date date,String pattern) {
        if (null == date){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static void main(String[] args) {
        getUserHomeDir();
    }
}
