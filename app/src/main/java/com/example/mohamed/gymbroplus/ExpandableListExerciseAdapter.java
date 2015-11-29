package com.example.mohamed.gymbroplus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Mohamed on 22-11-2015.
 */
public class ExpandableListExerciseAdapter extends BaseExpandableListAdapter {

    private List<Exercise> catList;
    private Context ctx;

    public ExpandableListExerciseAdapter(List<Exercise> catList, Context ctx) {

        this.catList = catList;
        this.ctx = ctx;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return catList.get(groupPosition).getRepList().get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return catList.get(groupPosition).getRepList().get(childPosition).hashCode();
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView itemName = (TextView) v.findViewById(R.id.lblListItem);

        Rep rep = catList.get(groupPosition).getRepList().get(childPosition);

        itemName.setText(String.valueOf(rep.getRepAmount()));

        return v;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int size = catList.get(groupPosition).getRepList().size();
        System.out.println("Child for group ["+groupPosition+"] is ["+size+"]");
        return size;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return catList.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return catList.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return catList.get(groupPosition).hashCode();
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater)ctx.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_group, parent, false);
        }

        TextView groupName = (TextView) v.findViewById(R.id.lblListHeader);


        Exercise cat = catList.get(groupPosition);

        groupName.setText(cat.getExerciseName());
        groupName.setTypeface(null, Typeface.BOLD);
        //DELETE EXERCISE
        ImageView delete = (ImageView) v.findViewById(R.id.delete);
        delete.setOnClickListener(new ExpandableListView.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setMessage("Do you want to remove?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Exercise group = catList.get(groupPosition);
                                catList.remove(groupPosition);
                                notifyDataSetChanged();
                                Show_Exercises se = new Show_Exercises();
                                se.delExercise(group, true, ctx);

                                Toast.makeText(ctx, " Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        //EDIT EXERCISE
        ImageView edit = (ImageView) v.findViewById(R.id.edit);
        edit.setOnClickListener(new ExpandableListView.OnClickListener() {
            public void onClick(View v) {
                Exercise group = catList.get(groupPosition);
                Intent intent = new Intent(ctx, Edit_Exercise.class);
                intent.putExtra("exerciselist", group);
                ctx.startActivity(intent);
            }
        });
        return v;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
