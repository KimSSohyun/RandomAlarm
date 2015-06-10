package com.sohyun.androidproj;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by KimSohyun on 2015-06-08.
 */
public class MemView extends View{
    private int dy = 4, dx = 4;
    private int cy, cx;
    private int by, bx;
    private Paint paint = new Paint();
    MemGame newGame;

    public MemView(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
    }
    public MemView(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public MemView(Context context){
        super(context);
    }

    public void start() throws Exception { newGame = new MemGame(); }
    public MemGame.GameState accept(char ch) throws Exception { return newGame.accept(ch); }

    public void onDraw(Canvas canvas){
        try {
            newGame = new MemGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(newGame == null) return;

        int[][] array = newGame.get_array();
        by = (getHeight() - 20 - ((dy-1)*5))/dy;
        bx = (getWidth() - 20 - ((dx-1)*5))/dx;
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);

        cy = 10; cx = 10;
        for(int y = 0; y < dy; y++){
            for(int x = 0; x < dx ; x++){
                switch(array[y][x]){
                    case 0:
                        paint.setColor(Color.WHITE);
                        break;
                    default:
                        paint.setColor(Color.BLACK);
                        break;
                }
                canvas.drawRect(cx,  cy, cx + bx, cy + by, paint);
                cx += (bx + 5);
            }
            cx = 10;
            cy += (by + 5);
        }
    }

    public void onUp() {
        dy --;
        invalidate();
    }
}
