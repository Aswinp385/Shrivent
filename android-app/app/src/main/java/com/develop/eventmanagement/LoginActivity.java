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

                if(validation()){
                    if(userNm.getText().toString().equalsIgnoreCase("admin")){
                        startActivity(new Intent(this, AdminActivity.class));
                        finish();
                    }else {
                        loginUser();

                    }
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

    private boolean validation() {
        if(Validation.checkIsEmpty(userNm)){
            Toast.makeText(LoginActivity.this, "Please enter the email", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!Validation.checkIsAnEmail(userNm)){
            Toast.makeText(LoginActivity.this, "Please Enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }if(Validation.checkIsEmpty(password)){
            Toast.makeText(LoginActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
