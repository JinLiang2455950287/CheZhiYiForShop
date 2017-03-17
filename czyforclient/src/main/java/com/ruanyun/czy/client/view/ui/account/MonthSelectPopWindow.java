package com.ruanyun.czy.client.view.ui.account;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import com.ruanyun.czy.client.R;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 月份选择 PopupWindow
 */
public class MonthSelectPopWindow {

	protected Context mContext;
	protected View mParentView;

	protected ListView mListView;

	protected PopupWindow mPopupWindow;
	protected List<String> mList;
	private MonthSelectAdapter mAdapter;
	private String selectItem;

	public MonthSelectPopWindow(Context context, View mParentView) {
		mContext = context;
		this.mParentView = mParentView;
		initView(mContext);
		initData();
	}

	protected void initView(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.popwindow_month_select, null);

		mPopupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		mPopupWindow.setBackgroundDrawable(dw);

		mListView = (ListView) view.findViewById(R.id.lv_popwindow_month_select);

	}

	protected void initData() {
		mList = new ArrayList<>();
		String month;
		for (int i=1;i<=9;i++){
			month = "0"+i;
			mList.add(month);
		}
		month = "10";
		mList.add(month);
		month = "11";
		mList.add(month);
		month = "12";
		mList.add(month);
		mAdapter = new MonthSelectAdapter(mContext,R.layout.item_popwindow_month_select,mList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				onItemPopupClick.onItemClick(mList.get(position));
			}
		});
	}

	public void show(boolean bShow) {

		if (bShow) {
			mPopupWindow.showAsDropDown(mParentView, 10, 10);
		} else {
			mPopupWindow.dismiss();
		}
	}

	/**
	 * 选中
	 * @param item
	 */
	public void setSelect(String item) {
		this.selectItem = item;
		mListView.setSelection(getPosition(item));
		mAdapter.setItem(item);
		mAdapter.notifyDataSetChanged();
	}

	private int getPosition(String item) {
		for (int i = 0; i < mList.size(); i++) {
			if (item.equals(mList.get(i))) {
				return i;
			}
		}
		return 0;
	}

	public interface OnPopupClickListener {
		void onItemClick(String month);
	}

	// add click callback
	OnPopupClickListener onItemPopupClick;

	public void setOnPopupClickListener(OnPopupClickListener onItemPopupClick) {
		this.onItemPopupClick = onItemPopupClick;
	}

	/**
	 * Adapter
	 */
	class MonthSelectAdapter extends CommonAdapter<String> {
		private String mItem;

		public String getItem() {
			return mItem;
		}

		public void setItem(String item) {
			this.mItem = item;
		}

		public MonthSelectAdapter(Context context, int layoutId, List<String> datas) {
			super(context, layoutId, datas);
		}

		@Override
		protected void convert(ViewHolder viewHolder, String item, int position) {
			viewHolder.setText(R.id.tv_popwindow_month_select,item);
			TextView tvPopwindowMonthSelect = viewHolder.getView(R.id.tv_popwindow_month_select);
			if (item.equals(this.mItem)) {
				tvPopwindowMonthSelect.setSelected(true);
			} else {
				tvPopwindowMonthSelect.setSelected(false);
			}
		}
	}

}
