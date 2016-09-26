package com.coursework.app;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coursework.com.coursework.domain.Bird;
import com.coursework.com.coursework.domain.Event;
import com.coursework.helper.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddBirdActivity extends Activity {
    private DBHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bird);
        final EditText birdName = (EditText)this.findViewById(R.id.inputBirdName);
        final EditText birdLocation = (EditText)this.findViewById(R.id.inputLocation);
        final EditText birdDate = (EditText)this.findViewById(R.id.inputDate);
        final EditText birdTime = (EditText)this.findViewById(R.id.inputTimeSee);
        final EditText watcherName = (EditText)this.findViewById(R.id.inputWatcherName);
        final TextView message = (TextView)this.findViewById(R.id.txtMessage);
        myDb = new DBHelper(this);
        Button btnSave = (Button)this.findViewById(R.id.btnSave);
        Date now = new Date();
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        birdDate.setText(sdfDate.format(now));
        birdTime.setText(sdfTime.format(now));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message.setTextColor(Color.RED);
                if(birdName.getText().toString().equalsIgnoreCase("")){
                    message.setText("Bird name is not blank");
                    return;
                }else if(birdLocation.getText().toString().equalsIgnoreCase("")){
                    message.setText("Location is not blank");
                    return;
                }else if(birdDate.getText().toString().equalsIgnoreCase("")){
                    message.setText("Date is not blank");
                    return;
                }else if(birdTime.getText().toString().equalsIgnoreCase("")){
                    message.setText("Time is not blank");
                    return;
                }else if(watcherName.getText().toString().equalsIgnoreCase("")){
                    message.setText("Watcher Name is not blank");
                    return;
                }else if(myDb.checkDuplicate(birdName.getText().toString())){
                    message.setText("Bird name is duplicated");
                    return;
                }else {
                    Bird bird = new Bird();
                    bird.setBirdName(birdName.getText().toString());
                    bird.setLocation(birdLocation.getText().toString());
                    bird.setTime(birdTime.getText().toString());
                    bird.setDate(birdDate.getText().toString());
                    bird.setWatcherName(watcherName.getText().toString());
                    myDb.insertBird(bird);

                    Event event = new Event();
                    event.setName("Add " +bird.getBirdName() + " at " + bird.getDate() + " " +bird.getTime());
                    event.setDescription("Add " + bird.getBirdName() + " in " + bird.getLocation() +" successfully !!!");
                    myDb.insertEvent(event);
                    message.setTextColor(Color.BLUE);
                    message.setText("Insert successfully");
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_bird, menu);
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
