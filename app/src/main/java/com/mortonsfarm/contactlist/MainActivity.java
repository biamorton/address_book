package com.mortonsfarm.contactlist;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "MMESSAGE";
    private ListView obj;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DBHelper(this);
        ArrayList arrayList = mydb.getAllContacts();
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        obj = (ListView)findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int idToSearch = i + 1;

                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", idToSearch);

                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.item1:
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);

                Intent intent = new Intent(getApplicationContext(), DisplayContact.class);
                intent.putExtras(dataBundle);

                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onKeyDown(int keycode, KeyEvent event){
        if(keycode == KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
}