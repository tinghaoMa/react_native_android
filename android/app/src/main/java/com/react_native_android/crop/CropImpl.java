package com.react_native_android.crop;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.ArrayList;


public class CropImpl implements ActivityEventListener, Crop {
    private final int RC_PICK = 50081;
    private final int RC_CROP = 50082;
    private final String CODE_ERROR_PICK = "用户取消";
    private final String CODE_ERROR_CROP = "裁切失败";

    private Promise pickPromise;
    private Uri outPutUri;
    private int aspectX;
    private int aspectY;
    private Activity activity;
    private ReactContext mReactContext;

    public static CropImpl of(Activity activity, ReactContext reactContext) {
        return new CropImpl(activity, reactContext);
    }

    private CropImpl(Activity activity, ReactContext reactContext) {
        this.activity = activity;
        this.mReactContext = reactContext;
    }

    public void updateActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PICK) {
            if (resultCode == Activity.RESULT_OK && data != null) {//从相册选择照片并裁剪
                outPutUri = Uri.fromFile(Utils.getPhotoCacheDir(System.currentTimeMillis() + "" +
                        ".jpg"));
                onCrop(data.getData(), outPutUri);
            } else {
                pickPromise.reject(CODE_ERROR_PICK, "没有获取到结果");
            }
        } else if (requestCode == RC_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                pickPromise.resolve(outPutUri.getPath());
            } else {
                pickPromise.reject(CODE_ERROR_CROP, "裁剪失败");
            }
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
    }

    @Override
    public void selectWithCrop(int aspectX, int aspectY, Promise promise) {
        this.pickPromise = promise;
        this.aspectX = aspectX;
        this.aspectY = aspectY;
        this.activity.startActivityForResult(IntentUtils.getPickIntentWithGallery(), RC_PICK);
    }

    @Override
    public void sayHelloToAndroid(String msg, Callback sucess) {
        String name = Thread.currentThread().getName();
        System.out.println("segg6575---msg = " + msg + "     js-thread-name " + name);
        sucess.invoke("我是Native 我收到了你的消息 你好");


        WritableMap map = Arguments.createMap();
        map.putString("name", "mth");
        map.putBoolean("result", true);
        sendEventMap(this.mReactContext, "nativeCallRNMap", map);

        WritableArray array = Arguments.createArray();
        array.pushBoolean(true);
        array.pushString("hello array");
        //已经使用过的map 不能再使用
        WritableMap map1 = Arguments.createMap();
        map1.putString("name1", "mth");
        map1.putBoolean("result1", true);
        array.pushMap(map1);
        sendEventArray(this.mReactContext, "nativeCallRNMapArray", array);
    }


    /**
     * 向RN 直接传递数据
     *
     * @param reactContext
     * @param eventName
     * @param params
     */
    private void sendEventMap(ReactContext reactContext, String eventName, WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit
                (eventName, params);
    }

    /**
     * 向RN 直接传递数据 集合
     *
     * @param reactContext
     * @param eventName
     * @param params
     */
    private void sendEventArray(ReactContext reactContext, String eventName, WritableArray params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit
                (eventName, params);
    }

    private void onCrop(Uri targetUri, Uri outputUri) {
        this.activity.startActivityForResult(IntentUtils.getCropIntentWith(targetUri, outputUri,
                aspectX, aspectY), RC_CROP);
    }


    @Override
    public void onRnSendMap(ReadableMap value) {
        String name = value.getString("name");
        int age = value.getInt("age");
        System.out.println("segg6575---name = " + name);
        System.out.println("segg6575---age = " + age);
    }

    @Override
    public void onRnSendArray(ReadableArray array) {
        ArrayList<Object> objects = array.toArrayList();
        for (Object obj : objects) {
            System.out.println("segg6575---obj = " + String.valueOf(obj));
        }
    }
}
