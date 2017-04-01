package com.ruanyun.chezhiyi.commonlib.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.ChatActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.PersonInfoActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.ui.UserProfileActivity;
import com.ruanyun.chezhiyi.commonlib.model.GoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.IGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.AccountParams;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.ArticleCaseDetailActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.PayOnlineActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.ShowBigImageActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WebViewActivity;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.sharesdk.onekeyshare.OnekeyShare;


public class AppUtility {

    @SuppressWarnings("unused")
    private static final String TAG = "AppUtility";
    private static Context context;
    //private static Toast toast;
    private static Toast toast;

    public static void setContext(Application app) {
        context = app.getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }


    public static boolean isNotEmpty(String str) {
        if (str != null && !"".equals(str) && !"null".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("InflateParams")
    public static void showToastMsg(String msg) {
        //if (toast == null) {
        //	toast = new Toast(context);
        //}
        if (null == toast) {
            toast = new Toast(context);
        }
        LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AutoLinearLayout v = (AutoLinearLayout) inflate.inflate(R.layout.view_custom_toast, null);
        v.getBackground().setAlpha(125);
        TextView tvMessage = (TextView) v.findViewById(R.id.tv_message);
        tvMessage.setText(msg);
        AutoUtils.auto(v);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToastMsg(@StringRes int resID) {
        showToastMsg(context.getString(resID));
    }

    /**
     * 显示大图
     *
     * @param context
     * @param imagePath
     */
    public static void showBigImage(Context context, String imagePath) {
        Intent intent = new Intent(context, ShowBigImageActivity.class);
        intent.setData(Uri.parse(imagePath));
        context.startActivity(intent);
    }

    /**
     * 去 聊天页面
     *
     * @param context
     * @param tochatUser
     * @return
     */
    public static Intent getChatIntent(Context context, String tochatUser) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("userId", tochatUser);
        return intent;
    }

    /**
     * 跳转到环信个人信息页面
     *
     * @author zhangsan
     * @date 16/8/25 下午7:31
     */
    public static void goToUserProfile(Context context, @NonNull User user) {
        Intent intent = new Intent();
        if (user.getUserType() == 2) {  //查看技师资料
            intent.setClass(context, UserProfileActivity.class);
        } else {  // 查看客户资料
            intent.setClass(context, PersonInfoActivity.class);
        }
        intent.putExtra(C.IntentKey.USER_INFO, user);
        context.startActivity(intent);
    }

    /**
     * 去文章案例公用详情
     *
     * @param context
     * @param url
     * @param type
     * @return
     */
    public static Intent getArticleCaseLibDetailIntent(Context context, String url, String type) {
        Intent intent = new Intent(context, ArticleCaseDetailActivity.class);
        intent.putExtra(C.IntentKey.WEBVIEW_URL, url);
        intent.putExtra(C.IntentKey.CASE_LIB_TYPE, type);
        return intent;
    }

    /**
     * @param content Description 复制文本到剪贴板
     * @author sjj
     * @date 2016-1-20 下午4:31:46
     */
    public static void copy(String content) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //cmb.setText(content.trim());
        cmb.setPrimaryClip(ClipData.newPlainText("", content.trim()));
        showToastMsg("已复制到剪贴板");
    }


    public static void showHttpError(Throwable cause) {
        String message = "请求失败";
        if (cause != null) {
            if (cause.toString().contains("java.net.SocketTimeoutException")) {
                message = "服务器未响应,请稍后重试";
            } else if (cause.toString().contains("java.net.ConnectException")) {
                message = "请检查网络连接！";
            }
        }
        showToastMsg(message);
    }

    public static <T> void checkNull(T t) {
        if (null == t) {
            return;
        }
    }

    /***
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isNetworkAvailable(/*Context context*/) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
        } else {// 如果仅仅是用来判断网络连接
            // 则可以使用 cm.getActiveNetworkInfo().isAvailable();

            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return "v" + version;
        } catch (Exception e) {
            e.printStackTrace();
            return "无";
        }
    }


    /***
     * 获取当前日期与时间
     *
     * @param
     * @return
     */
    public static String getCurrentDateAndTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sDateFormat.format(new Date());
        return date;

    }

    public static String getCurrentDate() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new Date());
        return date;

    }

    public static String getCurrentTime(String formdate) {
        return new SimpleDateFormat(formdate, Locale.CHINA).format(new Date());
    }

    public static String getCurrentTime() {
        return getCurrentTime("MM-dd HH:mm");
    }

    /**
     * 日期格式字符串转换成时间戳 (毫秒)
     *
     * @param dateStr 字符串日期
     * @param format  如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName：应用包名
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String pkName = packageInfos.get(i).packageName;
                if (pkName.equals(packageName)) return true;
            }
        }
        return false;
    }


    static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     */
    static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * drawable 转 bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();

        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 获取跳转webview页面的intent
     *
     * @author jery
     * @date 2016/5/31 11:37
     */
    public static Intent getWebIntent(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(C.IntentKey.WEB_URL, url);
        return intent;
    }

    public static Intent getWebIntent(Context context, String url, String title) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(C.IntentKey.WEB_URL, url);
        intent.putExtra(C.IntentKey.TITLE, title);
        return intent;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    // 获取屏幕的大小
    public static Screen getScreenPix(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return new Screen(dm.widthPixels, dm.heightPixels);
    }

    public static class Screen {

        public int widthPixels;
        public int heightPixels;

        public Screen() {

        }

        public Screen(int widthPixels, int heightPixels) {
            this.widthPixels = widthPixels;
            this.heightPixels = heightPixels;
        }

        @Override
        public String toString() {
            return "(" + widthPixels + "," + heightPixels + ")";
        }

    }


    /**
     * 设置ListView高度
     */
    public static void measuredListHeight(ListView mListView) {
        int listViewHeight = 0;
        int adaptCount = mListView.getAdapter().getCount();
        for (int i = 0; i < adaptCount; i++) {
            View temp = mListView.getAdapter().getView(i, null, mListView);
            temp.measure(0, 0);
            listViewHeight += temp.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = mListView.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = listViewHeight;
        mListView.setLayoutParams(layoutParams);
    }

    /**
     * 修复inputmethod 内存泄漏 在activity ondestroy调用
     *
     * @author zhangsan
     * @date 16/7/9 上午10:00
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0; i < arr.length; i++) {
            String param = arr[i];
            try {
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
//                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
//                        if (QLog.isColorLevel()) {
//                            QLog.d(ReflecterHelper.class.getSimpleName(), QLog.CLR, "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);
//                        }
                        break;
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    /**
     * 根据用户生日计算年龄
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            return -1;
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }


    public static void showShare(Context showContext, String url) {
        //ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();

        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        //oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        //oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        //oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(showContext);
    }


    /**
     * SpannableString 类型的字符串  金额数字放大
     *
     * @param mContext
     * @param split
     * @param src
     * @param isLeft
     * @return SpannableString 类型的字符串
     */
    public static SpannableString getSpannerString(Context mContext, String split, String src, boolean isLeft) {
        SpannableString spannableString = new SpannableString(src);
        int index = src.indexOf(split);
        if (index == -1) return spannableString;
        if (isLeft) {
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.text_orange)), 0, index, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(1.3f), 1, index, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        } else {
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.text_orange)), index, src.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            spannableString.setSpan(new RelativeSizeSpan(1.3f), index + 1, src.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return spannableString;
    }


    /**
     * 获取商品的类型
     *
     * @param goodsType 包含 cp_01 cp, zc, tg, 等
     * @return 只有 CP,ZC,TG, 等 大的类型
     */
    public static String getTypeName(String goodsType) {
        if (TextUtils.isEmpty(goodsType)) {
            return "CP";
        }
        int index = goodsType.indexOf("_");
        if (index > 0) {
            goodsType = goodsType.substring(0, index);
        }
        return goodsType;
    }

//    /**
//     * 众筹
//     *
//     * @param context
//     * @param crowdFundingInfo
//     */
//    public static void toSubmit(Context context, CrowdFundingInfo crowdFundingInfo) {
//        toSubmit(context, crowdFundingInfo.getCrowdNum(), crowdFundingInfo.getGoodsNum(),
//                crowdFundingInfo.getGoodsType(), crowdFundingInfo.getTitle(),
//                crowdFundingInfo.getActivityPrice(), crowdFundingInfo.getProjectNum(), crowdFundingInfo.getMainPhoto());
//    }


//    public static void toSubmit(Context context, PromotionInfo promotionInfo) {
//        toSubmit(context, promotionInfo.getPromotionInfoNum(), promotionInfo.getGoodsNum(),
//                promotionInfo.getGoodsType(), promotionInfo.getTitle(),
//                promotionInfo.getActivityPrice(), promotionInfo.getProjectNum(), promotionInfo.getMainPhoto());
//    }


//    public static void toSubmit(Context context, ProductInfo productInfo) {
//        toSubmit(context, productInfo.getGoodsNum(), productInfo.getGoodsNum(),
//                productInfo.getGoodsType(), productInfo.getGoodsName(),
//                productInfo.getSalePrice(), productInfo.getProjectParent(), productInfo.getMainPhoto());
//    }


//    public static void toSubmit(Context context, SeckillDetailInfo seckillDetailInfo) {
//        toSubmit(context, seckillDetailInfo.getSeckillDetailNum(), seckillDetailInfo.getGoodsNum(),
//                seckillDetailInfo.getGoodsType(), seckillDetailInfo.getTitle(),
//                seckillDetailInfo.getActivityPrice(), seckillDetailInfo.getProjectNum(), seckillDetailInfo.getMainPhoto());
//    }


//    /**
//     * @param context
//     * @param info
//     */
//    public static void toSubmit(Context context, IGoodsInfo info) {
//        toSubmit(context, info.getCommonNum(), info.getGoodsNum(),
//                info.getGoodsType(), info.getGoodsName(),
//                info.getActivityPrice(), info.getProjectNum(), info.getMainPhoto());
//
//            toSubmit(context, new GoodsInfo(info.getCommonNum(), info.getGoodsNum(),
//                    getTypeName(info.getGoodsType()), info.getGoodsName(),
//                    info.getActivityPrice(), info.getProjectNum(), info.getMainPhoto()));
//    }


//    /**
//     * d
//     *
//     * @param commonNum
//     * @param goodsNum
//     * @param goodsType
//     * @param goodsName
//     * @param salePrice 真实的销售价
//     * @param mainPhoto
//     */
//    public static void toSubmit(Context mContext,
//            String commonNum,
//            String goodsNum,
//            String goodsType,
//            String goodsName,
//            double salePrice,
//            String projectNum,
//            String mainPhoto) {
//        goodsType = getTypeName(goodsType);
//        toSubmit(mContext, new GoodsInfo(commonNum, goodsNum, goodsType, goodsName, salePrice, projectNum, mainPhoto));
//
//    }


    public static void toSubmit(Context mContext, IGoodsInfo goodsInfo) {
        Intent intent = getSubmitIntent(mContext, goodsInfo);
        mContext.startActivity(intent);
    }

    public static Intent getSubmitIntent(Context mContext, IGoodsInfo goodsInfo) {
        Intent intent = new Intent();
        intent.setClassName(mContext, "com.ruanyun.czy.client.view.ui.my.SubmitOrderActivity");
        intent.putExtra(C.IntentKey.GOODS_INFO, goodsInfo);
        return intent;
    }


    /**
     * 去支付界面 的  intent
     *
     * @param orderInfo
     * @param mContext
     */
    public static Intent getPayIntent(OrderInfo orderInfo, Context mContext) {
        Intent intent = new Intent(mContext, PayOnlineActivity.class);
        intent.putExtra(C.IntentKey.ORDER_INFO, orderInfo);
        // 如果是充值订单  需要
        if (orderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_RECHARGE))
            intent.putExtra(C.IntentKey.PAY_PAGE_TYPE, PayOnlineActivity.PAGE_TYPE_RECHARGE);

        int type = AccountParams.PAY_TYPE_SHOP;
        if (orderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_DJ)) {  //定金
            type = AccountParams.PAY_TYPE_DJ;
        } else if (orderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_HD)) {  //活动
            type = AccountParams.PAY_TYPE_APPLY;
        } else if (orderInfo.getOrderType().equals(C.OrderType.ORDER_TYPE_GD)) {  //工单
            type = AccountParams.PAY_TYPE_WORKINFO;
        }
        //支付类型  //  3 商城购物消费 4 支付预约定金消费 5 活动报名消费 6 工单结算消费
        intent.putExtra(C.IntentKey.RECORD_TYPE, type);
        return intent;
    }


    /**
     * 显示商品详细页
     *
     * @param payPrice       实际付款的商品单价
     * @param currentUserNum 当前用户的Num
     * @param goodsNum       商品的 num
     * @param goodsType      商品的类型  或  订单的类型
     * @param commonNum      商品的业务主键
     * @param buyUserNum     购买者的 userNum
     * @param mContext
     * @param goodsName      商品的名称
     * @param projectNum     商品所属类别编号
     * @param mainPhoto      商品的图片
     * @param commonFlag     可以购买1  不允许购买 2
     * @param subtitle
     */
    public static void showGoodsWebView(double payPrice,
                                        String currentUserNum,
                                        String goodsNum,
                                        String goodsType,
                                        String commonNum,
                                        String buyUserNum,
                                        Context mContext,
                                        String goodsName,
                                        String projectNum,
                                        String mainPhoto,
                                        String commonFlag,
                                        String subtitle) {

        Intent intent = getGoodsIntent(payPrice,
                currentUserNum,
                goodsNum,
                goodsType,
                commonNum,
                buyUserNum,
                mContext,
                goodsName,
                projectNum,
                mainPhoto,
                commonFlag,
                subtitle);
        mContext.startActivity(intent);
    }

    /**
     * 此方法默认打开的商品的页面可以购买商品
     *
     * @see #showGoodsWebView(double, String, String, String, String, String, Context, String, String, String, String, String) </br>
     */
    public static void showGoodsWebView(double payPrice,
                                        String currentUserNum,
                                        String goodsNum,
                                        String goodsType,
                                        String commonNum,
                                        String buyUserNum,
                                        Context mContext,
                                        String goodsName,
                                        String projectNum,
                                        String mainPhoto,
                                        String subtitle) {
        showGoodsWebView(payPrice,
                currentUserNum,
                goodsNum,
                goodsType,
                commonNum,
                buyUserNum,
                mContext,
                goodsName,
                projectNum,
                mainPhoto,
                "1",
                subtitle);
    }

    /**
     * @param payPrice       实际付款的商品单价
     * @param currentUserNum 当前用户的Num
     * @param goodsNum       商品的 num
     * @param goodsType      商品的类型  或  订单的类型
     * @param commonNum      商品的业务主键
     * @param buyUserNum     购买者的 userNum
     * @param mContext
     * @param goodsName      商品的名称
     * @param projectNum     商品所属类别编号
     * @param mainPhoto      商品的图片
     * @param commonFlag     可以购买1  不允许购买 2
     * @param subtitle       副标题  分享时使用
     * @return
     */
    public static Intent getGoodsIntent(double payPrice,
                                        String currentUserNum,
                                        String goodsNum,
                                        String goodsType,
                                        String commonNum,
                                        String buyUserNum,
                                        Context mContext,
                                        String goodsName,
                                        String projectNum,
                                        String mainPhoto,
                                        String commonFlag,
                                        String subtitle) {
        String goodsJsonString = getGoodsJsonString(goodsNum,
                payPrice,
                goodsType,
                commonNum,
                goodsName,
                projectNum,
                mainPhoto,
                commonFlag,
                subtitle);

        String url = AppUtility.getGoodsUrlString(currentUserNum, buyUserNum, goodsJsonString);
        LogX.d("WebViewActivity", "------点击-------> \n" + url);
        Intent goodsWebIntent = AppUtility.getWebIntent(mContext, url, WebViewActivity.CP);
        goodsWebIntent.putExtra(C.IntentKey.GOODS_JSON, goodsJsonString);
        goodsWebIntent.putExtra(C.IntentKey.SEND_TO_FRIEND, true);

        goodsWebIntent.putExtra(C.IntentKey.SHARE_CP_NAME, goodsName);
        goodsWebIntent.putExtra(C.IntentKey.SHARE_CP_IMAGE, mainPhoto);
        goodsWebIntent.putExtra(C.IntentKey.SHARE_CP_SUBTITLE, subtitle);

        return goodsWebIntent;
    }


    /**
     * @param currentUserNum
     * @param buyUserNum      购买者编号
     * @param goodsJsonString 加密前的商品的   jsonstring
     * @return
     */
    public static String getGoodsUrlString(String currentUserNum, String buyUserNum, String goodsJsonString) {
        String url = String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_GOODSINFO),
                currentUserNum,
                encodeJsonArrayString(goodsJsonString),
                buyUserNum);
        LogX.d(TAG, url);
        return url;
    }


    /**
     * 商品详细页  参数加密
     *
     * @param goodsJsonString
     * @return
     */
    public static String encodeJsonArrayString(String goodsJsonString) {
        String jsonString = goodsJsonString;
        LogX.d(TAG, "------->\n" + jsonString);
        jsonString = RSAUtils.encodeKey(jsonString);
        LogX.d(TAG, "------->\n" + jsonString);
        jsonString = jsonString.replaceAll("/", "@");
        return jsonString;
    }

    /**
     * @param goodsNum
     * @param activityPrice
     * @param orderType
     * @param commonNum
     * @param goodsName
     * @param projectNum
     * @param mainPhoto
     * @param commonFlag
     * @param viceTitle
     * @return 商品的   jsonstring
     */
    public static String getGoodsJsonString(String goodsNum,
                                            double activityPrice,
                                            String orderType,
                                            String commonNum,
                                            String goodsName,
                                            String projectNum,
                                            String mainPhoto,
                                            String commonFlag,
                                            String viceTitle) {
        orderType = AppUtility.getTypeName(orderType);
        GoodsInfo goodsInfo = new GoodsInfo(commonNum,
                goodsNum,
                orderType,
                TextUtils.isEmpty(goodsName) ? "" : Base64.encode(goodsName.getBytes()),
                activityPrice,
                projectNum,
                mainPhoto,
                commonFlag,
                TextUtils.isEmpty(viceTitle) ? "" : Base64.encode(viceTitle.getBytes()));
        Gson gson = new Gson();
        String toJson = gson.toJson(goodsInfo);
        LogX.e("WebViewActivity", " AppUtility\n" + toJson);
        return toJson;
    }

    /**
     * 获取商品信息   解密
     *
     * @param jsonArrayString
     * @return
     */
    public static String deCodePayString(String jsonArrayString) {
        LogX.d(TAG, "---jsonArrayString---->\n" + jsonArrayString);
        jsonArrayString = RSAUtils.deCodeKey(jsonArrayString);
        LogX.d(TAG, "---jsonArrayString  deCodeKey  1 ---->\n" + jsonArrayString);
        String str = null;
        try {
            str = new String(Base64.decode(jsonArrayString), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        LogX.d(TAG, "----jsonArrayString    deCodeKey  2--->\n" + str);
        return str;
    }


    /**
     * 类型判断实际购买价格
     *
     * @param goodsType
     * @param salePrice
     * @param activityPrice
     * @return
     */
    public static double getPrice(String goodsType, double salePrice, double activityPrice) {
        double price = salePrice;
        if (goodsType.equals(/*"CX"*/ C.OrderType.ORDER_TYPE_CX) || goodsType.equals(/*"MS"*/C
                .OrderType.ORDER_TYPE_MS) || goodsType.equals(C.OrderType.ORDER_TYPE_ZC)) {
            price = activityPrice;
        }
        return price;
    }

    public static GoodsInfo getGoodsInfo(String jsonString) {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, GoodsInfo.class);
    }
}
