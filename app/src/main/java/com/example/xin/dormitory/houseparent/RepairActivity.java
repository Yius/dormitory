package com.example.xin.dormitory.houseparent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xin.dormitory.R;
import com.example.xin.dormitory.Utility.HttpUtil;
import com.example.xin.dormitory.Utility.MyApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RepairActivity extends AppCompatActivity {

    private List<Repair> repairList = new ArrayList<>();
    private RepairAdapter adapter;
    private SmartRefreshLayout smartRefresh;

    //显示全部则为"2",已处理为"1"，未处理为"0"。
    private String which = "2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);
        initRepair();
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RepairAdapter(repairList);
        recyclerView.setAdapter(adapter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        TitleBar titleBar = findViewById(R.id.titlebar);
//        titleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_navigation_more) {
//            @Override
//            public void performAction(View view) {
//                simulateKey(KeyEvent.KEYCODE_MENU);
//            }
//        });

        smartRefresh = findViewById(R.id.smart_refresh);
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                refreshRepairs();
            }
        });
    }

    /**
     * 初始化显示修理申请
     */
    private void initRepair(){
        repairList.clear();

        SharedPreferences pref = getSharedPreferences("dataH",MODE_PRIVATE);
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("which",which).add("govern",pref.getString("govern","")).build();
        //服务器地址，ip地址需要时常更换
        String address= HttpUtil.address+"getRepairInfo.php";
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
                        repairList.add(new Repair(jsonObject));
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
                        Toast.makeText(MyApplication.getContext(),"数据加载完成",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void refreshRepairs(){
        //网络操作耗时，故开子线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initRepair();
                        smartRefresh.finishRefresh();
                    }
                });
            }
        }).start();
    }


    /**
     * 菜单设置
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载 布局实现
        getMenuInflater().inflate(R.menu.menu_repair_h, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 菜单的选择事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.show_handled:
                which = "1";
                smartRefresh.autoRefresh();
                break;
            case R.id.show_unhandled:
                which = "0";
                smartRefresh.autoRefresh();
                break;
            case R.id.show_all:
                which = "2";
                smartRefresh.autoRefresh();
        }
        return super.onOptionsItemSelected(item);
    }

//    public static void simulateKey(final int KeyCode) {
//        new Thread() {
//            public void run() {
//                try {
//                    Instrumentation inst = new Instrumentation();
//                    inst.sendKeyDownUpSync(KeyCode);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//    }

}
