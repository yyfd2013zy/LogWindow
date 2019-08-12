package logwindow.vinda.com.logwindow;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtils {

    /**
     * 获取磁盘的根目录
     *
     * @param context
     * @return
     */
    public static String getRootPath(Context context) {
        String rootPath = "";
        if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(Environment
                .getExternalStorageState())) {
            rootPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
            //rootPath = context.getExternalCacheDir().getParent();
        } else {
            rootPath = context.getFilesDir().getAbsolutePath();
        }
        return rootPath;
    }

    public static String getAppRootPath(Context context) {
        String filePath = getRootPath(context)
                + "/LogTest";
        File file = new File(filePath);
        if (!file.exists()) {
            boolean result = file.mkdirs();
        }
        return filePath;
    }

    public static String getLogPath(Context context) {
        String filePath = getAppRootPath(context)
                + "/log";
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return filePath;
    }
}
