package com.example.routersever.controller.adress;

import com.example.routersever.controller.adress.IAdress.IAdress;
import com.example.routersever.controller.adress.IAdressFactory.IAdressFactory;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 16:51.
 * Function :获取IP地址管理器
 */
public class AdressFactoryImpl implements IAdressFactory {

    private AdressFactoryImpl() {
    }

    private static class Factory {
        private static final AdressFactoryImpl mInstance = new AdressFactoryImpl();
    }

    public static IAdressFactory getFactory() {
        return Factory.mInstance;
    }

    @Override
    public IAdress getIps() {
        return AdressImpl.getInstance();
    }
}
