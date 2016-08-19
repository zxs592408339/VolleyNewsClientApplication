package com.example.administrator.volleynewsclientapplication.videonews.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.volleynewsclientapplication.R;


public class VideoNewsPlayActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mVideoNewsNameTxt;
    private ImageView mVideoNewsDetailsVideoImg;
    private SurfaceView mVideoNewsSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private MediaPlayer mMediaPlayer;
    private int mVideoWidth;
    private int mVideoHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_news_play);
        mVideoNewsNameTxt = (TextView) findViewById(R.id.video_news_name_txt);
        mVideoNewsDetailsVideoImg = (ImageView) findViewById(R.id.video_news_details_video_img);

        mVideoNewsSurfaceView = (SurfaceView) findViewById(R.id.video_news_surface_view);
        mVideoNewsDetailsVideoImg.setOnClickListener(this);
        mSurfaceHolder = mVideoNewsSurfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                inintVideo();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (mMediaPlayer != null) {
                    mMediaPlayer.release();
                    mMediaPlayer = null;
                }
            }
        });
    }

    private void inintVideo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("VIDEO_NEWS");
        String Mp4_url = bundle.getString("VIDEO_NEWS_URL");
        String title = bundle.getString("VIDEO_NEWS_TITLE");
        mVideoNewsNameTxt.setText("视屏：" + title);


        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(Mp4_url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMediaPlayer.setDisplay(mSurfaceHolder);
        try {
            mMediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mVideoWidth = mMediaPlayer.getVideoWidth();
//                mVideoHeight = mMediaPlayer.getVideoHeight();
                mVideoHeight = 600;
                if (mVideoWidth != 0 && mVideoHeight != 0) {
            /* 设置视频的宽度和高度 */
                    mSurfaceHolder.setFixedSize(mVideoWidth, mVideoHeight);
            /* 开始播放 */
                    mMediaPlayer.start();
                }
            }
        });//预处理结束监听

        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {

            }
        }); //播放进度监听
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
            }
        });//播放完成监听

        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }


    private boolean checkSDCard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
