package com.example.routersever.sever.ISever;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/8 17:47.
 * Function :
 */
public interface ISeverAutowired {

    void autowired(@NonNull Activity activity);

    void autowired(@NonNull android.app.Fragment fragment);

    void autowired(@NonNull FragmentActivity activity);

    void autowired(@NonNull Fragment fragment);
}
