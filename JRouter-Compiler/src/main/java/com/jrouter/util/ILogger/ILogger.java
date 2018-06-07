package com.jrouter.util.ILogger;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/7 14:09.
 * Function :
 */
public interface ILogger {

    void info(String msg);

    void error(Throwable error);
}
