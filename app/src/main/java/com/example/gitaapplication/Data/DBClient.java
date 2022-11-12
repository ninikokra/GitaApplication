package com.example.gitaapplication.Data;

import android.content.Context;

import androidx.room.Room;

public class DBClient {
    private Context context;
    private static DBClient mainInstance;
    private AppDataBase appDataBase;

    public DBClient(Context context){
        this.context = context;
        appDataBase = Room.databaseBuilder(context,AppDataBase.class,
                "second").build();
    }
    public static synchronized DBClient getInstance(Context context){
        if (mainInstance == null){
            mainInstance = new DBClient(context);
        }
        return mainInstance;
    }
    public AppDataBase getAppDataBase(){
        return appDataBase;
    }
}
