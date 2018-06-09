package com.example.administrator.jrouter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.routersever.sever.JRouterSever;
import com.jrouter.annotation.ParamsNode;
import com.jrouter.annotation.RouterNode;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 17:26.
 * Function :
 */
@RouterNode(path = "asd",group = "app")
public class TestActivity extends Activity {

    @ParamsNode(key = "name")
    public String nnnn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JRouterSever.getFactory().getAutowireds().autowired(this);
        Toast.makeText(this,nnnn,Toast.LENGTH_SHORT).show();
    }

    public void Click(View view){}
}
