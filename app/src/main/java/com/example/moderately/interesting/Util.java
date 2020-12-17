package com.example.moderately.interesting;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.moderately.interesting.properties.Position;

public class Util {
    public static Bitmap resizeBitmap(int width, Bitmap bitmap) {
        double ratio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
        double height = width * ratio;

        return Bitmap.createScaledBitmap(bitmap, width, (int) height, true);
    }

    public static void centerDraw(Position position, Bitmap bitmap, Canvas canvas) {
        int xCenter = position.xPosition - bitmap.getWidth() / 2;
        int yCenter = position.yPosition - bitmap.getHeight() / 2;

        canvas.drawBitmap(bitmap, xCenter, yCenter, null);
    }

    public static Boolean collidingBitmap(Bitmap firstBitmap, Position firstPosition, Bitmap secondBitmap, Position secondPosition) {
        return (firstPosition.xPosition - firstBitmap.getWidth() / 2 < secondPosition.xPosition + secondBitmap.getWidth() / 2 &&
                firstPosition.xPosition + firstBitmap.getWidth() / 2 > secondPosition.xPosition - secondBitmap.getWidth() / 2 &&
                firstPosition.yPosition - firstBitmap.getHeight() / 2 < secondPosition.yPosition + secondBitmap.getHeight() / 2 &&
                firstPosition.yPosition + firstBitmap.getHeight() / 2 > secondPosition.yPosition - secondBitmap.getHeight() / 2);
    }
}
