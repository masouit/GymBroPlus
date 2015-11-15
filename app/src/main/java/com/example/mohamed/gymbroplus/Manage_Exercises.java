package com.example.mohamed.gymbroplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Manage_Exercises extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__exercises);
    }

    public void menuAdd_Exercise(View view) {
        Intent intent = new Intent(this, Add_Exercise.class);
        startActivity(intent);
    }
    public void menuShow_Exercises(View view) {
        Intent intent = new Intent(this, Show_Exercises.class);
        startActivity(intent);
    }
}
