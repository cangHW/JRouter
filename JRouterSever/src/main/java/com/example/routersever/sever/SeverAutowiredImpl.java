package com.example.routersever.sever;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.example.routersever.controller.autowired.AutowiredFactoryImpl;
import com.example.routersever.sever.ISever.ISeverAutowired;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/8 17:48.
 * Function :
 */
public class SeverAutowiredImpl implements ISeverAutowired{

    private SeverAutowiredImpl() {
    }

    private static class Factory {
        private static final SeverAutowiredImpl mInstance = new SeverAutowiredImpl();
    }

    public static ISeverAutowired getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void autowired(@NonNull Activity activity) {
        AutowiredFactoryImpl.getFactory().getAutowired().autowired(activity.getClass().getPackage().getName(),activity.getClass().getSimpleName(),activity);
    }

    @Override
    public void autowired(@NonNull Fragment fragment) {
        AutowiredFactoryImpl.getFactory().getAutowired().autowired(fragment.getClass().getPackage().getName(),fragment.getClass().getSimpleName(),fragment);
    }

    @Override
    public void autowired(@NonNull FragmentActivity activity) {
        AutowiredFactoryImpl.getFactory().getAutowired().autowired(activity.getClass().getPackage().getName(),activity.getClass().getSimpleName(),activity);
    }

    @Override
    public void autowired(@NonNull android.support.v4.app.Fragment fragment) {
        AutowiredFactoryImpl.getFactory().getAutowired().autowired(fragment.getClass().getPackage().getName(),fragment.getClass().getSimpleName(),fragment);
    }

}
