package com.anu.calculator.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;

import com.anu.calculator.R;
import com.anu.calculator.graphs.ChartVect;
import com.anu.calculator.graphs.GraphRange;
import com.anu.calculator.graphs.graphViewer;
import com.anu.calculator.graphs.ListModel;
import com.anu.calculator.graphs.popup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author: Siwei Wu (u6735397)
 */
/**
 * The main graph activity for the graphing modules
 *
 */
public class GraphActivity extends AppCompatActivity {
    static graphViewer gView;
    public static final float INIT_RANGE = 20.0f;

    /**
     * Draw the graph
     */
    public static void setGraph(){
        gView.invalidate();
    }

    /**
     * pull the list of functions (encapsulated in ListModel class) from back end
     * @return the list of functions (and whether to be graphed)
     */
    public static ArrayList<ListModel> getFunctions(){
        gView.refresh_function();
        return gView.functionList;
    }

    /**
     * push the list of functions to back end
     * this is uesd to pass whether the graph is to be graphed or not between the UI and back end
     *
     * @param new_functions
     */
    public static void setFunctions(ArrayList<ListModel> new_functions){
        gView.functionList = new_functions;
    }

    /**
     * Create the components on the graph view
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        createGraph(new GraphRange(new ChartVect(-INIT_RANGE,-INIT_RANGE), new ChartVect(INIT_RANGE,INIT_RANGE)));
        createFloatingButton();
        createFunctionButtons();
        createOptions();
    }

    /**
     * Create the options check box - whether to show axis, label and grid
     */
    public void createOptions(){
        CheckBox chkAxis = findViewById(R.id.checkBox_Axis);
        CheckBox chkAxisLabel= findViewById(R.id.checkBox_Label);
        CheckBox chkGrid= findViewById(R.id.checkBox_grid);
        chkAxis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    gView.isAxis = true;
                } else {
                    gView.isAxis = false;
                }
                gView.invalidate();
            }
        });
        chkAxisLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    gView.isAxisLabel= true;
                } else {
                    gView.isAxisLabel = false;
                }
                gView.invalidate();
            }
        });
        chkGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox) view).isChecked()){
                    gView.isGrid= true;
                } else {
                    gView.isGrid= false;
                }
                gView.invalidate();
            }
        });
    }

    /**
     *
     * Create the Function and Reset button
     */
    public void createFunctionButtons() {
        Button b = findViewById(R.id.select_function_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GraphActivity.this, popup.class));
            }
        });
        Button b2 = findViewById(R.id.reset_view);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gView.reset_view();
                gView.invalidate();
            }
        });
    }

    /**
     * Create on the graph view
     *
     * @param range the initial range of the graph (-20 to 20)
     */
    public void createGraph(GraphRange range){
        gView = findViewById(R.id.graph_view);
        gView.init_functions(range);


    }

    /**
     * Create the navigation button to move between Graph and Calculator
     *
     */
    public void createFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_Main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain = new Intent(GraphActivity.this, MainActivity.class);
                toMain.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(toMain, 0);
                // Hide the soft keyboard.
                InputMethodManager inputManager = (InputMethodManager)
                Objects.requireNonNull(getSystemService(Context.INPUT_METHOD_SERVICE));
                inputManager.toggleSoftInput(0, 0);
            }
        });
    }
}