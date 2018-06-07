package com.example.modulea;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.jrouter.annotation.RouterNode;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 11:30.
 * Function :
 */
@RouterNode(path = "/a",group = "a",desc = "asd")
public class A_Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        String name=getIntent().getStringExtra("name");
        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
    }
}
