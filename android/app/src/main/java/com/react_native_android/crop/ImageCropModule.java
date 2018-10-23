package com.react_native_android.crop;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;


public class ImageCropModule extends ReactContextBaseJavaModule implements Crop {

    private CropImpl cropImpl;
    private ReactContext reactContext;

    public ImageCropModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "ImageCrop";
    }

    @Override
    @ReactMethod
    public void selectWithCrop(int aspectX, int aspectY, Promise promise) {
        getCrop().selectWithCrop(aspectX, aspectY, promise);
    }

    @Override
    @ReactMethod
    public void sayHelloToAndroid(String msg, Callback sucess) {
        System.out.println("segg6575---@ReactMethod 方法一定要记得添加该注解");
        getCrop().sayHelloToAndroid(msg, sucess);
    }

    private CropImpl getCrop() {
        if (cropImpl == null) {
            cropImpl = CropImpl.of(getCurrentActivity(), this.reactContext);
            getReactApplicationContext().addActivityEventListener(cropImpl);
        } else {
            cropImpl.updateActivity(getCurrentActivity());
        }
        return cropImpl;
    }

    @Override
    @ReactMethod
    public void onRnSendMap(ReadableMap value) {
        getCrop().onRnSendMap(value);
    }


    @Override
    @ReactMethod
    public void onRnSendArray(ReadableArray array) {
        getCrop().onRnSendArray(array);
    }
}
