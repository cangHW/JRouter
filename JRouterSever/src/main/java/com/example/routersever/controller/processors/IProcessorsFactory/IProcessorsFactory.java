package com.example.routersever.controller.processors.IProcessorsFactory;

import com.example.routersever.controller.processors.IProcessors.IIntentProcessors;

/**
 * Created by Canghaixiao.
 * Time : 2018/7/2 14:36.
 * Function :
 */
public interface IProcessorsFactory {

    IIntentProcessors getIntentProcessors();

}
