package com.example.flightreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class reserved_flight extends AppCompatActivity{


    private Context context= this;
    private db_helper database = null;
    private static String BookFlight_Table = "bookflight_table";
    TableLayout tableLayout = null;
    SQLiteDatabase db = null;
    TableRow row = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_flight);

        database = new db_helper(this);

        tableActions();

    }

    public void tableActions(){


        // Reference to TableLayout
        tableLayout= findViewById(R.id.table_main);
        // Add header row
        TableRow rowHeader = new TableRow(context);
        rowHeader.setBackgroundColor(Color.parseColor("#FAF0E6"));
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        String[] headerText={"CUSTOMERID","CUSTOMERNAME","FLIGHT ID","NAME", "SOURCE", "DESTINATION", "ARRIVAL", "DEPARTURE", "PRICE", "TAKE OFF DATE", "TICKET ID", "ADDRESS", "GENDER", "CLASS", "SEAT"};
        for(String c:headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER_HORIZONTAL);
            tv.setTextSize(14);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        // Get data from sqlite database and add them to the table
        // Open the database for reading
         db = database.getReadableDatabase();
        // Start the transaction.
        db.beginTransaction();

        try
        {
            String selectQuery = "SELECT * FROM "+ BookFlight_Table;
            Cursor cursor = db.rawQuery(selectQuery,null);

            if(cursor.getCount()< 0)   Toast.makeText(reserved_flight.this, "No data to display", Toast.LENGTH_SHORT).show();

            if(cursor.getCount() >0)
            {
                while (cursor.moveToNext()) {
                    // Read columns data
                    String customer_id= cursor.getString(cursor.getColumnIndex("customer_id"));
                    String customer_name= cursor.getString(cursor.getColumnIndex("customer_name"));
                    String flight_id= cursor.getString(cursor.getColumnIndex("flight_id"));
                    String flight_name= cursor.getString(cursor.getColumnIndex("flight_name"));
                    String flight_source= cursor.getString(cursor.getColumnIndex("flight_source"));
                    String flight_destination= cursor.getString(cursor.getColumnIndex("flight_destination"));
                    String arrival= cursor.getString(cursor.getColumnIndex("arrival"));
                    String departure= cursor.getString(cursor.getColumnIndex("departure"));
                    String price= cursor.getString(cursor.getColumnIndex("price"));
                    String flight_takeoff_date= cursor.getString(cursor.getColumnIndex("flight_takeoff_date"));
                    String ticket_id= cursor.getString(cursor.getColumnIndex("ticket_id"));
                    String customer_address= cursor.getString(cursor.getColumnIndex("customer_address"));
                    String gender= cursor.getString(cursor.getColumnIndex("gender"));
                    String class_type= cursor.getString(cursor.getColumnIndex("class_type"));
                    String reserved_seats= cursor.getString(cursor.getColumnIndex("reserved_seats"));


                    // dara rows
                    row = new TableRow(context);
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText={ customer_id , customer_name , flight_id , flight_name , flight_source , flight_destination , arrival , departure , price , flight_takeoff_date , ticket_id , customer_address , gender , class_type , reserved_seats};
                    for(String text:colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER_HORIZONTAL);
                        tv.setTextSize(18);
                        tv.setTextColor(Color.parseColor("#000000"));
                        tv.setPadding(7, 7, 7, 7);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);

                }

            }
            db.setTransactionSuccessful();

        }
        catch (SQLiteException e){
            e.printStackTrace();
        }
        finally{
            db.endTransaction();
            // End the transaction.
            db.close();
            // Close database
        }

    }



}

















