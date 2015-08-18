package com.luowei.tstore.module.common;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.component.photoview.PhotoView;
import com.luowei.tstore.component.photoview.PhotoViewAttacher;
import com.luowei.tstore.config.AppConfig;
import com.luowei.tstore.module.BaseActivity;
import com.luowei.tstore.utils.CommonUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by luowei on 2015/8/18.
 */
public class PhotoViewActivity extends BaseActivity {
    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;
    @ViewInject(R.id.tvNum)
    private TextView tvNum;
    private List<String> imageUrls = new ArrayList<>();// 图片的地址
    public static final String IMAGE_URLS = "imageUrls";// 图片的地址
    public static final String POSITION = "position";// 默认显示图片位置
    private int position;
    private PhotoPreviewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_common_photo_view);
        imageUrls = getIntent().getStringArrayListExtra(IMAGE_URLS);
        position = getIntent().getIntExtra(POSITION, 0);
        tvNum.setText((position + 1) + "/" + imageUrls.size());
        adapter = new PhotoPreviewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvNum.setText((position + 1) + "/" + imageUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void startActivity(Activity context, int position, ArrayList<String> photos) {
        Intent it = new Intent(context, PhotoViewActivity.class);
        it.putStringArrayListExtra(IMAGE_URLS, photos);
        it.putExtra(POSITION, position);
        context.startActivity(it);
        context.overridePendingTransition(R.anim.activity_scale_enter_anim, android.R.anim.fade_out);
    }

    @Override
    public String getActivityName() {
        return "图片预览";
    }

    public void onSaveClick(View view) {
//            ImageLoader.getInstance().displayImage(imageUrls.get(position), ivImage);
//                    ivThumbImage.setVisibility(View.GONE);
//                    progress.setVisibility(View.GONE);
//                    ivImage.setOnLongClickListener(new View.OnLongClickListener() {
//                        @Override
//                        public boolean onLongClick(View v) {
//                            View vSave = inflater.inflate(R.layout.pop_save_image, null);
//                            final PopupWindow pw = new PopupWindow(vSave,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT,true);
//                            pw.setOutsideTouchable(true);
//                            pw.setBackgroundDrawable(new ColorDrawable(0x44000000));
//                            pw.setAnimationStyle(R.style.ButtonPopupAnimation);
//                            pw.showAtLocation(v, Gravity.CENTER, 0, 0);
//                            vSave.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    pw.dismiss();
//                                }
//                            });
//                            vSave.findViewById(R.id.tv_save).setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    pw.dismiss();
//                                }
//                            });
//                            return true;
//                        }
//                    });


        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmmss",
                    Locale.CHINA);
            Date curDate = new Date(System.currentTimeMillis());
            String localTempImgFileName = formatter.format(curDate) + ".jpg";
            File file = new File(AppConfig.DEFUALT_PATH_PHOTO, localTempImgFileName);
            FileOutputStream fos = new FileOutputStream(file);
//                arg2.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            fos.close();
            CommonUtil.showToast("图片已经保存到: " + file);
        } catch (Exception e) {
            e.printStackTrace();
            CommonUtil.showToast("保存图片出错");
        }
    }

    class PhotoPreviewPagerAdapter extends PagerAdapter {
        private LayoutInflater inflater;
        private DisplayImageOptions options;
        private View currentView;

        public PhotoPreviewPagerAdapter() {
            inflater = LayoutInflater.from(PhotoViewActivity.this);
            options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }

        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //销毁时取消加载图片
//            ImageLoader.getInstance().cancelDisplayTask((ImageView)((View)object).findViewById(R.id.pv_zoom));
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View vRoot = inflater.inflate(R.layout.viewpager_item_common_photo, null);
            final PhotoView ivImage = (PhotoView) vRoot.findViewById(R.id.pv_zoom);
            final ProgressBar progress = (ProgressBar) vRoot.findViewById(R.id.progress_bar);
            ImageLoader.getInstance().displayImage(imageUrls.get(position), ivImage);
            ivImage.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    finish();
                    overridePendingTransition(0, R.anim.activity_scale_exit_anim);
                }
            });
            container.addView(vRoot);
            return vRoot;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
            currentView = (View) object;
        }

        public View getCurrentView() {
            return currentView;
        }

    }
}
