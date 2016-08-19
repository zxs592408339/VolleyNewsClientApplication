package com.example.administrator.volleynewsclientapplication.textnews.fragment;

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

public class TextNewsDetailsImgFragment extends Fragment {
    private static String SRC = "src", NO = "position", TOTAL = "size";
    private String src;
    private int no, total;
    private TextView mTextNewsDetailsPicsNoTxt, mTextNewsDetailsPicTotalTxt;
    private ImageView mTextNewsDetailsPicImg;

    public static Fragment newInstance(String src, int position, int size) {
        TextNewsDetailsImgFragment textNewsDetailsImgFragment = new TextNewsDetailsImgFragment();
        Bundle bundle = new Bundle();
        bundle.putString(SRC, src);
        bundle.putInt(NO, position);
        bundle.putInt(TOTAL, size);
        textNewsDetailsImgFragment.setArguments(bundle);
        return textNewsDetailsImgFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        src = getArguments() == null ? null : getArguments().getString(SRC);
        no = getArguments() == null ? null : getArguments().getInt(NO);
        total = getArguments() == null ? null : getArguments().getInt(TOTAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_text_news_details_img, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTextNewsDetailsPicsNoTxt = (TextView) getView().findViewById(R.id.text_news_details_pics_no_txt);
        mTextNewsDetailsPicTotalTxt = (TextView) getView().findViewById(R.id.text_news_details_pics_total_txt);
        mTextNewsDetailsPicImg = (ImageView) getView().findViewById(R.id.text_news_details_pics_img);
        mTextNewsDetailsPicsNoTxt.setText((no + 1) + "");
        mTextNewsDetailsPicTotalTxt.setText(total + "");
        Picasso.with(getContext()).load(src).into(mTextNewsDetailsPicImg);

    }
}
