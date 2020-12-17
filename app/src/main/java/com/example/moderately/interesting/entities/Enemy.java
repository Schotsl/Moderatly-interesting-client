package com.example.moderately.interesting.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Enemy {
    private Bitmap bitmap;

    private int xPosition;
    private int yPosition;
    private int yVelocity;

    private int screenWidth;
    private int screenHeight;

    public Enemy(int screenWidth, int screenHeight, Bitmap bitmap) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        double enemyWidth = screenWidth * 0.25;
        double enemyHeight = enemyWidth * bitmap.getHeight() / bitmap.getWidth();
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) enemyWidth, (int) enemyHeight, true);

        this.yPosition = (int) (-screenHeight * Math.random());
        this.xPosition = (int) (screenWidth * Math.random());
        this.yVelocity = 10;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, xPosition - bitmap.getWidth() / 2, yPosition - bitmap.getHeight() / 2, null);
    }

    public void update() {
        yPosition += yVelocity;

        if (yPosition - bitmap.getHeight() / 2 > screenHeight) {
            yPosition = -bitmap.getHeight() / 2;
            xPosition = (int) (Math.random() * screenWidth);
        }
    }
}
