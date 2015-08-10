package com.luowei.tstore.module;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.luowei.tstore.R;
import com.luowei.tstore.entity.Function;
import com.luowei.tstore.utils.ResUtil;
import com.luowei.tstore.utils.ViewHelper;

import java.util.List;

public class MainArrayAdapter extends RecyclerView.Adapter<MainArrayAdapter.ViewHolder> {
    private List<Function> mData;
    public MainArrayAdapter(List<Function> data){
        mData = data;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.adapter_grid_main_tool,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Function f = mData.get(position);
        viewHolder.tvName.setText(f.getName());
        viewHolder.ivImage.setImageResource(ResUtil.getId(viewHolder.ivImage.getContext(),
                "drawable",f.getImgId()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName;
        public ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = ViewHelper.findById(itemView,R.id.tvName);
            ivImage = ViewHelper.findById(itemView,R.id.ivImage);
        }
    }
}
