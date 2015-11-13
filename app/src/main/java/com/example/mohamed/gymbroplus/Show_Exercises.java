package com.example.mohamed.gymbroplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Show_Exercises extends AppCompatActivity {
    DatabaseHandler db;
    List<Exercise> Exercises = new ArrayList<Exercise>();
    ArrayAdapter<Exercise> exerciseAdapter;
    ListView exerciseListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__exercises);
        setTitle("Show Exercises");

        exerciseListView = (ListView) findViewById(R.id.listViewExercise);

    }

    private class ExerciseListAdapter extends ArrayAdapter<Exercise> {
        public ExerciseListAdapter() {
            super (Show_Exercises.this, R.layout.show_exercise, Exercises);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null)
                view = getLayoutInflater().inflate(R.layout.show_exercise, parent, false);

            Exercise currentExercise = Exercises.get(position);

            TextView name = (TextView) view.findViewById(R.id.textViewExerciseName);
            name.setText(currentExercise.getExerciseName());

            return view;
        }
    }

    private void populateList() {
        exerciseAdapter = new ExerciseListAdapter();
        exerciseListView.setAdapter(exerciseAdapter);
    }

    public void showExercises(View view){
        db = new DatabaseHandler(getApplicationContext());

        Exercises.addAll(db.getAllExercises());

        for (Exercise exercise : Exercises) {
            db.getAllRepsByExercise(exercise.toString());

        }
       // exerciseAdapter.notifyDataSetChanged();
        populateList();
    }

}
