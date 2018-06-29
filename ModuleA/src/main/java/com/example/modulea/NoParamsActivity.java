package com.example.modulea;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jrouter.annotation.RouterNode;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/14 14:20.
 * Function :
 */
@RouterNode(path = "/a",group = "a",desc = "无参数")
public class NoParamsActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_params);
    }

    public void Click(View view){
        finish();
    }
}
