package com.cse5324.projecthotel;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestReservations extends AppCompatActivity {
    hotelDatabase hdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_reservations);

        getSupportActionBar().setTitle("Request Hotel Reservations");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //go back button
        //What's the user id?
        Intent getI = getIntent();
        final String info = getI.getStringExtra("user_id");
        Toast.makeText(RequestReservations.this, "user id: "+info, Toast.LENGTH_SHORT).show();

        //Logout
        final Button button= findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Goes to Login page
                startActivity(new Intent(RequestReservations.this, MainAppScreenActivity.class));
            }
        });
        //OpenMenu
        final Button btnMenu= findViewById(R.id.filterMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(RequestReservations.this, RequestReservationFilter.class);
                intent.putExtra("user_id", info);
                startActivity(intent);
            }
        });

        //Load Page contents
        View linearLayout = findViewById(R.id.page);
        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);

        //Structure
        TableLayout table = new TableLayout(this);
        table.setBackgroundColor(Color.parseColor("#000000"));
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        table.setLayoutParams(param);
        param.setMargins(5,5,5,5);

        hdb = new hotelDatabase(this);
        final Cursor cursor = hdb.getRoom();
        int count = cursor.getCount();
        if(cursor.getCount()==0)
        {
            Log.i("msg: ", "no data?");
            TableRow trMain = new TableRow(this);
            TextView none = new TextView(this);
            none.setText("No reservations are currently available");
            none.setTextSize(20);
            trMain.addView(none);
            table.addView(trMain);
        }
        else
        {
            Log.i("msg: ", "there is data!");
            int i = 0; //row count
            while(cursor.moveToNext() && i<count)
            {
                int hid = cursor.getInt(1); //hotel id
                String rt = cursor.getString(3).toLowerCase(); // room type
                TableRow trMain = new TableRow(this);
                trMain.setId(R.id.reqRow+i);
                trMain.setBackgroundColor(Color.parseColor("#ffffff"));
                TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.MATCH_PARENT
                );
                params.setMargins(5,3,5,3);
                trMain.setLayoutParams(params);
                trMain.setWeightSum(1f);

                //picture load
                int id = getResources().getIdentifier(rt+hid, "drawable", getPackageName());
                Drawable draw = getResources().getDrawable(id);
                //ImageView pic = findViewById(R.id.roomPicture);
                ImageView pic = new ImageView(this);
                pic.setBackground(draw);

                final float scale = getResources().getDisplayMetrics().density;
                int pixels = (int) (170 * scale + 0.5f);

                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(0,
                        pixels);
                layoutParams.weight = 0.35f;
                pic.setLayoutParams(layoutParams);

                TableLayout miniTable = new TableLayout(this);
                TableRow.LayoutParams p = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.MATCH_PARENT
                );
                p.weight = 0.65f;
                p.setMargins(10,5,5,0);
                miniTable.setLayoutParams(p);

                ///Room no.
                TableRow tr0 = new TableRow(this);
                TextView th0 = new TextView(this);
                th0.setTypeface(null, Typeface.BOLD);
                th0.setText("Room No.: ");
                th0.setTextSize(12);
                TextView td0 = new TextView(this);
                td0.setText(cursor.getString(2));        //get hotel name from db
                td0.setTextSize(12);
                tr0.addView(th0);
                tr0.addView(td0);
                miniTable.addView(tr0);

                //Hotel Name
                TableRow tr1 = new TableRow(this);
                TextView th1 = new TextView(this);
                th1.setTypeface(null, Typeface.BOLD);
                th1.setText("Hotel Name: ");
                th1.setTextSize(12);
                TextView td1 = new TextView(this);
                td1.setText(Integer.toString(hid));        //get hotel name from db
                td1.setTextSize(12);
                tr1.addView(th1);
                tr1.addView(td1);
                miniTable.addView(tr1);

                //No. of rooms
                TableRow tr2 = new TableRow(this);
                TextView th2 = new TextView(this);
                th2.setTypeface(null, Typeface.BOLD);
                th2.setText("Number of Rooms: ");
                th2.setTextSize(12);
                TextView td2 = new TextView(this);
                td2.setText("1 room");        //get room type from db
                td2.setTextSize(12);
                tr2.addView(th2);
                tr2.addView(td2);
                miniTable.addView(tr2);

                //Room Type
                TableRow tr3 = new TableRow(this);
                TextView th3 = new TextView(this);
                th3.setTypeface(null, Typeface.BOLD);
                th3.setText("Room Type: ");
                th3.setTextSize(12);
                TextView td3 = new TextView(this);
                td3.setText(rt);        //get bed from db
                td3.setTextSize(12);
                tr3.addView(th3);
                tr3.addView(td3);
                miniTable.addView(tr3);

                //Arrival Date
                TableRow tr4 = new TableRow(this);
                TextView th4 = new TextView(this);
                th4.setTypeface(null, Typeface.BOLD);
                th4.setText("Arrival Date: ");
                th4.setTextSize(12);
                TextView td4 = new TextView(this);
                /*Date date = new Date(cursor.getLong(5)); */
                Date date=new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                td4.setText(sdf.format(date));        //get date from db
                td4.setTextSize(12);
                tr4.addView(th4);
                tr4.addView(td4);
                miniTable.addView(tr4);

                //number of days stay available
                TableRow tr5 = new TableRow(this);
                TextView th5 = new TextView(this);
                th5.setTypeface(null, Typeface.BOLD);
                th5.setText("Number of Nights: ");
                th5.setTextSize(12);
                TextView td5 = new TextView(this);
                td5.setText("30");        //get time from db
                td5.setTextSize(12);
                tr5.addView(th5);
                tr5.addView(td5);
                miniTable.addView(tr5);

                //Room price
                TableRow tr6 = new TableRow(this);
                TextView th6 = new TextView(this);
                th6.setTypeface(null, Typeface.BOLD);
                th6.setText("Total Price: ");
                th6.setTextSize(12);
                TextView td6 = new TextView(this);
                td6.setText("100 dollars");        //get time from db
                td6.setTextSize(12);
                tr6.addView(th6);
                tr6.addView(td6);
                miniTable.addView(tr6);

                table.addView(trMain);
                trMain.addView(pic);
                trMain.addView(miniTable);
                i++;

                ////////////////
                final int cs = cursor.getInt(0);
                trMain.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        final Intent intent = new Intent(RequestReservations.this, FetchContent.class);
                        intent.putExtra("row_num", Integer.toString(cs));
                        intent.putExtra("user_id", info);
                        //intent.putExtra("room_id", cursor.getString(0));
                        intent.putExtra("from", "request");
                        startActivity(intent);
                    }
                });
            }
        }
        ll.addView(table);
        sv.addView(ll);
        ((LinearLayout) linearLayout).addView(sv);
    }

    //go back button to work
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}