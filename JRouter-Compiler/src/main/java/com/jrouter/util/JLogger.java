package com.jrouter.util;

import com.jrouter.constants.Constants;
import com.jrouter.util.ILogger.ILogger;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/7 14:09.
 * Function :
 */
public class JLogger implements ILogger {

    private Messager mMessager;

    public JLogger(Messager messager){
        this.mMessager=messager;
    }

    @Override
    public void info(String msg) {
        if (isNotEmpty(msg)) {
            mMessager.printMessage(Diagnostic.Kind.NOTE, Constants.LOG_TAG + msg);
        }
    }

    @Override
    public void error(Throwable error) {
        if (null != error) {
            mMessager.printMessage(Diagnostic.Kind.ERROR, Constants.LOG_TAG + "An exception is encountered, [" + error.getMessage() + "]" + "\n" + formatStackTrace(error.getStackTrace()));
        }
    }

    private String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    private boolean isNotEmpty(final CharSequence cs) {
        boolean isEmpty =  cs == null || cs.length() == 0;
        return !isEmpty;
    }
}
