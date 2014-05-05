package com.pinchy.android.activites;

/**
 * Created by conormongey on 04/05/2014.
 */
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pinchy.android.R;

public class WebViewActivity extends Activity {

    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        Bundle extras = getIntent().getExtras();
        String url = "http://lobste.rs/";

        if (extras != null) {
            url = extras.getString("url");
        }
        webView = (WebView) findViewById(R.id.webView1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.getSettings().setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }


}