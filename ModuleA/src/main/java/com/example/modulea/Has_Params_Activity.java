package com.example.modulea;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.example.routersever.sever.JRouterSever;
import com.jrouter.annotation.AutowiredNode;
import com.jrouter.annotation.RouterNode;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 11:30.
 * Function :
 */
@RouterNode(path = "/a",group = "a",desc = "有参数")
public class Has_Params_Activity extends Activity {

    @AutowiredNode()
    public String name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_has_params);
        JRouterSever.getSever().getAutowireds().autowired(this);
        Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(100);
        finish();
    }

    public void Click(View view){
        setResult(100);
        finish();
    }
}
