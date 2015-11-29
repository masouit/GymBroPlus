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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Edit_Exercise extends AppCompatActivity implements OnCheckedChangeListener {
    private LinearLayout mLayout;
    //int exerciselistId;
    private int repCount = 0;
    DatabaseHandler db;
    EditText exercisename,repamount;
    EditText edittextreps;
    List<Integer> repidlist = new ArrayList<Integer>();
    Exercise exercises;
    //ArrayList<Exercise> exercises;
    List<Rep> reps = new ArrayList<Rep>();
    RadioButton cbChest,cbBack,cbShoulder,cbAbs,cbLeg,cbArms;
    String groupname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__exercise);
        setTitle("Edit exercise");

        //Get objects from other intent
        Intent intent = this.getIntent();
        exercises = (Exercise) intent.getParcelableExtra("exerciselist");
        cbChest     = (RadioButton) findViewById(R.id.imageViewChest);
        cbBack      = (RadioButton) findViewById(R.id.imageViewBack);
        cbShoulder  = (RadioButton) findViewById(R.id.imageViewShoulder);
        cbAbs       = (RadioButton) findViewById(R.id.imageViewAbs);
        cbLeg       = (RadioButton) findViewById(R.id.imageViewLegs);
        cbArms      = (RadioButton) findViewById(R.id.imageViewArms);

        cbChest.setOnCheckedChangeListener(this);
        cbBack.setOnCheckedChangeListener(this);
        cbShoulder.setOnCheckedChangeListener(this);
        cbAbs.setOnCheckedChangeListener(this);
        cbLeg.setOnCheckedChangeListener(this);
        cbArms.setOnCheckedChangeListener(this);

        setGroupname(exercises.getGroupName());

        mLayout = (LinearLayout) findViewById(R.id.linearLayoutEdit);
        addRepTextview(mLayout);
    }

    public void addRepTextview(View view){
        db = new DatabaseHandler(getApplicationContext());
        reps.addAll(db.getAllRepsByExercise(exercises.getExerciseId()));
        db.closeDB();

        exercisename = (EditText) findViewById(R.id.editTextEditExercise);
        exercisename.setText(exercises.getExerciseName());
        exercisename.setSelectAllOnFocus(true);

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
            editTextRep.setSelectAllOnFocus(true);
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
                    delRep(addView, buttonRemove);
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
        exercisename.requestFocus();
    }
    //TODO delete rep
    private void delRep(View addView,Button buttonRemove) {
        ((LinearLayout) addView.getParent()).removeView(addView);

        repCount--;
        if(repCount>0){
            int id_ = buttonRemove.getId()-1;
            Button btn = ((Button) findViewById(id_));
            //Toast.makeText(getApplicationContext(), "added button"+id_, Toast.LENGTH_SHORT).show();
            btn.setVisibility(View.VISIBLE);
            int id_edittext = repCount + 100;
            EditText editTextRep = (EditText) findViewById(id_edittext);
            db.deleteRep(reps.get(repCount).getRepId());
            //editTextRep.requestFocus();
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
        //exercisename = (EditText)findViewById(R.id.editTextEditExercise);
        Exercise exercise = new Exercise(exercisename.getText().toString(),groupname);
        int exId = exercises.getExerciseId();
        //set exerciseid
        exercise.setExerciseId(exId);

        // update exercise in db
        db.updateExercise(exercise);

        for(int i=0; i<repidlist.size(); i++) {
            String edittextid = String.valueOf(repidlist.get(i));
            int repid = getResources().getIdentifier(edittextid, "id", getPackageName());
            edittextreps = ((EditText) findViewById(repid));
            //Toast.makeText(getApplicationContext(), "added edittext "+String.valueOf(repid), Toast.LENGTH_SHORT).show();
            // Inserting reps in db
            Rep rep = new Rep(exId,Integer.parseInt(edittextreps.getText().toString()));
            int replistid = reps.get(i).getRepId();
            if (replistid != 0){
                rep.setRepId(replistid);
                long rep1_id = db.updateRep(rep);
                Log.i("database", "added name: " + exercisename.getText().toString() + " exid: " + exId + " and repamount: " + rep.getRepAmount() + " repid: " + replistid);
            }else{
                //create new rep
                long rep1_id = db.createRep(rep);
                Log.i("database", "added name: " + exercisename.getText().toString() + " exid: " + exId + " and repamount: " + rep.getRepAmount() + " repid: " + rep1_id);
            }

            //Toast.makeText(getApplicationContext(), "added "+exercisename.getText().toString()+exercise_id+" and rep "+edittextreps.getText().toString()+ rep1_id, Toast.LENGTH_SHORT).show();
        }

        db.closeDB();
    }

    public void addNewRepTextview(View view){
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
            reps.add((repCount-1),new Rep(exercises.getExerciseId(),0));
            //Toast.makeText(getApplicationContext(), "edittextrepid "+edittextrepid, Toast.LENGTH_SHORT).show();

            final Button buttonRemove = (Button) addView.findViewById(R.id.remove);
            buttonRemove.setId(id_button);
            buttonRemove.setText("-");

            buttonRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delNewRep(addView, buttonRemove);
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
        else{
            Toast.makeText(getApplicationContext(), "Cannot add more then 100 reps", Toast.LENGTH_SHORT).show();
        }
    }
    private void delNewRep(View addView,Button buttonRemove) {
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
            reps.remove(repCount);
        }
    }
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == cbAbs) {
            if (isChecked) {
                groupname="Abs";

            } else {
            }
        }else
        if (buttonView == cbArms) {
            if (isChecked) {
                groupname="Arms";
            } else {
            }
        }else
        if (buttonView == cbBack) {
            if (isChecked) {
                groupname="Back";
            } else {
            }
        }else
        if (buttonView == cbChest) {
            if (isChecked) {
                groupname="Chest";
            } else {
            }
        }else
        if (buttonView == cbLeg) {
            if (isChecked) {
                groupname="Legs";
            } else {
            }
        }else
        if (buttonView == cbShoulder) {
            if (isChecked) {
                groupname="Shoulders";
            } else {
            }
        }
    }

    public void setGroupname(String _groupname){
        switch(_groupname) {
            case "Abs":
                cbAbs.setChecked(true);
                break;
            case "Arms":
                cbArms.setChecked(true);
                break;
            case "Back":
                cbBack.setChecked(true);
                break;
            case "Chest":
                cbChest.setChecked(true);
                break;
            case "Legs":
                cbLeg.setChecked(true);
                break;
            case "Shoulders":
                cbShoulder.setChecked(true);
                break;
        }
    }
}
