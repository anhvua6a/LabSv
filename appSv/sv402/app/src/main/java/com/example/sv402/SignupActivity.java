package com.example.sv402;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sv402.model.serverResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    private EditText edtUser;
    private EditText edtPass;
    private EditText edtName;
    private EditText edtAge;
    private EditText edtAddress;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initView();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.31.17:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiSever api = retrofit.create(apiSever.class);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.register(edtUser.getText().toString().trim(),
                        edtPass.getText().toString().trim(),
                        edtName.getText().toString().trim(),
                        Integer.parseInt(edtAge.getText().toString()),
                        edtAddress.getText().toString().trim()).enqueue(new Callback<serverResponse>() {
                    @Override
                    public void onResponse(Call<serverResponse> call, Response<serverResponse> response) {
                        if (response.body().success) {
                            Toast.makeText(SignupActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(SignupActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<serverResponse> call, Throwable t) {
                        Log.e( "onFailure: ",t.getMessage() );
                    }
                });
            }
        });
    }

    private void initView() {
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtName = (EditText) findViewById(R.id.edtName);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        btnRegister = (Button) findViewById(R.id.btnRegister);
    }
}