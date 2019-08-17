package com.example.a13162.activitytest.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
            convertView = inflater.inflate(R.layout.recycle_view_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.nfc_usage_text);
        textView.setText(nfcUsageEntities.get(position).toString());
        return convertView;

    }
}
