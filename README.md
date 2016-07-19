# ReactNative-StackTracer [![Release](https://jitpack.io/v/aoriani/ReactNative-StackTracer.svg)](https://jitpack.io/#aoriani/ReactNative-StackTracer)
Support for React Native stack traces on Crashylitics and other crash report tools for Android.

## Motivation 
When a fatal Javascript error happens in production for your React Native App, the 
[`ExceptionsManagerModule`](https://github.com/facebook/react-native/blob/master/ReactAndroid/src/main/java/com/facebook/react/modules/core/ExceptionsManagerModule.java)
wraps the Javascript stack trace in the message field of a `JavaScriptException` and throws it. That is good because the fatal JS error
can be captured by crash report tools such as Crashlytics. On the other hand it is bad because the `JavascriptException` is always thrown 
in the same place. Therefore all JS crashes look the same because they have the exact same Java stack trace : 

![Crashlytics stack trace](http://i.imgur.com/TSdkFua.png)

Would not be wonderful if instead of having the same Java stacktrace over and over again  we could have the JavaScript stack? 
So distinct JS crashes would yield distinct entries on Crashlytics? Your wish just became true with this little library! 

![Crashlytics stack trace](http://i.imgur.com/yc34XcA.png)

##Yet another Crashlytics React-Native module? 
No. This library doesn't deal with logs like it is done [here](https://medium.com/delivery-com-engineering/add-crashlytics-to-your-react-native-ios-app-69a983a9062a#.as8so4vv5) 
nor it exposes the Crashlytics API to Javascript like [this project](https://github.com/corymsmith/react-native-fabric).

This library sinthesizes a Java stack that is similar to the JS one. The advantages of this technique is that distinct JS crashes will have
have distinct entries on the Crashlytics Dashboard, making fatal JS errors easy to track as the native crashes.

##What about iOS?
This library doesn't support iOS. The fact that Android is not actually native (Android apps run on top of a Java virtual 
machine, Dalvik or Art), makes it easy to fake program stacks. iOS is really native, so its programing stack is a real one 
controled by the device's processor. 

##How to use it

**Step 1**  - Add the library to the Android project, the `android` folder inside your React Native project.

* Add it in your root build.gradle at the end of repositories:

```
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

* Add the dependency

```
	dependencies {
		compile "com.github.aoriani:ReactNative-StackTracer:0.1.1"
	}
```

**Step 2**  - Add `StrackTracePackage` to your `ReactNativeHost`: 

```java
import com.github.aoriani.rnstacktracer.StackTracePackage;

...

private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        protected boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            List<ReactPackage> packages = new ArrayList<>();
            packages.add(new MainReactPackage());
            if (!BuildConfig.DEBUG) {
                packages.add(new StackTracePackage());
            }
            return packages;
        }
    };
```
*Warning: It's advisable to only add the `StackTracePackage` in production. You still want to see the "Red Screen of Death"
during development.*



