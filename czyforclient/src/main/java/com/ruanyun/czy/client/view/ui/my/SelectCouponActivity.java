package com.ruanyun.czy.client.view.ui.my;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.view.adapter.SelectDiscountCouponAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import de.greenrobot.event.EventBus;

/**
 * Description ：根据优惠劵集合  选择优惠券
 * <p/>
 * Created by hdl on 2016/9/21.
 */
public class SelectCouponActivity extends AutoLayoutActivity
        implements Topbar.onTopbarClickListener {

    @BindView(R.id.tv_not_usable_coupon)
    TextView tvNotUsableCoupon;//无符合条件的优惠券时使用
    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.refreshlayout)
    BGARefreshLayout refreshLayout;
    @BindView(R.id.emptyview)
    RYEmptyView emptyView;

    private SelectDiscountCouponAdapter adapter;
    private List<ProjectType> stairprojectTypes;//一级工单服务分类集合
    private HashMap<String, String> stairprojectMap = new HashMap<>();//一级工单服务分类Map
    private List<OrderGoodsInfo> discountCouponList; //接收  传入的优惠劵列表
    /**  true --  工单结算选择优惠劵   false --  购买商品选择优惠劵*/
    private boolean discountCouponSelect;
    private HashMap<String, OrderGoodsInfo> selectWorkOrderCoupon = new HashMap<>();// 选择的优惠劵集合

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_product_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topbar.setTttleText("优惠券")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setRightText("确定")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);

        //接收传入的优惠劵列表
        discountCouponList = getIntent().getParcelableArrayListExtra(C.IntentKey.DISCOUNT_COUPON_LIST);
        discountCouponSelect = getIntent().getBooleanExtra(C.IntentKey.DISCOUNT_COUPON_SELECT, false);
        for (int i = 0; i < discountCouponList.size(); i++) {
            OrderGoodsInfo orderGoodsInfo = discountCouponList.get(i);
            if (orderGoodsInfo.isSelect()) {
                if (TextUtils.isEmpty(orderGoodsInfo.getWorkOrderNum())) {
                    selectWorkOrderCoupon.put("000000", orderGoodsInfo);
                } else {
                    selectWorkOrderCoupon.put(orderGoodsInfo.getWorkOrderNum(), orderGoodsInfo);
                }
            }
        }
        stairprojectTypes = new ArrayList<>();
        stairprojectTypes = DbHelper.getInstance().getSeviceTypes();//获取工单服务和技师技能数据结构集合
        for (ProjectType stairprojectType : stairprojectTypes) {
            stairprojectMap.put(stairprojectType.getProjectNum(), stairprojectType.getProjectName());
        }
        adapter = new SelectDiscountCouponAdapter(mContext, R.layout.list_item_my_coupon, discountCouponList, stairprojectMap);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderGoodsInfo info = discountCouponList.get(position);
                if (discountCouponSelect) {  //工单结算选择优惠劵
                    workOrderItemClick(info);
                } else {  //购买商品 选择优惠劵  单选优惠劵
                    goodsSubmitItemClick(info);
                }
            }
        });
    }

    /**
     * 购买商品 选择优惠劵  单选优惠劵
     * @param info
     */
    private void goodsSubmitItemClick(OrderGoodsInfo info) {
        if (info.isSelect()) {      //如果选中时  改变为未选中
            info.setSelect(false);
            selectWorkOrderCoupon.clear();
            adapter.setDatas(discountCouponList);
        } else {   //
            info.setSelect(true);
            OrderGoodsInfo goodsInfo = selectWorkOrderCoupon.get("000000");
            if (goodsInfo != null) {
                goodsInfo.setSelect(false);//之前选中优惠劵 状态改变
            }
            selectWorkOrderCoupon.put("000000", info);//保存 优惠劵
            adapter.setDatas(discountCouponList);
        }
    }

    /**
     * 工单结算选择优惠劵
     * @param info
     */
    private void workOrderItemClick(OrderGoodsInfo info) {
        if (info.isSelect()) {//如果选中时 改变为未选中
            info.setSelect(false);
            selectWorkOrderCoupon.remove(TextUtils.isEmpty(info.getWorkOrderNum()) ? "000000" : info.getWorkOrderNum());
            adapter.setDatas(discountCouponList);
        } else {  // 选择 选中
            if (AppUtility.isNotEmpty(info.getWorkOrderNum())) {     // 其他优惠劵
                if (selectWorkOrderCoupon.get("000000") != null) {      // 已经选择了通用劵
                    selectWorkOrderCoupon.get("000000").setSelect(false);//清除通用劵的选中状态
                    selectWorkOrderCoupon.clear();
                } else { // 没有选择通用劵  找出选中集合中的相同 WorkOrderNum 的优惠劵 选中状态 置 空 false
                    for (String key : selectWorkOrderCoupon.keySet()) {//遍历map的key值
                        if (key.equals(info.getWorkOrderNum())) {
                            selectWorkOrderCoupon.get(key).setSelect(false);
                        }
                    }
                }
                selectWorkOrderCoupon.put(info.getWorkOrderNum(), info);
            } else if (info.getProjectParent().equals("000000")) {    //通用券
                //遍历map 将map中的值全部修改成不选中
                for (String key : selectWorkOrderCoupon.keySet()) {
                    OrderGoodsInfo value = selectWorkOrderCoupon.get(key);
                    value.setSelect(false);
                }
                selectWorkOrderCoupon.clear();
                selectWorkOrderCoupon.put("000000", info);
            }
            //选中当前项
            info.setSelect(true);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == R.id.img_btn_left) {
            finish();
        } else if (id == R.id.tv_title_right) {
            confirmDispose();
        }
    }

    /**
     * 点击确认时 处理
     */
    private void confirmDispose() {
        if (discountCouponSelect) {
            EventBus.getDefault().post(new Event(C.EventKey.DISCOUNT_COUPON_MAP, selectWorkOrderCoupon));
        } else {
            setResult(RESULT_OK, getIntent().putExtra(C.IntentKey.DISCOUNT_COUPON_LIST, selectWorkOrderCoupon));
        }
        finish();
    }

}
