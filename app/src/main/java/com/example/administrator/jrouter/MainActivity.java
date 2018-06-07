package com.example.administrator.jrouter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.routersever.adress.JRouterIps;
import com.example.routersever.sever.JRouterSever;
import com.jrouter.annotation.Autowired;
import com.jrouter.annotation.RouterNode;

@RouterNode(path = "/asd",group = "app")
public class MainActivity extends AppCompatActivity {

    @Autowired()
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Click(View view){
//        String data= JRouterSever.getInstance().getFunctions().getFunction(Module_A_Interface.class).getData();
//        Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
//        JRouterSever.getInstance().getUIs().openUI(this,"JRouter://ModuleA/a/a");
//        JRouterAdress_ModuleA
        String ip= JRouterIps.getFactory().getIps().getIp("ModuleA","A_Activity","com.example.modulea");
//        JRouterSever.getFactory().getUIs().openUI(this,ip);
        JRouterSever.getFactory().getUIs().openUI(this,ip,new String[]{"name","asd"});
    }
}
