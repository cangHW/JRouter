package com.example.routersever.controller.processors;

import com.example.routersever.controller.processors.IProcessors.IIntentProcessors;
import com.example.routersever.controller.processors.IProcessorsFactory.IProcessorsFactory;

/**
 * Created by Canghaixiao.
 * Time : 2018/7/2 14:38.
 * Function :
 */
public class ProcessorsFactoryImpl implements IProcessorsFactory {

    private ProcessorsFactoryImpl() {
    }

    private static class Factory {
        private static final ProcessorsFactoryImpl mInstance = new ProcessorsFactoryImpl();
    }

    public static IProcessorsFactory getFactory() {
        return Factory.mInstance;
    }

    @Override
    public IIntentProcessors getIntentProcessors() {
        return IntentProcessorsImpl.getInstance();
    }
}
