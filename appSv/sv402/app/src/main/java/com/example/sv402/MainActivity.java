package com.example.sv402;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sv402.model.User;
import com.example.sv402.model.serverResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvList;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.31.17:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiSever api = retrofit.create(apiSever.class);


        api.getAllUser().enqueue(new Callback<serverResponse>() {
            @Override
            public void onResponse(Call<serverResponse> call, Response<serverResponse> response) {
                if (response.body().success) {
                   userList = response.body().userList;
                   UserAdapter userAdapter = new UserAdapter(MainActivity.this, userList, new UserAdapter.onClickDelete() {
                       @Override
                       public void onClickXoa(int position) {
                           api.deleteUser(userList.get(position).id).enqueue(new Callback<serverResponse>() {
                               @Override
                               public void onResponse(Call<serverResponse> call, Response<serverResponse> response) {
                                   if(response.body().success){
                                       Toast.makeText(MainActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                       startActivity(new Intent(MainActivity.this,MainActivity.class));
                                   }else {
                                       Toast.makeText(MainActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                   }
                               }

                               @Override
                               public void onFailure(Call<serverResponse> call, Throwable t) {
                                   Log.e("onFailure: ",t.getMessage());
                               }
                           });
                       }

                       @Override
                       public void onClickUpdate(int position) {
                           AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                           View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_signup,null);

                           alertDialog.setView(v);

                           TextView tvTitle;
                            EditText edtUser;
                            EditText edtPass;
                            EditText edtName;
                            EditText edtAge;
                            EditText edtAddress;
                            Button btnRegister;

                            tvTitle= v.findViewById(R.id.tvTitle);
                           edtUser = (EditText) v.findViewById(R.id.edtUser);
                           edtPass = (EditText) v.findViewById(R.id.edtPass);
                           edtName = (EditText) v.findViewById(R.id.edtName);
                           edtAge = (EditText) v.findViewById(R.id.edtAge);
                           edtAddress = (EditText) v.findViewById(R.id.edtAddress);
                           btnRegister = (Button) v.findViewById(R.id.btnRegister);

                           tvTitle.setText("Update");
                           btnRegister.setText("Update");

                           edtUser.setText(userList.get(position).username);
                           edtPass.setText(userList.get(position).password);
                           edtName.setText(userList.get(position).name);
                           edtAge.setText(userList.get(position).age+"");
                           edtAddress.setText(userList.get(position).address);


                           btnRegister.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   api.updateUser(userList.get(position).id,edtUser.getText().toString(),
                                           edtPass.getText().toString(),
                                           edtName.getText().toString(),
                                           Integer.parseInt(edtAge.getText().toString()),
                                           edtAddress.getText().toString()).enqueue(new Callback<serverResponse>() {
                                       @Override
                                       public void onResponse(Call<serverResponse> call, Response<serverResponse> response) {
                                           if(response.body().success){
                                               Toast.makeText(MainActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                               startActivity(new Intent(MainActivity.this,MainActivity.class));
                                           }else {
                                               Toast.makeText(MainActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                                           }
                                       }

                                       @Override
                                       public void onFailure(Call<serverResponse> call, Throwable t) {
                                           Log.e("onFailure: ",t.getMessage());
                                       }
                                   });
                               }
                           });
                           alertDialog.show();


                       }
                   });
                   rvList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                   rvList.setAdapter(userAdapter);
                } else {
                    Toast.makeText(MainActivity.this, response.body().message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<serverResponse> call, Throwable t) {
                Log.e( "onFailure: ",t.getMessage() );
            }
        });


    }

    private void initView() {

        rvList = (RecyclerView) findViewById(R.id.rvList);
    }
}