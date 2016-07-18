package com.github.aoriani.rnstacktracer;

/**
 * Created by aoriani on 7/17/16.
 */

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.modules.core.ExceptionsManagerModule;

/**
 * We cannot subclass {@link com.facebook.react.modules.core.ExceptionsManagerModule} and override
 * its method. The constructor of {@link com.facebook.react.modules.core.ExceptionsManagerModule} an
 * instance of {@link com.facebook.react.devsupport.DevSupportManager}, which on its turn requires
 * an instance of {@link com.facebook.react.ReactInstanceManager}. However you need to pass the
 * module before building the {@link com.facebook.react.ReactInstanceManager}.
 * <p>
 * This interface contains all {@link com.facebook.react.bridge.ReactMethod} of
 * {@link com.facebook.react.modules.core.ExceptionsManagerModule}
 */
public interface ExceptionsManagerModuleInterface {

    /**
     * Must reaturn the same as {@link ExceptionsManagerModule#getName()} so we can override the
     * core module.
     */
    String MODULE_NAME = "RKExceptionsManager";

    void reportFatalException(String title, ReadableArray details, int exceptionId);

    void reportSoftException(String title, ReadableArray details, int exceptionId);

    void updateExceptionMessage(String title, ReadableArray details, int exceptionId);

    void dismissRedbox();
}
