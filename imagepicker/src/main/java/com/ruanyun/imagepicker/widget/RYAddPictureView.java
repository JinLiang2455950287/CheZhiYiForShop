package com.ruanyun.imagepicker.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.GridView;


import com.ruanyun.imagepicker.AndroidImagePicker;
import com.ruanyun.imagepicker.R;
import com.ruanyun.imagepicker.bean.ImageItem;
import com.ruanyun.imagepicker.bean.ImageSet;
import com.ruanyun.imagepicker.ui.ImagesGridActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel
 * @ClassName: RYAddPictureView
 * @Description: 软云通用添加图片的view，注意监听的注册和销毁，和onactivityresult的回调，可以设置sizeLimit控制图片数量
 * @date 2016/4/29 8:57
 */
public class RYAddPictureView extends GridView implements ImagePickerAdapter.OnImageChangeListener,
        AndroidImagePicker.OnPictureTakeCompleteListener, AndroidImagePicker.OnImagePickCompleteListener {


    Context mContext;

    public  int requestCode = 1433;

    private List<ImageItem> imageList = new ArrayList<>();
    private int sizeLimit = 1;

    ImagePickerAdapter adapter;

    ImageItem addItem;

    ImageSet imageSetCache = new ImageSet();

    public RYAddPictureView(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext = mContext;
        initView();
    }

    private void initView() {
        initImagePicker();
    }

    private void initImagePicker() {
        /** S imagepicker */
        imageList = new ArrayList<>();
        addItem = new ImageItem("", "", 1);
        addItem.isAdd = true;
        imageList.add(addItem);
        adapter = new ImagePickerAdapter(mContext, imageList, R.layout.grid_item_select_image, sizeLimit);
        setAdapter(adapter);
        adapter.setOnImageChangeListener(this);

        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit);
        /** E imagepicker */
    }

    /**
     * @description: 调用时需要在onStart加上
     * @author: Daniel
     */
    public void setOnListeners() {
        AndroidImagePicker.getInstance().addOnPictureTakeCompleteListener(this);
        AndroidImagePicker.getInstance().addOnImagePickCompleteListener(this);
    }

    /**
     * @description: 调用时需要在onDestroy加上
     * @author: Daniel
     */
    public void destroyListeners() {
        AndroidImagePicker.getInstance().removeOnPictureTakeCompleteListener(this);
        AndroidImagePicker.getInstance().removeOnImagePickCompleteListener(this);
        if (imageList != null) {
            imageList.clear();
            imageList = null;
        }
    }

    /**
     * 添加图片
     */
    @Override
    public void onAddBtnClick() {
        handleAddImageClick();
    }

    @Override
    public void onDelete(ImageItem item) {
        imageList = adapter.getData();
        imageSetCache.imageItems = imageList;
       // AppBus.getInstance().post(new EventInfo(C.Event.IMAGE_LIST_CHANGED,imageSetCache));
        if(onPickResultChangedListener!=null)
         onPickResultChangedListener.onPicDelete(item);
    }

    /**
     * @description: 添加图片调用接口
     * @author: Daniel
     */
    private void handleAddImageClick() {
        Intent intent = new Intent();
        AndroidImagePicker.getInstance().setSelectMode(AndroidImagePicker.Select_Mode.MODE_MULTI);
        AndroidImagePicker.getInstance().setShouldShowCamera(true);
        AndroidImagePicker.getInstance().setRequestCode(requestCode);
        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit - (adapter.getCount() - 1));
        intent.setClass(mContext, ImagesGridActivity.class);
        ((Activity) mContext).startActivityForResult(intent, requestCode);
    }

    /**
     * @description: 更新图片列表
     * @author: Daniel
     */
    private void refreshGrid() {
        adapter.setData(this.imageList);
        adapter.sortListData(addItem);
        adapter.notifyDataSetChanged();
        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit - (adapter.getCount() - 1));
        imageSetCache.imageItems = imageList;
       // AppBus.getInstance().post(new EventInfo(C.Event.IMAGE_LIST_CHANGED,imageSetCache));
    }
    /**
      * 编辑时初始化组装自己的list item 传入
      *@author zhangsan
      *@date   16/9/8 下午2:39
      */
    public void refreshImage(List<ImageItem> imageItems){
        this.imageList = imageItems;
        adapter.setData(imageList);
        adapter.sortListData(addItem);
        adapter.notifyDataSetChanged();
        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit - (adapter.getCount() - 1));
    }


    /**
     * @description: 选完图片onActivityResult需要调用该方法
     * @author: Daniel
     */
    public void onImageActivityResult() {
        imageList.addAll(AndroidImagePicker.getInstance().getSelectedImages());
        refreshGrid();
    }

    @Override
    public void onPictureTakeComplete(String picturePath,int requestCode) {
        if(requestCode == this.requestCode) {
            imageList.add(new ImageItem(picturePath, "", 1));
            refreshGrid();
        }
    }

    @Override
    public void onImagePickComplete(List<ImageItem> items,int requestCode) {
//        LogX.d("panmengze","onImagePickComplete listsize = " + items.size());
        if(requestCode == this.requestCode) {
            imageList.addAll(items);
            refreshGrid();
        }
    }

    public int getSizeLimit() {
        return sizeLimit;
    }

    /**
     * @description: 设置图片限制
     * @author: Daniel
     */
    public void setSizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit - (adapter.getCount() - 1));
        adapter.setSizeLimit(sizeLimit);
    }



    /**
     * @description: 获取输入图片列表
     * @author: Daniel
     */
    public List<ImageItem> getImageList() {
        return imageList;
    }

    public void setImageList(List<ImageItem> imageList) {
        if(imageList == null){
            imageList = new ArrayList<>();
        }
        if(imageList.size() < 1){
            addItem.isAdd = true;
            imageList.add(addItem);
        }
        this.imageList = imageList;
        adapter.setData(this.imageList);
        adapter.notifyDataSetChanged();
        AndroidImagePicker.getInstance().setSelectLimit(sizeLimit - (adapter.getCount() - 1));
    }

    /**
     * 最大高度
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    public void setOnPickResultChangedListener(RYAddPictureView.onPickResultChangedListener onPickResultChangedListener) {
        this.onPickResultChangedListener = onPickResultChangedListener;
    }
    onPickResultChangedListener onPickResultChangedListener;

   public interface  onPickResultChangedListener{
       //点击图片删除时触发
       void onPicDelete(ImageItem item);
      // void onImage
   }
}
