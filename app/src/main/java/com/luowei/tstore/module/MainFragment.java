package com.luowei.tstore.module;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.luowei.tstore.R;
import com.luowei.tstore.utils.ViewHelper;

/**
 * Created by luowei on 2015/7/25.
 */
public class MainFragment extends BaseFragment  {
    private static final int AMOUNT_OF_DATA = 50;

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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setList();
    }

    private void setList() {
        RecyclerView recyclerView = ViewHelper.findById(getActivity(), R.id.simpleList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        RVArrayAdapter arrayAdapter = new RVArrayAdapter(getData());
        recyclerView.setAdapter(arrayAdapter);
    }

    @NonNull
    private String[] getData() {
        String[] data = new String[AMOUNT_OF_DATA];
        for(int i=0;i<AMOUNT_OF_DATA;++i){
            data[i]=(getString(R.string.sample_data,(i+1)));
        }
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
