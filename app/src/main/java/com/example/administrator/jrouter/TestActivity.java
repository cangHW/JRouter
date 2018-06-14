package com.example.administrator.jrouter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.routersever.constant.Constants;
import com.example.routersever.dispatcher.DispacherFactoryImpl;
import com.example.routersever.interfaces.DataCallback;
import com.example.routersever.sever.JRouterSever;
import com.jrouter.annotation.AutowiredNode;
import com.jrouter.annotation.RouterNode;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 17:26.
 * Function :
 */
@RouterNode(path = "asd",group = "app")
public class TestActivity extends Activity {

    @AutowiredNode(key = "name")
    public String nnnn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JRouterSever.getSever().getAutowireds().autowired(this);
        Toast.makeText(this,nnnn,Toast.LENGTH_SHORT).show();
    }

    public void Click(View view){
//        String asd= DispacherFactoryImpl.getFactory().getDispacheData().receiveMessage(String.class);
        JRouterSever.getSever().getDatas().getOnConnect(String.class, new DataCallback<String>() {
            @Override
            public void onCallback(String s) {
                Toast.makeText(TestActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
