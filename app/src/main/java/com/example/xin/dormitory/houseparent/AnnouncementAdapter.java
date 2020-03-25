package com.example.xin.dormitory.houseparent;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xin.dormitory.common.Announcement;
import com.example.xin.dormitory.R;

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder>{

    private Context mContext;
    private List<Announcement> mAnnouncementList;

    static class ViewHolder extends RecyclerView.ViewHolder{

        View announcementView;
        //这是发布编号
        TextView tv_theID;
        TextView tv_title;
        TextView tv_Atime;

        public ViewHolder(View view){
            super(view);
            announcementView = view;
            tv_theID = view.findViewById(R.id.tv_theID);
            tv_title = view.findViewById(R.id.tv_title);
            tv_Atime = view.findViewById(R.id.tv_Atime);
        }
    }

    public AnnouncementAdapter(List<Announcement> announcementList){
        mAnnouncementList = announcementList;
    }

    @Override
    public AnnouncementAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if(mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.announcement,parent,false);
        final AnnouncementAdapter.ViewHolder holder = new AnnouncementAdapter.ViewHolder(view);
        holder.announcementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Announcement announcement = mAnnouncementList.get(position);
                Intent intent = new Intent(view.getContext(),AnnouncementDetailActivity.class);
                intent.putExtra("announcement_data",announcement);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(AnnouncementAdapter.ViewHolder holder, int position) {
        Announcement announcement = mAnnouncementList.get(position);
        holder.tv_theID.setText("编号:"+announcement.getID());
        holder.tv_title.setText("标题:"+announcement.getTitle());
        holder.tv_Atime.setText("发布时间:"+announcement.getAtime());
    }

    @Override
    public int getItemCount() {
        return mAnnouncementList.size();
    }
   
}
