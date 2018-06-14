package com.example.routersever.util;

import android.text.TextUtils;

import com.example.routersever.controller.cache.CacheFactoryImpl;
import com.google.gson.Gson;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/13 17:42.
 * Function :
 */
public class DataUtil {

    public static <T> boolean checkDataType(T t) {
        Class aClass = t.getClass();
        if (aClass == Integer.class) {
            return true;
        } else if (aClass == Long.class) {
            return true;
        } else if (aClass == Boolean.class) {
            return true;
        } else if (aClass == Float.class) {
            return true;
        } else if (aClass == Double.class) {
            return true;
        } else if (aClass == String.class) {
            return true;
        }
        return false;
    }

    public static <T> String isJavaBean(T t) {
        Gson gson = CacheFactoryImpl.getFactory().getCache().getGson();
        try {
            String s = gson.toJson(t);
            if (!TextUtils.isEmpty(s)) {
                return s;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
