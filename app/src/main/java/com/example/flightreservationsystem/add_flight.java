package com.example.flightreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class add_flight extends AppCompatActivity {
    EditText id;
    EditText name;
    EditText price;
    EditText seats;
    EditText takeoff_date;
    Spinner source_spinner;
    Spinner destination_spinner;
    EditText departure;
    EditText arrival;

    Button choose_takeoff_date;
    Button save_flight;
    String date;
    db_helper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        database = new db_helper(this);

        initializeViews();
        setCountrySpinner();
        NumberPicker np = findViewById(R.id.numberPicker);
        np.setMinValue(1);
        np.setMaxValue(20);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int no_of_available_seats = numberPicker.getValue();
                seats.setText(String.valueOf(no_of_available_seats));
            }
        });


        choose_takeoff_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calender_dialog();
            }
        });

        save_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valdidate_and_save_flight_to_db();
            }
        });


    }


    private void valdidate_and_save_flight_to_db(){

        final String flight_id= id.getText().toString();
        final String flight_name = name.getText().toString();
        final String flight_source = source_spinner.getItemAtPosition(source_spinner.getSelectedItemPosition()).toString();
        final String flight_destination = destination_spinner.getItemAtPosition(destination_spinner.getSelectedItemPosition()).toString();
        final String _flight_available_seats = seats.getText().toString();
        int flight_available_seat = Integer.parseInt(_flight_available_seats);
        final String flight_price = price.getText().toString();
        final String flight_takeoff_date = takeoff_date.getText().toString();
        final String flight_departure_time = departure.getText().toString();
        final String flight_arrival_time = arrival.getText().toString();

        if( flight_id.length() == 0 )  id.setError( "Flight Id is required!" );
        else if( flight_name.length() == 0 ) name.setError( "Flight Name is required!" );
        else if( flight_source.trim().equals("*select country*"))  Toast.makeText(add_flight.this, "please select a valid country", Toast.LENGTH_SHORT).show();
        else if( flight_destination.trim().equals("*select country*"))  Toast.makeText(add_flight.this, "please select a valid country", Toast.LENGTH_SHORT).show();
        else if( flight_price.length() == 0 ) price.setError( "Price is required!" );
        else if( flight_takeoff_date.length() == 0) takeoff_date.setError( "Take off date is required!" );
        else if( flight_departure_time.length() == 0 ) departure.setError( "Departure time is required!" );
        else if( flight_arrival_time.length() == 0 ) arrival.setError( "Arrival time is required!" );
        else{


            Boolean insert_into_flight_db = database.insert(flight_id,flight_name,flight_source, flight_destination, flight_available_seat, flight_price, flight_takeoff_date, flight_departure_time, flight_arrival_time);

            if(insert_into_flight_db == true){

                Toast.makeText(add_flight.this, "New flight added successfully ", Toast.LENGTH_LONG).show();
            }else{

                Toast.makeText(add_flight.this, "Flight Id already exist :unamused: ", Toast.LENGTH_LONG).show();
            }

        }

    }

    private void calender_dialog(){

        final Calendar calendar = Calendar.getInstance();
        int yy = calendar.get(Calendar.YEAR);
        int mm = calendar.get(Calendar.MONTH);
        int dd = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(add_flight.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                int s = monthOfYear + 1;
                String DateString = dayOfMonth + "/" + s + "/" + year;
                takeoff_date.setText(DateString);


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
        source_spinner.setAdapter(countryAdapter);
        destination_spinner.setAdapter(countryAdapter);
    }

    private void initializeViews(){

        id =  findViewById(R.id.txtFlightId);
        name =  findViewById(R.id.txtFlightName);
        source_spinner =  findViewById(R.id.spinnerSource);
        destination_spinner =  findViewById(R.id.spinnerDestination);
        seats = findViewById(R.id.txtAvailableSeats);
        price = findViewById(R.id.txtPrice);
        takeoff_date = findViewById(R.id.txtTakeoffDate);
        choose_takeoff_date =  findViewById(R.id.btnTakeoffDate);
        departure =  findViewById(R.id.txtDepartureTime);
        arrival =  findViewById(R.id.txtArrivalTime);
        save_flight =  findViewById(R.id.btnSaveFlight);

    }


}
