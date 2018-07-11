package com.wondercars.executiveridetracker.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

public class DatabaseManager {

    private static PiqipDbHelper DBHelper;
    private SQLiteDatabase db;
    private Context context;
    private static DatabaseManager databaseManager = null;

    protected DatabaseManager(Context ctx) {
        if (DBHelper == null)
            DBHelper = new PiqipDbHelper(ctx);
        try {
            DBHelper.createDataBase();
        }catch (IOException e){
            e.printStackTrace();
        }
        this.context = ctx;
    }

    public static DatabaseManager getInstance(Context ctx) {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager(ctx);
        }
        return databaseManager;
    }

    protected SQLiteDatabase open() throws SQLException {
        if (DBHelper != null) {
            db = DBHelper.getWritableDatabase();
            db = DBHelper.getReadableDatabase();
        }else {
            DBHelper = new PiqipDbHelper(context);
            db = DBHelper.getWritableDatabase();
            db = DBHelper.getReadableDatabase();
        }
        return db;
    }

    private void close() {
        DBHelper.close();
    }

    public void cleanUp(Long currentTime) {

    }

    public void clearDatabase() {
        open();
    }
}