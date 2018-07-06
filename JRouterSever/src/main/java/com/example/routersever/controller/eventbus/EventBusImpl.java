package com.example.routersever.controller.eventbus;

import android.support.annotation.NonNull;

import com.example.routersever.controller.eventbus.IEventBus.IEventBus;

import java.lang.ref.SoftReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/22 14:07.
 * Function :
 */
class EventBusImpl implements IEventBus{

    private HashMap<String,HashMap<String,ArrayList<SoftReference<Object>>>> mParams_Class_Wapper;

    private EventBusImpl() {
        mParams_Class_Wapper=new HashMap<>();
    }

    private static class Factory {
        private static final EventBusImpl mInstance = new EventBusImpl();
    }

    public static IEventBus getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void register(@NonNull Object o) {
        Class aClass= o.getClass();
        Method[] methods=aClass.getDeclaredMethods();
        for (Method method:methods){
            if (method.getName().equals(METHOD_MAIN)){
                register(o,method,METHOD_MAIN);
            }
        }
    }

    @Override
    public void unRegister(@NonNull Object o) {
        for (Map.Entry<String, HashMap<String, ArrayList<SoftReference<Object>>>> entry : mParams_Class_Wapper.entrySet()) {
            HashMap<String, ArrayList<SoftReference<Object>>> hashMap=entry.getValue();
            for (Map.Entry<String, ArrayList<SoftReference<Object>>> listEntry:hashMap.entrySet()){
                ArrayList<SoftReference<Object>> objects=listEntry.getValue();
                for (int i=0;i<objects.size();i++){
                    SoftReference<Object> activity=objects.get(i);
                    if (activity!=null){
                        if (activity.get()==null||activity.get()==o){
                            objects.set(i,null);
                        }
                    }
                }
            }
        }
    }

    @Override
    public List<Object> getObject(String methodType,String parameterType) {
        if (mParams_Class_Wapper.containsKey(methodType)){
            HashMap<String,ArrayList<SoftReference<Object>>> parameterHashMap=mParams_Class_Wapper.get(methodType);
            if (parameterHashMap.containsKey(parameterType)){
                ArrayList<SoftReference<Object>> activitys=parameterHashMap.get(parameterType);
                ArrayList<Object> objects=new ArrayList<>();
                for (int i=0;i<activitys.size();i++){
                    SoftReference<Object> reference=activitys.get(i);
                    if (reference==null||reference.get()==null){
                        activitys.set(i,null);
                    }else {
                        objects.add(reference.get());
                    }
                }
                return objects;
            }
        }
        return null;
    }

    private void register(Object o,Method method,String Name){
        Class<?>[] classes=method.getParameterTypes();
        if (classes.length>0) {
            Class parameterClass = classes[0];
            String parameterName=parameterClass.getName();
            ArrayList<SoftReference<Object>> activitys;
            if (mParams_Class_Wapper.containsKey(Name)){
                HashMap<String,ArrayList<SoftReference<Object>>> objectHashMap=mParams_Class_Wapper.get(Name);
                if (objectHashMap.containsKey(parameterName)){
                    activitys=objectHashMap.get(parameterName);
                }else {
                    activitys=new ArrayList<>();
                    objectHashMap.put(parameterName,activitys);
                }
            }else {
                HashMap<String,ArrayList<SoftReference<Object>>> objectHashMap=new HashMap<>();
                activitys=new ArrayList<>();
                objectHashMap.put(parameterName,activitys);
                mParams_Class_Wapper.put(Name,objectHashMap);
            }
            for (int i=0;i<activitys.size();i++){
                SoftReference<Object> reference=activitys.get(i);
                if (reference==null||reference.get()==null){
                    activitys.set(i,new SoftReference<>(o));
                    return;
                }
            }
            activitys.add(new SoftReference<>(o));
        }
    }
}
