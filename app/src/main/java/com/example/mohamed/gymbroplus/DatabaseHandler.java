package com.example.mohamed.gymbroplus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed on 5-11-2015.
 * DatabaseHandler
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // database version
    private static final int DATABASE_VERSION = 1;

    // database name
    protected static final String DATABASE_NAME     = "GymBro";

    // table names
    private static final String TABLE_Exercise          = "Exercise";
    private static final String TABLE_Rep               = "Rep";
    private static final String TABLE_Blueprint         = "Blueprint";
    private static final String TABLE_BlueprintDay      = "BlueprintDay";
    private static final String TABLE_DayExercise       = "DayExercise";


    // Common column names
    private static final String KEY_exerciseId      = "exerciseId";
    private static final String KEY_repId           = "repId";
    private static final String KEY_blueprintId     = "blueprintId";
    private static final String KEY_bpdayId         = "bpdayId";

    // table Exercise column names
    private static final String KEY_exerciseName    = "exerciseName";
    private static final String KEY_groupName       = "groupName";

    // table Rep column names
    private static final String KEY_repAmount       = "repAmount";

    // table Blueprint column names
    private static final String KEY_blueprintName   = "blueprintName";

    // table BlueprintDay column names
    private static final String KEY_bpdayNumber     = "bpdayNumber";

    // table DayExercise column names
    private static final String KEY_dayExId         = "dayExId";
    private static final String KEY_orderNumber     = "orderNumber";

    // Table Create Statements
    // Exercise table create statement
    private static final String CREATE_TABLE_Exercise =
            "CREATE TABLE " + TABLE_Exercise + "(" +
                    KEY_exerciseId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_groupName + " TEXT," +
                    KEY_exerciseName + " TEXT" + ");";

    // Rep table create statement
    private static final String CREATE_TABLE_Rep =
            "CREATE TABLE " + TABLE_Rep + "(" +
                    KEY_repId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_exerciseId + " INTEGER," +
                    KEY_repAmount + " INTEGER," +
                    "FOREIGN KEY(" + KEY_exerciseId + ") " +
                    "REFERENCES " + TABLE_Exercise + "(" + KEY_exerciseId + ") " + ");";

    // Blueprint table create statement
    private static final String CREATE_TABLE_Blueprint =
            "CREATE TABLE " + TABLE_Blueprint + "(" +
                    KEY_blueprintId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_blueprintName + " TEXT" + ");";

    // BlueprintDay table create statement
    private static final String CREATE_TABLE_BlueprintDay =
            "CREATE TABLE " + TABLE_BlueprintDay + "(" +
                    KEY_bpdayId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_blueprintId + " INTEGER," +
                    KEY_bpdayNumber + " INTEGER," +
                    "FOREIGN KEY(" + KEY_blueprintId + ") " +
                    "REFERENCES " + TABLE_Blueprint + "(" + KEY_blueprintId + ") " + ");";

    // DayExercise table create statement
    private static final String CREATE_TABLE_DayExercise =
            "CREATE TABLE " + TABLE_DayExercise + "(" +
                    KEY_dayExId + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    KEY_bpdayId + " INTEGER," +
                    KEY_orderNumber + " INTEGER," +
                    KEY_exerciseId + " INTEGER," +
                    "FOREIGN KEY(" + KEY_bpdayId + ") " +
                    "REFERENCES " + TABLE_BlueprintDay + "(" + KEY_bpdayId + "), " +
                    "FOREIGN KEY(" + KEY_exerciseId + ") " +
                    "REFERENCES " + TABLE_Exercise + "(" + KEY_exerciseId + ") " + ");";

    // constructor
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_Exercise);
        db.execSQL(CREATE_TABLE_Rep);
        db.execSQL(CREATE_TABLE_Blueprint);
        db.execSQL(CREATE_TABLE_BlueprintDay);
        db.execSQL(CREATE_TABLE_DayExercise);
    }

    // When upgrading the database, it will drop the current table and recreate.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Exercise);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Rep);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Blueprint);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BlueprintDay);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DayExercise);

        onCreate(db);
    }

    /**
     * Exercise
     */

    //Create Exercise
    public int createExercise(Exercise exercise){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_exerciseName, exercise.getExerciseName());
        values.put(KEY_groupName, exercise.getGroupName());

        // insert row
        long exercise_id = db.insert(TABLE_Exercise, null, values);

        return (int)exercise_id;
    }

    //GetAll Exercise
    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<Exercise>();
        String selectQuery = "SELECT  * FROM " + TABLE_Exercise+" ORDER BY "+KEY_exerciseName+" ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Exercise t = new Exercise();
                t.setExerciseId(c.getInt((c.getColumnIndex(KEY_exerciseId))));
                t.setExerciseName(c.getString(c.getColumnIndex(KEY_exerciseName)));
                t.setGroupName(c.getString(c.getColumnIndex(KEY_groupName)));

                // adding to exercise list
                exercises.add(t);
            } while (c.moveToNext());
        }
        return exercises;
    }

    //Update Exercise
    public int updateExercise(Exercise exercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_exerciseName, exercise.getExerciseName());
        values.put(KEY_groupName, exercise.getGroupName());

        // updating row
        return db.update(TABLE_Exercise, values, KEY_exerciseId + " = ?",
                new String[] { String.valueOf(exercise.getExerciseId()) });
    }

    //Delete Exercise (will delete reps first)
    public void deleteExercise(Exercise exercise, boolean should_delete_all_exercise_reps) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting exercise
        // check if reps under this exercise should also be deleted
        if (should_delete_all_exercise_reps) {
            // get all reps under this exercise
            List<Rep> allexercisereps = getAllRepsByExercise(exercise.getExerciseId());

            // delete all reps
            for (Rep rep : allexercisereps) {
                // delete rep
                deleteRep(rep.getRepId());
            }
        }

        // now delete the exercise
        db.delete(TABLE_Exercise, KEY_exerciseId + " = ?",
                new String[]{String.valueOf(exercise.getExerciseId())});
    }

    /**
     * Rep
     */

    //Create Rep
    public long createRep(Rep rep) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_exerciseId, rep.getExerciseId());
        values.put(KEY_repAmount, rep.getRepAmount());

        // insert row
        long rep_id = db.insert(TABLE_Rep, null, values);

        return rep_id;
    }

    //Get Rep
    public Rep getRep(long rep_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_Rep + " WHERE "
                + KEY_repId + " = " + rep_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Rep td = new Rep();
        td.setRepId(c.getInt(c.getColumnIndex(KEY_repId)));
        td.setRepAmount((c.getInt(c.getColumnIndex(KEY_repAmount))));

        return td;
    }

    //Get All Reps
    public List<Rep> getAllReps() {
        List<Rep> reps = new ArrayList<Rep>();
        String selectQuery = "SELECT  * FROM " + TABLE_Rep;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Rep td = new Rep();
                td.setRepId(c.getInt(c.getColumnIndex(KEY_repId)));
                td.setRepAmount((c.getInt(c.getColumnIndex(KEY_repAmount))));

                // adding to rep list
                reps.add(td);
            } while (c.moveToNext());
        }

        return reps;
    }

    //Get All Reps from Exercise
    public List<Rep> getAllRepsByExercise(int exercise_id) {
        List<Rep> reps = new ArrayList<Rep>();

        String selectQuery = "SELECT * FROM "+ TABLE_Rep +" r INNER JOIN "+ TABLE_Exercise +" e ON e."+ KEY_exerciseId +" = r."+KEY_exerciseId+" WHERE e."+ KEY_exerciseId + " = " + exercise_id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Rep td = new Rep();
                td.setRepId(c.getInt(c.getColumnIndex(KEY_repId)));
                td.setExerciseId(c.getInt(c.getColumnIndex(KEY_exerciseId)));
                td.setRepAmount((c.getInt(c.getColumnIndex(KEY_repAmount))));

                // adding to rep list
                reps.add(td);
            } while (c.moveToNext());
        }

        return reps;
    }

    //Update Rep
    public int updateRep(Rep rep) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_repAmount, rep.getRepAmount());

        // updating row
        return db.update(TABLE_Rep, values, KEY_repId + " = ?",
                new String[] { String.valueOf(rep.getRepId()) });
    }

    //Delete Rep
    public void deleteRep(long rep_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_Rep, KEY_repId + " = ?",
                new String[]{String.valueOf(rep_id)});
    }

    /**
     * Blueprint
     */

    //Create Blueprint
    public int createBlueprint(Blueprint blueprint){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_blueprintName, blueprint.getBlueprintName());

        // insert row
        long blueprint_id = db.insert(TABLE_Blueprint, null, values);

        return (int)blueprint_id;
    }

    //Get Blueprint
    public Blueprint getBlueprint(long blueprintday_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_Blueprint + " WHERE "
                + KEY_blueprintId + " = " + blueprintday_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Blueprint td = new Blueprint();
        td.setBlueprintId(c.getInt((c.getColumnIndex(KEY_blueprintId))));
        td.setBlueprintName(c.getString(c.getColumnIndex(KEY_blueprintName)));

        return td;
    }

    //GetAll Blueprint
    public List<Blueprint> getAllBlueprints() {
        List<Blueprint> blueprints = new ArrayList<Blueprint>();
        String selectQuery = "SELECT  * FROM " + TABLE_Blueprint+" ORDER BY "+KEY_blueprintName+" ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Blueprint t = new Blueprint();
                t.setBlueprintId(c.getInt((c.getColumnIndex(KEY_blueprintId))));
                t.setBlueprintName(c.getString(c.getColumnIndex(KEY_blueprintName)));

                // adding to exercise list
                blueprints.add(t);
            } while (c.moveToNext());
        }
        return blueprints;
    }

    //Update Blueprint
    public int updateBlueprint(Blueprint blueprint) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TABLE_Blueprint, blueprint.getBlueprintName());

        // updating row
        return db.update(TABLE_Blueprint, values, KEY_blueprintId + " = ?",
                new String[] { String.valueOf(blueprint.getBlueprintId()) });
    }

    //Delete Blueprint (will delete days first)
    public void deleteBlueprint(Blueprint blueprint, boolean should_delete_all_exercise_reps) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting exercise
        // check if reps under this exercise should also be deleted
        if (should_delete_all_exercise_reps) {
            // get all days under this exercise
            List<BlueprintDay> allblueprintdays = getAllBlueprintDaysByBlueprint(blueprint.getBlueprintId());

            // delete all days
            for (BlueprintDay day : allblueprintdays) {
                // delete rep
                deleteRep(day.getBlueprintDayId());
            }
        }

        // now delete the exercise
        db.delete(TABLE_Blueprint, KEY_blueprintId + " = ?",
                new String[] { String.valueOf(blueprint.getBlueprintId()) });
    }

    /**
     * BlueprintDay
     */

    //Create BlueprintDay
    public long createBlueprintDay(BlueprintDay blueprintday) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_blueprintId, blueprintday.getBlueprintId());
        values.put(KEY_bpdayNumber, blueprintday.getBlueprintDayNumber());

        // insert row
        long blueprintday_id = db.insert(TABLE_BlueprintDay, null, values);

        return blueprintday_id;
    }

    //Get BlueprintDay
    public BlueprintDay getBlueprintDay(long blueprintday_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_BlueprintDay + " WHERE "
                + KEY_bpdayId + " = " + blueprintday_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        BlueprintDay td = new BlueprintDay();
        td.setBlueprintDayId(c.getInt(c.getColumnIndex(KEY_bpdayId)));
        td.setBlueprintDayNumber((c.getInt(c.getColumnIndex(KEY_bpdayNumber))));

        return td;
    }

    //Get All BlueprintDays
    public List<BlueprintDay> getAllBlueprintDays() {
        List<BlueprintDay> blueprintdays = new ArrayList<BlueprintDay>();
        String selectQuery = "SELECT  * FROM " + TABLE_BlueprintDay;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                BlueprintDay td = new BlueprintDay();
                td.setBlueprintDayId(c.getInt(c.getColumnIndex(KEY_bpdayId)));
                td.setBlueprintDayNumber((c.getInt(c.getColumnIndex(KEY_bpdayNumber))));

                // adding to rep list
                blueprintdays.add(td);
            } while (c.moveToNext());
        }

        return blueprintdays;
    }

    //Get All Days from Blueprint
    public List<BlueprintDay> getAllBlueprintDaysByBlueprint(int blueprint_id) {
        List<BlueprintDay> blueprintdays = new ArrayList<BlueprintDay>();

        String selectQuery = "SELECT * FROM "+ TABLE_BlueprintDay +" r INNER JOIN "+ TABLE_Blueprint +" e ON e."+ KEY_blueprintId +" = r."+KEY_blueprintId+" WHERE e."+ KEY_blueprintId + " = " + blueprint_id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                BlueprintDay td = new BlueprintDay();
                td.setBlueprintDayId(c.getInt(c.getColumnIndex(KEY_bpdayId)));
                td.setBlueprintId(c.getInt(c.getColumnIndex(KEY_blueprintId)));
                td.setBlueprintDayNumber((c.getInt(c.getColumnIndex(KEY_bpdayNumber))));

                // adding to rep list
                blueprintdays.add(td);
            } while (c.moveToNext());
        }

        return blueprintdays;
    }

    //Update BlueprintDay
    public int updateBlueprintDay(BlueprintDay blueprintday) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_bpdayNumber, blueprintday.getBlueprintDayNumber());

        // updating row
        return db.update(TABLE_BlueprintDay, values, KEY_bpdayId + " = ?",
                new String[] { String.valueOf(blueprintday.getBlueprintDayId()) });
    }

    //Delete BlueprintDay
    public void deleteBlueprintDay(long blueprintday_id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_BlueprintDay, KEY_bpdayId + " = ?",
                new String[]{String.valueOf(blueprintday_id)});
    }

    /**
     * DayExercise
     */

    //Create DayExercise
    public long createDayExercise(DayExercise dayexercise) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_bpdayId, dayexercise.getBpDayId());
        values.put(KEY_orderNumber, dayexercise.getOrdernumber());
        values.put(KEY_exerciseId, dayexercise.getExersieId());

        // insert row
        long dayexercise_id = db.insert(TABLE_DayExercise, null, values);

        return dayexercise_id;
    }

    //Get DayExercise

    //Get AllDayExercisesByBlueprintDay
    public List<DayExercise> getAllDayExercisesByBlueprintDay(int blueprintday_id) {
        List<DayExercise> dayexercises = new ArrayList<DayExercise>();

        String selectQuery = "SELECT * FROM "+ TABLE_DayExercise +" r INNER JOIN "+ TABLE_BlueprintDay +" e ON e."+ KEY_bpdayId +" = r."+KEY_bpdayId+" WHERE e."+ KEY_bpdayId + " = " + blueprintday_id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DayExercise td = new DayExercise();
                td.setDayExId(c.getInt(c.getColumnIndex(KEY_dayExId)));
                td.setBpDayId(c.getInt(c.getColumnIndex(KEY_bpdayId)));
                td.setOrdernumber((c.getInt(c.getColumnIndex(KEY_orderNumber))));
                td.setExersieId((c.getInt(c.getColumnIndex(KEY_exerciseId))));

                // adding to rep list
                dayexercises.add(td);
            } while (c.moveToNext());
        }

        return dayexercises;
    }




    //get countexday
    public int countExDayById(int blueprintday_id){
        int count;
        String selectQuery = "SELECT COUNT("+ KEY_bpdayId +") FROM "+ TABLE_DayExercise +" WHERE "+ KEY_bpdayId + " = " + blueprintday_id+" GROUP BY "+KEY_bpdayId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        count = c.getInt(0);

        return count;
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
