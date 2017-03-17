package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.*;
import com.ruanyun.chezhiyi.commonlib.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 车牌地区选择 PopupWindow
 */
public class CarRegionPopWindow extends PopupWindow implements PopupWindow.OnDismissListener{

	protected Context mContext;
	//protected View mParentView;

	protected GridView mGridView;

	//protected PopupWindow mPopupWindow;
	protected List<String> mList;
	private CarRegionAdapter mAdapter;
	//private String carRegion;

	public CarRegionPopWindow(Context context) {
		mContext = context;
		//this.mParentView = mParentView;
		mList=Arrays.asList(mContext.getResources().getStringArray(R.array.car_region));
		//mList = list;
		initView(mContext);
		initData();
	}

	protected void initView(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.popwindow_car_region, null);
		//mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
		//		LayoutParams.WRAP_CONTENT);
		//mPopupWindow.setFocusable(true);
		//mPopupWindow.setOutsideTouchable(true);
		setFocusable(true);
		setOutsideTouchable(false);
		setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
		setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		setAnimationStyle(R.style.popupwindow_anim_style);
		//ColorDrawable dw = new ColorDrawable(0x00);
		//mPopupWindow.setBackgroundDrawable(dw);
		mGridView = (GridView) view.findViewById(R.id.gv_popwindow_car_region);
		setContentView(view);
		setOnDismissListener(this);

	}

	protected void initData() {
		mAdapter = new CarRegionAdapter(mContext,R.layout.item_popwindow_car_region,mList);
		mGridView.setAdapter(mAdapter);
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onPopWindowCarRegionClick.onPopWindowCarRegionItemClick(mList.get(position));
				dismiss();
			}
		});
	}

	/**
	 * 设置添加屏幕的背景透明度
	 */
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
		((Activity) mContext).getWindow().setAttributes(lp);
	}

	/**
	 * 显示 PopupWindow
	 * @param
     */
	public void show(View view) {
		showAtLocation(view, Gravity.BOTTOM, 0, 0);
		backgroundAlpha(0.6f);
		//   5.1  有效
		((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	@Override
	public void onDismiss() {
		backgroundAlpha(1f);
	}

	public interface OnPopWindowCarRegionClickListener {
		void onPopWindowCarRegionItemClick(String carRegion);
	}

	// add click callback
	OnPopWindowCarRegionClickListener onPopWindowCarRegionClick;

	public void setOnPopupClickListener(OnPopWindowCarRegionClickListener onPopWindowCarRegionClick) {
		this.onPopWindowCarRegionClick = onPopWindowCarRegionClick;
	}

	/**
	 * adapter
	 */
	class CarRegionAdapter extends CommonAdapter<String> {
		public CarRegionAdapter(Context context, int layoutId, List<String> datas) {
			super(context, layoutId, datas);
		}

		@Override
		protected void convert(ViewHolder viewHolder, String item, int position) {
			viewHolder.setText(R.id.tv_car_region,item + "");
		}


	}

}
