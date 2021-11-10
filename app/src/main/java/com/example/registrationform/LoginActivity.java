package com.example.registrationform;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.registrationform.Database.AppDatabase;
import com.example.registrationform.Database.User;
import com.example.registrationform.Database.UserDao;

public class LoginActivity extends AppCompatActivity {

    Button registerButton, loginButton;
    EditText editemail,editpassword;
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(true);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.buttonLogin);
        registerButton = findViewById(R.id.buttonRegister);
        editemail = findViewById(R.id.editTextEmail);
        editpassword = findViewById(R.id.editTextPassword);
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app-db").build();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email = editemail.getText().toString().trim();
                password = editpassword.getText().toString().trim();
                if(email.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(email).matches()))
                    editemail.setError("Invalid email");
                else if(password.isEmpty())
                    editpassword.setError("Invalid password");
                else {
                    UserDao userDao = db.userDao();
                    String dbPass = userDao.getPasswordByEmail(email);
                    Log.d("BUILD", "onClick: dbPass:" + dbPass);
                    if (!password.equals(dbPass)) {
                        Toast.makeText(LoginActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                        startActivity(intent);
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