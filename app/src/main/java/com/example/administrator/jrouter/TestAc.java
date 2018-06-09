package com.example.administrator.jrouter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jrouter.annotation.ParamsNode;
import com.jrouter.annotation.RouterNode;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/7 15:44.
 * Function :
 */
@RouterNode(path = "asd",group = "app")
public class TestAc extends Activity {

//    @ParamsNode()
//    String xxx;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Click(View view){}
}
