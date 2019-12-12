package com.example.flightreservationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class user_login extends AppCompatActivity {


    EditText email_address;
    EditText password;
    Button Login;
    TextView Register;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);


        email_address = findViewById(R.id.txtUserEmailaddress);
        password = findViewById(R.id.txtUserPassword);
        Login = findViewById(R.id.btnUserLogin);
        Register = findViewById(R.id.textUserRegister);

        auth = FirebaseAuth.getInstance();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String user_email = email_address.getText().toString();
                String user_password = password.getText().toString();

                if( user_email.length() == 0 )  email_address.setError( "Email address is required!" );
                else if ( user_password.length() == 0 )  password.setError( "Password is required!" );
                else{

                    auth.signInWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                // there was an error


                                Toast.makeText(user_login.this, "Login failed, please check your email or password", Toast.LENGTH_LONG).show();

                            } else {

                                SharedPreferences mySharedPreferences = user_login.this.getSharedPreferences("emailpreference", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = mySharedPreferences.edit();
                                editor.putString("customer_id",user_email);
                                editor.apply();

                                startActivity(new Intent(user_login.this, user_menu.class));

                            }


                        }
                    });
                }



            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(user_login.this, user_register.class));

            }
        });




    }
}
