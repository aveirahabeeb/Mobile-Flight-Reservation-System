package com.example.flightreservationsystem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import  model.flight_model;
public class remove_flight extends AppCompatActivity {


    private ListView listView;
    db_helper db = null ;
    ArrayAdapter adapter = null;
    ArrayAdapter adapter1 = null;
    ArrayList<String>  flight_id = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_flight);

        db = new db_helper(this);
        listView = findViewById(R.id.FlightIdlistView);
        flight_id = db.getallflightId();
        adapter = new ArrayAdapter<String>(remove_flight.this, android.R.layout.simple_expandable_list_item_1,  flight_id);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String flight_to_delete = (String)adapterView.getItemAtPosition(position);
                AlertDialog diaBox = AskOption(flight_to_delete);
                diaBox.show();


            }
        });
    }

    private AlertDialog AskOption(final String flight_to_delete) {

        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // set message, title, and icon
                .setTitle("Delete")
                .setMessage(":question: You are about to Delete Flight data")


                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                       db.deleteflight(flight_to_delete);
                       Toast.makeText(remove_flight.this, "Flight data deleted ", Toast.LENGTH_SHORT).show();
                        flight_id = db.getallflightId();
                        if (flight_id.isEmpty()) {
                            Toast.makeText(remove_flight.this, "data not available", Toast.LENGTH_SHORT).show();
                        }
                        adapter1 = new ArrayAdapter<String>(remove_flight.this, android.R.layout.simple_expandable_list_item_1,  flight_id);
                        listView.setAdapter(adapter1);
                        dialog.dismiss();

                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();

        return myQuittingDialogBox;
    }

}
