package com.qf.widget4;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.qf.day28_custemview1.R;

/**
 * 仿TextView
 *
 * 自定义属性：
 * 1、在values文件夹下创建attrs.xml配置文件
 * 2、在使用该自定义属性的布局文件上添加命名控件xmlns:app="http://schemas.android.com/apk/res-auto"
 * 3、在布局文件上通过app:xxx的方式使用自定义属性
 * 4、在自定义控件中获得自定义属性
 */
public class LabelText extends View {

    private static final String TAG = "print";
    private Paint paint;
    private String text;
    private int tsize;
    private int tcolor;

    public LabelText(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        init();
    }

    /**
     * 解析自定义属性的方法
     */
    private void getAttrs(AttributeSet attrs){
        TypedArray obtainAttributes = getResources().obtainAttributes(attrs, R.styleable.labeltext);
        //获得文本
        text = obtainAttributes.getString(R.styleable.labeltext_textv);
        //获得文本的大小
        tsize = (int) obtainAttributes.getDimension(R.styleable.labeltext_sizev, 15);
        //获得文本的颜色
        tcolor = obtainAttributes.getColor(R.styleable.labeltext_colorv, Color.BLACK);

        Log.d(TAG, "getAttrs: " + text + "   " + tsize + "   " + tcolor);
        //回收资源
        obtainAttributes.recycle();
    }

    private void init() {
        paint = new Paint();
        paint.setTextSize(tsize);
        paint.setAntiAlias(true);
        paint.setColor(tcolor);
        paint.setTextAlign(Paint.Align.CENTER);//设置文本的对齐方式
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawPoint(getWidth()/2, getHeight()/2, new Paint());
        setBackgroundColor(Color.GRAY);
        canvas.drawText(text, getWidth() / 2, getHeight() / 2 + ((paint.descent() - paint.ascent()) / 2 - paint.descent()), paint);
    }

    /**
     * 测量控件的方法
     * @param widthMeasureSpec 文本宽度的空间测量值
     * @param heightMeasureSpec 文本高度的控件测量值
     *
     * 测量值是一个32位整数，前两位是一个模式位，后面存放的是数值位
     * 里面包含两个数据：第一高度个数据是当前高度（宽度）的模式
     *               第二个数据是当前（宽度）所能摆放的系统推荐值
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得测量的宽度值
        int width = measureView(widthMeasureSpec, 0);
        //获得测量的高度值
        int height = measureView(heightMeasureSpec, 1);
        //对宽高进行一个保存
        setMeasuredDimension(width, height);
    }

    /**
     * 测量工具方法
     * @param spec 宽或高的空间值
     * @param type 0 - 表示测量宽度 1 - 表示测量高度
     *
     * 测量文本的高度:paint.descent() - paint.ascent()
     * 测量文本的宽度:paint.measureText("xxxx")
     * 测量的画笔，必须为绘制该文本的画笔
     * @return
     */
    private int measureView(int spec, int type){
        int mode = MeasureSpec.getMode(spec);//从空间值中获得模式
        int size = MeasureSpec.getSize(spec);//获得系统的推荐值
        switch (mode){
            case MeasureSpec.EXACTLY:
                //表示精确的值，当空间的宽（高）设置为match_parent时，或者为一个精确的数值时，会是这种模式
                return size;
            case MeasureSpec.AT_MOST:
                //表示尽可能多，当空间的宽（高）设置为warp_content时，是这种模式，这种模式的宽高最大不能超过size
                if(type == 0){
                    //表示需要测量宽度
                    return (int) (paint.measureText(text) + getPaddingLeft() + getPaddingRight());
                } else {
                    //表示需要测量高度
                    return (int) (paint.descent() - paint.ascent() + getPaddingTop() + getPaddingBottom());
                }
            case MeasureSpec.UNSPECIFIED:
                //表示想要多大给多大，该模式通常出现在scrollview这种父容器对子控件宽高不设限的情况
                break;
        }
        return size;
    }
}
