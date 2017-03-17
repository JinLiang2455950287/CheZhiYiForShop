package com.ruanyun.imagepicker.widget;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ruanyun.imagepicker.AndroidImagePicker;
import com.ruanyun.imagepicker.GlideImagePresenter;
import com.ruanyun.imagepicker.ImagePresenter;
import com.ruanyun.imagepicker.R;
import com.ruanyun.imagepicker.UilImagePresenter;
import com.ruanyun.imagepicker.base.CommonAdapter;
import com.ruanyun.imagepicker.base.CommonViewHolder;
import com.ruanyun.imagepicker.bean.ImageItem;


import java.util.List;

public class ImagePickerAdapter extends CommonAdapter<ImageItem> {
    private OnImageChangeListener listener;
    private int sizeLimit = 9;
    UilImagePresenter mImagePresenter = new UilImagePresenter();

    // private int imageCount=10;
    public ImagePickerAdapter(Context context, List<ImageItem> datas,
                              int layoutId) {
        super(context, datas, layoutId);
    }

    public ImagePickerAdapter(Context context, List<ImageItem> datas,
                              int layoutId, int sizeLimit) {
        this(context, datas, layoutId);
        this.sizeLimit = sizeLimit;
    }

    @Override
    public void convert(final CommonViewHolder holder, final ImageItem imageItem) {
        ImageView ivPicker = holder.getView(R.id.ivPicker);
        ImageButton ivBtnDelete = holder.getView(R.id.ivbtnDelete);
        // int position = holder.getmPosition();
        if (!imageItem.isAdd) {
            ivPicker.setVisibility(View.VISIBLE);
            ivPicker.setOnClickListener(null);
//            ivPicker.setTag("file://" + imageItem.path);
//            if (ivPicker.getTag() != null && ivPicker.getTag().equals("file://" + imageItem.path)) {
//                ImageLoader.getInstance().displayImage((String) (ivPicker.getTag()), ivPicker, ImageLoaderOption.getInstance());
//            Glide.with(mContext).load(imageItem.path).into(ivPicker);
//            GlideImagePresenter.onPresentImage2(ivPicker, imageItem.path);
            mImagePresenter.onPresentImage2(ivPicker, imageItem.path, ivPicker.getWidth());
//            }
            ivBtnDelete.setVisibility(View.VISIBLE);
            ivBtnDelete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    mDatas.remove(imageItem);
                    int limit = AndroidImagePicker.getInstance().getSelectLimit();
                    AndroidImagePicker.getInstance().deleteSelectedImageItem(holder.getmPosition(), imageItem);
                    AndroidImagePicker.getInstance().setSelectLimit(limit + 1);
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onDelete(imageItem);
                    }
                }
            });
        } else if (imageItem.isAdd) {
            ivBtnDelete.setOnClickListener(null);
            ivBtnDelete.setVisibility(View.GONE);
            ivPicker.setVisibility(View.VISIBLE);
            Glide.clear(ivPicker);
            ivPicker.setImageResource(R.drawable.icon_addphoto);
            ivPicker.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        listener.onAddBtnClick();
                    }
                }
            });
            if (getCount() == sizeLimit + 1) {
                ivBtnDelete.setOnClickListener(null);
                ivBtnDelete.setVisibility(View.GONE);
                ivPicker.setVisibility(View.GONE);
            }
        }
    }

    public interface OnImageChangeListener {
        void onAddBtnClick();
        void onDelete(ImageItem item);
    }

    public void setOnImageChangeListener(OnImageChangeListener clickListener) {
        this.listener = clickListener;
    }

    /**
     * @param item
     */
    public void sortListData(ImageItem item) {
        if (mDatas.size() == 0) {
            return;
        } else {
          /*  int index = -1;
            int size = mDatas.size();
            for (int i = 0; i < size; i++) {
                if (mDatas.get(i).isAdd) {

                    index = i;
                    mDatas.add(size, item);
                }
            }*/
          mDatas.remove(item);
          mDatas.add(item);
        }
    }

    public int getSizeLimit() {
        return sizeLimit;
    }

    public void setSizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
    }
}
