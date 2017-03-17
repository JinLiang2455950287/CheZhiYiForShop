package com.ruanyun.czy.client.view.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.hyphenate.easeui.widget.EaseSidebar;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.CarModel;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.BrandChoicesAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;

/**
 * Created by msq on 2016/9/21.
 */
public class BrandChoicesActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.sidebar)
    EaseSidebar sidebar;
    @BindView(R.id.topbar)
    Topbar topbar;

    List<CarModel> list = new ArrayList<>();//保存当前页数据
    BrandChoicesAdapter adapter;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_brand_chioce);
        ButterKnife.bind(this);
        initView();
        initList();
        initData();
        registerBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    private void initView() {
        topbar.setTttleText("选择品牌")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

    }

    @Subscribe
    public void recieveFinish(String msg){
        if(C.EventKey.EXIT.equals(msg))
            finish();
    }

    //获取CarModel一级数据
    private void initList(){
        for(int i = 0;i < App.getInstance().beanCacheHelper.getLocalCarModels().size();i++){
            if(App.getInstance().beanCacheHelper.getLocalCarModels().get(i).getParentModelNum().equals("000000")){
                list.add(App.getInstance().beanCacheHelper.getLocalCarModels().get(i));
            }
        }
    }

    private void initData(){
        adapter = new BrandChoicesAdapter(this,R.layout.list_item_select_brand,list);
        lv.setAdapter(adapter);
        sidebar.setListView(lv);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getSecondList(list.get(i).getCarModelNum());
                Intent intent = getIntent();
                intent.setClass(mContext,CarChoicesActivity.class);
                intent.putExtra("CarModelNum",list.get(i).getCarModelNum());
                startActivity(intent);

            }
        });
    }

    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }
}
