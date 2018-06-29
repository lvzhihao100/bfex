package com.sskj.bfex.v.activity;


import android.Manifest;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bulong.rudeness.RudenessScreenHelper;
import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.p.activity.InviteFriendPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.CopyUtil;
import com.sskj.bfex.utils.ImgUtils;
import com.sskj.bfex.utils.PermissionTipUtil;
import com.sskj.bfex.utils.QRCodeUtil;
import com.sskj.bfex.v.base.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;


public class InviteFriendActivity extends BaseActivity<InviteFriendPresenter> {
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
        return R.layout.activity_invite_friend;
    }

    @Override
    public InviteFriendPresenter getPresenter() {
        return new InviteFriendPresenter();
    }

    @Override
    protected void onViewBinding() {
        mTitle.setText("邀请好友");
//        mToolBar.setRightButtonIcon(getResources().getDrawable(R.mipmap.icon_bill));
        mToolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
//        mToolBar.setRightButtonOnClickLinster(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(mActivity, RechargeRecordActivity.class));
//            }
//        });


        ClickUtil.click(tvCopy, () -> {
            CopyUtil.copy(tvCode.getText().toString());
        });
        ClickUtil.click(tvSaveQr, () -> {
            if (mbitmap != null) {
                new RxPermissions(InviteFriendActivity.this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(isGrant -> {
                            if (isGrant) {
                                ImgUtils.saveImageToGallery(this, mbitmap);

                            } else {
                                PermissionTipUtil.tip(InviteFriendActivity.this, "存储");
                            }
                        });
            }
        });
        mPresenter.upDateUserInfo();
    }

    public void updateUserInfo(String data) {
        tvCode.setText(data);
        QRCodeUtil.createImage(/*HttpConfig.INVITE_URL+*/data, (int) RudenessScreenHelper.pt2px(this, 230), new QRCodeUtil.OnEncodeQRCodeCallback() {
            @Override
            public void onAnalyzeSuccess(Bitmap bitmap) {
                mbitmap = bitmap;
                ivQrCode.setImageBitmap(bitmap);
            }

            @Override
            public void onAnalyzeFailed() {

            }
        });
    }
}