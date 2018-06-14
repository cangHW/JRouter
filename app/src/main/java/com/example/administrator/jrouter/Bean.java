package com.example.administrator.jrouter;

/**
 * Created by Administrator on 2018/6/3.
 */

public class Bean {

    String name;
    int old;
    int sex;

    public Bean(String name,int old,int sex){
        this.name=name;
        this.old=old;
        this.sex=sex;
    }

    @Override
    public String toString() {
        return "name:"+name+" old:"+old+" sex:"+sex;
    }
}
