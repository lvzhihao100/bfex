package com.sskj.bfex.v.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.RechargeInfoBean;
import com.sskj.bfex.p.activity.RechargePresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.CopyUtil;
import com.sskj.bfex.utils.ImgUtils;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

/**
 * 账户充值
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class RechargeActivity extends BaseActivity<RechargePresenter> {

    @BindView(R.id.iv_qr_code)
    ImageView ivQrCode;
    @BindView(R.id.tv_save_qr)
    TextView tvSaveQr;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.tv_copy)
    TextView tvCopy;
    @BindView(R.id.toolBar)
    ToolBarLayout mToolBar;
    @BindView(R.id.title)
    TextView mTitle;
    Bitmap mbitmap;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_recharge;
    }

    @Override
    public RechargePresenter getPresenter() {
        return new RechargePresenter();
    }

    @Override
    protected void onViewBinding() {

        mTitle.setText("USDT充币");
        mToolBar.setRightButtonIcon(getResources().getDrawable(R.mipmap.icon_bill));
        mToolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
        mToolBar.setRightButtonOnClickLinster(view -> startActivity(new Intent(mActivity, RechargeRecordActivity.class)));
        mPresenter.recharge();

    }

    public void updateView(RechargeInfoBean bean) {
        tvCode.setText(bean.getUrl());
        Glide.with(this).load(HttpConfig.BASE_URL + "/" + bean.getQrc()).into(ivQrCode);
//        ivQrCode.setImageURI(Uri.parse(HttpConfig.BASE_URL + "/" + bean.getQrc()));
//        QRCodeUtil.createImage("123", (int) RudenessScreenHelper.pt2px(this, 230), new QRCodeUtil.OnEncodeQRCodeCallback() {
//            @Override
//            public void onAnalyzeSuccess(Bitmap bitmap) {
//                mbitmap = bitmap;
//                ivQrCode.setImageBitmap(bitmap);
//            }
//
//            @Override
//            public void onAnalyzeFailed() {
//
//            }
//        });
        ClickUtil.click(tvCopy, () -> {
            CopyUtil.copy(tvCode.getText().toString());
        });
        ClickUtil.click(tvSaveQr, () -> {
            ImgUtils.saveImageToGallery(RechargeActivity.this, ivQrCode);
//            if (mbitmap != null) {
//                new RxPermissions(RechargeActivity.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        .subscribe(isGrant -> {
//                            if (isGrant) {
//                                ImgUtils.saveImageToGallery(this, mbitmap);
//
//                            } else {
//                                PermissionTipUtil.tip(RechargeActivity.this, "存储");
//                            }
//                        });
//            }

        });

    }

}
