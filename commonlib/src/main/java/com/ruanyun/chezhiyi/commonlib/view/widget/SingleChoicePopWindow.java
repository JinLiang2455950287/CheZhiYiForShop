package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;

/**
 * 技师单选 PopupWindow
 */
public class SingleChoicePopWindow extends PopupWindow implements OnClickListener {

	protected Context mContext;
	protected View mParentView;

	protected TextView mTVTitle;
	protected Button mButtonOK;
	protected ListView mListView;

	protected PopupWindow mPopupWindow;
	protected List<User> mList;
	private ChooseTechnicianAdapter mAdapter;
	private User user;

	public SingleChoicePopWindow(Context context, View mParentView,
								 List<User> list) {
		mContext = context;
		this.mParentView = mParentView;
		mList = list;
		if (mList!=null) user = mList.get(0);
		initView(mContext);
		initData();
	}

	protected void initView(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.popwindow_choice_technician, null);
		mTVTitle = (TextView) view.findViewById(R.id.tv_popup_Title);
		mButtonOK = (Button) view.findViewById(R.id.btn_oK_or_cancel);
		mButtonOK.setOnClickListener(this);

		mPopupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		mPopupWindow.setBackgroundDrawable(dw);

		mListView = (ListView) view.findViewById(R.id.listView);

	}

	protected void initData() {
		mAdapter = new ChooseTechnicianAdapter(mContext,R.layout.item_popwindow_single_choice,mList);
		setHeight();
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mAdapter.setSingleBoxResourceID(position);
				mAdapter.notifyDataSetChanged();
				user = mList.get(position);
			}
		});
	}

	public void setHeight(){
		int listViewHeight = 0;
		int adaptCount = mAdapter.getCount();
		if(adaptCount<7)return;
		for(int i=0;i<7;i++){
			View temp = mAdapter.getView(i,null,mListView);
			temp.measure(0,0);
			listViewHeight += temp.getMeasuredHeight();
		}
		ViewGroup.LayoutParams layoutParams = this.mListView.getLayoutParams();
		layoutParams.width = LayoutParams.MATCH_PARENT;
		layoutParams.height = listViewHeight;
		mListView.setLayoutParams(layoutParams);
	}

	public void show(boolean bShow) {

		if (bShow) {
			mPopupWindow.showAtLocation(mParentView, Gravity.BOTTOM, 0, 0);
		} else {
			mPopupWindow.dismiss();
		}
	}

//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.btn_oK:
//			this.show(false);
//			onItemPopupClick.onItemClick(user);
//			break;
//		}
//	}

	@Override
	public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.btn_ok) {
			this.show(false);
			onItemPopupClick.onItemClick(user);
		}
	}

	public void setTitle(String title) {
		mTVTitle.setText(title);
	}

	public interface OnPopupClickListener {
		void onItemClick(User user);
	}

	// add click callback
	OnPopupClickListener onItemPopupClick;

	public void setOnPopupClickListener(OnPopupClickListener onItemPopupClick) {
		this.onItemPopupClick = onItemPopupClick;
	}

	/**
	 * Adapter
	 */
	class ChooseTechnicianAdapter extends CommonAdapter<User> {
		private int SingleBoxResourceID = 0;
		public ChooseTechnicianAdapter(Context context, int layoutId, List<User> datas) {
			super(context, layoutId, datas);
		}

		public int getSingleBoxResourceID() {
			return SingleBoxResourceID;
		}

		public void setSingleBoxResourceID(int SingleBoxResourceID) {
			this.SingleBoxResourceID = SingleBoxResourceID;
		}

		@Override
		protected void convert(ViewHolder viewHolder, User item, int position) {
			viewHolder.setText(R.id.tv_user_pupop_name,item.getNickName());
			ImageView ivSingleBox = viewHolder.getView(R.id.iv_single_Box);
			if(SingleBoxResourceID==position) {
				ivSingleBox.setImageResource(R.drawable.icon_box_selected);
			}else {
				ivSingleBox.setImageResource(R.drawable.icon_box_select);
			}
		}
	}

}
