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

public class CheckStayAndDepartActivity extends AppCompatActivity {

    private Button bt_stay,bt_left;
    private Animation myAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_stay_and_depart);
        bt_stay = findViewById(R.id.bt_stay);
        bt_left = findViewById(R.id.bt_depart);
        Toolbar toolbar = findViewById(R.id.toolbar_check);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setListeners();
    }


    private void setListeners(){
        OnClick onClick = new OnClick();
        bt_stay.setOnClickListener(onClick);
        bt_left.setOnClickListener(onClick);
    }


    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v){
            Intent intent = null;
            switch(v.getId()) {
                case R.id.bt_stay:
                    myAnimation= AnimationUtils.loadAnimation(CheckStayAndDepartActivity.this, R.anim.anim_alpha);
                    v.startAnimation(myAnimation);
                    intent = new Intent(CheckStayAndDepartActivity.this,CheckStayStudentsActivity.class);
                    break;
                case R.id.bt_depart:
                    myAnimation= AnimationUtils.loadAnimation(CheckStayAndDepartActivity.this, R.anim.anim_alpha);
                    v.startAnimation(myAnimation);
                    intent = new Intent(CheckStayAndDepartActivity.this,CheckDepartStudentsActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
