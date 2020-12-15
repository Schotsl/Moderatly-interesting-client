package com.example.moderately.interesting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.content.res.Resources;

public class PlayerSprite {
    private Bitmap bitmap;

    private int xPosition;
    private int yPosition;
    private int xVelocity;
    private int yVelocity;

    private int screenWidth;
    private int screenHeight;

    public PlayerSprite (int screenWidth, int screenHeight, Bitmap bitmap) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        double playerWidth = screenWidth * 0.25;
        double playerHeight = playerWidth * bitmap.getHeight() / bitmap.getWidth();

        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int)playerWidth, (int)playerHeight, true);
        this.yPosition = (int) (screenHeight * 0.85);
        this.xPosition = 100;
        this.yVelocity = 10;
        this.xVelocity = 10;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, xPosition - bitmap.getWidth() / 2, yPosition - bitmap.getHeight() / 2, null);
    }

    public void update(Integer touchX) {
        if (touchX != null) {
            if (touchX > xPosition + xVelocity) xPosition += xVelocity;
            else if (touchX < xPosition - xVelocity) xPosition -= xVelocity;
            else xPosition = touchX;
        }
    }
}
