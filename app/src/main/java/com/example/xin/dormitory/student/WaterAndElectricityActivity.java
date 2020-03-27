package com.example.xin.dormitory.student;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.xin.dormitory.R;
import com.example.xin.dormitory.Utility.HttpUtil;
import com.xuexiang.xui.widget.layout.ExpandableLayout;
import com.xuexiang.xui.widget.textview.supertextview.SuperButton;
import com.xuexiang.xui.widget.textview.supertextview.SuperTextView;

/**
 * 水电费界面（oyx新加）
 */

public class WaterAndElectricityActivity extends AppCompatActivity {

    @BindView(R.id.super_tv_water)
    SuperTextView tv_water;

    @BindView(R.id.expandable_layout_water)
    ExpandableLayout expandableLayout1;

    @BindView(R.id.super_tv_electricity)
    SuperTextView tv_electricity;

    @BindView(R.id.expandable_layout_electricity)
    ExpandableLayout expandableLayout2;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_web_site_water)
    TextView web_site_water;

    @BindView(R.id.tv_web_site_electricity)
    TextView web_site_electricity;

    @BindView(R.id.super_btn_water)
    SuperButton water;

    @BindView(R.id.super_btn_electricity)
    SuperButton electricity;

    @BindView(R.id.tv_dormID)
    TextView dormID;

    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtils.translucent(this);
        setContentView(R.layout.activity_water_and_electricity);
        ButterKnife.bind(this);
        initToolbar();
        initDormID();
        initTextView();
        initWater();
        initElectricity();
    }

    private void initToolbar() {
//        setSupportActionBar(toolbar);
//        actionBar = getSupportActionBar();
//        if (toolbar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setTitle("水电费");
//            toolbar.setTitleTextColor(Color.WHITE);
//        }
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initDormID(){
        dormID.setText(getIntent().getStringExtra("dormID"));
    }

    private void initTextView(){
        HttpUtil.waterCheck = "https://ssp.scnu.edu.cn/";
        HttpUtil.electricityCheck="https://ssp.scnu.edu.cn/";
        if(HttpUtil.waterCheck!=null&&HttpUtil.waterCheck!=""){
            web_site_water.setText(HttpUtil.waterCheck);
            water.setVisibility(View.VISIBLE);
            water.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WaterAndElectricityActivity.this,CheckEleAndWaterActivity.class);
                    intent.putExtra("source","water");
                    startActivity(intent);
                }
            });
        }
        if(HttpUtil.electricityCheck!=null&&HttpUtil.electricityCheck!=""){
            web_site_electricity.setText(HttpUtil.electricityCheck);
            electricity.setVisibility(View.VISIBLE);
            electricity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WaterAndElectricityActivity.this,CheckEleAndWaterActivity.class);
                    intent.putExtra("source","electricity");
                    startActivity(intent);
                }
            });
        }
    }

    private void initWater(){
        expandableLayout1.setOnExpansionChangedListener(new ExpandableLayout.OnExpansionChangedListener() {
            @Override
            public void onExpansionChanged(float expansion, int state) {
                if (tv_water != null && tv_water.getRightIconIV() != null) {
                    tv_water.getRightIconIV().setRotation(expansion * 90);
                    tv_water.useShape();
                    if(state<=1){
                        tv_water.setShapeCornersBottomLeftRadius(15);
                        tv_water.setShapeCornersBottomRightRadius(15);
                    }else{
                        tv_water.setShapeCornersBottomLeftRadius(0);
                        tv_water.setShapeCornersBottomRightRadius(0);
                    }
                }
            }
        });
        tv_water.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClick(SuperTextView superTextView) {
                if (expandableLayout1!= null) {
                    expandableLayout1.toggle();
                }
            }
        });
    }


    private void initElectricity(){
        expandableLayout2.setOnExpansionChangedListener(new ExpandableLayout.OnExpansionChangedListener() {
            @Override
            public void onExpansionChanged(float expansion, int state) {
                if (tv_electricity != null && tv_electricity.getRightIconIV() != null) {
                    tv_electricity.getRightIconIV().setRotation(expansion * 90);
                    tv_electricity.useShape();
                    if(state<=1){
                        tv_electricity.setShapeCornersBottomLeftRadius(15);
                        tv_electricity.setShapeCornersBottomRightRadius(15);
                    }else{
                        tv_electricity.setShapeCornersBottomLeftRadius(0);
                        tv_electricity.setShapeCornersBottomRightRadius(0);
                    }
                }
            }
        });
        tv_electricity.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClick(SuperTextView superTextView) {
                if (expandableLayout2!= null) {
                    expandableLayout2.toggle();
                }
            }
        });
    }
}

