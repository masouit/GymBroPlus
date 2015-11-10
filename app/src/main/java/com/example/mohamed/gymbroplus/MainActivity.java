package com.example.mohamed.gymbroplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText weightTxt1;
    TextView exercisenameTxt, clientnameTxt, dayTxt, setTxt1, repsTxt1;

    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        exercisenameTxt = (TextView) findViewById(R.id.textViewExercise);
        clientnameTxt = (TextView) findViewById(R.id.textViewClientName);
        dayTxt = (TextView) findViewById(R.id.textViewDay);
        setTxt1 = (TextView) findViewById(R.id.textViewSet1);
        repsTxt1 = (TextView) findViewById(R.id.textViewReps1);
        weightTxt1 = (EditText) findViewById(R.id.editTextWeightSet1);

        final Button addBtn = (Button) findViewById(R.id.buttonSave);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Data has been saved",Toast.LENGTH_SHORT).show();
            }
        });
        */
        dbHandler = new DatabaseHandler(getApplicationContext());


    }
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, Add_Exercise.class);
        startActivity(intent);
    }
}
