package com.example.mohamed.gymbroplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    /** Called when the user clicks the Send button */


    public void menuManage_Exercises(View view) {
        Intent intent = new Intent(this, Manage_Exercises.class);
        startActivity(intent);
    }
    public void menuManage_Blueprints(View view) {
        Intent intent = new Intent(this, Manage_Blueprints.class);
        startActivity(intent);
    }
}
