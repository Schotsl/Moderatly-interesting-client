package com.example.moderately.interesting;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private Canvas canvas;
    private boolean running;
    private GameView gameView;
    private SurfaceHolder surfaceHolder;

    public MainThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.gameView = gameView;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run()
    {
        while (running) {
            canvas = this.surfaceHolder.lockCanvas();

            synchronized (surfaceHolder) {
                this.gameView.update();
                this.gameView.draw(canvas);
            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }
}