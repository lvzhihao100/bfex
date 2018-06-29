package com.sskj.bfex.v.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.sskj.bfex.R;
import com.sskj.bfex.m.HttpConfig;
import com.sskj.bfex.m.bean.HttpData;
import com.sskj.bfex.m.http.JsonCallBack;
import com.sskj.bfex.utils.ClickUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebActivity extends AppCompatActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    //    @BindView(R.id.webView)
//    WebView webView;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        ButterKnife.bind(this);
        ClickUtil.click(ivBack, () -> onBackPressed());
//        initWeb();
        OkGo.<HttpData<String>>post(HttpConfig.BASE_URL + HttpConfig.GET_XIEYI)
                .params("type", "user")
                .execute(new JsonCallBack<HttpData<String>>() {
                    @Override
                    public void onSuccess(Response<HttpData<String>> response) {
                        HttpData<String> httpListData = response.body();
//                        webView.loadUrl(httpListData.getMsg());
//                        webView.setWebChromeClient(new WebChromeClient() {
////            @Override
////            public void onReceivedTitle(WebView view, String title) {
////                super.onReceivedTitle(view, title);
////                initTopTitleBar(title);
////            }
//                        });
//                        webView.setWebViewClient(new WebViewClient() {
////            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                view.loadUrl(url + extra);
////                return true;
////            }
//                        });
//                        webView.loadData(httpListData.getMsg(), "text/html", );
                        tvContent.setText(Html.fromHtml(httpListData.getData()));
                    }
                });
    }


//    private void initWeb() {
//
//        //获取websetings 设置
//        WebSettings settings = webView.getSettings();
//        settings.setSupportZoom(true);
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        //设置浏览器支持javaScript
//        settings.setJavaScriptEnabled(true);
//        //设置打开自带缩放按钮
//        settings.setBuiltInZoomControls(true);
//
//        // init webview settings
//        settings.setAllowContentAccess(true);
//        settings.setDatabaseEnabled(true);
//        settings.setDomStorageEnabled(true);
//        settings.setAppCacheEnabled(false);
//        settings.setSavePassword(false);
//        settings.setSaveFormData(false);
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        // 设置不可缩放
//        settings.setBuiltInZoomControls(false);
//        settings.setSupportZoom(false);
//        settings.setDisplayZoomControls(false);
//
//        // 设置支持屏幕适配
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
//        // 进行跳转用户输入的url地址
//        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//
//        webView.setWebChromeClient(new WebChromeClient() {
////            @Override
////            public void onReceivedTitle(WebView view, String title) {
////                super.onReceivedTitle(view, title);
////                initTopTitleBar(title);
////            }
//        });
//        webView.setWebViewClient(new WebViewClient() {
////            @Override
////            public boolean shouldOverrideUrlLoading(WebView view, String url) {
////                view.loadUrl(url + extra);
////                return true;
////            }
//        });
//    }

//    @Override
//    public void onBackPressed() {
//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
//            webView.goBack();// 返回前一个页面
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
}
