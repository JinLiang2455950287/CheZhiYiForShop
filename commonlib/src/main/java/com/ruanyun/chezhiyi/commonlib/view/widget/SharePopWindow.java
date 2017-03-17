package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mob.tools.utils.UIHandler;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.IconInfo;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.adapter.ShareIconAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * 分享 弹出框
 */
public class SharePopWindow extends PopupWindow implements PopupWindow.OnDismissListener, PlatformActionListener,
        Handler.Callback {
    private Context mContext;
    private String strings[] = {"分享到QQ好友", "分享到QQ空间", "分享到微信", "分享到朋友圈", "分享到微博"};
    private int icon[] = {R.drawable.icon_share_qq, R.drawable.icon_share_qq_quan,
            R.drawable.icon_share_ws, R.drawable.icon_share_ws_quan, R.drawable.icon_share_xl};

    private GridView grid;

//    /** 朋友圈分享对象 */
//    private Platform platform_circle;
//
//    /** 微信好友分享对象 */
//    private Platform platform_wxFriend;
//
//    /** QQ空间分享对象 */
//    private Platform platform_qzone;
//
//    /** QQ好友分享对象 */
//    private Platform platform_qqFriend;
//
//    /** 新浪分享对象 */
//    private Platform platform_sina;

    /** 分享的标题部分 */
    private String mShareTitle = "掌上汽服下载链接";

    /** 分享的文字内容部分 */
    private String mShareText = "分享自"+ App.getInstance().getStoreInfo().getSotreName();

    /** 分享的图片部分 */
    private String mShareImage = FileUtil.getImageUrl(App.getInstance().getStoreInfo().getFigurePic());

    /** 分享的网址部分 */
    private String mShareUrl = String.format(FileUtil.getFileUrl(C.ApiUrl.URL_DOWNLOAD), App
            .getInstance().getCurrentUserNum());

    /**
     * 默认是分享app下载地址
     */
    private boolean shareApp = true;

    /**
     * 发送给好友
     */
    private boolean sendToFriend = false;//发送给好友

    private ShareIconAdapter shareAdapter;

    public SharePopWindow(Context mContext) {
        this.mContext = mContext;
        initShareSDK();
        initView();
    }

    public SharePopWindow(Context context, View.OnClickListener clickListener) {
        super(context);
        this.mContext = context;
        this.copyClickListener=clickListener;
        initShareSDK();
        initView();
    }
    
    public SharePopWindow(Context context, boolean sendToFriend, View.OnClickListener clickListener) {
        super(context);
        this.mContext = context;
        this.copyClickListener=clickListener;
        this.sendToFriend = sendToFriend;
        initShareSDK();
        initView();
    }

    public SharePopWindow(Context context, String mShareTitle, String mShareText, String mShareImage, String mShareUrl) {
        super(context);
        this.mContext = context;
        this.mShareTitle = mShareTitle;
        this.mShareText = mShareText;
        this.mShareImage = mShareImage;
        this.mShareUrl = mShareUrl;
        initShareSDK();
        initView();
    }



    private void initView() {
        setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color
                .transparent)));
        setOutsideTouchable(false);
        setFocusable(true);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setAnimationStyle(R.style.popupwindow_anim_style);
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_share, null);
        grid=ButterKnife.findById(view,R.id.grid);
        List<IconInfo> infoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            infoList.add(new IconInfo(icon[i],strings[i]));
        }
        shareAdapter=new ShareIconAdapter(mContext,R.layout.grid_item_share,infoList);
        grid.setAdapter(shareAdapter);
//        ImageView ivClose = ButterKnife.findById(view, R.id.iv_close);
//        ivClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
        TextView tvCancel=ButterKnife.findById(view,R.id.tv_cancel);
        TextView tvCopy=ButterKnife.findById(view,R.id.tv_copy);//复制链接按钮
        if (sendToFriend) {
            tvCopy.setVisibility(View.VISIBLE);
        } else {
            tvCopy.setVisibility(View.GONE);
        }
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tvCopy.setOnClickListener(copyClickListener);
        setContentView(view);
        setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setOnDismissListener(this);
        // : 2016/9/30 分享点击事件
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    // 分享到微信朋友圈
                    case 3:
                        shareCircleFriend();
                        break;
                    // 分享到QQ空间
                    case 1:
                        shareQzone();
                        break;
                    // 分享到微信好友
                    case 2:
                        shareWxFriend();
                        break;
                    // 分享到QQ好友
                    case 0:
                        shareQQFriend();
                        break;
                    // 分享到新浪微博
                    case 4:
                        shareSinaWeiBo();
                        break;
                }
            }
        });
    }

    private void initShareSDK() {
        // 初始化sdk分享资源
        ShareSDK.initSDK(mContext);
    }

    View.OnClickListener copyClickListener;
    public void setCopyClickListener(View.OnClickListener listener){
        this.copyClickListener=listener;
    }

    /**
     * 分享到朋友圈
     */
    private void shareCircleFriend() {
        if (!AppUtility.isInstalled(mContext, "com.tencent.mm")) {
            AppUtility.showToastMsg("请先安装微信");
            return;
        }

        OnekeyShare onekeyShare = new OnekeyShare();
        onekeyShare.setPlatform(WechatMoments.NAME);
        onekeyShare.setSilent(true);
        onekeyShare.setTitle(mShareTitle);
        onekeyShare.setUrl(mShareUrl);
        onekeyShare.setText(mShareText);
        onekeyShare.setImageUrl(mShareImage);
        onekeyShare.setCallback(this);
        onekeyShare.show(mContext);

//        platform_circle = ShareSDK.getPlatform(mContext, WechatMoments.NAME);
//        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
//        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
//        sp.setTitle(mShareTitle);
//        sp.setText(mShareText);
//        sp.setUrl(mShareUrl);
//        //        sp.setImageUrl(mShareImage);
//        platform_circle.setPlatformActionListener(this); // 设置分享事件回调
//        // 执行图文分享
//        platform_circle.share(sp);
    }

    /**
     * 分享到微信好友
     */
    private void shareWxFriend() {
        if (!AppUtility.isInstalled(mContext, "com.tencent.mm")) {
            AppUtility.showToastMsg("请先安装微信");
            return;
        }

        OnekeyShare onekeyShare = new OnekeyShare();
        onekeyShare.setPlatform(Wechat.NAME);
        onekeyShare.setSilent(true);
        onekeyShare.setTitle(mShareTitle);
        onekeyShare.setUrl(mShareUrl);
        onekeyShare.setText(mShareText);
        onekeyShare.setImageUrl(mShareImage);
        onekeyShare.setCallback(this);
        onekeyShare.show(mContext);

//        platform_wxFriend = ShareSDK.getPlatform(mContext, Wechat.NAME);
//        Wechat.ShareParams sp = new Wechat.ShareParams();
//        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
//        sp.setTitle(mShareTitle);
//        sp.setText(mShareText);
//        sp.setUrl(mShareUrl);
//        sp.setImageArray(new String[]{mShareImage});
//        //        mShareUrl = String.format(FileUtil.getFileUrl(C.ApiUrl.URL_DOWNLOAD), App
//        //                .getInstance().getCurrentUserNum());
//        //        sp.setImageUrl(mShareImage);
//        //        sp.setImagePath(mShareImage);
//        platform_wxFriend.setPlatformActionListener(this); // 设置分享事件回调
//        // 执行图文分享
//        platform_wxFriend.share(sp);
    }

    /**
     * 分享到QQ空间
     */
    private void shareQzone() {

        OnekeyShare onekeyShare = new OnekeyShare();
        onekeyShare.setPlatform(QZone.NAME);
        onekeyShare.setSilent(true);
        onekeyShare.setTitle(mShareTitle);
        onekeyShare.setTitleUrl(mShareUrl);
        onekeyShare.setSiteUrl(mShareUrl);
        onekeyShare.setText(mShareText);
        onekeyShare.setImageUrl(mShareImage);
        onekeyShare.setCallback(this);
        onekeyShare.show(mContext);

//        platform_qzone = ShareSDK.getPlatform(mContext, QZone.NAME);
//        cn.sharesdk.tencent.qzone.QZone.ShareParams sp = new cn.sharesdk.tencent.qzone.QZone.ShareParams();
//        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
//        sp.setTitle(mShareTitle);
//        sp.setText(mShareText);
//        sp.setTitleUrl(mShareUrl);
//        sp.setSite(mShareTitle);
//        sp.setSiteUrl(mShareUrl);
//        sp.setImageUrl(mShareImage);// imageUrl存在的时候，原来的imagePath将被忽略
//        platform_qzone.setPlatformActionListener(this); // 设置分享事件回调
//        // 执行图文分享
//        platform_qzone.share(sp);
    }

    /**
     * 分享到QQ好友
     */
    private void shareQQFriend() {

        OnekeyShare onekeyShare = new OnekeyShare();
        onekeyShare.setPlatform(QQ.NAME);
        onekeyShare.setSilent(true);
        onekeyShare.setTitle(mShareTitle);
        onekeyShare.setTitleUrl(mShareUrl);
        onekeyShare.setText(mShareText);
        onekeyShare.setImageUrl(mShareImage);
        onekeyShare.setCallback(this);
        onekeyShare.show(mContext);

//        platform_qqFriend = ShareSDK.getPlatform(QQ.NAME);
//        cn.sharesdk.tencent.qq.QQ.ShareParams sp = new cn.sharesdk.tencent.qq.QQ.ShareParams();
//        sp.setShareType(Platform.SHARE_WEBPAGE);// 一定要设置分享属性
//        sp.setTitle(mShareTitle);
//        sp.setTitleUrl(mShareUrl);
//        sp.setText(mShareText);
//        sp.setImageUrl(mShareImage);
//        platform_qqFriend.setPlatformActionListener(this); // 设置分享事件回调
//        // 执行图文分享
//        platform_qqFriend.share(sp);
    }

    /**
     * 分享到新浪微博
     */
    private void shareSinaWeiBo() {

        OnekeyShare onekeyShare = new OnekeyShare();
        onekeyShare.setPlatform(SinaWeibo.NAME);
        onekeyShare.setSilent(true);
        onekeyShare.setTitle(mShareTitle);
        onekeyShare.setTitleUrl(mShareUrl);
        onekeyShare.setText(mShareText);
        onekeyShare.setImageUrl(mShareImage);
        onekeyShare.setCallback(this);
        onekeyShare.show(mContext);

//        platform_sina = ShareSDK.getPlatform(SinaWeibo.NAME);
//        System.out.println("platform_sina.isValid()----"+platform_sina.isValid());
////        if (!platform_sina.isValid()) {// 如果有新浪微博客户端，每次都可以重新选择或添加分享账号
////            platform_sina.removeAccount();
////        }
//        cn.sharesdk.sina.weibo.SinaWeibo.ShareParams sp = new cn.sharesdk.sina.weibo.SinaWeibo.ShareParams();
//        sp.setShareType(Platform.SHARE_WEBPAGE);
//        sp.setTitle(mShareTitle);
//        sp.setText(mShareText);
//        sp.setUrl(mShareUrl);
////        sp.setImageUrl(mShareImage);
//        sp.setImageArray(new String[]{mShareImage});
//        platform_sina.setPlatformActionListener(this); // 设置分享事件回调
//        // 执行图文分享
//        platform_sina.share(sp);
////        isSina = true;
////        dialog.show();

    }



    public void showBottom(View parent) {
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        backgroundAlpha(0.6f);

    }

    /**
     * 设置添加屏幕的背景透明度
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) mContext).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity) mContext).getWindow().setAttributes(lp);
        //   5.1  有效
        ((Activity) mContext).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void onDismiss() {
        backgroundAlpha(1f);
        // 释放资源空间
        ShareSDK.stopSDK(mContext);
    }

    @Override
    public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
            Message msg = new Message();
            msg.arg1 = 1;
            msg.arg2 = action;
            msg.obj = plat;
            UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform plat, int action) {
        Message msg = new Message();
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onError(Platform plat, int action, Throwable t) {
        t.printStackTrace();
        Message msg = new Message();
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.arg1) {
            // 1代表分享成功，2代表分享失败，3代表分享取消
            case 1:
                // 成功
                AppUtility.showToastMsg("分享成功");
                if (App.getInstance().isClient() && shareApp) {
                    //回调 积分
                    Call<ResultBase> call = App.getInstance().getApiService().shareCallBack(App.getInstance().getCurrentUserNum());
                    call.enqueue(new Callback<ResultBase>() {
                        @Override
                        public void onResponse(Call<ResultBase> call, Response<ResultBase> response) {

                            App.getInstance().beanCacheHelper.getAccountMoney();
                        }

                        @Override
                        public void onFailure(Call<ResultBase> call, Throwable t) {

                        }
                    });
                }
//                dismiss();
                break;
            case 2:
                // 失败
                AppUtility.showToastMsg("分享失败");
                break;
            case 3:
                // 分享取消
                AppUtility.showToastMsg("分享取消");
                break;
        }
        return false;
    }

    public String getShareTitle() {
        return mShareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.mShareTitle = shareTitle;
    }

    public String getShareText() {
        return mShareText;
    }

    public void setShareText(String shareText) {
        this.mShareText = shareText;
    }

    public String getShareImage() {
        return mShareImage;
    }

    public void setShareImage(String shareImage) {
        this.mShareImage = shareImage;
    }

    public String getShareUrl() {
        return mShareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.mShareUrl = shareUrl;
    }

    public boolean isShareApp() {
        return shareApp;
    }

    public void setShareApp(boolean shareApp) {
        this.shareApp = shareApp;
    }
}

