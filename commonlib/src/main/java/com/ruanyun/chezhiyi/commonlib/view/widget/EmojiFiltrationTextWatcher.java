package com.ruanyun.chezhiyi.commonlib.view.widget;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.lang.ref.WeakReference;

/**
 * Description ：  Emoji表情过滤器
 * <p/>
 * Created by ycw on 2016/11/26.
 */

public class EmojiFiltrationTextWatcher implements TextWatcher {

    WeakReference<EditText> weakReferenceEditText;

    //输入表情前的光标位置
    private int cursorPos;
    //输入表情前EditText中的文本
    private String inputAfterText;
    //是否重置了EditText的内容
    private boolean resetText;

    public EmojiFiltrationTextWatcher(EditText editText) {
//        this.weakReferenceEditText = editText;
        weakReferenceEditText = new WeakReference<EditText>(editText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (!resetText) {
            cursorPos = weakReferenceEditText.get().getSelectionEnd();
            // 这里用s.toString()而不直接用s是因为如果用s，
            // 那么，inputAfterText和s在内存中指向的是同一个地址，s改变了，
            // inputAfterText也就改变了，那么表情过滤就失败了
            inputAfterText= s.toString();
        }

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!resetText) {
            try {
                if(before != 0) {
                    return;
                }
                if (count >= 2) {       //表情符号的字符长度最小为2
                    CharSequence input = s.subSequence(cursorPos, cursorPos + count);
                    if (containsEmoji(input.toString())) {
                        resetText = true;
    //                    Toast.makeText(App.getInstance(), "不支持输入Emoji表情符号", Toast.LENGTH_SHORT).show();
                        //是表情符号就将文本还原为输入表情符号之前的内容
                        weakReferenceEditText.get().setText(inputAfterText);
                        CharSequence text = weakReferenceEditText.get().getText();
                        if (text instanceof Spannable) {
                            Spannable spanText = (Spannable) text;
                            Selection.setSelection(spanText, text.length());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resetText = false;
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!resetText)
        emojiFiltAfterTextChanged(s);
    }

    public void emojiFiltAfterTextChanged(Editable s) {}

    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }
}
