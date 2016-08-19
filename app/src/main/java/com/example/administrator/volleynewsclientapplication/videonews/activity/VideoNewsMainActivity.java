package com.example.administrator.volleynewsclientapplication.videonews.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.volleynewsclientapplication.R;
import com.example.administrator.volleynewsclientapplication.videonews.fragment.VideoNewsFragment;


public class VideoNewsMainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mVideoNewsViewPager;
    private TextView mNewsHotVideoTxt,mNewsRecreationVideoTxt,mNewsFunnyVideoTxt,mNewsQualityVideoTxt;
    private ImageView mVideoTitleImg;
    private String[] video_type_id = {"V9LG4B3A0", "V9LG4CHOR", "V9LG4E6VR", "00850FRB"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_news_main);

        mNewsHotVideoTxt = (TextView) findViewById(R.id.news_hot_video);
        mNewsRecreationVideoTxt = (TextView) findViewById(R.id.news_recreation_video);
        mNewsFunnyVideoTxt = (TextView) findViewById(R.id.news_funny_video);
        mNewsQualityVideoTxt = (TextView) findViewById(R.id.news_quality_video);
        mVideoTitleImg = (ImageView) findViewById(R.id.video_news_title);
        mVideoTitleImg.setOnClickListener(this);
        mNewsHotVideoTxt.setOnClickListener(this);
        mNewsRecreationVideoTxt.setOnClickListener(this);
        mNewsFunnyVideoTxt.setOnClickListener(this);
        mNewsQualityVideoTxt.setOnClickListener(this);

        mNewsHotVideoTxt.setBackgroundResource(R.drawable.btn_common_pressed);
        mNewsHotVideoTxt.setTextColor(getResources().getColor(R.color.colorWhite));

        mVideoNewsViewPager = (ViewPager) findViewById(R.id.video_news_view_pager);
        mVideoNewsViewPager.addOnPageChangeListener(this);

        VideoNewsAdapter adapter = new VideoNewsAdapter(getSupportFragmentManager());
        mVideoNewsViewPager.setAdapter(adapter);

        adapter.setData(video_type_id);
    }


    @Override
    public void onClick(View view) {
        clearBackgroundColor();
        switch (view.getId()){
            case R.id.news_hot_video:
                mVideoNewsViewPager.setCurrentItem(0);
                mNewsHotVideoTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsHotVideoTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.news_recreation_video:
                mVideoNewsViewPager.setCurrentItem(1);
                mNewsRecreationVideoTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsRecreationVideoTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.news_funny_video:
                mVideoNewsViewPager.setCurrentItem(2);
                mNewsFunnyVideoTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsFunnyVideoTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.news_quality_video:
                mVideoNewsViewPager.setCurrentItem(3);
                mNewsQualityVideoTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsQualityVideoTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.video_news_title:
                finish();
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
                mNewsHotVideoTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsHotVideoTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case 1:
                mNewsRecreationVideoTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsRecreationVideoTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case 2:
                mNewsFunnyVideoTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsFunnyVideoTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case 3:
                mNewsQualityVideoTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsQualityVideoTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void clearBackgroundColor() {
        mNewsHotVideoTxt.setBackgroundColor(Color.TRANSPARENT);
        mNewsRecreationVideoTxt.setBackgroundColor(Color.TRANSPARENT);
        mNewsFunnyVideoTxt.setBackgroundColor(Color.TRANSPARENT);
        mNewsQualityVideoTxt.setBackgroundColor(Color.TRANSPARENT);
        mNewsHotVideoTxt.setTextColor(getResources().getColor(R.color.colorHui));
        mNewsRecreationVideoTxt.setTextColor(getResources().getColor(R.color.colorHui));
        mNewsFunnyVideoTxt.setTextColor(getResources().getColor(R.color.colorHui));
        mNewsQualityVideoTxt.setTextColor(getResources().getColor(R.color.colorHui));
    }

    class VideoNewsAdapter extends FragmentStatePagerAdapter {
        String[] video_type_id = new String[]{};

        public VideoNewsAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(String[] video_type_id) {
            this.video_type_id = video_type_id;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return VideoNewsFragment.newInstance(video_type_id[position]);
        }

        @Override
        public int getCount() {
            return video_type_id.length;
        }
    }
}
