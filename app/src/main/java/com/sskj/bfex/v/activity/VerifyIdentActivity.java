package com.sskj.bfex.v.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.p.activity.VerifyIdentPresenter;
import com.sskj.bfex.utils.FileUtil;
import com.sskj.bfex.utils.PictureUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import java.io.File;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class VerifyIdentActivity extends BaseActivity<VerifyIdentPresenter> {


    private static final int REQUEST_A_CAMERA = 1000;
    private static final int REQUEST_B_CAMERA = 1001;
    private static final int REQUEST_C_CAMERA = 1002;
    private static final String NAME_A = "photo_a.jpg";
    private static final String NAME_B = "photo_b.jpg";
    private static final String NAME_C = "photo_c.jpg";
    @BindView(R.id.toolBar)
    ToolBarLayout mToolBar;
    @BindView(R.id.verify_real_name_edittext)
    EditText mVerifyRealNameEdittext;
    @BindView(R.id.verify_ident_edittext)
    EditText mVerifyIdentEdittext;
    @BindView(R.id.verify_photo_a)
    ImageView mVerifyPhotoA;
    @BindView(R.id.verify_photo_b)
    ImageView mVerifyPhotoB;
    @BindView(R.id.verify_photo_c)
    ImageView mVerifyPhotoC;
    @BindView(R.id.verify_submit)
    Button mVerifySubmit;
    @BindView(R.id.title)
    TextView mTitle;
    private String mCurrFileName;

    private HashMap<String, Object> mParams = new HashMap<String, Object>();
    private String mobile;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_verify_ident;
    }

    @Override
    public VerifyIdentPresenter getPresenter() {
        return new VerifyIdentPresenter();
    }

    @Override
    protected void onViewBinding() {
        mTitle.setText("实名认证");
        mToolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String filePath = null;
            switch (requestCode) {
                case REQUEST_A_CAMERA://人证合一
                    filePath = new File(getFilesDir(), NAME_A).getAbsolutePath();
                    break;
                case REQUEST_B_CAMERA://身份证正面
                    filePath = new File(getFilesDir(), NAME_B).getAbsolutePath();
                    break;
                case REQUEST_C_CAMERA: //身份证背面
                    filePath = new File(getFilesDir(), NAME_C).getAbsolutePath();
                    break;
            }
            if (TextUtils.isEmpty(filePath)) {
                ToastUtil.showShort("照片获取失败,请重新拍摄");
                return;
            }

            Bitmap smallBitmap = PictureUtil.getSmallBitmap(filePath);
            PictureUtil.saveBitmap(smallBitmap, 70, filePath, Bitmap.CompressFormat.JPEG);
            File file = new File(filePath);
            if (file != null && smallBitmap != null) {
                setFilePath(smallBitmap, filePath, file);
                if (mParams.get("photo_a") != null && mParams.get("photo_b") != null && mParams.get("photo_b") != null) {
                    mVerifySubmit.setEnabled(true);
                }
            }
        }

    }

    private void setFilePath(Bitmap smallBitmap, String filePath, File file) {
        if (NAME_A.equals(mCurrFileName)) {
            mParams.put("photo_a", file);
            mParams.put("photoAName", mCurrFileName);
            mVerifyPhotoA.setImageBitmap(smallBitmap);
        } else if (NAME_B.equals(mCurrFileName)) {
            mParams.put("photo_b", file);
            mParams.put("photoBName", mCurrFileName);
            mVerifyPhotoB.setImageBitmap(smallBitmap);

        } else if (NAME_C.equals(mCurrFileName)) {//身份证背面
            mParams.put("photo_c", file);
            mParams.put("photoCName", mCurrFileName);
            mVerifyPhotoC.setImageBitmap(smallBitmap);
        }

    }


    @OnClick({R.id.verify_photo_a, R.id.verify_photo_b, R.id.verify_photo_c, R.id.verify_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.verify_photo_a:
                mCurrFileName = NAME_A;
                Intent intent = new Intent(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication(), NAME_A).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_A_CAMERA);
                break;
            case R.id.verify_photo_b:
                mCurrFileName = NAME_B;
                intent = new Intent(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication(), NAME_B).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                startActivityForResult(intent, REQUEST_B_CAMERA);
                break;
            case R.id.verify_photo_c:
                mCurrFileName = NAME_C;
                intent = new Intent(this, CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getApplication(), NAME_C).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_BACK);
                startActivityForResult(intent, REQUEST_C_CAMERA);
                break;
            case R.id.verify_submit:
                String identNum = mVerifyIdentEdittext.getText().toString().trim();
                String realName = mVerifyRealNameEdittext.getText().toString().trim();
                if (TextUtils.isEmpty(realName)) {
                    ToastUtil.showShort("请输入真实姓名");
                    return;
                }
                if (TextUtils.isEmpty(identNum)) {
                    ToastUtil.showShort("请输入身份证号");
                    return;
                }
                if (identNum.length() != 18) {
                    ToastUtil.showShort("请输入正确的身份证号");
                    return;
                }
                if (mParams.size() < 6) {
                    ToastUtil.showShort("请补全图片信息");
                    return;
                }
                mPresenter.submitVerify(realName, identNum, mParams);
                break;
        }
    }

    public void onSubmitVerifyResponseSuccess() {
        ToastUtil.showShort("已提交认证，请耐心等待");
        setResult(RESULT_OK, new Intent().putExtra("resultData", 2));
        finish();
//        startActivity(new Intent(mActivity, CommonActivity.class));
    }
}
