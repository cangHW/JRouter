package com.jrouter.constants;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/5 11:06.
 * Function :
 */
public interface Constants {

    String LOG_TAG = "JRouter-Compiler";
    String MODULE_NAME = "ModuleName";
    String AUTOWIRED = "Autowired";

    ////////////Annotation  start////////////////////////////////
    String PACKAGE = "com.jrouter.annotation";
    String ANNOTATION_ROUTER_NODE = PACKAGE + ".RouterNode";
    String ANNOTATION_PARAMS_NODE = PACKAGE + ".AutowiredNode";
    ////////////Annotation  end////////////////////////////////

    ////////////supper  start////////////////////////////////
    String UI_SUPPERCLASS = "com.example.routersever.controller.ui.Abstract.AbstractUiCache";
    String AUTOWIRED_SUPPERINTERFACE = "com.example.routersever.controller.autowired.IAutowired.IInject";
    ////////////supper  end////////////////////////////////

    ////////////class filed start////////////////////////////////
    String METHOD_CM = "createMapper";
    String PARAMETER_GROUP = "group";
    String FIELD_MAPPER_UI = "mUi_Mapper";
    String FIELD_MAPPER_PARAMS_TYPE = "mParams_Key_Type_Mapper";
    String FIELD_MAPPER_PARAMS_CANEMPTY = "mParams_Key_CanEmpty_Mapper";

    String METHOD_AUTOWIRED = "inject";
    String PARAMETER_OBJECT = "o";
    String METHOD_GETVIEW = "getView";
    ////////////class filed end////////////////////////////////

    ////////////Class Type start///////////////////////////////
    String ACTIVITY = "android.app.Activity";
    String FRAGMENT = "android.app.Fragment";
    String SERVICE = "android.app.Service";
    ////////////Class Type end/////////////////////////////////

    ////////////Java Type start/////////////////////////////////
    String TYPE_BYTE = "java.lang.Byte";
    String TYPE_SHORT = "java.lang.Short";
    String TYPE_INTEGER = "java.lang.Integer";
    String TYPE_LONG = "java.lang.Long";
    String TYPE_FLOAT = "java.lang.Float";
    String TYPE_DOUBEL = "java.lang.Double";
    String TYPE_BOOLEAN = "java.lang.Boolean";
    String TYPE_STRING = "java.lang.String";
    String TYPE_PARCELABLE = "android.os.Parcelable";
    String TYPE_SERIALIZABLE = "java.io.Serializable";
    String TYPE_BUNDLE = "android.os.Bundle";
    ////////////Java Type end/////////////////////////////////

}
