package com.develop.eventmanagement;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewEvent extends AppCompatActivity implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {

    EditText eventTitle,eventDesc,eventCost,eventLocation;
    TextView eventDate,eventTime,eventToTime;
    AppCompatButton addBtn;
    ImageView dateImg,backImg;

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
        eventToTime = findViewById(R.id.eventtotime);
        addBtn = findViewById(R.id.addeventBtn);
        dateImg = findViewById(R.id.dobImg);
        backImg = findViewById(R.id.backImg);

        chooseImage();

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(NewEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eventTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        eventToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(NewEvent.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                eventToTime.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                picker.show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewEvent();
            }
        });

        dateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int Year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day  = cal.get(Calendar.DAY_OF_MONTH);
                new SpinnerDatePickerDialogBuilder()
                        .context(NewEvent.this)
                        .callback(NewEvent.this)
                        .spinnerTheme(R.style.NumberPickerStyle)
                        .showTitle(false)
                        .showDaySpinner(true)
                        .defaultDate(Year, month, day)
                        .maxDate(Year+2, 12, 31)
                        .minDate(Year, month, day)
                        .build()
                        .show();
                //  DatePickerDialog dateDialog = new DatePickerDialog()
            }
        });


    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
       String  mydate = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        eventDate.setText(mydate);
        //  Toast.makeText(this, year+"/"+(monthOfYear+1)+"/"+dayOfMonth, Toast.LENGTH_SHORT).show();
    }

    private void addNewEvent() {

        String title = eventTitle.getText().toString();
        String desc = eventDesc.getText().toString();
        String cost = eventCost.getText().toString();
        String location = eventLocation.getText().toString();
        String date = eventDate.getText().toString();
        String time = eventTime.getText().toString()+" - "+eventToTime.getText().toString();
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
