package com.example.routersever.controller.autowired;

import com.example.routersever.controller.autowired.IAutowired.IAutowired;
import com.example.routersever.controller.autowired.IAutowiredFactory.IAutowiredFactory;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/8 17:38.
 * Function :
 */
public class AutowiredFactoryImpl implements IAutowiredFactory{

    private AutowiredFactoryImpl() {
    }

    private static class Factory {
        private static final AutowiredFactoryImpl mInstance = new AutowiredFactoryImpl();
    }

    public static IAutowiredFactory getFactory() {
        return Factory.mInstance;
    }

    @Override
    public IAutowired getAutowired() {
        return AutowiredImpl.getInstance();
    }
}
