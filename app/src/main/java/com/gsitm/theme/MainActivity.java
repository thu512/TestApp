package com.gsitm.theme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import io.realm.Realm;

public class MainActivity extends BaseActivity {

    private WebView mWebView;
    private WebSettings mWebSetting;
    private CustomWebViewClient webViewClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        mWebView = findViewById(R.id.web_view);

        mWebView.setWebChromeClient(new WebChromeClient());
        webViewClient = new CustomWebViewClient(this);

        //웹뷰 셋팅
        mWebView.setWebViewClient(webViewClient);
        mWebSetting = mWebView.getSettings();
        mWebSetting.setAllowFileAccessFromFileURLs(true);
        mWebSetting.setAllowUniversalAccessFromFileURLs(true);
        mWebSetting.setJavaScriptEnabled(true);
        mWebSetting.setLoadWithOverviewMode(true);
        mWebSetting.setBuiltInZoomControls(false);
        mWebSetting.setSupportZoom(true);
        mWebSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        mWebSetting.setDefaultZoom(WebSettings.ZoomDensity.FAR);

        //헤더에 유저에이전트 추가
        String userAgent = mWebView.getSettings().getUserAgentString();
        mWebView.getSettings().setUserAgentString(userAgent+" gsepartnermobile");


        mWebView.loadUrl("file:///android_asset/www/sample.html");
    }
}
