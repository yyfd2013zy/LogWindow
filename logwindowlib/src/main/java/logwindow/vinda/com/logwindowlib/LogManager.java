package logwindow.vinda.com.logwindowlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import org.apache.log4j.Level;

import de.mindpipe.android.logging.log4j.LogConfigurator;
import logwindow.vinda.com.logwindowlib.service.LogWindowService;

public class LogManager {
    protected final Builder builder;
    private Activity mContext;

    public LogManager(Builder builder) {
        this.builder = builder;
        this.mContext = builder.context;
        init();

    }

    private void init() {
        if (builder.saveLogLocal) {
            initLog4j();
        }
        if (builder.isOpenWindow){
            startLogPopService();
        }
    }

    private void startLogPopService() {
        //启动全局弹窗，显示日志信息
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(mContext)) {
                Intent intentOne = new Intent(mContext, LogWindowService.class);
                mContext.startService(intentOne);
            } else {
                //悬浮窗授权
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                mContext.startActivityForResult(intent, 100);
            }
        } else {
            Intent intentOne = new Intent(mContext, LogWindowService.class);
            mContext.startService(intentOne);
        }
    }

    private void initLog4j() {
        if (TextUtils.isEmpty(builder.localLogFile)){
            System.out.print("---TextUtils.isEmpty(builder.localLogFile)---");
            return;
        }
        LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(builder.localLogFile + "/log.txt");
        logConfigurator.setRootLevel(Level.ALL);
        logConfigurator.setLevel("org.apache", Level.ALL);
        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
        logConfigurator.setMaxFileSize(5 * 1024 * 1024);
        logConfigurator.setImmediateFlush(true);
        logConfigurator.setMaxBackupSize(4);
        logConfigurator.configure();
    }

    public final Builder getBuilder() {
        return builder;
    }

    public static class Builder {
        protected Activity context;
        protected boolean isOpenWindow;//是否打开悬浮窗
        protected boolean saveLogLocal;//是否打印日志到本地
        protected String localLogFile ="";//本地存储日志路径

        public Builder(Activity context) {
            this.context =context;
        }

        public Builder setOpenWindow(boolean isOpenWindow) {
            this.isOpenWindow = isOpenWindow;
            return this;
        }

        public Builder setSaveLogtoLocal(boolean saveLogLocal) {
            this.saveLogLocal = saveLogLocal;
            return this;
        }

        public Builder setLocalLogFIle(String logFile){
            this.localLogFile = logFile;
            return this;
        }
        public LogManager build() {
            return new LogManager(this);
        }

        public LogManager init() {
            LogManager logManager = build();
            return logManager;
        }
    }
}
