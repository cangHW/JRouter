package com.jrouter.node;

import javax.lang.model.element.Element;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/8 14:21.
 * Function :
 */
public class ParamsNodeMessage {

    public Element parentElement;
    public Element element;
    public String key;
    public boolean isCanEmpty;
    public String desc;
    public String name;
    public String type;

    public boolean isView = false;
    public int viewId;

    public String packageName;
    public String className;


    @Override
    public String toString() {
        return packageName + "  " + className + "  " + name;
    }
}
