package com.example.mohamed.gymbroplus;

/**
 * Created by Mohamed on 5-11-2015.
 * Exercise
 */
public class Exercise {
    private int _exerciseid,_exerciseset,_exercisereps;
    private String _exercisename;

    public Exercise(int id,String exercisename, int set, int reps){
        _exerciseid = id;
        _exercisename = exercisename;
        _exerciseset = set;
        _exercisereps = reps;
    }

    public int getId(){return _exerciseid;}

    public String getExercisename(){return _exercisename;}

    public int getSet(){return _exerciseset;}

    public int getReps(){return _exercisereps;}


}
