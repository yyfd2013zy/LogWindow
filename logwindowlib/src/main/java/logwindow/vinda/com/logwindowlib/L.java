package logwindow.vinda.com.logwindowlib;


import org.apache.log4j.Logger;

import logwindow.vinda.com.logwindowlib.service.LogWindowService;

public class L {
    private static final String tag = "DigitalBox";
    private static final Logger LOGGER = Logger.getLogger(tag);

    public static void i(String tag, String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo(tag,msg);
        }
        LOGGER.info("PID = "+android.os.Process.myPid()+" /LogInfo "+tag + "---->" + msg);
    }

    public static void i(String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo("",msg);
        }
        LOGGER.info("PID = "+android.os.Process.myPid()+" /LogInfo "+msg);
    }

    public static void d(String tag, String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo(tag,msg);
        }
        LOGGER.debug("PID = "+android.os.Process.myPid()+" /LogInfo "+tag + "---->" + msg);
    }

    public static void dInDebug(String tag, String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo(tag,msg);
        }
        if (BuildConfig.DEBUG) {
            LOGGER.debug("PID = "+android.os.Process.myPid()+" /LogInfo "+tag + "---->" + msg);
        }
    }

    public static void d(String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo("",msg);
        }
        LOGGER.debug("PID = "+android.os.Process.myPid()+" /LogInfo "+msg);
    }

    public static void e(String tag, String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo("",msg);
        }
        LOGGER.error("PID = "+android.os.Process.myPid()+" /LogInfo "+tag + "---->" + msg);
    }

    public static void e(String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo("",msg);
        }
        LOGGER.error("PID = "+android.os.Process.myPid()+" /LogInfo "+msg);
    }

}
