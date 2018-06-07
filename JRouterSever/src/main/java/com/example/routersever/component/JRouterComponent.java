package com.example.routersever.component;

import com.example.routersever.JRouter;
import com.example.routersever.component.IComponent.IComponent;
import com.example.routersever.component.IComponentFactory.IComponentFactory;
import com.example.routersever.util.ExceptionUtil;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/2 10:30.
 * Function :获取组件管理服务
 */
public class JRouterComponent implements IComponentFactory {

    static {
        if (!JRouter.isReady()) {
            ExceptionUtil.Runtime("you need to do JRouter.init(context) first");
        }
    }

    private static class Factory {
        private static JRouterComponent mInstance = new JRouterComponent();
    }

    public static IComponentFactory getFactory() {
        return Factory.mInstance;
    }

    @Override
    public IComponent getComponents() {
        return ComponentImpl.getInstance();
    }
}
