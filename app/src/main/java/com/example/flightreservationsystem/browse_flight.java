package com.example.flightreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import model.flight_model;

public class browse_flight extends AppCompatActivity {

    EditText search_takeoff_date;
    Spinner  search_source_spinner;
    Spinner search_destination_spinner;
    Button  btn_search;
    Button  btn_choose_takeoff_date;
    db_helper db;

    RecyclerView search_recyler_view = null;
    RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_flight);

        db = new db_helper(this);
        search_source_spinner =  findViewById(R.id.search_spinnerSource);
        search_destination_spinner =  findViewById(R.id.search_spinnerDestination);
        btn_search = findViewById(R.id.btnflightSearch);
        search_takeoff_date = findViewById(R.id.txtsearch_TakeoffDate);
        btn_choose_takeoff_date = findViewById(R.id.btn_choose_TakeoffDate);

        search_recyler_view = findViewById(R.id.SearchRecylerView);
        search_recyler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        search_recyler_view.setLayoutManager(mLayoutManager);


        setCountrySpinner();
        btn_choose_takeoff_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calender_dialog();
            }
        });


        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                valdidate_and_getdatafrom_db();
            }
        });

    }



    private void valdidate_and_getdatafrom_db(){

        final String flight_takeoff_date = search_takeoff_date.getText().toString().trim();
        final String  flight_source = search_source_spinner.getItemAtPosition(search_source_spinner.getSelectedItemPosition()).toString().trim();
        final String flight_destination = search_destination_spinner.getItemAtPosition(search_destination_spinner.getSelectedItemPosition()).toString().trim();

        if( flight_source.trim().equals("*select country*") || ( flight_destination.trim().equals("*select country*")) ){
            Toast.makeText(browse_flight.this, "please select a valid country", Toast.LENGTH_SHORT).show() ;
            return;
        }
        if (flight_takeoff_date.length() == 0 ) {

            ArrayList<flight_model>  flight_data = db.searchBySourceandDestination(flight_source, flight_destination);

            if (flight_data.isEmpty()) {
                Toast.makeText(browse_flight.this, "Flight data not available", Toast.LENGTH_SHORT).show() ;
                search_recyler_view.getRecycledViewPool().clear();

            } search_recyler_view.setAdapter(new adapter_class(flight_data));

        }
        else{

            ArrayList<flight_model>  flight_data = db.searchByall(flight_source, flight_destination, flight_takeoff_date);

            if (flight_data.isEmpty()) {
                Toast.makeText(browse_flight.this, "Flight data not available", Toast.LENGTH_SHORT).show() ;
                search_recyler_view.getRecycledViewPool().clear();

            } search_recyler_view.setAdapter(new adapter_class(flight_data));



        }
    }

    private void calender_dialog(){

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(browse_flight.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                int s = monthOfYear + 1;
                String DateString = dayOfMonth + "/" + s + "/" + year;
                search_takeoff_date.setText(DateString);


            }
        }, dd, mm, yy);
        datePicker.show();
    }

    private void setCountrySpinner(){

        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        countries.add("*select country*");
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }

        Collections.sort(countries);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, countries);

        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        search_source_spinner.setAdapter(countryAdapter);
        search_destination_spinner.setAdapter(countryAdapter);
    }
}
