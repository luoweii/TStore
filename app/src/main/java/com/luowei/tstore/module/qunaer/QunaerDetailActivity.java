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
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.entity.QunaerDetailMsg;
import com.luowei.tstore.module.BaseActivity;
import com.luowei.tstore.module.common.PhotoViewActivity;
import com.luowei.tstore.module.common.WebActivity;
import com.luowei.tstore.service.QunaerService;
import com.luowei.tstore.utils.CommonUtil;
import com.luowei.tstore.utils.JSONUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
    @ViewInject(R.id.tvAlias)
    private TextView tvAlias;
    @ViewInject(R.id.tvDesc)
    private TextView tvDesc;
    @ViewInject(R.id.tvAddress)
    private TextView tvAddress;
    @ViewInject(R.id.tvDetail)
    private TextView tvDetail;
    @ViewInject(R.id.tvPrice)
    private TextView tvPrice;

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
        QunaerService.getTicketDetail(id, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                try {
                    LogUtils.d("---------------------------post response---------------------------\n" + responseInfo.result);
                    JSONObject jo = new JSONObject(responseInfo.result);
                    JSONObject jo1 = jo.getJSONObject("retData").getJSONObject("ticketDetail").getJSONObject("data")
                            .getJSONObject("display").getJSONObject("ticket");
                    Object priceList = jo1.get("priceList");
                    if (priceList.toString().startsWith("{")) {
                        JSONArray ja = new JSONArray();
                        ja.put(priceList);
                        jo1.put("priceList", ja);
                    }
                    QunaerDetailMsg data = JSONUtil.fromJson(jo.toString(), QunaerDetailMsg.class);
                    final QunaerDetailMsg.Ticket ticket = data.retData.ticketDetail.data.display.ticket;
                    ImageLoader.getInstance().displayImage(ticket.imageUrl, ivBackdrop);
                    collapsingToolbar.setTitle(ticket.spotName);
                    if (TextUtils.isEmpty(ticket.alias)) {
                        tvAlias.setVisibility(View.GONE);
                    } else {
                        tvAlias.setText(Html.fromHtml("<font color='#aaaaaa'>别名:</font>"));
                        tvAlias.append("   " + ticket.alias);
                    }
                    tvAddress.setText(Html.fromHtml("<font color='#aaaaaa'>地点:</font>"));
                    tvAddress.append("   " + ticket.address);
                    if (TextUtils.isEmpty(ticket.description)) {
                        tvDesc.setVisibility(View.GONE);
                    } else {
                        tvDesc.setText("    " + ticket.description);
                    }
                    String sPrice = "";
                    for (QunaerDetailMsg.PriceList p : ticket.priceList) {
                        sPrice += p.ticketTitle+"        "+p.price+"￥\n";
                    }
                    tvPrice.setText(sPrice);
                    tvDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WebActivity.startActivity(v.getContext(), ticket.detailUrl);
                        }
                    });
                    ivBackdrop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ArrayList<String> list = new ArrayList<>();
                            list.add(ticket.imageUrl);
                            PhotoViewActivity.startActivity(QunaerDetailActivity.this, 0, list);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                CommonUtil.showToast(s);
            }
        });
    }

    @Override
    public String getActivityName() {
        return "景点详情";
    }

    public static void staticActivity(Context context, String id) {
        Intent it = new Intent(context, QunaerDetailActivity.class);
        it.putExtra("id", id);
        context.startActivity(it);
    }
}
