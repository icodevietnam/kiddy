package com.coursework.app.iDiscovery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.coursework.app.R;

import com.coursework.com.coursework.domain.Report;
import com.coursework.helper.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ViewReportActivity extends Activity {

    ListView listView;
    DBHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        final Intent intent = getIntent();
        final String birdIdString = intent.getStringExtra("iDiscoveryId");
        myDb = new DBHelper(this);
        listView = (ListView)this.findViewById(R.id.listReport);
        List<Report> listReports = myDb.getAllReports(birdIdString);
        ArrayList<String> values = new ArrayList<String>();
        for (Report r : listReports){
            String str = r.getId() + " - Name:" + r.getName() + " - Description:" + r.getDescription();
            values.add(str);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_report, menu);
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
