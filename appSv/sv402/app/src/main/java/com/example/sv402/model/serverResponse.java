package com.example.sv402.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class serverResponse {
    @SerializedName("success")
    @Expose
    public boolean success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("users")
    @Expose
    public List<User> userList;

}
