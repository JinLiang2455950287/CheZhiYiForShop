package com.ruanyun.czy.client.view.ui.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.base.PageInfoBase;
import com.ruanyun.chezhiyi.commonlib.base.RefreshBaseActivity;
import com.ruanyun.chezhiyi.commonlib.model.CommentUserTelephoneInfo;
import com.ruanyun.chezhiyi.commonlib.model.params.PageParamsBase;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.czy.client.R;
import com.ruanyun.czy.client.view.adapter.CommentUserTelephoneAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by msq on 2016/9/10.
 */
public class ActivityCommonUserTelephone extends RefreshBaseActivity implements Topbar.onTopbarClickListener {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.tv_110)
    TextView tv110;
    @BindView(R.id.tv_122)
    TextView tv122;
    @BindView(R.id.tv_120)
    TextView tv120;
    @BindView(R.id.tv_119)
    TextView tv119;
    @BindView(R.id.list)
    ListView lv;

    CommentUserTelephoneAdapter commentUserTelephoneAdapter;
    PageParamsBase params = new PageParamsBase();
    List<CommentUserTelephoneInfo> commentUserTelephoneInfo;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_common_user_telephone);
        ButterKnife.bind(this);
        initRefreshLayout();
        initView();
        initData();
        initAdapter();
    }

    private void initView() {
        refreshWithAnim();
        topbar.setTttleText("常用电话")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .setTopbarClickListener(this);

    }

    private void initData(){
        params.setNumPerPage(10);
    }

    private void initAdapter(){
        commentUserTelephoneInfo = new ArrayList<>();
        commentUserTelephoneAdapter = new CommentUserTelephoneAdapter(this,R.layout.list_item_comment_user_telephone,commentUserTelephoneInfo);
        lv.setAdapter(commentUserTelephoneAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CommentUserTelephoneInfo commentUserTelephoneInfo = commentUserTelephoneAdapter.getItem(i);
                onStartIntent(commentUserTelephoneInfo.getContent());
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

    /**
     * 启动一个意图
     */
    private void onStartIntent(final String telephone) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(telephone).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + telephone)));
                dialog.dismiss();
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    @Override
    public Call loadData() {
        params.setPageNum(currentPage);
        return app.getApiService().getCommentUserTelephoneList(app.getCurrentUserNum(),params);
    }

    @Override
    public void onRefreshResult(PageInfoBase result, String tag) {
        commentUserTelephoneAdapter.setData(result.getResult());
    }

    @Override
    public void onLoadMoreResult(PageInfoBase result, String tag) {
        commentUserTelephoneAdapter.addData(result.getResult());
    }

    @OnClick({R.id.tv_110, R.id.tv_122, R.id.tv_120, R.id.tv_119})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_110:
                onStartIntent("110");
                break;
            case R.id.tv_122:
                onStartIntent("122");
                break;
            case R.id.tv_120:
                onStartIntent("120");
                break;
            case R.id.tv_119:
                onStartIntent("119");
                break;
        }
    }
}
