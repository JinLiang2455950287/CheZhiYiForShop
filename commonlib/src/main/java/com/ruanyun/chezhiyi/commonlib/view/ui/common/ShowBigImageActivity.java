package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.hyphenate.easeui.widget.photoview.EasePhotoView;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.LogX;


import butterknife.ButterKnife;

/**
 * Description:
 * author: jery on 2016/6/24 11:00.
 */
public class ShowBigImageActivity extends AutoLayoutActivity {

    EasePhotoView imgContent;
    ImageButton  imgBtnBack;
    ProgressBar progressBar;
    private Uri imageUrl;
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_show_bigimage);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        imageUrl=getIntent().getData();
        LogX.d(TAG,"imageUrl:"+imageUrl);
        imgContent=getView(R.id.img_content);
        imgBtnBack=getView(R.id.imgbtn_back);
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar=getView(R.id.progressBar);
        Glide.with(mContext)
                .load(imageUrl)
                .error(R.drawable.ease_default_image)
                .into(new GlideDrawableImageViewTarget(imgContent){
                    @Override
                    public void onStart() {
                        progressBar.setVisibility(View.VISIBLE);
                        super.onStart();
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        progressBar.setVisibility(View.GONE);
                        super.onResourceReady(resource, animation);
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        progressBar.setVisibility(View.GONE);
                        super.onLoadFailed(e, errorDrawable);
                    }
                });

    }
}
