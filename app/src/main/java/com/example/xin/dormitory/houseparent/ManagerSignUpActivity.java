package com.example.xin.dormitory.houseparent;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.xin.dormitory.R;
import com.example.xin.dormitory.Utility.HttpUtil;
import com.example.xin.dormitory.Utility.MyApplication;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ManagerSignUpActivity extends AppCompatActivity {

    private Button bt_newSign;
    private Button bt_situation;
    private Animation myAnimation;
    private Button loca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_sign_up);
        bt_newSign = findViewById(R.id.bt_newSign);
        bt_situation = findViewById(R.id.bt_situation);
        loca = findViewById(R.id.loca);
        Toolbar toolbar = findViewById(R.id.toolbar_sign);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setListeners();
    }


    private void setListeners(){
        OnClick onClick = new OnClick();
        bt_newSign.setOnClickListener(onClick);
        bt_situation.setOnClickListener(onClick);
        loca.setOnClickListener(onClick);
    }


    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v){
            switch(v.getId()) {
                case R.id.bt_newSign:
                    myAnimation= AnimationUtils.loadAnimation(ManagerSignUpActivity.this, R.anim.anim_alpha);
                    v.startAnimation(myAnimation);
                    postNewSign();
                    break;
                case R.id.bt_situation:
                    myAnimation= AnimationUtils.loadAnimation(ManagerSignUpActivity.this, R.anim.anim_alpha);
                    v.startAnimation(myAnimation);
                    startActivity(new Intent(ManagerSignUpActivity.this,SignRecordSituationActivity.class));
                    break;
                case R.id.loca:
                    startActivity(new Intent(ManagerSignUpActivity.this,MainActivity.class));
                default:
                    break;
            }
        }
    }


    /**
     * 用来发起新签到
     */
    private void postNewSign(){
        final EditText et = new EditText(ManagerSignUpActivity.this);
        et.setMaxLines(2);
        new AlertDialog.Builder(ManagerSignUpActivity.this).setTitle("请输入标题")
                .setView(et)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //按下确定键后的事件
                        String title  = et.getText().toString();
                        postNewSignHelp(title);
                    }
                }).setNegativeButton("取消",null).show();
    }


    /**
     * 发起新签到所进行的网络交互的协助函数
     * @param title 签到标题
     */
    private void postNewSignHelp(String title){
        SharedPreferences pref = getSharedPreferences("dataH",MODE_PRIVATE);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Rtime = formatter.format(new Date(System.currentTimeMillis()));
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("Rtime",Rtime).add("houseparentID",pref.getString("ID","")).add("title",title).add("govern",pref.getString("govern","")).build();
        //服务器地址，ip地址需要时常更换
        String address=HttpUtil.address+"createNewSign.php";
        Request request = new Request.Builder().url(address).post(requestBody).build();
        //匿名内部类实现回调接口
        client.newCall(request).enqueue(new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(),"服务器连接失败，无法发布信息",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                //子线程中操作Toast会出现问题，所以用runOnUiThread
                if (HttpUtil.parseSimpleJSONData(responseData)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyApplication.getContext(), "发起成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MyApplication.getContext(), "发起失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}
