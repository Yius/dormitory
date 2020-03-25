package com.example.xin.dormitory.houseparent;

import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
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

public class CreateAnnouncementActivity extends AppCompatActivity {

    private EditText et_title;
    private EditText et_content;
    private TextView tv_announce;
    private Animation myAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);
        initLayout();
    }


    /**
     * 把布局初始化的代码写在一起
     */
    private void initLayout(){
        Toolbar toolbar = findViewById(R.id.toolbar_announce);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        tv_announce = findViewById(R.id.tv_announce);
        setListeners();
    }


    /**
     * 监听器初始化
     */
    private void setListeners(){
        OnClick onClick = new OnClick();
        tv_announce.setOnClickListener(onClick);
    }


    private class OnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_announce:
                    String content = et_content.getText().toString();
                    String title = et_title.getText().toString();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm::ss");
                    String Atime = formatter.format(new Date(System.currentTimeMillis()));
                    myAnimation= AnimationUtils.loadAnimation(CreateAnnouncementActivity.this, R.anim.anim_alpha);
                    v.startAnimation(myAnimation);
                    SharedPreferences pref = getSharedPreferences("dataH",MODE_PRIVATE);
                    OkHttpClient client = new OkHttpClient();
                    if(content.equals("")||title.equals("")){
                        Toast.makeText(MyApplication.getContext(), "标题和内容不能为空", Toast.LENGTH_SHORT).show();
                    }else {
                        RequestBody requestBody = new FormBody.Builder().add("houseparentID", pref.getString("ID", "")).add("govern",pref.getString("govern","")).add("Atime", Atime).add("title", title).add("content", content).build();
                        //服务器地址，ip地址需要时常更换
                        String address = HttpUtil.address + "createAnnouncement.php";
                        Request request = new Request.Builder().url(address).post(requestBody).build();
                        //匿名内部类实现回调接口
                        client.newCall(request).enqueue(new okhttp3.Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MyApplication.getContext(), "服务器连接失败", Toast.LENGTH_SHORT).show();
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
                                            Toast.makeText(MyApplication.getContext(), "发布成功", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                    finish();
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(MyApplication.getContext(), "发布失败", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });
                    }
                    break;
                    default:
                        break;
            }

        }
    }
}
