package com.example.flightreservationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import model.User;

public class user_register extends AppCompatActivity {


    EditText email_address;
    EditText password;
    EditText full_name;
    EditText address;
    Spinner country_spinner;
    EditText phone_number;
    Button save;
    TextView txtlogin;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseRef;


    private static final String TAG = "MyActivity";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);


        firebaseAuth = FirebaseAuth.getInstance();
        databaseRef  = FirebaseDatabase.getInstance().getReference();


        initializeViews();
        setCountrySpinner();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String user_email_address = email_address.getText().toString();
                final String user_password= password.getText().toString();
                final String user_full_name = full_name.getText().toString();
                final String user_address= address.getText().toString();
                final String user_country = country_spinner.getItemAtPosition(country_spinner.getSelectedItemPosition()).toString();
                final String user_phone_number= phone_number.getText().toString();


                if(user_email_address.isEmpty() || user_password.isEmpty() || user_full_name.isEmpty() || user_address.isEmpty() || user_country.isEmpty() || user_phone_number.isEmpty() ) {
                    Toast.makeText(user_register.this, "Fill up fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if( user_email_address.length() < 6 )  email_address.setError( "Password should be at least 6 characters!" );


                //saves user data in db
                if(databaseRef != null){

                    Query query = databaseRef.child("CustomerDb").child("CustomerData").orderByChild("email").equalTo(user_email_address);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {


                          try{

                              if(dataSnapshot.getChildrenCount()>0) {
                                  Toast.makeText(user_register.this, "User'email' already exist", Toast.LENGTH_SHORT).show();

                              }else{
                                  //creates new login details for user
                                  firebaseAuth.createUserWithEmailAndPassword(user_email_address, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                      @Override
                                      public void onComplete(@NonNull Task<AuthResult> task) {

                                          if(task.isSuccessful()){ //if email and password is created successfully

                                              User user_data = new User(user_email_address, user_full_name, user_address, user_country, user_phone_number);
                                              databaseRef.child("CustomerDb").child("CustomerData").push().setValue(user_data);
                                              Toast.makeText(user_register.this, "Account created successfully", Toast.LENGTH_LONG).show();

                                          }else {
                                              Log.e("Error", "Failed=" + task.getException().getMessage());

                                              Toast.makeText(user_register.this, "Error creating customer data", Toast.LENGTH_SHORT).show();

                                          }
                                      }
                                  });


                              }


                          }catch (Exception e){
                              e.printStackTrace();
                              Toast.makeText(user_register.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                          }
                      }

                      @Override
                      public void onCancelled(DatabaseError databaseError) {

                          Toast.makeText(user_register.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                      }
                  });



                }


            }
        });



        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(user_register.this, user_login.class));

            }
        });




    }


    private void setCountrySpinner(){

        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        countries.add("**Select Country**");
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
        country_spinner.setAdapter(countryAdapter);
    }



    private void initializeViews(){


        email_address =  findViewById(R.id.txtUserEmailaddress);
        password =  findViewById(R.id.txtUserPassword);
        full_name = findViewById(R.id.txtFullName);
        address = findViewById(R.id.txtAddress);
        country_spinner =  findViewById(R.id.spinnerCountry);
        phone_number =  findViewById(R.id.txtPhoneNumber);
        save =  findViewById(R.id.btnRegister);
        txtlogin =  findViewById(R.id.txtlogin);

    }


}




