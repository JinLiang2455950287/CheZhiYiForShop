package com.ruanyun.chezhiyi.commonlib.hxchat.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.ruanyun.chezhiyi.commonlib.App;
import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.GoodsInfo;
import com.ruanyun.chezhiyi.commonlib.util.AppUtility;
import com.ruanyun.chezhiyi.commonlib.util.Base64;
import com.ruanyun.chezhiyi.commonlib.util.C;
import com.ruanyun.chezhiyi.commonlib.util.FileUtil;
import com.ruanyun.chezhiyi.commonlib.view.ui.common.WebViewActivity;

/**
 * Description:
 * author: zhangsan on 16/10/27 上午9:04.
 */
public class ShareGoodsChatRow
        extends EaseChatRow {
    static final String DEFAUT_GOODS_JSON = "{\"goodsName\":\"商品名称\",\"activityPrice\":0.1,\"scoreNumber\":0.0,\"mainPhoto\":\"\",\"totalCount\":0,\"isPrice\":false,\"priceType\":\"normal\"}";
    ImageView imgGoods;
    TextView tvGoodsTitle, tvGoodsPrice;
    private GoodsInfo mGoodsInfo;
    private String mJson;

    public ShareGoodsChatRow(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_goods_share_receive : R.layout.ease_row_goods_share, this);
    }

    @Override
    protected void onFindViewById() {
        imgGoods = (ImageView) findViewById(R.id.img_goods);
        tvGoodsTitle = (TextView) findViewById(R.id.tv_goods_title);
        tvGoodsPrice = (TextView) findViewById(R.id.tv_goods_price);
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSetUpView() {
        try {
            mJson = message.getStringAttribute(C.IntentKey.GOODS_JSON, DEFAUT_GOODS_JSON);
            mGoodsInfo = new Gson().fromJson(mJson, GoodsInfo.class);
            mGoodsInfo.setGoodsName(new String(Base64.decode(mGoodsInfo.getGoodsName())));
            mGoodsInfo.setViceTitle(new String(Base64.decode(mGoodsInfo.getViceTitle())));
            if (mGoodsInfo != null) {
                tvGoodsPrice.setText(new StringBuilder("¥").append(mGoodsInfo.getActivityPrice()).toString());
                tvGoodsTitle.setText(mGoodsInfo.getGoodsName());
                Glide.with(context).load(FileUtil.getFileUrl(mGoodsInfo.getMainPhoto()))
                        .into(imgGoods);
            }
        } catch (Exception e) {

        }

    }

    @Override
    protected void onBubbleClick() {
        String url = message.getStringAttribute(C.IntentKey.GOODS_URL, "");
        if (App.getInstance().isClient()) {
            Intent intent = AppUtility.getWebIntent(context, new StringBuilder(url).append("?buyUserNum=").append(App.getInstance()
                    .getCurrentUserNum()).toString(), WebViewActivity.CP);
            intent.putExtra(C.IntentKey.SHARE_CP_NAME, mGoodsInfo.getGoodsName());
            intent.putExtra(C.IntentKey.SHARE_CP_IMAGE, mGoodsInfo.getMainPhoto());
            intent.putExtra(C.IntentKey.SHARE_CP_SUBTITLE, mGoodsInfo.getViceTitle());
            intent.putExtra(C.IntentKey.SEND_TO_FRIEND, true);
            intent.putExtra(C.IntentKey.GOODS_JSON, mJson);
            context.startActivity(intent);
        } else {
            Intent intent = AppUtility.getWebIntent(context, url, WebViewActivity.CP);
            intent.putExtra(C.IntentKey.SHARE_CP_NAME, mGoodsInfo.getGoodsName());
            intent.putExtra(C.IntentKey.SHARE_CP_IMAGE, mGoodsInfo.getMainPhoto());
            intent.putExtra(C.IntentKey.SHARE_CP_SUBTITLE, mGoodsInfo.getViceTitle());
            intent.putExtra(C.IntentKey.SEND_TO_FRIEND, true);
            intent.putExtra(C.IntentKey.GOODS_JSON, mJson);
            context.startActivity(intent);
        }
    }
}
