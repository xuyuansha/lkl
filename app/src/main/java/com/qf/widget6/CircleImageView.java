package com.qf.widget6;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.qf.day28_custemview1.R;

/**
 * 自定义圆形ImageView
 */
public class CircleImageView extends ImageView{

    private Paint paint;
    private int imageType;

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(attrs);
        init();
    }

    /**
     * 解析自定义参数
     * @param attrs
     */
    private void getAttrs(AttributeSet attrs){
        TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.circleimageview);
        imageType = typedArray.getInteger(R.styleable.circleimageview_imagetype, 0);
        typedArray.recycle();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();
        if(imageType != 0 && drawable != null && drawable instanceof BitmapDrawable){
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap();//得到imageview中设置的图片

            int x = getWidth() <= getHeight() ? getWidth() : getHeight();

            //在画布上新建图层
            canvas.saveLayer(new RectF(0, 0, getWidth(), getHeight()), null, 0);
            canvas.drawCircle(x / 2, x / 2, x / 2, paint);

            //设置图片混合模式
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            if(imageType == 1){
                canvas.drawBitmap(bitmap,
                        new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                        new Rect(0, 0, x, x), paint);
            } else if(imageType == 2){
                canvas.drawRoundRect(new RectF(0, 0, x, x), 30, 30, paint);
            }
            paint.setXfermode(null);
            canvas.restore();//复原画布
        } else {
            super.onDraw(canvas);
        }

    }
}
