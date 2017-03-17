package com.ruanyun.czy.client.view.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.base.ResultBase;
import com.ruanyun.chezhiyi.commonlib.model.AccountBookInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.model.OrderInfo;
import com.ruanyun.chezhiyi.commonlib.model.ProjectType;
import com.ruanyun.chezhiyi.commonlib.model.params.AccountBookParams;
import com.ruanyun.chezhiyi.commonlib.presenter.AccountBookMoneySumPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.view.AccountBookMoneySumMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.AccountBookAdapter;
import com.ruanyun.czy.client.view.ui.my.OrderDetailedActivity;
import com.zhy.autolayout.AutoRelativeLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import retrofit2.Call;

/**
 * @author 客户端的记账本界面
 * @ClassName: ${file_name}
 * @Description:
 * @date ${date}${time}
 */
public class AccountBookActivity extends RefreshBaseActivity implements Topbar.onTopbarClickListener,
        MonthSelectPopWindow.OnPopupClickListener, AccountBookMoneySumMvpView {


    @BindView(R.id.topbar)
    Topbar topbar;
    /**修改年月*/
    @BindView(R.id.rl_year_month)
    RelativeLayout rlYearMonth;
    /**查看的年*/
    @BindView(R.id.tv_account_year)
    TextView tvAccountYear;
    /**查看的月*/
    @BindView(R.id.tv_account_month)
    TextView tvAccountMonth;
    /**支出金额*/
    @BindView(R.id.tv_account_money_amount)
    TextView tvAccounMoneytAmount;
    /**记账本列表*/
    @BindView(R.id.list)
    ListView list;

    private AccountBookParams params;//传参
    private List<AccountBookInfo> accountBookInfos;
    private AccountBookAdapter adapter;
    private Calendar calendar;
    private MonthSelectPopWindow mPopWindow;//月份选择popWindow
    private List<ProjectType> stairprojectTypes;//一级工单服务分类集合
    private AccountBookMoneySumPresenter presenter = new AccountBookMoneySumPresenter();

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_account_book);
        ButterKnife.bind(this);
        presenter.attachView(this);
        registerBus();
        initRefreshLayout();
        initView();
        init();
        setAdapter();
        refreshWithLoading();//下拉刷新
    }

    /**
     * ParticularsActivity 219
     * RememberingActivity 400
     * @param msg
     */
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onRecieveRefresh(Event<String> msg){
        if(msg.key.equals(C.EventKey.KEY_REFRESH_LIST)){
            refreshWithLoading();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unRegisterBus();
    }
    private void initView() {
        TextView addText = getViewFromLayout(R.layout.layou_topbar_right_add_book,topbar,false);
        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, RememberingActivity.class);
                intent.putExtra(C.IntentKey.ADDUPDATA, RememberingActivity.PAGE_ADD);
                intent.putParcelableArrayListExtra(C.IntentKey.STAIR_PROJECT_TYPES,(ArrayList) stairprojectTypes);
                startActivity(intent);
            }
        });
        topbar.setTttleText("记账本")
                .addViewToTopbar(addText, (AutoRelativeLayout.LayoutParams) addText.getLayoutParams())
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
//        showMonthSelectPopWindow();
        mPopWindow = new MonthSelectPopWindow(mContext,findViewById(R.id.rl_year_month));
        mPopWindow.setOnPopupClickListener(this);
    }

    private void init() {
        params = new AccountBookParams();
        String date = AppUtility.getCurrentDate();
        params.setMonth(date.substring(0, 4) + date.substring(5, 7));
        params.setNumPerPage(10);
        params.setTotalPage(10);
        tvAccountYear.setText(date.substring(0, 4)+"年");
        tvAccountMonth.setText(date.substring(5, 7));
        stairprojectTypes = new ArrayList<>();
        stairprojectTypes = DbHelper.getInstance().getSeviceTypes();//获取工单服务和技师技能数据结构
    }

    /**
     * 设置记账本列表Adapter及监听
     */
    private void setAdapter() {
        accountBookInfos = new ArrayList<>();
        adapter = new AccountBookAdapter(mContext, R.layout.item_account_info, accountBookInfos);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AccountBookInfo bookInfo = accountBookInfos.get(position);
                if(bookInfo.getBookType()==AccountBookInfo.BOOK_TYPE_AUTO &&!bookInfo.getProjectNum().equals("000000")){
                    //AppUtility.showToastMsg("跳转到我的订单界面");
                    presenter.getOrderDetail(bookInfo.getCommonNum());
                }else if(bookInfo.getBookType()==AccountBookInfo.BOOK_TYPE_MANUAL){
                    Intent intent = new Intent();
                    intent.setClass(mContext,ParticularsActivity.class);
                    intent.putExtra(/*"AccountBookInfo"*/C.IntentKey.ACCOUNT_BOOK_INFO,bookInfo);
                    intent.putParcelableArrayListExtra(C.IntentKey.STAIR_PROJECT_TYPES, (ArrayList) stairprojectTypes);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * topBar监听
     * @param v
     */
    @Override
    public void onTobbarViewClick(View v) {
        int id = v.getId();
        if (id == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }

    /**
     * 下拉刷新
     * @return
     */
    @Override
    public Call loadData() {
        getMoneyAmount();//获取总金额
        params.setPageNum(currentPage);
        return app.getApiService().getAccountBookList(app.getCurrentUserNum(), params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        totalPage = result.getTotalPage();
        accountBookInfos = dataTransition(result.getResult());
        upAdapter();
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        accountBookInfos.addAll(dataTransition(result.getResult()));
        upAdapter();
    }

    /**
     * 数据转换
     */
    private List<AccountBookInfo> dataTransition(List<AccountBookInfo> accountBookInfos) {
        if(accountBookInfos==null||accountBookInfos.size()==0){
            return new ArrayList<>();
        }
        if(stairprojectTypes==null||stairprojectTypes.size()==0){
            return new ArrayList<>();
        }
        for (int i=0;i<accountBookInfos.size();i++) {
            for (ProjectType stairprojectType : stairprojectTypes) {
                if(accountBookInfos.get(i).getProjectNum().equals(stairprojectType.getProjectNum())){
                    accountBookInfos.get(i).setProjectName(stairprojectType.getProjectName());
                }
            }
        }
        return accountBookInfos;
    }

    /**
     * 更新列表
     */
    private void upAdapter() {
        adapter.setDatas(accountBookInfos);
        adapter.notifyDataSetChanged();
    }

    /**
     * 选择月份监听
     * @param view
     */
    @OnClick({R.id.rl_year_month})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_year_month:
                mPopWindow.show(true);
                mPopWindow.setSelect(tvAccountMonth.getText().toString());
                break;
        }
    }
//
//    private void showMonthSelectPopWindow() {
//
//    }

    /**
     * 月份选择回调
     * @param month
     */
    @Override
    public void onItemClick(String month) {
        calendar = Calendar.getInstance();
        int myear = calendar.get(Calendar.YEAR);
        tvAccountYear.setText(myear+"年");
        tvAccountMonth.setText(month);
        params.setMonth(myear+month);
        mPopWindow.setSelect(month);
        mPopWindow.show(false);
        refreshWithLoading();
    }

    /**
     * 获取总金额
     */
    private void getMoneyAmount() {
        Call<ResultBase<BigDecimal>> call =  app.getApiService().getAccountBookMoneyAmount(app.getCurrentUserNum(),params);
        presenter.getAccountBookMoneySum(call);
    }

    @Override
    public void showLoadingView(String msg) {
        showLoading(msg);
    }

    @Override
    public void dismissLoadingView() {
        dissMissLoading();
    }

    @Override
    public void showAccountBookMoneySumSuccessTip(BigDecimal decimal) {
        String moneyAmount = decimal.setScale(2, BigDecimal.ROUND_HALF_UP).abs().toString();
        tvAccounMoneytAmount.setText(moneyAmount);
    }

    @Override
    public void showAccountBookMoneySumFail() {
        tvAccounMoneytAmount.setText("0.00");
    }

    @Override
    public void orderDetailResult(OrderInfo orderInfo) {
        Intent intent=new Intent(mContext, OrderDetailedActivity.class);
        intent.putExtra(C.IntentKey.ORDER_INFO,orderInfo);
        showActivity(intent);
    }
}
