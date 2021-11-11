package com.example.registrationform;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.registrationform.Database.AppDatabase;
import com.example.registrationform.Database.User;
import com.example.registrationform.Database.UserDao;

public class MainActivity extends Activity {

    //Declare all the views used in the XML file
    EditText editTextFname, editTextEmail, editTextMobile,editTextPassword,editTextConfirmPassword;
    Button buttonRegister;
    TextView textLogin;
    AppDatabase db;

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
        textLogin = findViewById(R.id.textView);

        db = AppDatabase.getDatabase(MainActivity.this);
        //Create an instance of AlertDialog.Builder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

        textLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = getIntent().getStringExtra("fromActivity");
                if (res != null && res.equals("Login")) {
                    finish();
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class)
                            .putExtra("fromActivity", "Register");
                    startActivity(intent);
                }
            }
        });

        //Define what must happen when the Register button is clicked
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Read all the values entered by the user into string variables
                String firstName, email, mobile,password, confirm_password;
                firstName = editTextFname.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();
                mobile = editTextMobile.getText().toString().trim();
                password = editTextPassword.getText().toString().trim();
                confirm_password = editTextConfirmPassword.getText().toString().trim();

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
                    if(!password.equals(confirm_password))  {
                        Toast.makeText(MainActivity.this, "Passwords doesn't match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    UserDao userDao = db.userDao();
                    User dbUser = userDao.getUserByEmail(email);
                    if (dbUser != null) {
                        Toast.makeText(MainActivity.this, "Email already in use!", Toast.LENGTH_SHORT).show();
                    } else {
                        dbUser = new User(firstName, email, mobile, password);
                        userDao.insert(dbUser);
                        Log.d("TAG", "onClick: dbUser" + dbUser);
                        Intent intent = new Intent(MainActivity.this, HomeScreenActivity.class)
                                .putExtra("userId", userDao.getUserByEmail(email).uid);
                        startActivity(intent);
                        finish();
                    }
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