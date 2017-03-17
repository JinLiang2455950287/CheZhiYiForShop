package com.ruanyun.chezhiyi.view.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.User;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleItemClicksListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Description:选择技师弹框
 * author: zhangsan on 16/11/3 下午4:27.
 */
public class ChooseTechnicianDialog extends BottomSheetDialogFragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    ChooseTechnicianAdapter adapter;
    private Context mContext;
    private List<User> technicianList = new ArrayList<>();

    private int selectIndex=-1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.pop_refund_application,container,false);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        adapter = new ChooseTechnicianAdapter(mContext, R.layout.list_item_choose_parentcode, technicianList);
        adapter.clickPositon=selectIndex;
        list.setAdapter(adapter);
        list.setOnItemClickListener(new NoDoubleItemClicksListener() {
            @Override
            public void noDoubleClick(AdapterView<?> parent, View view, int position, long id) {
                if(chooseTechinicanResult!=null) {
                    chooseTechinicanResult.onChooseTechinicanResult(adapter.getItem(position));
                }
                adapter.clickPositon=position;
                adapter.notifyDataSetChanged();
                dismiss();
            }
        });
        tvTitle.setText("请选择洗车技师");
    }

    public List<User> getTechnicianList() {
        return technicianList;
    }

    public void updateList(List<User> userList) {
        this.technicianList = userList;
        //adapter.notifyDataSetChanged();
    }

    /**
     * 显示dialog时根据user找到列表中是否选中
     *
     * @author zhangsan
     * @date 16/11/3 下午5:02
     */
    public void showDlg(User user,FragmentManager manager) {
        this.selectIndex=technicianList.indexOf(user);
        show(manager,"");
    }

    @OnClick(R.id.tv_cancel)
    public void onClick() {
        dismiss();
    }

    public void setChooseTechinicanResult(onChooseTechinicanResult chooseTechinicanResult) {
        this.chooseTechinicanResult = chooseTechinicanResult;
    }

    onChooseTechinicanResult chooseTechinicanResult;
     /**
       * 选择回调
       *@author zhangsan
       *@date   16/11/3 下午5:46
       */
    public interface onChooseTechinicanResult {
        void onChooseTechinicanResult(User user);
    }

    class ChooseTechnicianAdapter extends CommonAdapter<User> {
        public int clickPositon = -1;

        public ChooseTechnicianAdapter(Context context, int layoutId, List<User> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, User user, int position) {
            TextView tvContent = holder.getView(R.id.tv_content);
            tvContent.setText(user.getNickName());
            if (clickPositon == position) {
                user.setSelected(true);
            } else {
                user.setSelected(false);
            }
            tvContent.setSelected(user.isSelected());
        }
    }
}
