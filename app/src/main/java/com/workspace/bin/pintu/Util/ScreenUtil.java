package com.workspace.bin.pintu.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by bin on 2017/4/10.
 */

public class ScreenUtil {
    /*
    * 获取屏幕相关参数
    *@param Context context
    * @return DisplayMetrics 屏幕宽高
    * */

    public static DisplayMetrics getScreenSize(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }

    /*
    * 获取屏幕density
    *
    * @param Context context
    * @return density 屏幕density
    * */

    public static float getDeviceDisplay(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.density;
    }
}
