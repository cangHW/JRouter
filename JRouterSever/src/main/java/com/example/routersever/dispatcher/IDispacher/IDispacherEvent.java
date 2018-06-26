package com.example.routersever.dispatcher.IDispacher;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/25 13:43.
 * Function :
 */
public interface IDispacherEvent {

    void init();

    void post(Object o,String message,String type);

}
