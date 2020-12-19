package com.example.moderately.interesting.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.content.res.Resources;
import android.graphics.Rect;

import com.example.moderately.interesting.Util;
import com.example.moderately.interesting.properties.Position;
import com.example.moderately.interesting.properties.Velocity;

public class Player {
    private Bitmap bitmap;
    private Position position = new Position();
    private Velocity velocity = new Velocity();

    public Player(int screenWidth, int screenHeight, Bitmap bitmap) {
        this.bitmap = bitmap;
        this.position.yPosition = (int) (screenHeight * 0.85);
        this.position.xPosition = 100;
        this.velocity.xVelocity = 10;
    }

    public void draw(Canvas canvas) {
        Util.centerDraw(position, bitmap, canvas);
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

    /**
     * Returns the Rect shape corresponding with this bitmap, used for collision detection
     * @return Rect matching the bitmap size.
     */
    public Rect getCollisionShape() {
        int left = position.xPosition - bitmap.getWidth() / 2;
        int top = position.yPosition - bitmap.getHeight() / 2;
        int right = position.xPosition + bitmap.getWidth() / 2;
        int bottom = position.yPosition + bitmap.getWidth() / 2;

        return new Rect(left, top, right, bottom);
    }
}
