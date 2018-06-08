package com.example.routersever.controller.autowired;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.example.routersever.controller.autowired.IAutowired.IAutowired;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/8 17:37.
 * Function :
 */
class AutowiredImpl implements IAutowired{

    private AutowiredImpl() {
    }

    private static class Factory {
        private static final AutowiredImpl mInstance = new AutowiredImpl();
    }

    public static IAutowired getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void autowired(Object o) {

    }
}
