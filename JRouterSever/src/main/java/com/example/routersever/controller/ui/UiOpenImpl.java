package com.example.routersever.controller.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.routersever.controller.ui.IUi.IUiCache;
import com.example.routersever.controller.ui.IUi.IUiOpen;
import com.example.routersever.message.ParameterMessage;
import com.example.routersever.message.RouterOpenRequst;
import com.example.routersever.message.RouterOpenResponse;
import com.jrouter.constants.Constants;
import com.jrouter.util.ClassPathUtils;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 14:14.
 * Function :UI跳转实现类
 */
class UiOpenImpl implements IUiOpen {

    private HashMap<String, SoftReference<IUiCache>> mUiWapper;

    private UiOpenImpl() {
        mUiWapper = new HashMap<>();
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
                List<ParameterMessage> params = uiCache.getClassParams(aClass);
                if (checkParams(params, message, response)) {
                    response.setSuccess(true);
                    return aClass;
                }
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
            createWapper(host);
        }
        SoftReference<IUiCache> softReference = mUiWapper.get(host);
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
        return mUiWapper.containsKey(host);
    }

    private SoftReference<IUiCache> replaceUiCache(String host) {
        mUiWapper.remove(host);
        createWapper(host);
        return mUiWapper.get(host);
    }

    private void createWapper(String host) {
        String path = ClassPathUtils.generateUiPath(host);
        try {
            Class aClass = Class.forName(path);
            IUiCache uiCache = (IUiCache) aClass.newInstance();
            mUiWapper.put(host, new SoftReference<>(uiCache));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    private boolean checkParams(List<ParameterMessage> params, RouterOpenRequst message, RouterOpenResponse response) {
        if (params.size() == 0) {
            return true;
        }

        for (ParameterMessage parameterMessage : params) {
            if (!parameterMessage.isCanEmpty()) {
                if (message.getParams() != null && message.getParams().length > 0) {
                    if (!checkParamsWithString(parameterMessage, message.getParams(), response)) {
                        return false;
                    }
                } else if (message.getBundle() != null) {
                    if (!checkParamsWithBundle(parameterMessage, message.getBundle(), response)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkParamsWithString(ParameterMessage parameterMessage, String[] params, RouterOpenResponse response) {
        boolean isHas = false;
        int i = 0;
        while (i < params.length) {
            if (parameterMessage.getKey().equals(params[i])) {
                if (!parameterMessage.getType().equals(Constants.STRING)) {
                    response.setErrorMsg("The params type error.PARAMS KEY: " + params[i]);
                    return false;
                }
                isHas = true;
            }
            i++;
            i++;
        }
        if (!isHas) {
            response.setErrorMsg("The params can not empty.PARAMS KEY: " + parameterMessage.getKey());
            return false;
        }
        return true;
    }

    private boolean checkParamsWithBundle(ParameterMessage parameterMessage, Bundle bundle, RouterOpenResponse response) {
        boolean flag = bundle.containsKey(parameterMessage.getKey());
        if (!flag && !parameterMessage.isCanEmpty()) {
            response.setErrorMsg("The params can not empty.PARAMS KEY: " + parameterMessage.getKey());
            return false;
        }
        return true;
    }
}
