package com.luowei.tstore.module.dictionary;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.config.Constant;
import com.luowei.tstore.entity.Function;
import com.luowei.tstore.module.BaseActivity;
import com.luowei.tstore.service.DictionaryService;
import com.luowei.tstore.entity.DictionaryMsg;
import com.luowei.tstore.entity.TranslateMsg;
import com.luowei.tstore.service.net.HttpCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by luowei on 2015/8/17.
 */
public class DictionaryActivity extends BaseActivity {

    private Function function;
    @ViewInject(R.id.tilIdcard)
    private TextInputLayout tilIdcard;
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject((R.id.tvResult))
    private TextView tvResult;
    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
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
    }

    @Override
    public String getActivityName() {
        return function.getName();
    }

    public void onLookupClick(View view) {
        final String word = tilIdcard.getEditText().getText().toString();
        if (radioGroup.getCheckedRadioButtonId() == R.id.rbTranslate) {
            DictionaryService.dictionary("http://apis.baidu.com/apistore/tranlateservice/translate", word, new HttpCallBack<TranslateMsg>() {
                @Override
                public void onSuccess(TranslateMsg data) {
                    tvResult.setText(word+"\n\n");
                    for (TranslateMsg.TransResult tr : data.retData.trans_result) {
                        tvResult.append(tr.dst+"\n");
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
        } else {
            DictionaryService.dictionary(function.getServer(), word, new HttpCallBack<DictionaryMsg>() {
                @Override
                public void onSuccess(DictionaryMsg data) {
                    DictionaryMsg.DictResult dictResult = data.retData.dict_result;
                    tvResult.setText(Html.fromHtml("<h2>" + dictResult.word_name + "</h2>"));
                    for (DictionaryMsg.Symbol symbol : dictResult.symbols) {
                        if (symbol.ph_zh == null) {
                            tvResult.append("英: " + symbol.ph_en + "    美: " + symbol.ph_am + "\n");
                        } else {
                            tvResult.append(symbol.ph_zh + "\n");
                        }
                        for (DictionaryMsg.Part part : symbol.parts) {
                            tvResult.append(part.part + "  " + part.means + "\n");
                        }
                        tvResult.append("\n");
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
    }
}
