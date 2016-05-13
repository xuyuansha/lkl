package com.qf.widget9;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qf.day28_custemview1.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合控件
 */
public class TabView extends HorizontalScrollView implements View.OnClickListener {

    private LinearLayout contLayout;
    private LinearLayout.LayoutParams layoutParams;
    private View navView;//导航光标

    private List<Integer> viewWidths;//用来存放所有tab组件的宽度

    public TabView(Context context) {
        super(context);
        init();
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化组合控件
     */
    public void init() {
        /**
         * 第三个参数为ture表示：将第一个参数所代表的布局的子控件全部附加给第二参数所代表的控件
         */
        LayoutInflater.from(getContext()).inflate(R.layout.tab_layout, this, true);
        contLayout = (LinearLayout) this.findViewById(R.id.ll_tab);//获得添加tab的线性布局
        navView = this.findViewById(R.id.view_nav);//获得导航小光标

        layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //取消HorizontalScroll的滑动光标
        this.setHorizontalScrollBarEnabled(false);
    }

    /**
     * 设置组合控件的tab项
     * 由外部调用
     * @param tabs
     */
    public void setTabs(String[] tabs){
        viewWidths = new ArrayList<>();

        for(int i = 0; i < tabs.length; i++){
            TextView tv = new TextView(getContext());
            tv.setText(tabs[i]);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(20, 0, 20, 0);
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(this);
            tv.setTag(i);
            contLayout.addView(tv);

            //调用一下组件的测量方法
            tv.measure(0, 0);//通知系统对组件进行测量
            viewWidths.add(tv.getMeasuredWidth());
//			Log.d("print", "tab的宽高---->" + tv.getMeasuredWidth() + "   " + tv.getMeasuredHeight());
        }

        //讲第一个tab项的宽度赋给光标
        LinearLayout.LayoutParams viewLayoutParams = (LinearLayout.LayoutParams) navView.getLayoutParams();
        viewLayoutParams.width = viewWidths.get(0);
        navView.setLayoutParams(viewLayoutParams);
    }

    /**
     * tab项的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * 设置nav的位置，用于和ViewPager联动
     */
    public void setNavAddress(int position, float pyl){
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) navView.getLayoutParams();

        //计算光标的移动位置
        int width = 0;
        for(int i = 0; i < position; i++){
            width += viewWidths.get(i);
        }
        width += viewWidths.get(position) * pyl;
        layoutParams.leftMargin = width;

        //计算光标的长度
        if(position < viewWidths.size() - 1){
            layoutParams.width = (int) (viewWidths.get(position) + (viewWidths.get(position + 1) - viewWidths.get(position)) * pyl);
        } else {
            layoutParams.width = viewWidths.get(position);
        }
        navView.setLayoutParams(layoutParams);

//        hsHorizontalScrollView.scrollTo(layoutParams.leftMargin - 50, 0);
        this.scrollTo(layoutParams.leftMargin - 50, 0);
    }
}
