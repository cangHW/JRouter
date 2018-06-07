package com.example.routersever.dispatcher.intent;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.routersever.cache.ui.UiFactoryImpl;
import com.example.routersever.constant.Constants;
import com.example.routersever.dispatcher.intent.IDispacher.IDispacherIntent;
import com.example.routersever.message.RouterOpenRequst;
import com.example.routersever.message.RouterOpenResponse;

import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/7 10:42.
 * Function :UI跳转事件分发处理服务
 */
class DispacherIntentImpl implements IDispacherIntent {

    private DispacherIntentImpl() {
    }

    private static class Factory {
        private static DispacherIntentImpl mInstance = new DispacherIntentImpl();
    }

    public static IDispacherIntent getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void dispache(Context context, String url, String[] params, Bundle bundle, int requestCode) {
        switch (verifyUrl(url)) {
            case OPEN_TYPE_CURRENTAPP:
                startOpenUi(context, url, params, bundle, requestCode);
                break;
            case OPEN_TYPE_SYSTEM:
                startOpenSystem(context, url);
                break;
            case OPEN_TYPE_THIRDAPP:
                startOpenThreadApp(context, url);
                break;
        }
    }

    private String verifyUrl(String url) {
        if (url.startsWith(Constants.SCHEME_JROUTER)) {
            return OPEN_TYPE_CURRENTAPP;
        } else if (url.startsWith(Constants.SCHEME_TEL)
                || url.startsWith(Constants.SCHEME_HTTP)
                || url.startsWith(Constants.SCHEME_HTTPS)) {
            return OPEN_TYPE_SYSTEM;
        } else {
            return OPEN_TYPE_THIRDAPP;
        }
    }

    private void startOpenSystem(Context context, String url) {
        UiFactoryImpl.getFactory().getOpens().openSystem(context, url);
    }

    private void startOpenThreadApp(Context context, String url) {
        UiFactoryImpl.getFactory().getOpens().openThirdApp(context, url);
    }

    private void startOpenUi(Context context, String url, String[] params, Bundle bundle, int requestCode) {
        RouterOpenRequst requst = RouterOpenRequst.builder();
        Uri uri = Uri.parse(url);
        requst.setContext(context)
                .setUrl(url)
                .setScheme(uri.getScheme())
                .setHost(uri.getHost())
                .setRequestCode(requestCode)
                .setBundle(bundle)
                .setParams(params);
        List<String> pathSegments = uri.getPathSegments();
        StringBuilder path = new StringBuilder();
        for (int i = 1; i < pathSegments.size(); i++) {
            path.append(pathSegments.get(i)).append("/");
        }
        path.deleteCharAt(path.length() - 1);
        requst.setGroup(pathSegments.get(0)).setPath(path.toString());

        String query = uri.getQuery();

        RouterOpenResponse response = UiFactoryImpl.getFactory().getOpens().openUi(requst);
        endOpenUi(response);
    }

    private void endOpenUi(RouterOpenResponse response) {
        if (!response.isSuccess()) {
            Log.d(Constants.TAG, response.getErrorMsg());
        }
    }

}
