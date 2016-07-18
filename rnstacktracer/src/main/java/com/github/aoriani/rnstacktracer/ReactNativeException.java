package com.github.aoriani.rnstacktracer;

import android.annotation.TargetApi;
import android.os.Build;


/**
 * The Java exception to be thrown when a fatal error happens on the JS side.
 * We could have reused {@link com.facebook.react.modules.core.JavascriptException}, however we don't
 * want to deviate its original usage (store JS stack on the exception's message). Besides we state
 * clearer the origin of exception.
 */
public class ReactNativeException extends RuntimeException {
    public ReactNativeException() {
        super();
    }

    public ReactNativeException(String message) {
        super(message);
    }

    public ReactNativeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReactNativeException(Throwable cause) {
        super(cause);
    }

    @TargetApi(Build.VERSION_CODES.N)
    protected ReactNativeException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
