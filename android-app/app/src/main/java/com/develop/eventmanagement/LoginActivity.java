package com.develop.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton loginBtn;
    TextView forgotPasstxt,signuptxt;
    EditText userNm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.btnLogin);
        forgotPasstxt = findViewById(R.id.tvforgtPassword);
        signuptxt = findViewById(R.id.tvSignup);
        userNm = findViewById(R.id.edtUnm);


        loginBtn.setOnClickListener(this);
        forgotPasstxt.setOnClickListener(this);
        signuptxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                if(userNm.getText().toString().equalsIgnoreCase("admin")){
                    startActivity(new Intent(this, AdminActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
            case R.id.tvforgtPassword:
                startActivity(new Intent(this,ForgotPassActivity.class));

                break;
            case R.id.tvSignup:
                startActivity(new Intent(this,SignUpActivity.class));

                break;
        }
    }
}
