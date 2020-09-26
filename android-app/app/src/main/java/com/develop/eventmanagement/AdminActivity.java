package com.develop.eventmanagement;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity implements OnClickInterface {

    RecyclerView recycler;
    EventAdminAdapter eAdapter;
    OnClickInterface onClickInterface = this;
    ImageView addeventImg,exitImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        addeventImg = findViewById(R.id.imgaddnew);
        exitImg = findViewById(R.id.imgexit);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setItemAnimator(new DefaultItemAnimator());
        eAdapter = new EventAdminAdapter(onClickInterface);
        recycler.setAdapter(eAdapter);

        exitImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        addeventImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminActivity.this,NewEvent.class));
            }
        });

    }

    private void logout() {
        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.custom_logout_popup);
        d.show();
        TextView tvCancel = d.findViewById(R.id.tvCancel);
        TextView tvLogout = d.findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 startActivity(new Intent(AdminActivity.this,LoginActivity.class));
                 finish();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });


    }

    @Override
    public void onSelect(int pos) {

        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.custom_delete_popup);
        d.show();
        TextView tvCancel = d.findViewById(R.id.tvCancel);
        TextView tvLogout = d.findViewById(R.id.tvLogout);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });


    }
}
