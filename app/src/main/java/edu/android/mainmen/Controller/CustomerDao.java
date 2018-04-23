package edu.android.mainmen.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import edu.android.mainmen.Model.Customer;
import edu.android.mainmen.Login.CustomerDBHelper;

import static edu.android.mainmen.Model.Customer.CustomerEntity.*;

public class CustomerDao {
    private static CustomerDao instance = null;
    private SQLiteDatabase database;

    private CustomerDao(Context context) {
        CustomerDBHelper helper = new CustomerDBHelper(context);
        database = helper.getWritableDatabase();

    }

    public static CustomerDao getInstance(Context context) {
        if(instance == null)
            instance = new CustomerDao(context);
        return instance;
    }

    //TODO : 정보를 db에 넣을 때 호출할 메소드
    public long insert(Customer customer) {
        long result = 0;
        ContentValues values = new ContentValues();
        values.put(COL_ID, customer.getId());
        values.put(COL_PASSWORD, customer.getPassword());
        values.put(COL_NAME, customer.getName());
        values.put(COL_AGE, customer.getAge());
        values.put(COL_EMAIL, customer.getEmail());

        result = database.insert(Customer.CustomerEntity.TABLE_NAME, null, values);
        return result;
    }

    //TODO : 비번찾기 할 때 호출할 메소드
    public Customer selectPasswordById(int cutstomerId) {
        Customer customer= null;
        String selection = COL_ID + " = ?";
        String [] selectionArgs = {String.valueOf(cutstomerId) };
        Cursor cursor = database.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if(cursor.moveToNext()) {
            String id = cursor.getString(1);
            String password = cursor.getString(2);
            String name = cursor.getString(3);
            int age = cursor.getInt(4);
            String email = cursor.getString(5);
            customer = new Customer(id, password, name, age, email);
        }
        return customer;
    }
}