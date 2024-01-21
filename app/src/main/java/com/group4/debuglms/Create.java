package com.group4.debuglms;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Create extends AppCompatActivity {

    EditText etName, etUsername, etPassword, etEmail, etContact, etAddress;
    TextView tvValid;
    SQLiteDatabase db;
    Button btnGenerate;
    TextView tvv;
    Spinner spCreateSection;
    Intent i;
    String strname, strusername, strpassword, strEmail, strContact, strAddress, strvalid, type, section;
    String strId,strName;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        try {
            etName = (EditText) findViewById(R.id.etName);
            etUsername = (EditText) findViewById(R.id.etcUsername);
            etPassword = (EditText) findViewById(R.id.etcPassword);
            etEmail = (EditText) findViewById(R.id.etEmail);
            etContact = (EditText) findViewById(R.id.etContact);
            etAddress = (EditText) findViewById(R.id.etAddress);
            tvValid = (TextView) findViewById(R.id.tvValid);
            tvv = (TextView) findViewById(R.id.tvv);
            btnGenerate = (Button) findViewById(R.id.btnGenerate);
            spCreateSection = (Spinner) findViewById(R.id.spCreateSection);

            strId = getIntent().getExtras().getString("id");
            strName = getIntent().getExtras().getString("name");
            type = getIntent().getExtras().getString("type");

            db = openOrCreateDatabase("app", MODE_PRIVATE, null);
            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");


            Calendar c1 = Calendar.getInstance();
            String currentDate = df.format(date);

            tvv.setText("Create a " + type);

            if (type.equalsIgnoreCase("teacher")){
                spCreateSection.setVisibility(View.INVISIBLE);
            }else {
                spCreateSection.setVisibility(View.VISIBLE);
                ArrayList<String> spinnerSection = new ArrayList<String>();
                spinnerSection.add("BS Information Technology");
                spinnerSection.add("BS Computer Science");
                spinnerSection.add("BS Computer Engineering");
                spinnerSection.add("BS Information System");

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(Create.this, android.R.layout.simple_spinner_dropdown_item, spinnerSection);
                spCreateSection.setAdapter(adapter);
            }


            if (type.equalsIgnoreCase("Student")) {
                btnGenerate.setVisibility(View.VISIBLE);
                c1.add(Calendar.DAY_OF_YEAR, 30);
                df = new SimpleDateFormat("MM-dd-yyyy");
                Date resultDate = c1.getTime();
                String dueDate = df.format(resultDate);
//                tvValid.setText(dueDate);
            } else {
                tvValid.setVisibility(View.GONE);
            }
        }catch(Exception e){
            Toast.makeText(this, ""+e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void create(View v){
        switch (v.getId()){
            case R.id.btnCancel:

                finish();
                Intent i = new Intent(getApplicationContext(), Admin.class);
                i.putExtra("type", type);
                i.putExtra("id",strId);
                i.putExtra("name",strName);
                startActivity(i);
                break;
            case R.id.btnSave:
                try{
                    strname = etName.getText().toString().trim();
                    strusername = etUsername.getText().toString().trim();
                    strpassword = etPassword.getText().toString().trim();
                    strvalid = tvValid.getText().toString().trim();
                    strEmail = etEmail.getText().toString().trim();
                    strContact = etContact.getText().toString().trim();
                    strAddress = etAddress.getText().toString().trim();
                    if (type.equalsIgnoreCase("teacher")){
                        section = "";
                    }else {
                        section = spCreateSection.getSelectedItem().toString();
                    }


                    if(strName.isEmpty()){
                        etName.setError("Fill in Empty Fields!");
                    }else if(strusername.isEmpty()){
                        etName.setError(null);
                        etUsername.setError("Fill in Empty Fields!");
                    }else if(strpassword.isEmpty()){
                        etName.setError(null);
                        etUsername.setError(null);
                        etPassword.setError("Fill in Empty Fields!");
                    }else if(strEmail.isEmpty()){
                        etName.setError(null);
                        etUsername.setError(null);
                        etPassword.setError("Fill in Empty Fields!");
                    }else if(strContact.isEmpty()){
                        etName.setError(null);
                        etUsername.setError(null);
                        etPassword.setError("Fill in Empty Fields!");
                    }else if(strAddress.isEmpty()){
                        etName.setError(null);
                        etUsername.setError(null);
                        etPassword.setError("Fill in Empty Fields!");
                    }else{
                        if(type.equalsIgnoreCase("Teacher")){
                            db.execSQL("insert into account(accounttype, section, name, username, password, email, contact, address)values('Teacher', '"+section+"', '"+strname+"', '"+strusername+"', '"+strpassword+"', '"+strEmail+"', '"+strContact+"', '"+strAddress+"')");

                            Toast.makeText(this, "New account for teacher has been saved!", Toast.LENGTH_SHORT).show();
                            finish();
                            i = new Intent(getApplicationContext(), List.class);
                            i.putExtra("type", type);
                            i.putExtra("id",strId);
                            i.putExtra("name",strName);
                            startActivity(i);
                        }else if(type.equalsIgnoreCase("Student")){
                            db.execSQL("insert into account(accounttype, section, name, username, password, email, contact, address)values('Student', '"+section+"' , '"+strname+"', '"+strusername+"', '"+strpassword+"', '"+strEmail+"', '"+strContact+"', '"+strAddress+"')");


                            Toast.makeText(this, "New account for student has been saved!", Toast.LENGTH_SHORT).show();
                            finish();
                            i = new Intent(getApplicationContext(), List.class);
                            i.putExtra("type", type);
                            i.putExtra("id",strId);
                            i.putExtra("name",strName);
                            startActivity(i);
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnGenerate:
                try{
                    strname = etName.getText().toString().trim();
                    strusername = etUsername.getText().toString().trim();
                    strpassword = etPassword.getText().toString().trim();
                    String captilizedString="", cap = "", cap2="";

                    if(!strname.trim().equals("")) {
                        captilizedString = strname.substring(0, 1);
                        cap = captilizedString.toUpperCase();
                        String bar;
                        String surname = "";
                        String[] name = strname.split("\\s+");//split name with space

                        if(name.length == 1){
                            etName.setError("Fill with FULL NAME!");
                        }else{
                            for (int j = 0; j < name.length;j++) {
                                if (j != 0) {
                                    bar = name[j].toString().trim();
                                    surname = surname + bar;
                                }
                            }
                            cap2 = surname.substring(0, 1).toUpperCase() + surname.substring(1);

                            Cursor f = db.rawQuery("select * from account where accounttype = 'Student'", null);
                            f.moveToLast();
                            id = f.getCount();
                            id++;
                            if (id < 10) {
                                etUsername.setText(cap + cap2 + "0" + id);
                                etPassword.setText(cap2 + cap + "0" + id);
                            }else{
                                etUsername.setText(cap + cap2 + id);
                                etPassword.setText(cap2 + cap + id);
                            }
                        }

                    }else{
                        etName.setError("Fill in The Blanks!");
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        i = new Intent(getApplicationContext(), List.class);
        i.putExtra("type", type);
        i.putExtra("id",strId);
        i.putExtra("name",strName);
        startActivity(i);
    }
}
