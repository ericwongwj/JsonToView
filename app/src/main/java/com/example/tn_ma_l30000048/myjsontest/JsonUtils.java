/**
 * Copyright (C) 2006-2014 Tuniu All rights reserved
 */
package com.example.tn_ma_l30000048.myjsontest;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

/**
 * JSON字符换和对象间转换的工具类
 */
public class JsonUtils {

    private static final Gson GSON = new Gson();

    /**
     * 将json字符串转换成相应的对象
     *
     * @param jsonString json字符串
     * @param classOfT   对象类型的class
     * @param <T>        对象的类型
     * @return 转换后的对象
     * @throws JsonParseException if json is not a valid representation for an object of type classOfT
     */
    public static <T> T decode(String jsonString, Class<T> classOfT) throws RuntimeException {
        return GSON.fromJson(jsonString, classOfT);
    }

    /**
     * 将json字符串转换成相应的对象
     *
     * @param jsonString json字符串
     * @param typeOfT    对象类型的type
     * @param <T>        对象的类型
     * @return 转换后的对象
     * @throws JsonParseException if json is not a valid representation for an object of type typeOfT
     */
    public static <T> T decode(String jsonString, Type typeOfT) throws RuntimeException {
        return GSON.fromJson(jsonString, typeOfT);
    }

    /**
     * 将已经从json转换后的对象（通常是一个Map）转换成对应Bean的对象实例
     *
     * @param object  一个转json换中的过程对象，通常是一个Map，若传值为null，则返回null
     * @param typeOfT 要转换的对象类型的type
     * @param <T>     要转换的对象类型
     * @return 转换后的对象
     * @throws JsonParseException if object is not a valid representation for an object of type typeOfT
     */
    public static <T> T decode(Object object, Type typeOfT) throws RuntimeException {
        String jsonString = GSON.toJson(object);
        return GSON.fromJson(jsonString, typeOfT);
    }

    /**
     * 将一个JavaBean转换成json字符串
     *
     * @param object 待转换的对象
     * @return json字符串
     * @throws JsonParseException if there was a problem while parsing object.
     */
    public static String encode(Object object) throws RuntimeException {
        return GSON.toJson(object);
    }
}

