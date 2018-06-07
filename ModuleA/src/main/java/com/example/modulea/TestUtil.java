package com.example.modulea;

import com.example.modulelib.interf.Module_A_Interface;

/**
 * Created by Administrator on 2018/6/3.
 */

public class TestUtil implements Module_A_Interface {
    @Override
    public String getData() {
        return "asd";
    }
}
