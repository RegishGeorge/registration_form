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
    EditText editTextFname, editTextLname, editTextAge, editTextEmail, editTextMobile,editTextPassword,editTextConfirmPassword;
    RadioGroup radioGroup;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(true);
        setContentView(R.layout.activity_main);

        //Initialize all the views (linking to corresponding XML element ID)
        editTextFname = findViewById(R.id.editTextFname);
        editTextLname = findViewById(R.id.editTextLname);
        editTextAge = findViewById(R.id.editTextAge);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        radioGroup = findViewById(R.id.radioGroup);
        buttonRegister = findViewById(R.id.buttonRegister);

        //Create an instance of AlertDialog.Builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        //Define what must happen when the Register button is clicked
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Read all the values entered by the user into string variables
                String firstName, lastName, age, email, mobile, gender="",password,confirmpassword;
                firstName = editTextFname.getText().toString().trim();
                lastName = editTextLname.getText().toString().trim();
                age = editTextAge.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();
                mobile = editTextMobile.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                confirmpassword = editTextConfirmPassword.getText().toString().trim();
                switch(radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButtonMale:      gender = "Male";
                                                    break;
                    case R.id.radioButtonFemale:    gender = "Female";
                                                    break;
                    case R.id.radioButtonOther:     gender = "Other";
                                                    break;
                }

                //Create a summary message
                String message = "Name: "+firstName+" "+lastName+"\nAge: "+age+"\nEmail: "+email+"\nMobile No.: "+mobile+"\nGender: "+gender;

                //Perform validation checks
                if(firstName.isEmpty()) {
                    editTextFname.setError("Field is required");
                } else if(lastName.isEmpty()) {
                    editTextLname.setError("Field is required");
                } else if(email.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                    editTextEmail.setError("Invalid email");
                } else if(age.isEmpty() || Integer.parseInt(age)==0) {
                    editTextAge.setError("Invalid age");
                } else if(mobile.isEmpty() || Long.parseLong(mobile)<1000000000) {
                    editTextMobile.setError("Invalid mobile number");
                }
                else if(password.length()<6) {
                    editTextPassword.setError("Invalid password");
                    if(!password.equals(confirmpassword));
                        {
                        Toast.makeText(MainActivity.this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                         }
                    }
                else if(gender.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please select your gender", Toast.LENGTH_SHORT).show();
                } else {
                    //Set a title and message
                    alertDialogBuilder.setTitle("Registration Successful");
                    alertDialogBuilder.setMessage(message);
                    alertDialogBuilder.setPositiveButton("OK", null);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    editTextFname.getText().clear();
                    editTextLname.getText().clear();
                    editTextAge.getText().clear();
                    editTextEmail.getText().clear();
                    editTextMobile.getText().clear();
                    editTextPassword.getText().clear();
                    editTextConfirmPassword.getText().clear();
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