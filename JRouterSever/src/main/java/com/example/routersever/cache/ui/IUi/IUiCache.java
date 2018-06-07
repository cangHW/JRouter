package com.example.routersever.cache.ui.IUi;

import com.example.routersever.message.RouterOpenRequst;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 11:10.
 * Function :
 */
public interface IUiCache {

    Class getIntentClass(RouterOpenRequst message);
}
