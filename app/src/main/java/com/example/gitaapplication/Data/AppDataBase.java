package com.example.gitaapplication.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Users.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract UsersDAO usersDAO();
}
