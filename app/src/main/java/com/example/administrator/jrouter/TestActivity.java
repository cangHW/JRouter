package com.example.administrator.jrouter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jrouter.annotation.RouterNode;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 17:26.
 * Function :
 */
@RouterNode(path = "asd",group = "app")
public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
