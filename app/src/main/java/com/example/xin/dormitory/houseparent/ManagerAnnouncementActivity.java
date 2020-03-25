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

public class ManagerAnnouncementActivity extends AppCompatActivity {

    private Button bt_newAnnouncement;
    private Button bt_oldAnnouncement;
    private Animation myAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_announcement);
        bt_newAnnouncement = findViewById(R.id.bt_newAnnouncement);
        bt_oldAnnouncement = findViewById(R.id.bt_oldAnnouncement);
        Toolbar toolbar = findViewById(R.id.toolbar_announcement);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setListeners();
    }


    private void setListeners(){
        OnClick onClick = new OnClick();
        bt_oldAnnouncement.setOnClickListener(onClick);
        bt_newAnnouncement.setOnClickListener(onClick);
    }


    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v){
            Intent intent = null;
            switch(v.getId()) {
                case R.id.bt_oldAnnouncement:
                    myAnimation= AnimationUtils.loadAnimation(ManagerAnnouncementActivity.this, R.anim.anim_alpha);
                    v.startAnimation(myAnimation);
                    intent = new Intent(ManagerAnnouncementActivity.this,CheckAnnouncementActivity.class);
                    break;
                case R.id.bt_newAnnouncement:
                    myAnimation= AnimationUtils.loadAnimation(ManagerAnnouncementActivity.this, R.anim.anim_alpha);
                    v.startAnimation(myAnimation);
                    intent = new Intent(ManagerAnnouncementActivity.this,CreateAnnouncementActivity.class);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        }
    }

}
