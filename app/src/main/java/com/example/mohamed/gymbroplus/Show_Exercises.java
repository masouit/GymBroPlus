package com.example.mohamed.gymbroplus;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import java.util.ArrayList;
import java.util.List;

public class Show_Exercises extends AppCompatActivity {
    DatabaseHandler db;
    ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    List<Rep> reps = new ArrayList<Rep>();

    ExpandableListExerciseAdapter listAdapter;
    ExpandableListView expListView;
    List<Exercise> listDataHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__exercises);
        setTitle("Show Exercises");

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);


        //set indicator to right
        setGroupIndicatorToRight();

        // preparing list data
        prepareListData();

        // Check for ExpandableListAdapter object
        if (listAdapter == null)
        {
            //Create ExpandableListAdapter Object
            listAdapter = new ExpandableListExerciseAdapter(listDataHeader,this);

            // Set Adapter to ExpandableList Adapter
            expListView.setAdapter(listAdapter);
        }
        else
        {
            // Refresh ExpandableListView data
            listAdapter.notifyDataSetChanged();
        }

        registerForContextMenu(expListView);
        // Listview Group click listener
        expListView.setOnGroupClickListener(new OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : ", Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //Log.i("", "Click");
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) menuInfo;

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);

        // Show context menu for groups
        if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            menu.setHeaderTitle("Group");
            menu.add(0, 0, 1, "Delete");
            menu.add(0,1,2,"Edit");

            // Show context menu for children
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            //menu.setHeaderTitle("Child");
            //menu.add(0, 0, 1, "Delete");
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item
                .getMenuInfo();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        int groupPosition = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int childPosition = ExpandableListView.getPackedPositionChild(info.packedPosition);
        int menuItem = item.getItemId();

        if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            // do something with parent
            //TODO contextmenu parent
            if(menuItem==0){//delete
                Toast.makeText(
                        getApplicationContext(),
                        "DELETE"
                        , Toast.LENGTH_SHORT)
                        .show();
            }else if(menuItem==1){//edit
                editExercise(exercises.get(groupPosition));
            }

        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            // do someting with child
            //TODO contextmenu child
        }

        return super.onContextItemSelected(item);
    }


    /*
     * Set groupindicator to the right
     */
    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBoundsRelative(width - getDipsFromPixel(32), width
                - getDipsFromPixel(4));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale );
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<Exercise>();
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
        listDataHeader.addAll(exercises);
        db.closeDB();
    }

    //DELETE EXERCISE
    public void delExercise(Exercise exercise,boolean deletereps,Context context){
        db = new DatabaseHandler(context);
        db.deleteExercise(exercise, deletereps);
        db.closeDB();
    }
    //EDIT EXERCISE
    public void editExercise(Exercise exercise){
        Intent intent = new Intent(this, Edit_Exercise.class);
        intent.putExtra("exerciselist", exercise);
        startActivity(intent);
    }
}
