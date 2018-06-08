package com.example.routersever.controller.adress.IAdress;

import android.support.annotation.NonNull;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 16:49.
 * Function :路由內部IP地址管理接口
 */
public interface IAdress {

    /**
     * 通过moduleName与className来获取目标的IP地址
     * （当一个module内存在同名文件时，可能会受到影响）
     *
     * @param moduleName 目标所在的module中gradle里面设置的moduleName
     *                   , 没有设置过的就是默认值DEFAULT
     * @param className  目标的Name
     */
    String getIp(String moduleName, String className);

    /**
     * 通过moduleName、className与packageName来获取目标的IP地址，多了
     * 一个packageName，可以提高查找的准确性，排除同名文件的干扰
     *
     * @param moduleName  目标所在的module中gradle里面设置的moduleName
     *                    , 没有设置过的就是默认值DEFAULT
     * @param className   目标的ClassName
     * @param packageName 目标的packageName
     */
    String getIp(String moduleName, String className, String packageName);
}
