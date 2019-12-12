package com.example.flightreservationsystem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import model.flight_model;
import java.util.ArrayList;


public class db_helper extends SQLiteOpenHelper {

    private static String flight_DB = "flightdata.db";
    private static String flight_Table = "flight_data_table";
    private static String BookFlight_Table = "bookflight_table";

    SQLiteDatabase db = null;

    public db_helper(Context context){

        super(context, flight_DB, null , 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("create table "+flight_Table+"(flight_id text primary key, flight_name text, flight_source text, flight_destination text, available_seats text, price text, takeoffdate text, departure_time text, arrival_time text)");
       db.execSQL("create table "+BookFlight_Table+"(customer_id text ,customer_name text, flight_id text, flight_name text, flight_source text, flight_destination text, arrival text, departure text, price text, flight_takeoff_date text, ticket_id text, customer_address text, gender text, class_type text, reserved_seats text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists "+BookFlight_Table+" ");
        db.execSQL("drop table if exists "+flight_Table+" ");

        onCreate(db);
    }

    //Insert into flight_db table
    public boolean insert(String flight_id , String flight_name, String flight_source, String flight_destination, int available_seats , String price, String takeoffdate, String departure_time, String arrival_time) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("flight_id", flight_id);
        contentValues.put("flight_name", flight_name);
        contentValues.put("flight_source", flight_source);
        contentValues.put("flight_destination", flight_destination);
        contentValues.put("available_seats", available_seats);
        contentValues.put("price", price);
        contentValues.put("takeoffdate", takeoffdate);
        contentValues.put("departure_time", departure_time);
        contentValues.put("arrival_time", arrival_time);
        long _insert = db.insert(flight_Table, null, contentValues);
        if (_insert == -1) return false;
        else return true;

    }

    public ArrayList<flight_model> searchBySourceandDestination(String flight_source, String flight_destination) {
            ArrayList<flight_model> flightmodel = null;
        try {
             db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select flight_id,flight_name,arrival_time,departure_time,price,takeoffdate from "+flight_Table+ " where flight_source='" + flight_source + "' and flight_destination='" + flight_destination + "'  ", null);
            cursor.moveToFirst();

                flightmodel = new ArrayList<flight_model>();
            while (cursor.isAfterLast() == false){

                flight_model flight_data = new flight_model();
                flight_data.setFlight_id(cursor.getString(cursor.getColumnIndex("flight_id")));
                flight_data.setFlight_name(cursor.getString(cursor.getColumnIndex("flight_name")));
                flight_data.setArrival(cursor.getString(cursor.getColumnIndex("arrival_time")));
                flight_data.setDeparture(cursor.getString(cursor.getColumnIndex("departure_time")));
                flight_data.setFlight_price(cursor.getString(cursor.getColumnIndex("price")));
                flight_data.setTakeoff_date(cursor.getString(cursor.getColumnIndex("takeoffdate")));
                System.err.println("----------------------"+ flight_data);
                flightmodel.add(flight_data);
                cursor.moveToNext();
                };
            }
        catch (Exception e) {
            flightmodel = null;
            e.printStackTrace();
        }
        return flightmodel;
    }

    public ArrayList<flight_model> searchByall(String flight_source, String flight_destination, String takeoffdate) {
        ArrayList<flight_model> flightmodel = null;
        try {
             db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select flight_id,flight_name,arrival_time,departure_time,price from "+ flight_Table+ " where flight_source='" + flight_source + "' and flight_destination='" + flight_destination + "' and takeoffdate='" + takeoffdate + "' " , null);
            cursor.moveToFirst();

            flightmodel = new ArrayList<flight_model>();
            while (cursor.isAfterLast() == false){
                flight_model flight_data = new flight_model();
                flight_data.setFlight_id(cursor.getString(cursor.getColumnIndex("flight_id")));
                flight_data.setFlight_name(cursor.getString(cursor.getColumnIndex("flight_name")));
                flight_data.setFlight_price(cursor.getString(cursor.getColumnIndex("price")));
                flight_data.setDeparture(cursor.getString(cursor.getColumnIndex("departure_time")));
                flight_data.setArrival(cursor.getString(cursor.getColumnIndex("arrival_time")));
                flight_data.setTakeoff_date("-");

                flightmodel.add(flight_data);
                cursor.moveToNext();
            };

        } catch (Exception e) {
            flightmodel = null;
            e.printStackTrace();
        }
        return flightmodel;

    }

    public ArrayList<String> getallflightId() {

        ArrayList<String> flightmodel = null;
        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select flight_id from "+ flight_Table+ "  " , null);
            cursor.moveToFirst();

            flightmodel = new ArrayList<String>();
            while (cursor.isAfterLast() == false){


                flightmodel.add(cursor.getString(cursor.getColumnIndex("flight_id")));
                System.err.println("----------------------"+ flightmodel);
                cursor.moveToNext();
            };

        } catch (Exception e) {
            flightmodel = null;
            e.printStackTrace();
        }
        return flightmodel;

    }

    public void deleteflight(String flight_id ) {
        db = this.getWritableDatabase();
       String query =  "delete from "+ flight_Table+ " where flight_id='" + flight_id + "' ";
       db.execSQL(query);
       db.close();

    }

    public ArrayList<flight_model> getallflightdata() {
        ArrayList<flight_model> flightmodel ;

        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery("select flight_id,flight_name,flight_source,flight_destination,available_seats,price,takeoffdate,departure_time,arrival_time from "+ flight_Table , null);
            cursor.moveToFirst();

            flightmodel = new ArrayList<>();
            while (cursor.isAfterLast() == false){

                flight_model flight_data = new flight_model();
                flight_data.setFlight_id(cursor.getString(cursor.getColumnIndex("flight_id")));
                flight_data.setFlight_name(cursor.getString(cursor.getColumnIndex("flight_name")));
                flight_data.setArrival(cursor.getString(cursor.getColumnIndex("arrival_time")));
                flight_data.setDeparture(cursor.getString(cursor.getColumnIndex("departure_time")));
                flight_data.setFlight_price(cursor.getString(cursor.getColumnIndex("price")));
                flight_data.setTakeoff_date(cursor.getString(cursor.getColumnIndex("takeoffdate")));
                flight_data.setAvailable_seat(cursor.getString(cursor.getColumnIndex("available_seats")));
                flight_data.setFlight_source(cursor.getString(cursor.getColumnIndex("flight_source")));
                flight_data.setFlight_destination(cursor.getString(cursor.getColumnIndex("flight_destination")));
                flightmodel.add(flight_data);
                cursor.moveToNext();
            };
        }
        catch (Exception e) {
            flightmodel = null;
            e.printStackTrace();
        }
        return flightmodel;
    }

    public boolean insertinto_bookflight_table(String customer_id,String customer_name,String flight_id,String flight_name,String flight_source,String flight_destination,String arrival,String departure,String price,String flight_takeoff_date,String ticket_id,String customer_address,String gender,String class_type,String reserved_seats) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("customer_id", customer_id);
        contentValues.put("customer_name", customer_name);
        contentValues.put("flight_id", flight_id);
        contentValues.put("flight_name", flight_name);
        contentValues.put("flight_source", flight_source);
        contentValues.put("flight_destination", flight_destination);
        contentValues.put("arrival", arrival);
        contentValues.put("departure", departure);
        contentValues.put("price", price);
        contentValues.put("flight_takeoff_date", flight_takeoff_date);
        contentValues.put("ticket_id", ticket_id);
        contentValues.put("customer_address", customer_address);
        contentValues.put("gender", gender);
        contentValues.put("class_type", class_type);
        contentValues.put("reserved_seats", reserved_seats);
        long insert = db.insert(BookFlight_Table, null, contentValues);
        if (insert == -1) return false;
        else return true;

    }

}












