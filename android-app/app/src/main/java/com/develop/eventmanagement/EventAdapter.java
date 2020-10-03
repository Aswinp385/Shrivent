package com.develop.eventmanagement;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    OnEventClickInterface onEventClickInterface;
    List<EventRespModel> list = new ArrayList<>();

    public EventAdapter(OnEventClickInterface onEventClickInterface) {
        this.onEventClickInterface = onEventClickInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_event,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        EventRespModel data = list.get(position);

        holder.title.setText(data.getTitle());
        holder.date.setText(data.getDate());
        holder.time.setText(data.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<EventRespModel> list) {
        if(list==null)
            return;

        for(EventRespModel data : list){
            addData(data);
        }
    }



    public void addData(EventRespModel data) {

        if(data!=null && !list.contains(data)){
            list.add(data);
            notifyItemInserted(list.size());
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout mainview;
        TextView title,date,description,time;

        public ViewHolder(View itemView) {
            super(itemView);
            mainview = itemView.findViewById(R.id.mainView);
            title = itemView.findViewById(R.id.event_title_txt);
            date = itemView.findViewById(R.id.eventdate);
            time = itemView.findViewById(R.id.eventtime);
            mainview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEventClickInterface.OnEventClick(getAdapterPosition());
                }
            });
        }
    }
}
