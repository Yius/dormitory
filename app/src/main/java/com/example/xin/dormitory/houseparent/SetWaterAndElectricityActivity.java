package com.example.xin.dormitory.houseparent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.xin.dormitory.R;

import butterknife.BindView;

public class SetWaterAndElectricityActivity extends AppCompatActivity {

    EditText et_water;
    EditText et_electricity;
    Button bt_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_water_and_electricity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        setListener();
    }

    private void initViews() {
        et_water = findViewById(R.id.et_water);
        et_electricity = findViewById(R.id.et_electricity);
        bt_ok = findViewById(R.id.bt_ok);
    }

    private void setListener() {
        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId()==R.id.bt_ok){
                    //TODO 设置的逻辑
//                    finish();
                }
            }
        });
    }
}
