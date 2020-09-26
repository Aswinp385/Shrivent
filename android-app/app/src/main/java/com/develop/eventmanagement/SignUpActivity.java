package com.develop.eventmanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton signUpBtn;
    TextView backtxt;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpBtn = findViewById(R.id.btnSignup);
        backtxt = findViewById(R.id.tvBack);

        signUpBtn.setOnClickListener(this);
        backtxt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignup:
                break;
            case R.id.tvBack:
                onBackPressed();
                break;
        }
    }
}
