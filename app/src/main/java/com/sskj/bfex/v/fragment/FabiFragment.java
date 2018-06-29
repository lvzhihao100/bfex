package com.sskj.bfex.v.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.adapter.MyFragmentPagerAdapter;
import com.sskj.bfex.p.fragment.FabiFragmentPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class FabiFragment extends BaseFragment<MainActivity, FabiFragmentPresenter> {

    @BindView(R.id.iv_right_end)
    ImageView ivRightEnd;
    @BindView(R.id.iv_right_start)
    ImageView ivRightStart;
    @BindView(R.id.tv_buy)
    TextView tvBuy;
    @BindView(R.id.tv_sell)
    TextView tvSell;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private AlertDialog tipDialog;

    public static FabiFragment newInstance() {

        Bundle args = new Bundle();

        FabiFragment fragment = new FabiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fabi;
    }

    @Override
    public FabiFragmentPresenter getPresenter() {
        return new FabiFragmentPresenter();
    }

    @Override
    public void initView() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(FabiBuyFragment.newInstance());
        fragments.add(FabiSellFragment.newInstance());
        viewPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                changeTextColor(position);
            }
        });
        ClickUtil.click(tvBuy, () -> {
            changeTextColor(0);

            if (viewPager.getCurrentItem() != 0) {
                viewPager.setCurrentItem(0, true);
            }
        });
        ClickUtil.click(tvSell, () -> {
            changeTextColor(1);

            if (viewPager.getCurrentItem() != 1) {
                viewPager.setCurrentItem(1, true);
            }
        });
        ClickUtil.click(ivRightStart, () -> {

        });
        ClickUtil.click(ivRightEnd, () -> {
            showTip();
        });


    }

    /**
     * 更改选项卡
     *
     * @param position
     */
    private void changeTextColor(int position) {
        tvBuy.setTextColor(getResources().getColor(position == 0 ? R.color.fabi_select_tab_text_color : R.color.fabi_unselect_tab_text_color));
        tvSell.setTextColor(getResources().getColor(position == 1 ? R.color.fabi_select_tab_text_color : R.color.fabi_unselect_tab_text_color));
    }

    /**
     * 显示申请商家的提示
     */
    private void showTip() {
        if (tipDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View view = View.inflate(getActivity(), R.layout.dialog_fabi_tip, null);
            ClickUtil.click(view.findViewById(R.id.tv_cancel), () -> {
                tipDialog.dismiss();
            });
            ClickUtil.click(view.findViewById(R.id.tv_apply), () -> {
                tipDialog.dismiss();
            });
            builder.setView(view);
            builder.setCancelable(true);
            tipDialog = builder.create();
        }

        tipDialog.show();
    }

}
