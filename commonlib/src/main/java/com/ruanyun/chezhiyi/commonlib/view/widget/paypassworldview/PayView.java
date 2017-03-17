package com.ruanyun.chezhiyi.commonlib.view.widget.paypassworldview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付密码控件
 * Created by hdl on 2016/11/8.
 */

public class PayView extends AutoRelativeLayout {
    private Context mContext;
    private String mStringPassword; //输入的密码
    private TextView[] mTextViewPsw; // 用数组保存6个TextView
    private GridView mGridView; //支付键盘布局
    private ArrayList<Map<String, String>> valueList;
    private ImageView mImageViewCancel;
    private TextView mTextViewForgetPsw;
    private int currentIndex = -1;// 用于记录当前输入密码格位置
    private View mView;
    private TextView mTextViewTitle;
    private TextView mTextViewDel;
    public PayView(Context context) {
        super(context, null);
    }
    public PayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext =  context;
        mView = View.inflate(context, R.layout.pay_view, null);
        valueList = new ArrayList<>();
        mTextViewPsw = new TextView[6];
        mImageViewCancel = (ImageView) mView.findViewById(R.id.iv_pay_back);
        mTextViewPsw[0] = (TextView) mView.findViewById(R.id.tv_pass1);
        mTextViewPsw[1] = (TextView) mView.findViewById(R.id.tv_pass2);
        mTextViewPsw[2] = (TextView) mView.findViewById(R.id.tv_pass3);
        mTextViewPsw[3] = (TextView) mView.findViewById(R.id.tv_pass4);
        mTextViewPsw[4] = (TextView) mView.findViewById(R.id.tv_pass5);
        mTextViewPsw[5] = (TextView) mView.findViewById(R.id.tv_pass6);
        mGridView = (GridView) mView.findViewById(R.id.gv_keybord);
        mTextViewTitle = (TextView) mView.findViewById(R.id.tv_pay_title);
        mTextViewForgetPsw = (TextView) mView.findViewById(R.id.tv_pay_forgetPwd);
        setView();
        addView(mView); //必须要，不然不显示控件
    }

    // 初始化按钮上应该显示的数字
    private void setView() {
        for (int i = 1; i < 13; i++) {
            Map<String, String> map = new HashMap<>();
            if (i < 10) {
                map.put("name", String.valueOf(i));
            } else if (i == 10) {
                map.put("name", "");
            } else if (i == 11) {
                map.put("name", String.valueOf(0));
            } else if (i == 12) {
                map.put("name", "");
            }
            valueList.add(map);
        }
        mGridView.setAdapter(adapter);
    }
    /**
     * 设置监听方法，在第6位输入完后触发
     */
    public void setOnFinishInput(final OnPasswordInputFinish pass) {
        mTextViewPsw[5].addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1) {
                    mStringPassword = ""; //每次触发都要将mStringPassword置空再重新获取，避免由于输入删除再输入造成混乱
                    for (int i = 0; i < 6; i++) {
                        mStringPassword += mTextViewPsw[i].getText().toString().trim();
                    }
                    pass.inputFinish(mStringPassword);//接口中要实现的方法，完成密码输入完成后的响应逻辑
                }
            }
        });
    }

    public interface OnPasswordInputFinish{
        void inputFinish(String passStr);
    }

    public void clearPassword(){
        currentIndex = -1;
        mStringPassword = "";
        for (TextView textView : mTextViewPsw) {
            textView.setText("");
        }
    }

    /**
     * 获取输入的密码
     */
    public String getPassword() {
        return mStringPassword;
    }
    /**
     * 返回取消支付的ImageView
     */
    public ImageView getCancel() {
        return mImageViewCancel;
    }
    /**
     * 返回忘记密码的TextView
     */
    public TextView getForgetPsw() {
        return mTextViewForgetPsw;
    }
    /**
     * 返回标题的TextView
     */
    public TextView getTitle() {
        return mTextViewTitle;
    }
    // GridView的适配器
    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return valueList.size();
        }
        @Override
        public Object getItem(int position) {
            return valueList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.item_pay_gride, null);
                holder = new ViewHolder();
                holder.btnKey = (TextView) convertView.findViewById(R.id.btn_keys);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.btnKey.setText(valueList.get(position).get("name"));
            if (position == 9) {
                holder.btnKey.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
            if (position == 11) {
                holder.btnKey.setBackgroundResource(R.drawable.del);
            }
            holder.btnKey.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position < 11 && currentIndex != 9&&position!=9) { //点击0-9按钮
                        if (currentIndex >= -1 && currentIndex < 5) { //判断输入位置
                            mTextViewPsw[++currentIndex].setText(valueList.get(position).get("name"));
                        }
                    } else {
                        if (position == 11) { //点击退格键
                            if (currentIndex - 1 >= -1) { // 判断是否删除完毕
                                mTextViewPsw[currentIndex--].setText("");
                            }
                        }
                        if(position==9){
                        }
                    }
                }
            });
            return convertView;
        }
    };
    static class ViewHolder {
        public TextView btnKey;
    }
}
