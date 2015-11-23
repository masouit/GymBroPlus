package com.example.mohamed.gymbroplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Edit_day extends AppCompatActivity {
    DatabaseHandler db;
    ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    List<Rep> reps = new ArrayList<Rep>();

    ExpandableListExerciseAdapter listAdapter;
    ExpandableListView expListViewExercises,expListViewData;
    List<Exercise> listDataExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_day);
        setTitle("Edit day");

        // get the listview
        expListViewExercises = (ExpandableListView) findViewById(R.id.expandableListViewExercises);
        expListViewData= (ExpandableListView) findViewById(R.id.expandableListViewDayData);

        // preparing list data
        prepareListData();

        // Check for ExpandableListAdapter object
        if (listAdapter == null)
        {
            //Create ExpandableListAdapter Object
            listAdapter = new ExpandableListExerciseAdapter(listDataExercise, this);

            // Set Adapter to ExpandableList Adapter
            expListViewExercises.setAdapter(listAdapter);
            expListViewData.setAdapter(listAdapter);
        }
        else
        {
            // Refresh ExpandableListView data
            listAdapter.notifyDataSetChanged();
        }

    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataExercise = new ArrayList<Exercise>();
        db = new DatabaseHandler(getApplicationContext());
        exercises.addAll(db.getAllExercises());

        for (int i = 0; i < exercises.size(); i++) {
            ArrayList<Rep> list = new ArrayList<Rep>();

            reps.addAll(db.getAllRepsByExercise(exercises.get(i).getExerciseId()));
            for (int j = 0; j < reps.size(); j++){
                list.add(reps.get(j));
            }
            exercises.get(i).setExerciseReps(list);
            reps.clear();
        }
        listDataExercise.addAll(exercises);
        db.closeDB();
    }
}
