package com.wondercars.executiveridetracker.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wondercars.executiveridetracker.Retrofit.DTOs.GetSlotsDTOs.BookingSlotsObj;

import java.util.ArrayList;


public class _BookingSlotsDB extends DatabaseManager {

    private static _BookingSlotsDB instance;

    public static class _BookingSlots {

        public static final String TABLE = "BookingSlots";
        public static final String BOOKING_ID = "bookingId";
        public static final String USER_ID = "userId";
        public static final String FROM_TIME = "fromTime";
        public static final String BOOKING_DATE = "bookingDate";
        public static final String CAR_ID = "carId";
    }

    private _BookingSlotsDB(Context context) {
        super(context);
    }

    public static _BookingSlotsDB getInstance(Context context) {
        if (instance == null)
            instance = new _BookingSlotsDB(context);
        return instance;
    }

    private BookingSlotsObj makebookingSlots(Cursor cur) {
        BookingSlotsObj bookingSlotsVO = new BookingSlotsObj();
        bookingSlotsVO.setId(cur.getString(cur.getColumnIndex(_BookingSlots.BOOKING_ID)));
        bookingSlotsVO.setUid(cur.getString(cur
                .getColumnIndex(_BookingSlots.USER_ID)));
        bookingSlotsVO.setFromTime(cur.getString(cur
                .getColumnIndex(_BookingSlots.FROM_TIME)));
        bookingSlotsVO.setBookingDate(cur.getString(cur
                .getColumnIndex(_BookingSlots.BOOKING_DATE)));
        bookingSlotsVO.setCarId(cur.getString(cur
                .getColumnIndex(_BookingSlots.CAR_ID)));
        return bookingSlotsVO;
    }

    private ContentValues makebookingSlotsContentValues(BookingSlotsObj bookingSlotsVO) {
        ContentValues cv = new ContentValues();
        cv.put(_BookingSlots.BOOKING_ID, bookingSlotsVO.getId());
        cv.put(_BookingSlots.USER_ID, bookingSlotsVO.getUid());
        cv.put(_BookingSlots.BOOKING_DATE, bookingSlotsVO.getBookingDate());
        cv.put(_BookingSlots.FROM_TIME, bookingSlotsVO.getFromTime());
        cv.put(_BookingSlots.CAR_ID, bookingSlotsVO.getCarId());
        return cv;
    }

    public void cleanUpEvents() {
        SQLiteDatabase db = open();
        db.delete(_BookingSlots.TABLE, null, null);

    }


    public boolean insertbookingSlotsList(ArrayList<BookingSlotsObj> bookingSlotsVOArrayList) {
        SQLiteDatabase db = open();
        for (BookingSlotsObj category : bookingSlotsVOArrayList) {
            ContentValues cv = makebookingSlotsContentValues(category);
            db.insert(_BookingSlots.TABLE, null, cv);
        }
        return true;
    }

    public boolean updatebookingSlotsList(ArrayList<BookingSlotsObj> bookingSlotsVOArrayList) {
        SQLiteDatabase db = open();
        String whereClause;
        for (BookingSlotsObj bookingSlotsVO : bookingSlotsVOArrayList) {
            whereClause = _BookingSlots.BOOKING_ID + " = " + bookingSlotsVO.getId();
            ContentValues cv = makebookingSlotsContentValues(bookingSlotsVO);
            long count = db.update(_BookingSlots.TABLE, cv, whereClause, null);
            if (count <= 0)
                db.insert(_BookingSlots.TABLE, null, cv);
        }
        return true;
    }

    public void updatebookingSlotsVo(BookingSlotsObj bookingSlotsVO) {
        SQLiteDatabase db = open();
        String whereClause = _BookingSlots.BOOKING_ID + " = " + bookingSlotsVO.getId();
        ContentValues cv = makebookingSlotsContentValues(bookingSlotsVO);
        db.update(_BookingSlots.TABLE, cv, whereClause, null);
    }

    public void deletebookingSlots(BookingSlotsObj bookingSlotsVO) {
        SQLiteDatabase db = open();
        String whereClause = _BookingSlots.BOOKING_ID + " = " + bookingSlotsVO.getId();
        db.delete(_BookingSlots.TABLE, whereClause, null);
    }

    public void deletebookingSlotsAccordingToDid(String bookingSlotsid) {
        SQLiteDatabase db = open();
        String whereClause = _BookingSlots.BOOKING_ID  + " = '" + bookingSlotsid + "'";
        db.delete(_BookingSlots.TABLE, whereClause, null);
    }

    public BookingSlotsObj getbookingSlotssVo(String bookingId) {
        SQLiteDatabase db = open();
        BookingSlotsObj bookingSlotsVO=null;
        String whereClause = _BookingSlots.BOOKING_ID + " =?";
        Cursor cur = db.query(_BookingSlots.TABLE, null, whereClause, new String[]{bookingId}, null, null, null);
        /*String query= "SELECT * FROM  "+_BookingSlots.TABLE+"";
        Cursor cur= db .rawQuery(query, null);*/

        if (cur.getCount() > 0) {
            cur.moveToFirst();
            bookingSlotsVO = makebookingSlots(cur);

        }
        cur.close();
        return bookingSlotsVO;
    }


    public ArrayList<BookingSlotsObj> getbookingSlotssList() {
        SQLiteDatabase db = open();
        ArrayList<BookingSlotsObj> patientTableVoArrayList = new ArrayList<>();
        Cursor cur = db.query(_BookingSlots.TABLE, null, null, new String[]{}, null, null, null);
        if (cur.moveToFirst()) {
            do {
                patientTableVoArrayList.add(makebookingSlots(cur));
            } while (cur.moveToNext());
        }
        cur.close();
        return patientTableVoArrayList;
    }

    public void updatebookingSlotsWithInsertion(BookingSlotsObj bookingSlotsVO) {
        SQLiteDatabase db = open();
        String whereClause = _BookingSlots.BOOKING_ID + " = '" + bookingSlotsVO.getId() + "'";
        ContentValues cv = makebookingSlotsContentValuesForUpdate(bookingSlotsVO);
        long count = db.update(_BookingSlots.TABLE, cv, whereClause, null);
        if (count <= 0)
            db.insert(_BookingSlots.TABLE, null, makebookingSlotsContentValues(bookingSlotsVO));
    }


    private ContentValues makebookingSlotsContentValuesForUpdate(BookingSlotsObj bookingSlotsVO) {
        ContentValues cv = new ContentValues();
        cv.put(_BookingSlots.USER_ID, bookingSlotsVO.getUid());
        cv.put(_BookingSlots.BOOKING_ID, bookingSlotsVO.getId());
        cv.put(_BookingSlots.BOOKING_DATE, bookingSlotsVO.getBookingDate());
        cv.put(_BookingSlots.CAR_ID, bookingSlotsVO.getCarId());
        cv.put(_BookingSlots.FROM_TIME, bookingSlotsVO.getFromTime());
        return cv;
    }

}
