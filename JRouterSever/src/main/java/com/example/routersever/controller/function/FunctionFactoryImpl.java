package com.example.routersever.controller.function;

import com.example.routersever.controller.function.IFunction.IFunction;
import com.example.routersever.controller.function.IFunctionFactory.IFunctionFactory;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/8 17:58.
 * Function :
 */
public class FunctionFactoryImpl implements IFunctionFactory {

    private FunctionFactoryImpl() {
    }

    private static class Factory {
        private static final FunctionFactoryImpl mInstance = new FunctionFactoryImpl();
    }

    public static IFunctionFactory getInstance() {
        return Factory.mInstance;
    }

    @Override
    public IFunction getFunction() {
        return FunctionImpl.getInstance();
    }
}
