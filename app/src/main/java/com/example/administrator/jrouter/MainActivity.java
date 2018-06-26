package com.example.administrator.jrouter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.routersever.component.JRouterComponent;
import com.example.routersever.controller.eventbus.EventBusFactory;
import com.example.routersever.sever.JRouterSever;
import com.jrouter.annotation.RouterNode;

@RouterNode(path = "/asd", group = "app")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Click(View view) {
        switch (view.getId()) {
            case R.id.bt:
                String ip1 = JRouterSever.getSever().getIps().getIp("ModuleA", "NoParamsActivity");
                JRouterSever.getSever().getUIs().openUI(this, ip1);
                break;
            case R.id.bt1:
                String ip = JRouterSever.getSever().getIps().getIp("ModuleA", "Has_Params_Activity");
                //带参数跳转方法一
                JRouterSever.getSever().getUIs().openUI(this, ip, new String[]{"name", "asd"});
                //带参数跳转方法二
//                Bundle bundle=new Bundle();
//                bundle.putString("name","asd");
//                JRouterSever.getSever().getUIs().openUI(this,ip,bundle);
                //带参数跳转方法三
//                JRouterSever.getSever().getUIs().openUI(this,ip,new String[]{"name","asd"},1000);
                break;
            case R.id.bt2:
                String ip2 = JRouterSever.getSever().getIps().getIp("App", "AidlActivity");
                JRouterSever.getSever().getDatas().set(new Bean("asd", 1, 6));
                JRouterSever.getSever().getUIs().openUI(this, ip2);
                break;
            case R.id.bt3:
                //这里我把那个注册服务的地方放在组件moduleA的初始化里面了，懒得改了，
                // 下面这个方法是注册组件moduleA，可以在回调里面做一些moduleA的初始化操作
                JRouterComponent.getFactory().getComponents().RegisterComponent("com.example.modulea.A_AppLication");

                String ip3 = JRouterSever.getSever().getIps().getIp("App", "ServiceActivity");
                JRouterSever.getSever().getUIs().openUI(this, ip3);
                break;
            case R.id.bt4:
                JRouterSever.getSever().getEventBus().unRegister(this);
                JRouterSever.getSever().getEventBus().register(this);
                String ip4 = JRouterSever.getSever().getIps().getIp("App", "EventBusActivity");
                JRouterSever.getSever().getUIs().openUI(this, ip4);
                break;
        }
    }

    public void onEventThreadMain(Bean bean){
        Toast.makeText(MainActivity.this,bean.name,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Toast.makeText(MainActivity.this, "带返回值跳转", Toast.LENGTH_SHORT).show();
        }
    }
}
