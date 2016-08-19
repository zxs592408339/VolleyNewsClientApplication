package com.example.administrator.volleynewsclientapplication.imagenews.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.volleynewsclientapplication.ConnectionUtils;
import com.example.administrator.volleynewsclientapplication.R;
import com.example.administrator.volleynewsclientapplication.imagenews.detailsjson.DetailsData;
import com.example.administrator.volleynewsclientapplication.imagenews.detailsjson.DetailsPics;
import com.example.administrator.volleynewsclientapplication.imagenews.detailsjson.ImageNewsDetails;
import com.example.administrator.volleynewsclientapplication.imagenews.fragment.ImageNewsDetailsFragment;
import com.example.administrator.volleynewsclientapplication.pagetransformer.ZoomOutPageTransformer;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ImageNewsDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private ConnectionUtils mConnectionUtils;
    private String id, title;
    private ViewPager mTextNewsViewPager;
    private ImageView mImageNewsDetailsImageImg;
    private ImageDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_news_details);
        mConnectionUtils = new ConnectionUtils(this);
        mTextNewsViewPager = (ViewPager) findViewById(R.id.image_news_details_view_pager);
        mTextNewsViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mImageNewsDetailsImageImg = (ImageView) findViewById(R.id.image_news_details_image_img);
        mImageNewsDetailsImageImg.setOnClickListener(this);
        adapter = new ImageDetailsAdapter(getSupportFragmentManager());
        mTextNewsViewPager.setAdapter(adapter);
        doDownLoad();
    }

    public void doDownLoad() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("IMAGE_NEWS");
        id = bundle.getString("IMAGE_NEWS_ID");
        String txtUrl = "http://api.sina.cn/sinago/article.json?postt=hdpic_hdpic_toutiao_4&wm=b207&from=6042095012&chwm=12050_0001&oldchwm=12050_0001&imei=867064013906290&uid=802909da86d9f5fc&id=" + id;
        mConnectionUtils.asycnConnect(txtUrl, ConnectionUtils.Method.GET, ConnectionUtils.Cache.TRUE, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                if (content == null) {
                    Toast.makeText(ImageNewsDetailsActivity.this, "请求出错!", Toast.LENGTH_SHORT).show();
                    return;
                }
                getJson(content);
            }
        });
    }

    public void getJson(String content) {
        Gson gson = new Gson();
        DetailsData detailsData = gson.fromJson(content, ImageNewsDetails.class).getData();
        List<DetailsPics> detailsPicsList = detailsData.getPics();
        title = detailsData.getTitle();
        adapter.setDetailsPics(detailsPicsList);

    }

    @Override
    public void onClick(View view) {
        finish();
    }

    class ImageDetailsAdapter extends FragmentStatePagerAdapter {
        List<DetailsPics> detailsPicsList = new ArrayList<>();

        public void setDetailsPics(List<DetailsPics> detailsPicsList) {
            this.detailsPicsList = detailsPicsList;
            notifyDataSetChanged();
        }

        public ImageDetailsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            String alt = detailsPicsList.get(position).getAlt();
            String kpic = detailsPicsList.get(position).getKpic();
            return ImageNewsDetailsFragment.newInstance(title, kpic, alt, position, detailsPicsList.size());
        }

        @Override
        public int getCount() {
            return detailsPicsList.size();
        }
    }
}
