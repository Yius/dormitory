package com.example.xin.dormitory.student;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.xin.dormitory.R;
import com.example.xin.dormitory.Utility.HttpUtil;

public class CheckEleAndWaterActivity extends AppCompatActivity {

    private WebView wv_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ele_and_water);
        wv_view = findViewById(R.id.wv_view);
        wv_view.getSettings().setJavaScriptEnabled(true);
        wv_view.setWebViewClient(new WebViewClient());
        String source = getIntent().getStringExtra("source");
        String url = null;
        switch (source){
            case "water":
                url = HttpUtil.waterCheck;
                break;
            case "electricity":
                url = HttpUtil.electricityCheck;
                break;
            default:
                break;
        }
        wv_view.loadUrl(url);
    }
}
