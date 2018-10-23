package com.react_native_android.crop;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;


public class ImageCropModule extends ReactContextBaseJavaModule implements Crop {

    private CropImpl cropImpl;

    public ImageCropModule(ReactApplicationContext reactContext) {
        super(reactContext);
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
    public void sayHelloToAndroid(String msg) {
        System.out.println("segg6575---@ReactMethod 方法一定要记得添加该注解");
        getCrop().sayHelloToAndroid(msg);
    }

    private CropImpl getCrop() {
        if (cropImpl == null) {
            cropImpl = CropImpl.of(getCurrentActivity());
            getReactApplicationContext().addActivityEventListener(cropImpl);
        } else {
            cropImpl.updateActivity(getCurrentActivity());
        }
        return cropImpl;
    }
}
