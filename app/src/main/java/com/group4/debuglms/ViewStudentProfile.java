package com.group4.debuglms;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class ViewStudentProfile extends AppCompatActivity {
    String strId, strName, name, username, password, email, contact, address, accID, section;
    TextView tvStudentDetails,tvStudentDetails2,tvStudentDetails3,tvStudentDetails4,tvStudentDetails5,tvStudentDetails6,tvStudentDetails7,tvStudentDetails8;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_student_profile);

            strId = getIntent().getExtras().getString("id");
            strName = getIntent().getExtras().getString("name");

            tvStudentDetails = (TextView) findViewById(R.id.tvStudentDetails);
            tvStudentDetails2 = (TextView) findViewById(R.id.tvStudentDetails2);
            tvStudentDetails3 = (TextView) findViewById(R.id.tvStudentDetails3);
            tvStudentDetails4 = (TextView) findViewById(R.id.tvStudentDetails4);
            tvStudentDetails5 = (TextView) findViewById(R.id.tvStudentDetails5);
            tvStudentDetails6 = (TextView) findViewById(R.id.tvStudentDetails6);
            tvStudentDetails7 = (TextView) findViewById(R.id.tvStudentDetails7);
            tvStudentDetails8 = (TextView) findViewById(R.id.tvStudentDetails8);


            db = openOrCreateDatabase("app", MODE_PRIVATE, null);

            Cursor cc = db.rawQuery("select * from account where accounttype = 'Student'", null);
            cc.moveToFirst();

            if (cc.getCount() > 0) {
                do {
                    name = cc.getString(cc.getColumnIndex("name"));
                    username = cc.getString(cc.getColumnIndex("username"));
                    password = cc.getString(cc.getColumnIndex("password"));
                    email = cc.getString(cc.getColumnIndex("email"));
                    contact = cc.getString(cc.getColumnIndex("contact"));
                    address = cc.getString(cc.getColumnIndex("address"));
                    accID = cc.getString(cc.getColumnIndex("id"));
                    section = cc.getString(cc.getColumnIndex("section"));
                    tvStudentDetails.setText(accID);
                    tvStudentDetails2.setText(section );
                    tvStudentDetails3.setText(name );
                    tvStudentDetails4.setText(username);
                    tvStudentDetails5.setText(password);
                    tvStudentDetails6.setText(email);
                    tvStudentDetails7.setText(contact);
                    tvStudentDetails8.setText(address);



                } while (cc.moveToNext());


            } else {

                Toast.makeText(getApplicationContext(), "No Student Available!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

        Intent i = new Intent(getApplicationContext(), Student.class);
        i.putExtra("id", strId);
        i.putExtra("name", strName);
        startActivity(i);
    }

    }

