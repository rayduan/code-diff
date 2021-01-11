package com.dr.common.log;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

/**
 * @author dr on 2018/8/10 09:55.
 */
public class LoggerUtil {
    private static final char THREAD_RIGHT_TAG = ']';
    private static final char THREAD_LEFT_TAG = '[';
    public static final char ENTERSTR = '\n';
    public static final char COMMA = ',';
    public static final char MID_LINE = '-';

    private LoggerUtil() {
    }

    public static void error(Logger LOGGER, String scene, Object... msgs) {
        LOGGER.error(getLogString(scene, msgs));
    }

    public static void error(Logger LOGGER, String scene, Throwable ex, Object... msgs) {
        LOGGER.error(getLogString(scene, msgs), ex);
    }

    public static void warn(Logger LOGGER, String scene, Throwable ex, Object... msgs) {
        LOGGER.warn(getLogString(scene, msgs), ex);
    }

    public static void warn(Logger LOGGER, String scene, Object... msgs) {
        LOGGER.warn(getLogString(scene, msgs));
    }

    public static void info(Logger LOGGER, String scene, Object... msgs) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(getLogString(scene, msgs));
        }

    }

    public static void debug(Logger LOGGER, String scene, Object... msgs) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(getLogString(scene, msgs));
        }

    }

    public static void info(Logger LOGGER, String msg) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(msg);
        }

    }

    public static void warn(Logger LOGGER, String msg) {
        LOGGER.warn(msg);
    }

    public static void error(Logger LOGGER, String msg, Throwable ex) {
        LOGGER.error(msg, ex);
    }

    public static String getLogString(String scene, Object... obj) {
        StringBuilder log = new StringBuilder();
        log.append('[').append(Thread.currentThread().getId()).append(']');
        log.append(',');
        log.append(StringUtils.isBlank(scene) ? '-' : scene);
        log.append(',');
        if (obj != null) {
            Object[] var3 = obj;
            int var4 = obj.length;
            for(int var5 = 0; var5 < var4; ++var5) {
                Object o = var3[var5];
                log.append(JSON.toJSON(o));
                log.append(',');
            }
        }
        if (StringUtils.isNotBlank(log) && log.toString().endsWith(",")) {
            log.delete(log.length() - 1, log.length());
        }
        return log.toString();
    }
}
