package com.sskj.bfex.v.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.sskj.bfex.R;
import com.sskj.bfex.common.widget.ToolBarLayout;
import com.sskj.bfex.p.activity.NoticeDetailPresenter;
import com.sskj.bfex.v.base.BaseActivity;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity<NoticeDetailPresenter> {

    @BindView(R.id.web)
    LinearLayout web;
    @BindView(R.id.web_toolbar)
    ToolBarLayout webToolbar;
    private AgentWeb mAgentWeb;

    private String url;
    private TextView mTitleTextView;
    private String title;
    private int type;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    public NoticeDetailPresenter getPresenter() {
        return new NoticeDetailPresenter();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initView() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type", type);
        webToolbar.setLeftButtonOnClickLinster(v -> finish());
        webToolbar.setTitle(title);
//        mTitleTextView = this.findViewById(R.id.toolbar_title);
//        setSupportActionBar(webToolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
        initWeb();
        if (type != -1) {
            mPresenter.getData(type);
        } else {
            loadData(url);
        }
    }

    private void initWeb() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(web, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownUrl() //拦截找不到相关页面的Scheme
                .createAgentWeb()
                .ready()
                .go("");
    }


    public void loadData(String content) {
        mAgentWeb.getUrlLoader().loadData(content, "text/html;charset=UTF-8", "UTF-8");
    }


    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (webToolbar != null) {
                webToolbar.setTitle(title);
            }
        }
    };

    /**
     * @param context
     * @param url     content
     * @param title
     * @param type    -1 头条  平台时传id
     */
    public static void start(Context context, String url, String title, int type) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAgentWeb.getWebLifeCycle().onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAgentWeb.getWebLifeCycle().onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAgentWeb.getWebLifeCycle().onResume();
    }
}
