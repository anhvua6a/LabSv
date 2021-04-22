package com.example.sv402.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("age")
    @Expose
    public int age;
    @SerializedName("address")
    @Expose
    public String address;

}
