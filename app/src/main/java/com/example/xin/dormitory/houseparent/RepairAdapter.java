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


/**
 * 此类将被HandledRepairActivity和UnhandledRepairActivity共用
 */
public class RepairAdapter extends RecyclerView.Adapter<RepairAdapter.ViewHolder> {

    private Context mContext;
    private List<Repair> mRepairList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View repairView;
        TextView tv_applyID;
        TextView tv_applyDate;
        TextView tv_dormID;
        TextView tv_repairName;

        public ViewHolder(View view){
            super(view);
            repairView = view;
            tv_applyDate = view.findViewById(R.id.tv_applyDate);
            tv_applyID = view.findViewById(R.id.tv_applyID);
            tv_dormID = view.findViewById(R.id.tv_dormID);
            tv_repairName = view.findViewById(R.id.tv_repairName);
        }
    }

    public RepairAdapter(List<Repair> repairList){
        mRepairList = repairList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.repair,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.repairView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Repair repair = mRepairList.get(position);
                Intent intent = new Intent(view.getContext(),RepairDetailsActivity.class);
                intent.putExtra("repair_data",repair);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repair repair = mRepairList.get(position);
        holder.tv_repairName.setText(repair.getRepairName());
        holder.tv_dormID.setText("报修宿舍:"+repair.getDormID());
        holder.tv_applyID.setText("编号:"+repair.getApplyID());
        holder.tv_applyDate.setText("报修日期:"+repair.getApplyDate());
    }

    @Override
    public int getItemCount() {
        return mRepairList.size();
    }
}
