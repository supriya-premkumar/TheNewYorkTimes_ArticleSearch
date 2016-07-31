package me.supriyapremkumar.thenewyorktimes_articles.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import me.supriyapremkumar.thenewyorktimes_articles.R;

public class ArticleDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_display_activity);
        String url = getIntent().getStringExtra("url");

        WebView webView = (WebView)findViewById(R.id.wvArticle);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
               view.loadUrl(request.toString());
                return true;
            }
        });

        webView.loadUrl(url);
    }

}
