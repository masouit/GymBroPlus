package com.example.mohamed.gymbroplus;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Show_Exercises extends AppCompatActivity {
    DatabaseHandler db;
    ArrayList<Exercise> exercises = new ArrayList<Exercise>();
    List<Rep> reps = new ArrayList<Rep>();

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;


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
            listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

            // Set Adapter to ExpandableList Adapter
            expListView.setAdapter(listAdapter);
        }
        else
        {
            // Refresh ExpandableListView data
            listAdapter.notifyDataSetChanged();
        }

//        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
//
//        // setting list adapter
//        expListView.setAdapter(listAdapter);

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

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
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
//            Toast.makeText(
//                    getApplicationContext(),
//                    listDataHeader.get(groupPosition)
//                    , Toast.LENGTH_SHORT)
//                    .show();
            if(menuItem==0){//delete
                Toast.makeText(
                        getApplicationContext(),
                        "DELETE"
                        , Toast.LENGTH_SHORT)
                        .show();
            }else if(menuItem==1){//edit
                editExercise(exercises.get(groupPosition),groupPosition);
            }

        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            // do someting with child
            //TODO contextmenu child
        }

        return super.onContextItemSelected(item);
    }
    public void editExercise(Exercise exercise,int exerciseId){
        Toast.makeText(
                getApplicationContext(),
                "EDIT "+exercise.getExerciseName()
                , Toast.LENGTH_SHORT)
                .show();
        //db = new DatabaseHandler(getApplicationContext());
        //exercise.setExerciseName(exercise.getExerciseName()+"edited");
        //db.updateExercise(exercise);

//        intent.putExtra("Contact_list", ContactLis);
//        ArrayList exerciselist = new ArrayList();
//        exerciselist.add(new String(exercise.getExerciseId(),exercise.getExerciseName()));

        Intent intent = new Intent(this, Edit_Exercise.class);
        intent.putExtra("exerciselist", exercises);
        intent.putExtra("exerciseId",exerciseId);
        startActivity(intent);

    }

    /*
     * Set groupindicator to the right
     */
    private void setGroupIndicatorToRight() {
        /* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBoundsRelative(width - getDipsFromPixel(60), width
                - getDipsFromPixel(40));
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
        db = new DatabaseHandler(getApplicationContext());
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        exercises.addAll(db.getAllExercises());
        int size = exercises.size();
        //String repsize,headername,headernametotal;

        List<List<String>> lists = new ArrayList<List<String>>();
        for (int i = 0; i < size; i++) {
            List<String> list = new ArrayList<>();
            //listDataHeader.add(exercises.get(i).getExerciseName());

            reps.addAll(db.getAllRepsByExercise(exercises.get(i).getExerciseName()));
            for (int j = 0; j < reps.size(); j++){
                list.add("Rep"+(j+1)+": "+String.valueOf(reps.get(j).getRepAmount()));
            }
            lists.add(list);
            //repsize = " ("+String.valueOf(reps.size())+")reps";
            //headername = exercises.get(i).getExerciseName();
            //headernametotal = headername+repsize;
            reps.clear();
            listDataHeader.add(exercises.get(i).getExerciseName());

            // Use the list further...
            listDataChild.put(exercises.get(i).getExerciseName(), lists.get(i));
        }
        db.closeDB();
    }
}
