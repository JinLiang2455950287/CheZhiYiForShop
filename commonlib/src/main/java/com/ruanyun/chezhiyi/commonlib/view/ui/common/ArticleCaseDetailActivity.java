package com.ruanyun.chezhiyi.commonlib.view.ui.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.model.ArticleInfo;
import com.ruanyun.chezhiyi.commonlib.model.CaseInfo;
import com.ruanyun.chezhiyi.commonlib.model.Event;
import com.ruanyun.chezhiyi.commonlib.presenter.CommentZanPresenter;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.util.LogX;
import com.ruanyun.chezhiyi.commonlib.util.NoDoubleClicksListener;
import com.ruanyun.chezhiyi.commonlib.view.CommentZanMvpView;
import com.ruanyun.chezhiyi.commonlib.view.widget.CommentEdittext;
import com.ruanyun.chezhiyi.commonlib.view.widget.EmojiFiltrationTextWatcher;
import com.ruanyun.chezhiyi.commonlib.view.widget.FunctionMenuPopWindow;
import com.ruanyun.chezhiyi.commonlib.view.widget.RYEmptyView;
import com.ruanyun.chezhiyi.commonlib.view.widget.SharePopWindow;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Description:文章案例公用详情
 * author: zhangsan on 16/9/9 下午4:24.
 */
public class ArticleCaseDetailActivity extends AutoLayoutActivity implements CommentZanMvpView, Topbar.onTopbarClickListener, FunctionMenuPopWindow.OnPopupFunvtionMenuClickListener {
    static final int MENU_ICONS[] = {R.drawable.my_percase_menu_edit, R.drawable.my_percase_menu_delete
            , R.drawable.my_percase_menu_share};
    static final String[] MENU_LABLE = {"编辑", "删除", "分享"};
    LinearLayout llRoot;
    RYEmptyView emptyView;
    Topbar topbar;
    WebView webview;
    CommentEdittext edtComment;
    TextView btnZan;
    ArticleInfo articleInfo;
    CaseInfo caseInfo;
    CommentZanPresenter commentZanPresenter;
    int zanCount = 0;//点赞数量
    boolean isRequestResponse = true;
    String comment_type = "", url = "", title = "", jsCode = "javascript:$('.float_layer').hide();";
    FunctionMenuPopWindow menuPopup;
    SharePopWindow sharePopWindow;
    private boolean loadError = false;
    private FrameLayout flComment;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_article_detail);
        initView();
        registerBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBus();
        llRoot.removeView(webview);
        webview.destroy();
        webview = null;
    }

    private void initView() {
        comment_type = getIntent().getStringExtra(C.IntentKey.CASE_LIB_TYPE);
        url = getIntent().getStringExtra(C.IntentKey.WEBVIEW_URL);
        title = getIntent().getStringExtra(C.IntentKey.WEBVIEW_TITLE);
        commentZanPresenter = new CommentZanPresenter();
        commentZanPresenter.attachView(this);
        llRoot = getView(R.id.ll_root);
        topbar = getView(R.id.topbar);
        webview = getView(R.id.webview);
        btnZan = getView(R.id.btn_zan);
        emptyView = getView(R.id.emptyview);
        edtComment = getView(R.id.edt_comment);
        flComment = getView(R.id.fl_comment);
        //edtComment.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        emptyView.bind(webview);
        emptyView.onReloadBtnClick(this, "reload");
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        setupListener();
        topbar.setTttleText(title)
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .enableRightImageBtn()
                .setRightImgBtnBg(R.drawable.nav_share)
                .onRightImgBtnClick()
                .setTopbarClickListener(this);
        topbar.getLayoutParamsTitle().addRule(RelativeLayout.RIGHT_OF, R.id.img_btn_left);
        topbar.getLayoutParamsTitle().addRule(RelativeLayout.LEFT_OF, R.id.img_btn_right);
        topbar.getTvTitle().setGravity(Gravity.CENTER);
        topbar.getTvTitle().setSingleLine();
//        if (!app.isClient()&&comment_type.equals(TYPE_CASE_LIBS)&&) {
//            topbar.setRightImgBtnBg(R.drawable.my_percase_menu);
//            menuPopup = new FunctionMenuPopWindow(mContext, topbar, MENU_ICONS, MENU_LABLE);
//            menuPopup.setOnPopupFunvtionMenuClickListener(this);
//        }
        sharePopWindow = new SharePopWindow(mContext);
        sharePopWindow.setShareUrl(url);
        sharePopWindow.setShareApp(false);

        if (comment_type.equals(TYPE_ARTICLE)) {        //文章
            articleInfo = getIntent().getParcelableExtra(C.IntentKey.ARTICLE_INFO);
            zanCount = articleInfo.getPraiseCount();
            btnZan.setText(getString(R.string.zan_num, zanCount));
            commentZanPresenter.isPraised(articleInfo.getArticleNum(), comment_type);

            sharePopWindow.setShareTitle(articleInfo.getTitle());
            sharePopWindow.setShareText(articleInfo.getSummary());
            sharePopWindow.setShareImage(FileUtil.getFileUrl(articleInfo.getMainPhoto()));
        } else {  //案例
            caseInfo = getIntent().getParcelableExtra(C.IntentKey.CASE_LIB_INFO);
            if (!app.isClient() && caseInfo.getUserNum().equals(app.getCurrentUserNum())) {
                topbar.setRightImgBtnBg(R.drawable.my_percase_menu);
                menuPopup = new FunctionMenuPopWindow(mContext, topbar, MENU_ICONS, MENU_LABLE);
                menuPopup.setOnPopupFunvtionMenuClickListener(this);
            }
            zanCount = caseInfo.getPraiseCount();
            btnZan.setText(getString(R.string.zan_num, zanCount));
            commentZanPresenter.isPraised(caseInfo.getLibraryNum(), comment_type);

            //  案例分享的标题和内容一样
            sharePopWindow.setShareTitle(caseInfo.getLibraryName());
            sharePopWindow.setShareText(caseInfo.getLibraryName());
            sharePopWindow.setShareImage(FileUtil.getFileUrl(caseInfo.getMainPhoto()));
        }
        webview.loadUrl(url);
        LogX.d(TAG, url);

    }


    private void setZanBtnText() {
        btnZan.setText(getString(R.string.zan_num, zanCount));
    }

    private void setupListener() {
        webview.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!loadError) {//当网页加载成功的时候判断是否加载成功
                    webview.setEnabled(true);
                } else { //加载失败的话，初始化页面加载失败的图，然后替换正在加载的视图页面
//                    webview.loadUrl("file:///android_asset/error.html");
                    flComment.setVisibility(View.GONE);
                    topbar.setRightImgBtnNull().getImgTitleRight().setVisibility(View.INVISIBLE);
//                    loadError=false;
                    emptyView.showLoadError();
                }
            }

            @Override
            public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebViewClient.a a) {
                super.onReceivedError(webView, webResourceRequest, a);
                loadError = true;
            }
        });
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                    webview.loadUrl(jsCode);
                    emptyView.loadSuccuss();
                } else {
                    emptyView.showLoading();
                }
            }

            /**
             * 当WebView加载之后，返回 HTML 页面的标题 Title
             * @param view
             * @param title
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
                if(!TextUtils.isEmpty(title)&&title.toLowerCase().contains("error")){
                    loadError = true;
                } else {
                    loadError = false;
                }
            }
        });
        //过滤emoji
        edtComment.addTextChangedListener(new EmojiFiltrationTextWatcher(edtComment));
        edtComment.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (comment_type.equals(TYPE_ARTICLE)) {
                        commentZanPresenter.comment(edtComment.getText().toString().trim(), comment_type, articleInfo.getArticleNum());
                    } else {
                        commentZanPresenter.comment(edtComment.getText().toString().trim(), comment_type, caseInfo.getLibraryNum());
                    }
                }
                return false;
            }
        });

        btnZan.setOnClickListener(new NoDoubleClicksListener() {
            @Override
            public void noDoubleClick(View v) {
                if (comment_type.equals(TYPE_ARTICLE) && isRequestResponse) {
                    commentZanPresenter.zanOrCancel(articleInfo.getArticleNum(), comment_type);
                } else if (/*comment_type.equals(TYPE_CASE_LIBS) && */ isRequestResponse) {
                    commentZanPresenter.zanOrCancel(caseInfo.getLibraryNum(), comment_type);
                }
                isRequestResponse = false;
            }
        });
    }


    /**
     * 重新加载页面
     */
    public void reload() {
        webview.reload();
    }

    @Override
    public void onCommentSuccess() {
        edtComment.setText("");
        reload();
    }

    /**
     * 点赞取消赞回调
     *
     * @author zhangsan
     * @date 16/10/28 下午4:30
     */
    @Override
    public void onPraisedResult() {
        EventBus.getDefault().post(new Event<String>(C.EventKey.KEY_REFRESH_LIST, ""));
        isRequestResponse = true;
        if (btnZan.isSelected()) {
            zanCount -= 1;
        } else {
            zanCount += 1;
        }
        setZanBtnText();
        btnZan.setSelected(!btnZan.isSelected());
    }

    @Override
    public void showToast(String msg) {
        AppUtility.showToastMsg(msg);
    }

    @Override
    public void showLoadingDlg(String msg) {
        showLoading(msg);
    }

    @Override
    public void dissMisDlg() {
        dissMissLoading();
    }

    @Override
    public void getPraiseStatusFail() {
        btnZan.setEnabled(false);
    }

    @Override
    public void getPraiseStatusSuccess(int status) {
        switch (status) {
            case STATUS_PRAISED:
                btnZan.setSelected(true);
                break;
            case STATUS_UNPRAISED:

                break;
        }
    }

    @Override
    public void caseInfoReulst(CaseInfo caseInfo) {
        Intent intent = new Intent();
        intent.setClassName(mContext, "com.ruanyun.chezhiyi.view.ui.mine.NewCaseActivity");
        intent.putExtra(C.IntentKey.CASE_INFO, caseInfo);
        showActivity(intent);
    }

    @Override
    public void delCaseSuccess() {
        EventBus.getDefault().post(new Event<String>(C.EventKey.KEY_REFRESH_LIST, ""));
        finish();
    }

    @Override
    public void delCaseFail() {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.img_btn_left) {
            finish();
        } else if (viewId == R.id.img_btn_right) {
            if (!app.isClient() && comment_type.equals(TYPE_CASE_LIBS) && menuPopup !=null) {
                menuPopup.show();
            } else {
//                AppUtility.showShare(mContext, url);
                sharePopWindow.showBottom(v);
            }
        }
    }

    @Override
    public void onFunctionMenuItemClick(int type) {
        switch (type) {
            case FunctionMenuPopWindow.COMPILE://编辑案例
                commentZanPresenter.getCaseLibDetaiLInfo(caseInfo.getLibraryNum());
                break;
            case FunctionMenuPopWindow.DELETE://删除
                commentZanPresenter.delCaseLib(caseInfo.getLibraryNum());
                break;
            case FunctionMenuPopWindow.SHARE://点击分享
//                AppUtility.showShare(mContext, url);
                sharePopWindow.showBottom(llRoot);
                break;
        }
    }


    @Subscribe
    public void  refresh(Event<String> event){
        if (event.key.equals(C.EventKey.KEY_REFRESH_WEB)) {
            reload();
        }
    }
}
