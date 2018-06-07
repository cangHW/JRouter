package com.example.routersever.cache.ui;

import com.example.routersever.cache.ui.IUi.IUiOpen;
import com.example.routersever.cache.ui.IUiFactory.IUiFactory;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 15:25.
 * Function :
 */
public class UiFactoryImpl implements IUiFactory {

    private UiFactoryImpl() {
    }

    private static class Factory {
        private static final UiFactoryImpl mInstance = new UiFactoryImpl();
    }

    public static IUiFactory getFactory() {
        return Factory.mInstance;
    }

    @Override
    public IUiOpen getOpens() {
        return UiOpenImpl.getInstance();
    }
}
