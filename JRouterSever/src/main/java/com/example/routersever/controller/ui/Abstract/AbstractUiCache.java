package com.example.routersever.controller.ui.Abstract;

import android.support.annotation.NonNull;

import com.example.routersever.controller.ui.IUi.IUiCache;
import com.example.routersever.message.ParameterMessage;
import com.example.routersever.message.RouterOpenRequst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 11:04.
 * Function :UI数据缓存类
 */
public abstract class AbstractUiCache implements IUiCache {

    protected HashMap<String, Class> mUi_Mapper = new HashMap<>();
    protected HashMap<Class, HashMap<String, String>> mParams_Key_Type_Mapper = new HashMap<>();
    protected HashMap<Class, HashMap<String, Boolean>> mParams_Key_CanEmpty_Mapper = new HashMap<>();

    protected abstract void createMapper(String group);

    @Override
    public Class getIntentClass(@NonNull RouterOpenRequst message) {
        if (!mUi_Mapper.containsKey(message.getUrl())) {
            createMapper(message.getGroup());
        }
        return mUi_Mapper.get(message.getUrl());
    }

    @Override
    public @NonNull List<ParameterMessage> getClassParams(@NonNull Class c) {
        List<ParameterMessage> messages = new ArrayList<>();
        HashMap<String, String> name_type = mParams_Key_Type_Mapper.get(c);
        HashMap<String, Boolean> name_empty = mParams_Key_CanEmpty_Mapper.get(c);

        if (name_type!=null) {
            for (Map.Entry<String, String> entry : name_type.entrySet()) {
                ParameterMessage message = ParameterMessage.builder();
                message.setKey(entry.getKey()).setType(entry.getValue());
                messages.add(message);
            }
        }

        for (ParameterMessage message : messages) {
            message.setCanEmpty(name_empty.get(message.getKey()));
        }

        return messages;
    }
}
