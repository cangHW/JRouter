package com.example.administrator.jrouter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.routersever.interfaces.DataCallback;
import com.example.routersever.sever.JRouterSever;
import com.jrouter.annotation.RouterNode;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 17:26.
 * Function :
 */
@RouterNode(path = "asd",group = "app")
public class AidlActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        JRouterSever.getSever().getDatas().getOnConnect(Bean.class, new DataCallback<Bean>() {
            @Override
            public void onCallback(Bean s) {
                Toast.makeText(AidlActivity.this, s.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Click(View view){

    }
}
