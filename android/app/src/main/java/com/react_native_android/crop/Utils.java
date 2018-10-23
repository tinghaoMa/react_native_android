package com.react_native_android.crop;

import android.os.Environment;
import java.io.File;



public class Utils {
    /**
     * 获取一个临时文件
     * @param fileName
     * @return
     */
    public static File getPhotoCacheDir(String fileName) {
        return new File(Environment.getExternalStorageDirectory().getAbsoluteFile(),fileName);
    }
}
