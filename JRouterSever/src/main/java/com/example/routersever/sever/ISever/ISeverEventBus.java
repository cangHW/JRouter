package com.example.routersever.sever.ISever;

import android.support.annotation.NonNull;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/25 12:30.
 * Function :
 */
public interface ISeverEventBus {

    void register(@NonNull Object o);

    void unRegister(@NonNull Object o);

    void post(@NonNull Object o);
}
