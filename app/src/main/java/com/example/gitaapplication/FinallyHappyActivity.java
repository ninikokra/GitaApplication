package com.example.gitaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class FinallyHappyActivity extends AppCompatActivity {

    WebView webView;
    Button wVButton;
    TextView happyText;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finally_happy);
        webView = findViewById(R.id.webView);
        wVButton = findViewById(R.id.urlButton);

        happyText = findViewById(R.id.happyTV);
        animation = AnimationUtils.loadAnimation(this,R.anim.text_animation);
        happyText.startAnimation(animation);

        wVButton.setOnClickListener(view -> {
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("https://www.youtube.com/watch?v=t5ze_e4R9QY&ab_channel=TheBeatles");
            webView.setWebChromeClient(new WebChromeClient());
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setPluginState(WebSettings.PluginState.ON);

        });


    }
}