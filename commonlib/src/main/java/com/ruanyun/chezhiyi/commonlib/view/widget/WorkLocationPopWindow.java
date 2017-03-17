package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Description ：工位选择
 * <p/>
 * Created by hdl on 2016/9/26.
 */
public class WorkLocationPopWindow extends PopupWindow implements OnClickListener {

	protected Context mContext;
	protected View mParentView;

	protected TextView mTVTitle;
	protected Button mButtonOK;
	protected ListView mListView;

	protected PopupWindow mPopupWindow;
	protected List<WorkBayInfo> mList;
	private WorkLocationAdapter mAdapter;
	private String workbayInfoNum;//选中工位

	public WorkLocationPopWindow(Context context, View mParentView,
								 List<WorkBayInfo> list, String workbayInfoNum) {
		mContext = context;
		this.mParentView = mParentView;
		this.workbayInfoNum = workbayInfoNum;
		mList = list;
		initView(mContext);
		initData();
	}

	protected void initView(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.popwindow_choice_technician, null);
		mTVTitle = (TextView) view.findViewById(R.id.tv_popup_Title);
		mListView = (ListView) view.findViewById(R.id.listView);
		mButtonOK = (Button) view.findViewById(R.id.btn_oK_or_cancel);
		mButtonOK.setOnClickListener(this);
		((Button)view.findViewById(R.id.btn_oK_or_cancel)).setText("取消");
		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		setTitle("暂无空闲工位");
		//实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0x00000000);
		//设置SelectPicPopupWindow弹出窗体的背景
		mPopupWindow.setBackgroundDrawable(dw);
		backgroundAlpha((Activity) mContext,0.7f);//0.0-1.0
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
				backgroundAlpha((Activity) mContext, 1f);
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
		mAdapter = new WorkLocationAdapter(mContext,R.layout.item_popwindow_single_choice,mList);
		mAdapter.setSingleBoxResourceID(workbayInfoNum);
		setHeight();
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mAdapter.setSingleBoxResourceID(mAdapter.getItem(position).getWorkbayInfoNum());
				mAdapter.notifyDataSetChanged();
				show(false);
				onItemPopupClick.onItemClick(mAdapter.getItem(position));
			}
		});
	}

	/**
	 * 设置ListView高度
	 */
	private void setHeight(){
		int listViewHeight = 0;
		int adaptCount = mAdapter.getCount();
		if(adaptCount<7)return;
		for(int i=0;i<7;i++){
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
			mPopupWindow.showAtLocation(mParentView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
		} else {
			mPopupWindow.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.btn_oK_or_cancel) {
			this.show(false);
		}
	}

	public void setTitle(String title) {
		mTVTitle.setText(title);
	}

	public interface OnPopupClickListener {
		void onItemClick(WorkBayInfo workBayInfo);
	}

	// add click callback
	OnPopupClickListener onItemPopupClick;

	public void setOnPopupClickListener(OnPopupClickListener onItemPopupClick) {
		this.onItemPopupClick = onItemPopupClick;
	}

	/**
	 * Adapter
	 */
	class WorkLocationAdapter extends CommonAdapter<WorkBayInfo> {
		private String SingleBoxResourceID;
		public WorkLocationAdapter(Context context, int layoutId, List<WorkBayInfo> datas) {
			super(context, layoutId, datas);
		}

		public void setSingleBoxResourceID(String SingleBoxResourceID) {
			this.SingleBoxResourceID = SingleBoxResourceID;
		}

		@Override
		protected void convert(ViewHolder viewHolder, WorkBayInfo item, int position) {
			AutoUtils.auto(viewHolder.getConvertView());
			viewHolder.setText(R.id.tv_user_pupop_name,item.getWorkbayName());
			ImageView ivSingleBox = viewHolder.getView(R.id.iv_single_Box);
			if(SingleBoxResourceID.equals(item.getWorkbayInfoNum())) {
				ivSingleBox.setImageResource(R.drawable.icon_box_selected);
			}else {
				ivSingleBox.setImageResource(R.drawable.icon_box_select);
			}
		}
	}

}
