package com.qf.widget7;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.qf.day28_custemview1.R;

/**
 * 自定义开关SwitchButton
 */
public class SwitchButton extends View{

    private static final String TAG = "print";
    private Bitmap bitmapBtnPressed;//获得按钮资源(按下状态)
    private Bitmap bitmapBtnUnPressed;//获得按钮资源(未按下状态)
    private Bitmap bitmapBottom;//获得底图资源
    private Bitmap bitmapFrame;//获得边框资源
    private Bitmap bitmapMask;//获得遮障资源

    private Bitmap bitmapBtn;//当前显示的按钮图片

    private Rect frameRect;//定义一个边框图的宽高矩形
    private Rect showRect;//底图和按钮显示的矩形

    private Paint paint;

    private int moveX;//底图和按钮在X轴上的偏移量
    private int moveMaxX;//移动的最大值


    private boolean isChecked;//当前按钮的选中状态

    private OnSwitchCheckedListener onSwitchCheckedListener;

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setOnSwitchCheckedListener(OnSwitchCheckedListener onSwitchCheckedListener) {
        this.onSwitchCheckedListener = onSwitchCheckedListener;
    }

    /**
     * 初始化资源
     */
    private void init() {
        bitmapBtnPressed = BitmapFactory.decodeResource(getResources(), R.drawable.checkswitch_btn_pressed);
        bitmapBtnUnPressed = BitmapFactory.decodeResource(getResources(), R.drawable.checkswitch_btn_unpressed);
        bitmapBottom = BitmapFactory.decodeResource(getResources(), R.drawable.checkswitch_bottom);
        bitmapFrame = BitmapFactory.decodeResource(getResources(), R.drawable.checkswitch_frame);
        bitmapMask = BitmapFactory.decodeResource(getResources(), R.drawable.checkswitch_mask);

        bitmapBtn = bitmapBtnUnPressed;//设置当前显示的按钮，默认未按下

        //计算底图和按钮的最大偏移量
        moveMaxX = bitmapBottom.getWidth() - bitmapFrame.getWidth();

        frameRect = new Rect(0, 0, bitmapFrame.getWidth(), bitmapFrame.getHeight());
        showRect = new Rect();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    }

    /**
     * 测量控件
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(bitmapFrame.getWidth(), bitmapFrame.getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        showRect.set(moveX, 0, bitmapFrame.getWidth() + moveX, bitmapFrame.getHeight());

        canvas.saveLayer(new RectF(frameRect), null, 0);
        //先绘制底图
        canvas.drawBitmap(bitmapBottom, showRect, frameRect, null);
        //绘制边框
        canvas.drawBitmap(bitmapFrame, frameRect, frameRect, null);
        //绘制按钮
        canvas.drawBitmap(bitmapBtn, showRect, frameRect, null);
        //绘制遮障
        canvas.drawBitmap(bitmapMask, frameRect, frameRect, paint);
        canvas.restore();
    }


    private int bx,
            bx2;//用来标示初始点击位置
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                bitmapBtn = bitmapBtnPressed;//把按钮设置为按下的状态
                bx2 = bx = x;
                invalidate();//重绘
                break;
            case MotionEvent.ACTION_MOVE:
                //计算偏移量
                moveX -= x - bx;
                bx = x;

                if(moveX < 0){
                    moveX = 0;
                    isChecked = false;
                }

                if(moveX > moveMaxX){
                    moveX = moveMaxX;
                    isChecked = true;
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                bitmapBtn = bitmapBtnUnPressed;//把按钮设置为未按下的状态
                if(Math.abs(bx2 - x) <= 10){
                    //表示点击事件
                    isChecked = !isChecked;
                    new MoveThread().start();
                }


                if(moveX != 0 && moveX != moveMaxX){
                    if(moveMaxX/2 < moveX){
                        moveX = moveMaxX;
                        isChecked = true;
                    } else {
                        moveX = 0;
                        isChecked = false;
                    }
                }

                if(onSwitchCheckedListener != null){
                    onSwitchCheckedListener.onCheckedChange(this, isChecked);
                }

                invalidate();
                break;
        }
        return true;
    }


    /**
     * 点击时，按钮滑动的线程
     */
    class MoveThread extends Thread{
        @Override
        public void run() {
            while(true){
                if(isChecked){
                    moveX += 5;
                } else {
                    moveX -= 5;
                }

                postInvalidate();

                if(moveX < 0){
                    moveX = 0;
                    postInvalidate();
                    break;
                }

                if(moveX > moveMaxX){
                    moveX = moveMaxX;
                    postInvalidate();
                    break;
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface OnSwitchCheckedListener{
        void onCheckedChange(SwitchButton switchBtn, boolean isChecked);
    }

    /**
     * 设置按钮的状态
     */
    public void setChecked(boolean isChecked){
        if(isChecked){
            moveX = moveMaxX;
        } else {
            moveX = 0;
        }

        if(onSwitchCheckedListener != null){
            onSwitchCheckedListener.onCheckedChange(this, isChecked);
        }
        invalidate();
    }
}
