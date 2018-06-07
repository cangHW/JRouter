package com.example.routersever.util;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/2 11:55.
 * Function :
 */
public class ExceptionUtil {

    /**
     * 空指针
     */
    public static void NullPointer(String error) {
        throw new NullPointerException(error);
    }

    /**
     * 类转换错误
     */
    public static void ClassCast(String error) {
        throw new ClassCastException(error);
    }

    /**
     * 参数非法
     */
    public static void IllegalArgument(String error) {
        throw new IllegalArgumentException(error);
    }

    /**
     * 运行时异常
     */
    public static void Runtime(String error) {
        throw new RuntimeException(error);
    }
}
