package com.example.moderately.interesting.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Star {
    private int starSize;
    private int starColor;

    private int xPosition;
    private int yPosition;
    private int yVelocity;

    private int screenHeight;

    public Star(int screenWidth, int screenHeight) {
        this.screenHeight = screenHeight;

        this.xPosition = (int) (Math.random() * screenWidth);
        this.yPosition = (int) (Math.random() * screenHeight);

        this.starSize = (int) (Math.random() * 5) + 3;
        this.yVelocity = (int) (Math.random() * 5) + 5;
        this.starColor = (int) (Math.random() * 50);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(200, 200, 200 + starColor));
        canvas.drawRect(xPosition, yPosition, xPosition + starSize, yPosition + starSize, paint);
    }

    public void update() {
        yPosition += yVelocity;

        if (yPosition + starSize > screenHeight) yPosition = -starSize;
    }
}
