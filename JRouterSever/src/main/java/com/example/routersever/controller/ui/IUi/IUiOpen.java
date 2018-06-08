package com.example.routersever.controller.ui.IUi;

import android.content.Context;

import com.example.routersever.message.RouterOpenRequst;
import com.example.routersever.message.RouterOpenResponse;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 14:17.
 * Function :UI跳转实现类接口
 */
public interface IUiOpen {

    /**
     * 打开当前应用界面
     */
    RouterOpenResponse openUi(RouterOpenRequst message);

    /**
     * 打开系统功能
     */
    void openSystem(Context context, String url);

    /**
     * 打开三方应用
     */
    void openThirdApp(Context context, String url);
}
