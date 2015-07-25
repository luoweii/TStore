package com.luowei.tstore.module;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;

import java.lang.reflect.Method;

import de.greenrobot.event.EventBus;

/**
 * 
 * @author 骆巍
 * @date 2015-1-29
 */
public abstract class BaseActivity extends AppCompatActivity {
	public EventBus eventBus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		eventBus = EventBus.getDefault();
	}
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
        ViewUtils.inject(this);
	}
	
	public abstract String getActivityName();
	
	@Override
	protected void onStart() {
		super.onStart();
        eventBus.register(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		LogUtils.i("--进入界面-->> " + getActivityName());
	}

	@Override
	protected void onPause() {
		super.onPause();
		LogUtils.i("--离开界面-->> " + getActivityName());
	}

    @Override
    protected void onStop() {
        super.onStop();
        if (eventBus.isRegistered(this))
            eventBus.unregister(this);
    }

    @Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtils.i("--销毁界面-->> " + getActivityName());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (android.R.id.home == item.getItemId()) {
			finish();
		}
		return true;
	}
	
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

    public void onEvent(Object obj){
    }
}
