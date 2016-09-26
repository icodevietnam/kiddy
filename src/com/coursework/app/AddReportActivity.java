package com.coursework.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.coursework.com.coursework.domain.Event;
import com.coursework.com.coursework.domain.Report;
import com.coursework.helper.DBHelper;

public class AddReportActivity extends Activity {
    private DBHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        myDb = new DBHelper(this);
        final Intent intent = getIntent();
        final String birdIdString = intent.getStringExtra("birdId");
        final EditText inputReportName = (EditText)this.findViewById(R.id.inputReportName);
        final EditText inputReportDescription = (EditText)this.findViewById(R.id.inputReportDescription);
        Button btnAddReportItems = (Button)this.findViewById(R.id.btnAddReportItem);
        btnAddReportItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Report report = new Report();
                report.setName(inputReportName.getText().toString());
                report.setDescription(inputReportDescription.getText().toString());
                report.setBirdId(birdIdString);
                myDb.insertReport(report);
                Intent intent1 = new Intent(AddReportActivity.this,ViewActivity.class);
                intent1.putExtra("birdId",birdIdString);
                startActivity(intent1);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_report, menu);
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
