package com.ruanyun.czy.client.view.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.ui.my.AddCarActivity;
import com.ruanyun.czy.client.view.ui.my.BrandChoicesActivity;
import com.ruanyun.imagepicker.AndroidImagePicker;
import com.ruanyun.imagepicker.ui.ImagesGridActivity;
import com.zhy.autolayout.AutoFrameLayout;


/**
 * Description:
 * author: zhangsan on 16/9/23 上午9:14.
 */
public class AddCarView extends AutoFrameLayout {

    ImageView imgAddcarImg;
    TextView tvChooseCarband;

    public AddCarView(Context context) {
        super(context);
        initView(context);

    }
    public AddCarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_addcar, this);
        tvChooseCarband= (TextView) findViewById(R.id.tv_choose_carband);
        imgAddcarImg= (ImageView) findViewById(R.id.img_addcar_img);
        imgAddcarImg.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                Intent intent = new Intent(getContext(), ImagesGridActivity.class);
                int requestCode = AddCarActivity.REQ_IMG_ADD;
                AndroidImagePicker.getInstance().setSelectMode(
                        AndroidImagePicker.Select_Mode.MODE_SINGLE);
                AndroidImagePicker.getInstance().setShouldShowCamera(false);
                //showActivity(intent);
                (  (Activity) getContext()).startActivityForResult(intent, requestCode);
            }
        });
        tvChooseCarband.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                Intent intent=new Intent(getContext(), BrandChoicesActivity.class);
                intent.putExtra(C.IntentKey.CHOOSE_CARMODEL_TYPE,AddCarActivity.REQ_IMG_ADD);
                getContext().startActivity(intent);
            }
        });
        findViewById(R.id.fr_root).getBackground().setAlpha(60);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

}
