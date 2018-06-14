package com.example.routersever.controller.cache.ICache;

import com.google.gson.Gson;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/13 17:56.
 * Function :
 */
public interface ICache {

    Gson getGson();

    int getPid();
}
