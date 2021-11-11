package com.example.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.registrationform.Database.AppDatabase;
import com.example.registrationform.Database.UserDao;

public class HomeScreenActivity extends AppCompatActivity {
    TextView message;
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        db = AppDatabase.getDatabase(HomeScreenActivity.this);
        UserDao userDao = db.userDao();
        message = findViewById(R.id.textLogin);
        int uid = getIntent().getIntExtra("userId", 0);
        String msg;
        if (uid != 0) {
            msg = "Hello, " + userDao.getUser(uid).name;
        } else {
            msg = "Uh oh! Something went wrong";
        }
        message.setText(msg);
    }
}