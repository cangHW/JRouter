package com.jrouter.util;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 11:21.
 * Function :
 */
public class ClassPathUtils {

    private static final String PACKAGER_START = "com.jrouter.lib.";

    private static final String ADRESS = PACKAGER_START + "Adress_";
    private static final String UI = PACKAGER_START + "Ui_";
    private static final String AUTOWIRED = PACKAGER_START + "autowired";

    private static final String JROUTER_CLASS_NAME_END = "$$JROUTER";
    private static final String AUTOWIRED_CLASS_NAME_END = "$$AUTOWIRED";

    private static final String FIELD_ADRESS_PACKAGE_END = "_$P";

    public static String generateAdressPath(String moduleName) {
        return ADRESS + moduleName + JROUTER_CLASS_NAME_END;
    }

    public static String generateUiPath(String moduleName) {
        return UI + moduleName + JROUTER_CLASS_NAME_END;
    }

    public static String generateAutowiredPath() {
        return AUTOWIRED;
    }

    public static String generateAutowiredClassName(String packageName, String className) {
        packageName = packageName.replaceAll("\\.", "_");
        return packageName + "_" + className + AUTOWIRED_CLASS_NAME_END;
    }

    public static String getFieldAdressPackageEnd() {
        return FIELD_ADRESS_PACKAGE_END;
    }
}
