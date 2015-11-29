package com.example.mohamed.gymbroplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.Toast;

public class Add_Blueprint extends AppCompatActivity {
    EditText blueprintname;
    NumberPicker np;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__blueprint);
        setTitle("Add blueprint");

        blueprintname = (EditText)findViewById(R.id.editTextBlueprintName);
        np = (NumberPicker) findViewById(R.id.numberPickerDaysBlueprint);

        np.setMinValue(1);
        np.setMaxValue(100);
    }

    public void addBlueprint(View view){

        if(TextUtils.isEmpty(blueprintname.getText().toString().trim())) {
            blueprintname.setError("Please enter a blueprint name.");
            return;
        }
        else{
            db = new DatabaseHandler(getApplicationContext());
            int daysamount=np.getValue();

            // Creating exercise
            Blueprint blueprint = new Blueprint(blueprintname.getText().toString());

            // Inserting exercise in db
            int blueprint_id = db.createBlueprint(blueprint);

            for(int i=0; i<daysamount; i++) {
                // Inserting days in db
                BlueprintDay bpday = new BlueprintDay(blueprint_id,(i+1));
                long bpday_id = db.createBlueprintDay(bpday);
                Log.i("database", "added name: " + blueprintname.getText().toString() + " bpid: " + blueprint_id + " and day: " + (i+1) + " repid: " + bpday_id);
            }
            Blueprint bp = db.getBlueprint(blueprint_id);
            db.closeDB();
            Toast.makeText(getApplicationContext(), blueprintname.getText().toString() + " is added.", Toast.LENGTH_SHORT).show();
            if(blueprint!= null){
                Intent intent = new Intent(this, Edit_Blueprint.class);
                intent.putExtra("blueprint", bp);
                startActivity(intent);
            }
        }
    }

}
