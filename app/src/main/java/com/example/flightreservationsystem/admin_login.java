package com.example.flightreservationsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class admin_login extends AppCompatActivity {

    EditText email_address;
    EditText password;
    Button Login;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        email_address = findViewById(R.id.txtAdminEmailaddress);
        password = findViewById(R.id.txtAdminPassword);
        Login = findViewById(R.id.btnAdminLogin);

        auth = FirebaseAuth.getInstance();
        System.err.println("--------------" + auth);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String admin_email = email_address.getText().toString();
                String admin_password = password.getText().toString();

                if( admin_email.length() == 0 )  email_address.setError( "Email address is required!" );
                else if ( admin_password.length() == 0 )  password.setError( "Password is required!" );
                else{

                    auth.signInWithEmailAndPassword(admin_email, admin_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                // there was an error
                                Log.e("Auth Error", "Failed=" + task.getException().getMessage());
                                Toast.makeText(admin_login.this, "Login failed, please check your email or password ", Toast.LENGTH_LONG).show();

                            } else {
                                startActivity(new Intent(admin_login.this, admin_menu.class));
                            }


                        }
                    });
                }



            }
        });
    }
}
