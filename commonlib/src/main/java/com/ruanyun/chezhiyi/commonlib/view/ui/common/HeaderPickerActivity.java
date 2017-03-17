package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;

import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYSelectPopWindow;
import com.ruanyun.chezhiyi.commonlib.view.widget.SelectPopWindow;
import com.ruanyun.imagepicker.AndroidImagePicker;
import com.ruanyun.imagepicker.runtimepermissions.PermissionsManager;
import com.ruanyun.imagepicker.runtimepermissions.PermissionsResultAction;
import com.ruanyun.imagepicker.ui.ImageCropActivity;
import com.ruanyun.imagepicker.ui.ImagesGridActivity;

import java.io.File;
import java.io.IOException;

/**
 * Created by Sxq on 2016/9/9.
 */
public abstract class HeaderPickerActivity extends AutoLayoutActivity implements RYSelectPopWindow.OnSelectListener, AndroidImagePicker.OnImageCropCompleteListener, AndroidImagePicker.OnPictureTakeCompleteListener {
    protected RYSelectPopWindow mRYSelectPopWindow;
    protected SelectPopWindow selectPopWindow;
    protected AndroidImagePicker androidImagePicker;
    public String[] permissions = { Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA};

    /**
     * 初始化 弹出选择 PopuWindow  选取照片或相机
     */
    protected void initSelectPopView() {
        mRYSelectPopWindow = new RYSelectPopWindow(mContext, this);
        initImagePickerListener();
        androidImagePicker = AndroidImagePicker.getInstance();
    }

    protected void initSexPop() {
        selectPopWindow = new SelectPopWindow(mContext) {
            @Override
            protected void getSex(String s, int i) {
                getSexed(s, i);
                selectPopWindow.dismiss();
            }
        };
    }

    protected void getSexed(String s, int state){

    };

    /**
     * imagePicker的监听设置
     */
    private void initImagePickerListener() {
        AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_SINGLE);
        AndroidImagePicker.getInstance().setShouldShowCamera(false);
        AndroidImagePicker.getInstance().addOnImageCropCompleteListener(this);
        AndroidImagePicker.getInstance().addOnPictureTakeCompleteListener(this);
    }

    @Override
    public void onSelectClick() {
        Intent intent = new Intent();
        intent.setClass(mContext, ImagesGridActivity.class);
        intent.putExtra("isCrop", true);
        startActivity(intent);
        mRYSelectPopWindow.dismiss();

    }

    @Override
    public void takePicClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, permissions, new PermissionsResultAction() {

                @Override
                public void onGranted() {
                    try {
                        androidImagePicker.takePicture(HeaderPickerActivity.this, AndroidImagePicker.REQ_CAMERA);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDenied(String permission) {
                    AppUtility.showToastMsg("请开启应用的相机权限！");

                }
            });
        } else {
            try {
                androidImagePicker.takePicture(HeaderPickerActivity.this, AndroidImagePicker.REQ_CAMERA);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mRYSelectPopWindow.dismiss();
    }

    @Override
    public void onImageCropComplete(Bitmap bmp, float ratio) {
        File file = FileUtil.saveBitmapFile(bmp, "head.jpg");
        //上传头像
        saveUserHeaderImage(file);
    }

    protected abstract void saveUserHeaderImage(File file);

    @Override
    public void onPictureTakeComplete(String picturePath, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext, ImageCropActivity.class);
        // TODO: 2017/1/3 imagePicker 的左下角的重选按钮的文字
        intent.putExtra("bottom_left", "取消");
        intent.putExtra(AndroidImagePicker.KEY_PIC_PATH, picturePath);
        startActivity(intent);
    }

    /**
     * 显示选择pop窗口
     *
     * @param view
     */
    protected void showSelectPopView(View view) {
        mRYSelectPopWindow.showFromBottom(view);
    }

    /**
     * 点击性别 显示pop窗口
     * @param view
     */
    protected void showPopView(View view) {
        selectPopWindow.showBottom(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AndroidImagePicker.getInstance().removeOnImageCropCompleteListener(this);
        AndroidImagePicker.getInstance().removeOnPictureTakeCompleteListener(this);
    }

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults){
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

}
