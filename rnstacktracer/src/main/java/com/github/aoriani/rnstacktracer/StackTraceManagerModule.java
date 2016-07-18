package com.github.aoriani.rnstacktracer;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.BaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableType;
import com.facebook.react.common.ReactConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by aoriani on 7/17/16.
 */

public class StackTraceManagerModule extends BaseJavaModule implements ExceptionsManagerModuleInterface {

    /**
     * @see com.facebook.react.modules.core.ExceptionsManagerModule#mJsModuleIdPattern
     */
    private static final Pattern mJsModuleIdPattern = Pattern.compile("(?:^|[/\\\\])(\\d+\\.js)$");

    @Override
    public String getName() {
        return MODULE_NAME;
    }

    /**
     * @see com.facebook.react.modules.core.ExceptionsManagerModule#stackFrameToModuleId(ReadableMap)
     */
    private static String stackFrameToModuleId(ReadableMap frame) {
        if (frame.hasKey("file") &&
                !frame.isNull("file") &&
                frame.getType("file") == ReadableType.String) {
            final Matcher matcher = mJsModuleIdPattern.matcher(frame.getString("file"));
            if (matcher.find()) {
                return matcher.group(1) + ":";
            }
        }
        return "";
    }

    /**
     * @see com.facebook.react.modules.core.ExceptionsManagerModule#stackTraceToString(String, ReadableArray)
     */
    private static String stackTraceToString(String message, ReadableArray stack) {
        StringBuilder stringBuilder = new StringBuilder(message).append(", stack:\n");
        for (int i = 0; i < stack.size(); i++) {
            ReadableMap frame = stack.getMap(i);
            stringBuilder
                    .append(frame.getString("methodName"))
                    .append("@")
                    .append(stackFrameToModuleId(frame))
                    .append(frame.getInt("lineNumber"));
            if (frame.hasKey("column") &&
                    !frame.isNull("column") &&
                    frame.getType("column") == ReadableType.Number) {
                stringBuilder
                        .append(":")
                        .append(frame.getInt("column"));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean canOverrideExistingModule() {
        //Must return true so we can override {@link com.facebook.react.modules.core.ExceptionsManagerModule}
        return true;
    }

    @ReactMethod
    @Override
    public void reportFatalException(String title, ReadableArray details, int exceptionId) {
        //We synthesize a Java stack trace from the JS stack trace. That way distinct JS stack will
        //have distinct entries in Crashlytics. A JS stack trace follows the <method>@<line>:<column>.
        //We try as best as we can to stick to that format so one could easily use a source mapper for JS.
        //For that reason we use empty string for class, the line number for the filename and column
        //for the line number
        

    }

    @ReactMethod
    @Override
    public void reportSoftException(String title, ReadableArray details, int exceptionId) {
        FLog.e(ReactConstants.TAG, stackTraceToString(title, details));
    }

    @ReactMethod
    @Override
    public void updateExceptionMessage(String title, ReadableArray details, int exceptionId) {

    }

    @ReactMethod
    @Override
    public void dismissRedbox() {

    }

}
