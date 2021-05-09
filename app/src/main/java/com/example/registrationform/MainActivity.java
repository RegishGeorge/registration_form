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
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {

    //Declare all the views used in the XML file
    EditText editTxtFname, editTxtLname, editTxtAge, editTxtEmail, editTxtMobile;
    Button btnSubmit;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(true);
        setContentView(R.layout.activity_main);

        //Initialize all the views (linking to corresponding XML element ID)
        editTxtFname = findViewById(R.id.edit_txt_fname);
        editTxtLname = findViewById(R.id.edit_txt_lname);
        editTxtAge = findViewById(R.id.edit_txt_age);
        editTxtEmail = findViewById(R.id.edit_txt_email);
        editTxtMobile = findViewById(R.id.edit_txt_mobile);
        radioGroup = findViewById(R.id.radioGroup);
        btnSubmit = findViewById(R.id.btn_submit);

        //Create an instance of AlertDialog.Builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //Define what must happen when the Register button is clicked
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName, lastName, age, email, mobile, gender = "";
                String errorMsg = "Field is required";

                //Read the values entered by the user and store to variables
                firstName = editTxtFname.getText().toString().trim();
                lastName = editTxtLname.getText().toString().trim();
                age = editTxtAge.getText().toString().trim();
                email = editTxtEmail.getText().toString().trim();
                mobile = editTxtMobile.getText().toString().trim();
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.rbtn_male:
                        gender = "Male";
                        break;
                    case R.id.rbtn_female:
                        gender = "Female";
                        break;
                    case R.id.rbtn_other:
                        gender = "Other";
                        break;
                }

                //Create a summary of the registration form details entered by the user
                String message = "Name: " + firstName + " " + lastName + "\nAge: " + age + "\nEmail address: " + email + "\nMobile No.: " + mobile + "\nGender: " + gender;

                //Validations
                if (firstName.isEmpty()) {
                    editTxtFname.setError(errorMsg);
                } else if (lastName.isEmpty()) {
                    editTxtLname.setError(errorMsg);
                } else if (age.isEmpty() || Integer.parseInt(age)==0) {
                    editTxtAge.setError("Invalid age");
                } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editTxtEmail.setError("Invalid email");
                } else if (mobile.isEmpty() || Long.parseLong(mobile)<1000000000) {
                    editTxtMobile.setError("Invalid mobile number");
                } else if (gender.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                } else {
                    alertDialogBuilder.setTitle("Registration Successful");
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setPositiveButton("OK", null);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    editTxtFname.getText().clear();
                    editTxtLname.getText().clear();
                    editTxtAge.getText().clear();
                    editTxtEmail.getText().clear();
                    editTxtMobile.getText().clear();
                    radioGroup.clearCheck();
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