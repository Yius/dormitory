package com.example.xin.dormitory.student;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.xin.dormitory.R;
import com.xuexiang.xui.widget.button.shadowbutton.ShadowButton;
//lyf新加，签到详情
public class SignDetails extends AppCompatActivity {

    ShadowButton sign;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sign=findViewById(R.id.sign);

    }
}
