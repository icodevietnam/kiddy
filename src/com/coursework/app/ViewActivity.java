package com.coursework.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coursework.com.coursework.domain.Event;
import com.coursework.com.coursework.domain.Kiddy;
import com.coursework.helper.DBHelper;

public class ViewActivity extends Activity {
    DBHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        Intent intent = getIntent();
        myDb = new DBHelper(this);
        Button btnDelete = (Button)this.findViewById(R.id.btnDeleteKiddy);
        TextView txtViewId = (TextView)this.findViewById(R.id.txtViewId);
        TextView txtViewKiddyActivityName = (TextView)this.findViewById(R.id.txtViewKiddyName);
        TextView txtViewLocation = (TextView)this.findViewById(R.id.txtViewKiddyLocation);
        TextView txtViewDateTime = (TextView)this.findViewById(R.id.txtViewKiddyDateTime);
        TextView txtViewWatcher = (TextView)this.findViewById(R.id.txtViewKiddyReporterName);
        String birdIdString = intent.getStringExtra("birdId");
        final Integer id = Integer.parseInt(birdIdString);
        Kiddy kiddy = myDb.getKiddyData(id);
        txtViewId.setText(id.toString());
        txtViewKiddyActivityName.setText("Activity Name:" + kiddy.getActivityName());
        txtViewLocation.setText("Location:" +kiddy.getLocation() );
        txtViewDateTime.setText("Date time:" + kiddy.getDate() + " " +kiddy.getTime());
        txtViewWatcher.setText("Watcher Name:" + kiddy.getReporterName());
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = new Event();
                event.setName("Delete bird id:" + id);
                event.setDescription("Delete bird with id " + id + " successfully !!!");
                myDb.insertEvent(event);
                myDb.deleteKiddy(id);
                Intent intent = new Intent(ViewActivity.this, ListKiddyActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Button btnAddReport =(Button)this.findViewById(R.id.btnAddReport);
        btnAddReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewActivity.this, AddReportActivity.class);
                intent.putExtra("kiddyId",id + "");
                startActivity(intent);
                finish();
            }
        });
        Button btnViewReport = (Button)this.findViewById(R.id.btnViewReport);
        btnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewActivity.this,ViewReportActivity.class);
                intent.putExtra("kiddyId",id + "");
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
