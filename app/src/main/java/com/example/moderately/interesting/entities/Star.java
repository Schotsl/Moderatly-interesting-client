package com.example.moderately.interesting.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.moderately.interesting.properties.Position;
import com.example.moderately.interesting.properties.Velocity;

public class Star {
    private int starSize;
    private int starColor;
    private int screenHeight;

    private Position position = new Position();
    private Velocity velocity = new Velocity();

    public Star(int screenWidth, int screenHeight) {
        this.screenHeight = screenHeight;

        this.position.xRandom(0, screenWidth);
        this.position.yRandom(0, screenHeight);
        this.velocity.yRandom(0, 5);

        this.starSize = (int) (Math.random() * 5) + 3;
        this.starColor = (int) (Math.random() * 50);
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(200, 200, 200 + starColor));
        canvas.drawRect(position.xPosition, position.yPosition, position.xPosition + starSize, position.yPosition + starSize, paint);
    }

    public void update() {
        position.yPosition += velocity.yVelocity;

        if (position.yPosition + starSize > screenHeight) position.yPosition = -starSize;
    }
}
