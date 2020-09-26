package com.develop.eventmanagement;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements OnEventClickInterface {

    RecyclerView recycler;
    EventAdapter eAdapter;
    OnEventClickInterface onEventClickInterface = this;
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
        eAdapter = new EventAdapter(onEventClickInterface);
        recycler.setAdapter(eAdapter);


        addeventImg.setVisibility(View.GONE);

        exitImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
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
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
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
    public void OnEventClick(int pos) {
        final Dialog mBottomSheetDialog = new BottomSheetDialog(this,
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView (R.layout.dialog_item_detail);
        mBottomSheetDialog.setCancelable (true);
        mBottomSheetDialog.getWindow ().setLayout (LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow ().setGravity (Gravity.BOTTOM);


        TextView title = mBottomSheetDialog.findViewById(R.id.item_title);
        TextView description = mBottomSheetDialog.findViewById(R.id.item_description);
        RoundRectCornerImageView image = mBottomSheetDialog.findViewById(R.id.item_img);
        AppCompatButton submit   = mBottomSheetDialog.findViewById(R.id.dialog_close);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });

        mBottomSheetDialog.show ();
    }
}
