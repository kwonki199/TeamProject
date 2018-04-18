package edu.android.mainmen.Login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import static android.provider.BaseColumns._ID;
import static edu.android.mainmen.Login.Customer.CustomerEntity.*;

public class CustomerDBHelper extends SQLiteOpenHelper {
    public static final String TAG = "edu.android";
    private static final String DB_NAME = "customer.db";
    private static final int DB_VERSION = 1;
    private static final String CREATE_CUSTOMER_TABLE = "create table " + Customer.CustomerEntity.TABLE_NAME
            + " (" + COL_ID + " integer primary key autoincrement, " + COL_PASSWORD + " text, " + COL_NAME + " text, " + COL_AGE + " text, " +  COL_EMAIL + ")";
    public CustomerDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        Log.i(TAG, "ProductDBHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_CUSTOMER_TABLE);
        Log.i(TAG, "ProductDBHelper onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}