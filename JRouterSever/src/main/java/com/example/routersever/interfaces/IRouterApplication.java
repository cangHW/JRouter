package com.example.routersever.interfaces;

import android.content.Context;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/2 10:30.
 * Function :组件入口，类似于组件的application
 */
public interface IRouterApplication {

    /**
     * 开始注册组件
     *
     * @param context applicationContext
     */
    void onCreate(Context context);

    /**
     * 移除组件
     */
    void onDestory();
}
