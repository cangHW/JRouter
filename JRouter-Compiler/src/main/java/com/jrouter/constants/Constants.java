package com.jrouter.constants;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/5 11:06.
 * Function :
 */
public interface Constants {

    String LOG_TAG = "JRouter-Compiler";
    String MODULE_NAME = "ModuleName";

    ////////////Annotation  start////////////////////////////////
    String PACKAGE = "com.jrouter.annotation";
    String ANNOTATION_ROUTER_NODE = PACKAGE + ".RouterNode";
    ////////////Annotation  end////////////////////////////////

    ////////////supperclass  start////////////////////////////////
    String UI_SUPPERCLASS = "com.example.routersever.cache.ui.Abstract.AbstractUiCache";
    ////////////supperclass  end////////////////////////////////

    ////////////class filed start////////////////////////////////
    String METHOD_CM = "createMapper";
    String PARAMETER_GROUP = "group";
    String PARAMETER_UI_MAPPER = "mUiMapper";
    ////////////class filed end////////////////////////////////

    ////////////Class Type start///////////////////////////////
    String ACTIVITY = "android.app.Activity";
    String FRAGMENT = "android.app.Fragment";
    String SERVICE = "android.app.Service";
    ////////////Class Type end/////////////////////////////////
}
