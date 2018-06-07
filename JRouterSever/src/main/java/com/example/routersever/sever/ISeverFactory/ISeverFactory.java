package com.example.routersever.sever.ISeverFactory;

import com.example.routersever.sever.ISever.ISeverFunction;
import com.example.routersever.sever.ISever.ISeverUi;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 10:49.
 * Function :路由内部的sever端接口
 */
public interface ISeverFactory {

    /**
     * 自定义功能（如注册一个接口对象供其他界面获取内容或传递内容等等）
     */
    ISeverFunction getFunctions();

    /**
     * UI跳转功能
     */
    ISeverUi getUIs();
}
