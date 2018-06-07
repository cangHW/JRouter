package com.example.routersever.message;

import android.content.Context;
import android.os.Bundle;

import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 14:02.
 * Function :
 */
public class RouterOpenRequst {

    private Context context;

    private String url;
    private String scheme;
    private String host;
    private String group;
    private String path;

    private int requestCode = -1;

    private String[] params;
    private Bundle bundle;

    public static RouterOpenRequst builder() {
        return new RouterOpenRequst();
    }

    public Context getContext() {
        return context;
    }

    public RouterOpenRequst setContext(Context context) {
        this.context = context;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RouterOpenRequst setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getScheme() {
        return scheme;
    }

    public RouterOpenRequst setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public String getHost() {
        return host;
    }

    public RouterOpenRequst setHost(String host) {
        this.host = host;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public RouterOpenRequst setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getPath() {
        return path;
    }

    public RouterOpenRequst setPath(String path) {
        this.path = path;
        return this;
    }

    public String[] getParams() {
        return params;
    }

    public RouterOpenRequst setParams(String[] params) {
        this.params = params;
        return this;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public RouterOpenRequst setRequestCode(int requestCode) {
        this.requestCode = requestCode;
        return this;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public RouterOpenRequst setBundle(Bundle bundle) {
        this.bundle = bundle;
        return this;
    }
}
