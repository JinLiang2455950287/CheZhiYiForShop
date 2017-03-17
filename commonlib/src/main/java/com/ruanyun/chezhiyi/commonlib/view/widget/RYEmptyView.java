package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.lang.reflect.Method;

/****
 * 自定义加载和显示数据加载失败view 需要绑定显示的view
 * 
 * @author jery 2015年10月21日15:43:53
 * 
 */
public class RYEmptyView extends RelativeLayout {
	private Button btnReload;
	// private ImageView imgStatus;
	private TextView tvTips;
	private View bindView;// 被绑定的view
	// private Animation rotaAnimation;
	private ProgressBar progressBar;

	public RYEmptyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		LayoutParams btnParma = new LayoutParams(-2, -2);
		btnParma.addRule(RelativeLayout.BELOW, R.id.emptyview_tv_tip);
		btnParma.addRule(RelativeLayout.CENTER_HORIZONTAL);
		btnParma.setMargins(0, 20, 0, 0);
		LayoutParams imgParma = new LayoutParams(-2, -2);
		LayoutParams progressBarParma = new LayoutParams(100, 100);
		progressBarParma.addRule(CENTER_IN_PARENT);
		imgParma.addRule(CENTER_IN_PARENT);
		LayoutParams tvParma = new LayoutParams(-2, -2);
		tvParma.addRule(RelativeLayout.BELOW, R.id.emptyview_img);
		tvParma.addRule(RelativeLayout.CENTER_HORIZONTAL);
		tvParma.setMargins(0, 20, 0, 0);

		// progressBar.setIndeterminate(true);
		// progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.loading_rotate_comman));

		progressBar = new ProgressBar(context,null);
		progressBar.setIndeterminate(true);
		progressBar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.anim_loading));
		btnReload = new Button(context);
		tvTips = new TextView(context);
		// imgStatus = new ImageView(context);
		tvTips.setId(R.id.emptyview_tv_tip);
		// imgStatus.setId(R.id.emptyview_img);
		progressBar.setId(R.id.emptyview_img);
		btnReload.setId(R.id.emptyview_btn);
		progressBar.setLayoutParams(progressBarParma);

	//	btnReload.setTextColor(getResources().getColor(R.color.white));
		btnReload.setText("刷 新");
		//btnReload.setPadding(10, 10, 10, 10);
	//	btnReload.setBackgroundResource(R.drawable.button_selector_default);
		//btnReload.setGravity(Gravity.CENTER);

		// tvTips.setTextColor(getResources().getColor(R.color.gray_divider));
		tvTips.setTextSize(16);
	//	setMessage(R.string.emptyview_loading);
		// rotaAnimation = AnimationUtils.loadAnimation(context,
		// R.anim.image_loading_anim);
		// imgStatus.setAnimation(rotaAnimation);
		// imgStatus.setImageResource(R.drawable.loading_comman);
		// imgStatus.setVisibility(View.GONE);
		// imgStatus.startAnimation(rotaAnimation);

		AutoUtils.auto(progressBar);
		AutoUtils.auto(tvTips);
		AutoUtils.auto(btnReload);

		addView(progressBar, progressBarParma);
		addView(tvTips, tvParma);
		addView(btnReload, btnParma);
	}

	public void bind(View view) {
		this.bindView = view;
	}

	public void unbind() {
		this.bindView = null;

	}

	public void loadSuccuss() {
		setVisibility(View.GONE);
		if (bindView != null)
			bindView.setVisibility(View.VISIBLE);

	}

	public void showEmpty() {
		if (bindView != null) {
			setVisibility(View.VISIBLE);
			bindView.setVisibility(View.GONE);
			// imgStatus.setVisibility(View.GONE);
			progressBar.setVisibility(View.INVISIBLE);
			btnReload.setVisibility(View.VISIBLE);
			setMessage("当前没有相关记录");
		}
	}

	public void showEmpty(String msg) {
		if (bindView != null) {
			setVisibility(View.VISIBLE);
			bindView.setVisibility(View.GONE);
			// imgStatus.setVisibility(View.GONE);
			progressBar.setVisibility(View.INVISIBLE);
			btnReload.setVisibility(View.GONE);
			setMessage(msg);
		}
	}
	public  void  setBtnReloadText(String text){
		this.btnReload.setText(text);
	}
	
	public void showNetError() {
		if (bindView != null) {
			bindView.setVisibility(View.GONE);
			setVisibility(View.VISIBLE);
			// imgStatus.setVisibility(View.GONE);
			btnReload.setVisibility(View.GONE);
			progressBar.setVisibility(View.INVISIBLE);
			setMessage("加载失败，网络不给力");
		}
	}

	public void showLoading() {
		if (bindView != null) {
			// imgStatus.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.VISIBLE);
			setMessage("加载中...");
			btnReload.setVisibility(View.GONE);
			bindView.setVisibility(View.GONE);
			setVisibility(View.VISIBLE);
		}
	}
    
	public void showLoadFail(String msg){
		if (bindView != null) {
			bindView.setVisibility(View.GONE);
			setVisibility(View.VISIBLE);
			btnReload.setVisibility(View.VISIBLE);
		}
	}
	public void showLoadFail() {
		if (bindView != null) {
			bindView.setVisibility(View.GONE);
			setVisibility(View.VISIBLE);
			progressBar.setVisibility(INVISIBLE);
			btnReload.setVisibility(View.VISIBLE);
			setMessage("加载失败");
		}
	}
	public void showLoadError() {
		if (bindView != null) {
			bindView.setVisibility(View.GONE);
			setVisibility(View.VISIBLE);
			progressBar.setVisibility(INVISIBLE);
			btnReload.setVisibility(View.INVISIBLE);
			setMessage("数据异常,加载失败");
		}
	}

	public void setOnReloadListener(OnClickListener clickListener){
		btnReload.setOnClickListener(clickListener);
	}

	public void setMessage(int stringId) {
		setMessage(getResources().getString(stringId));
	}

	public void setMessage(String message) {
		this.tvTips.setText(message);
	}

	public void onReloadBtnClick(final Object base, final String method, final Object... parameters) {
		btnReload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int length = parameters.length;
				Class<?>[] paramsTypes = new Class<?>[length];
				for (int i = 0; i < length; i++) {
					paramsTypes[i] = parameters[i].getClass();
				}
				try {
					Method m = base.getClass().getDeclaredMethod(method, paramsTypes);
					m.setAccessible(true);
					m.invoke(base, parameters);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	public void onReloadClick(final Object base, final String method, final Object... parameters) {
		btnReload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int length = parameters.length;
				Class<?>[] paramsTypes = new Class<?>[length];
				for (int i = 0; i < length; i++) {
					paramsTypes[i] = parameters[i].getClass();
				}
				try {
					Method m = base.getClass().getMethod(method, paramsTypes);
					m.setAccessible(true);
					m.invoke(base, parameters);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	public Button getBtnReload() {
		return btnReload;
	}
}
