package com.luowei.tstore.module.qunaer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.config.Constant;
import com.luowei.tstore.entity.Function;
import com.luowei.tstore.entity.QunaerListMsg;
import com.luowei.tstore.module.BaseActivity;
import com.luowei.tstore.service.QunaerService;
import com.luowei.tstore.service.net.HttpCallBack;
import com.luowei.tstore.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by luowei on 2015/8/19.
 */
public class QunaerLIstActivity extends BaseActivity {
    private Function function;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;
    private List<QunaerListMsg.Ticket> data = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qunaer_list);
        function = (Function) getIntent().getSerializableExtra(Constant.FUNCTION);
        SwipeBackActivityHelper sbah = new SwipeBackActivityHelper(this);
        sbah.onActivityCreate();
        sbah.onPostCreate();
        sbah.getSwipeBackLayout().setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            setTitle(getActivityName());
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(adapter);

        getData();
    }

    private void getData() {
        QunaerService.getTicketList(function.getServer(), 1, new HttpCallBack<QunaerListMsg>() {
            @Override
            public void onSuccess(QunaerListMsg data) {
                QunaerLIstActivity.this.data = data.retData.ticketList;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int errCode, String msg) {
                CommonUtil.showToast(errCode + " " + msg);
            }
        });
    }

    @Override
    public String getActivityName() {
        return function.getName();
    }


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvName;
            public TextView tvId;
            public TextView tvAlias;

            public ViewHolder(View view) {
                super(view);
                tvName = (TextView) view.findViewById(R.id.tvName);
                tvId = (TextView) view.findViewById(R.id.tvId);
                tvAlias = (TextView) view.findViewById(R.id.tvAlias);
            }

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_qunaer_list, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            QunaerListMsg.Ticket ticket = data.get(position);
            holder.tvName.setText(ticket.spotName);
            holder.tvId.setText(ticket.productId);
            if (ticket.spotAliasName != null)
                holder.tvAlias.setText(ticket.spotAliasName.toString());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
