package com.example.administrator.volleynewsclientapplication.textnews.json;

import java.util.List;

public class TodayNews {
    private String title;
    private String digest;
    private String imgsrc;
    private String docid ;

    private List<Ads> ads;
    private List<Imgextra> imgextra;

    public String getDocid() {
        return docid;
    }

    public List<Imgextra> getImgextra() {
        return imgextra;
    }

    public List<Ads> getAds() {
        return ads;
    }

    public String getTitle() {
        return title;
    }

    public String getDigest() {
        return digest;
    }

    public String getImgsrc() {
        return imgsrc;
    }

}
