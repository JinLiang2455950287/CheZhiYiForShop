package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ruanyun.chezhiyi.commonlib.R;
import com.ruanyun.chezhiyi.commonlib.model.SeckillHeadInfo;
import com.ruanyun.chezhiyi.commonlib.view.widget.coutdownview.CountdownView;
import com.ruanyun.chezhiyi.commonlib.view.widget.coutdownview.DynamicConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description:
 * author: zhangsan on 16/9/18 下午7:28.
 */
public class CountDownViewIndicator extends LinearLayout {
    static final int SELECTED_COLOR = R.color.theme_color_default;
    static final int DESELECTED_COLOR_BOARDER = R.color.seckill_boarder_gray;
    static final int DESELECTED_COLOR_BG = R.color.seckill_bg_gray;
    static final int WHITE = R.color.white;
    static final long DAY_TIME = 24*60*60*1000;
    public static final int STATUS_START = 4433;
    public static final int STATUS_END = 87098;

    //ImageView mIndicator;
    TextView mCurrentLessText, mNextLessText;
    CountdownView mCurrentCountdownView, mNextCountdownView;
    //Matrix matrix;
    DynamicConfig.Builder builder;
    DynamicConfig.BackgroundInfo backgroundInfo;
    public int currentStatus;

    public CountDownViewIndicator(Context context) {
        super(context);
    }

    public CountDownViewIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CountDownViewIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_countdown_indicater, this);
        // mIndicator = getView(R.id.img_indicater);
        mCurrentLessText = getView(R.id.tv_current_date);
        mNextLessText = getView(R.id.tv_next_date);
        mCurrentCountdownView = getView(R.id.countdwn_view_current);
        mNextCountdownView = getView(R.id.countdwn_view_next);
        // matrix = new Matrix();
        builder = new DynamicConfig.Builder();
        backgroundInfo = new DynamicConfig.BackgroundInfo();
        //  mIndicator.setImageMatrix();
    }

 /*   public void setTitle(String startTime, String endTime) {
        mCurrentLessText.setText(getContext().getString(R.string.seckill_end_time, subTimeStr(endTime)));
        mNextLessText.setText(getContext().getString(R.string.seckill_start_time, subTimeStr(startTime)));
    }*/

    public void onCoutDownViewSelected(CountdownView countdownView) {
        backgroundInfo.setColor(getResColor(SELECTED_COLOR))
                .setShowTimeBgDivisionLine(true)
                .setDivisionLineColor(getResColor(WHITE));
        builder.setBackgroundInfo(backgroundInfo)
                .setTimeTextColor(getResColor(WHITE))
                .setSuffixTextColor(getResColor(SELECTED_COLOR));
        countdownView.dynamicShow(builder.build());
    }

    public void onCoutDownViewDeSelected(CountdownView countdownView) {
        backgroundInfo.setColor(getResColor(R.color.white))
                .setBorderSize(1f)
                .setBorderColor(getResColor(DESELECTED_COLOR_BOARDER))
                .setShowTimeBgDivisionLine(false);
        builder.setBackgroundInfo(backgroundInfo)
                .setTimeTextColor(getResColor(DESELECTED_COLOR_BG))
                .setSuffixTextColor(getResColor(DESELECTED_COLOR_BG));
        countdownView.dynamicShow(builder.build());
    }


    public void onPageScrolled(float translationX) {
        // matrix.postTranslate(translationX, 0);
        // mIndicator.setImageMatrix(matrix);
        // mIndicator.setX(translationX);
    }

    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                onCoutDownViewSelected(mCurrentCountdownView);
                onCoutDownViewDeSelected(mNextCountdownView);
                break;
            case 1:
                onCoutDownViewSelected(mNextCountdownView);
                onCoutDownViewDeSelected(mCurrentCountdownView);
                break;
        }
    }

    public void stopCountDown() {
        mNextCountdownView.stop();
        mNextCountdownView.stop();
    }

    public void startCountDown(SeckillHeadInfo seckillHeadInfo) {
        if (TextUtils.isEmpty(seckillHeadInfo.getSeckillMainInfoNum())) {
            mCurrentLessText.setText("活动已结束");
            if (onCountDownFinshListener != null) {
                onCountDownFinshListener.onCounDownFinish(mCurrentCountdownView, STATUS_END);
            }
            return;
        }
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat timeDf=new SimpleDateFormat("HH:mm");
        try {
            Date currentDate = dateformat.parse(seckillHeadInfo.getCurrentDate());
            Date startTimeDate = dateformat.parse(getDateFromTime(seckillHeadInfo.getCurrentDate(), seckillHeadInfo.getBeginTime()));
            Date endTimeDate = dateformat.parse((getDateFromTime(seckillHeadInfo.getCurrentDate(), seckillHeadInfo.getEndTime())));
            if (currentDate.getTime() < startTimeDate.getTime()) {//小于开始时间
                currentStatus = STATUS_START;
                mCurrentCountdownView.start(startTimeDate.getTime()-currentDate.getTime());
                mCurrentLessText.setText(getContext().getString(R.string.seckill_start_time, subTimeStr(seckillHeadInfo.getBeginTime())));
                mCurrentCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                    @Override
                    public void onEnd(CountdownView cv) {
                        if (onCountDownFinshListener != null) {
                            onCountDownFinshListener.onCounDownFinish(cv, STATUS_START);
                        }
                    }
                });
                if (onCountDownFinshListener != null) {
                    onCountDownFinshListener.onCounDownFinish(mCurrentCountdownView, STATUS_END);
                }
            } else if (currentDate.getTime() >= startTimeDate.getTime() && currentDate.getTime() < endTimeDate.getTime()) {//在秒杀的时间范围内
                currentStatus = STATUS_END;
                mCurrentCountdownView.start(endTimeDate.getTime() - currentDate.getTime());
                mCurrentLessText.setText(getContext().getString(R.string.seckill_end_time, subTimeStr(seckillHeadInfo.getEndTime())));
                mCurrentCountdownView.setOnCountdownEndListener(new CountdownView.OnCountdownEndListener() {
                    @Override
                    public void onEnd(CountdownView cv) {
                        if (onCountDownFinshListener != null) {
                            onCountDownFinshListener.onCounDownFinish(cv, STATUS_END);
                        }
                    }
                });
                if (onCountDownFinshListener != null) {
                    onCountDownFinshListener.onCounDownFinish(mCurrentCountdownView, STATUS_START);
                }
            } else if(currentDate.getTime()>=endTimeDate.getTime()) {
                mCurrentLessText.setText("活动已结束");
                if (onCountDownFinshListener != null) {
                    onCountDownFinshListener.onCounDownFinish(mCurrentCountdownView, STATUS_END);
                }
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void startNextCountDown(SeckillHeadInfo headInfo) {
        if (TextUtils.isEmpty(headInfo.getSeckillMainInfoNum())) {
            mNextLessText.setText("活动已结束");
            return;
        }
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat timeDf=new SimpleDateFormat("HH:mm");
        try {
            Date currentDate = dateformat.parse(headInfo.getCurrentDate());
            Date startTimeDate = dateformat.parse(getDateFromTime(headInfo.getCurrentDate(), headInfo.getBeginTime()));
            long lesstime=DAY_TIME+startTimeDate.getTime();
            mNextCountdownView.start(lesstime-currentDate.getTime());
            mNextLessText.setText(getContext().getString(R.string.seckill_start_time, subTimeStr(headInfo.getBeginTime())));
            /*else if(c){

            }*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private <T extends View> T getView(@IdRes int id) {
        return (T) findViewById(id);
    }

    private int getResColor(@ColorRes int color) {
        return getContext().getResources().getColor(color);
    }

    /**
     * 截取时间前两位
     *
     * @author zhangsan
     * @date 16/9/20 上午9:12
     */
    private String subTimeStr(String s) {
        if (!TextUtils.isEmpty(s) && s.length() > 2) {
            return s.substring(0, 2);
        }
        return "";
    }

    private String getDateFromTime(String currentDate, String time) {
        StringBuilder sb = new StringBuilder(currentDate.substring(0, 10));
        sb.append(" ").append(time).append(":00");
        return sb.toString();
    }

    private String getDateFromDateTime(String date, String time) {
        StringBuilder sb = new StringBuilder(date);
        sb.append(" ").append(time).append(":00");
        return sb.toString();
    }

    public void setOnCountDownFinshListener(CountDownViewIndicator.onCountDownFinshListener onCountDownFinshListener) {
        this.onCountDownFinshListener = onCountDownFinshListener;
    }

    onCountDownFinshListener onCountDownFinshListener;

    public interface onCountDownFinshListener {
        void onCounDownFinish(CountdownView countdownView, int status);
    }
}
