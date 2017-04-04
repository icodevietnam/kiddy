package com.coursework.app.iDiscovery;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.coursework.app.R;

import com.coursework.com.coursework.domain.Event;
import com.coursework.com.coursework.domain.iDiscovery;
import com.coursework.helper.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddIDiscoveryActivity extends Activity {
    public  static final String MESSAGE_ACTIVITY_NAME_BLANK = "Activity Name is not blank";
    public  static final String MESSAGE_ACTIVITY_NAME_EXISTED = "Activity Name is existed";
    public  static final String MESSAGE_LOCATION_BLANK = "Location is not blank";
    public  static final String MESSAGE_DATE_BLANK = "Date is not blank";
    public  static final String MESSAGE_DATE_WRONG_FORMAT = "Date is wrong format";
    public  static final String MESSAGE_TIME_BLANK = "Time is not blank";
    public  static final String MESSAGE_TIME_WRONG_FORMAT = "Time is wrong format";
    public  static final String MESSAGE_REPORTER_NAME_BLANK = "Reporter Name is not blank";
    private static int RESULT_LOAD_IMAGE = 1;

    private DBHelper myDb;
    private String activityName;
    private String location;
    private String date;
    private String time;
    private String reporterName;
    private String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kiddy);
        final EditText textActivityName = (EditText)this.findViewById(R.id.textActivityName);
        final EditText textLocation = (EditText)this.findViewById(R.id.textLocation);
        final EditText textDate = (EditText)this.findViewById(R.id.textDate);
        final EditText textTime = (EditText)this.findViewById(R.id.textTime);
        final EditText textReporterName = (EditText)this.findViewById(R.id.textReporterName);
        final TextView message = (TextView)this.findViewById(R.id.txtMessage);
        final Button btnLoadImage = (Button)this.findViewById(R.id.btnLoadImage);
        myDb = new DBHelper(this);
        Button btnSave = (Button)this.findViewById(R.id.btnSave);
        Date now = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
        textDate.setText(sdfDate.format(now));
        textTime.setText(sdfTime.format(now));

        textActivityName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityName = textActivityName.getText().toString();
                validateActivityName(activityName,textActivityName);
            }
        });

        textLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                location = textLocation.getText().toString();
                validateBlank(location,textLocation,MESSAGE_LOCATION_BLANK);
            }
        });

        textDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String regex = "^\\d{2}-\\d{2}-\\d{4}$";
                date = textDate.getText().toString();
                validateDateTime(date,regex,textDate,MESSAGE_DATE_BLANK,MESSAGE_DATE_WRONG_FORMAT);
            }
        });

        textTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String regex = "^\\d{2}:\\d{2}$";
                time = textTime.getText().toString();
                validateDateTime(time,regex,textTime,MESSAGE_TIME_BLANK,MESSAGE_TIME_WRONG_FORMAT);
            }
        });

        textReporterName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                reporterName = textReporterName.getText().toString();
                validateBlank(reporterName,textReporterName,MESSAGE_REPORTER_NAME_BLANK);
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityName = textActivityName.getText().toString();
                location = textLocation.getText().toString();
                String regex = "^\\d{2}-\\d{2}-\\d{4}$";
                date = textDate.getText().toString();
                String regexTime = "^\\d{2}:\\d{2}$";
                time = textTime.getText().toString();
                reporterName = textReporterName.getText().toString();
                if(validateBlank(location,textLocation,MESSAGE_LOCATION_BLANK) && validateBlank(location,textLocation,MESSAGE_LOCATION_BLANK) &&
                        validateDateTime(date,regex,textDate,MESSAGE_DATE_BLANK,MESSAGE_DATE_WRONG_FORMAT) && validateDateTime(time,regexTime,textTime,MESSAGE_TIME_BLANK,MESSAGE_TIME_WRONG_FORMAT) && validateBlank(reporterName,textReporterName,MESSAGE_REPORTER_NAME_BLANK)) {
                    iDiscovery iDiscovery = new iDiscovery();
                    iDiscovery.setActivityName(activityName);
                    iDiscovery.setReporterName(reporterName);
                    iDiscovery.setLocation(location);
                    iDiscovery.setDate(date);
                    iDiscovery.setTime(time);

                    myDb.insertIDiscovery(iDiscovery);

                    Event event = new Event();
                    event.setName("Add Kid Activity " + iDiscovery.getActivityName() + " at " + iDiscovery.getDate() + " " + iDiscovery.getTime());
                    event.setDescription("Activities action name: " + iDiscovery.getActivityName() + " in " + iDiscovery.getLocation() +" successfully !!!");
                    myDb.insertEvent(event);
                    Toast toast = Toast.makeText(getApplicationContext(), "Save successfully ", Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), "It's remain errors", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        btnLoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULT_LOAD_IMAGE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Toast toast = Toast.makeText(getApplicationContext(),picturePath, Toast.LENGTH_LONG);
            toast.show();

//            ImageView imageView = (ImageView) findViewById(R.id.imgView);
//            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

    private boolean validateActivityName(String activityName, EditText textActivityName){
        boolean isValidate =  false;
        if(activityName.length()>0){
            if(myDb.checkIDiscoveryDuplicate(activityName)){
                textActivityName.setError(MESSAGE_ACTIVITY_NAME_EXISTED);
                isValidate = false;
            }else {
                textActivityName.setError(null);
                isValidate = true;
            }
        }else{
            textActivityName.setError(MESSAGE_ACTIVITY_NAME_BLANK);
            isValidate = false;
        }
        return isValidate;
    }

    private boolean validateBlank(String value,EditText editText,String msg){
        boolean isValidate =  false;
        if(value.length()>0){
            editText.setError(null);
            isValidate = true;
        }else{
            editText.setError(msg);
            isValidate = false;
        }
        return isValidate;
    }

    private boolean validateDateTime(String value,String regex,EditText textDateTime,String msgBlank, String msgDateTime){
        boolean isValidate =  false;
        if(value.length()>0){
            if(value.matches(regex)){
                textDateTime.setError(null);
                isValidate = true;
            }else {
                textDateTime.setError(msgDateTime);
                isValidate = false;
            }
        }else{
            textDateTime.setError(msgBlank);
            isValidate = false;
        }
        return isValidate;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_kiddy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
