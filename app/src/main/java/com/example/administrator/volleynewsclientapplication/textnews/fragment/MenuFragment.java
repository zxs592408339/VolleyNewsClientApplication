package com.example.administrator.volleynewsclientapplication.textnews.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.volleynewsclientapplication.R;
import com.example.administrator.volleynewsclientapplication.imagenews.activity.ImageNewsMainActivity;
import com.example.administrator.volleynewsclientapplication.videonews.activity.VideoNewsMainActivity;


public class MenuFragment extends Fragment implements View.OnClickListener {

    private OnMenuClickListener mOnMenuClickListener;
    private TextView mMenuImageTxt, mMenuVideoTxt, mMenuWeatherTxt, mMenuMapTxt, mMenuMoerTxt;


    public interface OnMenuClickListener {
        void onMenuClick();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMenuClickListener) {
            this.mOnMenuClickListener = (OnMenuClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + "必需实现接口：OnMenuClickListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMenuImageTxt = (TextView) getView().findViewById(R.id.menu_tab_image);
        mMenuVideoTxt = (TextView) getView().findViewById(R.id.menu_tab_video);
        mMenuWeatherTxt = (TextView) getView().findViewById(R.id.menu_tab_weather);
        mMenuMapTxt = (TextView) getView().findViewById(R.id.menu_tab_map);
        mMenuMoerTxt = (TextView) getView().findViewById(R.id.menu_tab_more);
        mMenuImageTxt.setOnClickListener(this);
        mMenuVideoTxt.setOnClickListener(this);
        mMenuWeatherTxt.setOnClickListener(this);
        mMenuMapTxt.setOnClickListener(this);
        mMenuMoerTxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mOnMenuClickListener != null) {
            switch (view.getId()) {
                case R.id.menu_tab_image:
                    Intent imageNewsIntent = new Intent(getContext(), ImageNewsMainActivity.class);
                    startActivity(imageNewsIntent);
                    break;
                case R.id.menu_tab_video:
                    Intent videoNewsIntent = new Intent(getContext(), VideoNewsMainActivity.class);
                    startActivity(videoNewsIntent);
                    break;
                case R.id.menu_tab_weather:
                    Toast.makeText(getContext(),"该功能正在开发中...",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menu_tab_map:
                    Toast.makeText(getContext(),"该功能正在开发中...",Toast.LENGTH_SHORT).show();
                    break;
                case R.id.menu_tab_more:
                    Toast.makeText(getContext(),"更多精彩，敬请期待...",Toast.LENGTH_SHORT).show();
                    break;
            }
            mOnMenuClickListener.onMenuClick();
        }
    }

    public static Fragment newInstance() {
        return new MenuFragment();
    }
}
