package com.example.xin.dormitory.houseparent;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.xin.dormitory.R;

public class ManagerRepairActivity extends AppCompatActivity {

    private Button bt_unhandleApplication;
    private Button bt_handleApplication;
    private Animation myAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_repair);
        bt_handleApplication = findViewById(R.id.bt_handleApplication);
        bt_unhandleApplication = findViewById(R.id.bt_unhandleApplication);
        Toolbar toolbar = findViewById(R.id.toolbar_repair);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setListeners();
    }


    private void setListeners(){
        OnClick onClick = new OnClick();
        bt_handleApplication.setOnClickListener(onClick);
        bt_unhandleApplication.setOnClickListener(onClick);
    }


    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v){
            Intent intent = null;
            switch(v.getId()) {
                case R.id.bt_handleApplication:
                    myAnimation= AnimationUtils.loadAnimation(ManagerRepairActivity.this, R.anim.anim_alpha);
                    v.startAnimation(myAnimation);
                    intent = new Intent(ManagerRepairActivity.this,HandledRepairActivity.class);
                    break;
                case R.id.bt_unhandleApplication:
                    myAnimation= AnimationUtils.loadAnimation(ManagerRepairActivity.this, R.anim.anim_alpha);
                    v.startAnimation(myAnimation);
                    intent = new Intent(ManagerRepairActivity.this,UnhandledRepairActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

}
