package com.sskj.bfex.v.activity;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.p.activity.AboutUsPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.CopyUtil;
import com.sskj.bfex.utils.ToastUtil;
import com.sskj.bfex.v.base.BaseActivity;

import org.reactivestreams.Subscription;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;

/**
 * <pre>
 *     author : 吕志豪
 *     e-mail : 1030753080@qq.com
 *     time   : 2018/04/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class AboutUsActivity extends BaseActivity<AboutUsPresenter> {
    @BindView(R.id.rl_contact_us)
    RelativeLayout rlContactUs;
    @BindView(R.id.toolBar)
    ToolBarLayout toolBar;
    @BindView(R.id.title)
    TextView title;
    private String emailStr;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about_us;
    }

    @Override
    public AboutUsPresenter getPresenter() {
        return new AboutUsPresenter();
    }

    @Override
    protected void onViewBinding() {
        Flowable.just(1,12,2,2)
                .subscribe(new FlowableSubscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("onSubscribe");
                        s.request(4);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println("onNext");
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onComplete");
                    }
                });
        mPresenter.getEmail();
        title.setText("关于我们");
        toolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
        ClickUtil.click(rlContactUs, () -> {
            if (emailStr != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = LayoutInflater.from(this);
                View v = inflater.inflate(R.layout.dialog_about_us_tip, null);
                TextView cancle = v.findViewById(R.id.tv_cancel);
                TextView copy = v.findViewById(R.id.tv_copy);
                TextView email = v.findViewById(R.id.tv_email);
                email.setText(emailStr);
                Dialog dialog = builder.create();
                dialog.show();
                dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
                ClickUtil.click(cancle, () -> dialog.dismiss());
                ClickUtil.click(copy, () -> {
                    dialog.dismiss();
                    CopyUtil.copy(email.getText().toString());
//            startActivity(new Intent(this, WithdrawRecordActivity.class));
                });
            } else {
                ToastUtil.showShort("联系方式暂未获取到");
            }
        });
    }

    /**
     * 自定义布局
     * setView()只会覆盖AlertDialog的Title与Button之间的那部分，而setContentView()则会覆盖全部，
     * setContentView()必须放在show()的后面
     */
    public void dialogEmail(String emailStr) {
        this.emailStr = emailStr;

    }

}
