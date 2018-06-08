package com.example.routersever.message;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/7 17:40.
 * Function :
 */
public class ParameterMessage {

    private String key;
    private String type;
    private String desc;
    private boolean isCanEmpty;

    public static ParameterMessage builder(){
        return new ParameterMessage();
    }

    public String getKey() {
        return key;
    }

    public ParameterMessage setKey(String key) {
        this.key = key;
        return this;
    }

    public String getType() {
        return type;
    }

    public ParameterMessage setType(String type) {
        this.type = type;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ParameterMessage setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public boolean isCanEmpty() {
        return isCanEmpty;
    }

    public ParameterMessage setCanEmpty(boolean canEmpty) {
        isCanEmpty = canEmpty;
        return this;
    }
}
