package com.example.routersever.dispatcher.intent.IDispacherFactory;

import com.example.routersever.dispatcher.intent.IDispacher.IDispacherIntent;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/7 10:43.
 * Function :获取UI跳转事件分发处理服务接口
 */
public interface IDispacherFactory {

    IDispacherIntent getDispacheIntent();
}
