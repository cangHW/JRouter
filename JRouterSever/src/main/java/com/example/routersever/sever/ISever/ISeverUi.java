package com.example.routersever.sever.ISever;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 11:54.
 * Function :UI跳转事件接口
 */
public interface ISeverUi {

    int REQUESTCODE = 0;

    void openUI(@NonNull Context context, @NonNull String url);

    void openUI(@NonNull Context context, @NonNull String url, String[] params);

    void openUI(@NonNull Context context, @NonNull String url, Bundle bundle);

    void openUI(@NonNull Context context, @NonNull String url, String[] params, int requestCode);

    void openUI(@NonNull Context context, @NonNull String url, Bundle bundle, int requestCode);
}
