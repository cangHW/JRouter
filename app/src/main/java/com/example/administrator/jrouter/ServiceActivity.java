package com.example.administrator.jrouter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.modulelib.interf.Module_A_Interface;
import com.example.routersever.sever.JRouterSever;
import com.jrouter.annotation.RouterNode;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/14 14:34.
 * Function :
 */
@RouterNode(path = "/service")
public class ServiceActivity  extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        String data=JRouterSever.getSever().getFunctions().getFunction(Module_A_Interface.class).getData();
        Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
    }
}
