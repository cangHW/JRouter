package com.example.routersever.controller.data;

import com.example.routersever.controller.data.IData.IData;
import com.example.routersever.controller.data.IDataFactory.IDataFactory;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/13 15:47.
 * Function :
 */
public class DataFactoryImpl implements IDataFactory {

    private DataFactoryImpl() {
    }

    private static class Factory {
        private static final DataFactoryImpl mInstance = new DataFactoryImpl();
    }

    public static IDataFactory getFactory() {
        return Factory.mInstance;
    }

    @Override
    public IData getDatas() {
        return DataImpl.getInstance();
    }
}
