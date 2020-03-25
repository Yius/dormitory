package com.example.xin.dormitory.houseparent;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xin.dormitory.R;

import java.util.List;

public class StayAdapter extends RecyclerView.Adapter<StayAdapter.ViewHolder>  {

    private Context mContext;
    private List<Stay> mStayList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View stayView;
        //这是提交编号
        TextView tv_theID;
        TextView tv_name;
        TextView tv_registerDate;
        TextView tv_startDate;
        TextView tv_endDate;

        public ViewHolder(View view){
            super(view);
            stayView = view;
            tv_theID = view.findViewById(R.id.tv_theID);
            tv_name = view.findViewById(R.id.tv_name);
            tv_registerDate = view.findViewById(R.id.tv_registerDate);
            tv_startDate = view.findViewById(R.id.tv_startDate);
            tv_endDate = view.findViewById(R.id.tv_endDate);
        }
    }

    public StayAdapter(List<Stay> stayList){
        mStayList = stayList;
    }

    @Override
    public StayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.stay_or_depart,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.stayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Stay stay = mStayList.get(position);
                Intent intent = new Intent(view.getContext(),StayStudentsDetailsActivity.class);
                intent.putExtra("stay_data",stay);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(StayAdapter.ViewHolder holder, int position) {
        Stay stay = mStayList.get(position);
        holder.tv_name.setText("留宿学生:"+stay.getName());
        holder.tv_registerDate.setText("提交日期:"+stay.getRegisterDate());
        holder.tv_theID.setText("编号:"+stay.getStayID());
        holder.tv_startDate.setText("起始日期:"+stay.getStartDate());
        holder.tv_endDate.setText("结束日期:"+stay.getEndDate());
    }

    @Override
    public int getItemCount() {
        return mStayList.size();
    }

}
