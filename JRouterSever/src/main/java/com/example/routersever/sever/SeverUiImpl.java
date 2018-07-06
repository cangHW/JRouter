package com.example.routersever.sever;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.routersever.constant.Constants;
import com.example.routersever.dispatcher.DispacherFactoryImpl;
import com.example.routersever.sever.ISever.ISeverUi;
import com.example.routersever.util.ExceptionUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/4 11:55.
 * Function :UI跳转事件处理类
 */
class SeverUiImpl implements ISeverUi {

    private SeverUiImpl() {
    }

    private static class Factory {
        private static final SeverUiImpl mInstance = new SeverUiImpl();
    }

    public static ISeverUi getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void openUI(@NonNull Context context, @NonNull String url) {
        openUI(context, url, new Bundle());
    }

    @Override
    public void openUI(@NonNull Context context, @NonNull String url, String[] params) {
        openUI(context, url, params, REQUESTCODE);
    }

    @Override
    public void openUI(@NonNull Context context, @NonNull String url, Bundle bundle) {
        openUI(context, url, bundle, REQUESTCODE);
    }

    @Override
    public void openUI(@NonNull Context context, @NonNull String url, String[] params, int requestCode) {
        if (params.length <= 0) {
            openUI(context, url);
            return;
        }
        if (params.length % 2 != 0) {
            ExceptionUtil.IllegalArgument("The params does not conform to the key-value format.  PARAMS: " + Arrays.toString(params));
            return;
        }
        if (verifyUrl(url)) {
            DispacherFactoryImpl.getFactory().getDispacheIntent().dispache(context, url, params, null, requestCode);
        }
    }

    @Override
    public void openUI(@NonNull Context context, @NonNull String url, Bundle bundle, int requestCode) {
        if (verifyUrl(url)) {
            DispacherFactoryImpl.getFactory().getDispacheIntent().dispache(context, url, null, bundle, requestCode);
        }
    }

    private boolean verifyUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            if (url.contains("://")) {
                if (url.startsWith(Constants.SCHEME_JROUTER)) {
                    Uri uri = Uri.parse(url);
                    List<String> pathSegments = uri.getPathSegments();
                    if (pathSegments.size() < 2) {
                        Log.d(Constants.TAG, "Url format error. URL: " + url);
                        return false;
                    }
                }
                return true;
            } else {
                Log.d(Constants.TAG, "Url format error. URL: " + url);
            }
        } else {
            Log.d(Constants.TAG, "The url cannot be NULL");
        }
        return false;
    }
}
