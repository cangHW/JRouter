package com.example.modulea;

import android.content.Context;

import com.example.modulelib.interf.Module_A_Interface;
import com.example.routersever.interfaces.IApplicationRouter;
import com.example.routersever.sever.JRouterSever;

/**
 * Created by Administrator on 2018/6/3.
 */

public class A_AppLication implements IApplicationRouter {
    @Override
    public void onCreate(Context context) {
        JRouterSever.getFactory().getFunctions().RegisterFunction(Module_A_Interface.class,new TestUtil());
    }

    @Override
    public void onDestory() {
        JRouterSever.getFactory().getFunctions().unRegisterFunction(Module_A_Interface.class);
    }
}
