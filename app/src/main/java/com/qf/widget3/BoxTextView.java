package com.qf.widget3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 带边框的TextView
 */
public class BoxTextView extends TextView{

    private Paint paint, crilcPaint;
    private int cx, cy;//圆形的坐标
    private int radiu;//圆形的半径

    private int dx, dy;//手指触碰的当前坐标

    private boolean flag, down;

    public BoxTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setPadding(10, 10, 10, 10);
        setBackgroundColor(Color.WHITE);

        paint = new Paint();
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);


        //水波的画笔
        crilcPaint = new Paint();
        crilcPaint.setColor(Color.parseColor("#88888888"));
        crilcPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制圆形
        if(down) {
            if (cx != 0 && cy != 0) {
                canvas.drawCircle(cx, cy, radiu, crilcPaint);
            }
        } else {
            canvas.drawColor(Color.WHITE);
        }

        //绘制一个边框
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawRect(rect, paint);

        super.onDraw(canvas);//不可删除


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
//                setBackgroundColor(Color.BLUE);
                cx = (int) event.getX();
                cy = (int) event.getY();

                flag = true;
                down = true;
                new TimerThread().start();
                return true;
            case MotionEvent.ACTION_MOVE:
                dx = (int) event.getX() - cx;
                dy = (int) event.getY() - cy;
                this.layout(getLeft() + dx, getTop() + dy, getRight() + dx, getBottom() + dy);
                break;
            case MotionEvent.ACTION_UP:
//                setBackgroundColor(Color.WHITE);
                flag = false;
                down = false;
                radiu = 0;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 线程
     */
    class TimerThread extends Thread{

        @Override
        public void run() {
            while(flag){
                radiu += 10;
                if(radiu >= getWidth()){
                    flag = false;
                    break;
                }

                postInvalidate();

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
