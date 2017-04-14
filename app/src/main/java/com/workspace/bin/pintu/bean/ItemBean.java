package com.workspace.bin.pintu.bean;

import android.graphics.Bitmap;

/**
 * Created by bin on 2017/4/10.
 */

public class ItemBean {
    // Item的Id
    private int mItemId;
    // bitmap的Id
    private int mBitmapId;
    // mBitmap
    private Bitmap mBitmap;

    public ItemBean() {
    }

    public ItemBean(int mItemId, int mBitmapId, Bitmap mBitmap) {
        this.mItemId = mItemId;
        this.mBitmapId = mBitmapId;
        this.mBitmap = mBitmap;
    }

    public int getItemId() {
        return mItemId;
    }

    public void setItemId(int mItemId) {
        this.mItemId = mItemId;
    }

    public int getBitmapId() {
        return mBitmapId;
    }

    public void setBitmapId(int mBitmapId) {
        this.mBitmapId = mBitmapId;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }
//    private int mItemId;
//    private int mBitmapId;
//    private Bitmap mBitmap;
//
//    public ItemBean() {
//    }
//
//    public ItemBean(Bitmap mBitmap, int mBitmapId, int mItemId) {
//        this.mBitmap = mBitmap;
//        this.mBitmapId = mBitmapId;
//        this.mItemId = mItemId;
//    }
//
//    public Bitmap getmBitmap() {
//        return mBitmap;
//    }
//
//    public void setmBitmap(Bitmap mBitmap) {
//        this.mBitmap = mBitmap;
//    }
//
//    public int getmBitmapId() {
//        return mBitmapId;
//    }
//
//    public void setmBitmapId(int mBitmapId) {
//        this.mBitmapId = mBitmapId;
//    }
//
//    public int getmItemId() {
//        return mItemId;
//    }
//
//    public void setmItemId(int mItemId) {
//        this.mItemId = mItemId;
//    }
}
