package com.qf.day28_custemview1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qf.widget9.TabView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ken on 2016/1/29.
 */
public class Custem9Activity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private String[] datas = {"头条", "直播间", "湘西土家族苗族自治州", "科技", "体育在线"};
    private TabView tabView;

    private ViewPager vp;
    private MyPagerAdapter myPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhview);
        
        initView();
    }

    private void initView() {
        tabView = (TabView) findViewById(R.id.tab_view);
        tabView.setTabs(datas);

        //初始化ViewPager相关
        vp = (ViewPager) findViewById(R.id.vp_id);
        myPagerAdapter = new MyPagerAdapter(this, datas);
        vp.setAdapter(myPagerAdapter);
        vp.addOnPageChangeListener(this);
    }

    /**
     * ViewPager的适配器
     * @author Ken
     *
     */
    class MyPagerAdapter extends PagerAdapter {

        private Context context;
        private String[] datas;
        private List<TextView> tvList;

        public MyPagerAdapter(Context context, String[] datas){
            this.context = context;
            this.datas = datas;

            tvList = new ArrayList<TextView>();
            for(int i = 0; i < datas.length; i++){
                TextView tv = new TextView(context);
                tv.setText(datas[i]);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(50);
                tvList.add(tv);
            }
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(tvList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(tvList.get(position));
            return tvList.get(position);
        }

        @Override
        public int getCount() {
            return datas.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }



    /**
     * ViewPager的监听器
     */
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }


    @Override
    public void onPageScrolled(int position, float pyl, int arg2) {
        tabView.setNavAddress(position, pyl);
    }


    @Override
    public void onPageSelected(int arg0) {

    }
}
