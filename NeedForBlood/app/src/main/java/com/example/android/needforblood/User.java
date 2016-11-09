package com.example.android.needforblood;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by rbpatel7 on 11/3/2016.
 */

public class User implements Serializable{

    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    private String usename,password,age,bg;
}
