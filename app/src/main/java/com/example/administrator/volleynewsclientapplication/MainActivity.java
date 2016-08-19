package com.example.administrator.volleynewsclientapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.volleynewsclientapplication.textnews.fragment.MenuFragment;
import com.example.administrator.volleynewsclientapplication.textnews.fragment.TextNewsFragment;
import com.warmtel.slidingmenu.lib.SlidingMenu;
import com.warmtel.slidingmenu.lib.app.SlidingActivity;

public class MainActivity extends SlidingActivity implements MenuFragment.OnMenuClickListener, ViewPager.OnPageChangeListener, View.OnClickListener {
    private SlidingMenu slidingMenu;
    private ViewPager mTextNewsViewPager;
    private TextView mTextNewsHeadlineTxt, mTextNewsRecreationTxt, mTextNewsPhysicalTxt, mTextNewsEconomicsTxt, mTextNewsScienceTxt;
    private String[] news_type_id = {"T1348647909107", "T1348648517839",
            "T1348649079062", "T1348648756099", "T1348649580692"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_news);

        mTextNewsHeadlineTxt = (TextView) findViewById(R.id.text_news_headline);
        mTextNewsRecreationTxt = (TextView) findViewById(R.id.text_news_recreation);
        mTextNewsPhysicalTxt = (TextView) findViewById(R.id.text_news_physical);
        mTextNewsEconomicsTxt = (TextView) findViewById(R.id.text_news_economics);
        mTextNewsScienceTxt = (TextView) findViewById(R.id.text_news_science);
        mTextNewsHeadlineTxt.setOnClickListener(this);
        mTextNewsRecreationTxt.setOnClickListener(this);
        mTextNewsPhysicalTxt.setOnClickListener(this);
        mTextNewsEconomicsTxt.setOnClickListener(this);
        mTextNewsScienceTxt.setOnClickListener(this);

        mTextNewsHeadlineTxt.setBackgroundResource(R.drawable.btn_common_pressed);
        mTextNewsHeadlineTxt.setTextColor(getResources().getColor(R.color.colorWhite));

        mTextNewsViewPager = (ViewPager) findViewById(R.id.text_news_view_pager);
        mTextNewsViewPager.addOnPageChangeListener(this);

        NewsTextTitleAdapter adapter = new NewsTextTitleAdapter(getSupportFragmentManager());
        mTextNewsViewPager.setAdapter(adapter);
        adapter.setData(news_type_id);

        setBehindContentView(R.layout.sliding_menu_layout);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.sliding_menu_layout, MenuFragment.newInstance())
                .commit();
        slidingMenu = getSlidingMenu();
        slidingMenu.setSlidingEnabled(true);
        slidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_off_width);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setMode(SlidingMenu.LEFT);
    }

    @Override
    public void onClick(View view) {
        clearBackgroundColor();
        switch (view.getId()){
            case R.id.text_news_headline:
                mTextNewsViewPager.setCurrentItem(0);
                mTextNewsHeadlineTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mTextNewsHeadlineTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.text_news_recreation:
                mTextNewsViewPager.setCurrentItem(1);
                mTextNewsRecreationTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mTextNewsRecreationTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.text_news_physical:
                mTextNewsViewPager.setCurrentItem(2);
                mTextNewsPhysicalTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mTextNewsPhysicalTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.text_news_economics:
                mTextNewsViewPager.setCurrentItem(3);
                mTextNewsEconomicsTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mTextNewsEconomicsTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.text_news_science:
                mTextNewsViewPager.setCurrentItem(4);
                mTextNewsScienceTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mTextNewsScienceTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        clearBackgroundColor();
        switch (position) {
            case 0:
                mTextNewsHeadlineTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mTextNewsHeadlineTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case 1:
                mTextNewsRecreationTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mTextNewsRecreationTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case 2:
                mTextNewsPhysicalTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mTextNewsPhysicalTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case 3:
                mTextNewsEconomicsTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mTextNewsEconomicsTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case 4:
                mTextNewsScienceTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mTextNewsScienceTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void clearBackgroundColor() {
        mTextNewsHeadlineTxt.setBackgroundColor(Color.TRANSPARENT);
        mTextNewsRecreationTxt.setBackgroundColor(Color.TRANSPARENT);
        mTextNewsPhysicalTxt.setBackgroundColor(Color.TRANSPARENT);
        mTextNewsEconomicsTxt.setBackgroundColor(Color.TRANSPARENT);
        mTextNewsScienceTxt.setBackgroundColor(Color.TRANSPARENT);
        mTextNewsHeadlineTxt.setTextColor(getResources().getColor(R.color.colorHui));
        mTextNewsRecreationTxt.setTextColor(getResources().getColor(R.color.colorHui));
        mTextNewsPhysicalTxt.setTextColor(getResources().getColor(R.color.colorHui));
        mTextNewsEconomicsTxt.setTextColor(getResources().getColor(R.color.colorHui));
        mTextNewsScienceTxt.setTextColor(getResources().getColor(R.color.colorHui));
    }

    @Override
    public void onMenuClick() {
        slidingMenu.toggle();
    }

    class NewsTextTitleAdapter extends FragmentStatePagerAdapter {
        String[] news_type_id = new String[]{};

        public NewsTextTitleAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(String[] news_type_id) {
            this.news_type_id = news_type_id;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return TextNewsFragment.newInstance(news_type_id[position], position);
        }

        @Override
        public int getCount() {
            return news_type_id.length;
        }
    }
}

