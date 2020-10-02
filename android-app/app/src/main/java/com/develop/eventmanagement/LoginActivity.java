package com.develop.eventmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    AppCompatButton loginBtn;
    TextView forgotPasstxt,signuptxt;
    EditText userNm,password;
    LoadingView loadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.btnLogin);
        forgotPasstxt = findViewById(R.id.tvforgtPassword);
        signuptxt = findViewById(R.id.tvSignup);
        userNm = findViewById(R.id.edtUnm);
        password = findViewById(R.id.edtPwd);
        loadingView = new LoadingView(LoginActivity.this);

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
                    loginUser();
//                    startActivity(new Intent(this, MainActivity.class));
//                    finish();
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


    private void loginUser() {
        loadingView.showLoadingView();

        String email = userNm.getText().toString();
        String pass  = password.getText().toString();

        RetrofitAPI getResponse = API.getClient().create(RetrofitAPI.class);

        Call<LoginResp> call = getResponse.LoginUrl(email,pass);
        call.enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
                loadingView.hideLoadingView();
                if(response.code()==200){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

//                    Toast.makeText(LoginActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResp> call, Throwable t) {
                loadingView.hideLoadingView();
            }
        });
    }
}
