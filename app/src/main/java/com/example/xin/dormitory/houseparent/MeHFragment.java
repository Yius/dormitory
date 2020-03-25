package com.example.xin.dormitory.houseparent;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xin.dormitory.R;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主界面"我"切换页（oyx新加）
 */
public class MeHFragment extends Fragment {

    private TextView name;

    private TextView id;

    private TextView govern;

    private LinearLayout personalInfo;
    private LinearLayout setUp;

    public static MeHFragment newInstance(String title) {
        Bundle args = new Bundle();
        MeHFragment fragment = new MeHFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me_h,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initLinearLayout();
    }

    private void initData(){
        name = getView().findViewById(R.id.tv_me_name);
        id = getView().findViewById(R.id.tv_me_id);
        govern = getView().findViewById(R.id.tv_me_govern);
        SharedPreferences pref = getContext().getSharedPreferences("dataH", getContext().MODE_PRIVATE);
        name.setText(pref.getString("name","")+"宿管");
        id.setText(pref.getString("ID",""));
        govern.setText(pref.getString("govern",""));
    }

    //个人信息和设置点击事件
    private void initLinearLayout(){
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
                    break;
            }
            startActivity(intent);
        }
    }
}
