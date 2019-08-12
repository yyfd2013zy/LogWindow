package logwindow.vinda.com.logwindowlib.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import logwindow.vinda.com.logwindowlib.L;
import logwindow.vinda.com.logwindowlib.util.WindowUtils;

/**
 * 全局悬浮窗，用来显示log信息，方便调试
 */
public class LogWindowService extends Service {
    private String TAG = "PopWindowService";
    public static WindowUtils windowUtils;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        L.d(TAG, "启动");
        windowUtils = new WindowUtils();
        windowUtils.showPopupWindow(this);
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        L.d(TAG, "销毁");
        windowUtils.release();
        windowUtils = null;
        super.onDestroy();
    }

}
