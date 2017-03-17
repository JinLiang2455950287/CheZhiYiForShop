package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.CleanableEditText;
import com.ruanyun.chezhiyi.commonlib.view.widget.EmojiFiltrationTextWatcher;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import butterknife.ButterKnife;

/**
 * 修改昵称
 * Created by Sxq on 2016/9/10.
 */
public class UpdateNickNameActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {

    public static final String STRING_NICK_NAME = "修改昵称";
    public static final String STRING_UPKEEP = "保养里程数";
    public static final String STRING_AUTHORIZATION_CODE = "输入设备授权码";
    Topbar topbar;
    CleanableEditText editName;
    TextView tvWordsCount;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ease_activity_modify_group_name);
        ButterKnife.bind(this);
        initView(getIntent().getStringExtra(C.IntentKey.TOPBAR_TITLE));
    }

    /**
     * 初始化
     */
    private void initView(String topTitle) {
        topbar = getView(R.id.topbar);
        editName = getView(R.id.edit_name);
        tvWordsCount = getView(R.id.tv_words_count);
        if (TextUtils.isEmpty(topTitle)) {
            topTitle = STRING_NICK_NAME;
//            editName.addTextChangedListener();
            editName.setText(app.getUser().getNickName());
            editName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(12)});
        } else if( STRING_UPKEEP.equals(topTitle) ) {  // 修改里程数
            tvWordsCount.setVisibility(View.GONE);
            editName.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        } else {  //  输入设备授权码
            tvWordsCount.setVisibility(View.GONE);
            editName.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        }
        editName.addTextChangedListener(new EmojiFiltrationTextWatcher(editName) {
            @Override
            public void emojiFiltAfterTextChanged(Editable s) {
                tvWordsCount.setText(s.length() + "/12");
                if (TextUtils.isEmpty(s.toString())) {
                    setRightClickAble(false);
                } else {
                    setRightClickAble(true);
                }
            }
        });
        topbar.setTttleText(topTitle).setBackBtnEnable(true)
                .onBackBtnClick()
                .setRightText("完成")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);

        String str = editName.getText().toString();
        if (TextUtils.isEmpty(str)) {
            setRightClickAble(false);
        } else {
            editName.setSelection(str.length());
            setRightClickAble(true);
        }
    }

    /**
     * 设置右上角 点击 使能
     * @param flag
     */
    private void setRightClickAble(boolean flag) {
        if (!flag) {
            topbar.getTvTitleRight().setOnClickListener(null);
            topbar.getTvTitleRight().setAlpha(0.5f);
        } else {
            topbar.onTopbarViewClick(topbar.getTvTitleRight());
            topbar.getTvTitleRight().setAlpha(1f);
        }
    }

    @Override
    public void onTobbarViewClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_title_right) {
            updateNickname();
        } else if (i == R.id.img_btn_left) {
            finish();
        }
    }

    private void updateNickname() {
        Intent intent=new Intent();
        intent.putExtra(C.IntentKey.UPDATE_NICKNAME,editName.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}
