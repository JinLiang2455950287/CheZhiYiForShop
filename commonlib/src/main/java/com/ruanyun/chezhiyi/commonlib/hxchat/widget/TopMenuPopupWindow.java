package com.ruanyun.chezhiyi.commonlib.hxchat.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;

/**
 * Description ：topbar 的添加好友，新建会话的泡泡
 * <p>
 * Created by ycw on 2016/8/11.
 */
public class TopMenuPopupWindow extends PopupWindow implements PopupWindow.OnDismissListener {

    public static final int MODE_SINGLE = 2122;
    public static final int MODE_MULTIPLE = 2123;

    Context mContext;
    LayoutInflater inflater;
    onMenuClickListener menuClickListener;
    int mType;

    View view, dividerView;
    TextView tvSendMsg, tvAddFriend;

    /**
     * 设置点击事件
     * @param menuClickListener
     */
    public void setMenuClickListener(onMenuClickListener menuClickListener) {
        this.menuClickListener = menuClickListener;
    }

    public TopMenuPopupWindow(Context context, int type) {
        super(context);
        mContext = context;
        mType = type;
        init();
    }


    private void init() {
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.ease_bg_menu_pop);
        setBackgroundDrawable(drawable);
        setFocusable(true);
        setOutsideTouchable(true);
        setOnDismissListener(this);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

        inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.ease_pop_menu_item_client, null);
        tvSendMsg = (TextView) view.findViewById(R.id.tv_menu_btn_msg);
        tvAddFriend = (TextView) view.findViewById(R.id.tv_menu_btn_add_friend);
        dividerView = view.findViewById(R.id.divider);

        tvSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuClickListener != null) {
                    menuClickListener.menuMsgClick();
                }
            }
        });
        tvAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (menuClickListener != null) {
                    menuClickListener.menuAddFriendClick();
                }
            }
        });
        setContentView(view);

        if (mType == MODE_MULTIPLE) {
            tvAddFriend.setVisibility(View.VISIBLE);
            dividerView.setVisibility(View.VISIBLE);
        } else {
            tvAddFriend.setVisibility(View.GONE);
            dividerView.setVisibility(View.GONE);
        }
        update();
    }

    public int getMeasuredWidth(){
        getContentView().measure(0, 0);
        return getContentView().getMeasuredWidth();
    }

    public int getMeasuredHeight(){
        getContentView().measure(0, 0);
        return getContentView().getMeasuredHeight();
    }

    public int getWindowWidth(){
        return ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth();
    }


    /**
     * 显示 popu 窗口
     * @param anchor
     * @param rightPadding
     */
    public void showAsDropDownRightPadding(View anchor, int rightPadding) {
        showAsDropDown(anchor, (getWindowWidth()-getMeasuredWidth()-40), 0);
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        backgroundAlpha(0.7f);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        backgroundAlpha(0.7f);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        backgroundAlpha(0.7f);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        backgroundAlpha(0.7f);
    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity)mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity)mContext).getWindow().setAttributes(lp);
        //   5.1  有效
        ((Activity)mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
    }

    public interface onMenuClickListener{
        /**
         * 发起会话
         */
        void menuMsgClick();

        /**
         * 添加好友
         */
        void menuAddFriendClick();
    }
}
