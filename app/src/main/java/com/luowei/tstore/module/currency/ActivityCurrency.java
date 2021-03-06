package com.luowei.tstore.module.currency;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.config.Constant;
import com.luowei.tstore.entity.CurrencyMsg;
import com.luowei.tstore.entity.CurrencyTypeMsg;
import com.luowei.tstore.entity.Function;
import com.luowei.tstore.module.BaseActivity;
import com.luowei.tstore.service.CurrencyService;
import com.luowei.tstore.service.net.HttpCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by luowei on 2015/8/23.
 */
public class ActivityCurrency extends BaseActivity {
    private Function function;
    @ViewInject(R.id.tilText)
    private TextInputLayout tilText;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject((R.id.tvResult))
    private TextView tvResult;
    @ViewInject(R.id.sFrom)
    private Spinner sFrom;
    @ViewInject(R.id.sTo)
    private Spinner sTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
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
        tilText.setHint("请输入金额");

        init();
    }

    private void init() {
        CurrencyService.type(function.getServer(), new HttpCallBack<CurrencyTypeMsg>() {
            @Override
            public void onSuccess(CurrencyTypeMsg data) {
                sFrom.setAdapter(new ArrayAdapter<>(ActivityCurrency.this, R.layout.support_simple_spinner_dropdown_item, data.retData));
                sTo.setAdapter(new ArrayAdapter<>(ActivityCurrency.this, R.layout.support_simple_spinner_dropdown_item, data.retData));
                for (int i = 0; i < data.retData.size(); i++) {
                    String str = data.retData.get(i);
                    if (str.equals("USD")) {
                        sFrom.setSelection(i);
                    } else if (str.equals("CNY")) {
                        sTo.setSelection(i);
                    }
                }
            }

            @Override
            public void onFailure(int errCode, String msg) {
                try {
                    JSONObject jo = new JSONObject(msg);
                    tvResult.setText(errCode + " " + jo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public String getActivityName() {
        return function.getName();
    }

    public void onLookupClick(View view) {
        String text = tilText.getEditText().getText().toString();
        String from = (String) sFrom.getSelectedItem();
        String to = (String) sTo.getSelectedItem();

        CurrencyService.currency("http://apis.baidu.com/apistore/currencyservice/currency",
                from, to, text, new HttpCallBack<CurrencyMsg>() {
                    @Override
                    public void onSuccess(CurrencyMsg data) {
                        tvResult.setText(data.toString());
                    }

                    @Override
                    public void onFailure(int errCode, String msg) {
                        tvResult.setText(errCode + " " + msg);
                    }
                });
    }
}
