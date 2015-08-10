package com.luowei.tstore.module;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.BaseAdapter;

import com.google.gson.reflect.TypeToken;
import com.luowei.tstore.R;
import com.luowei.tstore.entity.Function;
import com.luowei.tstore.utils.CommonUtil;
import com.luowei.tstore.utils.JSONUtil;
import com.luowei.tstore.utils.ViewHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luowei on 2015/7/25.
 */
public class MainFragment extends BaseFragment  {
    private List<Function> data = new ArrayList<>();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StandardAppBarFragment.
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    public MainFragment() {
    }

    private void initData() {
        try {
            InputStream is = getResources().openRawResource(R.raw.functions);
            byte [] buffer = new byte[is.available()];
            while (is.read(buffer) != -1);
            String json = new String(buffer);
            data = JSONUtil.getInstance().fromJson(json,new TypeToken<List<Function>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        setList();
    }

    private void setList() {
        RecyclerView recyclerView = ViewHelper.findById(getActivity(), R.id.simpleList);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        MainArrayAdapter arrayAdapter = new MainArrayAdapter(getData());
        recyclerView.setAdapter(arrayAdapter);
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
    }

    @NonNull
    private List<Function> getData() {
        return data;
    }

    @Override
    protected int getTitle() {
        return R.string.app_name;
    }

    @Override
    public boolean hasCustomToolbar() {
        return true;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_main;
    }
}
