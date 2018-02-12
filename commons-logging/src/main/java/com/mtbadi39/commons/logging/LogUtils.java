package com.mtbadi39.commons.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);

    public static String resolveException(String fileName, String methodName, Throwable t) {
        String message = "";
        String _fileName;
        String _methodName;
        try {
            StackTraceElement[] StackTraceElementsList = t.getStackTrace();
            for (StackTraceElement element : StackTraceElementsList) {
                _fileName = element.getFileName();
                if (_fileName != null) {
                    if (_fileName.contains(fileName)) {
                        LOGGER.debug(" element.getFileName() = " + _fileName);
                        message += "\r\n  >> " + element.toString();
                        _methodName = element.getMethodName();
                        if (_methodName != null) {
                            if (_methodName.contains(methodName)) {
                                LOGGER.debug(" element.getMethodName() = " + _methodName);
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error(LogUtils.resolveException("LogUtils", "resolveException", e));
        }
        message += "\r\n  >> " + t.getClass().getSimpleName() + " : " + t.getMessage();
        return message;
    }
}
