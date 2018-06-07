package com.example.routersever.dispatcher.intent.IDispacher;

import android.content.Context;
import android.os.Bundle;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/7 10:42.
 * Function :UI跳转事件分发处理服务接口
 */
public interface IDispacherIntent {

    String OPEN_TYPE_CURRENTAPP = "currentApp";
    String OPEN_TYPE_SYSTEM = "system";
    String OPEN_TYPE_THIRDAPP = "thirdApp";

    /**
     * 对UI跳转事件进行分发
     */
    void dispache(Context context, String url, String[] params, Bundle bundle, int requestCode);

}
