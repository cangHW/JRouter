package com.example.routersever.dispatcher.IDispacherFactory;

import com.example.routersever.dispatcher.IDispacher.IDispacherData;
import com.example.routersever.dispatcher.IDispacher.IDispacherIntent;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/7 10:43.
 * Function :获取事件分发处理服务接口
 */
public interface IDispacherFactory {

    IDispacherIntent getDispacheIntent();

    IDispacherData getDispacheData();
}
