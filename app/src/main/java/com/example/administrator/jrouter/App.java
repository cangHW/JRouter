package com.example.administrator.jrouter;

import android.app.Application;

import com.example.routersever.JRouter;
import com.example.routersever.component.JRouterComponent;

//import com.example.routersever.JRouter;
//import com.example.routersever.component.JRouterComponent;

/**
 * Created by Administrator on 2018/6/3.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JRouter.init(this);
        JRouterComponent.getFactory().getComponents().RegisterComponent("com.example.modulea.A_AppLication");
    }
}
