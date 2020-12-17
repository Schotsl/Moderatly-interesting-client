package com.example.moderately.interesting;

import android.graphics.Bitmap;

public class Util {
    public static Bitmap resizeBitmap(int width, Bitmap bitmap) {
        double ratio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
        double height = width * ratio;
        return Bitmap.createScaledBitmap(bitmap, width, (int) height, true);
    }
}
