package com.example.administrator.jrouter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.example.routersever.controller.eventbus.EventBusFactory;
import com.example.routersever.sever.JRouterSever;
import com.jrouter.annotation.RouterNode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/25 11:57.
 * Function :
 */
@RouterNode(path = "event")
public class EventBusActivity extends Activity {

    private EditText et;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus);
        et=findViewById(R.id.et);
    }

    public void Click(View view){
        String message=et.getText().toString().trim();
        JRouterSever.getSever().getEventBus().post(message);
        finish();
    }
}
