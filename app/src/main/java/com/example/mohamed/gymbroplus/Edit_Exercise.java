package com.example.mohamed.gymbroplus;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Edit_Exercise extends AppCompatActivity {
    private LinearLayout mLayout;
    int exerciselistId;
    private int repCount = 0;
    DatabaseHandler db;
    EditText exercisename,repamount;
    EditText edittextreps;
    List<Integer> repidlist = new ArrayList<Integer>();
    ArrayList<Exercise> exercises;
    List<Rep> reps = new ArrayList<Rep>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__exercise);

        mLayout = (LinearLayout) findViewById(R.id.linearLayoutEdit);
        setTitle("Edit exercise");

        //Get objects from other intent
        Intent intent = this.getIntent();
        exercises = (ArrayList<Exercise>) getIntent().getSerializableExtra("exerciselist");
        exerciselistId = getIntent().getIntExtra("exerciseId",0);

        EditText editTextRep = (EditText) findViewById(R.id.editTextEditExercise);
        //exercises.size();
        editTextRep.setText(exercises.get(exerciselistId).getExerciseName());
        addRepTextview(mLayout);
    }

    public void addRepTextview(View view){
        db = new DatabaseHandler(getApplicationContext());
        reps.addAll(db.getAllRepsByExercise(exercises.get(exerciselistId).getExerciseName()));
        db.closeDB();


        for (int i=0;i<reps.size();i++) {
            repCount++;
            int id_button = repCount;
            int id_edittext = repCount + 100;

            final LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View addView = layoutInflater.inflate(R.layout.rep_row, null);
            TextView viewTextRep = (TextView) addView.findViewById(R.id.textViewRep);
            final EditText editTextRep = (EditText) addView.findViewById(R.id.editTextRep);
            viewTextRep.append(String.valueOf(repCount));
            editTextRep.setId(id_edittext);
            editTextRep.setText(String.valueOf(reps.get(i).getRepAmount()));
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
            final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollView2));
            scrollview.post(new Runnable() {
                @Override
                public void run() {
                    scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                    editTextRep.requestFocus();
                }
            });
        }
    }
    //TODO
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

    public void saveExercise(View view){
        db = new DatabaseHandler(getApplicationContext());
        //EditText editTextRep = (EditText) findViewById(R.id.editTextEditExercise);
        exercisename = (EditText)findViewById(R.id.editTextEditExercise);
        Exercise exercise = new Exercise(exercisename.getText().toString());
        int exId = exercises.get(exerciselistId).getExerciseId();
        //set exerciseid
        exercise.setExerciseId(exId);

        // update exercise in db
        int exercise_id = db.updateExercise(exercise);

        for(int i=0; i<repidlist.size(); i++) {
            String edittextid = String.valueOf(repidlist.get(i));
            int repid = getResources().getIdentifier(edittextid, "id", getPackageName());
            edittextreps = ((EditText) findViewById(repid));
            //Toast.makeText(getApplicationContext(), "added edittext "+String.valueOf(repid), Toast.LENGTH_SHORT).show();
            // Inserting reps in db
            Rep rep = new Rep(exercise_id,Integer.parseInt(edittextreps.getText().toString()));
            int replistid = reps.get(i).getRepId();
            rep.setRepId(replistid);
            long rep1_id = db.updateRep(rep);
            Log.i("database", "added name: " + exercisename.getText().toString() + " exid: " + exercise_id + " and repamount: " + rep.getRepAmount() + " repid: " + replistid);
            //Toast.makeText(getApplicationContext(), "added "+exercisename.getText().toString()+exercise_id+" and rep "+edittextreps.getText().toString()+ rep1_id, Toast.LENGTH_SHORT).show();
        }

        db.closeDB();
    }

}
