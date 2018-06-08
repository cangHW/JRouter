package com.example.routersever.controller.ui.IUi;

import android.support.annotation.NonNull;

import com.example.routersever.message.ParameterMessage;
import com.example.routersever.message.RouterOpenRequst;

import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 11:10.
 * Function :
 */
public interface IUiCache {

    Class getIntentClass(@NonNull RouterOpenRequst message);

    @NonNull List<ParameterMessage> getClassParams(@NonNull Class c);
}
