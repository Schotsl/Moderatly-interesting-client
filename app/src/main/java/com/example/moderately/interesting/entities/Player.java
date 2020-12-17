package com.example.moderately.interesting.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.content.res.Resources;

import com.example.moderately.interesting.properties.Position;
import com.example.moderately.interesting.properties.Velocity;

public class Player {
    private Bitmap bitmap;

    private Position position = new Position();
    private Velocity velocity = new Velocity();

    public Player(int screenWidth, int screenHeight, Bitmap bitmap) {
        double playerWidth = screenWidth * 0.25;
        double playerHeight = playerWidth * bitmap.getHeight() / bitmap.getWidth();
        this.bitmap = Bitmap.createScaledBitmap(bitmap, (int) playerWidth, (int) playerHeight, true);

        this.position.yPosition = (int) (screenHeight * 0.85);
        this.position.xPosition = 100;
        this.velocity.xVelocity = 10;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, position.xPosition - bitmap.getWidth() / 2, position.yPosition - bitmap.getHeight() / 2, null);
    }

    public Position update(Integer touchX) {
        if (touchX != null) {
            if (touchX > position.xPosition + velocity.xVelocity) position.xPosition += velocity.xVelocity;
            else if (touchX < position.xPosition - velocity.xVelocity) position.xPosition -= velocity.xVelocity;
            else position.xPosition = touchX;
        }

        // Have yet to find a way to clone the position object
        Position position = new Position();
        position.yPosition = this.position.yPosition;
        position.xPosition = this.position.xPosition;
        return position;
    }
}
