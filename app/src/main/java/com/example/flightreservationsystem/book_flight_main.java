package com.example.flightreservationsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class book_flight_main extends AppCompatActivity {

    public static final String SOURCES =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";

    db_helper database;
    EditText ticket_id, flight_name,flight_id,arrival,departure,price,customer_address, customer_name,
             flight_takeoff_date,available_seat,flight_source, flight_destination, customer_id;

    Spinner reserved_seat_spinner, gender_spinner, class_spinner;
    Button btn_book_flight;
    String _flight_name,_flight_id,
            _flight_arrival, _flight_departure,
            _flight_takeoff_date, _flight_source, _flight_destination, _flight_price, _available_seat;
    SQLiteDatabase db = null;
    private String BookFlight_Table = "bookflight_table";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_flight_main);
        database = new db_helper(this);

        getValuesfromBundle_and_setToViews();
        setSeatSpinner();

        btn_book_flight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate_and_send_data_to_db();


            }

        });

    }


    private void getValuesfromBundle_and_setToViews(){

        SharedPreferences mySharedPreferences = this.getSharedPreferences("emailpreference", Context.MODE_PRIVATE);
        String _customer_id = mySharedPreferences.getString("customer_id","");

        Bundle bundle = getIntent().getExtras();
        _flight_name = bundle.getString("_flight_name");
        _flight_id = bundle.getString("_flight_id");
        _flight_arrival = bundle.getString("_flight_arrival");
        _flight_departure = bundle.getString("_flight_departure");
        _flight_takeoff_date = bundle.getString("_flight_takeoff_date");
        _flight_price = bundle.getString("_flight_price");
        _available_seat = bundle.getString("_available_seat");
        _flight_source = bundle.getString("_flight_source");
        _flight_destination = bundle.getString("_flight_destination");


        class_spinner =  findViewById(R.id.spinnerClass);
        gender_spinner =  findViewById(R.id.spinnerGender);
        reserved_seat_spinner =  findViewById(R.id.spinnerReserveSeat);
        ticket_id = findViewById(R.id.txtTicketId);
        ticket_id.setText(generateString(new Random(), SOURCES, 8));
        flight_name = findViewById(R.id.txtFlightName);
        flight_id = findViewById(R.id.txtFlightId);
        arrival = findViewById(R.id.txtArrival);
        departure = findViewById(R.id.txtDeparture);
        price = findViewById(R.id.txtPrice);
        flight_takeoff_date = findViewById(R.id.txtflighttakeoff);
        available_seat = findViewById(R.id.txtSeats);
        flight_source = findViewById(R.id.txtSource);
        flight_destination = findViewById(R.id.txtDestination);
        customer_address = findViewById(R.id.txtAddress);
        customer_id = findViewById(R.id.txtCustomerId);
        btn_book_flight = findViewById(R.id.btnBookflight);
        customer_name = findViewById(R.id.txtCustomerName);

        customer_id.setText(_customer_id);
        flight_name.setText(_flight_name);
        flight_id.setText(_flight_id);
        arrival.setText(_flight_arrival);
        departure.setText(_flight_departure);
        flight_takeoff_date.setText(_flight_takeoff_date);
        price.setText(_flight_price);
        available_seat.setText(_available_seat);
        flight_source.setText(_flight_source);
        flight_destination.setText(_flight_destination);
    }

    private void setSeatSpinner(){
        ArrayList choose_seat = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            choose_seat.add(Integer.toString(i));
        }

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, choose_seat);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        reserved_seat_spinner.setAdapter(countryAdapter);

    }

    private void validate_and_send_data_to_db(){

        String _flight_name =  flight_name.getText().toString();
        String _flight_id =  flight_id.getText().toString();
        String _arrival = arrival.getText().toString();
        String _availableseat = available_seat.getText().toString();

        String _departure =  departure.getText().toString();
        String _flight_takeoff_date =  flight_takeoff_date.getText().toString();
        String _price =  price.getText().toString();
        String _flight_source = flight_source.getText().toString();
        String _flight_destination = flight_destination.getText().toString();

        String _ticket_id = ticket_id.getText().toString();
        String _customer_id = customer_id.getText().toString();

        String _class = class_spinner.getItemAtPosition(class_spinner.getSelectedItemPosition()).toString();
        String _gender = gender_spinner.getItemAtPosition(gender_spinner.getSelectedItemPosition()).toString();
        String _reserved_seat = reserved_seat_spinner.getItemAtPosition(reserved_seat_spinner.getSelectedItemPosition()).toString();
        String _customer_name = customer_name.getText().toString();
        String _customer_address = customer_address.getText().toString();


        if(_reserved_seat.trim().length() > _availableseat.trim().length()) Toast.makeText(book_flight_main.this, "No of seat not available", Toast.LENGTH_LONG).show();
        else if( _customer_name.length() == 0 )  customer_name.setError( "Name is required!" );
        else if( _customer_address.length() == 0 ) customer_address.setError( "Address is required!" );
        else{

            Boolean insert_into_bookflight = database.insertinto_bookflight_table(_customer_id,_customer_name,_flight_id,_flight_name,_flight_source,_flight_destination,_arrival,_departure,_price,_flight_takeoff_date,_ticket_id,_customer_address,_gender,_class,_reserved_seat);

            if(insert_into_bookflight == true){


                try{
                    Thread.sleep(2000);
                    createPdf(_ticket_id);
                    Toast.makeText(book_flight_main.this, "Flight booked and Booking Ticket download successful", Toast.LENGTH_SHORT).show();
                }catch(Exception e){ e.printStackTrace(); }


            }
            else {
                Toast.makeText(book_flight_main.this, "Error booking flight", Toast.LENGTH_LONG).show();

            }
        }


    }

    public static String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    public void createPdf(String ticket_Id) throws FileNotFoundException, DocumentException {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        System.out.println(formatter.format(date));

        Document document=new Document(PageSize.A4);  // create the document
        File root = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)+"/");
        if (!root.exists()) {
            root.mkdirs();   // create root directory in sdcard
        }
        Log.e("Error", root.toString());
        File file = new File(root,"Bookings_ " +formatter.format(date)+" .pdf");  // generate pdf file in that directory
        FileOutputStream fos = new FileOutputStream(file);
        PdfWriter.getInstance(document, fos);
        document.open();  // open the directory

        db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+ BookFlight_Table + " where ticket_id='" + ticket_Id + "'  " , null);

        document.add(new Paragraph("                        Booking Information \n\n"));

        PdfPTable table = new PdfPTable(2);

        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);


        //Internal Storage/Android/data/com.example.flightreservationsystem/files/Download



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


            table.addCell(new Paragraph("Customer Id:     " + customer_id));
            table.addCell(new Paragraph("Customer Name:     " + customer_name));
            table.addCell(new Paragraph("Customer Address:     " + customer_address));
            table.addCell(new Paragraph("Gender:     " + gender));
            table.addCell(new Paragraph("Ticket Id:     " + ticket_id));
            table.addCell(new Paragraph("Flight Id:     " + flight_id));
            table.addCell(new Paragraph("Flight Name:     " + flight_name));
            table.addCell(new Paragraph("Flight Source:     " + flight_source));
            table.addCell(new Paragraph("Flight Destination: " + flight_destination));
            table.addCell(new Paragraph("Arrival Time:     " + arrival));
            table.addCell(new Paragraph("Departure Time:     " + departure));
            table.addCell(new Paragraph("Price:     " + price));
            table.addCell(new Paragraph("Take off Date:     " + flight_takeoff_date));
            table.addCell(new Paragraph("Class:     " + class_type));
            table.addCell(new Paragraph("Reserved Seat:     " + reserved_seats));
            table.addCell(new Paragraph("------------"));


        }

        document.add(table);
        document.addCreationDate();
        document.close();

    }

}
