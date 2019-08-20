package com.example.a13162.activitytest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a13162.activitytest.R;
import com.example.a13162.activitytest.entity.NfcUsageEntity;

import java.util.List;

public class MyListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<NfcUsageEntity> nfcUsageEntities;


    public MyListViewAdapter(Context mContext, List<NfcUsageEntity> nfcUsageEntities) {
        super();
        this.mContext = mContext;
        this.nfcUsageEntities = nfcUsageEntities;
    }

    @Override
    public int getCount() {
        return nfcUsageEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.usage_list_item, parent, false);
        }

        NfcUsageEntity nfcUsageEntity = nfcUsageEntities.get(position);

        int pictureResource = R.drawable.nfc;
        String title = "nfc";
        String content = "nfc content";
        String date = nfcUsageEntity.date.toString();

        switch (nfcUsageEntity.xcxPath) {
            //共享单车相关小程序
            case "pages/index/index":
            case "pages/openLockSuccess/openLockSuccess":
                title = "共享单车";
                pictureResource = R.drawable.bike;
                break;
            //医疗相关小程序
            case "pages/activeCheck/activeCheck?type=exsanguinate":
            case "pages/activeCheck/activeCheck?type=ct":
            case "pages/medicineList/medicineList":
            case "pages/activeCheck/activeCheck?type=xray":
            case "pages/detail/detail?departmentid=2":
            case "pages/jiuzheng/jiuzheng":
                title = "医疗";
                pictureResource = R.drawable.medical1;
                break;
            //旅游相关小程序
            case "pages/daoyouji/daoyouji":
            case "pages/daoyouji2/daoyouji2?guideMachineName=甘肃彩陶文化简介":
            case "pages/daoyouji2/daoyouji2?guideMachineName=安特生考察路线图":
                title = "旅游";
                pictureResource = R.drawable.travel;
                break;
        }

        switch (nfcUsageEntity.xcxPath) {
            //共享单车相关小程序
            case "pages/index/index":
                content = "准备解锁";
                pictureResource = R.drawable.bike;
                break;
            case "pages/openLockSuccess/openLockSuccess":
                pictureResource = R.drawable.bike;
                content = "成功解锁";
                break;
            //医疗相关小程序
            case "pages/activeCheck/activeCheck?type=exsanguinate":
                pictureResource = R.drawable.exsanguinate;
                content = "检查血常规";
                break;
            case "pages/activeCheck/activeCheck?type=ct":
                pictureResource = R.drawable.ct;
                content = "检查ct";
                break;
            case "pages/medicineList/medicineList":
                pictureResource = R.drawable.get_medicine;
                content = "查看药品";
                break;
            case "pages/activeCheck/activeCheck?type=xray":
                pictureResource = R.drawable.xray;
                content = "检查x光";
                break;
            case "pages/detail/detail?departmentid=2":
                pictureResource = R.drawable.guahao;
                content = "骨科挂号";
                break;
            case "pages/jiuzheng/jiuzheng":
                pictureResource = R.drawable.jiuzhen1;
                content = "就诊";
                break;
            //旅游相关小程序
            case "pages/daoyouji/daoyouji":
                content = "导游机-蒙娜丽莎";
                break;
            case "pages/daoyouji2/daoyouji2?guideMachineName=甘肃彩陶文化简介":
                content = "甘肃彩陶文化简介";
                break;
            case "pages/daoyouji2/daoyouji2?guideMachineName=安特生考察路线图":
                content = "安特生考察路线图";
                break;
        }

        ImageView icon = convertView.findViewById(R.id.nfc_usage_icon);
        icon.setImageResource(pictureResource);

        TextView titleView = convertView.findViewById(R.id.nfc_usage_title);
        titleView.setText(title);

        TextView contentView = convertView.findViewById(R.id.nfc_usage_content);
        contentView.setText(content + "\n使用时间:" + date);

//        TextView textView = convertView.findViewById(R.id.nfc_usage_text);
//        textView.setText(nfcUsageEntities.get(position).toString());
        return convertView;

    }
}
