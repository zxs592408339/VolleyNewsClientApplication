package com.example.administrator.volleynewsclientapplication.textnews.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.volleynewsclientapplication.ConnectionUtils;
import com.example.administrator.volleynewsclientapplication.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TextNewsDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ConnectionUtils mConnectionUtils;
    private String docid;
    private ImageView mNewsTextImg, mNewsInformationBackImg;
    private TextView mNewsTextTitleTxt, mNewsTextSourceAndPtimeTxt, mNewsTextImgNoTxt, mNewsTextInformationTxt;
    private ArrayList<String> srcs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_news);
        mConnectionUtils = new ConnectionUtils(this);

        mNewsInformationBackImg = (ImageView) findViewById(R.id.news_information_back);
        mNewsTextImg = (ImageView) findViewById(R.id.news_text_img);
        mNewsTextTitleTxt = (TextView) findViewById(R.id.news_text_title);
        mNewsTextSourceAndPtimeTxt = (TextView) findViewById(R.id.news_text_source_and_ptime);
        mNewsTextImgNoTxt = (TextView) findViewById(R.id.news_text_img_no);
        mNewsTextInformationTxt = (TextView) findViewById(R.id.news_text_information);
        mNewsInformationBackImg.setOnClickListener(this);
        mNewsTextImg.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("TEXT_NEWS");
        docid = bundle.getString("TEXT_NEWS_DOCID");

        getBundle();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.news_information_back:
                finish();
                break;
            case R.id.news_text_img:
                Intent intent = new Intent(this, TextNewsDetailsImgActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("SRCS", srcs);
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
                break;
        }

    }

    public void getBundle() {
        String txtUrl = "http://c.m.163.com/nc/article/" + docid + "/full.html";
        mConnectionUtils.asycnConnect(txtUrl, ConnectionUtils.Method.GET, ConnectionUtils.Cache.TRUE, new ConnectionUtils.HttpConnectionInterface() {
            @Override
            public void execute(String content) {
                if (content == null) {
                    Toast.makeText(TextNewsDetailsActivity.this, "网络连接超时!", Toast.LENGTH_SHORT).show();
                    return;
                }
                getJson(content);
            }
        });
    }

    public void getJson(String content) {
        JSONObject JSONObject;
        try {
            JSONObject = new JSONObject(content);
            JSONObject json = JSONObject.getJSONObject(docid);

            String title = json.getString("title");
            String source = json.getString("source");
            String ptime = json.getString("ptime");
            String body = json.getString("body");

            mNewsTextTitleTxt.setText(title);
            mNewsTextSourceAndPtimeTxt.setText("来源：" + source + " " + ptime);
            mNewsTextInformationTxt.setText(Html.fromHtml(body));

            JSONArray array = json.getJSONArray("img");
            for (int i = 0, length = array.length(); i < length; i++) {
                JSONObject object = array.getJSONObject(i);
                String src = object.getString("src");
                srcs.add(src);
            }

            if (srcs.size() != 0) {
                Picasso.with(this).load(srcs.get(0)).into(mNewsTextImg);
                mNewsTextImgNoTxt.setText("共" + array.length() + "张");
                mNewsTextImgNoTxt.setVisibility(View.VISIBLE);
                mNewsTextImg.setVisibility(View.VISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
