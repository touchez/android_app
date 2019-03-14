package com.example.a13162.activitytest;

import java.util.ArrayList;
import java.util.List;

public class DataUtil {
    public static List<VideoBean> getVideoList() {
        List<VideoBean> videoList = new ArrayList<>();
        videoList.add(new VideoBean("NFC开锁",
                "https://cms-bucket.nosdn.127.net/cb37178af1584c1588f4a01e5ecf323120180418133127.jpeg",
                "http://nfcvideo.oss-cn-shanghai.aliyuncs.com/IMG_8428.MP4"));
        return videoList;
    }
}
