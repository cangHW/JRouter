package com.example.routersever.cache.ui.Abstract;

import com.example.routersever.cache.ui.IUi.IUiCache;
import com.example.routersever.message.RouterOpenRequst;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 11:04.
 * Function :
 */
public abstract class AbstractUiCache implements IUiCache {

    protected HashMap<String, Class> mUiMapper = new HashMap<>();

    protected HashMap<Class, Map<String, Integer>> mParamsMapper = new HashMap<>();

    protected abstract void createMapper(String group);

    @Override
    public Class getIntentClass(RouterOpenRequst message) {
        if (!mUiMapper.containsKey(message.getUrl())) {
            createMapper(message.getGroup());
        }
        return mUiMapper.get(message.getUrl());
    }
}
