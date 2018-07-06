package com.example.routersever.controller.processors.IProcessors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.routersever.interfaces.IRouterProcessors;

import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/7/2 14:26.
 * Function :
 */
public interface IIntentProcessors {

    void addProcessors(@NonNull IRouterProcessors processors);

    List<IRouterProcessors> getProcessors();
}
