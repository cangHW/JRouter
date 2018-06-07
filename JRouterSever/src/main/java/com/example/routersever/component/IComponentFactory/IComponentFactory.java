package com.example.routersever.component.IComponentFactory;

import com.example.routersever.component.IComponent.IComponent;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 10:38.
 * Function :获取组件管理服务接口
 */
public interface IComponentFactory {

    /**
     * 获取组件管理服务
     */
    IComponent getComponents();
}
