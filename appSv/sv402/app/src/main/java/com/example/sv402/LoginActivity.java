package com.example.sv402;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

import com.example.sv402.model.User;
import com.example.sv402.model.serverResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUser,edtPass;
    private Button btnLogin,btnSignup;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        init();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.31.17:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiSever api = retrofit.create(apiSever.class);





        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.login(edtUser.getText().toString(),edtPass.getText().toString()).enqueue(new Callback<serverResponse>() {
                    @Override
                    public void onResponse(Call<serverResponse> call, Response<serverResponse> response) {
                        if(response.body().success){
                            Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }else {
                            Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<serverResponse> call, Throwable t) {
                        Log.e("onFailure: ",t.getMessage() );
                    }
                });
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });
    }

    private void init() {
        edtPass=findViewById(R.id.edtPass);
        edtUser=findViewById(R.id.edtUser);
        btnLogin=findViewById(R.id.btnLogin);
        btnSignup=findViewById(R.id.btnSignup);
    }
}