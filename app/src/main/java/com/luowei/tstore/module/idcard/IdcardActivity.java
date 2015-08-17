package com.luowei.tstore.module.idcard;

import android.os.Bundle;
import android.os.PersistableBundle;
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
import com.luowei.tstore.service.IdcardService;
import com.luowei.tstore.service.message.IdcardMsg;
import com.luowei.tstore.service.net.HttpCallBack;
import com.luowei.tstore.utils.CommonUtil;
import com.luowei.tstore.utils.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by luowei on 2015/8/17.
 */
public class IdcardActivity extends BaseActivity {
    private Function function;
    @ViewInject(R.id.tilIdcard)
    private TextInputLayout tilIdcard;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject((R.id.tvResult))
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);
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
        tilIdcard.setHint("身份证号码");
    }

    @Override
    public String getActivityName() {
        return function.getName();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onLookupClick(View view) {
        String idcard = tilIdcard.getEditText().getText().toString();
        IdcardService.idcardLookup(function.getServer(), idcard, new HttpCallBack<IdcardMsg>() {
            @Override
            public void onSuccess(IdcardMsg data) {
                tvResult.setText(data.toString());
            }

            @Override
            public void onFailure(int errCode, String msg) {
                try {
                    JSONObject jo = new JSONObject(msg);
                    if (jo.has("retMsg")) {
                        tvResult.setText(errCode + " " + jo.getString("retMsg"));
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvResult.setText(errCode + " " + msg);
            }
        });
    }
}
