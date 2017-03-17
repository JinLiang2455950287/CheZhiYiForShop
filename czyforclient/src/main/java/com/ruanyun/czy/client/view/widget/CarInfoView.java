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
import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.model.CarInfo;
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
 * author: zhangsan on 16/9/23 上午9:20.
 */
public class CarInfoView extends AutoFrameLayout {
    ImageView imgCarPhoto;
    TextView tvCarName;
    TextView tvChooseCarband;
    ImageView imgChooseCarband;
    CarInfo carInfo;
    public CarInfoView(Context context) {
        super(context);
        initView(context);
    }

    public CarInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_carinfo_item, this);
        //getBackground().setAlpha(60);

        imgCarPhoto= (ImageView) findViewById(R.id.img_car_photo);
        tvCarName= (TextView) findViewById(R.id.tv_car_name);
        tvChooseCarband= (TextView) findViewById(R.id.tv_choose_carband);
        imgChooseCarband= (ImageView) findViewById(R.id.img_choose_carband);
        imgChooseCarband.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
               goCarModelChoose();
            }
        });
        tvChooseCarband.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                goCarModelChoose();
            }
        });
        imgCarPhoto.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                Intent intent = new Intent(getContext(), ImagesGridActivity.class);
                int requestCode = AddCarActivity.REQ_IMG_MODIFY;
                AndroidImagePicker.getInstance().setSelectMode(
                        AndroidImagePicker.Select_Mode.MODE_SINGLE);
                AndroidImagePicker.getInstance().setShouldShowCamera(false);

                (  (Activity) getContext()).startActivityForResult(intent, requestCode);
            }


        });
       findViewById(R.id.fr_root).getBackground().setAlpha(60);
    }
    public void setCarName(String carName){
         tvCarName.setText(carName);
     }

    public void setCarTypeName(String carTypeName){
        tvChooseCarband.setText(carTypeName);
    }

    private void goCarModelChoose(){
        Intent intent=new Intent(getContext(), BrandChoicesActivity.class);
        intent.putExtra(C.IntentKey.CHOOSE_CARMODEL_TYPE,AddCarActivity.REQ_IMG_MODIFY);
        getContext().startActivity(intent);
    }

    public void setCarPhoto(String path){
           Glide.with(getContext())
                .load(path)
                .error(R.drawable.mycar_default_bg)
                .into(imgCarPhoto);
    }

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }
}
