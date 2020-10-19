package com.develop.eventmanagement;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEvent extends AppCompatActivity implements com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {

    EditText eventTitle,eventDesc,eventCost,eventLocation,eventGuest;
    TextView eventDate,eventTime,eventToTime;
    AppCompatButton addBtn;
    ImageView dateImg,backImg;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0;
    public int req = 101;
    Uri filePath = null;
    File myImgData = null;
    Bitmap myimg;
    String myimagepath = "";
    String imagename = "",id ="";
    RoundRectCornerImageView eventimg;
    LoadingView loadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newevent);

        eventTitle  = findViewById(R.id.edteventTitle);
        eventDesc = findViewById(R.id.edteventdesc);
        eventCost = findViewById(R.id.edteventcost);
        eventGuest = findViewById(R.id.edteventguest);
        eventLocation = findViewById(R.id.edteventlocation);
        eventDate = findViewById(R.id.eventdate);
        eventTime = findViewById(R.id.eventtime);
        eventToTime = findViewById(R.id.eventtotime);
        addBtn = findViewById(R.id.addeventBtn);
        dateImg = findViewById(R.id.dobImg);
        backImg = findViewById(R.id.backImg);
        eventimg = findViewById(R.id.eventImg);
        loadingView = new LoadingView(EditEvent.this);

        id = getIntent().getStringExtra("id");

        addBtn.setText("Update");

        getEvent(id);


        eventimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = Utilities.checkPermissionStorage(EditEvent.this);
                boolean result1 = Utilities.checkCamera(EditEvent.this);
                if (result && result1) {
                    selectImage();
                }
            }
        });
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
                TimePickerDialog picker = new TimePickerDialog(EditEvent.this,
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
                TimePickerDialog picker = new TimePickerDialog(EditEvent.this,
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
                loadingView.showLoadingView();
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
                        .context(EditEvent.this)
                        .callback(EditEvent.this)
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

    private void getEvent(String id) {
        RetrofitAPI getResponse = API.getClient().create(RetrofitAPI.class);

        Call<EventRespModel> call = getResponse.getEvent(id);
        call.enqueue(new Callback<EventRespModel>() {
            @Override
            public void onResponse(Call<EventRespModel> call, Response<EventRespModel> response) {
                loadingView.hideLoadingView();
                if(response.code()==200){

                    eventTitle.setText(response.body().getTitle());
                    eventDesc.setText(response.body().getDescription());
                    eventCost.setText(response.body().getCost());
                    eventDate.setText(response.body().getDate());
                    eventLocation.setText(response.body().getLocation());
                    eventGuest.setText(response.body().getGuest());
                    eventDate.setText(response.body().getDate());
                    if(!response.body().getTime().equals("")) {
                        String[] dates = response.body().getTime().split("-");
                        eventTime.setText(dates[0]);
                        eventToTime.setText(dates[1]);
                    }
                    if(!response.body().getFile().equals("")){
                        Picasso.with(EditEvent.this).load(response.body().getFile()).into(eventimg);
                    }
                    myimagepath = convertUrlToBase64(response.body().getFile() + "");
                }else{
                    Toast.makeText(EditEvent.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<EventRespModel> call, Throwable t) {
                loadingView.hideLoadingView();
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
        String guest = eventGuest.getText().toString();
        String image = myimagepath;

        RetrofitAPI getResponse = API.getClient().create(RetrofitAPI.class);

        Call<SignUpResp> call = getResponse.updateEvent(id,title,desc,guest,cost,location,date,time,image);
        call.enqueue(new Callback<SignUpResp>() {
            @Override
            public void onResponse(Call<SignUpResp> call, Response<SignUpResp> response) {
                loadingView.hideLoadingView();
                if(response.code()==200){
                    Toast.makeText(EditEvent.this, "Event Updated Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(EditEvent.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUpResp> call, Throwable t) {
                loadingView.hideLoadingView();
            }
        });


    }


    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditEvent.this);
        //builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    cameraIntent();

                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask = "Choose from Gallery";
                    chooseImage(req);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void chooseImage(int requestCode) {

        List<Intent> targets = new ArrayList<Intent>();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        List<ResolveInfo> candidates = getPackageManager().queryIntentActivities(intent, 0);

        for (ResolveInfo candidate : candidates) {
            String packageName = candidate.activityInfo.packageName;
            if (!packageName.equals("com.google.android.apps.docs") && !packageName.equals("com.google.android.apps.plus") && !packageName.equals("com.android.documentsui")) {
                Intent iWantThis = new Intent();
                iWantThis.setType("image/*");
                iWantThis.setAction(Intent.ACTION_GET_CONTENT);
                iWantThis.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                iWantThis.setPackage(packageName);
                targets.add(iWantThis);
            }
        }
        Intent chooser = Intent.createChooser(targets.remove(0), "Select Picture");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targets.toArray(new Parcelable[targets.size()]));
        startActivityForResult(chooser, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == req) {
                // filePath = data.getData();
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                Log.d("filepath", "file path is" + filePath);
                try {

                    myimg = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(data.getData(), filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                    String filePath = cursor.getString(columnIndex);

                    cursor.close();

                    //String mypath = getPath(data.getData());

                    int rotateImageAngle = getPhotoOrientation(EditEvent.this, data.getData(), filePath);
                    if (myimg != null) {
                        myimg = RotateBitmap(myimg, rotateImageAngle);
                    }
                    myimg.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                    // Log.e("datapath", "path is from gallery=" + mypath);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                myImgData = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                imagename = System.currentTimeMillis() + ".jpg";

                FileOutputStream fo;
                try {
                    myImgData.createNewFile();
                    fo = new FileOutputStream(myImgData);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath = Uri.fromFile(myImgData);
                    myimagepath = getFileToByte(myImgData + "");
                    if (myimagepath.equalsIgnoreCase("")){

                    }else {
                      //  UpDateUsetImagePart1();
                    }

                    Log.d("MYFUUFUU", "data=" + myimagepath);
                    // Toast.makeText(this, "image path sighup=" + filePath, Toast.LENGTH_SHORT).show();
                    eventimg.setImageBitmap(myimg);
                    //   uploadMultipart();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                File destination = new File(Environment.getExternalStorageDirectory(),
                        System.currentTimeMillis() + ".jpg");
                imagename = System.currentTimeMillis() + ".jpg";

                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                    filePath = Uri.fromFile(destination);
                    myimagepath = getFileToByte(destination + "");
                    if (myimagepath.equalsIgnoreCase("")){

                    }else {
//                        UpDateUsetImagePart1();
                    }

                    Log.d("MYFUUFUU", "data=" + myimagepath);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                eventimg.setImageBitmap(thumbnail);
            }
        }

    }

    public int getPhotoOrientation(Context context, Uri imageUri, String imagePath) {
        int rotate = 0;
        try {
            context.getContentResolver().notifyChange(imageUri, null);
            File imageFile = new File(imagePath);

            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.i("RotateImage", "Exif orientation: " + orientation);
            Log.i("RotateImage", "Rotate value: " + rotate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static Bitmap RotateBitmap(Bitmap source, int angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public String convertUrlToBase64(String url) {
        URL newurl;
        Bitmap bitmap;
        String base64 = "";
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            newurl = new URL(url);
            bitmap = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            base64 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "data:image/jpeg;base64,"+base64;
    }

    private String getByteArrayFromImageURL(String url) {

        try {
            URL imageUrl = new URL(url);
            URLConnection ucon = imageUrl.openConnection();
            InputStream is = ucon.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int read = 0;
            while ((read = is.read(buffer, 0, buffer.length)) != -1) {
                baos.write(buffer, 0, read);
            }
            baos.flush();
            return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        } catch (Exception e) {
            Log.d("Error", e.toString());
        }
        return null;
    }


    public String getFileToByte(String filePath) {
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "data:image/jpeg;base64," + encodeString;
    }
}
