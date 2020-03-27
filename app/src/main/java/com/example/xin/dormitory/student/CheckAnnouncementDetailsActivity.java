package com.example.xin.dormitory.student;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xin.dormitory.common.Announcement;
import com.example.xin.dormitory.R;
import com.example.xin.dormitory.Utility.HttpUtil;
import com.example.xin.dormitory.Utility.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 因学生的公告详情应该要能看到发布人ID，故另建此类
 */
public class CheckAnnouncementDetailsActivity extends AppCompatActivity {

    private TextView tv_title;
    private TextView tv_Atime;
    private TextView tv_content;
    private TextView tv_ID;
    private TextView tv_houseparentID;
    private Announcement announcement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_announcement_details);
        initLayout();
    }

    /**
     * 把布局初始化的代码写在一起
     */
    private void initLayout(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_ID = findViewById(R.id.tv_ID);
        tv_Atime = findViewById(R.id.tv_Atime);
        tv_content = findViewById(R.id.tv_content);
        tv_content.setMovementMethod(new ScrollingMovementMethod());
        tv_title = findViewById(R.id.tv_title);
        tv_houseparentID = findViewById(R.id.tv_houseparentID);
        announcement = (Announcement) getIntent().getSerializableExtra("announcement_data");
        tv_ID.setText(String.valueOf(announcement.getID()));
        tv_title.setText(announcement.getTitle());
        tv_content.setText(announcement.getContent());
        tv_Atime.setText(announcement.getAtime());
        tv_houseparentID.setText(announcement.getHouseparentID());
        setListeners();

    }


    /**
     * 监听器初始化
     */
    private void setListeners(){
        OnClick onClick = new OnClick();
        tv_houseparentID.setOnClickListener(onClick);
    }



    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_houseparentID:
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder().add("ID",announcement.getHouseparentID()).build();
                    //服务器地址，ip地址需要时常更换
                    String address=HttpUtil.address+"infoH.php";
                    Request request = new Request.Builder().url(address).post(requestBody).build();
                    //匿名内部类实现回调接口
                    client.newCall(request).enqueue(new okhttp3.Callback(){

                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MyApplication.getContext(),"服务器连接失败，无法获取信息",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseData = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(responseData);
                                //TODO 你来写,jsonObject的内容有：ID,name,govern,phone,password。分别代表宿管ID，宿管姓名，宿管管理楼层，宿管手机号，宿管密码，不一定全部用到
                                Intent intent = new Intent(CheckAnnouncementDetailsActivity.this,AddContactsHActivity.class);
                                intent.putExtra("contactHName",jsonObject.getString("name"));
                                intent.putExtra("contactHID",jsonObject.getString("ID"));
                                intent.putExtra("contactHPhone",jsonObject.getString("phone"));
                                intent.putExtra("contactHGovern",jsonObject.getString("govern"));
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

}
