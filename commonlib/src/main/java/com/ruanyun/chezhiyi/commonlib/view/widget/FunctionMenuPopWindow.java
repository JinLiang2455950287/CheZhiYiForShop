package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.*;
import android.view.ViewGroup.LayoutParams;
import android.widget.*;
import com.ruanyun.chezhiyi.commonlib.R;

import java.util.List;

/**
 * 技师案例详情菜单
 */
public class FunctionMenuPopWindow {

	protected Context mContext;
	protected View mParentView;

	protected ListView mListView;
	public static final int COMPILE = 0;
	public static final int DELETE = 1;
	public static final int SHARE = 2;

	protected PopupWindow mPopupWindow;
	private FunctionMenuAdapter mAdapter;
	int[] iconIds;
	String[] names;

	public FunctionMenuPopWindow(Context context, View mParentView,
								 int[] iconIds, String[] names) {
		mContext = context;
		this.mParentView = mParentView;
		this.iconIds = iconIds;
		this.names = names;
		initView(mContext);
		initData();
	}

	protected void initView(Context context) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.popwindow_function_menu, null);
		mPopupWindow = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setOutsideTouchable(true);
		ColorDrawable dw = new ColorDrawable(0x00);
		mPopupWindow.setBackgroundDrawable(dw);
		mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			@Override
			public void onDismiss() {
				backgroundAlpha(1);
			}
		});
		mListView = (ListView) view.findViewById(R.id.lv_popwindow_menu);

	}

	protected void initData() {

		mAdapter = new FunctionMenuAdapter(mContext,iconIds,names);
		mAdapter.notifyDataSetChanged();
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switch (position){
					case 0:
						PostBack(COMPILE);
						break;
					case 1:
						PostBack(DELETE);
						break;
					case 2:
						PostBack(SHARE);
						break;
				}
			}
		});
	}

	private void PostBack(int type) {
		if (onFunctionMenuItemPopupClick!=null) {
			mPopupWindow.dismiss();
			onFunctionMenuItemPopupClick.onFunctionMenuItemClick(type);
		}
	}


	/**
	 * 设置数据
	 * @param iconIds
	 * @param names
     */
	public void setIconIdsName(int[] iconIds, String[] names) {
		this.iconIds = iconIds;
		this.names = names;
		mAdapter.setIconIdsNames(iconIds,names);
	}

	/**
	 * 刷新数据，请先设置数据setIconIdsName
	 */
	public void notifyDataSetChanged(){
		mAdapter.notifyDataSetChanged();
	}

	public void show() {
		mPopupWindow.showAsDropDown(mParentView, (getWindowWidth()-getMeasuredWidth()-30), -20);
//		mPopupWindow.showAsDropDown(mParentView, -(getMeasuredWidth()), 10);
		backgroundAlpha(0.6f);
	}

	public int getMeasuredWidth(){
		mPopupWindow.getContentView().measure(0, 0);
		return mPopupWindow.getContentView().getMeasuredWidth();
	}

	public int getMeasuredHeight(){
		mPopupWindow.getContentView().measure(0, 0);
		return mPopupWindow.getContentView().getMeasuredHeight();
	}

	public int getWindowWidth(){
		return ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth();
	}

	/**
	 * 设置添加屏幕的背景透明度
	 *
	 * @param bgAlpha
	 */
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
		((Activity) mContext).getWindow().setAttributes(lp);
		//   5.1  有效
		((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}

	public interface OnPopupFunvtionMenuClickListener {
		void onFunctionMenuItemClick(int type);
	}

	// add click callback
	OnPopupFunvtionMenuClickListener onFunctionMenuItemPopupClick;

	public void setOnPopupFunvtionMenuClickListener(OnPopupFunvtionMenuClickListener onFunctionMenuItemPopupClick) {
		this.onFunctionMenuItemPopupClick = onFunctionMenuItemPopupClick;
	}

	/**
	 * Adapter
	 */
	class FunctionMenuAdapter extends BaseAdapter{
		Context context;
		int[] iconIds;
		String[] names;
		public FunctionMenuAdapter(Context context, int[] iconIds, String[] names) {
			this.context = context;
			setIconIdsNames(iconIds, names);
		}

		public void setIconIdsNames(int[] iconIds, String[] names) {
			this.iconIds = iconIds;
			this.names = names;
		}

		@Override
		public int getCount() {
			return names.length;
		}
		@Override
		public Object getItem(int position) {
			return names[position];
		}
		@Override
		public long getItemId(int position) {
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder ;
			if(convertView==null){
				convertView = LayoutInflater.from(context).inflate(R.layout.item_popwindow_function_menu,null);
				holder = new ViewHolder();
				holder.mtextView = (TextView) convertView.findViewById(R.id.tv_function_menu);
				convertView.setTag(holder);
			}
			holder = (ViewHolder) convertView.getTag();
			Drawable drawable= context.getResources().getDrawable(iconIds[position]);
			// 这一步必须要做,否则不会显示.
			drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

			holder.mtextView.setCompoundDrawables(drawable,null,null,null);
			holder.mtextView.setText(names[position]+"");
			return convertView;
		}
		class ViewHolder{
			TextView mtextView;
		}
	}


}
