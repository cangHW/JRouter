package com.jrouter.node;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/5 14:22.
 * Function :
 */
public class RouterNodeMessage {

    public Element element;
    public String path;
    public String group;
    public String desc;

    public String className;
    public String packageName;

    public ArrayList<String> params_key;
    public ArrayList<String> params_type;
    public ArrayList<String> params_desc;
    public ArrayList<Boolean> params_canEmpty;
}
