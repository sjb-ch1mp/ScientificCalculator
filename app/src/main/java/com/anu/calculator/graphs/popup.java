package com.anu.calculator.graphs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.anu.calculator.Expression;
import com.anu.calculator.R;
import com.anu.calculator.ui.GraphActivity;
import com.anu.calculator.ui.MainActivity;
import com.anu.calculator.utilities.History;
import com.anu.calculator.utilities.HistoryItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
/**
 * @author: Siwei Wu (u6735397)
 */

/**
 * The activity for the pop up window for user to select the functions
 */
public class popup extends Activity {
    ListView listView;
    ArrayList<ListModel> functionsList = new ArrayList<>();
    ArrayList<ListModel> temp = new ArrayList<>();
    functionlistAdaptor list_adaptor;

    /**
     * Generate the list of functions and cooresponding radio button
     *
     * based on example code: https://www.journaldev.com/14171/android-checkbox
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindow);
        DisplayMetrics dim =  new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dim);
        int w = dim.widthPixels;
        int h = dim.heightPixels;
        getWindow().setLayout((int) (w*0.9),(int)(h*0.9));
        Button cancel_b = findViewById(R.id.popup_cancel_button);
        Button confirm_b = findViewById(R.id.popup_confirm_button);
        functionsList = GraphActivity.getFunctions();
        temp = new ArrayList<ListModel>();
        for (int i = 0 ; i<functionsList.size();i++){
            temp.add(new ListModel(functionsList.get(i).func,functionsList.get(0).checked)) ;
        }

        listView =  findViewById(R.id.listView);
        list_adaptor = new functionlistAdaptor(functionsList,getApplicationContext());
        listView.setAdapter(list_adaptor);
        //TextView t = findViewById(R.id.FunctionTitle);
        //t.setText(String.valueOf(functionsList.size()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ListModel item = functionsList.get(i);
                item.checked = !item.checked;
                list_adaptor.notifyDataSetChanged();
            }
        });

        View.OnClickListener onClick_cancel = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GraphActivity.setFunctions(temp);
                finish();
            }
        };
        View.OnClickListener onClick_confirm = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GraphActivity.setGraph();
                finish();
            }
        };
        confirm_b.setOnClickListener(onClick_confirm);
        cancel_b.setOnClickListener(onClick_cancel);
    }

}
