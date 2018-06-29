package com.sskj.bfex.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * ProjectName：（项目名称）
 * DESC: (类描述)
 * Created by 吕志豪 on 2018/04/19 16:21
 * updateName:(吕志豪)
 * updateTime:(16:21)
 * updateDesc:(修改内容)
 */

public class EditChangeUtil {
    public static void onChange(EditText editText, OnChange onChange) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (onChange != null) {
                    onChange.change(s);
                }
            }
        });

    }

    public interface OnChange {
        void change(Editable e);
    }

}
