package com.luowei.tstore.module.pullword;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.config.Constant;
import com.luowei.tstore.entity.Function;
import com.luowei.tstore.module.BaseActivity;
import com.luowei.tstore.service.PullwordService;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by luowei on 2015/8/19.
 */
public class PullwordActivity extends BaseActivity {
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
        setContentView(R.layout.activity_pullword);
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
        tilIdcard.setHint("输入一句话");
    }

    @Override
    public String getActivityName() {
        return  function.getName();
    }

    public void onLookupClick(View view) {
        String word = tilIdcard.getEditText().getText().toString();
        PullwordService.pullword(function.getServer(), word, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                tvResult.setText(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                tvResult.setText(e.getExceptionCode() + " " + s);
            }
        });
    }
}
