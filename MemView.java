package com.sohyun.androidproj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by KimSohyun on 2015-06-08.
 */
public class MemView extends View {
    private int dy = 4, dx = 4;
    private int cy, cx;
    private int by, bx;
    private Paint paint = new Paint();
    public MemGame newGame;
    private int[][] quizarr;
    private Timer quiztimer;
    private int quizidx, gapidx;
    private int level;

    public MemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MemView(Context context) {
        super(context);
    }

    public void start(int level) throws Exception {
        newGame = new MemGame(level);
    }

    public MemGame.GameState accept(int ch) throws Exception {
        return newGame.accept(ch);
    }

    public void printQuiz(int l){
        quizidx = 0;
        gapidx = 0;
        level = l;
        activateTimer();
    }
    public void printClickBtn(int ch){
        if(ch == -1) return;
        int[] arr = new int[dx*dy];
        level = 1;
        arr[ch] = 1;
        quizarr = newGame.FirstToSecondArr(arr);
        activateTimer();
    }
    public void printWrong(){
        level = 1; int i, j;
        quizarr = new int[dy][dx];
        for(i=0 ; i<dy ; i++){
            for(j=0; j<dx ; j++){
                quizarr[i][j] = 1;
            }
        }
        activateTimer();
    }
    private class TimerHandlerView extends TimerTask {
        public void run(){
            if(gapidx == quizidx) {
                quizarr = newGame.get_array(quizidx);
                postInvalidate();
                Log.d("MemView", "ViewTimer-invalidate");
                quizidx++;
            }
            else {
                quizarr = new int[dy][dx];
                postInvalidate();
                gapidx++;
            }
            if(quizidx >= level && gapidx == quizidx) {
                deactivateTimer();
                quizidx = 0;
            }
        }
    }
    public void activateTimer(){
        if(quiztimer == null){
            TimerHandlerView job = new TimerHandlerView();
            quiztimer = new Timer(true);
            quiztimer.scheduleAtFixedRate(job, 500, 500);
        }
    }
    public void deactivateTimer(){
        if(quiztimer != null){
            quiztimer.cancel();
            quiztimer = null;
        }
    }


    public void onDraw(Canvas canvas) {
        if (newGame == null) return;
        else if(quizarr == null) quizarr = new int[dx][dy];

        int[][] array = quizarr;
        by = (getHeight() - 20 - ((dy - 1) * 3)) / dy;
        bx = (getWidth() - 20 - ((dx - 1) * 3)) / dx;
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);

        cy = 10;
        cx = 10;
        for (int y = 0; y < dy; y++) {
            for (int x = 0; x < dx; x++) {
                switch (array[y][x]) {
                    case 0:
                        paint.setColor(Color.WHITE);
                        break;
                    default:
                        paint.setColor(Color.BLACK);
                        break;
                }
                canvas.drawRect(cx, cy, cx + bx, cy + by, paint);
                cx += (bx + 5);
            }
            cx = 10;
            cy += (by + 5);
        }
    }
}
