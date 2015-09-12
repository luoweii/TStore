package com.luowei.tstore.module.qunaer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.entity.QunaerDetailMsg;
import com.luowei.tstore.module.BaseActivity;
import com.luowei.tstore.service.QunaerService;
import com.luowei.tstore.service.net.HttpCallBack;
import com.luowei.tstore.utils.CommonUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * 景点详情
 * Created by luowei on 2015/8/19.
 */
public class QunaerDetailActivity extends BaseActivity {
    @ViewInject(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    private boolean isLoading;
    private String id;
    @ViewInject(R.id.ivBackdrop)
    private ImageView ivBackdrop;
    @ViewInject(R.id.collapsing_toolbar)
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        setContentView(R.layout.activity_qunaer_detail);
        id = getIntent().getStringExtra("id");
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
        collapsingToolbar.setTitle(getActivityName());
        getData();

//        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getData();
//            }
//        });
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//                getData();
//            }
//        });
    }

    private void getData() {
        isLoading = true;
        QunaerService.getTicketDetail(id, new HttpCallBack<QunaerDetailMsg>() {
            @Override
            public void onSuccess(QunaerDetailMsg data) {
                ImageLoader.getInstance().displayImage(data.retData.ticketDetail.data.display.ticket.imageUrl,ivBackdrop);
                collapsingToolbar.setTitle(data.retData.ticketDetail.data.display.ticket.spotName);
            }

            @Override
            public void onFailure(int errCode, String msg) {
                CommonUtil.showToast(errCode+" "+msg);
            }
        });
    }

    @Override
    public String getActivityName() {
        return "景点详情";
    }

    public static void staticActivity(Context context,String id) {
        Intent it = new Intent(context,QunaerDetailActivity.class);
        it.putExtra("id",id);
        context.startActivity(it);
    }
}
