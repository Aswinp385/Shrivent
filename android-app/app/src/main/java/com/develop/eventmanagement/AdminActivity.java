package com.develop.eventmanagement;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminActivity extends AppCompatActivity implements OnClickInterface {

    RecyclerView recycler;
    EventAdminAdapter eAdapter;
    OnClickInterface onClickInterface = this;
    ImageView addeventImg,exitImg;
    LoadingView loadingView;
    List<EventRespModel> list = new ArrayList<>();
    SwipeRefreshLayout swipeToRefresh;

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
        swipeToRefresh = findViewById(R.id.swipeToRefresh);
        loadingView = new LoadingView(AdminActivity.this);


        swipeToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeToRefresh.setRefreshing(false);
                getEventList();

            }
        });

        getEventList();
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


    private void getEventList() {

        RetrofitAPI getResponse = API.getClient().create(RetrofitAPI.class);

        Call<List<EventRespModel>> call = getResponse.getEventList();
        call.enqueue(new Callback<List<EventRespModel>>() {
            @Override
            public void onResponse(Call<List<EventRespModel>> call, Response<List<EventRespModel>> response) {
                loadingView.hideLoadingView();
                if(response.code()==200){

                    list = new ArrayList<>();
                    eAdapter.clearAll();



                    list = response.body();
                    eAdapter.addList(list);
                    eAdapter.notifyDataSetChanged();
//                    Toast.makeText(LoginActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<EventRespModel>> call, Throwable t) {
                loadingView.hideLoadingView();
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
