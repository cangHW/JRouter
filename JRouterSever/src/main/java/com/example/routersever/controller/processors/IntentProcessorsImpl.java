package com.example.routersever.controller.processors;

import android.support.annotation.NonNull;

import com.example.routersever.controller.processors.IProcessors.IIntentProcessors;
import com.example.routersever.interfaces.IRouterProcessors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Canghaixiao.
 * Time : 2018/7/2 14:27.
 * Function :
 */
class IntentProcessorsImpl implements IIntentProcessors {

    private ArrayList<IRouterProcessors> mProcessors;

    private IntentProcessorsImpl() {
        mProcessors=new ArrayList<>();
    }

    private static class Factory {
        private static final IntentProcessorsImpl mInstance = new IntentProcessorsImpl();
    }

    public static IIntentProcessors getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void addProcessors(@NonNull IRouterProcessors processors) {
        mProcessors.add(processors);
    }

    @Override
    public List<IRouterProcessors> getProcessors() {
        return mProcessors;
    }
}
