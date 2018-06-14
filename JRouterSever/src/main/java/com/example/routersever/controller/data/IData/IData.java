package com.example.routersever.controller.data.IData;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/13 12:20.
 * Function :
 */
public interface IData {

    <T> void setData(String tag,T t);

    <T> T getData(String tag,Class<T> tClass);

    Object getData(String tag);
}
