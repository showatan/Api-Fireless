package com.bomberos.soloingenios.api_fireless;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class Emergencia extends AppCompatActivity {
    WebView browser;
    SwipeRefreshLayout mySwipeRefreshLayout;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia);
        //Definimos el refresh
        mySwipeRefreshLayout = (SwipeRefreshLayout)this.findViewById(R.id.swipeContainer);

        // Definimos el webView
        browser=(WebView)findViewById(R.id.miweb);

        // Habilitamos Javascript
        browser.getSettings().setJavaScriptEnabled(true);

        browser.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // Cargamos la web
        browser.loadUrl("http://api.fireless.cl");

        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        browser.reload();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        //progres bar

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        browser.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
                Emergencia.this.setProgress(progress * 1000);

                progressBar.incrementProgressBy(progress);

                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });


    }
    @Override
    public void onBackPressed()
    {
        if (browser.canGoBack())
        {
            browser.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }
}
