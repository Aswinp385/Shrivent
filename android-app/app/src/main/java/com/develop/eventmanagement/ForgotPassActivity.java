package com.develop.eventmanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton nxtBtn;
    TextView tvBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        nxtBtn = findViewById(R.id.btnReset);
        tvBack = findViewById(R.id.tvBack);

        nxtBtn.setOnClickListener(this);
        tvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnReset:
                break;
            case R.id.tvBack:
                onBackPressed();
                break;
        }
    }
}
