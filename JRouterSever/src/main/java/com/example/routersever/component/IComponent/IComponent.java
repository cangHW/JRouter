package com.example.routersever.component.IComponent;

import android.support.annotation.NonNull;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 10:33.
 * Function :组件管理接口
 */
public interface IComponent {

    /**
     * 注册一个组件
     *
     * @param componentName 组件完整地址（包名+类名）
     */
    void RegisterComponent(@NonNull String componentName);

    /**
     * 移除一个组件
     *
     * @param componentName 组件完整地址（包名+类名）
     */
    void unRegisterComponent(@NonNull String componentName);
}
