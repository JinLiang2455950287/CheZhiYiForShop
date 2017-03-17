package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description :技师添加服务项目
 * <p/>
 * Created by hdl on 2016/9/29.
 */
public class ServiceSelectPopWindow implements OnClickListener {

	protected Context mContext;
	protected View mParentView;

	protected ListView mListView;
	protected LinearLayout llPopwindow;

	protected PopupWindow mPopupWindow;
	protected List<ProjectType> mList;
	private ServiceSelectAdapter mAdapter;
	private int selectedProjectType;//选中项目

	public ServiceSelectPopWindow(Context context, View mParentView,
								  List<ProjectType> list, int selectedProjectType) {
		mContext = context;
		this.mParentView = mParentView;
		this.selectedProjectType = selectedProjectType;
		mList = list;
		initView(mContext);
		initData();
	}

	protected void initView(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.popwindow_service_select, null);
		mListView = (ListView) view.findViewById(R.id.lv_service_select);
		llPopwindow = (LinearLayout) view.findViewById(R.id.ll_popwindow);
		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		AutoUtils.auto(view);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(false);
//		实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00);
//		设置SelectPicPopupWindow弹出窗体的背景
		mPopupWindow.setBackgroundDrawable(dw);
		mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				onItemPopupClick.onItemClick(-1);
			}
		});
	}

	/**
	 * 设置添加屏幕的背景透明度
	 * @param bgAlpha
	 */
	public void backgroundAlpha(Activity context, float bgAlpha)
	{
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = bgAlpha;
		context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		context.getWindow().setAttributes(lp);
	}

	protected void initData() {
		mAdapter = new ServiceSelectAdapter(mContext,R.layout.item_popwindow_single_choice,mList);
		mAdapter.setSingleBoxResourceID(selectedProjectType);
		setHeight();
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mAdapter.setSingleBoxResourceID(position);
				mAdapter.notifyDataSetChanged();
				show(false);
				if(selectedProjectType==position) {
					onItemPopupClick.onItemClick(-1);
				}else {
					onItemPopupClick.onItemClick(position);
				}
			}
		});
		llPopwindow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onItemPopupClick.onItemClick(-1);
			}
		});
	}

	/**
	 * 设置ListView高度
	 */
	private void setHeight(){
		int listViewHeight = 0;
		int adaptCount = mAdapter.getCount();
		if(adaptCount<6)return;
		for(int i=0;i<6;i++){
			View temp = mAdapter.getView(i,null,mListView);
			temp.measure(0,0);
			listViewHeight += temp.getMeasuredHeight();
		}
		LayoutParams layoutParams = this.mListView.getLayoutParams();
		layoutParams.width = LayoutParams.MATCH_PARENT;
		layoutParams.height = listViewHeight;
		mListView.setLayoutParams(layoutParams);
	}

	/**
	 * 显示popWindow
	 * @param bShow
     */
	public void show(boolean bShow) {

		if (bShow) {
			mPopupWindow.showAsDropDown(mParentView);
//			backgroundAlpha((Activity) mContext,0.5f);//0.0-1.0
		} else {
			mPopupWindow.dismiss();
//			backgroundAlpha((Activity) mContext, 1f);
		}
	}

	public int getSelectedProjectType() {
		return selectedProjectType;
	}

	/**
	 * 设置选中的ProjectType
	 * @param selectedProjectType
     */
	public void setSelectedProjectType(int selectedProjectType) {
		this.selectedProjectType = selectedProjectType;
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.btn_oK_or_cancel) {
			this.show(false);
		}
	}


	public interface OnPopupClickListener {
		void onItemClick(int position);
	}

	// add click callback
	OnPopupClickListener onItemPopupClick;

	public void setOnPopupClickListener(OnPopupClickListener onItemPopupClick) {
		this.onItemPopupClick = onItemPopupClick;
	}

	/**
	 * Adapter
	 */
	class ServiceSelectAdapter extends CommonAdapter<ProjectType> {
		private int SingleBoxResourceID = 0;
		public ServiceSelectAdapter(Context context, int layoutId, List<ProjectType> datas) {
			super(context, layoutId, datas);
		}

		public int getSingleBoxResourceID() {
			return SingleBoxResourceID;
		}

		public void setSingleBoxResourceID(int SingleBoxResourceID) {
			this.SingleBoxResourceID = SingleBoxResourceID;
		}

		@Override
		protected void convert(ViewHolder viewHolder, ProjectType item, int position) {
			AutoUtils.auto(viewHolder.getConvertView());
			TextView tvName = viewHolder.getView(R.id.tv_user_pupop_name);
			tvName.setText(item.getProjectName());
			ImageView ivSingleBox = viewHolder.getView(R.id.iv_single_Box);
			if(SingleBoxResourceID==position) {
				ivSingleBox.setVisibility(View.VISIBLE);
				ivSingleBox.setImageResource(R.drawable.icon_goods_list_tick);
				ColorStateList csl = mContext.getResources().getColorStateList(R.color.theme_color_default);
				tvName.setTextColor(csl);
			}else {
				ivSingleBox.setVisibility(View.INVISIBLE);
				ColorStateList csl = mContext.getResources().getColorStateList(R.color.text_black);
				tvName.setTextColor(csl);

			}
		}
	}

}
