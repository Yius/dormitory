package com.example.xin.dormitory.houseparent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.example.xin.dormitory.R;

public class StayStudentsDetailsActivity extends AppCompatActivity {

    private TextView tv_registerDate;
    private TextView tv_dormID;
    private TextView tv_ID;
    private TextView tv_name;
    private TextView tv_contact;
    private TextView tv_startDate;
    private TextView tv_endDate;
    private Stay stay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stay_students_details);
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
        tv_contact = findViewById(R.id.tv_contact);
        tv_startDate = findViewById(R.id.tv_startDate);
        tv_endDate = findViewById(R.id.tv_endDate);
        stay = (Stay) getIntent().getSerializableExtra("stay_data");
        tv_contact.setText(stay.getContact());
        tv_registerDate.setText("提交日期:"+stay.getRegisterDate());
        tv_ID.setText(stay.getID());
        tv_dormID.setText(stay.getDormID());
        tv_name.setText(stay.getName());
        tv_startDate.setText(stay.getStartDate());
        tv_endDate.setText(stay.getEndDate());
    }
}
