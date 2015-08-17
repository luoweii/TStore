package com.luowei.tstore.module.stock;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.config.Constant;
import com.luowei.tstore.entity.Function;
import com.luowei.tstore.module.BaseActivity;
import com.luowei.tstore.service.StockService;
import com.luowei.tstore.service.message.StockMsg;
import com.luowei.tstore.service.net.HttpCallBack;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by luowei on 2015/8/17.
 */
public class StockActivity extends BaseActivity {
    private Function function;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.tilStock)
    private TextInputLayout tilStock;
    @ViewInject(R.id.tvResult)
    private TextView tvResult;
    @ViewInject(R.id.iv1)
    private ImageView iv1;
    @ViewInject(R.id.iv2)
    private ImageView iv2;
    @ViewInject(R.id.iv3)
    private ImageView iv3;
    @ViewInject(R.id.iv4)
    private ImageView iv4;
    @ViewInject(R.id.rgStock)
    private RadioGroup rgStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_activity);
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
        tilStock.setHint("股票代码");
    }

    @Override
    public String getActivityName() {
        return function.getName();
    }

    public void onLookupClick(View view) {
        String stockId = tilStock.getEditText().getText().toString();
        String url = function.getServer();
        if (rgStock.getCheckedRadioButtonId() == R.id.rbHK) {
            url = "http://apis.baidu.com/apistore/stockservice/hkstock";
        } else if (rgStock.getCheckedRadioButtonId() == R.id.rbUS) {
            url = "http://apis.baidu.com/apistore/stockservice/usstock";
        } else {
            StockService.stockLookup(url,stockId, new HttpCallBack<StockMsg>(){
                @Override
                public void onSuccess(StockMsg data) {
                    tvResult.setText(data.toString());
                    StockMsg.Klinegraph klinegraph = data.retData.klinegraph;
                    if (klinegraph == null) return;
                    ImageLoader.getInstance().displayImage(klinegraph.minurl,iv1);
                    ImageLoader.getInstance().displayImage(klinegraph.dayurl,iv2);
                    ImageLoader.getInstance().displayImage(klinegraph.weekurl,iv3);
                    ImageLoader.getInstance().displayImage(klinegraph.monthurl,iv4);
                }

                @Override
                public void onFailure(int errCode, String msg) {
                    try {
                        JSONObject jo = new JSONObject(msg);
                        tvResult.setText(errCode + " " + jo.getString("errMsg"));
                        return;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    tvResult.setText(errCode + " " + msg);
                }
            });
            return;
        }
        StockService.stockLookup(url, stockId, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                tvResult.setText(responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                tvResult.setText(e.getExceptionCode()+" "+s);
            }
        });
    }
}
