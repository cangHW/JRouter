package com.jrouter.util;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/6 11:21.
 * Function :
 */
public class ClassPathUtils {

    private static final String PACKAGER_NAME = "com.jrouter.lib.";

    private static final String ADRESS = PACKAGER_NAME + "Adress_";
    private static final String UI = PACKAGER_NAME + "Ui_";

    private static final String END_PACKAGE="_$P";

    public static String generateAdressPath(String name) {
        return ADRESS + name;
    }

    public static String generateUiPath(String name) {
        return UI + name;
    }

    public static String getEndPackage(){
        return END_PACKAGE;
    }
}
