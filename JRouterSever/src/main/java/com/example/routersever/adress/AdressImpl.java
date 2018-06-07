package com.example.routersever.adress;

import android.support.annotation.NonNull;

import com.example.routersever.adress.IAdress.IAdress;
import com.jrouter.util.ClassPathUtils;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 16:50.
 * Function :路由內部IP地址管理类
 */
class AdressImpl implements IAdress {

    private HashMap<String, HashMap<String, String>> mModule_IpWapper = new HashMap<>();
    private HashMap<String, String> mPackage_ClassNameWapper = new HashMap<>();

    private AdressImpl() {
    }

    private static class Factory {
        private static final AdressImpl mInstance = new AdressImpl();
    }

    public static IAdress getInstance() {
        return Factory.mInstance;
    }

    @Override
    public String getIp(@NonNull String moduleName, @NonNull String className) {
        if (!mModule_IpWapper.containsKey(moduleName)) {
            fetch(moduleName);
        }
        HashMap<String, String> ips = mModule_IpWapper.get(moduleName);
        return ips.get(className);
    }

    @Override
    public String getIp(@NonNull String moduleName, @NonNull String className, @NonNull String packageName) {
        if (!mModule_IpWapper.containsKey(moduleName)) {
            fetch(moduleName);
        }
        String realName = mPackage_ClassNameWapper.get(packageName + "." + className);
        HashMap<String, String> ips = mModule_IpWapper.get(moduleName);
        return ips.get(realName);
    }

    private void fetch(String moduleName) {
        try {
            HashMap<String, String> ids = new HashMap<>();
            String adress = ClassPathUtils.generateAdressPath(moduleName);
            Class aClass = Class.forName(adress);
            Field[] fields = aClass.getFields();
            for (Field field : fields) {
                field.setAccessible(true);
                String name = field.getName();
                if (name.endsWith(ClassPathUtils.getEndPackage())) {
                    mPackage_ClassNameWapper.put(field.get(null).toString(), name.substring(0, name.length() - ClassPathUtils.getEndPackage().length()));
                } else {
                    ids.put(field.getName(), field.get(null).toString());
                }
            }
            mModule_IpWapper.put(moduleName, ids);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
