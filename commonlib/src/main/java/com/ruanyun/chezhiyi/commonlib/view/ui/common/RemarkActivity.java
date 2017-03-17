package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.view.widget.EmojiFiltrationTextWatcher;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import butterknife.ButterKnife;

/**
 * 个人说明  备注等  公用界面
 * Created by Sxq on 2016/9/11.
 */
public class RemarkActivity extends AutoLayoutActivity implements Topbar.onTopbarClickListener {
    Topbar topbar;
    EditText editContent;
    TextView tvWordsCount;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_personalized_signation);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topbar=getView(R.id.topbar);
        editContent=getView(R.id.edit_content);
        tvWordsCount=getView(R.id.tv_words_count);
        String title = getIntent().getStringExtra(C.IntentKey.TOPBAR_TITLE);
        String context = getIntent().getStringExtra(C.IntentKey.EDIT_CONTEXT);
        topbar.setTttleText(title)
                .onBackBtnClick()
                .setBackBtnEnable(true)
                .setRightText("完成")
                .addViewToTopbar(topbar.getTvTitleRight(), topbar.getLayoutParamsImgRight())
                .onRightTextClick()
                .setTopbarClickListener(this);

        editContent.addTextChangedListener(new EmojiFiltrationTextWatcher(editContent) {
            @Override
            public void emojiFiltAfterTextChanged(Editable s) {
                tvWordsCount.setText(s.length() + "/50 个字");
                if (TextUtils.isEmpty(s.toString())) {
                    setRightClickAble(false);
                } else {
                    setRightClickAble(true);
                }
            }
        });
//        editContent.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                tvWordsCount.setText(s.length() + "/50 个字");
//                if (TextUtils.isEmpty(s.toString())) {
//                    setRightClickAble(false);
//                } else {
//                    setRightClickAble(true);
//                }
//            }
//        });
        editContent.setText(context);
        if (TextUtils.isEmpty(context)) {
            setRightClickAble(false);
        } else {
            editContent.setSelection(context.length());
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
        if (i == R.id.img_btn_left) {
            finish();

        } else if (i == R.id.tv_title_right) {
            updateSigture();

        }
    }

    private void updateSigture() {
        Intent intent = new Intent();
        intent.putExtra(C.IntentKey.UPDATE_SIGNATURE,editContent.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}
