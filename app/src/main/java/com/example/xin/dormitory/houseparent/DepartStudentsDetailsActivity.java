package com.example.xin.dormitory.houseparent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;

import com.example.xin.dormitory.R;

/**
 * 离宿学生详情类
 */
public class DepartStudentsDetailsActivity extends AppCompatActivity {

    private TextView tv_registerDate;
    private TextView tv_dormID;
    private TextView tv_ID;
    private TextView tv_name;
    private TextView tv_departCause;
    private TextView tv_contact;
    private TextView tv_departTime;
    private TextView tv_backTime;
    private Depart depart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depart_students_details);
        initLayout();
    }

    /**
     * 把布局初始化的代码写在一起
     */
    private void initLayout(){
        Toolbar toolbar = findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_registerDate = findViewById(R.id.tv_registerDate);
        tv_dormID = findViewById(R.id.tv_dormID);
        tv_ID = findViewById(R.id.tv_ID);
        tv_name = findViewById(R.id.tv_name);
        tv_departCause = findViewById(R.id.tv_departCause);
        tv_contact = findViewById(R.id.tv_contact);
        tv_departTime = findViewById(R.id.tv_departTime);
        tv_backTime = findViewById(R.id.tv_backTime);
        depart = (Depart) getIntent().getSerializableExtra("depart_data");
        tv_contact.setText(depart.getContact());
        tv_registerDate.setText("提交日期:"+depart.getRegisterDate());
        tv_ID.setText(depart.getID());
        tv_dormID.setText(depart.getDormID());
        tv_name.setText(depart.getName());
        tv_departCause.setText(depart.getDepartCause());
        tv_departTime.setText(depart.getDepartTime());
        tv_backTime.setText(depart.getBackTime());
    }
}
