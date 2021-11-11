package com.example.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.registrationform.Database.AppDatabase;
import com.example.registrationform.Database.User;
import com.example.registrationform.Database.UserDao;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    TextView registerButton;
    EditText editemail, editpassword;
    AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarTranslucent(true);
        setContentView(R.layout.activity_login);

        db = AppDatabase.getDatabase(LoginActivity.this);

        loginButton = findViewById(R.id.button2);
        registerButton = findViewById(R.id.textView2);
        editemail = findViewById(R.id.editTextTextPersonName);
        editpassword = findViewById(R.id.editTextTextPersonName2);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String res = getIntent().getStringExtra("fromActivity");
                if (res != null && res.equals("Login")) {
                    finish();
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class)
                            .putExtra("fromActivity", "Login");
                    startActivity(intent);
                }
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
                    User dbUser = userDao.getUserByEmail(email);
                    if (dbUser == null) {
                        Toast.makeText(LoginActivity.this, "User not Found!", Toast.LENGTH_SHORT).show();
                    }  else if (!password.equals(dbUser.password)) {
                        Toast.makeText(LoginActivity.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class)
                                .putExtra("userId", dbUser.uid);
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