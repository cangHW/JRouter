package com.example.routersever.cache.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.routersever.cache.ui.IUi.IUiCache;
import com.example.routersever.cache.ui.IUi.IUiOpen;
import com.example.routersever.message.RouterOpenRequst;
import com.example.routersever.message.RouterOpenResponse;
import com.jrouter.util.ClassPathUtils;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 14:14.
 * Function :UI跳转实现类
 */
class UiOpenImpl implements IUiOpen {

    private HashMap<String, SoftReference<IUiCache>> mUiCache = new HashMap<>();

    private UiOpenImpl() {
    }

    private static class Factory {
        private static final UiOpenImpl mInstance = new UiOpenImpl();
    }

    public static IUiOpen getInstance() {
        return Factory.mInstance;
    }

    @Override
    public RouterOpenResponse openUi(RouterOpenRequst message) {
        return start(message);
    }

    @Override
    public void openSystem(Context context, String url) {

    }

    @Override
    public void openThirdApp(Context context, String url) {

    }

    private RouterOpenResponse start(RouterOpenRequst message) {
        RouterOpenResponse response = RouterOpenResponse.builder();
        Class aClass = fetchClass(message, response);
        if (aClass != null) {
            Intent intent = new Intent(message.getContext(), aClass);
            Bundle bundle = message.getBundle();
            if (bundle != null) {
                intent.putExtras(bundle);
            }
            String[] params = message.getParams();
            if (params != null) {
                int i = 0;
                do {
                    intent.putExtra(params[i], params[++i]);
                    ++i;
                } while (i < params.length);
            }
            if (message.getRequestCode() > 0) {
                ((Activity) message.getContext()).startActivityForResult(intent, message.getRequestCode());
            } else {
                message.getContext().startActivity(intent);
            }
            response.setSuccess(true);
        }
        return response;
    }

    private Class fetchClass(RouterOpenRequst message, RouterOpenResponse response) {
        IUiCache uiCache = fetchIUiCache(message.getHost());
        if (uiCache != null) {
            Class aClass = uiCache.getIntentClass(message);
            if (aClass != null) {
                response.setSuccess(true);
                return aClass;
            } else {
                response.setErrorMsg("The group or path is wrong, please check the group and path. GROUP: " + message.getGroup() + " PATH: " + message.getPath());
            }
        } else {
            response.setErrorMsg("The host is wrong, please check the host. HOST: " + message.getHost());
        }
        return null;
    }

    private IUiCache fetchIUiCache(String host) {
        if (!isHasCache(host)) {
            createCache(host);
        }
        SoftReference<IUiCache> softReference = mUiCache.get(host);
        if (softReference == null) {
            softReference = replaceUiCache(host);
        }
        IUiCache uiCache = softReference.get();
        if (uiCache == null) {
            uiCache = replaceUiCache(host).get();
        }
        return uiCache;
    }

    private boolean isHasCache(String host) {
        return mUiCache.containsKey(host);
    }

    private SoftReference<IUiCache> replaceUiCache(String host) {
        mUiCache.remove(host);
        createCache(host);
        return mUiCache.get(host);
    }

    private void createCache(String host) {
        String path = ClassPathUtils.generateUiPath(host);
        try {
            Class aClass = Class.forName(path);
            IUiCache uiCache = (IUiCache) aClass.newInstance();
            mUiCache.put(host, new SoftReference<>(uiCache));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
