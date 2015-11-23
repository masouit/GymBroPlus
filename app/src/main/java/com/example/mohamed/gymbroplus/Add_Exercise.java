package com.example.mohamed.gymbroplus;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Add_Exercise extends AppCompatActivity {
    private LinearLayout mLayout;
    private int repCount = 0;
    // Database Handler
    DatabaseHandler db;
    AutoCompleteTextView exercisename;
    EditText repamount;
    EditText edittextreps;
    String[] exercisearray;

    List<Exercise> exercises = new ArrayList<Exercise>();
    List<Integer> repidlist = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__exercise);
        mLayout = (LinearLayout) findViewById(R.id.linearLayout1);
        setTitle("Add exercise");

        exercisename = (AutoCompleteTextView)findViewById(R.id.editTextExercisename);
        autoCompleteExercise();
    }

    public void autoCompleteExercise(){
        db = new DatabaseHandler(getApplicationContext());
        exercises.addAll(db.getAllExercises());
        db.closeDB();

        exercisearray = new String[exercises.size()];
        int index = 0;
        for (Exercise value : exercises) {
            exercisearray[index] = (String) value.getExerciseName();
            index++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,exercisearray);
        exercisename.setAdapter(adapter);

    }

    public void addExercise(View view){
        if(Arrays.asList(exercisearray).contains(exercisename.getText().toString())){
            Toast.makeText(getApplicationContext(), "name already exist ", Toast.LENGTH_SHORT).show();
            return;
        } else
        {
            db = new DatabaseHandler(getApplicationContext());
            //exercisename = (AutoCompleteTextView)findViewById(R.id.editTextExercisename);

            // Creating exercise
            Exercise exercise = new Exercise(exercisename.getText().toString());

            // Inserting exercise in db
            int exercise_id = db.createExercise(exercise);

            for(int i=0; i<repidlist.size(); i++) {
                String edittextid = String.valueOf(repidlist.get(i));
                int repid = getResources().getIdentifier(edittextid, "id", getPackageName());
                edittextreps = ((EditText) findViewById(repid));
                //Toast.makeText(getApplicationContext(), "added edittext "+String.valueOf(repid), Toast.LENGTH_SHORT).show();
                // Inserting reps in db
                Rep rep = new Rep(exercise_id,Integer.parseInt(edittextreps.getText().toString()));
                long rep1_id = db.createRep(rep);
                Log.i("database","added name: "+exercisename.getText().toString()+" exid: "+exercise_id+" and repamount: "+rep.getRepAmount()+" repid: "+ rep1_id);
                //Toast.makeText(getApplicationContext(), "added "+exercisename.getText().toString()+exercise_id+" and rep "+edittextreps.getText().toString()+ rep1_id, Toast.LENGTH_SHORT).show();
            }

            db.closeDB();
            Toast.makeText(getApplicationContext(), exercisename.getText().toString()+" is added.", Toast.LENGTH_SHORT).show();
        }
    }

    public void addRepTextview(View view){
        if(repCount<100) {
            repCount++;
            int id_button = repCount;
            int id_edittext = repCount + 100;

            final LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.rep_row, null);
            TextView viewTextRep = (TextView) addView.findViewById(R.id.textViewRep);
            final EditText editTextRep = (EditText) addView.findViewById(R.id.editTextRep);
            viewTextRep.append(String.valueOf(repCount));
            editTextRep.setId(id_edittext);
            mLayout.addView(addView);

            int edittextrepid = editTextRep.getId();
            repidlist.add(edittextrepid);
            //Toast.makeText(getApplicationContext(), "edittextrepid "+edittextrepid, Toast.LENGTH_SHORT).show();

            final Button buttonRemove = (Button) addView.findViewById(R.id.remove);
            buttonRemove.setId(id_button);
            buttonRemove.setText("-");

            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delViewButton(addView, buttonRemove);
                    editTextRep.requestFocus();
                }
            });

            if (repCount > 1) {
                int id_ = buttonRemove.getId() - 1;
                Button btn = ((Button) findViewById(id_));
                //Toast.makeText(getApplicationContext(), "added button" + id_, Toast.LENGTH_SHORT).show();
                btn.setVisibility(View.INVISIBLE);
            }
            final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollView));
            scrollview.post(new Runnable() {
                @Override
                public void run() {
                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    editTextRep.requestFocus();
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(), "Cannot add more then 100 reps", Toast.LENGTH_SHORT).show();
        }
    }

    private void delViewButton(View addView,Button buttonRemove) {
        ((LinearLayout) addView.getParent()).removeView(addView);

        repCount--;
        if(repCount>0){
            int id_ = buttonRemove.getId()-1;
            Button btn = ((Button) findViewById(id_));
            //Toast.makeText(getApplicationContext(), "added button"+id_, Toast.LENGTH_SHORT).show();
            btn.setVisibility(View.VISIBLE);
            int id_edittext = repCount + 100;
            EditText editTextRep = (EditText) findViewById(id_edittext);
            editTextRep.requestFocus();
        }
    }

    private TextView createNewTextView(String text) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText("Rep" + text);
        return textView;
    }
}
