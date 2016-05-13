package com.qf.widget2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.qf.day28_custemview1.R;

/**
 * Canvas 和 Paint
 */
public class CustemView extends View{

    private Paint paint;

    public CustemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();//初始化画笔
        paint.setAlpha(10);//设置透明度
        paint.setAntiAlias(true);//开启抗锯齿
        paint.setColor(Color.RED);//设置画笔的颜色
        /**
         * Paint.Style.STROKE 空心绘制
         * Paint.Style.FILL 实心绘制
         */
        paint.setStyle(Paint.Style.FILL);//设置画笔的样式
        paint.setStrokeWidth(1);//设置描边的宽度，和Paint.Style.STROKE 配合使用
        paint.setTextSize(20);//设置字体的大小
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制圆形
        canvas.drawCircle(100, 100, 30, paint);

        //绘制文本， 默认在文本的左下角（在基线上）
        canvas.drawText("Hello World!", 0, 20, paint);

        /**
         * 绘制矩形
         * Rect:是int类型
         * RectF:是Float类型
         * 两个类的方法不完全一样
         */
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(100, 100, 300, 200, paint);
        Rect rect = new Rect(200, 200, 300, 400);
        canvas.drawRect(rect, paint);

        /**
         * 绘制一个圆角矩形
         */
        RectF rectF = new RectF(20f, 320f, 150f, 400f);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(rectF, 40f, 40f, paint);

        /**
         * 绘制一条线
         */
        canvas.drawLine(0, 0, 200, 100, paint);

        /**
         * 绘制路径
         * Path说明：
         * close() 该方法可以进行路径合并
         * lineTo() 绘制直线
         * quadTo() 绘制一条曲线，绘制的方式，贝赛尔曲线
         */
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        Path path = new Path();
        path.moveTo(100, 100);
        path.quadTo(200, 50, 400, 100);
//        path.lineTo(200, 50);
//        path.lineTo(300, 50);
//        path.lineTo(400, 100);
//        path.close();//调用这个方法，会进行路径合并
//        canvas.drawPath(path, paint);

        /**
         * 绘制文本根据路径
         */
        canvas.drawTextOnPath("Hello World!", path, 0, 0, paint);

        /**
         * 绘制Bitmap
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Rect rect1 = new Rect(0, 0, bitmap.getWidth()/2, bitmap.getHeight());//表示显示的位图范围
        Rect rectWz = new Rect(350, 0, 450, 100);//表示图片绘制的位置
        canvas.drawBitmap(bitmap, rect1, rectWz, paint);
    }
}
