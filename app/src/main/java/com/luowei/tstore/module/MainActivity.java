package com.luowei.tstore.module;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.utils.Navigator;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    @ViewInject(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @ViewInject(R.id.navigation_view)
    NavigationView mNavigationView;
    @ViewInject(R.id.toolbar)
    private Toolbar mToolbar;
    @IdRes
    private int mCurrentMenuItem;
    private static Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupNavDrawer();
        initNavigator();
        mCurrentMenuItem = R.id.standard_app_bar_menu_item;
        setNewRootFragment(MainFragment.newInstance());
    }

    private void setupToolbar() {
        if (mToolbar == null) {
            LogUtils.d("Didn't find a toolbar");
            return;
        }
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) return;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setupNavDrawer() {
        if (mDrawerLayout == null) {
            LogUtils.d("mDrawerLayout is null - Can not setup the NavDrawer! Have you set the android.support.v7.widget.DrawerLayout?");
            return;
        }
        mNavigationView.setNavigationItemSelectedListener(this);
        LogUtils.d("setup setupNavDrawer");
    }

    private void initNavigator() {
        if (mNavigator != null) return;
        mNavigator = new Navigator(getSupportFragmentManager(), R.id.container);
    }

    private void setNewRootFragment(BaseFragment fragment) {
        if (fragment.hasCustomToolbar()) {
            hideActionBar();
        } else {
            showActionBar();
        }
        mNavigator.setRootFragment(fragment);
        mDrawerLayout.closeDrawers();
    }

    private void hideActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null) return;
        actionBar.hide();
    }

    private void showActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar == null) return;
        actionBar.show();
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public String getActivityName() {
        return "主导航页面";
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        @IdRes int id = menuItem.getItemId();
        if(id == mCurrentMenuItem) {
            mDrawerLayout.closeDrawers();
            return false;
        }
        switch (id){
            case R.id.standard_app_bar_menu_item:
//                setNewRootFragment(StandardAppBarFragment.newInstance());
                break;
            case R.id.tabs_menu_item:
//                setNewRootFragment(TabHolderFragment.newInstance());
                break;

            case R.id.parallax_menu_item:
//                setNewRootFragment(FlexibleSpaceWithImageFragment.newInstance());
                break;

            case R.id.pinned_app_bar_menu_item:
//                setNewRootFragment(FlexibleSpaceFragment.newInstance());
                break;

            case R.id.floating_action_button:
//                setNewRootFragment(FloatingActionButtonFragment.newInstance());
                break;
        }
        mCurrentMenuItem = id;
        menuItem.setChecked(true);
        return false;
    }

    @Override
    public void finish() {
        mNavigator = null;
        super.finish();
    }
}
