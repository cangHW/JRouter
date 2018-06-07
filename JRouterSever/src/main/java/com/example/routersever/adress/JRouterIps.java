package com.example.routersever.adress;

import com.example.routersever.adress.IAdress.IAdress;
import com.example.routersever.adress.IAdressFactory.IAdressFactory;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 16:51.
 * Function :获取IP地址管理器
 */
public class JRouterIps implements IAdressFactory {

    private JRouterIps() {
    }

    private static class Factory {
        private static final JRouterIps mInstance = new JRouterIps();
    }

    public static IAdressFactory getFactory() {
        return Factory.mInstance;
    }

    @Override
    public IAdress getIps() {
        return AdressImpl.getInstance();
    }
}
