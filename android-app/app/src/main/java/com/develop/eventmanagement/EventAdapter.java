package com.develop.eventmanagement;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder>{

    OnEventClickInterface onEventClickInterface;

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

    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout mainview;

        public ViewHolder(View itemView) {
            super(itemView);
            mainview = itemView.findViewById(R.id.mainView);
            mainview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEventClickInterface.OnEventClick(getAdapterPosition());
                }
            });
        }
    }
}
