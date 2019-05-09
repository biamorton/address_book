package com.mortonsfarm.contactlist;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayContact extends Activity {
    int fromWhereIAmComing = 0;
    private DBHelper mydb;

    TextView name;
    TextView phone;
    TextView email;
    TextView street;
    TextView city;
    int idToUpdate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);

        name = (TextView) findViewById(R.id.editTextName);
        phone = (TextView) findViewById(R.id.editTextPhone);
        email = (TextView) findViewById(R.id.editTextEmail);
        street = (TextView) findViewById(R.id.editTextStreet);
        city = (TextView) findViewById(R.id.editTextCity);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int Value = extras.getInt("id");

            if(Value > 0){
                //view part not add contact
                Cursor rs = mydb.getData(Value);
                idToUpdate = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
                String stree = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_STREET));
                String cit = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_CITY));

                if(!rs.isClosed()){
                    rs.close();
                }

                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.INVISIBLE);

                name.setText((CharSequence)nam);
                name.setFocusable(false);
                name.setClickable(false);

                phone.setText((CharSequence)phon);
                phone.setFocusable(false);
                phone.setClickable(false);

                email.setText((CharSequence)emai);
                email.setFocusable(false);
                email.setClickable(false);

                street.setText((CharSequence)stree);
                street.setFocusable(false);
                street.setClickable(false);

                city.setText((CharSequence)cit);
                city.setFocusable(false);
                city.setClickable(false);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            int Value = extras.getInt("id");
            if(Value > 0){
                getMenuInflater().inflate(R.menu.display_contact, menu);
            } else {
                getMenuInflater().inflate(R.menu.menu_main, menu);
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        switch (item.getItemId()){
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);

                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                phone.setEnabled(true);
                phone.setFocusableInTouchMode(true);
                phone.setClickable(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                street.setEnabled(true);
                street.setFocusableInTouchMode(true);
                street.setClickable(true);

                city.setEnabled(true);
                city.setFocusableInTouchMode(true);
                city.setClickable(true);

                return true;
            case R.id.Delete_Contact:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mydb.deleteContact(idToUpdate);
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //cancel dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Are you sure?");
                d.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void run(View view){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            int Value = extras.getInt("id");
            if(Value > 0){
                if(mydb.updateContact(idToUpdate, name.getText().toString(), phone.getText().toString(), email.getText().toString(), street.getText().toString(), city.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(mydb.insertContact(name.getText().toString(), phone.getText().toString(), email.getText().toString(), street.getText().toString(), city.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Not Done", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
