package com.example.administrator.volleynewsclientapplication.videonews.fragment;


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

import com.example.administrator.volleynewsclientapplication.ConnectionUtils;
import com.example.administrator.volleynewsclientapplication.R;
import com.example.administrator.volleynewsclientapplication.videonews.activity.VideoNewsPlayActivity;
import com.example.administrator.volleynewsclientapplication.videonews.json.VideoNews;
import com.squareup.picasso.Picasso;
import com.warmtel.xlistview.XListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoNewsFragment extends Fragment implements XListView.IXListViewListener, AdapterView.OnItemClickListener {
    private static String VIDEO_TYPE_ID = "video_type_id";
    private ConnectionUtils mConnectionUtils;
    private XListView mVideoNewsListView;
    private String video_type_id;
    private int pageNo = 1, pageSize = 10, mTotalPageCount = 60;
    private List<VideoNews> videoNewsList = new ArrayList<>();
    private VideoNewsAdapter adapter;
    private VideoNews videoNews;

    public static Fragment newInstance(String video_type_id) {
        VideoNewsFragment videoNewsFragment = new VideoNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(VIDEO_TYPE_ID, video_type_id);
        videoNewsFragment.setArguments(bundle);
        return videoNewsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        video_type_id = getArguments() == null ? null : getArguments().getString(VIDEO_TYPE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_news, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mConnectionUtils = new ConnectionUtils(getContext());
        mVideoNewsListView = (XListView) getView().findViewById(R.id.video_news_list_view);
        mVideoNewsListView.setXListViewListener(this);
        mVideoNewsListView.setPullLoadEnable(true); //上拉加载更多开关
        mVideoNewsListView.setPullRefreshEnable(true);
        mVideoNewsListView.setOnItemClickListener(this);
        adapter = new VideoNewsAdapter(getContext());
        mVideoNewsListView.setAdapter(adapter);
        doDownLoad(pageNo);
    }

    public void doDownLoad(int pageNo) {
        String txtUrl = "http://c.3g.163.com/nc/video/list/" + video_type_id + "/n/" + pageNo * pageSize + "-" + pageSize + ".html";
        mConnectionUtils.asycnConnect(txtUrl, ConnectionUtils.Method.GET, ConnectionUtils.Cache.TRUE, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                if (content == null) {
                    Toast.makeText(getContext(), "网络连接超时!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mVideoNewsListView.stopLoadMore();
                mVideoNewsListView.stopRefresh();
                mVideoNewsListView.setRefreshTime("刚刚");
                getJson(content);
            }
        });
    }


    public void getJson(String content) {
        try {
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonArray = jsonObject.getJSONArray(video_type_id);
            for (int i = 0, length = jsonArray.length(); i < length; i++) {
                videoNews = new VideoNews();
                JSONObject object = jsonArray.getJSONObject(i);
                videoNews.setTitle(object.getString("title"));
                videoNews.setCover(object.getString("cover"));
                videoNews.setMp4_url(object.getString("mp4_url"));
                videoNewsList.add(videoNews);
            }
            adapter.setNewList(videoNewsList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onRefresh() {
        if(ConnectionUtils.isNetAvailable(getContext())){
            pageNo = 1;
            videoNewsList.clear();
            doDownLoad(pageNo);
        }else {
            Toast.makeText(getContext(),"网络异常，请检查网络设置！",Toast.LENGTH_SHORT).show();
            mVideoNewsListView.stopLoadMore();
            mVideoNewsListView.stopRefresh();
        }
        mVideoNewsListView.setPullLoadEnable(true);
    }

    @Override
    public void onLoadMore() {
        if (++pageNo > mTotalPageCount) {
            pageNo = mTotalPageCount;
            mVideoNewsListView.setPullLoadEnable(false);
            Toast.makeText(getContext(), "已到底部", Toast.LENGTH_SHORT).show();
            return;
        }
        doDownLoad(pageNo);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        VideoNews videoNews = (VideoNews) adapterView.getAdapter().getItem(i);
        Intent intent = new Intent(getContext(), VideoNewsPlayActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("VIDEO_NEWS_URL", videoNews.getMp4_url());
        bundle.putString("VIDEO_NEWS_TITLE", videoNews.getTitle());
        intent.putExtra("VIDEO_NEWS", bundle);
        startActivity(intent);
    }


    public class VideoNewsAdapter extends BaseAdapter {
        List<VideoNews> videoNewsList = new ArrayList<>();
        LayoutInflater mLayoutInflater;

        public VideoNewsAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        public void setNewList(List<VideoNews> videoNewsList) {
            this.videoNewsList = videoNewsList;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return videoNewsList.size();
        }

        @Override
        public Object getItem(int position) {
            return videoNewsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mLayoutInflater.inflate(R.layout.layout_video_news, null);
                ImageView videoNewsImg = (ImageView) convertView.findViewById(R.id.video_news_img);
                TextView videoNewsTitleTxt = (TextView) convertView.findViewById(R.id.video_news_title_txt);
                VideoNewsBean videoNewsBean = new VideoNewsBean();
                videoNewsBean.videoNewsImg = videoNewsImg;
                videoNewsBean.videoNewsTitleTxt = videoNewsTitleTxt;
                convertView.setTag(videoNewsBean);
            }
            VideoNews videoNews = (VideoNews) getItem(position);
            VideoNewsBean videoNewsBean = (VideoNewsBean) convertView.getTag();
            Picasso.with(getContext()).load(videoNews.getCover()).into(videoNewsBean.videoNewsImg);
            videoNewsBean.videoNewsTitleTxt.setText(videoNews.getTitle());
            return convertView;
        }
    }

    class VideoNewsBean {
        ImageView videoNewsImg;
        TextView videoNewsTitleTxt;
    }
}
