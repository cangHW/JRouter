package com.example.routersever.controller.autowired;

import com.example.routersever.controller.autowired.IAutowired.IAutowired;
import com.example.routersever.controller.autowired.IAutowired.IInject;
import com.jrouter.util.ClassPathUtils;

import java.util.HashMap;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/8 17:37.
 * Function :
 */
class AutowiredImpl implements IAutowired{

    private HashMap<String,IInject> mAutowiredWapper=new HashMap<>();

    private AutowiredImpl() {
    }

    private static class Factory {
        private static final AutowiredImpl mInstance = new AutowiredImpl();
    }

    public static IAutowired getInstance() {
        return Factory.mInstance;
    }

    @Override
    public void autowired(String packageName, String className, Object o) {
        String autowired_ClassName=ClassPathUtils.generateAutowiredClassName(packageName, className);
        if (!mAutowiredWapper.containsKey(autowired_ClassName)){
            createWapper(autowired_ClassName);
        }
        if (mAutowiredWapper.containsKey(autowired_ClassName)) {
            mAutowiredWapper.get(autowired_ClassName).inject(o);
        }
    }

    private void createWapper(String className){
        try {
            String autowired_PackageName=ClassPathUtils.generateAutowiredPackageName();
            Class aClass=Class.forName(autowired_PackageName+"."+className);
            IInject inject= (IInject) aClass.newInstance();
            mAutowiredWapper.put(className,inject);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
