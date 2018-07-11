package com.wondercars.executiveridetracker.Database;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import umer.accl.utils.MyAppsLog;

public class PiqipDbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "executives.sqlite";
    //TODO before create new signed APK.Please update DB Version = 2
    public static final int DB_VERSION = 2;
    public static String DB_PATH = "/data/data/" + "com.wondercars.executiveridetracker" + "/databases/";
    final int PERMISSIONS_REQUEST_WRITE_STORAGE_STATE = 1;
    private Context context;


    /*ActivityCompat.OnRequestPermissionsResultCallback onRequestPermissionsResultCallback = new ActivityCompat.OnRequestPermissionsResultCallback() {
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode) {
                case PERMISSIONS_REQUEST_WRITE_STORAGE_STATE:
                    if (verifyPermissions(grantResults)) {
                        backup();
                    }
//                else {
//                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
//                }
                    break;
            }

        }
    };*/
    private SQLiteDatabase db;


    public PiqipDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;

    }

    public static boolean verifyPermissions(int[] grantResults) {
        if (grantResults.length < 1) {
            return false;
        }

        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void createDataBase() throws IOException {
        //check if the database exists
        Log.v("In Create Database ", "");
        boolean databaseExist = checkDataBase();
        Log.v("Database exists : ", "" + databaseExist);
        if (databaseExist) {
            // Do Nothing.

        } else {
            this.getWritableDatabase();
            /*Boolean checkFlag=checkOldDataBase();
            if(checkFlag){
                context.deleteDatabase(DB_NAME);
            }*/
            //copyDataBase();


            //Log.v("Old Database exists : ", "" + checkFlag);

        }// end if else dbExist
    } // end createDataBase().

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase() {
        File databaseFile = new File(DB_PATH + DB_NAME);
        return databaseFile.exists();
    }

    public boolean checkOldDataBase() {
        File databaseFile = new File(DB_PATH + DB_NAME);
        return databaseFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transferring byte stream.
     */
    private void copyDataBase() throws IOException {
        Log.v("In Create copyDataBase ", "");
        //Open your local db as the input stream
        InputStream myInput = context.getAssets().open("piqip.sqlite");
        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;
        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
        Log.v("Databse :", "Inserted successfully");
    }

    /**
     * This method opens the data base connection.
     * First it create the path up till data base of the device.
     * Then create connection with data base.
     */
    public void openDataBase() throws SQLException {
        //Open the database
        String myPath = DB_PATH + DB_NAME;
        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * This Method is used to close the data base connection.
     */
    @Override
    public synchronized void close() {
        if (db != null)
            db.close();
        super.close();
    }

    private static final String CREATE_TABLE_BOOKINGSLOTS = "CREATE TABLE "
            + _BookingSlotsDB._BookingSlots.TABLE + "(" + _BookingSlotsDB._BookingSlots.BOOKING_ID + " TEXT PRIMARY KEY," + _BookingSlotsDB._BookingSlots.USER_ID
            + " TEXT," + _BookingSlotsDB._BookingSlots.FROM_TIME + " DATETIME," + _BookingSlotsDB._BookingSlots.CAR_ID + " TEXT," + _BookingSlotsDB._BookingSlots.BOOKING_DATE
            + " DATETIME" + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOOKINGSLOTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       /* String upgradeDemoGraphicTableQuery = "ALTER TABLE " + _BookingSlotsDB._DemoGraphic.TABLE + " ADD COLUMN " + _BookingSlotsDB._DemoGraphic.p_age + " TEXT";
        String upgradePrescriptionTableQuery = "ALTER TABLE " + _PrescriptionDB._Prescription.TABLE + " ADD COLUMN " + _PrescriptionDB._Prescription.NOTE + " TEXT";

        String upgradePrescriptionuserIdTableQuery = "ALTER TABLE " + _PrescriptionDB._Prescription.TABLE + " ADD COLUMN " + _PrescriptionDB._Prescription.USER_ID + " TEXT";
        String upgradeNotesuserIdTableQuery = "ALTER TABLE " + _NotesDB._Notes.TABLE + " ADD COLUMN " + _NotesDB._Notes.USER_ID + " TEXT";
        String upgrade_DemoGraphicDBsuserIdTableQuery = "ALTER TABLE " + _BookingSlotsDB._DemoGraphic.TABLE + " ADD COLUMN " + _BookingSlotsDB._DemoGraphic.USER_ID + " TEXT";
        String upgrade_DiagnosisDBuserIdTableQuery = "ALTER TABLE " + _DiagnosisDB._Diagnosis.TABLE + " ADD COLUMN " + _DiagnosisDB._Diagnosis.USER_ID + " TEXT";
        String upgradeNote_InvestigationDB_userIdTableQuery = "ALTER TABLE " + _InvestigationDB._Investigation.TABLE + " ADD COLUMN " + _InvestigationDB._Investigation.USER_ID + " TEXT";
        String upgrade_OrderInstructionDB_userIdTableQuery = "ALTER TABLE " + _OrderInstructionDB._OrderInstruction.TABLE + " ADD COLUMN " + _OrderInstructionDB._OrderInstruction.USER_ID + " TEXT";
        String upgrade_PatientDB_userIdTableQuery = "ALTER TABLE " + _PatientDB._Patient.TABLE + " ADD COLUMN " + _PatientDB._Patient.USER_ID + " TEXT";
        String upgrade_PrescriptionsNoteDB_userIdTableQuery = "ALTER TABLE " + _PrescriptionsNoteDB._PrescriptionsNote.TABLE + " ADD COLUMN " + _PrescriptionsNoteDB._PrescriptionsNote.USER_ID + " TEXT";
        String upgrade_Recent_Search_PrescriptionDB_userIdTableQuery = "ALTER TABLE " + _Recent_Search_PrescriptionDB._RecentSearchPrescription.TABLE + " ADD COLUMN " + _Recent_Search_PrescriptionDB._RecentSearchPrescription.USER_ID + " TEXT";
        String upgrade_VisitDB_userIdTableQuery = "ALTER TABLE " + _VisitDB._Visit.TABLE + " ADD COLUMN " + _VisitDB._Visit.USER_ID + " TEXT";
        String upgrade__VitalsDB_userIdTableQuery = "ALTER TABLE " + _VitalsDB._Vitals.TABLE + " ADD COLUMN " + _VitalsDB._Vitals.USER_ID + " TEXT";

        String CreatePreNote = "CREATE TABLE Prescriptions_note" + " (userId VARCHAR, patient_id VARCHAR, prescription_id  VARCHAR, Prescription_note_id VARCHAR, prescriptions_note VARCHAR DEFAULT (null), patient_visit_date  VARCHAR);";
*/
      /*private static final String NOTES_CREATE =
  "create table " + TABLE_NOTES + " ("
  + "id integer primary key autoincrement, "
  + "note text, "
  + "lastedit text);";*/


      /*  if (oldVersion < 2) {
            try {

                db.execSQL(upgradeDemoGraphicTableQuery);
                db.execSQL(upgradePrescriptionTableQuery);
                db.execSQL(upgrade_DemoGraphicDBsuserIdTableQuery);
                db.execSQL(upgrade_DiagnosisDBuserIdTableQuery);
                db.execSQL(upgradeNote_InvestigationDB_userIdTableQuery);
                db.execSQL(upgrade_OrderInstructionDB_userIdTableQuery);
                db.execSQL(upgrade_PatientDB_userIdTableQuery);
                db.execSQL(upgrade_PrescriptionsNoteDB_userIdTableQuery);
                db.execSQL(upgrade_Recent_Search_PrescriptionDB_userIdTableQuery);
                db.execSQL(upgrade_VisitDB_userIdTableQuery);
                db.execSQL(upgrade__VitalsDB_userIdTableQuery);
                db.execSQL(upgradePrescriptionuserIdTableQuery);
                db.execSQL(upgradeNotesuserIdTableQuery);

                MyAppsLog.v("Prescriptions_note", "Prescriptions_note created");

                //new CommonMethods().showShortToast(context, "onUpgrade");
                db.execSQL(CreatePreNote);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/

    }

    public void backup(Activity activity) {
        try {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_WRITE_STORAGE_STATE);
                return;
            }

            Log.v("In", "backUp");
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();
            if (sd.canWrite()) {
                Log.d("d", "sd can write");
                String currentDBPath = DB_PATH;//+ DatabaseWrapper.DATABASE_NAME;
                String backupDBPath = "PIQIP.db";
                File currentDB = new File(data, "data//com.ssm.piqip//databases//piqip.sqlite");
                File backupDB = new File(sd, backupDBPath);
               /* if(backupDB.exists()){
                    backupDB.delete();
                    backupDB.createNewFile();
                }*/
                if (currentDB.exists()) {
                    Log.d("d", "db exists");
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                Log.v("created ", "backUp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            // throw new Error("Copying Failed");
        }
    }
}
