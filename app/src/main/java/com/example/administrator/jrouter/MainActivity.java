package com.example.administrator.jrouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.routersever.constant.Constants;
import com.example.routersever.dispatcher.DispacherFactoryImpl;
import com.example.routersever.sever.JRouterSever;
import com.jrouter.annotation.AutowiredNode;
import com.jrouter.annotation.RouterNode;

import java.util.ArrayList;
import java.util.HashMap;

@RouterNode(path = "/asd",group = "app")
public class MainActivity extends AppCompatActivity {

    @AutowiredNode()
    public String name;

    @AutowiredNode()
    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        findViewById()
//        DispacherFactoryImpl.getFactory().getDispacheData().sendMessage("xxx");
//        Constants.xx="xxx";
        JRouterSever.getSever().getDatas().set("xxx");

//        new ArrayList<String>(){
//            {add("asd");}            {add("asd");}            {add("asd");}            {add("asd");}            {add("asd");}
//        };
    }

    public void Click(View view){
//        String data= JRouterSever.getFactory().getFunctions().getFunction(Module_A_Interface.class).getData();
//        Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
//        JRouterSever.getFactory().getUIs().openUI(this,"JRouter://ModuleA/a/a");
//        JRouterAdress_ModuleA
//        String ip= JRouterSever.getSever().getIps().getIp("ModuleA","A_Activity","com.example.modulea");
        String ip=JRouterSever.getSever().getIps().getIp("App","TestActivity");
//        JRouterSever.getSever().getUIs().openUI(this,ip);
        JRouterSever.getSever().getUIs().openUI(this,ip,new String[]{"name","asd"});
//        byte[] xx=new byte[2];

//        MainActivity xx=new MainActivity();
//        JRouterSever.getSever().getDatas().set(xx);
    }
}
