package com.example.mohamed.gymbroplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Manage_Blueprints extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__blueprints);
        setTitle("Manage blueprints");
    }

    public void menuAdd_Blueprint(View view) {
        Intent intent = new Intent(this, Add_Blueprint.class);
        startActivity(intent);
    }
    public void menuShow_Blueprints(View view) {
        Intent intent = new Intent(this, Show_Blueprints.class);
        startActivity(intent);
    }
}
