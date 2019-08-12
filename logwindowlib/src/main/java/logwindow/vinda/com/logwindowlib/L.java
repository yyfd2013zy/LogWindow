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
        LOGGER.info(tag + "---->" + msg);
    }

    public static void i(String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo("",msg);
        }
        LOGGER.info(msg);
    }

    public static void d(String tag, String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo(tag,msg);
        }
        LOGGER.debug(tag + "---->" + msg);
    }

    public static void dInDebug(String tag, String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo(tag,msg);
        }
        if (BuildConfig.DEBUG) {
            LOGGER.debug(tag + "---->" + msg);
        }
    }

    public static void d(String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo("",msg);
        }
        LOGGER.debug(msg);
    }

    public static void e(String tag, String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo("",msg);
        }
        LOGGER.error(tag + "---->" + msg);
    }

    public static void e(String msg) {
        if (LogWindowService.windowUtils != null) {
            LogWindowService.windowUtils.showInfo("",msg);
        }
        LOGGER.error(msg);
    }

}
