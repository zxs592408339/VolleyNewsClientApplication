package com.example.administrator.volleynewsclientapplication.imagenews.fragment;


import android.content.Context;
import android.content.Intent;
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
import com.example.administrator.volleynewsclientapplication.imagenews.activity.ImageNewsDetailsActivity;
import com.example.administrator.volleynewsclientapplication.imagenews.json.Data;
import com.example.administrator.volleynewsclientapplication.imagenews.json.ImageNews;
import com.example.administrator.volleynewsclientapplication.imagenews.json.NewsList;
import com.squareup.picasso.Picasso;
import com.warmtel.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

public class ImageNewsFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private static String IMAGE_NEWS_ID = "imageNewsId";
    private ConnectionUtils mConnectionUtils;
    private XListView mImageNewsListView;
    private ImageNewsAdapter adapter;
    private String newsID;

    public static Fragment newInstance(String newsID) {
        ImageNewsFragment imageNewsFragment = new ImageNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(IMAGE_NEWS_ID, newsID);
        imageNewsFragment.setArguments(bundle);
        return imageNewsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsID = getArguments() == null ? null : getArguments().getString(IMAGE_NEWS_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mConnectionUtils = new ConnectionUtils(getContext());
        mImageNewsListView = (XListView) getView().findViewById(R.id.image_news_list);
        mImageNewsListView.setXListViewListener(this);
        mImageNewsListView.setPullLoadEnable(true); //上拉加载更多开关
        mImageNewsListView.setPullRefreshEnable(true);
        mImageNewsListView.setOnItemClickListener(this);
        adapter = new ImageNewsAdapter(getContext());
        mImageNewsListView.setAdapter(adapter);
        doDownLoad();
    }

    public void doDownLoad() {
        String txtUrl = "http://api.sina.cn/sinago/list.json?channel=hdpic_" + newsID;
         GsonRequest<ImageNews> gsonRequest = new GsonRequest<>(txtUrl, ImageNews.class, new Response.Listener<ImageNews>() {
            @Override
            public void onResponse(ImageNews response) {
                Data data = response.getData();
                List<NewsList> newsList = data.getList();
                adapter.addData(newsList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "网络连接超时!", Toast.LENGTH_SHORT).show();
            }
        });
        RequestManager.addRequest(gsonRequest, this);
    }

    @Override
    public void onRefresh() {
        if(ConnectionUtils.isNetAvailable(getContext())){
            doDownLoad();
        }else {
            Toast.makeText(getContext(),"网络异常，请检查网络设置！",Toast.LENGTH_SHORT).show();
            mImageNewsListView.stopLoadMore();
            mImageNewsListView.stopRefresh();
        }
        mImageNewsListView.setPullLoadEnable(true);
    }

    @Override
    public void onLoadMore() {
        mImageNewsListView.setPullLoadEnable(false);
        Toast.makeText(getContext(), "没有更多图片新闻了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        NewsList newsList = (NewsList) adapterView.getAdapter().getItem(i);
        Intent intent = new Intent(getContext(), ImageNewsDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("IMAGE_NEWS_ID", newsList.getId());
        intent.putExtra("IMAGE_NEWS", bundle);
        startActivity(intent);
    }

    class ImageNewsAdapter extends BaseAdapter {
        LayoutInflater mLayoutInflater;
        List<NewsList> newsLists = new ArrayList<>();

        public ImageNewsAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        public void addData(List<NewsList> newsList) {
            this.newsLists = newsList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return newsLists.size();
        }

        @Override
        public Object getItem(int i) {
            return newsLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = mLayoutInflater.inflate(R.layout.layout_inage_news, null);
                ImageView imageNewsImg = (ImageView) view.findViewById(R.id.image_news_img);
                TextView imageNewsTxt = (TextView) view.findViewById(R.id.image_news_txt);
                NewsListBean newsListBean = new NewsListBean();
                newsListBean.imageNewsImg = imageNewsImg;
                newsListBean.imageNewsTxt = imageNewsTxt;
                view.setTag(newsListBean);
            }
            NewsList newsList = (NewsList) getItem(i);
            NewsListBean newsListBean = (NewsListBean) view.getTag();
            Picasso.with(getContext()).load(newsList.getKpic()).into(newsListBean.imageNewsImg);
            newsListBean.imageNewsTxt.setText(newsList.getTitle());
            return view;
        }
    }

    class NewsListBean {
        ImageView imageNewsImg;
        TextView imageNewsTxt;
    }
}
