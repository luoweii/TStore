package com.luowei.tstore.module.qunaer;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.component.MetaballView;
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
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    private Function function;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;
    private List<QunaerListMsg.Ticket> data = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private int pageNo;
    private boolean isLoading;

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
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 0;
                getData();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getData();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    if (lastVisibleItem + 1 == adapter.getItemCount() && !isLoading) {
                        getData();
                    }
                }
            }
        });
    }

    private void getData() {
        isLoading = true;
        QunaerService.getTicketList(function.getServer(), ++pageNo, new HttpCallBack<QunaerListMsg>() {
            @Override
            public void onSuccess(QunaerListMsg data) {
                isLoading = false;
                swipeRefreshLayout.setRefreshing(false);
                if (pageNo == 1) {
                    QunaerLIstActivity.this.data.clear();
                }
                pageNo = data.retData.pageNo;
                QunaerLIstActivity.this.data.addAll(data.retData.ticketList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int errCode, String msg) {
                isLoading = false;
                swipeRefreshLayout.setRefreshing(false);
                CommonUtil.showToast(errCode + " " + msg);
            }
        });
    }

    @Override
    public String getActivityName() {
        return function.getName();
    }


    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int TYPE_ITEM = 0;
        private static final int TYPE_FOOTER = 1;

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            public TextView tvName;
            public TextView tvId;
            public TextView tvAlias;
            public LinearLayout llContent;

            public ItemViewHolder(View view) {
                super(view);
                tvName = (TextView) view.findViewById(R.id.tvName);
                tvId = (TextView) view.findViewById(R.id.tvId);
                tvAlias = (TextView) view.findViewById(R.id.tvAlias);
                llContent = (LinearLayout) view.findViewById(R.id.llContent);
            }
        }

        public class FooterViewHolder extends RecyclerView.ViewHolder {
            public MetaballView metaballView;

            public FooterViewHolder(View itemView) {
                super(itemView);
                metaballView = (MetaballView) itemView.findViewById(R.id.metaballView);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list_qunaer_list, parent, false);
                return new ItemViewHolder(view);
            } else {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_list_qunaer_footer, parent, false);
                return new FooterViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                ItemViewHolder holder1 = (ItemViewHolder) holder;
                final QunaerListMsg.Ticket ticket = data.get(position);
                holder1.tvName.setText(ticket.spotName);
                holder1.tvId.setText(ticket.productId);
                if (ticket.spotAliasName != null)
                    holder1.tvAlias.setText(ticket.spotAliasName.toString());
                else
                    holder1.tvAlias.setText("");
                holder1.llContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QunaerDetailActivity.staticActivity(v.getContext(), ticket.productId);
                    }
                });
            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder holder1 = (FooterViewHolder) holder;
            }
        }

        @Override
        public int getItemCount() {
            return data.size() == 0 ? 0 : data.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (position + 1 == getItemCount()) {
                return TYPE_FOOTER;
            } else {
                return TYPE_ITEM;
            }
        }
    }
}
