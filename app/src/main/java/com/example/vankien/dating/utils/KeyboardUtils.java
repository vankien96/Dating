package com.example.vankien.dating.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by vanki on 3/8/2018.
 */

public class KeyboardUtils {
    private static KeyboardUtils shareInstance = new KeyboardUtils();
    public static KeyboardUtils getShareInstance(){
        return shareInstance;
    }
    public void hideKeyboard(Activity activity){
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
