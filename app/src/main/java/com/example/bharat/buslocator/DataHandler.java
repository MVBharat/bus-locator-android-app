package com.example.bharat.buslocator;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

/**
 * Created by Mr.Defect on 4/11/2017.
 */

public class DataHandler  {

    private static final String DATABASE_NAME = "Registeration.sqlite";
    public static final int DATABASE_VERSION=1;

    // Registaration table columns
    private static final String TABLE_NAME = "regisration_table";
    private static final String COL_NAME = "NAME";
    private static final String COL_EMAIL = "EMAIL";
    private static final String COL_MOBILE = "MOBILE";
    private static final String COL_PASSWORD = "PASSWORD";
    private static final String COL_REPASSWORD = "REPASSWORD";


    private static final String create_table = "CREATE TABLE " + TABLE_NAME + "(" + COL_NAME + " TEXT, " + COL_EMAIL + " TEXT NOT NULL, " + COL_MOBILE + " TEXT, " + COL_PASSWORD + " TEXT, " + COL_REPASSWORD + " TEXT );";

    private static final String TABLE_BUS_INFO = "bus_info";
    private static final String COL_BUSNUM = "BUS_NUM";
    private static final String COL_GPSID = "GPS";
    private static final String COL_BUS_DRIVER = "BUS_DRIVER";
    private static final String COL_BUS_ROOT = "BUS_ROOT";
    private static final String  COL_BUS_TIMING = "BUS_TIMING";

    private static final String create_table_businfo = "CREATE TABLE " + TABLE_BUS_INFO + "("
            + COL_BUSNUM + " TEXT, "
            + COL_GPSID + " TEXT, "
            + COL_BUS_DRIVER + " TEXT, "
            + COL_BUS_ROOT + " TEXT, "
            + COL_BUS_TIMING + " TEXT "
            + ");";

    //Complaint data
    private static final String TABLE_COMPLAINTNAME="complaints_table";
    private static final String COL_EMAILS="EMAILS";
    private static final String COL_SUB="SUB";
    private static final String COL_COMP="COMPLAINT";

    private static final String create_table_complaints= "CREATE TABLE " + TABLE_COMPLAINTNAME + "("
            + COL_EMAILS + " TEXT NOT NULL, "
            + COL_SUB + " TEXT NOT NULL, "
            + COL_COMP + " TEXT NOT NULL "
            + ");";

    DatabaseHelper dbhelper;
    Context ctx;
    SQLiteDatabase db;
    public DataHandler(Context ctx)
    {
        this.ctx=ctx;
        dbhelper=new DatabaseHelper(ctx);
    }




    private static class DatabaseHelper extends SQLiteOpenHelper
    {

        public DatabaseHelper(Context ctx)
        {
            super(ctx,DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.e("Database Operation","Before Table is created");
            try
            {
                db.execSQL(create_table);
                db.execSQL(create_table_businfo);
                db.execSQL(create_table_complaints);
                Log.e("Database Operation","Table is created");
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS regisration_table");
            db.execSQL("DROP TABLE IF EXISTS bus_info");
            db.execSQL("DROP TABLE IF EXISTS complaints_table");
            onCreate(db);
        }
    }
    public DataHandler open()
    {
        db=dbhelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        dbhelper.close();
    }


    public void InsertDataRe(String name, String mail, String mobile,String password, String confirmP) {

        db=dbhelper.getWritableDatabase();
        String sql="INSERT INTO regisration_table VALUES(?,?,?,?,?) ";
        SQLiteStatement statement=db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,name);
        statement.bindString(2,mail);
        statement.bindString(3,mobile);
        statement.bindString(4,password);
        statement.bindString(5,confirmP);
        statement.executeInsert();

    }
    public void insertBusInfo(String busno, String gpsiid, String driver, String root, String timing) {
        db=dbhelper.getWritableDatabase();
        String sql="INSERT INTO bus_info VALUES(?,?,?,?,?) ";
        SQLiteStatement statement=db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,busno);
        statement.bindString(2,gpsiid);
        statement.bindString(3,driver);
        statement.bindString(4,root);
        statement.bindString(5,timing);

        statement.executeInsert();
    }

    public void editComplaints(String emailadd, String subject, String compdetail) {

        db=dbhelper.getWritableDatabase();
        String sql= "INSERT INTO complaints_table VALUES(?,?,?) ";
        SQLiteStatement statement=db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1,emailadd);
        statement.bindString(2,subject);
        statement.bindString(3,compdetail);

        statement.executeInsert();
    }

    public Cursor getBusNODetails() {
        db=dbhelper.getWritableDatabase();
        Cursor data=db.rawQuery("Select * from bus_info",null);
        return data;

    }
    public Cursor getBusDetails(String busnum) {
        db=dbhelper.getWritableDatabase();
        Cursor data=db.rawQuery("Select * from bus_info where BUS_NUM='"+busnum+"'",null);
        return data;
    }

    public void deletbusinfo(String busnumb) {
        db=dbhelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_BUS_INFO+ " WHERE "+COL_BUSNUM+"='"+busnumb+"'");
    }


    public Cursor getDetails() {
        db=dbhelper.getWritableDatabase();
        Cursor data=db.rawQuery("Select * from complaints_table",null);
        return data;

    }


    public Cursor getDetailsUsers()
    {
        db=dbhelper.getWritableDatabase();
        Cursor data=db.rawQuery("Select * from regisration_table",null);
        return data;
    }

    public String getLoginCredits(String useremail) {
        db=dbhelper.getWritableDatabase();
        Cursor data=db.rawQuery("select PASSWORD from regisration_table where EMAIL='"+useremail+"'",null);
        if(data.getCount()<1)
        {
            return "NOT EXIST";
        }
        else
        {
            data.moveToFirst();
            String password=data.getString(data.getColumnIndex("PASSWORD"));
            data.close();
            return password;
        }
    }


    public void renewPassUpdate(String email,String newpass) {

        db=dbhelper.getWritableDatabase();
        db.execSQL("UPDATE regisration_table SET PASSWORD='"+newpass+"' WHERE EMAIL='"+email+"'");

    }
}



