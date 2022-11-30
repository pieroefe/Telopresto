package com.example.telopresto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class WhatIs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.what_is);
    }

    public void irLogin(View view) {
        Intent intent = new Intent(this, Login_principal.class);
        startActivity(intent);
    }


}
