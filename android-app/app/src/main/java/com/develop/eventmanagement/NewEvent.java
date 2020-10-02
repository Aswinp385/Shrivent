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

public class NewEvent extends AppCompatActivity {

    EditText eventTitle,eventDesc,eventCost,eventLocation;
    TextView eventDate,eventTime;
    AppCompatButton addBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newevent);

        eventTitle  = findViewById(R.id.edteventTitle);
        eventDesc = findViewById(R.id.edteventdesc);
        eventCost = findViewById(R.id.edteventcost);
        eventLocation = findViewById(R.id.edteventlocation);
        eventDate = findViewById(R.id.eventdate);
        eventTime = findViewById(R.id.eventtime);
        addBtn = findViewById(R.id.addeventBtn);

        chooseImage();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent();
            }
        });


    }

    private void addNewEvent() {

        String title = eventTitle.getText().toString();
        String desc = eventDesc.getText().toString();
        String cost = eventCost.getText().toString();
        String location = eventLocation.getText().toString();
        String date = eventDate.getText().toString();
        String time = eventTime.getText().toString();
        String guest = eventCost.getText().toString();

        RetrofitAPI getResponse = API.getClient().create(RetrofitAPI.class);

        Call<SignUpResp> call = getResponse.addEvent(title,desc,guest,location,date,time);
        call.enqueue(new Callback<SignUpResp>() {
            @Override
            public void onResponse(Call<SignUpResp> call, Response<SignUpResp> response) {
                if(response.code()==200){
                    Toast.makeText(NewEvent.this, "Event Added Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(NewEvent.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResp> call, Throwable t) {

            }
        });


    }


    private void chooseImage() {


    }
}
