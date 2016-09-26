package com.coursework.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coursework.com.coursework.domain.Bird;
import com.coursework.com.coursework.domain.Event;
import com.coursework.helper.DBHelper;

public class ViewActivity extends Activity {
    DBHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent intent = getIntent();
        myDb = new DBHelper(this);
        Button btnDelete = (Button)this.findViewById(R.id.btnDeleteBird);
        TextView txtViewId = (TextView)this.findViewById(R.id.txtViewId);
        TextView txtViewBirdName = (TextView)this.findViewById(R.id.txtViewBirdName);
        TextView txtViewLocation = (TextView)this.findViewById(R.id.txtViewBirdLocation);
        TextView txtViewDateTime = (TextView)this.findViewById(R.id.txtViewBirdDateTime);
        TextView txtViewWatcher = (TextView)this.findViewById(R.id.txtViewBirdWatcherName);
        String birdIdString = intent.getStringExtra("birdId");
        final Integer id = Integer.parseInt(birdIdString);
        Bird bird = myDb.getBirdData(id);
        txtViewId.setText(id.toString());
        txtViewBirdName.setText("Name:" + bird.getBirdName());
        txtViewLocation.setText("Location:" +bird.getLocation() );
        txtViewDateTime.setText("Date time:" + bird.getDate() + " " +bird.getTime());
        txtViewWatcher.setText("Watcher Name:" + bird.getWatcherName());
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = new Event();
                event.setName("Delete bird id:" + id);
                event.setDescription("Delete bird with id " + id + " successfully !!!");
                myDb.insertEvent(event);
                myDb.deleteBird(id);
                Intent intent = new Intent(ViewActivity.this, ListBirdActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button btnAddReport =(Button)this.findViewById(R.id.btnAddReport);
        btnAddReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewActivity.this, AddReportActivity.class);
                intent.putExtra("birdId",id + "");
                startActivity(intent);
                finish();
            }
        });
        Button btnViewReport = (Button)this.findViewById(R.id.btnViewReport);
        btnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewActivity.this,ViewReportActivity.class);
                intent.putExtra("birdId",id + "");
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
