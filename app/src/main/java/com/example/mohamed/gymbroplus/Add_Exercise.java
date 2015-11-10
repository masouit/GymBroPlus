package com.example.mohamed.gymbroplus;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Add_Exercise extends AppCompatActivity {
    private LinearLayout mLayout;
    private int repCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__exercise);
        mLayout = (LinearLayout) findViewById(R.id.linearLayout1);
    }

    public void addRepTextview(View view){
        repCount++;
        /*
        TextView tv=new TextView(getApplicationContext());
        tv.setTextColor(Color.BLACK);
        tv.setEms(10);
        tv.setText("Rep" + repCount);
        mLayout.addView(tv);

        EditText et=new EditText(getApplicationContext());
        et.setTextColor(Color.BLACK);
        et.setId(id);
        mLayout.addView(et);
        */
        //createNewTextView("textViewRep" + repCount);
       // mLayout.addView(createNewTextView(String.valueOf(repCount)));



        //Toast.makeText(getApplicationContext(), "added view", Toast.LENGTH_SHORT).show();

        ArrayList<View> viewList = new ArrayList<View>();

        mLayout = (LinearLayout) findViewById(R.id.linearLayout1);

        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addView = layoutInflater.inflate(R.layout.rep_row, null);
        TextView textOut = (TextView)addView.findViewById(R.id.textView4);
        textOut.append(String.valueOf(repCount));
        mLayout.addView(addView);

        //Button buttonRemove = (Button)addView.findViewById(R.id.remove);

        //Toast.makeText(getApplicationContext(), "added button"+repCount, Toast.LENGTH_SHORT).show();
        final int id_button =repCount;
        final Button buttonRemove = (Button) addView.findViewById(R.id.remove);
        //buttonRemove.setTag("button"+repCount);
        buttonRemove.setId(id_button);
        buttonRemove.setText("-");

        buttonRemove.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                ((LinearLayout) addView.getParent()).removeView(addView);
                repCount--;
                if(repCount>0){
                    int id_ = buttonRemove.getId()-1;
                    Button btn = ((Button) findViewById(id_));
                    Toast.makeText(getApplicationContext(), "added button"+id_, Toast.LENGTH_SHORT).show();
                    // Button btn = (Button) addView.findViewWithTag("button" + (j - 1));
                    //btn.setText("+");
                    btn.setVisibility(View.VISIBLE);
                }
            }
        });

        if(repCount>1){
            int id_ = buttonRemove.getId()-1;
            Button btn = ((Button) findViewById(id_));
            Toast.makeText(getApplicationContext(), "added button"+id_, Toast.LENGTH_SHORT).show();
           // Button btn = (Button) addView.findViewWithTag("button" + (j - 1));
            btn.setVisibility(View.INVISIBLE);
        }


    }
    //TODO delRepTextview
    public void delRepTextview(View view){
        View myView = findViewById(R.id.linearLayout1);

        mLayout.removeView(myView);


    }
    private TextView createNewTextView(String text) {
        final LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TextView textView = new TextView(this);
        textView.setLayoutParams(lparams);
        textView.setText("Rep" + text);
        return textView;
    }
/*
    private void addEditView() {
        // TODO Auto-generated method stub
        LinearLayout li=new LinearLayout(this);
        EditText et=new EditText(this);
        Button b=new Button(this);

        b.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                int pos=(Integer) v.getTag();
                mainLayout.removeViewAt(pos);

            }
        });

        b.setTag((mainLayout.getChildCount()+1));
    }
    */
}
