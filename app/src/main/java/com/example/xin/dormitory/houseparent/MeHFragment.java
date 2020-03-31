package com.example.xin.dormitory.houseparent;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xin.dormitory.R;
import com.example.xin.dormitory.Utility.HttpUtil;
import com.example.xin.dormitory.Utility.MyApplication;
import com.xuexiang.xui.widget.imageview.RadiusImageView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 主界面"我"切换页（oyx新加）
 */
public class MeHFragment extends Fragment {

    private TextView name;

    private TextView id;

    private TextView govern;

    private LinearLayout personalInfo;
    private LinearLayout setUp;
    private RadiusImageView radiusImageView;

    public static MeHFragment newInstance(String title) {
        Bundle args = new Bundle();
        MeHFragment fragment = new MeHFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_h, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initLinearLayout();
//        loadAvatar();
    }

    private void initData() {
        name = getView().findViewById(R.id.tv_me_name);
        id = getView().findViewById(R.id.tv_me_id);
        govern = getView().findViewById(R.id.tv_me_govern);
        SharedPreferences pref = getContext().getSharedPreferences("dataH", getContext().MODE_PRIVATE);
        name.setText(pref.getString("name", "") + "宿管");
        id.setText(pref.getString("ID", ""));
        govern.setText(pref.getString("govern", ""));
        radiusImageView = getView().findViewById(R.id.personal_img);
    }

    //个人信息和设置点击事件
    private void initLinearLayout() {
        OnClick onClick = new OnClick();
        personalInfo = getView().findViewById(R.id.ll_personal_info_h);
        setUp = getView().findViewById(R.id.ll_set_up_h);
        personalInfo.setOnClickListener(onClick);
        setUp.setOnClickListener(onClick);
    }

    private class OnClick implements LinearLayout.OnClickListener {
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.ll_personal_info_h:
                    intent = new Intent(getActivity(), SelfInfoHActivity.class);
                    break;
                case R.id.ll_set_up_h:
                    intent = new Intent(getActivity(), SetWaterAndElectricityActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

    private void loadAvatar() {

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("ID", HttpUtil.HID).add("type", "houseparent").build();
        //服务器地址，ip地址需要时常更换
        String address = HttpUtil.address + "getAvatar.php";
        Request request = new Request.Builder().url(address).post(requestBody).build();
        //匿名内部类实现回调接口
        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getContext(), "连接失败，无法获取您的头像", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                byte[] check = new byte[1024];
                //判断是否为空
                if (inputStream.read(check)==-1) {
                    //记得更新图片要在UI线程
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            radiusImageView.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        loadAvatar();
    }

}
