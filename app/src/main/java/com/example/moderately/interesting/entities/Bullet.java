package com.example.moderately.interesting.entities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.moderately.interesting.Util;
import com.example.moderately.interesting.properties.Position;
import com.example.moderately.interesting.properties.Velocity;

public class Bullet {
    private Bitmap bitmap;
    private Position position;
    private Velocity velocity = new Velocity();

    public Bullet(Position position, Bitmap bitmap) {
        this.velocity.yVelocity = -10;
        this.position = position;
        this.bitmap = bitmap;
    }

    public void draw(Canvas canvas) {
        Util.centerDraw(position, bitmap, canvas);
    }

    public Position update() {
        position.yPosition += velocity.yVelocity;

        // Have yet to find a way to clone the position object
        Position position = new Position();
        position.yPosition = this.position.yPosition;
        position.xPosition = this.position.xPosition;
        return position;
    }

    public boolean outside() {
        return position.yPosition - 10 < 0;
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
