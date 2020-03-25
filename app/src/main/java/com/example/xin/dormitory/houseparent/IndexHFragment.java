package com.example.xin.dormitory.houseparent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xin.dormitory.R;
import com.example.xin.dormitory.Utility.BottomNavigationBarUtils;
import com.xuexiang.xui.utils.DensityUtils;
import com.xuexiang.xui.utils.StatusBarUtils;
import com.xuexiang.xui.utils.Utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 宿管主界面首页面（oyx新加）
 */
public class IndexHFragment extends Fragment {

    private LinearLayout linearLayout;

    private TextView name;

    private RelativeLayout wx_handle;
    private RelativeLayout announcement;
    private RelativeLayout check_in;
    private RelativeLayout depart_stay;

    public static IndexHFragment newInstance(String title) {
        Bundle args = new Bundle();
        IndexHFragment fragment = new IndexHFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index_h,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initLinearLayout();
        initTextView();
        initRelativeLayout();
    }


    //初始化主界面，适应不同的手机屏幕大小
    private void initLinearLayout(){
        linearLayout = getView().findViewById(R.id.ll_index_h);
        ViewGroup.LayoutParams lp;
        lp = linearLayout.getLayoutParams();
        Log.d("w",""+lp.width);
        //int w =DensityUtils.px2dp(Utils.getScreenWidth(view.getContext()));

        //获取当前底部虚拟按键的高度（不存在为0）
        int cur_bh = BottomNavigationBarUtils.getNavigationBarHeightIfRoom(getContext());
        //获取非全面屏下虚拟按键的高度（无论是否隐藏）
        int real_bh= BottomNavigationBarUtils.getNavigationBarHeight(getContext());
        Log.d("height",""+real_bh);

        int h = DensityUtils.px2dp(Utils.getScreenHeight(getContext()));
        lp.height = DensityUtils.dp2px(h- StatusBarUtils.getStatusBarHeight(getContext())-55-10-DensityUtils.px2dp((cur_bh>0?0:real_bh)));
        lp.width = 2*lp.height/3;
        Log.d("saa",""+lp.height);
        linearLayout.setLayoutParams(lp);
    }

    private void initTextView(){
        name = getView().findViewById(R.id.tv_name_h);
        SharedPreferences pref = getContext().getSharedPreferences("dataH", getContext().MODE_PRIVATE);
        name.setText(pref.getString("name","")+"宿管");
    }

    //主界面四个功能的点击事件
    private void initRelativeLayout(){
        OnClick onClick = new OnClick();
        wx_handle = getView().findViewById(R.id.rl_weixiu_handle);
        announcement = getView().findViewById(R.id.rl_announcement);
        check_in = getView().findViewById(R.id.rl_check_in);
        depart_stay = getView().findViewById(R.id.rl_depart_stay);
        wx_handle.setOnClickListener(onClick);
        announcement.setOnClickListener(onClick);
        check_in.setOnClickListener(onClick);
        depart_stay.setOnClickListener(onClick);
    }

    private class OnClick implements RelativeLayout.OnClickListener {
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.rl_weixiu_handle:
                    intent = new Intent(getActivity(), UnhandledRepairActivity.class);
                    break;
                case R.id.rl_announcement:
                    intent = new Intent(getActivity(), ManagerAnnouncementActivity.class);
                    break;
                case R.id.rl_check_in:
                    intent = new Intent(getActivity(), ManagerSignUpActivity.class);
                    break;
                case R.id.rl_depart_stay:
                    intent = new Intent(getActivity(), CheckStayAndDepartActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}
