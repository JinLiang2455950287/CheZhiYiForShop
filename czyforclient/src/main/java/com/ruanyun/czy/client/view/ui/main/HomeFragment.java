package com.ruanyun.czy.client.view.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.BaseFragment;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.HomeIconInfo;
import com.ruanyun.chezhiyi.commonlib.model.HxUser;
import com.ruanyun.chezhiyi.commonlib.model.RecommendInfo;
import com.ruanyun.chezhiyi.commonlib.model.StoreInfo;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.params.RecommentParams;
import com.ruanyun.chezhiyi.commonlib.presenter.StoreInfoPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.ImageUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.StoreInfoMvpView;
import com.ruanyun.chezhiyi.commonlib.view.adapter.HomeRecommendAdapter;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.BaiduLocationActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.CaseLibActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.RecommendListActivity;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WebViewActivity;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.HomeFunctionAdapter;
import com.ruanyun.czy.client.view.ui.my.MakeAppointmentActivity;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;

/**
 * 客户端  --   首页
 * Created by ycw on 2016/8/23.
 */
public class HomeFragment extends /*Refresh*/BaseFragment implements StoreInfoMvpView {

    @BindView(R.id.iv_logo)
    ImageView ivLogo;           //店铺的logo
    @BindView(R.id.iv_shop_bg)
    ImageView ivShopBg;           //店铺的logo
    @BindView(R.id.tv_title)
    TextView tvTitle;           //店铺名
    @BindView(R.id.tv_is_support_order)
    TextView tvIsSupportOrder;  //是否支持预约
    @BindView(R.id.tv_shop_hours)
    TextView tvShopHours;       //营业时间
    @BindView(R.id.tv_location)
    TextView tvLocation;        //位置
    @BindView(R.id.iv_phone)
    ImageView ivPhone;          //拨打电话
    @BindView(R.id.tv_all_project)
    TextView tvAllProject;//推荐项目
    @BindView(R.id.iv_recommend)
    ImageView ivRecommend;// 推荐项目的图标
    @BindView(R.id.recyclerView_home_recommend)
    RecyclerView recyclerViewHomeRecommend;//推荐商品列表
    @BindView(R.id.refreshlayout)
    BGARefreshLayout refreshlayout;//
    @BindView(R.id.recyclerView_function)
    RecyclerView recyclerViewFunction;//首页的四个按钮

    StoreInfoPresenter storeInfoPresenter = new StoreInfoPresenter();
    HomeRecommendAdapter homeRecommendAdapter;
    RecommentParams params = new RecommentParams();
    StoreInfo storeInfo;

//    @Override
//    public Call loadData() {
//        return null;
//    }
//
//    @Override
//    public void onRefreshResult(PageInfoBase result, String tag) {
//    }
//
//    @Override
//    public void onLoadMoreResult(PageInfoBase result, String tag) {
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogX.e(TAG, "onCreateView: HomeFragment     -----    MainBaseActivity----");
        mContentView = LayoutInflater.from(mContext).inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, mContentView);
//        initRefreshLayout();
        storeInfoPresenter.attachView(this);
        return mContentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogX.e(TAG, "onActivityCreated: HomeFragment   --------      MainBaseActivity  -----");
        initView();

    }

    /**
     * 请求数据
     */
    private void initUIData() {
        //店铺信息
        Call<ResultBase<StoreInfo>> call = app.getApiService().storeInfo(app.getCurrentUserNum(), app.storeNum);
        storeInfoPresenter.getStoreInfo(call);

        //获取推荐项目
        params.setRecommentProjectType(1);
//        params.setPageNum(currentPage);
        storeInfoPresenter.getRecommendList(app.getApiService().getRecommendList(app.getCurrentUserNum(), params));
    }

    private void initView() {
        List<HomeIconInfo> iconInfoList = DbHelper.getInstance().getHomeIconInfoList(C.ModuleType.MODULE_TYPE_CLIENT_HOME);
        if (iconInfoList.size() == 0) return;
        int endPosition = iconInfoList.size() - 1;
        List<HomeIconInfo> linkIcon = iconInfoList.subList(0, endPosition);
        //初始化首页功能图标
        getHomeFunctionRecycler(linkIcon);

        //推荐项目
        tvAllProject.setText(iconInfoList.get(endPosition).getHomeIconName());
        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(iconInfoList.get(endPosition).getAndroidPic()), ivRecommend);
        //推荐项目
        GridLayoutManager homeManager = new GridLayoutManager(mContext, 1);
        recyclerViewHomeRecommend.setLayoutManager(homeManager);
        homeRecommendAdapter = new HomeRecommendAdapter(mContext, R.layout.list_item_project, new ArrayList<RecommendInfo>());
        recyclerViewHomeRecommend.setAdapter(homeRecommendAdapter);
        homeRecommendAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                RecommendInfo recommendInfo = (RecommendInfo) o;
                double payPrice = AppUtility.getPrice(recommendInfo.getGoodsType(), recommendInfo
                        .getSalePrice(), recommendInfo.getActivityPrice());
                LogX.d(TAG, "---payPrice----->\n" + payPrice);
                AppUtility.showGoodsWebView(payPrice, app.getCurrentUserNum(), recommendInfo
                        .getGoodsNum(), recommendInfo.getGoodsType(), recommendInfo.getGoodsNum()
                        , app.getCurrentUserNum(), mContext, recommendInfo.getTitle(),
                        recommendInfo.getRecommentProjectNum(), recommendInfo.getMainPhoto(), recommendInfo.getTitle() );
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
        initUIData();
    }


    /**
     *  初始化功能图标
     * @param linkIcon 数据库获取的图标集合
     */
    private void getHomeFunctionRecycler(List<HomeIconInfo> linkIcon) {
        GridLayoutManager manager = new GridLayoutManager(mContext, 4);
        recyclerViewFunction.setLayoutManager(manager);
        HomeFunctionAdapter adapter = new HomeFunctionAdapter(mContext, R.layout.item_client_my_rv_function,  linkIcon);
        recyclerViewFunction.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
//                LogX.d(TAG, "homeItemClick--------->" + position + "adapter-------->" + adapter.getDatas().get(position).toString());
//                LogX.d(TAG, "homeItemClick--------->" + position + "obj-------->" + ((HomeIconInfo) o).toString());
                onFunctionClick(((HomeIconInfo) o).getModuleNum());
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, Object o, int position) {
                return false;
            }
        });
    }

    /**
     * 功能图标点击事件
     *
     * @param moduleNum
     */
    public void onFunctionClick(String moduleNum) {
        switch (moduleNum) {
            case "1"://门店详情
                String url = String.format(FileUtil.getFileUrl(C.ApiUrl.WEB_URL_STORE), app.getCurrentUserNum(), app.storeNum);
                Intent intent = AppUtility.getWebIntent(mContext, url, WebViewActivity.MD);
                showActivity(intent);
                break;

            case "2"://案例展示
                showActivity(CaseLibActivity.class);
                break;

            case "3"://在线预约
                showActivity(MakeAppointmentActivity.class);
                break;

            case "4"://小秘书
                if (app.getStoreInfo()!= null && AppUtility.isNotEmpty(app.getStoreInfo().getUserNumSecretary()) ) {
                    HxUser hxUser = DbHelper.getInstance().getUserByNum(app.getStoreInfo().getUserNumSecretary());
                    if (hxUser == null) {
                        storeInfoPresenter.getFriendShipInfo(app.getHxApiService().getFriendShipInfo(App.getInstance().getCurrentUserNum(), app.getStoreInfo().getUserNumSecretary()));
                    } else { //存在小秘书账号  跳转到聊天
                        showActivity(AppUtility.getChatIntent(mContext, app.getStoreInfo().getUserNumSecretary()));
                    }
                } else {
                    AppUtility.showToastMsg("商家没有小秘书");
                }
                break;
        }
    }


//    /**
//     * 取到小秘书的信息后
//     * @param event
//     */
//    @Subscribe(threadMode = ThreadMode.MainThread, sticky = true, priority = 100)
//    public void getUserSecretary(Event<User> event) {
//        if ( event.key.equals(C.EventKey.KEY_USER_SECRETARY) ) {
//            User user = event.value;
//            HxUser hxUser = new HxUser();
//            hxUser.setUserNick(user.getNickName());
//            hxUser.setUserNum(user.getUserNum());
//            hxUser.setUserPhoto(user.getUserPhoto());
//            showActivity(AppUtility.getChatIntent(mContext, hxUser.getUserNum()));
//            DbHelper.getInstance().insertHxUser(hxUser);
//            EventBus.getDefault().removeStickyEvent(event);
//        }
//    }


    @OnClick({R.id.tv_location, R.id.iv_phone, R.id.tv_all_project})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_location://地址点击事件
               if(storeInfo!=null){
                   try {
                       Intent intent=new Intent(mContext, BaiduLocationActivity.class);
                       intent.putExtra(C.IntentKey.DESLONGTITUDE,Double.parseDouble(storeInfo.getLongitude()));
                       intent.putExtra(C.IntentKey.DESLATITUDE,Double.parseDouble(storeInfo.getLatitude()));
                       intent.putExtra(C.IntentKey.ADDRESS,storeInfo.getAddress());
                       showActivity(intent);
                   }catch (NumberFormatException e){
                       AppUtility.showToastMsg("经纬度数据异常");
                   }
               }
                break;
            case R.id.iv_phone://电话点击事件
                if(storeInfo!=null && AppUtility.isNotEmpty(storeInfo.getLinkTel())) {
                    confirmDialPhone();
                }
                break;
            case R.id.tv_all_project://更多推荐项目
                showActivity(RecommendListActivity.class);
                break;
        }
    }


    /**
     * 确认拨打电话提示
     */
    public void confirmDialPhone() {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                .setMessage(storeInfo.getLinkTel())
//                .setTitle("确认拨打电话？")
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        showActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + storeInfo.getLinkTel())));
                        dialog.dismiss();
                    }
                }).create();
                alertDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        storeInfoPresenter.detachView();
    }

    /**
     * 获取到店铺信息  显示到界面
     * @param storeInfo
     */
    @Override
    public void setHeadViewData(StoreInfo storeInfo) {
        this.storeInfo=storeInfo;
//        if(!TextUtils.isEmpty(storeInfo.getUserNumSecretary())){
//            HxUser user=new HxUser();
//            user.setUserNum(storeInfo.getUserNumSecretary());
//            user.setUserNick("小秘书");
//            DbHelper.getInstance().insertHxUser(user);
//        }
        app.setStoreInfo(storeInfo);
        ivPhone.setImageResource(AppUtility.isNotEmpty(storeInfo.getLinkTel()) ? R.drawable.home_white_phone : R.drawable.home_gray_phone);
        tvLocation.setText(storeInfo.getAddress());
        tvTitle.setText(storeInfo.getSotreName());
        tvIsSupportOrder.setText(storeInfo.getIsOrder() == 1 ? "支持预约" : "不支持预约");
        tvShopHours.setText(new StringBuilder().append("营业时间：").append(storeInfo.getBeginTime())
                .append("-").append(storeInfo.getEndTime()).toString());

        ImageUtil.loadImage(mContext, FileUtil.getImageUrl(storeInfo.getFigurePic()), ivLogo, R
                .drawable.default_img);
        Glide.with(mContext).load(FileUtil.getImageUrl(storeInfo.getBackgroundImg()))
                .error(R.drawable.home_client_bg).into(ivShopBg);
        LogX.d(TAG, "---bg--->" + FileUtil.getImageUrl(storeInfo.getBackgroundImg()));

    }

    /**
     * 获取   店铺推荐商品列表  成功
     * @param result
     */
    @Override
    public void getRecommendInfoOnSuccess(List<RecommendInfo> result) {
        homeRecommendAdapter.setDatas(homeRecommendAdapter.getTypeNameList(result));
        homeRecommendAdapter.notifyDataSetChanged();
    }

    @Override
    public void disMissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    /**
     * 获取小秘书信息成功
     * @param user
     */
    @Override
    public void getFriendShipInfoSuccess(User user) {
        if (user == null) return;
        HxUser hxUser = new HxUser();
        hxUser.setUserNick(user.getNickName());
        hxUser.setUserNum(user.getUserNum());
        hxUser.setUserType(user.getUserType());
        hxUser.setUserPhoto(user.getUserPhoto());
        showActivity(AppUtility.getChatIntent(mContext, hxUser.getUserNum()));
        DbHelper.getInstance().insertHxUser(hxUser);
    }
}
