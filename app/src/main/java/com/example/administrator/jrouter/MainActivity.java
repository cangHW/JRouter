package com.example.administrator.jrouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.routersever.controller.adress.AdressFactoryImpl;
import com.example.routersever.sever.JRouterSever;
import com.jrouter.annotation.ParamsNode;
import com.jrouter.annotation.RouterNode;

import java.util.ArrayList;
import java.util.HashMap;

@RouterNode(path = "/asd",group = "app")
public class MainActivity extends AppCompatActivity {

    @ParamsNode()
    public String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new HashMap<String,String>(){
            {put("asd","asd");}
        };
        new ArrayList<String>(){
            {add("asd");}            {add("asd");}            {add("asd");}            {add("asd");}            {add("asd");}
        };
    }

    public void Click(View view){
//        String data= JRouterSever.getInstance().getFunctions().getFunction(Module_A_Interface.class).getData();
//        Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
//        JRouterSever.getInstance().getUIs().openUI(this,"JRouter://ModuleA/a/a");
//        JRouterAdress_ModuleA
//        String ip= JRouterSever.getFactory().getIps().getIp("ModuleA","A_Activity","com.example.modulea");
        String ip=JRouterSever.getFactory().getIps().getIp("App","TestActivity");
//        JRouterSever.getFactory().getUIs().openUI(this,ip);
        JRouterSever.getFactory().getUIs().openUI(this,ip,new String[]{"nnn","asd"});
    }
}
