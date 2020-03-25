package com.example.xin.dormitory.houseparent;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xin.dormitory.common.Sign;
import com.example.xin.dormitory.R;

import java.util.List;

//未完成
public class SignAdapterForHouseparent extends RecyclerView.Adapter<SignAdapterForHouseparent.ViewHolder> {

    private Context mContext;
    private List<Sign> mSignList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View signView;
        //这是发布编号
        TextView tv_theID;
        TextView tv_Rtime;
        TextView tv_nums;
        TextView tv_title;
        TextView tv_totalnums;

        public ViewHolder(View view){
            super(view);
            signView = view;
            tv_theID = view.findViewById(R.id.tv_theID);
            tv_Rtime = view.findViewById(R.id.tv_Rtime);
            tv_nums = view.findViewById(R.id.tv_nums);
            tv_title = view.findViewById(R.id.tv_title);
            tv_totalnums = view.findViewById(R.id.tv_totalnums);
        }
    }

    public SignAdapterForHouseparent(List<Sign> signList){
        mSignList = signList;
    }

    @Override
    public SignAdapterForHouseparent.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.sign_for_houseparent,parent,false);
        final SignAdapterForHouseparent.ViewHolder holder = new SignAdapterForHouseparent.ViewHolder(view);
        holder.signView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Sign sign = mSignList.get(position);
                Intent intent = new Intent(view.getContext(),CheckUnsignedStudentsActivity.class);
                intent.putExtra("sign_data",sign);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(SignAdapterForHouseparent.ViewHolder holder, int position) {
        Sign sign = mSignList.get(position);
        holder.tv_Rtime.setText("发布时间:"+sign.getRtime());
        holder.tv_title.setText("标题:"+sign.getTitle());
        holder.tv_theID.setText("编号:"+sign.getID());
        holder.tv_nums.setText("签到人数:"+sign.getNums());
        holder.tv_totalnums.setText("应签到人数:"+sign.getTotalnums());
    }

    @Override
    public int getItemCount() {
        return mSignList.size();
    }
    
}
