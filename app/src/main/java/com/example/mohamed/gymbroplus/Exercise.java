package com.example.mohamed.gymbroplus;

/**
 * Created by Mohamed on 5-11-2015.
 * Exercise
 */
public class Exercise {
    private int _exerciseid;
    private String _exercisename;

    // constructors
    public Exercise() {
    }

    public Exercise(String exercisename){
        _exercisename = exercisename;
    }

    public Exercise(int id,String exercisename){
        _exerciseid = id;
        _exercisename = exercisename;
    }

    // setters
    public void setExerciseId(int id) {
        _exerciseid = id;
    }

    public void setExerciseName(String exercisename) {
        _exercisename = exercisename;
    }

    // getters
    public long getExerciseId() {
        return _exerciseid;
    }

    public String getExerciseName() {
        return _exercisename;
    }
}
