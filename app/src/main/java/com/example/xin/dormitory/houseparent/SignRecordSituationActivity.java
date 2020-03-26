package com.example.xin.dormitory.houseparent;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.xin.dormitory.R;
import com.example.xin.dormitory.Utility.HttpUtil;
import com.example.xin.dormitory.common.Sign;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.xuexiang.xui.widget.dialog.materialdialog.DialogAction;
import com.xuexiang.xui.widget.dialog.materialdialog.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.xin.dormitory.Utility.MyApplication.getContext;

/**
 * 签到情况类
 */
public class SignRecordSituationActivity extends AppCompatActivity {

    private List<Sign> signList = new ArrayList<>();
    private SignAdapterForHouseparent adapter;
    private SwipeRefreshLayout swipeRefresh;
    private FloatingActionButton fab;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_record_situation);
        initSignRecords();
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SignAdapterForHouseparent(signList);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshSignStudents();
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.fab:
                        firstCheck();
                        postNewSign();
                        break;
                }
            }
        });

    }

    /**
     * 初始化显示的签到记录
     */
    private void initSignRecords(){
        signList.clear();

        //学生可以接受属于这栋楼的签到，而宿管只能看自己发布的签到
        SharedPreferences pref = getSharedPreferences("dataH",MODE_PRIVATE);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("houseparentID",pref.getString("ID","")).build();
        //服务器地址，ip地址需要时常更换
        String address=HttpUtil.address+"checkSignRecordInfo.php";
        Request request = new Request.Builder().url(address).post(requestBody).build();
        //匿名内部类实现回调接口
        client.newCall(request).enqueue(new okhttp3.Callback(){

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"服务器连接失败，无法获取信息",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(responseData);
                    for(int i=0;i<jsonArray.length();++i){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        signList.add(new Sign(jsonObject));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //通知数据改变，涉及UI变化，故在runOnUiThread中操作
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"数据加载完成",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void refreshSignStudents(){
        //网络操作耗时，故开子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initSignRecords();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }


    /**
     * 用来发起新签到
     */
    private void postNewSign(){
        new MaterialDialog.Builder(SignRecordSituationActivity.this)
                .customView(R.layout.sign_dialog, true)
                .title("发布新签到")
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        RadioButton radioButton = dialog.findViewById(R.id.rb_current);
                        RadioButton radioButton2 = dialog.findViewById(R.id.rb_others);
                        EditText et_title = dialog.findViewById(R.id.et_title);
                        if(radioButton.isChecked()){
                            //初始化定位
                            mLocationClient = new AMapLocationClient(getApplicationContext());
                            //初始化AMapLocationClientOption对象
                            mLocationOption = new AMapLocationClientOption();
                            mLocationListener = new AMapLocationListener(){
                                @Override
                                public void onLocationChanged(AMapLocation amapLocation) {
                                    if (amapLocation != null) {
                                        if (amapLocation.getErrorCode() == 0) {
                                            postNewSignHelp(et_title.getText().toString(),amapLocation.getLatitude(),amapLocation.getLongitude(),amapLocation.getAddress());
                                        }else {
                                            Toast.makeText(SignRecordSituationActivity.this,"很抱歉，无法获取您的定位信息！",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            };
                            //设置定位回调监听
                            mLocationClient.setLocationListener(mLocationListener);
                            mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
                            //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
                            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                            if(null != mLocationClient){
                                mLocationClient.setLocationOption(mLocationOption);
                            }
                            mLocationClient.stopLocation();
                            mLocationClient.startLocation();
                        }
                        if(radioButton2.isChecked()){
                            Intent intent = new Intent(SignRecordSituationActivity.this,LocationChooseActivity.class);
                            intent.putExtra("title",et_title.getText().toString());
                            startActivity(intent);
                        }
                    }
                })
                .negativeText("取消")
                .show();
    }


    /**
     * 发起新签到所进行的网络交互的协助函数
     * @param title 签到标题
     */
    private void postNewSignHelp(String title,double latitude,double longitude,String detailAddress){
        SharedPreferences pref = getSharedPreferences("dataH",MODE_PRIVATE);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Rtime = formatter.format(new Date(System.currentTimeMillis()));
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("Rtime",Rtime).add("houseparentID",pref.getString("ID",""))
                .add("title",title).add("govern",pref.getString("govern","")).add("latitude",String.valueOf(latitude))
                .add("longitude",String.valueOf(longitude)).add("detailAddress",detailAddress).add("houseparentName",pref.getString("name","")).build();
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
                        Toast.makeText(getContext(),"服务器连接失败，无法发布信息",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getContext(), "发起成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "发起失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    public void firstCheck(){
        //这里以ACCESS_COARSE_LOCATION为例
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},1);//自定义的code
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},2);//自定义的code
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},3);//自定义的code
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},4);//自定义的code
        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},5);//自定义的code
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        for(int result:grantResults){
            if(result!=PackageManager.PERMISSION_GRANTED){
                Toast.makeText(SignRecordSituationActivity.this,"缺少必要的权限！",Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

}
