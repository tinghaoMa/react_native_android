package com.react_native_android.view;

import android.widget.Button;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

public class RCTCustomButton extends SimpleViewManager<Button> {

    private ThemedReactContext mReactContext;

    @Override
    public String getName() {
        return "RCTCustomButton";
    }
    @ReactProp(name = "text")
    public void setText(Button button, String text) {
        button.setText(text);
    }

    @Override
    protected Button createViewInstance(ThemedReactContext reactContext) {

        this.mReactContext = reactContext;
        Button button = new Button(reactContext);
        return button;
    }
}