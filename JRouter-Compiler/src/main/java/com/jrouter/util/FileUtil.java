package com.jrouter.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Canghaixiao.
 * Time : 2018/6/12 16:06.
 * Function :
 */
public class FileUtil {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean createFile(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                file.delete();
            }
            File dir = new File(file.getParent());
            dir.mkdirs();
            file.createNewFile();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void writeStringToFile(String fileName, String content) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"));
            out.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
