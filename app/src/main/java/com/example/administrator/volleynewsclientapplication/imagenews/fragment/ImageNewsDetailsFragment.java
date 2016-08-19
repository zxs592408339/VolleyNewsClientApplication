package com.example.administrator.volleynewsclientapplication.imagenews.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.volleynewsclientapplication.R;
import com.squareup.picasso.Picasso;

public class ImageNewsDetailsFragment extends Fragment {
    private static String PICS = "pics", TITLE = "title", ALT = "alt", NO = "no", TOTAL = "total";
    private String pics, title, alt;
    private int no, total;
    private ImageView mImageNewsDetailsImg;
    private TextView mImageNewsDetailsTitleTxt, mImageNewsDetailsNoTxt, mImageNewsDetailsTotalTxt, mImageNewsDetailsTxt;

    public static Fragment newInstance(String title, String pics, String alt, int no, int total) {
        ImageNewsDetailsFragment imageNewsDetailsFragment = new ImageNewsDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(PICS, pics);
        bundle.putString(ALT, alt);
        bundle.putInt(NO, no);
        bundle.putInt(TOTAL, total);
        imageNewsDetailsFragment.setArguments(bundle);
        return imageNewsDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pics = getArguments() == null ? null : getArguments().getString(PICS);
        title = getArguments() == null ? null : getArguments().getString(TITLE);
        alt = getArguments() == null ? null : getArguments().getString(ALT);
        no = getArguments() == null ? -1 : getArguments().getInt(NO) + 1;
        total = getArguments() == null ? -1 : getArguments().getInt(TOTAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_news_details, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mImageNewsDetailsImg = (ImageView) getView().findViewById(R.id.image_news_details_img);
        mImageNewsDetailsTitleTxt = (TextView) getView().findViewById(R.id.image_news_details_title_txt);
        mImageNewsDetailsNoTxt = (TextView) getView().findViewById(R.id.image_news_details_txt_on);
        mImageNewsDetailsTotalTxt = (TextView) getView().findViewById(R.id.image_news_details_txt_total);
        mImageNewsDetailsTxt = (TextView) getView().findViewById(R.id.image_news_details_txt);
        Picasso.with(getContext()).load(pics).into(mImageNewsDetailsImg);
        mImageNewsDetailsTitleTxt.setText(title);
        mImageNewsDetailsNoTxt.setText(no + "");
        mImageNewsDetailsTotalTxt.setText(total + "");
        mImageNewsDetailsTxt.setText(alt);
    }
}
