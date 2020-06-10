package com.example.quanlyquancf;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlyquancf.Lop.LoginAccount;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent s = new Intent(getApplicationContext(),LoginAccount.class);
        startActivity(s);
        //setContentView(R.layout.activity_login_account);


        }

    }

