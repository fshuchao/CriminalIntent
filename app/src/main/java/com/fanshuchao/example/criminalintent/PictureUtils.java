package com.fanshuchao.example.criminalintent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;

/**
 * Created by fanshuchao on 2017-1-19.
 */

public class PictureUtils {

    //先确认屏幕尺寸，按此缩放图像，保证载入的图片不至过大
    public static Bitmap getScaleBitmap(String path, Activity activity){
        Point size = new Point();
        //获取屏幕高度、宽度
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaleBitmap(path,size.x,size.y);
    }

    public static Bitmap getScaleBitmap(String path,int destWidth, int destHeight){
        //Read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path,options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        //Figure out how much to scale down by
        int inSampleSize = 1;
        if (srcHeight>destHeight||srcWidth>destWidth){
            if(srcWidth>srcHeight){
                inSampleSize = Math.round(srcHeight/destHeight);
            }else{
                inSampleSize = Math.round(srcWidth/destWidth);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        //Read in and create final bitmap
        return BitmapFactory.decodeFile(path,options);
    }

}
