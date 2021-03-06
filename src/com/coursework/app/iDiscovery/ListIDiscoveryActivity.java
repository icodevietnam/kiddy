package com.coursework.app.iDiscovery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.coursework.app.R;

import com.coursework.com.coursework.domain.Event;
import com.coursework.com.coursework.domain.iDiscovery;
import com.coursework.helper.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ListIDiscoveryActivity extends Activity {
    ListView listView;
    DBHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_idiscovery);
        myDb = new DBHelper(this);
        // Get ListView
        listView = (ListView)this.findViewById(R.id.listBird);
        List<iDiscovery> listDiscoveries = myDb.getAlliDiscoveries();
        final ArrayList<String> values = new ArrayList<String>();
        final EditText inputSearch = (EditText)this.findViewById(R.id.inputSearch);
        Button btnSearch = (Button)this.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<iDiscovery> searchList = myDb.searchByName(inputSearch.getText().toString());
                values.removeAll(values);
                for (iDiscovery iDiscovery : searchList) {
                    String str = iDiscovery.getId() + "-Name:" + iDiscovery.getActivityName() + "-Location:" + iDiscovery.getLocation() + "-Date:" + iDiscovery.getDate() + " " + iDiscovery.getTime();
                    values.add(str);
                }
                setAdapter(getApplicationContext(),values,listView);
                Event event = new Event();
                event.setName("Search " + inputSearch.getText().toString());
                event.setDescription("Search " + inputSearch.getText().toString() + " successfully. Search Found: " + values.size() );
                myDb.insertEvent(event);
            }
        });
        for(iDiscovery iDiscovery :listDiscoveries){
            String str = iDiscovery.getId() + "-Name:" + iDiscovery.getActivityName() + "-Location:" + iDiscovery.getLocation() +"-Date:" + iDiscovery.getDate()+" " + iDiscovery.getTime();
            values.add(str);
        }

        setAdapter(this,values,listView);
        }

    private void setAdapter(Context context,ArrayList<String> values, final ListView listView){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1, android.R.id.text1,values);
        listView.setAdapter(adapter);
        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // ListView Clicked item index
                int itemPosition = position;
                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
                // Show Alert
                String[] arrayStr = itemValue.split("-");
                Intent intent = new Intent(ListIDiscoveryActivity.this, ViewActivity.class);
                intent.putExtra("iDiscoveryId", arrayStr[0]);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_idiscovery, menu);
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
