package com.sskj.bfex.v.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sskj.bfex.R;
import com.sskj.bfex.common.adapter.MyBaseAdapter;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.m.bean.AddressList;
import com.sskj.bfex.p.activity.CashPresenter;
import com.sskj.bfex.utils.ClickUtil;
import com.sskj.bfex.utils.UserUtil;
import com.sskj.bfex.v.base.BaseActivity;

import java.util.List;

import butterknife.BindView;

/**
 * <pre>
 *     author : 李岩
 *     e-mail : 465357793@qq.com
 *     time   : 2018/04/03
 *     desc   : 选择提现地址
 *     version: 1.0
 * </pre>
 */
public class CashAddressListActivity extends BaseActivity<CashPresenter> {

    @BindView(R.id.cash_address_add)
    Button mCashAddressAdd;
    @BindView(R.id.cash_address_list)
    ListView mCashAddressList;
    @BindView(R.id.toolBar)
    ToolBarLayout mToolBar;
    @BindView(R.id.title)
    TextView mTitle;
    private MyBaseAdapter<AddressList.DataBean.ResBean> mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_cash_address_list;
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestAddressList(UserUtil.getAccount());
    }

    @Override
    public CashPresenter getPresenter() {
        return new CashPresenter();
    }

    @Override
    protected void onViewBinding() {
        mTitle.setText("USDT钱包地址管理");
        mToolBar.setLeftButtonOnClickLinster(v -> onBackPressed());
        ClickUtil.click(mCashAddressAdd, () -> {
            startActivity(new Intent(mActivity, CashAddressAddActivity.class));
        });
        mAdapter = new MyBaseAdapter<AddressList.DataBean.ResBean>(new MyBaseAdapter.initView<AddressList.DataBean.ResBean>() {
            @Override
            public View initItenView(int position, View convertView, ViewGroup parent, AddressList.DataBean.ResBean obj) {
                if (convertView == null){
                    convertView = LayoutInflater.from(mActivity).inflate(R.layout.item_cash_address, null);
                }
                TextView remark = convertView.findViewById(R.id.address_list_remark);
                TextView content = convertView.findViewById(R.id.address_list_content);
                remark.setText(TextUtils.isEmpty(obj.getNotes()) ? "无备注" : obj.getNotes());
                content.setText(obj.getQiaobao_url());
                return convertView;
            }
        });
        mCashAddressList.setAdapter(mAdapter);
        mCashAddressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("address_url", mAdapter.getItem(i).getQiaobao_url());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mCashAddressList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle("温馨提示")
                        .setMessage("是否删除改地址")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                mPresenter.requestDelite(position, mAdapter.getDatas().get(position).getId());
                            }
                        });
                AlertDialog dialog = builder.show();

                return true;
            }
        });
    }

    public void onResponseCashAddressListSuccess(List<AddressList.DataBean.ResBean> data) {
        mAdapter.setList(data);
    }

}
