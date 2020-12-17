package com.example.moderately.interesting.entities;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.moderately.interesting.properties.Position;
import com.example.moderately.interesting.properties.Velocity;

public class Bullet {
    private Position position;
    private Velocity velocity = new Velocity();

    public Bullet(Position position) {
        this.velocity.yVelocity = -10;
        this.position = position;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.rgb(255, 0, 0));
        canvas.drawRect(position.xPosition - 5, position.yPosition - 10, position.xPosition + 5, position.yPosition + 10, paint);
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
}
