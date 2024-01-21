package com.group4.debuglms;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentCT extends AppCompatActivity {
    TextView tvCT;
    ListView lvCT;
    SQLiteDatabase db;
    ArrayList<String> studentCT;
    ArrayAdapter<String> adapter1;
    Cursor c,d;
    String strId,strName,ct, type, lessons;
    String strSubject,strRoom,strDays,strTime,strTeacher,a_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_ct);
        try{
            tvCT = (TextView)findViewById(R.id.tvCT);
            lvCT =(ListView)findViewById(R.id.lvCT);


            db = openOrCreateDatabase("app", MODE_PRIVATE, null);

//            type = getIntent().getExtras().getString("type");
            strId = getIntent().getExtras().getString("id");
            strName = getIntent().getExtras().getString("name");
            ct = getIntent().getExtras().getString("ct");
            Toast.makeText(getApplicationContext(), ct, Toast.LENGTH_SHORT).show();
            tvCT.setText(ct);
            tvCT.setAllCaps(true);


            c = db.rawQuery("select subject.a_id, subject.s_name, subject.room, subject.days, subject.time, subject.lessons " +
                    "from subject " +
                    "INNER JOIN class " +
                    "on subject.s_name = class.s_name " +
                    "where class.a_id = '"+strId+"'",null);
            c.moveToFirst();
            studentCT = new ArrayList<>();


            if(c.getCount() >0){
                do{
                    a_id = c.getString(c.getColumnIndex("subject.a_id"));
                    strSubject = c.getString(c.getColumnIndex("subject.s_name"));
                    strRoom = c.getString(c.getColumnIndex("subject.room"));
                    strDays = c.getString(c.getColumnIndex("subject.days"));
                    strTime = c.getString(c.getColumnIndex("subject.time"));
                    lessons = c.getString(c.getColumnIndex("subject.lessons"));


                    if(ct.equalsIgnoreCase("Teachers")){
                        d = db.rawQuery("select * from account where id = '"+a_id+"'",null);
                        d.moveToFirst();


                        strTeacher = d.getString(d.getColumnIndex("name"));
                        studentCT.add(strSubject+"\t\t\t\t\t-\t\t\t\t\t"+strTeacher);
                    }else if(ct.equalsIgnoreCase("Classes")){
                        studentCT.add(strSubject+", "+strRoom+", "+strDays+", "+strTime+"\n\nCopy the link to access the Lessons Page:\n"+lessons);
                    }
                }while(c.moveToNext());
            }
            adapter1 = new ArrayAdapter<String>(StudentCT.this, android.R.layout.simple_list_item_1, studentCT);
            lvCT.setAdapter(adapter1);

           /* if (ct.equalsIgnoreCase("classes")){
                lvCT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        try {

                            AlertDialog.Builder ab = new AlertDialog.Builder(com.group4.debuglms.StudentCT.this);
                            ab.setCancelable(true);
                            LayoutInflater li = getLayoutInflater();
                            View cv = li.inflate(R.layout.lessons_page, null);
                            ab.setView(cv);
                            TextView tvLessons = (TextView)cv.findViewById(R.id.tvLessons);

                            Cursor cc = db.rawQuery("select subject.a_id, subject.s_name, subject.room, subject.days, subject.time, subject.lessons " +
                                    "from subject " +
                                    "INNER JOIN class " +
                                    "on subject.s_name = class.s_name " +
                                    "where class.a_id = '"+strId+"'",null);
                            cc.moveToFirst();

                            if(cc.getCount() >0) {
                                do {
                                    String lessons = cc.getString(cc.getColumnIndex("subject.lessons"));

                                    tvLessons.setText("Copy the link below and paste it to your browser to access our lessons page:\n\n" + lessons);

                                    final AlertDialog ac = ab.create();
                                    ac.show();
                                }while (cc.moveToNext());
                            }

                        }catch (Exception ee){
                            Toast.makeText(getApplicationContext(), ""+ee.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }*/

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent i = new Intent(getApplicationContext(), Student.class);
        i.putExtra("id",strId);
        i.putExtra("name",strName);
        startActivity(i);
    }
}

