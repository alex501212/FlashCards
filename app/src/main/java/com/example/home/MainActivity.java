package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Home");  // set activity title
    }

    public void review(View v)  // start review activity
    {
        Intent i = new Intent(this, Review.class);
        startActivity(i);
    }

    public void NewCard(View v)  // start NewCard activity
    {
        Intent i = new Intent(this, NewCard.class);
        startActivity(i);
    }
}
