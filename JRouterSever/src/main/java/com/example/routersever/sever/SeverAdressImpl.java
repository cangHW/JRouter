package com.example.routersever.sever;

import android.support.annotation.NonNull;

import com.example.routersever.controller.adress.AdressFactoryImpl;
import com.example.routersever.sever.ISever.ISeverAdress;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/8 18:05.
 * Function :
 */
public class SeverAdressImpl implements ISeverAdress {

    private SeverAdressImpl() {
    }

    private static class Factory {
        private static SeverAdressImpl mInstance = new SeverAdressImpl();
    }

    public static ISeverAdress getInstance() {
        return Factory.mInstance;
    }

    @Override
    public String getIp(@NonNull String moduleName, @NonNull String className) {
        return AdressFactoryImpl.getFactory().getIps().getIp(moduleName, className);
    }

    @Override
    public String getIp(@NonNull String moduleName, @NonNull String className, @NonNull String packageName) {
        return AdressFactoryImpl.getFactory().getIps().getIp(moduleName, className,packageName);
    }
}
