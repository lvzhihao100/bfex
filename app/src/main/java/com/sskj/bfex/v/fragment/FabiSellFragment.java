package com.sskj.bfex.v.fragment;

import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.shizhefei.mvc.MVCCoolHelper;
import com.shizhefei.view.coolrefreshview.CoolRefreshView;
import com.sskj.bfex.R;
import com.sskj.bfex.adapter.slimadapter.IViewInjector;
import com.sskj.bfex.adapter.slimadapter.SlimAdapter;
import com.sskj.bfex.adapter.slimadapter.SlimInjector;
import com.sskj.bfex.mvchelper.ModelRx2DataSource;
import com.sskj.bfex.p.base.BasePresenter;
import com.sskj.bfex.p.fragment.FabiFragmentPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.v.activity.MainActivity;
import com.sskj.bfex.v.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Flowable;

public class FabiSellFragment extends BaseFragment<MainActivity, BasePresenter> {

    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.iv_money_arrow)
    ImageView ivMoneyArrow;
    @BindView(R.id.iv_pay_type_arrow)
    ImageView ivPayTypeArrow;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.coolRefreshView)
    CoolRefreshView coolRefreshView;
    private SlimAdapter slimAdapter;
    private int curPos;
    private BottomSheetDialog sellDialog;
    private BottomSheetDialog codeDialog;

    public static FabiSellFragment newInstance() {
        FabiSellFragment fragment = new FabiSellFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fabi_buy;
    }

    @Override
    public BasePresenter getPresenter() {
        return new BasePresenter();
    }

    @Override
    public void initView() {
        ClickUtil.click(tvMoney, () -> {
            showPricePop();
        });
        ClickUtil.click(tvPayType, () -> {
            showPayType();
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        slimAdapter = SlimAdapter.create().register(R.layout.recy_item_fabi, new SlimInjector<String>() {
            @Override
            public void onInject(String data, IViewInjector injector) {

                injector.clicked(R.id.bt_sale, v -> {
                    curPos = slimAdapter.getData().indexOf(data);
                    showSell();
                });
            }
        }).attachTo(recyclerView).updateData(new ArrayList());
        ModelRx2DataSource<String> dataSource =
                new ModelRx2DataSource<>((ModelRx2DataSource.OnLoadSource<String>)
                        page -> Flowable.range(0, 10)
                                .map(integer -> integer + "")
                                .toList()
                                .toFlowable(), 10);
        MVCCoolHelper<List<String>> mvcCoolHelper = new MVCCoolHelper<>(coolRefreshView);
        mvcCoolHelper.setDataSource(dataSource);
        mvcCoolHelper.setAdapter(slimAdapter);
        mvcCoolHelper.refresh();
    }

    /**
     * 显示支付方式
     */
    private void showPayType() {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_fabi_pay_type, null);
        PopupWindow popupWindow = new PopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(popupView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setAnimationStyle(R.style.AnimDown);
        popupWindow.setClippingEnabled(true);

        popupWindow.showAsDropDown(tvMoney);
    }

    /**
     * 显示销售底部弹窗
     */
    private void showSell() {
        if (sellDialog == null) {
            sellDialog = new BottomSheetDialog(getActivity());
            View dialogView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.bottom_sheet_fabi_sell, null);
            Button btDeal = dialogView.findViewById(R.id.bt_deal);
            ClickUtil.click(btDeal, this::showCode);
            sellDialog.setContentView(dialogView);
        }
        sellDialog.show();

    }

    private void showPricePop() {
        View popupView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_fabi_price, null);
        PopupWindow popupWindow = new PopupWindow(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(popupView);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x66000000));
        popupWindow.setOutsideTouchable(true);
//        popupWindow.setAnimationStyle(R.style.AnimDown);
        popupWindow.setClippingEnabled(true);

        popupWindow.showAsDropDown(tvMoney);
    }

    /**
     * 显示验证码底部弹窗
     */
    private void showCode() {
        if (codeDialog == null) {
            codeDialog = new BottomSheetDialog(getActivity());
            View dialogView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.bottom_sheet_fabi_check_code, null);
            codeDialog.setContentView(dialogView);
        }
        codeDialog.show();

    }
}
