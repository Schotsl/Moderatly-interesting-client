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
        this.xPosition = 100;
        this.yPosition = 100;
        this.xVelocity = 10;
        this.yVelocity = 10;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, xPosition, yPosition, null);
    }

    public void update() {
        xPosition += xVelocity;
        yPosition += yVelocity;

        if (xPosition + bitmap.getWidth() > screenWidth || xPosition < 0) xVelocity =  -xVelocity;
        if (yPosition + bitmap.getHeight() > screenHeight || yPosition < 0) yVelocity = -yVelocity;
    }
}
