package com.example.xin.dormitory.houseparent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xin.dormitory.R;
import com.example.xin.dormitory.Utility.HttpUtil;
import com.example.xin.dormitory.Utility.MyApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xuexiang.xui.widget.tabbar.TabControlView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StayAndDepartActivity extends AppCompatActivity {

    private List<Stay> stayList = new ArrayList<>();
    private List<Depart> departList = new ArrayList<>();
    private StayAdapter stayAdapter;
    private DepartAdapter departAdapter;
    private SmartRefreshLayout smartRefresh;
    private final int CHECK_DEPART_STUDENT = 0;
    private final int CHECK_STAY_STUDENT = 1;
    private int contentMode = CHECK_DEPART_STUDENT;
    private RecyclerView recyclerView;
    private TabControlView mTabControlView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stay_and_depart);

        recyclerView = findViewById(R.id.recycle_view);
        mTabControlView = findViewById(R.id.tcv_select);
        try {
            mTabControlView.setItems(new String[]{"离宿","留宿"}, new String[]{"0","1"});
            mTabControlView.setDefaultSelection(contentMode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTabControlView.setOnTabSelectionChangedListener(new TabControlView.OnTabSelectionChangedListener() {
            @Override
            public void newSelection(String title, String value) {
                contentMode = Integer.parseInt(value);
                adapterSettingHelper();
                refreshStudentInfo();
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        departAdapter = new DepartAdapter(departList);
        stayAdapter = new StayAdapter(stayList);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        smartRefresh = findViewById(R.id.smart_refresh);
        smartRefresh.setRefreshHeader(getRefreshHeader("WaterDropHeader"));
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                adapterSettingHelper();
                refreshStudentInfo();
            }
        });

        adapterSettingHelper();
        refreshStudentInfo();

    }


    /**
     * 初始化显示修理申请
     */
    private void initDepartInfo(){
        departList.clear();

        SharedPreferences pref = getSharedPreferences("dataH",MODE_PRIVATE);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("govern",pref.getString("govern","")).build();
        //服务器地址，ip地址需要时常更换
        String address=HttpUtil.address+"departStudentsInfo.php";
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
                    JSONArray jsonArray = new JSONArray(responseData);
                    for(int i=0;i<jsonArray.length();++i){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        departList.add(new Depart(jsonObject));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //通知数据改变，涉及UI变化，故在runOnUiThread中操作
                            departAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(),"数据加载完成",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    /**
     * 初始化显示的留宿学生
     */
    private void initStayInfo(){
        stayList.clear();

        SharedPreferences pref = getSharedPreferences("dataH",MODE_PRIVATE);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("govern",pref.getString("govern","")).build();
        //服务器地址，ip地址需要时常更换
        String address=HttpUtil.address+"stayStudentsInfo.php";
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
                    JSONArray jsonArray = new JSONArray(responseData);
                    for(int i=0;i<jsonArray.length();++i){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        stayList.add(new Stay(jsonObject));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //通知数据改变，涉及UI变化，故在runOnUiThread中操作
                            stayAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(),"数据加载完成",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void refreshStudentInfo(){
        //网络操作耗时，故开子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (contentMode){
                            case CHECK_DEPART_STUDENT:
                                initDepartInfo();
                                break;
                            case CHECK_STAY_STUDENT:
                                initStayInfo();
                                break;
                        }
                        smartRefresh.finishRefresh();
                    }
                });
            }
        }).start();
    }

    private void adapterSettingHelper(){
        switch (contentMode){
            case CHECK_DEPART_STUDENT:
                recyclerView.setAdapter(departAdapter);
                break;
            case CHECK_STAY_STUDENT:
                recyclerView.setAdapter(stayAdapter);
                break;
        }
    }

    private RefreshHeader getRefreshHeader(String name) {
        try {
            Class<?> headerClass = Class.forName("com.scwang.smartrefresh.header." + name);
            Constructor<?> constructor = headerClass.getConstructor(Context.class);
            return  (RefreshHeader) constructor.newInstance(MyApplication.getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
