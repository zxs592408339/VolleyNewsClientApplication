package com.example.administrator.volleynewsclientapplication.textnews.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.volleynewsclientapplication.R;
import com.example.administrator.volleynewsclientapplication.pagetransformer.ZoomOutPageTransformer;
import com.example.administrator.volleynewsclientapplication.textnews.fragment.TextNewsDetailsImgFragment;

import java.util.ArrayList;

public class TextNewsDetailsImgActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager mTextNewsDetailsImageViewPager;
    private ImageView mTextNewsDetailsImageImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_news_details_img);
        mTextNewsDetailsImageViewPager = (ViewPager) findViewById(R.id.text_news_details_image_view_pager);
        mTextNewsDetailsImageViewPager.setPageTransformer(true, new ZoomOutPageTransformer());

        mTextNewsDetailsImageImg = (ImageView) findViewById(R.id.text_news_details_image_img);
        mTextNewsDetailsImageImg.setOnClickListener(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        ArrayList<String> srcs = bundle.getStringArrayList("SRCS");
        TextNewsDetailsImgAdapter adapter = new TextNewsDetailsImgAdapter(getSupportFragmentManager());
        mTextNewsDetailsImageViewPager.setAdapter(adapter);
        adapter.setSrcs(srcs);
    }

    @Override
    public void onClick(View view) {
        finish();
    }


    class TextNewsDetailsImgAdapter extends FragmentStatePagerAdapter {
        ArrayList<String> srcs = new ArrayList<>();

        public TextNewsDetailsImgAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setSrcs(ArrayList<String> srcs) {
            this.srcs = srcs;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return TextNewsDetailsImgFragment.newInstance(srcs.get(position), position, srcs.size());
        }

        @Override
        public int getCount() {
            return srcs.size();
        }
    }
}
