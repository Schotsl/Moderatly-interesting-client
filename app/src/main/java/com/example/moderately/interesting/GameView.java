package com.example.moderately.interesting;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.BitmapFactory;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private PlayerSprite playerSprite;

    public GameView(Context context) {
        super(context);

        setFocusable(true);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        playerSprite = new PlayerSprite(BitmapFactory.decodeResource(getResources(), R.drawable.player));

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread.setRunning(false);
    }

    public void update() {
        playerSprite.update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        if (canvas != null) playerSprite.draw(canvas);
    }
}