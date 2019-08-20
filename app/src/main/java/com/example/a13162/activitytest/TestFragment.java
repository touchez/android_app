package com.example.a13162.activitytest;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dueeeke.videoplayer.player.IjkVideoView;
import com.example.a13162.activitytest.adapter.MyListViewAdapter;
import com.example.a13162.activitytest.entity.NfcUsageEntity;
import com.example.a13162.activitytest.viewmodel.NfcUsageViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    private TextView tv;

    private List<NfcUsageEntity> nfcUsageList;
    private NfcUsageViewModel mViewModel;
    private MyListViewAdapter listViewAdapter;

    public static TestFragment newInstance(String name) {

        Bundle args = new Bundle();
        args.putString("name", name);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ListView nfcUsageView = view.findViewById(R.id.nfc_usage_list);
        nfcUsageList = new ArrayList<>();
        listViewAdapter = new MyListViewAdapter(getContext(), nfcUsageList);
        nfcUsageView.setAdapter(listViewAdapter);

        mViewModel = new ViewModelProvider(this).get(NfcUsageViewModel.class);

        subscribeUiNfcUsage();
    }

    private void subscribeUiNfcUsage() {
        mViewModel.nfcUsages.observe(this, new Observer<List<NfcUsageEntity>>() {
            @Override
            public void onChanged(@Nullable List<NfcUsageEntity> nfcUsageEntities) {
                showNfcUsageInUi(nfcUsageEntities);
            }
        });
    }

    private void showNfcUsageInUi(final List<NfcUsageEntity> nfcUsageEntities) {
        nfcUsageList.clear();
        nfcUsageList.addAll(nfcUsageEntities);
        Collections.reverse(nfcUsageList);

        listViewAdapter.notifyDataSetChanged();
    }

}
