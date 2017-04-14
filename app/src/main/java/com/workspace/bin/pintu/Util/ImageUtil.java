package com.workspace.bin.pintu.Util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.InputStream;

/**
 * Created by bin on 2017/4/10.
 */

public class ImageUtil {
    public static BitmapDrawable setImage(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        opt.inJustDecodeBounds = false;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(is, null, opt);
            is.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
        return new BitmapDrawable(context.getResources(), bitmap);
    }


}
