package com.example.registrationform;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {

    //Declare all the views used in the XML file
    EditText editTextFname, editTextEmail, editTextMobile,editTextPassword,editTextConfirmPassword;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(true);
        setContentView(R.layout.activity_main);

        //Initialize all the views (linking to corresponding XML element ID)
        editTextFname = findViewById(R.id.editTextTextPersonName7);
        editTextEmail = findViewById(R.id.editTextTextPersonName8);
        editTextMobile = findViewById(R.id.editTextTextPersonName9);
        editTextPassword = findViewById(R.id.editTextTextPersonName10);
        editTextConfirmPassword = findViewById(R.id.editTextTextPersonName11);
        buttonRegister = findViewById(R.id.button3);

        //Create an instance of AlertDialog.Builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //Define what must happen when the Register button is clicked
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Read all the values entered by the user into string variables
                String firstName, lastName, age, email, mobile, gender="",password,confirmpassword;
                firstName = editTextFname.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();
                mobile = editTextMobile.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                confirmpassword = editTextConfirmPassword.getText().toString().trim();

                //Create a summary message
                String message = "Name: "+firstName+" "+"\nEmail: "+email+"\nMobile No.: "+mobile;

                //Perform validation checks
                if(firstName.isEmpty()) {
                    editTextFname.setError("Field is required");
                } else if(email.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                    editTextEmail.setError("Invalid email");
                } else if(mobile.isEmpty() || Long.parseLong(mobile)<1000000000) {
                    editTextMobile.setError("Invalid mobile number");
                }
                else if(password.length()<6) {
                    editTextPassword.setError("Invalid password");
                    if(!password.equals(confirmpassword))  {
                        Toast.makeText(MainActivity.this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Set a title and message
                    alertDialogBuilder.setTitle("Registration Successful");
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setPositiveButton("OK", null);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    editTextFname.getText().clear();
                    editTextEmail.getText().clear();
                    editTextMobile.getText().clear();
                    editTextPassword.getText().clear();
                    editTextConfirmPassword.getText().clear();
                    getCurrentFocus().clearFocus();
                }
            }
        });
    }

    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}