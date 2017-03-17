package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.ParentCodeInfo;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.DbHelper;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Description:
 * author: zhangsan on 16/10/20 下午8:42.
 */
public class ChooseInterestsDialog extends BottomSheetDialogFragment implements View.OnClickListener {

    TextView tvConfirm;

    TextView tvCancel;


    private List<ParentCodeInfo> parentCodeInfos;
    private ListView list;
    ParentCodeAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_dialog_choose_intrests, container);
        list = (ListView) view.findViewById(R.id.list);
        tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
        tvCancel.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        parentCodeInfos = DbHelper.getInstance().getParentCodeList(C.ParentCode.USER_INTEREST);
        adapter = new ParentCodeAdapter(getActivity(), R.layout.list_item_choose_parentcode, parentCodeInfos);
       setParentcodeListStatus();
        list.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.tv_confirm) {
            if (onInterestsResult != null) {
                onInterestsResult.onInterestsResult(adapter.getSelectInterests());
            }
            dismiss();
        } else if (viewId == R.id.tv_cancel) {
            dismiss();
        }

    }
    /**
      *
      *@author zhangsan
      *@date   16/10/21 上午9:23
      */
    private void setParentcodeListStatus(){
        if(intrestsStrList!=null&&intrestsStrList.size()>0){
            for(String s:intrestsStrList){
                for(ParentCodeInfo info:parentCodeInfos){
                    if(s.equals(info.getItemName())){
                        info.setSelected(true);
                    }
                }
            }
        }
    }

   List<String> intrestsStrList;//
    /**
      * 根据传过来的个人爱好 字符串 逗号分隔 选中列表中匹配项
      *@author zhangsan
      *@date   16/10/21 上午9:10
      */
    public void setIntrestsSelectString(String intrests){
        if(!TextUtils.isEmpty(intrests)) {
            intrestsStrList = Arrays.asList(intrests.split(","));
        } else {
            intrestsStrList = new ArrayList<>();
        }
    }

    public void setOnInterestsResult(ChooseInterestsDialog.onInterestsResult onInterestsResult) {
        this.onInterestsResult = onInterestsResult;
    }

    onInterestsResult onInterestsResult;

    public interface onInterestsResult {
        void onInterestsResult(String result);
    }

    class ParentCodeAdapter extends CommonAdapter<ParentCodeInfo> {

        public ParentCodeAdapter(Context context, int layoutId, List<ParentCodeInfo> datas) {
            super(context, layoutId, datas);
        }

        @Override
        protected void convert(ViewHolder holder, final ParentCodeInfo item, int position) {
            final TextView tvContent = holder.getView(R.id.tv_content);
            tvContent.setText(item.getItemName());
            tvContent.setSelected(item.getSelected());
            tvContent.setOnClickListener(new NoDoubleClicksListener() {
                @Override
                public void noDoubleClick(View v) {
                    item.setSelected(!item.getSelected());
                    tvContent.setSelected(item.getSelected());
                }
            });
        }

        public String getSelectInterests() {
            StringBuilder sb = new StringBuilder();
            for (ParentCodeInfo parentCodeInfo : parentCodeInfos) {
                if (parentCodeInfo.getSelected()) {
                    sb.append(parentCodeInfo.getItemName()).append(",");
                }
            }

            return sb.length() > 1 ? sb.substring(0, sb.length() - 1) : sb.toString();
        }
    }


}
