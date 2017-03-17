package com.ruanyun.czy.client.view.ui.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.CarModel;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.CarCommentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.Subscribe;

/**
 * Created by msq on 2016/9/22.
 */
public class CarChoicesActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.lv)
    ListView lv;
    CarCommentAdapter adapter;

    List<CarModel> listOne = new ArrayList<>();//保存当前页一级和二级数据
    List<CarModel> listSecond = new ArrayList<>();//保存当前页一级数据
    String CarModelNum;//获得点击的CarModelNum

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_car_comment);
        ButterKnife.bind(this);
        initView();
        initData();
        registerBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
    }

    private void initView() {
        topbar.setTttleText("选择车系")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);
    }

    private void initData(){
        Intent intent = getIntent();
        CarModelNum = intent.getStringExtra("CarModelNum");
        adapter = new CarCommentAdapter(this,R.layout.list_item_car_comment,getSecondList(CarModelNum));
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!checkList(i)){
                    Intent intent = getIntent();
                    intent.setClass(mContext,ModelsChoicesActivity.class);
                    intent.putExtra("CarModelNum",listOne.get(i).getCarModelNum());
                    startActivity(intent);
                }
            }
        });
    }

    @Subscribe
    public void recieveFinish(String msg){
        if(C.EventKey.EXIT.equals(msg))
            finish();
    }

    //遍历获得当前页面的一级二级数据
    public List<CarModel> getSecondList(String str){
        listOne.clear();
        listSecond.clear();
        List<CarModel> listCar = App.getInstance().beanCacheHelper.getLocalCarModels();
        for(int i = 0; i < listCar.size(); i++){
            if(listCar.get(i).getParentModelNum().equals(str)){
                listOne.add(listCar.get(i));
                listSecond.add(listCar.get(i));
                for(int j = 0; j < listCar.size(); j++){
                    if(listCar.get(j).getParentModelNum().equals(listCar.get(i).getCarModelNum())){
                        listOne.add(listCar.get(j));
                    }
                }
            }
        }
        return listOne;
    }

    //检测点击的是否是当前页的二级item
    public boolean checkList(int position){
        for(int i = 0; i < listSecond.size(); i++){
            if(listSecond.get(i).getParentModelNum().equals(listOne.get(position).getParentModelNum())){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == com.ruanyun.chezhiyi.commonlib.R.id.img_btn_left) {
            finish();
        }
    }
}
