package edu.android.mainmen.Model;

import android.provider.BaseColumns;

public class Customer {
    public static abstract class CustomerEntity implements BaseColumns {
        public static final String TABLE_NAME = "customers";
        public static final String COL_ID = "id";
        public static final String COL_PASSWORD = "password";
        public static final String COL_NAME = "name";
        public static final String COL_AGE = "age";
        public static final String COL_EMAIL = "email";
    }

    private String id;
    private String password;
    private String name;
    private int age;
    private String email;

    public Customer(String id, String password, String name, int age, String email) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}