package com.example.mohamed.gymbroplus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Edit_Blueprint extends AppCompatActivity {
    DatabaseHandler db;
    ExpandableListBlueprintAdapter listAdapter;
    ExpandableListView expListView;
    Blueprint blueprint;
    List<Blueprint> listDataHeader;
    ArrayList<Blueprint> blueprints = new ArrayList<Blueprint>();
    List<BlueprintDay> days = new ArrayList<BlueprintDay>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__blueprint);


        //blueprintId = getIntent().getIntExtra("blueprintId",0);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expandableListViewBlueprint);

        //Get objects from other intent
        Intent intent = this.getIntent();
        blueprint = (Blueprint) intent.getParcelableExtra("blueprint");

        prepareListData();

        // Check for ExpandableListAdapter object
        if (listAdapter == null) {
            //Create ExpandableListAdapter Object
            listAdapter = new ExpandableListBlueprintAdapter(listDataHeader, this);

            // Set Adapter to ExpandableList Adapter
            expListView.setAdapter(listAdapter);
        }
        else {
            // Refresh ExpandableListView data
            listAdapter.notifyDataSetChanged();
        }
        ImageView edit = (ImageView) findViewById(R.id.edit);
        if(edit!=null)
        edit.setVisibility(View.INVISIBLE);

        registerForContextMenu(expListView);
        expListView.expandGroup(0);
        expListView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return parent.isGroupExpanded(groupPosition);
            }
        });
        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(getBaseContext(), Edit_day.class);
                intent.putExtra("dayId",days.get(childPosition));
                startActivity(intent);
                return false;
            }
        });
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<Blueprint>();
        db = new DatabaseHandler(getApplicationContext());
        blueprints.add(blueprint);

        for (int i = 0; i < blueprints.size(); i++) {
            ArrayList<BlueprintDay> list = new ArrayList<BlueprintDay>();

            days.addAll(db.getAllBlueprintDaysByBlueprint(blueprints.get(i).getBlueprintId()));
            for (int j = 0; j < days.size(); j++){
                list.add(days.get(j));
            }
            blueprints.get(i).setBlueprintDays(list);
        }
        listDataHeader.addAll(blueprints);
        db.closeDB();
    }
    //EDIT EXERCISE
    public void editDay(BlueprintDay blueprintday){
        Intent intent = new Intent(this, Edit_Exercise.class);
        intent.putExtra("exerciselist", blueprintday);
        startActivity(intent);
    }
}
