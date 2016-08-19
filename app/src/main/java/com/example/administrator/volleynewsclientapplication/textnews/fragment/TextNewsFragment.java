package com.example.administrator.volleynewsclientapplication.textnews.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.myrequest.GsonRequest;
import com.android.volley.myvolleytool.RequestManager;
import com.example.administrator.volleynewsclientapplication.ConnectionUtils;
import com.example.administrator.volleynewsclientapplication.R;
import com.example.administrator.volleynewsclientapplication.textnews.activity.TextNewsDetailsActivity;
import com.example.administrator.volleynewsclientapplication.textnews.json.Ads;
import com.example.administrator.volleynewsclientapplication.textnews.json.Imgextra;
import com.example.administrator.volleynewsclientapplication.textnews.json.TextNewsList;
import com.example.administrator.volleynewsclientapplication.textnews.json.TodayNews;
import com.scxh.slider.library.SliderLayout;
import com.scxh.slider.library.SliderTypes.BaseSliderView;
import com.scxh.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.warmtel.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;


public class TextNewsFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private static final int NewsHeadline = 0;
    private static final int NewsRecreation = 1;
    private static final int NewsPhysical = 2;
    private static final int NewsEconomics = 3;
    private static final int NewsScience = 4;
    private static String NEWS_ID = "newsId";
    private static String NEWS_TYPE_POSITION = "newsTypePosition";
    private ConnectionUtils mConnectionUtils;
    private String news_type;
    private String news_type_id;
    private int position;
    private int pageNo = 1, pageSize = 20, mTotalPageCount = 20;
    private XListView mTodayNewList;
    private TextNewsAdapter adapter;
    private SliderLayout mSliderLayout;
    private List<Ads> adsList;
    private View headerView;
    private boolean flag = true;
    private List<TodayNews> newsLists;


    public static Fragment newInstance(String newsID, int position) {
        TextNewsFragment toDayNewsFragment = new TextNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(NEWS_ID, newsID);
        bundle.putInt(NEWS_TYPE_POSITION, position);
        toDayNewsFragment.setArguments(bundle);
        return toDayNewsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments() == null ? -1 : getArguments().getInt(NEWS_TYPE_POSITION);
        news_type_id = getArguments().getString(NEWS_ID);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_today_news, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mConnectionUtils = new ConnectionUtils(getContext());
        mTodayNewList = (XListView) getView().findViewById(R.id.today_new_list);
        mTodayNewList.setXListViewListener(this);
        mTodayNewList.setPullLoadEnable(true); //上拉加载更多开关
        mTodayNewList.setPullRefreshEnable(true);
        mTodayNewList.setOnItemClickListener(this);
        adapter = new TextNewsAdapter(getContext());
        mTodayNewList.setAdapter(adapter);
        doDownLoad(pageNo);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        TodayNews todayNews = (TodayNews) adapterView.getAdapter().getItem(i);
        Intent intent = new Intent(getContext(), TextNewsDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("TEXT_NEWS_DOCID", todayNews.getDocid());
        intent.putExtra("TEXT_NEWS", bundle);
        startActivity(intent);
    }

    public void getHeaderView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        headerView = inflater.inflate(R.layout.slider_layout_view, null);
        mSliderLayout = (SliderLayout) headerView.findViewById(R.id.slider_layout);
        mTodayNewList.addHeaderView(headerView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mSliderLayout.setOnContextClickListener(new View.OnContextClickListener() {
                @Override
                public boolean onContextClick(View view) {

                    return false;
                }
            });
        }
    }

    public void setHeaderView() {
        if (mSliderLayout != null) {
            mSliderLayout.removeAllSliders();
        }
        for (Ads ads : adsList) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(ads.getTitle())
                    .image(ads.getImgsrc())
                    .setScaleType(BaseSliderView.ScaleType.Fit);
            mSliderLayout.addSlider(textSliderView);
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom); //指示器位置

    }


    public void doDownLoad(int pageNo) {
        if (position == 0) {
            news_type = "headline/";
        } else {
            news_type = "list/";
        }
        String txtUrl = "http://c.m.163.com/nc/article/" + news_type + news_type_id + "/" + (pageNo - 1) * pageSize + "-" + pageSize + ".html";


        GsonRequest<TextNewsList> gsonRequest = new GsonRequest<>(txtUrl, TextNewsList.class, new Response.Listener<TextNewsList>() {
            @Override
            public void onResponse(TextNewsList response) {
                switch (position) {
                    case 0:
                        newsLists = response.getT1348647909107();
                        break;
                    case 1:
                        newsLists = response.getT1348648517839();
                        break;
                    case 2:
                        newsLists = response.getT1348649079062();
                        break;
                    case 3:
                        newsLists = response.getT1348648756099();
                        break;
                    case 4:
                        newsLists = response.getT1348649580692();
                        break;

                }


                getJson();
                adapter.setNewList(newsLists);
                mTodayNewList.stopLoadMore();
                mTodayNewList.stopRefresh();
                mTodayNewList.setRefreshTime("刚刚");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestManager.addRequest(gsonRequest, this);
    }


    public void getJson() {

        TodayNews todayNews = newsLists.get(0);
        adsList = todayNews.getAds();
        if (flag) {
            getHeaderView();
            flag = false;
        }
        if (pageNo == 1) {
            setHeaderView();
        }
    }

    @Override
    public void onRefresh() {
        if (ConnectionUtils.isNetAvailable(getContext())) {
            pageNo = 1;
            doDownLoad(pageNo);
        } else {
            Toast.makeText(getContext(),"网络异常，请检查网络设置！",Toast.LENGTH_SHORT).show();
            mTodayNewList.stopLoadMore();
            mTodayNewList.stopRefresh();
        }
        mTodayNewList.setPullLoadEnable(true);
    }

    @Override
    public void onLoadMore() {
        if (++pageNo > mTotalPageCount) {
            pageNo = mTotalPageCount;
            mTodayNewList.setPullLoadEnable(false);
            Toast.makeText(getContext(), "已到底部", Toast.LENGTH_SHORT).show();
            return;
        }
        doDownLoad(pageNo);
    }


    public class TextNewsAdapter extends BaseAdapter {
        private static final int ITEM_TYPE_ONE = 0;
        private static final int ITEM_TYPE_TWO = 1;//三张图片
        List<TodayNews> newList = new ArrayList<>();
        LayoutInflater mLayoutInflater;

        public TextNewsAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        public void setNewList(List<TodayNews> newList) {
            if (pageNo == 1) {
                this.newList = newList;
            } else {
                this.newList.addAll(newList);
            }
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return newList.size();
        }

        @Override
        public Object getItem(int position) {
            return newList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            TodayNews todayNews = newList.get(position);
            List<Imgextra> imgextraList = todayNews.getImgextra();

            if (imgextraList == null) {
                return ITEM_TYPE_ONE;
            } else {
                return ITEM_TYPE_TWO;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);
            if (type == ITEM_TYPE_ONE) {
                return getOneView(position, convertView, parent);
            } else {
                return getTwoView(position, convertView, parent);
            }
        }

        public View getOneView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.show_news_one_layout, null);
                ImageView newImg = (ImageView) convertView.findViewById(R.id.new_img);
                TextView newTitle = (TextView) convertView.findViewById(R.id.new_title_txt);
                TextView newDigest = (TextView) convertView.findViewById(R.id.new_digset_txt);
                TodayNewBeanOne todayNewBean = new TodayNewBeanOne();
                todayNewBean.newImg = newImg;
                todayNewBean.newTitle = newTitle;
                todayNewBean.newDigest = newDigest;
                convertView.setTag(todayNewBean);
            }
            TodayNews todayNew = (TodayNews) getItem(position);
            TodayNewBeanOne todayNewBeanOne = (TodayNewBeanOne) convertView.getTag();
            String httpUrl = todayNew.getImgsrc();
            Picasso.with(getContext()).load(httpUrl).into(todayNewBeanOne.newImg);
            todayNewBeanOne.newTitle.setText(todayNew.getTitle());
            todayNewBeanOne.newDigest.setText(todayNew.getDigest());
            return convertView;
        }

        public View getTwoView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.show_news_two_layout, null);
                TextView newsTitle = (TextView) convertView.findViewById(R.id.news_title_two_txt);
                ImageView imageOneView = (ImageView) convertView.findViewById(R.id.news_img_one);
                ImageView imageTwoView = (ImageView) convertView.findViewById(R.id.news_img_two);
                ImageView imageThreeView = (ImageView) convertView.findViewById(R.id.news_img_three);
                TodayNewBeanTwo todayNewBeanTwo = new TodayNewBeanTwo();
                todayNewBeanTwo.newsTitle = newsTitle;
                todayNewBeanTwo.imageOneView = imageOneView;
                todayNewBeanTwo.imageTwoView = imageTwoView;
                todayNewBeanTwo.imageThreeView = imageThreeView;
                convertView.setTag(todayNewBeanTwo);
            }
            TodayNews todayNews = (TodayNews) getItem(position);
            List<Imgextra> imgextra = todayNews.getImgextra();
            TodayNewBeanTwo todayNewBeanTwo = (TodayNewBeanTwo) convertView.getTag();
            todayNewBeanTwo.newsTitle.setText(todayNews.getTitle());
            Picasso.with(getContext()).load(todayNews.getImgsrc()).into(todayNewBeanTwo.imageOneView);
            Picasso.with(getContext()).load(imgextra.get(0).getImgsrc()).into(todayNewBeanTwo.imageTwoView);
            Picasso.with(getContext()).load(imgextra.get(1).getImgsrc()).into(todayNewBeanTwo.imageThreeView);
            return convertView;
        }

        class TodayNewBeanTwo {
            TextView newsTitle;
            ImageView imageOneView, imageTwoView, imageThreeView;
        }

        class TodayNewBeanOne {
            ImageView newImg;
            TextView newTitle, newDigest;
        }
    }
}
