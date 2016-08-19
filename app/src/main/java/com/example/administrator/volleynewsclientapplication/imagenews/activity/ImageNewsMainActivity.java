package com.example.administrator.volleynewsclientapplication.imagenews.activity;

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
import com.example.administrator.volleynewsclientapplication.imagenews.fragment.ImageNewsFragment;


public class ImageNewsMainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
  private String[] newsType = {"toutiao&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=&imei=867064013906290&uid=802909da86d9f5fc&p=",
  "funny&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&p=",
  "pretty&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&p=",
  "story&adid=4ad30dabe134695c3b7c3a65977d7e72&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&p="};
    private ViewPager mPicteresViewPager;
    private ImageView mImageNewsTitleImg;
    private TextView mNewsCollectionPicturesTxt,mNewsFunnyPicturesTxt,mNewsPrettyPicturesTxt,mNewsStoryPicturesTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_main);
        mNewsCollectionPicturesTxt = (TextView) findViewById(R.id.news_collection_pictures);
        mNewsFunnyPicturesTxt = (TextView) findViewById(R.id.news_funny_pictures);
        mNewsPrettyPicturesTxt = (TextView) findViewById(R.id.news_pretty_pictures);
        mNewsStoryPicturesTxt = (TextView) findViewById(R.id.news_story_pictures);
        mImageNewsTitleImg = (ImageView) findViewById(R.id.image_news_title);
        mNewsCollectionPicturesTxt.setOnClickListener(this);
        mNewsFunnyPicturesTxt.setOnClickListener(this);
        mNewsPrettyPicturesTxt.setOnClickListener(this);
        mNewsStoryPicturesTxt.setOnClickListener(this);
        mImageNewsTitleImg.setOnClickListener(this);

        mNewsCollectionPicturesTxt.setBackgroundResource(R.drawable.btn_common_pressed);
        mNewsCollectionPicturesTxt.setTextColor(getResources().getColor(R.color.colorWhite));

        mPicteresViewPager = (ViewPager) findViewById(R.id.image_news_view_pager);
        mPicteresViewPager.addOnPageChangeListener(this);
        ImageNewsTitleAdapter adapter = new ImageNewsTitleAdapter(getSupportFragmentManager());
        mPicteresViewPager.setAdapter(adapter);
        adapter.setData(newsType);
    }

    @Override
    public void onClick(View view) {
        clearBackgroundColor();
        switch (view.getId()){
            case R.id.news_collection_pictures:
                mPicteresViewPager.setCurrentItem(0);
                mNewsCollectionPicturesTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsCollectionPicturesTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.news_funny_pictures:
                mPicteresViewPager.setCurrentItem(1);
                mNewsFunnyPicturesTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsFunnyPicturesTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.news_pretty_pictures:
                mPicteresViewPager.setCurrentItem(2);
                mNewsPrettyPicturesTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsPrettyPicturesTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.news_story_pictures:
                mPicteresViewPager.setCurrentItem(3);
                mNewsStoryPicturesTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsStoryPicturesTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.image_news_title:
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
                mNewsCollectionPicturesTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsCollectionPicturesTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case 1:
                mNewsFunnyPicturesTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsFunnyPicturesTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case 2:
                mNewsPrettyPicturesTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsPrettyPicturesTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
            case 3:
                mNewsStoryPicturesTxt.setBackgroundResource(R.drawable.btn_common_pressed);
                mNewsStoryPicturesTxt.setTextColor(getResources().getColor(R.color.colorWhite));
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void clearBackgroundColor() {
        mNewsCollectionPicturesTxt.setBackgroundColor(Color.TRANSPARENT);
        mNewsFunnyPicturesTxt.setBackgroundColor(Color.TRANSPARENT);
        mNewsPrettyPicturesTxt.setBackgroundColor(Color.TRANSPARENT);
        mNewsStoryPicturesTxt.setBackgroundColor(Color.TRANSPARENT);
        mNewsCollectionPicturesTxt.setTextColor(getResources().getColor(R.color.colorHui));
        mNewsFunnyPicturesTxt.setTextColor(getResources().getColor(R.color.colorHui));
        mNewsPrettyPicturesTxt.setTextColor(getResources().getColor(R.color.colorHui));
        mNewsStoryPicturesTxt.setTextColor(getResources().getColor(R.color.colorHui));
    }

    class ImageNewsTitleAdapter extends FragmentStatePagerAdapter {
        String[] newsType = new String[]{};

        public ImageNewsTitleAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setData(String[] newsType) {
            this.newsType = newsType;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return ImageNewsFragment.newInstance(newsType[position]);
        }

        @Override
        public int getCount() {
            return newsType.length;
        }
    }
}
