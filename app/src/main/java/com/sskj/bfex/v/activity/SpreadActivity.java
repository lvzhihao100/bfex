package com.sskj.bfex.v.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.LoginBean;
import com.sskj.bfex.p.activity.SpreadPresenter;
import com.sskj.bfex.utils.ImgUtils;
import com.sskj.bfex.utils.PermissionTipUtil;
import com.sskj.bfex.utils.QRCodeUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;

/**
 * Created by lv on 18-6-7.
 */

public class SpreadActivity extends BaseActivity<SpreadPresenter> {
    @BindView(R.id.toolBar)
    ToolBarLayout toolBar;
    @BindView(R.id.title)
    TextView titleText;
    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.tv_invite_person)
    TextView tvInvitePerson;
    @BindView(R.id.tv_invite_code)
    TextView tvInviteCode;


    @Override
    protected void initView() {

        toolBar.setLeftButtonOnClickLinster((v) -> onBackPressed());

        titleText.setText("规则介绍");

    }

    @Override
    protected void initData() {
        mPresenter.loadData();
        mPresenter.getAccount();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, SpreadActivity.class);
        context.startActivity(starter);
    }

    public void updateUI(LoginBean loginBean) {
        tvInviteCode.setText("邀请码：" + loginBean.getNumber());
        tvInvitePerson.setText("邀请人：" + loginBean.getAccount());
    }

    public void updateQR(String qrCodeUrl) {

        QRCodeUtil.createImage(qrCodeUrl, 400, new QRCodeUtil.OnEncodeQRCodeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap bitmap) {
                ivQrCode.setImageBitmap(bitmap);
            }

            @Override
            public void onAnalyzeFailed() {
                ToastUtil.showShort("二维码生成失败");
            }
        });
        ivQrCode.setOnLongClickListener(v -> {
            new RxPermissions(this)
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(isGrant -> {
                        if (isGrant) {
                            ImgUtils.saveImageToGallery(this, v);
                        } else {
                            PermissionTipUtil.tip(this, "存储");
                        }
                    });
            return true;
        });
    }

    @Override
    protected int getLayoutResId() {

        return R.layout.activity_spread;
    }

    @Override
    public SpreadPresenter getPresenter() {
        return new SpreadPresenter();
    }
}
