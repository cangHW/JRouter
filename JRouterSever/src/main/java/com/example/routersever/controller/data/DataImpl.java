package com.example.routersever.controller.data;

import com.example.routersever.controller.data.IData.IData;

import java.util.HashMap;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/13 12:23.
 * Function :
 */
public class DataImpl implements IData {

    private HashMap<String,Object> mHashMap=new HashMap<>();

    private DataImpl() {
    }

    private static class Factory {
        private static final DataImpl mInstance = new DataImpl();
    }

    public static IData getInstance() {
        return Factory.mInstance;
    }

    @Override
    public <T> void setData(String tag,T t) {
        mHashMap.put(tag,t);
    }

    @Override
    public <T> T getData(String tag,Class<T> tClass) {
        if (mHashMap.containsKey(tag)) {
            Object o = mHashMap.get(tag);
            return tClass.cast(o);
        }
        return null;
    }

    @Override
    public Object getData(String tag) {
        if (mHashMap.containsKey(tag)){
            return mHashMap.get(tag);
        }
        return null;
    }
}
