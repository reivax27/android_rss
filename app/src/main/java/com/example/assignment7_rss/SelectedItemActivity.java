package com.example.assignment7_rss;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;

public class SelectedItemActivity extends AppCompatActivity {

    private EditText etTitle, etPubDate;
    private TextView tvDescription;
    private WebView wvContent;
    private String title, link, description, pubDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item);

        etTitle = findViewById(R.id.etTitle);
        etPubDate = findViewById(R.id.etPubDate);
        //tvDescription = findViewById(R.id.tvDescription);

        //store current selected item's information
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        link = intent.getStringExtra("link");
        description = intent.getStringExtra("description");
        pubDate = intent.getStringExtra("pubDate");

        etTitle.setText(title);
        etPubDate.setText(pubDate);
        //tvDescription.setText(description + "...");
        wvContent = findViewById(R.id.wvContent);
        wvContent.loadUrl(link);
//        wvContent.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(link);
//                return false;
//                //return super.shouldOverrideUrlLoading(view, url);
//            }
//        });


    }
}
