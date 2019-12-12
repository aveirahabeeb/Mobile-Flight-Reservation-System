package com.example.flightreservationsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.flight_model;

public class book_flight extends AppCompatActivity {

    db_helper db;
    RecyclerView book_recyler_view;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_flight);


        db = new db_helper(this);
        initializeViews();
        loadData();

        book_recyler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View itemView, int position) {

                        TextView name = itemView.findViewById(R.id.holder_book_flight_name);
                        TextView id =  itemView.findViewById(R.id.holder_book_flight_id);
                        TextView arrival =  itemView.findViewById(R.id.holder_book_arrival);
                        TextView departure =  itemView.findViewById(R.id.holder_book_departure);
                        TextView price =   itemView.findViewById(R.id.holder_book_price);
                        TextView takeoff_date = itemView.findViewById(R.id.holder_book_takeoffdate);
                        TextView seats =  itemView.findViewById(R.id.holder_book_seat);
                        TextView source = itemView.findViewById(R.id.holder_book_source);
                        TextView destination =  itemView.findViewById(R.id.holder_book_destination);

                        String flight_name = name.getText().toString();
                        String flight_id = id.getText().toString();
                        String flight_arrival = arrival.getText().toString();
                        String flight_departure = departure.getText().toString();
                        String flight_takeoff_date = takeoff_date.getText().toString();
                        String flight_price = price.getText().toString();
                        String available_seat = seats.getText().toString();
                        String flight_source = source.getText().toString();
                        String flight_destination = destination.getText().toString();


                        String _flight_name = flight_name.substring(flight_name.lastIndexOf(":") + 1);
                        String _flight_id = flight_id.substring(flight_id.lastIndexOf(":") + 1);
                        String _flight_arrival = flight_arrival.substring(flight_arrival.lastIndexOf(":") -  1);
                        String _flight_departure = flight_departure.substring(flight_departure.lastIndexOf(":") - 1);
                        String _flight_takeoff_date = flight_takeoff_date.substring(flight_takeoff_date.lastIndexOf(":") + 1);
                        String _flight_price = flight_price.substring(flight_price.lastIndexOf(":") + 1);
                        String _available_seat = available_seat.substring(available_seat.lastIndexOf(":") + 1);
                        String _flight_source = flight_source.substring(flight_source.lastIndexOf(":") + 1);
                        String _flight_destination = flight_destination.substring(flight_destination.lastIndexOf(":") + 1);

                        Intent intent = new Intent(book_flight.this, book_flight_main.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("_flight_name", _flight_name);
                        bundle.putString("_flight_id", _flight_id);
                        bundle.putString("_flight_arrival", _flight_arrival);
                        bundle.putString("_flight_departure", _flight_departure);
                        bundle.putString("_flight_price", _flight_price);
                        bundle.putString("_flight_takeoff_date", _flight_takeoff_date);
                        bundle.putString("_available_seat", _available_seat);
                        bundle.putString("_flight_departure", _flight_departure);
                        bundle.putString("_flight_source", _flight_source);
                        bundle.putString("_flight_destination", _flight_destination);

                        intent.putExtras(bundle);
                        startActivity(intent);


                    }
                }));



    }


    public void loadData(){

        ArrayList<flight_model> all_flight_data = db.getallflightdata();
        if (all_flight_data.isEmpty()) {
            Toast.makeText(book_flight.this, "Flight data not available", Toast.LENGTH_SHORT).show();
        }
        book_recyler_view.setAdapter(new adapter_book_class(all_flight_data));
    }

    private void initializeViews(){

        book_recyler_view = findViewById(R.id.bookRecylerView);
        book_recyler_view.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        book_recyler_view.setLayoutManager(mLayoutManager);

    }
}
