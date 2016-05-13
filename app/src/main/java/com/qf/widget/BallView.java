package com.qf.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * 自定义View的分类：
 * 1、组合控件
 * 2、拓展控件
 * 3、完全自绘控件
 *
 * 自定义View的三个核心方法：
 * 1、onMeasure() 测量控件
 * 2、onLayout() 摆放控件 -- 当重写ViewGroup时，需要重写该方法
 * 3、onDraw() 自绘方法
 *
 * invalidate();重绘方法，调用该方法会重新执行onDraw
 * postInvalidate();重绘方法，在子线程调用
 */
public class BallView extends View{

    private Paint paint;//画笔

    private int cx = 100, cy = 100;

    /**
     * 第一个构造方法：当这个自定义的View 需要通过代码的方式去进行创建的时候，重写该方法
     * @param context
     */
    public BallView(Context context) {
        super(context);
        init();
    }

    /**
     * 第二个构造方法：当这个自定义的View 需要在布局文件中声明的时候，重写该方法
     * @param context
        * @param attrs
    */
    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 第三个构造方法：在布局中使用，并且带有样式时，调用该方法
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public BallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setAlpha(100);//设置画笔的透明度
        paint.setAntiAlias(true);//开启抗锯齿
        paint.setColor(Color.RED);//设置画笔的颜色
    }

    /**
     * 绘制方法
     * canvas ：表示一个画布，该控件显示的内容，就是绘制在该画布上
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        //在画布上绘制一个圆形
        canvas.drawCircle(cx, cy, 30, paint);
    }

    /**
     * 触摸事件处理方法
     * @param event
     * @return 返回true,表示该控件已经处理了该事件，在这种情况下，ACTION_MOVE和ACTION_UP才会传递到控件上
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        cx = (int) event.getX();
        cy = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //手指触碰到屏幕时，触发
                invalidate();//重绘控件，调用该方法会重新执行onDraw
                return true;
            case MotionEvent.ACTION_MOVE:
                //手指在屏幕上滑动时，触发
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //手指离开屏幕时，触发
                break;
        }
        return super.onTouchEvent(event);
    }
}
