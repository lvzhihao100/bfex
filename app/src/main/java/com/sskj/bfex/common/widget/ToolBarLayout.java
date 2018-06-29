package com.sskj.bfex.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sskj.bfex.R;

/**
 * Created by ruolan on 2015/11/29.
 */
public class ToolBarLayout extends Toolbar {

    private final String mLeftText;
    private Drawable mRightImg;
    private String mTitle;
    private int mBgColor;
    private String mRightText;
    private int mTitleColor;
    private int mRightTextColor;
    private Drawable mLeftImg;
    //添加布局必不可少的工具
    private LayoutInflater mInflater;

    //搜索框
    private EditText mEditSearchView;
    //标题
    private TextView mTextTitle;
    //右边按钮
    public TextView mRightButton;
    //左边按钮
    public TextView mLeftButton;

    private View mView;

    //以下是继承ToolBar必须创建的三个构造方法
    public ToolBarLayout(Context context) {
        this(context, null);
    }

    public ToolBarLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ToolBarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //Set the content insets for this toolbar relative to layout direction.
        setContentInsetsRelative(10, 10);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ToolBarLayout,
                0, 0);
        setBackgroundColor(Color.WHITE);
        try {
            mLeftImg = a.getDrawable(R.styleable.ToolBarLayout_left_img);
            mLeftText = a.getString(R.styleable.ToolBarLayout_left_text);
            mRightImg = a.getDrawable(R.styleable.ToolBarLayout_right_img);
            mRightText = a.getString(R.styleable.ToolBarLayout_right_text);
            mTitle = a.getString(R.styleable.ToolBarLayout_title);
            mTitleColor = a.getInt(R.styleable.ToolBarLayout_title_color, 0);
        } finally {
            a.recycle();
        }


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
    }


    private void initView() {

        if (mView == null) {
            //初始化
            mInflater = LayoutInflater.from(getContext());
            //添加布局文件
            mView = mInflater.inflate(R.layout.layout_toolbar, null);
//
            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mLeftButton = (TextView) mView.findViewById(R.id.toolbar_leftbutton);
            mRightButton = (TextView) mView.findViewById(R.id.toolbar_rightbutton);
            if (mTitleColor != 0)
                mTextTitle.setTextColor(mTitleColor);

            setTitle(mTitle);
            setRightButtonIcon(mRightImg);
            setRightButtonText(mRightText);
            setLeftButtonIcon(mLeftImg);
            setLeftButtonText(mLeftText);
            //然后使用LayoutParams把控件添加到子view中
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);

        }
    }

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        if (mTextTitle != null && !TextUtils.isEmpty(title)) {
            mTitle = title.toString();
            mTextTitle.setText(title);
            showTitleView();
        }
    }

    public String getTitleText() {
        return TextUtils.isEmpty(mTitle) ? "" : mTitle;
    }

    //隐藏标题
    public void hideTitleView() {
        if (mTextTitle != null)
            mTextTitle.setVisibility(GONE);
    }

    //显示标题
    public void showTitleView() {
        if (mTextTitle != null)
            mTextTitle.setVisibility(VISIBLE);
    }

    //隐藏右侧图标
    public void hideRightImgView() {
        if (mRightButton != null) {
            mRightButton.setVisibility(GONE);
        }
    }

    //显示右侧图标
    public void showRightImgView() {
        if (mRightButton != null) {
            mRightButton.setVisibility(VISIBLE);
        }
    }

    //给右侧按钮设置图片，也可以在布局文件中直接引入
    // 如：app:leftButtonIcon="@drawable/icon_back_32px"
    public void setRightButtonIcon(Drawable icon) {
        if (mRightButton != null) {
            mRightButton.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            mRightButton.setVisibility(VISIBLE);
        } else {
            mLeftButton.setVisibility(INVISIBLE);
        }
    }

    //给左侧按钮设置图片，也可以在布局文件中直接引入
    public void setLeftButtonIcon(Drawable icon) {
        if (mLeftButton != null) {
            mLeftButton.setCompoundDrawablesWithIntrinsicBounds(icon, null, null, null);
            mLeftButton.setVisibility(VISIBLE);
        } else {
            mLeftButton.setVisibility(INVISIBLE);
        }
    }

    //给左侧按钮设置图片
    public void setLeftButtonText(String text) {
        if (mLeftButton != null) {
            mLeftButton.setText(text);
            mLeftButton.setVisibility(VISIBLE);
        } else {
            mLeftButton.setVisibility(INVISIBLE);
        }
    }


    public void setRightButtonText(String text) {
        if (mRightButton != null) {
            mRightButton.setText(text);
            mRightButton.setVisibility(VISIBLE);
        } else {
            mRightButton.setVisibility(INVISIBLE);
        }
    }

    //给左侧按钮设置图片
    public String getLeftButtonText() {
        if (mLeftButton != null) {
            return mLeftButton.getText().toString().trim();
        }
        return "";
    }

    //设置右侧按钮监听事件
    public void setRightButtonOnClickLinster(OnClickListener linster) {
        mRightButton.setOnClickListener(linster);
    }

    //设置左侧按钮监听事件
    public void setLeftButtonOnClickLinster(OnClickListener linster) {
        mLeftButton.setOnClickListener(linster);
    }


}