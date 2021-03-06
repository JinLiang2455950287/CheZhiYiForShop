package com.ruanyun.chezhiyi.view.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


import com.google.gson.Gson;
import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.params.ProductGroupPurchaseParams;
import com.ruanyun.chezhiyi.commonlib.model.params.WorkOrderSubmitInfo;
import com.ruanyun.chezhiyi.commonlib.presenter.InsteadOrderPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.InsteadOrderMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.ServiceSelectPopWindow;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.view.adapter.AddServiceGoodsAdapter;
import com.ruanyun.chezhiyi.view.ui.workorder.CustomerReceptionActivity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

/**
 * Description ：添加服务商品或服务项目
 * <p/>
 * Created by hdl on 2016/9/29.
 */

public class AddServiceGoodsActivity2 extends RefreshBaseActivity implements Topbar.onTopbarClickListener, ServiceSelectPopWindow.OnPopupClickListener, InsteadOrderMvpView {
    @BindView(R.id.query)
    EditText query;
    @BindView(R.id.search_clear)
    ImageButton clearSearch;
    @BindView(R.id.list)
    ListView listview;
    private Topbar topbar;
    private boolean popwindowShow = false;//popwindow是否显示
    private ServiceSelectPopWindow popWindow;
    List<ProjectType> stairprojectTypes = new ArrayList<>();//一级工单服务分类集合
    private int selectedProjectType = 0;//选中的服务项目
    private ProjectType projectType;//添加服务商品时有值，添加服务项目时不传为null
    /**
     * true为添加商品 false为添加服务项目
     */
    private boolean isAddGoods;
    /**
     * 产品集合
     */
    List<ProductInfo> mProductList = new ArrayList<>();
    private AddServiceGoodsAdapter adapter;
    /**
     * 获取产品列表传参
     */
    private ProductGroupPurchaseParams params = new ProductGroupPurchaseParams();

    private String workOrderNum;//订单编号代下单时使用
    InsteadOrderPresenter presenter = new InsteadOrderPresenter();
    //回传
    private String projectNum = "004000000000000";
    public HashMap<String, List<OrderGoodsInfo>> childs = new HashMap<>();
    private List<ProductInfo> productInfoHuiChuanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_add_service_goods);
        ButterKnife.bind(this);
        initRefreshLayout();
        registerBus();
        presenter.attachView(this);
        initView();
        initAdapter();
        initListener();
        refreshWithLoading();
        refreshLayout.setPullDownRefreshEnable(false);//屏蔽下拉刷新
    }

    @Override
    public Call loadData() {
        params.setPageNum(currentPage);
        return app.getApiService().getProductList(app.getCurrentUserNum(), params);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null)
            presenter.onCancel();
        unRegisterBus();
    }

    /**
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void alterProductInfoCount(Event<ProductInfo> event) {
        ProductInfo productInfoTemp = null;
        if (event != null && event.key.equals(C.EventKey.COUNT_PRODUCTINFO)) {
            ProductInfo productInfo = event.value;
            LogX.e("1522productInfo", productInfo.toString());
            for (int i = 0; i < productInfoHuiChuanList.size(); i++) {
                productInfoTemp = productInfoHuiChuanList.get(i);
                if (productInfoTemp.getGoodsNum().equals(productInfo.getGoodsNum())) {
                    productInfoTemp.setGoodsCount(productInfoTemp.getGoodsCount());
                    productInfoTemp = null;
                    break;
                } else {
                    productInfoTemp = productInfoHuiChuanList.get(i);
                }
            }

            if (productInfoTemp != null) {
                productInfoHuiChuanList.add(productInfo);
            }
            if (productInfoHuiChuanList.size() == 0) {
                productInfoHuiChuanList.add(productInfo);
            }

            for (ProductInfo info : mProductList) {
                if (info.getGoodsNum().equals(productInfo.getGoodsNum())) {
                    info.setGoodsCount(productInfo.getGoodsCount());
                    break;
                }
            }
            notifyFiltrateData(query.getText());

//            LogX.e("1522productInfoTemp", productInfoTemp.toString());
            LogX.e("1522回传", productInfo.getGoodsCount() + ";" + productInfoHuiChuanList.size() + ";" + productInfoHuiChuanList.toString());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            boolean hideInputResult = isShouldHideInput(v, ev);
            if (hideInputResult) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) this
                        .getSystemService(INPUT_METHOD_SERVICE);
                if (v != null) {
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            //之前一直不成功的原因是,getX获取的是相对父视图的坐标,getRawX获取的才是相对屏幕原点的坐标！！！
            if (event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void initView() {
        projectType = getIntent().getParcelableExtra(C.IntentKey.PROJECTTYPE_INFO);
        workOrderNum = getIntent().getStringExtra(C.IntentKey.WORKORDER_NUM);
        if (projectType != null) isAddGoods = true;//projectType不为空时，为添加服务商品
        topbar = getView(R.id.topbar);
        topbar.setBackBtnEnable(true)
                .onBackBtnClick()
                .setRightText("确认")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);
        if (isAddGoods) {
            topbar.setTttleText(projectType.getProjectName());
            params.setProjectParent(projectType.getProjectNum());
        } else {
            stairprojectTypes = DbHelper.getInstance().getSeviceTypes();//获取工单服务和技师技能数据结构一级
            LogX.e("返回get", stairprojectTypes.toString());
            topbar.onTopbarViewClick(topbar.getTvTitle());
            if (stairprojectTypes != null && stairprojectTypes.size() > 0) {
                if (stairprojectTypes.get(0).getProjectName() != null) {
                    topbar.setTttleText(stairprojectTypes.get(0).getProjectName());
                    params.setProjectParent(stairprojectTypes.get(0).getProjectNum());
                }
            }
            LogX.e("服务工单", stairprojectTypes.toString());
            popWindow = new ServiceSelectPopWindow(mContext, getView(R.id.topbar), stairprojectTypes, selectedProjectType);
            popWindow.setOnPopupClickListener(this);
            setTitleDrawable(R.drawable.icon_arrow_white_down, false);
        }
        params.setGoodsTypeString(params.WORKORDER_ADD_GOODS);


    }

    private void initAdapter() {
        adapter = new AddServiceGoodsAdapter(mContext, R.layout.list_item_add_service_goods, mProductList);
        listview.setAdapter(adapter);
    }

    private void initListener() {
        query.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                notifyFiltrateData(s);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        clearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query.getText().clear();
            }
        });
    }


    /**
     * 刷新筛选数据
     *
     * @param s
     */
    private void notifyFiltrateData(CharSequence s) {
        if (TextUtils.isEmpty(s)) {
            clearSearch.setVisibility(View.INVISIBLE);
            adapter.setData(mProductList);
        } else {
            clearSearch.setVisibility(View.VISIBLE);
            adapter.setData(adapter.getSearchResult(mProductList, s.toString().trim()));
        }
    }


    /**
     * topbar 监听
     *
     * @param v
     */
    @Override
    public void onTobbarViewClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_left:
                Intent intent2 = new Intent();
                intent2.putExtra("1522", (Serializable) productInfoHuiChuanList);
                setResult(1522, intent2);
                finish();
                break;
            case R.id.tv_topbar_title:
                if (popwindowShow) {
//                 popwindowInvisible();//点击topbar消失
                } else {
                    setTitleDrawable(R.drawable.icon_arrow_white_up, true);
                }
                break;
            case R.id.tv_title_right:
//                if (isAddGoods) {

                Intent intent = new Intent();

                intent.putExtra("1522", (Serializable) productInfoHuiChuanList);
                setResult(1522, intent);
                finish();
//                    LogX.e("代下单", "isAddGoods");
//                    if (workOrderNum == null) {//添加服务商品
//                        addServiceGoods(CustomerReceptionActivity.REQ_ADD_GOODS);
//                        LogX.e("代下单", "添加服务商品");
//                    } else {//代下单
////                        saveInsteadOrder();
//                        LogX.e("代下单", "//代下单");
//                    }
//                } else {//添加服务项目
//                    addServiceProject();
//                    LogX.e("代下单", "添加服务项目");
//                }
//                break;
        }
    }

    /*返回键的监听*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent2 = new Intent();
            intent2.putExtra("1522", (Serializable) productInfoHuiChuanList);
            setResult(1522, intent2);
            finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    /**
     * 添加服务商品
     */
    private void addServiceGoods(int requestCode) {
        List<ProductInfo> productList = new ArrayList<>();
        for (ProductInfo info : mProductList) {
            if (info.getGoodsCount() > 0) {
                productList.add(info);
            }
        }
        Event<List<ProductInfo>> result = new Event<>();
        result.value = productList;
        result.keyInt = requestCode;
        result.key = C.EventKey.ADD_PROJECT;
        EventBus.getDefault().post(result);
        finish();
    }


    /**
     * 将添加的产品转成商品集合json
     *
     * @return
     */
    private String getProductInfoListJsonStr() {
        List<WorkOrderSubmitInfo.WorkOrderGoods> WorkOrderGoodsList = new ArrayList<>();
        WorkOrderSubmitInfo.WorkOrderGoods WorkOrderGoods;
        for (ProductInfo info : mProductList) {
            if (info.getGoodsCount() > 0) {
                WorkOrderGoods = new WorkOrderSubmitInfo.WorkOrderGoods(info.getGoodsNum(),
                        info.getGoodsName(), "",
                        String.valueOf(info.getSalePrice()),
                        info.getGoodsCount(),
                        info.getMainPhoto(),
                        info.getSgtcje() == null ? "0" : info.getSgtcje().toString(),
                        info.getXstcje() == null ? "0" : info.getXstcje().toString());
                WorkOrderGoodsList.add(WorkOrderGoods);
            }
        }
        if (WorkOrderGoodsList.size() > 0) {//选择了商品
            Gson gson = new Gson();
            String stData = gson.toJson(WorkOrderGoodsList);
            return stData;
        } else {//没选择商品
            return null;
        }

    }

    /**
     * 添加服务项目
     */
    private void addServiceProject() {
        addServiceGoods(CustomerReceptionActivity.REQ_ADD_SERVICE);
    }

    /**
     * popwindow隐藏
     */
    private void popwindowInvisible() {
        setTitleDrawable(R.drawable.icon_arrow_white_down, false);
    }

    private void setTitleDrawable(int drawableId, boolean isShow) {
        Drawable drawable = getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        topbar.getTvTitle().setCompoundDrawables(null, null, drawable, null);
        topbar.getTvTitle().setCompoundDrawablePadding(20);
        popWindow.show(isShow);
        popwindowShow = isShow;
    }

    /**
     * popwindow回调
     *
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        LogX.e("服务工单", position + "");
        if (position == -1) {
            popwindowInvisible();
        } else {
            popwindowInvisible();
            topbar.setTttleText(stairprojectTypes.get(position).getProjectName());
            params.setProjectParent(stairprojectTypes.get(position).getProjectNum());
            selectedProjectType = position;
            popWindow.setSelectedProjectType(selectedProjectType);
            refreshWithLoading();
        }
    }

    @Override
    public void showLoadingView() {
        showLoading();
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showInsteadOrderTip(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showInsteadOrderSuccess(ResultBase resultBase) {
        //AppUtility.showToastMsg("下单成功");
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        totalPage = result.getTotalPage();
        mProductList = result.getResult();
        adapter.setData(mProductList);
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        mProductList.addAll(result.getResult());
        adapter.setData(mProductList);
    }
}
