package com.ruanyun.chezhiyi.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.model.CustomerRepUiModel;
import com.ruanyun.chezhiyi.commonlib.model.OrderGoodsInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProductInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.model.WorkBayInfo;
import com.ruanyun.chezhiyi.commonlib.model.WorkOrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.WorkOrderSubmitInfo;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.view.ui.home.AddServiceGoodsActivity;
import com.ruanyun.chezhiyi.view.widget.ChooseServiceTab;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Description:客户接待选择服务 工位 商品 列表adpater
 * author: zhangsan on 16/10/8 上午8:21.
 */
public class CustomerRepAdapter extends MultiItemTypeAdapter<CustomerRepUiModel> {

    private ChooseServiceTab.onTabClickListener tabclickListener;
    //  private SparseArray<CustomerRepUiModel> uiModeListCache = new SparseArray<>();
    /**
     * 已购商品缓存
     **/
    private HashMap<String, List<CustomerRepUiModel>> selectGoodsCache = new HashMap<>();
    /**
     * 新增商品缓存
     */
    // private HashMap<String, List<CustomerRepUiModel>> newGoodsCache = new HashMap<>();
    /**
     * 选中工位或技师缓存
     **/
    private HashMap<String, CustomerRepUiModel> selectWorkbayCache = new HashMap<>();
    private HashMap<String, CustomerRepUiModel> selectTechnicianCache = new HashMap<>();
    /**
     * 切换标签, 添加按钮, emptyview uimodel
     **/
    private CustomerRepUiModel tabUIModel, addServiceUIModel, emptyViewUIModel;
    private CustomerAddNewServiceDelegate addNewServiceDelegate = new CustomerAddNewServiceDelegate();
    //private CustomerRepProjectTypeDelegate ta=new CustomerRepProjectTypeDelegate();
    private CustomerRepSelTechDelegate selTechDelegate = new CustomerRepSelTechDelegate();
    private CustomerSelWorkStationDelegate selWorkStationDelegate = new CustomerSelWorkStationDelegate();

    public CustomerRepAdapter(Context context, List<CustomerRepUiModel> datas) {
        super(context, datas);
        tabUIModel = new CustomerRepUiModel();
        tabUIModel.setItemType(CustomerRepUiModel.TYPE_TAB);
        addServiceUIModel = new CustomerRepUiModel();
        addServiceUIModel.setItemType(CustomerRepUiModel.TYPE_BUTTON);
        emptyViewUIModel = new CustomerRepUiModel();
        emptyViewUIModel.setItemType(CustomerRepUiModel.TYPE_EMPTY_VIEW);
        addItemViewDelegate(new CustomerRepProjectTypeDelegate());
        addItemViewDelegate(new CustomerRepTabDelegate());
        addItemViewDelegate(new CustomerEmptyViewDelegate());
        addItemViewDelegate(addNewServiceDelegate);
        addItemViewDelegate(selWorkStationDelegate);
        addItemViewDelegate(selTechDelegate);
        addItemViewDelegate(new CustomerGoodsSelectDelegate());
    }

    /**
     * 设置切换标签item点击事件
     *
     * @author zhangsan
     * @date 16/10/11 下午5:03
     */
    public void setTabclickListener(ChooseServiceTab.onTabClickListener tabclickListener) {
        this.tabclickListener = tabclickListener;
    }

    /**
     * 根据viewType获取列表中第一次出现该type的 索引
     *
     * @author zhangsan
     * @date 16/10/15 下午3:24
     */
    public int getFirstIndexOfType(int viewType) {
        int index = 0;
        for (int i = 0, size = mDatas.size(); i < size; i++) {
            if (mDatas.get(i).getItemType() == viewType) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * 根据viewType获取列表中最后出现该type的 索引
     *
     * @author zhangsan
     * @date 16/10/15 下午3:24
     */
    public int getLastIndexOfType(int viewType) {
        int index = 0;
        for (int i = 0, size = mDatas.size(); i < size; i++) {
            if (mDatas.get(i).getItemType() == viewType) {
                index = i;
            }
        }
        return index;
    }

    /**
     * 获取展开item第一次出现的索引
     *
     * @author zhangsan
     * @date 16/10/26 上午11:25
     */
    public int getFirstIndexOfExpandItem() {
        int index = 0;
        for (int i = 0, size = mDatas.size(); i < size; i++) {
            CustomerRepUiModel customerRepUiModel = mDatas.get(i);
            if (customerRepUiModel.getItemType() == CustomerRepUiModel.TYPE_TAB) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * 移除列表中不是服务项item //收起所有展开的服务项 返回删除数量
     *
     * @author zhangsan
     * @date 16/10/11 下午3:08
     */
    public int removeExpandItems(String projectNum) {
        Iterator<CustomerRepUiModel> iterator = mDatas.iterator();
        int removeCount = 0;
        while (iterator.hasNext()) {
            CustomerRepUiModel customerRepUiModel = iterator.next();
            if (customerRepUiModel.getItemType() == CustomerRepUiModel.TYPE_PROJECT_TYPE &&
                    !customerRepUiModel.getItemNum().equals(projectNum)) {
                customerRepUiModel.setSelected(false);
            } else if (customerRepUiModel.getItemType() != CustomerRepUiModel.TYPE_PROJECT_TYPE) {
                iterator.remove();
                removeCount++;
            }

        }
        return removeCount;
    }

    /**
     * 删除除了projecttype  和切换标签以外的itemtype
     *
     * @author zhangsan
     * @date 16/10/15 下午3:20
     */
    public int removeItemsExceptTab() {
        Iterator<CustomerRepUiModel> iterator = mDatas.iterator();
        int removeCount = 0;
        while (iterator.hasNext()) {
            CustomerRepUiModel customerRepUiModel = iterator.next();
            if (customerRepUiModel.getItemType() == CustomerRepUiModel.TYPE_PROJECT_TYPE ||
                    customerRepUiModel.getItemType() == CustomerRepUiModel.TYPE_TAB) {
            } else {
                iterator.remove();
                removeCount++;
            }
        }
        return removeCount;
    }

    /**
     * 获取服务大项item数量
     *
     * @author zhangsan
     * @date 16/11/4 上午11:26
     */
    public int getServiceTypeItemCount() {
        int count = 0;
        for (CustomerRepUiModel model : mDatas) {
            if (model.getItemType() == CustomerRepUiModel.TYPE_PROJECT_TYPE) {
                count++;
            }
        }
        return count;
    }

    /**
     * 获取添加商品服务的uimodel
     *
     * @author zhangsan
     * @date 16/10/29 下午5:21
     */
    public CustomerRepUiModel getAddServiceUIModel(String projectNum) {
        addServiceUIModel.setItemNum(projectNum);
        return addServiceUIModel;
    }

    /**
     * 判断两个item 是否是同一个类型  对比选中缓存和服务端新加进来数据源 切换标签时判断哪个要选中
     * index=-1 表示未找到 不做选中处理
     *
     * @author zhangsan
     * @date 16/10/15 上午11:33
     */
    public int indexOfSecletModel(String projectNum, int tabType) {
        int index = -1;
        CustomerRepUiModel comparedModel = null;
        if (tabType == ChooseServiceTab.TAB_TECHNICIAN) {
            comparedModel = getSelectTechncian(projectNum);
        } else if (tabType == ChooseServiceTab.TAB_WORK_STATION) {
            comparedModel = getSelectWorkbay(projectNum);
        }
        if (null == comparedModel) {
            return -1;
        }
        for (int i = 0, size = mDatas.size(); i < size; i++) {
            //type类型 技师
            if (tabType == ChooseServiceTab.TAB_TECHNICIAN && mDatas.get(i).getItemType() == CustomerRepUiModel.TYPE_TECHNICIAN) {
                User comparedUser = (User) comparedModel.relativeBean;
                User user = (User) mDatas.get(i).relativeBean;
                if (user.getUserNum().equals(comparedUser.getUserNum())) {
                    index = i;
                    break;
                }
                //type类型 工位
            } else if (tabType == ChooseServiceTab.TAB_WORK_STATION && mDatas.get(i).getItemType() == CustomerRepUiModel.TYPE_WORK_STATION) {
                WorkBayInfo comparedWorkbay = (WorkBayInfo) comparedModel.relativeBean;
                WorkBayInfo workBayInfo = (WorkBayInfo) mDatas.get(i).relativeBean;
                if (comparedWorkbay.getWorkbayInfoNum().equals(workBayInfo.getWorkbayInfoNum())) ;
                {
                    index = i;
                    break;
                }

            }

        }
        if (tabType == ChooseServiceTab.TAB_TECHNICIAN) {
            setTechnicianSelection(index);
        } else {
            setWorkOrderSelection(index);
        }
        if (index != -1) {
            notifyItemChanged(index);
        }
        return index;
    }

    /**
     * 设置工位选中位置
     *
     * @author zhangsan
     * @date 16/11/4 下午3:54
     */
    public void setWorkOrderSelection(int positon) {
        selWorkStationDelegate.clickPositon = positon;
    }

    /**
     * 设置技师选中位置
     *
     * @author zhangsan
     * @date 16/11/4 下午3:55
     */
    public void setTechnicianSelection(int postion) {
        selTechDelegate.clickPositon = postion;
    }

    /**
     * 点击展开标签时添加切换标签 并触发上次点击的tabtype对应的点击事件
     *
     * @author zhangsan
     * @date 16/10/18 下午3:06
     */
    public void inserChangeTabAtPostion(int selectTab, int position, String projectNum) {
        tabUIModel.setItemNum(projectNum);
        mDatas.add(position, tabUIModel);
        notifyItemInserted(position);
        tabclickListener.onTabClick(selectTab, position, projectNum);
    }

    /**
     * 获取服务端已购商品为空时在指定位置插入显示emptyview的 uimodel
     *
     * @author zhangsan
     * @date 16/10/18 下午5:41
     */
    public void inserEmptyItemAtPostion(int position, int tab, String projectNum) {
        switch (tab) {
            case ChooseServiceTab.TAB_GOODS:
                emptyViewUIModel.setEmptyType(CustomerRepUiModel.EMPTY_TYPE_GOODS);
                break;
            case ChooseServiceTab.TAB_TECHNICIAN:
                emptyViewUIModel.setEmptyType(CustomerRepUiModel.EMPTY_TYPE_TECHNICIAN);
                break;
            case ChooseServiceTab.TAB_WORK_STATION:
                emptyViewUIModel.setEmptyType(CustomerRepUiModel.EMPTY_TYPE_WORKBAY);
                break;
        }

        mDatas.add(position, setEmptyUiModelNum(projectNum));
        notifyItemInserted(position);
    }

    /**
     * 给emptyview设置服务大项编号 用于intent传递到添加页面判断 再添加服务商品emptyview 用到
     *
     * @author zhangsan
     * @date 16/11/4 下午3:50
     */
    public CustomerRepUiModel setEmptyUiModelNum(String projectNum) {
        this.emptyViewUIModel.setItemNum(projectNum);
        return emptyViewUIModel;
    }

    public void buidBaughtGoods(List<WorkOrderInfo> workOrderInfos) {
        for (int i = 0, size = workOrderInfos.size(); i < size; i++) {
            List<CustomerRepUiModel> customerRepUiModels = new ArrayList<CustomerRepUiModel>();
            for (OrderGoodsInfo goodsInfo : workOrderInfos.get(i).getOrderGoodsDetailList()) {
                CustomerRepUiModel customerRepUiModel = new CustomerRepUiModel();
                customerRepUiModel.setItemName(goodsInfo.getGoodsName());
                customerRepUiModel.setItemNum(goodsInfo.getGoodsNum());
                customerRepUiModel.parentNum = workOrderInfos.get(i).getProjectNum();// 商品所属工单的num
                customerRepUiModel.setItemType(CustomerRepUiModel.TYPE_GOODS);
                customerRepUiModel.setIsOverdue(goodsInfo.getIsOverdue());
                customerRepUiModel.relativeBean = goodsInfo;
                customerRepUiModels.add(customerRepUiModel);
            }
            selectGoodsCache.put(workOrderInfos.get(i).getProjectNum(), customerRepUiModels);
        }
    }

    /**
     * 清除选中所有缓存
     *
     * @author zhangsan
     * @date 16/11/4 下午5:31
     */
    public void clearSelectCache() {
        selectGoodsCache.clear();
        selectWorkbayCache.clear();
        selectTechnicianCache.clear();
    }

    /**
     * 根据服务工单编号和选择标签类型获取选中项文本内容
     *
     * @author zhangsan
     * @date 16/11/7 上午9:46
     */
    private String setSelectItemStr(CustomerRepUiModel model, String projectNum, int tabType) {
        String s = "";
        switch (tabType) {
            case ChooseServiceTab.TAB_GOODS:        //selectGoodsCache.get(projectNum) != null &&
//========================================= 获取第一个商品明显示  START  ==========================================
//                CustomerRepUiModel goodsModels = null/* = selectGoodsCache.get(projectNum).isEmpty() ? null : selectGoodsCache.get(projectNum).get(0)*/;
//                if (!selectGoodsCache.get(projectNum).isEmpty()) {  // 当前服务项已选商品不为空
//                    for (CustomerRepUiModel projectNumItem : selectGoodsCache.get(projectNum)) {
//                        if (projectNumItem.isSelected()) {
//                            goodsModels = projectNumItem;
//                            break;
//                        }
//                    }
//                }
//
//                if (goodsModels != null) {
//                    if (goodsModels.relativeBean instanceof ProductInfo) {
//                        ProductInfo productInfo = (ProductInfo) goodsModels.relativeBean;
//                        s = productInfo.getGoodsName();
//                    } else {
//                        OrderGoodsInfo info = (OrderGoodsInfo) goodsModels.relativeBean;
//                        s = info.getGoodsName();
//                    }
//                    model.selectedGoods = s;
//                    model.setSelDescription();
//                } else {
//                    model.selectedGoods = "";
//                    model.setSelDescription();
//                }
//=======================  获取第一个商品明显示  END  ==========================================================

//======================= 获取所有的商品名的拼接  START  ==========================================================
                List<CustomerRepUiModel> customerRepUiModels = new ArrayList<>();
                StringBuilder sb = new StringBuilder();
                if (!selectGoodsCache.get(projectNum).isEmpty()) {  // 当前服务项已选商品不为空
                    for (CustomerRepUiModel projectNumItem : selectGoodsCache.get(projectNum)) {
                        if (projectNumItem.isSelected()) {
                            customerRepUiModels.add(projectNumItem);
                        }
                    }
                    for (CustomerRepUiModel customerRepUiModel : customerRepUiModels) {
                        if (customerRepUiModel.relativeBean instanceof ProductInfo) {
                            ProductInfo productInfo = (ProductInfo) customerRepUiModel.relativeBean;
                            s = productInfo.getGoodsName();
                        } else {
                            OrderGoodsInfo info = (OrderGoodsInfo) customerRepUiModel.relativeBean;
                            s = info.getGoodsName();
                        }

                        if (sb.length() > 0) {
                            sb = sb.append(",");
                        }
                        sb.append(s);
                    }
                }/* else {
                    model.selectedGoods = "";
                }*/
                model.selectedGoods = sb.toString();
                model.setSelDescription();
//=======================  获取所有的商品名的拼接  END ===============================================
                break;
            case ChooseServiceTab.TAB_TECHNICIAN:
                CustomerRepUiModel userModle = getSelectTechncian(projectNum);
                if (userModle != null) {
                    User user = (User) userModle.relativeBean;
                    s = user.getNickName();
                    model.selecedTechnicanName = s;
                    model.setSelDescription();
                } else {
                    model.selecedTechnicanName = "";
                    model.setSelDescription();
                }
                break;
            case ChooseServiceTab.TAB_WORK_STATION:
                CustomerRepUiModel workStationModel = getSelectWorkbay(projectNum);
                if (workStationModel != null) {
                    WorkBayInfo workBayInfo = (WorkBayInfo) workStationModel.relativeBean;
                    s = workBayInfo.getWorkbayName();
                    model.selectedWorkStation = s;
                    model.setSelDescription();
                } else {
                    model.selectedWorkStation = "";
                    model.setSelDescription();
                }
                break;
        }
        return s;
    }

    /**
     * 当item点击选中后
     *
     * @author zhangsan
     * @date 16/11/7 上午10:05
     */
    public void onItemeSelect(String projectNum, int tabType) {
        for (int i = 0, size = mDatas.size(); i < size; i++) {
            CustomerRepUiModel model = mDatas.get(i);
            if (model.getItemType() == CustomerRepUiModel.TYPE_PROJECT_TYPE && model.getItemNum().equals(projectNum)) {// 找点击
                setSelectItemStr(model, projectNum, tabType);
                notifyItemChanged(i);
                break;
            }
        }
    }

    /**
     * 选中商品放入缓存map方便构建参数
     *
     * @author zhangsan
     * @date 16/11/4 下午4:15
     */
    public void putGoodsListIntoCache(String projectNum, List<CustomerRepUiModel> goodsList) {
        List<CustomerRepUiModel> goodsCache = selectGoodsCache.get(projectNum);
        if (null == goodsCache) {
            selectGoodsCache.put(projectNum, goodsList);
        } else {
            goodsCache.addAll(goodsList);
        }
    }

    /**
     * 根据工单编号获取选中商品
     *
     * @author zhangsan
     * @date 16/10/29 上午10:48
     */
    @Nullable
    public List<CustomerRepUiModel> getGoodsListByProjectType(String projectNum) {
        return selectGoodsCache.get(projectNum);
    }

    /**
     * 获取选中者技师
     *
     * @author zhangsan
     * @date 16/10/13 下午6:49
     */
    @Nullable
    public CustomerRepUiModel getSelectTechncian(String projectNum) {
        return selectTechnicianCache.get(projectNum);
    }

    /**
     * 获取选中工位
     *
     * @author zhangsan
     * @date 16/10/13 下午6:49
     */
    @Nullable
    public CustomerRepUiModel getSelectWorkbay(String projectNum) {
        return selectWorkbayCache.get(projectNum);
    }

    /**
     * 根据viewtype 刷新部分item
     *
     * @author zhangsan
     * @date 16/10/9 下午2:09
     */
    private void refreshItemByViewType(int viewType) {
        notifyItemRangeChanged(getFirstIndexOfType(viewType), getLastIndexOfType(viewType));
    }

   /* public void setOnProjectTypeClick(onProjectTypeClickListener onProjectTypeClick) {
        this.onProjectTypeClickListener = onProjectTypeClick;
    }*/

    /**
     * 根据所选工单信息构建参数
     *
     * @author zhangsan
     * @date 16/10/19 下午2:38
     */
    public List<WorkOrderSubmitInfo.WorkOrderListInfo> buildWorkOrderParams() {
        List<WorkOrderSubmitInfo.WorkOrderListInfo> workOrderListInfos = new ArrayList<>();
        for (CustomerRepUiModel uiModel : mDatas) {
            if (uiModel.getItemType() == CustomerRepUiModel.TYPE_PROJECT_TYPE && uiModel.isServiceSelected()) {
                WorkOrderSubmitInfo.WorkOrderListInfo workOrderInfo = new WorkOrderSubmitInfo.WorkOrderListInfo();
                if (uiModel.relativeBean != null && uiModel.relativeBean instanceof WorkOrderInfo) {
                    workOrderInfo.workOrderNum = ((WorkOrderInfo) uiModel.relativeBean).getWorkOrderNum();
                }
                workOrderInfo.projectNum = uiModel.getItemNum();
                workOrderInfo.workOrderGoodsList = getWorkGoodList(uiModel.getItemNum());
                setWorbayInfoInWorkOrder(workOrderInfo, uiModel.getItemNum());
                setTechnicianInfoIntoWorkOrder(workOrderInfo, uiModel.getItemNum());
                workOrderListInfos.add(workOrderInfo);
            }
        }
        return workOrderListInfos;
    }

    private void setWorbayInfoInWorkOrder(WorkOrderSubmitInfo.WorkOrderListInfo workOrder, String projectNum) {
        CustomerRepUiModel customerRepUiModel = getSelectWorkbay(projectNum);
        if (customerRepUiModel == null) {
            return;
        }
        WorkBayInfo workBayInfo = (WorkBayInfo) customerRepUiModel.relativeBean;
        workOrder.workbayInfoNum = workBayInfo.getWorkbayInfoNum();
        workOrder.workbayName = workBayInfo.getWorkbayName();

    }

    private void setTechnicianInfoIntoWorkOrder(WorkOrderSubmitInfo.WorkOrderListInfo workOrder, String projectNum) {
        CustomerRepUiModel customerRepUiModel = getSelectTechncian(projectNum);
        if (customerRepUiModel == null) {
            return;
        }
        User user = (User) customerRepUiModel.relativeBean;
        workOrder.leadingUserName = user.getNickName();
        workOrder.leadingUserNum = user.getUserNum();
    }

    /**
     * 获取选中商品信息
     *
     * @author zhangsan
     * @date 16/10/29 上午11:43
     */
    private List<WorkOrderSubmitInfo.WorkOrderGoods> getWorkGoodList(String projectNum) {
        List<CustomerRepUiModel> selectGoods = selectGoodsCache.get(projectNum);
        List<WorkOrderSubmitInfo.WorkOrderGoods> workOrderGoodsList = new ArrayList<>();
        if (null == selectGoods) {
            return workOrderGoodsList;
        }
        for (CustomerRepUiModel customerRepUiModel : selectGoods) {
            if (customerRepUiModel.isSelected()) {
                WorkOrderSubmitInfo.WorkOrderGoods goods = new WorkOrderSubmitInfo.WorkOrderGoods();
                goods.goodsName = customerRepUiModel.getItemName();
                goods.goodsNum = customerRepUiModel.getItemNum();
                goods.goodsTotalCount = customerRepUiModel.getCount();
                if (customerRepUiModel.isNewItem && customerRepUiModel.relativeBean instanceof ProductInfo) {//新添加
                    ProductInfo productInfo = (ProductInfo) customerRepUiModel.relativeBean;
                    goods.mainPhoto = productInfo.getMainPhoto();
                    goods.singlePrice = Double.toString(productInfo.getSalePrice());
                    goods.xstcAmount = productInfo.getXstcje() == null ? "0" : productInfo.getXstcje().toString();
                    goods.sgtcAmount = productInfo.getSgtcje() == null ? "0" : productInfo.getSgtcje().toString();
                } else if (!customerRepUiModel.isNewItem && customerRepUiModel.relativeBean instanceof OrderGoodsInfo) {//服务端获取的已购商品
                    OrderGoodsInfo orderGoodsInfo = (OrderGoodsInfo) customerRepUiModel.relativeBean;
                    goods.singlePrice = orderGoodsInfo.getAmount() == null ? "0" : orderGoodsInfo.getAmount().toString();
                    goods.orderGoodsDetailNum = orderGoodsInfo.getOrderGoodsDetailNum();
                    goods.mainPhoto = orderGoodsInfo.getMainPhoto();
                    goods.sgtcAmount = String.valueOf(orderGoodsInfo.getSgtcAmount());
                    goods.xstcAmount = String.valueOf(orderGoodsInfo.getXstcAmount());
                }
                workOrderGoodsList.add(goods);
            }

        }
        return workOrderGoodsList;
    }


    public ArrayList<String> getCurrentProjectNums() {
        ArrayList<String> projectNums = new ArrayList<>();
        for (CustomerRepUiModel customerRepUiModel : mDatas) {
            if (customerRepUiModel.getItemType() == CustomerRepUiModel.TYPE_PROJECT_TYPE) {
                projectNums.add(customerRepUiModel.getItemNum());
            }
        }
        return projectNums;
    }

    /*private onProjectTypeClickListener onProjectTypeClickListener;

    public interface onProjectTypeClickListener {
        void onProjectTypeItemClick(CustomerRepUiModel customerRepUiModel, int posiotion);
    }*/


    /**
     * 服务展开标签
     *
     * @author zhangsan
     * @date 16/10/9 下午1:55
     */
    class CustomerRepProjectTypeDelegate implements ItemViewDelegate<CustomerRepUiModel> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.list_item_cusrep_project_type;
        }

        @Override
        public boolean isForViewType(CustomerRepUiModel item, int position) {
            return item.getItemType() == CustomerRepUiModel.TYPE_PROJECT_TYPE;
        }

        @Override
        public void convert(final ViewHolder holder, final CustomerRepUiModel customerRepUiModel, final int position) {
            AutoUtils.auto(holder.getConvertView());
            holder.setText(R.id.tv_project_name, customerRepUiModel.getItemName());
            ImageView imgStatus = holder.getView(R.id.img_sel_status);
            holder.setText(R.id.tv_describe, customerRepUiModel.getDescription());
            final ImageView imgServiceSel = holder.getView(R.id.img_service_sel);
            imgServiceSel.setSelected(customerRepUiModel.isServiceSelected());
            imgStatus.setSelected(customerRepUiModel.isSelected());
            imgServiceSel.setOnClickListener(new NoDoubleClicksListener() {
                @Override
                public void noDoubleClick(View v) {
                    customerRepUiModel.setServiceSelected(!customerRepUiModel.isServiceSelected());
                    imgServiceSel.setSelected(customerRepUiModel.isServiceSelected());
                }
            });
            holder.getView(R.id.rl_root).setOnClickListener(new NoDoubleClicksListener() {
                @Override
                public void noDoubleClick(View v) {
                    // int index = getIndexOfExpandItems(position);//获取上一个展开标签位置
                    customerRepUiModel.setSelected(!customerRepUiModel.isSelected());
                    notifyItemChanged(position);
                    if (!customerRepUiModel.isSelected()) {//收起
                        removeExpandItems(customerRepUiModel.getItemNum());
                        notifyDataSetChanged();
                    } else {//展开
                        int removeCount = removeExpandItems(customerRepUiModel.getItemNum());//移除所有展开内容 并获取移除数量
                        if (removeCount > 0) {
                            //FIXME 改为notifyitemrangeremove 更好
                            notifyDataSetChanged();
                        }
                        int index = mDatas.indexOf(customerRepUiModel);//索引校验
                        if (position == index) {
                            inserChangeTabAtPostion(customerRepUiModel.getSelecTab(), position + 1, customerRepUiModel.getItemNum());
                        } else if (index != -1) {
                            inserChangeTabAtPostion(customerRepUiModel.getSelecTab(), index + 1, customerRepUiModel.getItemNum());
                        }
                    }

                }
            });

        }
    }

    /**
     * 切换标签
     *
     * @author zhangsan
     * @date 16/10/11 上午11:21
     */
    class CustomerRepTabDelegate implements ItemViewDelegate<CustomerRepUiModel> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.list_item_cusrep_tab;
        }

        @Override
        public boolean isForViewType(CustomerRepUiModel item, int position) {
            return item.getItemType() == CustomerRepUiModel.TYPE_TAB;
        }

        @Override
        public void convert(ViewHolder holder, CustomerRepUiModel customerRepUiModel, int position) {
            AutoUtils.auto(holder.getConvertView());
            CustomerRepUiModel parentModel = mDatas.get(position - 1);
            ChooseServiceTab tab = holder.getView(R.id.choose_tab);
            tab.relatedPosition = position;
            tab.setTabSelcted(parentModel.getSelecTab());
            tab.relatedProjectNum = customerRepUiModel.getItemNum();
            tab.setOnTabClickListener(tabclickListener);
        }


    }

    /**
     * 列表为空
     *
     * @author zhangsan
     * @date 16/10/9 上午11:48
     */
    class CustomerEmptyViewDelegate implements ItemViewDelegate<CustomerRepUiModel> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.list_item_cusrep_empty;
        }

        @Override
        public boolean isForViewType(CustomerRepUiModel item, int position) {
            return item.getItemType() == CustomerRepUiModel.TYPE_EMPTY_VIEW;
        }

        @Override
        public void convert(ViewHolder holder, final CustomerRepUiModel customerRepUiModel, int position) {
            AutoUtils.auto(holder.getConvertView());
            TextView tvAdd = holder.getView(R.id.tv_add);

            switch (customerRepUiModel.getEmptyType()) {
                case CustomerRepUiModel.EMPTY_TYPE_GOODS://添加服务商品为空
                    holder.setText(R.id.tv_empty_tip, mContext.getString(R.string.empty_tips_goods));
                    tvAdd.setVisibility(View.VISIBLE);
                    tvAdd.setOnClickListener(new NoDoubleClicksListener() {
                        @Override
                        public void noDoubleClick(View v) {
                            Intent intent = new Intent(mContext, AddServiceGoodsActivity.class);
                            ProjectType projectType = DbHelper.getInstance().getServiceType(customerRepUiModel.getItemNum());
                            intent.putExtra(C.IntentKey.PROJECTTYPE_INFO, projectType);
                            mContext.startActivity(intent);
                        }
                    });
                    break;
                case CustomerRepUiModel.EMPTY_TYPE_TECHNICIAN://选技师 列表为空
                    holder.setText(R.id.tv_empty_tip, mContext.getString(R.string.empty_tips_technician));
                    tvAdd.setVisibility(View.GONE);
                    break;
                case CustomerRepUiModel.EMPTY_TYPE_WORKBAY://选工位列表为空
                    holder.setText(R.id.tv_empty_tip, mContext.getString(R.string.empty_tips_workbay));
                    tvAdd.setVisibility(View.GONE);
                    break;
            }
        }
    }

    /**
     * 添加服务商品按钮
     *
     * @author zhangsan
     * @date 16/10/9 上午11:48
     */
    class CustomerAddNewServiceDelegate implements ItemViewDelegate<CustomerRepUiModel> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.list_item_cusrep_add_projecttype;
        }

        @Override
        public boolean isForViewType(CustomerRepUiModel item, int position) {
            return item.getItemType() == CustomerRepUiModel.TYPE_BUTTON;
        }

        @Override
        public void convert(ViewHolder holder, final CustomerRepUiModel customerRepUiModel, int position) {
            AutoUtils.auto(holder.getConvertView());
            holder.getConvertView().setOnClickListener(new NoDoubleClicksListener() {
                @Override
                public void noDoubleClick(View v) {
                    Intent intent = new Intent(mContext, AddServiceGoodsActivity.class);
                    ProjectType projectType = DbHelper.getInstance().getServiceType(customerRepUiModel.getItemNum());
                    intent.putExtra(C.IntentKey.PROJECTTYPE_INFO, projectType);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    /**
     * 选技师
     *
     * @author zhangsan
     * @date 16/10/9 上午11:47
     */
    class CustomerRepSelTechDelegate implements ItemViewDelegate<CustomerRepUiModel> {
        public int clickPositon = -1;

        @Override
        public int getItemViewLayoutId() {
            return R.layout.list_item_cusrep_select_tecorstation;
        }

        @Override
        public boolean isForViewType(CustomerRepUiModel item, int position) {
            return item.getItemType() == CustomerRepUiModel.TYPE_TECHNICIAN;
        }

        @Override
        public void convert(ViewHolder holder, final CustomerRepUiModel customerRepUiModel, final int position) {
            AutoUtils.auto(holder.getConvertView());
            holder.setText(R.id.tv_item_name, customerRepUiModel.getItemName());
            ImageView imgStatus = holder.getView(R.id.img_sel_status);
            if (clickPositon == position) {
                customerRepUiModel.setSelected(true);
            } else {
                customerRepUiModel.setSelected(false);
            }
            imgStatus.setSelected(customerRepUiModel.isSelected());
            holder.getConvertView().setOnClickListener(new NoDoubleClicksListener() {
                @Override
                public void noDoubleClick(View v) {
                    if (customerRepUiModel.isSelected()) {  //  选技师选中的情况下  取消选择
                        clickPositon = -1;
                        customerRepUiModel.setSelected(!customerRepUiModel.isSelected());
                        refreshItemByViewType(customerRepUiModel.getItemType());
                        selectTechnicianCache.remove(customerRepUiModel.getItemNum());
                        onItemeSelect(customerRepUiModel.getItemNum(), ChooseServiceTab.TAB_TECHNICIAN);
                    } else {  //  选技师未选中的情况下  选择
                        //添加判断逻辑 该选中技师是否在其他工单已选择
                        if (hasBeSelected(((User)customerRepUiModel.relativeBean).getUserNum())) return;// 已被选择跳过以下步骤
                        clickPositon = position;
                        customerRepUiModel.setSelected(!customerRepUiModel.isSelected());
                        refreshItemByViewType(customerRepUiModel.getItemType());
                        selectTechnicianCache.put(customerRepUiModel.getItemNum(), customerRepUiModel);
                        onItemeSelect(customerRepUiModel.getItemNum(), ChooseServiceTab.TAB_TECHNICIAN);
                    }

                }
            });
        }
    }

    private boolean hasBeSelected(String userNum) {
        for (String s : selectTechnicianCache.keySet()) {
            User relativeBean = (User) selectTechnicianCache.get(s).relativeBean;
            if ( relativeBean.getUserNum().equals(userNum)) {
                AppUtility.showToastMsg(relativeBean.getNickName()+"在其他服务中已选择过");
                return true;
            }
        }
        return false;
    }

    /**
     * 选工位item
     *
     * @author zhangsan
     * @date 16/10/9 上午11:47
     */
    class CustomerSelWorkStationDelegate implements ItemViewDelegate<CustomerRepUiModel> {
        public int clickPositon = -1;

        @Override
        public int getItemViewLayoutId() {
            return R.layout.list_item_cusrep_select_tecorstation;
        }

        @Override
        public boolean isForViewType(CustomerRepUiModel item, int position) {
            return item.getItemType() == CustomerRepUiModel.TYPE_WORK_STATION;
        }

        @Override
        public void convert(ViewHolder holder, final CustomerRepUiModel customerRepUiModel, final int position) {
            AutoUtils.auto(holder.getConvertView());
            holder.setText(R.id.tv_item_name, customerRepUiModel.getItemName());
            ImageView imgStatus = holder.getView(R.id.img_sel_status);
            if (clickPositon == position) {
                customerRepUiModel.setSelected(true);
            } else {
                customerRepUiModel.setSelected(false);
            }
            imgStatus.setSelected(customerRepUiModel.isSelected());
            holder.getConvertView().setOnClickListener(new NoDoubleClicksListener() {
                @Override
                public void noDoubleClick(View v) {
                    if (customerRepUiModel.isSelected()) {  // 如果是选中状态 置为不选中
                        clickPositon = -1;
//                        customerRepUiModel.setSelected(!customerRepUiModel.isSelected());
//                        refreshItemByViewType(customerRepUiModel.getItemType());
                        selectWorkbayCache.remove(customerRepUiModel.getItemNum());// 清除工位的选择缓存
//                        onItemeSelect(customerRepUiModel.getItemNum(), ChooseServiceTab.TAB_WORK_STATION);
                    } else {            // 如果不是选中状态 置为选中
                        clickPositon = position;
//                        customerRepUiModel.setSelected(!customerRepUiModel.isSelected());
//                        refreshItemByViewType(customerRepUiModel.getItemType());
                        selectWorkbayCache.put(customerRepUiModel.getItemNum(), customerRepUiModel);
//                        onItemeSelect(customerRepUiModel.getItemNum(), ChooseServiceTab.TAB_WORK_STATION);
                    }
                    customerRepUiModel.setSelected(!customerRepUiModel.isSelected());
                    refreshItemByViewType(customerRepUiModel.getItemType());
                    onItemeSelect(customerRepUiModel.getItemNum(), ChooseServiceTab.TAB_WORK_STATION);
                }
            });
        }
    }

    /**
     * 选择商品
     */
    class CustomerGoodsSelectDelegate implements ItemViewDelegate<CustomerRepUiModel> {

        @Override
        public int getItemViewLayoutId() {
            return R.layout.list_item_cusrep_goods;
        }

        @Override
        public boolean isForViewType(CustomerRepUiModel item, int position) {
            return item.getItemType() == CustomerRepUiModel.TYPE_GOODS;
        }

        @Override
        public void convert(ViewHolder holder, final CustomerRepUiModel customerRepUiModel, final int position) {
            AutoUtils.auto(holder.getConvertView());
            ImageView imgSelStatus = holder.getView(R.id.img_sel_status);
            imgSelStatus.setSelected(customerRepUiModel.isSelected());
            holder.setText(R.id.tv_count, "×" + Integer.toString(customerRepUiModel.getCount()));
            holder.setText(R.id.tv_goods_name, customerRepUiModel.getItemName());
            if (customerRepUiModel.getIsOverdue() == 1) { //商品已过期
                holder.setVisible(R.id.tv_overdue, true);
                holder.setTextColor(R.id.tv_goods_name, ContextCompat.getColor(mContext, R.color.text_default));
                holder.setOnClickListener(R.id.img_sel_status, null);
            } else {
                holder.setVisible(R.id.tv_overdue, false);
                holder.setTextColor(R.id.tv_goods_name, ContextCompat.getColor(mContext, R.color.font_black));
                holder.setOnClickListener(R.id.img_sel_status, new NoDoubleClicksListener() {
                    @Override
                    public void noDoubleClick(View v) {
                        customerRepUiModel.setSelected(!customerRepUiModel.isSelected());
                        notifyItemChanged(position);
                        onItemeSelect(customerRepUiModel.parentNum, ChooseServiceTab.TAB_GOODS);
                    }
                });
            }
            /*holder.setOnClickListener(R.id.tv_goods_name, new NoDoubleClicksListener() {
                @Override
                public void noDoubleClick(View v) {
                    //商品的点击事件
                    if (customerRepUiModel.relativeBean instanceof OrderGoodsInfo) {
                        OrderGoodsInfo goodsInfo = (OrderGoodsInfo) customerRepUiModel.relativeBean;
                        AppUtility.showGoodsWebView(
                                (goodsInfo.getAmount() == null || goodsInfo.getAmount().equals(""))
                                        ? 0 : goodsInfo.getAmount().doubleValue(),
                                App.getInstance().getCurrentUserNum(),
                                goodsInfo.getGoodsNum(),
                                goodsInfo.getGoodsType(),
                                goodsInfo.getGoodsNum(),
                                App.getInstance().getCurrentUserNum(),
                                mContext,
                                goodsInfo.getGoodsName(),
                                goodsInfo.getProjectParent(),
                                goodsInfo.getMainPhoto(), "2",
                                goodsInfo.getViceTitle() );
                    } else if (customerRepUiModel.relativeBean instanceof ProductInfo) {
                        ProductInfo productInfo = (ProductInfo) customerRepUiModel.relativeBean;
                        AppUtility.showGoodsWebView(
                                productInfo.getSalePrice(),
                                App.getInstance().getCurrentUserNum(),
                                productInfo.getGoodsNum(),
                                productInfo.getGoodsType(),
                                productInfo.getGoodsNum(),
                                App.getInstance().getCurrentUserNum(),
                                mContext,
                                productInfo.getGoodsName(),
                                productInfo.getProjectParent(),
                                productInfo.getMainPhoto(), "2", productInfo.getViceTitle() );
                    }
                }
            });*/
        }
    }
}
