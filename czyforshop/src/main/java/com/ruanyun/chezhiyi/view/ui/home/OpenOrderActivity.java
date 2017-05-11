package com.ruanyun.chezhiyi.view.ui.home;

import android.Manifest;
import android.content.Intent;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ruanyun.chezhiyi.R;
import com.ruanyun.chezhiyi.commonlib.base.AutoLayoutActivity;
import com.ruanyun.chezhiyi.commonlib.hxchat.runtimepermissions.PermissionsManager;
import com.ruanyun.chezhiyi.commonlib.hxchat.runtimepermissions.PermissionsResultAction;
import com.ruanyun.chezhiyi.commonlib.view.widget.Topbar;
import com.ruanyun.chezhiyi.view.ui.workorder.CustomerReceptionActivity;
import com.wintone.demo.plateid.MemoryCameraActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.ruanyun.chezhiyi.view.ui.workorder.CustomerReceptionActivity.REQ_REC_PLATENUM;

/*
* 开单页面 获取 扫描 车牌号码
* 2017.3.8
* jin
* */

public class OpenOrderActivity extends AutoLayoutActivity implements View.OnFocusChangeListener, View.OnTouchListener,
        Topbar.onTopbarClickListener {

    @BindView(R.id.topbar)
    Topbar topbar;
    @BindView(R.id.edit_1)
    EditText edit1;
    @BindView(R.id.edit_2)
    EditText edit2;
    @BindView(R.id.edit_3)
    EditText edit3;
    @BindView(R.id.edit_4)
    EditText edit4;
    @BindView(R.id.edit_5)
    EditText edit5;
    @BindView(R.id.edit_6)
    EditText edit6;
    private EditText editProvince, currentEdit;
    private TextView img_btn_scan;
    private EditText[] mArray;
    private StringBuffer haopai;
    private KeyboardView mKeyboardView;

    /**
     * 省份简称键盘
     */
    private Keyboard province_keyboard;
    /**
     * 数字与大写字母键盘
     */
    private Keyboard number_keyboar;
    private int currentIndex;
    private boolean textWatcherEnable = true;
    String[] permissions = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        topbar = getView(R.id.topbar);
        topbar.setTttleText("车牌输入")
                .setBackBtnEnable(true)
                .onBackBtnClick()
                .enableRightImageBtn()
                .setRightImgBtnBg(R.drawable.icon_scan_white)
                .onRightImgBtnClick()
                .setTopbarClickListener(this);
        topbar.getLayoutParamsTitle().addRule(RelativeLayout.RIGHT_OF, R.id.img_btn_left);
        topbar.getLayoutParamsTitle().addRule(RelativeLayout.LEFT_OF, R.id.img_btn_right);
        topbar.getTvTitle().setGravity(Gravity.CENTER);
        topbar.getTvTitle().setSingleLine();


        province_keyboard = new Keyboard(this, R.xml.province_abbreviation);
        number_keyboar = new Keyboard(this, R.xml.number_or_letters);
        mKeyboardView = (KeyboardView) findViewById(R.id.keyboard_view);
        mKeyboardView.setKeyboard(province_keyboard);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(listener);
        editProvince = (EditText) findViewById(R.id.edit_province);
        mArray = new EditText[]{editProvince, edit1, edit2, edit3, edit4, edit5, edit6};
        img_btn_scan = (TextView) findViewById(R.id.img_btn_scan);


        editProvince.setOnFocusChangeListener(this);
        edit1.setOnFocusChangeListener(this);
        edit2.setOnFocusChangeListener(this);
        edit3.setOnFocusChangeListener(this);
        edit4.setOnFocusChangeListener(this);
        edit5.setOnFocusChangeListener(this);
        edit6.setOnFocusChangeListener(this);

        editProvince.setOnTouchListener(this);
        edit1.setOnTouchListener(this);
        edit2.setOnTouchListener(this);
        edit3.setOnTouchListener(this);
        edit4.setOnTouchListener(this);
        edit5.setOnTouchListener(this);
        edit6.setOnTouchListener(this);


        for (int i = 0; i < mArray.length; i++) {
            final int j = i;
            mArray[j].addTextChangedListener(new TextWatcher() {
                private CharSequence temp;
                private int sStart;
                private int sEnd;

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    temp = s;
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    sStart = mArray[j].getSelectionStart();
                    sEnd = mArray[j].getSelectionEnd();

                    if (temp.length() == 1 && j < mArray.length - 1) {
                        mArray[j + 1].setFocusable(true);
                        mArray[j + 1].setFocusableInTouchMode(true);
                        mArray[j + 1].requestFocus();
                    }
                    if (temp.length() > 1) {

                        s.delete(0, 1);
                        int tSelection = sStart;
                        mArray[j].setText(s);
                        mArray[j].setSelection(tSelection);
                        mArray[j].setFocusable(true);
                    }
                }
            });
        }

//        editProvince.setFocusable(true);
//        editProvince.requestFocus();
        edit2.setFocusable(true);
        edit2.requestFocus();

        img_btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (haopai != null) {
                    if (haopai.length() == 7) {
//                        Intent intent = new Intent(OpenOrderActivity.this, CustomerReceptionActivity.class);
                        Intent intent = new Intent(OpenOrderActivity.this, QuickOpenOrderActivity.class);
                        intent.putExtra("plateNumber", haopai.toString());
                        startActivity(intent);
                    } else {
                        Toast.makeText(OpenOrderActivity.this, "输入错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 跳转到车牌识别页面
     *
     * @author zhangsan
     * @date 16/10/12 下午4:34
     */
    private void showVideoRecPlateNum() {
        Intent video_intent = new Intent();
        video_intent.putExtra("camera", true);
        video_intent.setClass(getApplicationContext(), MemoryCameraActivity.class);
        startActivityForResult(video_intent, REQ_REC_PLATENUM);
        textWatcherEnable = false;
    }

    //获取扫描车牌号结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_REC_PLATENUM) {
            String carNumber = data.getCharSequenceExtra("number").toString();
            Intent intent = new Intent(OpenOrderActivity.this, CustomerReceptionActivity.class);
            intent.putExtra("plateNumber", carNumber);
            startActivity(intent);
        }
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = currentEdit.getText();
            int start = currentEdit.getSelectionStart();
            if (primaryCode == -1) {                //隐藏键盘
                if (isShow()) {
                    hideKeyboard();
                }
            } else if (primaryCode == -3) {// 回退

                Log.i("Tag1111", "onKey currentIndex= " + currentIndex);
                if (editable != null) {
                    //没有输入内容时软键盘重置为省份简称软键盘
                    editable.clear();

                    if (currentIndex > 0) {
                        currentIndex = currentIndex - 1;
                        mArray[currentIndex].setFocusable(true);
                        mArray[currentIndex].requestFocus();
                    }
                }

            } else {
                editable.insert(start, Character.toString((char) primaryCode));
                if (currentIndex == 6 && mArray[6].getText().length() > 0) {
                    hideKeyboard();
                    haopai = new StringBuffer();
                    for (int i = 0; i < mArray.length; i++) {
                        haopai.append(mArray[i].getText().toString());
                    }
                }
            }
        }
    };

    /**
     * 指定切换软键盘 isnumber false表示要切换为省份简称软键盘 true表示要切换为数字软键盘
     */
    public void changeKeyboard(boolean isnumber) {
        if (isnumber) {
            mKeyboardView.setKeyboard(number_keyboar);
        } else {
            mKeyboardView.setKeyboard(province_keyboard);
        }
    }

    /**
     * 软键盘展示
     */
    public void showKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 软键盘隐藏
     */
    public void hideKeyboard() {
        int visibility = mKeyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyboardView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 禁掉系统软键盘
     */
    public void hideSoftInputMethod() {

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            currentEdit.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName,
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(currentEdit, false);
            } catch (NoSuchMethodException e) {
                currentEdit.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 软键盘展示状态
     */
    public boolean isShow() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShow()) {
                hideKeyboard();
            } else {
                finish();
            }
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            currentEdit = (EditText) v;
            for (int i = 0; i < mArray.length; i++) {

                EditText temp = mArray[i];
                if (temp == (EditText) v) {
                    currentIndex = i;
                }
            }
            if (currentIndex == 0) {
                changeKeyboard(false);
            } else {
                changeKeyboard(true);
            }
            hideSoftInputMethod();

            if (!isShow()) {
                showKeyboard();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!isShow()) {
            showKeyboard();
        }
        return false;
    }


    @Override
    public void onTobbarViewClick(View v) {
        int viewId = v.getId();

        if (viewId == R.id.img_btn_left) {
            finish();
        } else if (viewId == R.id.img_btn_right) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this, permissions, new PermissionsResultAction() {

                    @Override
                    public void onGranted() {
                        showVideoRecPlateNum();
                    }

                    @Override
                    public void onDenied(String permission) {

                    }
                });
            } else {
                showVideoRecPlateNum();
            }


        }

    }
}