package com.luowei.tstore.module.ocr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.luowei.tstore.R;
import com.luowei.tstore.config.Constant;
import com.luowei.tstore.entity.Function;
import com.luowei.tstore.module.BaseActivity;
import com.luowei.tstore.service.OCRService;
import com.luowei.tstore.entity.OCRWordMsg;
import com.luowei.tstore.service.net.HttpCallBack;
import com.luowei.tstore.utils.CommonUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper;

/**
 * Created by luowei on 2015/8/13.
 */
public class OCRActivity extends BaseActivity {
    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;
    @ViewInject(R.id.image)
    private ImageView image;
    @ViewInject(R.id.tvInfo)
    private TextView tvInfo;
    @ViewInject(R.id.tvWord)
    private TextView tvWord;
    private Function function;
    private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(false)
            .cacheOnDisk(false)
            .considerExifParams(true)
            .displayer(new FadeInBitmapDisplayer(300))
            .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
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
        function = (Function) getIntent().getSerializableExtra(Constant.FUNCTION);

//        toolbar.setTitle(getActivityName());
//        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_36dp);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
    }

    @Override
    public String getActivityName() {
        return "百度文字识别";
    }

    public void onCameraClick(View v) {
        // 打开相机
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        startActivityForResult(intent, Constant.PICK_CAMERA);
    }

    public void onAlbumClick(View v) {
        Intent it = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(it, Constant.PICK_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constant.PICK_CAMERA) {
                Bitmap bm = data.getParcelableExtra("data");
                image.setImageBitmap(bm);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                tvInfo.setText("size:" + (baos.size() / 1000) + " bitmap:" + (bm.getByteCount() / 1000) + " width:" + bm.getWidth() + " height:" + bm.getHeight());

                String image = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                OCRService.ocr(function.getServer(),"android","LocateRecognize","1", URLEncoder.encode(image), new HttpCallBack<OCRWordMsg>() {
                    @Override
                    public void onSuccess(OCRWordMsg data) {
                        CommonUtil.showToast(data.errNum+" "+data.errMsg);
                    }

                    @Override
                    public void onFailure(int errCode, String msg) {
                        CommonUtil.showToast(errCode+": "+msg);
                    }
                });
            } else if (requestCode == Constant.PICK_GALLERY) {
                Uri uri = data.getData();
                ImageLoader.getInstance().displayImage(uri.toString(), image, options, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        loadedImage.compress(Bitmap.CompressFormat.JPEG, 90, baos);
                        tvInfo.setText("size:" + (baos.size() / 1000) + " bitmap:" + (loadedImage.getByteCount() / 1000) + " width:" + loadedImage.getWidth() + " height:" + loadedImage.getHeight());

                        String image = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                        OCRService.ocr(function.getServer(), "android", "LocateRecognize", "1", URLEncoder.encode(image), new HttpCallBack<OCRWordMsg>() {
                            @Override
                            public void onSuccess(OCRWordMsg data) {
                                CommonUtil.showToast(data.errNum + " " + data.errMsg);
                            }

                            @Override
                            public void onFailure(int errCode, String msg) {
                                CommonUtil.showToast(errCode + ": " + msg);
                            }
                        });
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {

                    }
                });
            }
        }
    }
}
