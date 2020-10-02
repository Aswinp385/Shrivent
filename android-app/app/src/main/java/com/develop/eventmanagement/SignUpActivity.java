package com.develop.eventmanagement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton signUpBtn;
    TextView backtxt;
    EditText fNameEdt,lastNameEdt,emailEdt,mobileEdt,passEdt,confPassEdt;
    LoadingView loadingView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signUpBtn = findViewById(R.id.btnSignup);
        backtxt   = findViewById(R.id.tvBack);
        fNameEdt  = findViewById(R.id.edtEmail);
        lastNameEdt  = findViewById(R.id.edtLastName);
        emailEdt  = findViewById(R.id.edtEmail);
        mobileEdt = findViewById(R.id.edtmobilNum);
        passEdt   = findViewById(R.id.edtPwd);
        confPassEdt = findViewById(R.id.edtConPwd);
        loadingView = new LoadingView(SignUpActivity.this);

        signUpBtn.setOnClickListener(this);
        backtxt.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSignup:
                createAccount();
                break;
            case R.id.tvBack:
                onBackPressed();
                break;
        }
    }

    private void createAccount() {
        loadingView.showLoadingView();
        String fname = fNameEdt.getText().toString();
        String lname = lastNameEdt.getText().toString();
        String email = emailEdt.getText().toString();
        String pass  = passEdt.getText().toString();
        String mobile = mobileEdt.getText().toString();

        RetrofitAPI getResponse = API.getClient().create(RetrofitAPI.class);

        Call<SignUpResp> call = getResponse.createAccount(fname,lname,email,pass,mobile);
        call.enqueue(new Callback<SignUpResp>() {
            @Override
            public void onResponse(Call<SignUpResp> call, Response<SignUpResp> response) {
                loadingView.hideLoadingView();
                if(response.code()==200){
                    Toast.makeText(SignUpActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SignUpActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResp> call, Throwable t) {
                 loadingView.hideLoadingView();
            }
        });
    }
}
