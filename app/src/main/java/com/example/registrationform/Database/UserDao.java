package com.example.registrationform.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users WHERE email=:input_email")
    User getUserByEmail(String input_email);

    @Query("SELECT * FROM users WHERE uid=:userId")
    User getUser(int userId);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
