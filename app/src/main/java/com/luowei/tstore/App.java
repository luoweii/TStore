package com.luowei.tstore;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luowei on 2015/7/25.
 */
public class App extends Application {

    public static Context context;
    //用户登录以后获取的通行证
    public static String TOKEN;
    //保存全局环境变量
    public static Map<Object, Object> maps;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            LogUtils.e("uncaughtException, thread:" + thread.toString(), ex);
//            final Intent intent = getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            getApplicationContext().startActivity(intent);
            System.exit(0);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.customTagPrefix = "TSTORE";
        Thread.setDefaultUncaughtExceptionHandler(uncaughtExceptionHandler);
        context = getApplicationContext();
        if (maps == null) maps = new HashMap<>();

        initImageLoader();
        //初始化内存分析工具
//        LeakCanary.install(this);
    }

    private void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .displayer(new FadeInBitmapDisplayer(300))
                .resetViewBeforeLoading(true)
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY)
                .build();

        ImageLoaderConfiguration imageconfig = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .threadPoolSize(3)
                .memoryCacheExtraOptions(720, 1280)  // 缓存到内存的图片大小范围
                .diskCacheSize(50 * 1024 * 1024)  // 50Mb
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .defaultDisplayImageOptions(options)
                .denyCacheImageMultipleSizesInMemory().build();
        ImageLoader.getInstance().init(imageconfig);
    }
}
