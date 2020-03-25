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

public class DepartAdapter extends RecyclerView.Adapter<DepartAdapter.ViewHolder>  {

    private Context mContext;
    private List<Depart> mDepartList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View departView;
        //这是提交编号
        TextView tv_theID;
        TextView tv_name;
        TextView tv_registerDate;
        TextView tv_departTime;
        TextView tv_backTime;

        public ViewHolder(View view){
            super(view);
            departView = view;
            tv_theID = view.findViewById(R.id.tv_theID);
            tv_name = view.findViewById(R.id.tv_name);
            tv_registerDate = view.findViewById(R.id.tv_registerDate);
            tv_departTime = view.findViewById(R.id.tv_startDate);
            tv_backTime = view.findViewById(R.id.tv_endDate);
        }
    }

    public DepartAdapter(List<Depart> departList){
        mDepartList = departList;
    }

    @Override
    public DepartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.stay_or_depart,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.departView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Depart depart = mDepartList.get(position);
                Intent intent = new Intent(view.getContext(),DepartStudentsDetailsActivity.class);
                intent.putExtra("depart_data",depart);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(DepartAdapter.ViewHolder holder, int position) {
        Depart depart = mDepartList.get(position);
        holder.tv_name.setText("离宿学生:"+ depart.getName());
        holder.tv_registerDate.setText("提交日期:"+ depart.getRegisterDate());
        holder.tv_theID.setText("编号:"+ depart.getDepartID());
        holder.tv_departTime.setText("离宿日期:"+ depart.getDepartTime());
        holder.tv_backTime.setText("返宿日期:"+ depart.getBackTime());
    }

    @Override
    public int getItemCount() {
        return mDepartList.size();
    }

}
