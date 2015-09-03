package com.luowei.tstore.module.iplookup;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.config.Constant;
import com.luowei.tstore.entity.Function;
import com.luowei.tstore.module.BaseActivity;
import com.luowei.tstore.service.IplookupService;
import com.luowei.tstore.entity.IplookupMsg;
import com.luowei.tstore.service.net.HttpCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by luowei on 2015/8/17.
 */
public class IplookupActivity extends BaseActivity {
    private Function function;
    @ViewInject(R.id.tilIp)
    private TextInputLayout tilIp;
    @ViewInject(R.id.tvResult)
    private TextView tvResult;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iplookup);
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
        tilIp.setHint("IP地址");
    }

    @Override
    public String getActivityName() {
        return function.getName();
    }

    public void onLookupClick(View view) {
        String ip = tilIp.getEditText().getText().toString();
        IplookupService.iplookup(function.getServer(), ip, new HttpCallBack<IplookupMsg>() {
            @Override
            public void onSuccess(IplookupMsg data) {
                tvResult.setText(data.toString());
            }

            @Override
            public void onFailure(int errCode, String msg) {
                try {
                    JSONObject jo = new JSONObject(msg);
                        tvResult.setText(errCode + " " + jo.getString("retData"));
                        return;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText(errCode + " " + msg);
            }
        });
    }
}
