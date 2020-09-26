package com.develop.eventmanagement;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class EventAdminAdapter extends RecyclerView.Adapter<EventAdminAdapter.ViewHolder>{

    OnClickInterface onClickInterface;

    public EventAdminAdapter(OnClickInterface onClickInterface) {
        this.onClickInterface = onClickInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_event,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.adminView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout adminView;
        public ViewHolder(View itemView) {
            super(itemView);
            adminView = itemView.findViewById(R.id.adminview);
            adminView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickInterface.onSelect(getAdapterPosition());
                }
            });
        }
    }
}
