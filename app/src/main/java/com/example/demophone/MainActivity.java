package com.example.demophone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demophone.API.APIclient;
import com.example.demophone.API.LoginRequest;
import com.example.demophone.API.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static retrofit2.converter.gson.GsonConverterFactory.create;

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button btn_SignIN, btn_SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        email = findViewById(R.id.editTextTextPersonName);
        password = findViewById(R.id.editTextTextPersonName2);
        btn_SignIN = findViewById(R.id.button);
        btn_SignUp = findViewById(R.id.button2);

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        btn_SignIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    ShowDialogWindow("password and email are requaired");
                } else {
                    login();
                }
            }
        });
    }
        public void login(){
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(email.getText().toString());
            loginRequest.setPassword(password.getText().toString());

            Call<LoginResponse> loginResponseCall= APIclient.getUserService().userLogin(loginRequest);
            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if(response.isSuccessful()){
                        Intent intent;
                        intent = new Intent(MainActivity.this, MainActivity2.class);
                        startActivity(intent);
                    }else {
                        ShowDialogWindow("wrong password or email");
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }




    public void ShowDialogWindow(String text){
        final AlertDialog aboutDialog = new AlertDialog.Builder(MainActivity.this).setMessage(text).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).create();
        aboutDialog.show();
    }

}