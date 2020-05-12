package com.anu.calculator.graphs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.anu.calculator.R;

import java.util.ArrayList;

/**
 * @author: Siwei Wu (u6735397)
 */

/**
 * The List of functions with radio button to be build when the user click on the set function button
 *
 */
public class functionlistAdaptor extends ArrayAdapter {
    private ArrayList<ListModel> dataSet;
    Context currentContext;


    /**
     * Sub-class for the UI aspect:
     * Text displying the function equation
     * Checkbox for selection whether to graph
     */
    private static class ViewHolder {
        TextView func;
        CheckBox checked;
    }

    /**
     *
     * Constructor for class
     * @param data - the Array List of the functions passed from the graphParser
     * @param context - this context
     */

    public functionlistAdaptor(ArrayList data, Context context) {
        super(context, R.layout.list_row, data);
        this.dataSet = data;
        this.currentContext = context;
    }

    /**
     * @return sizeof the dataset
     */
    @Override
    public int getCount() {
        return dataSet.size();
    }

    /**
     *
     * @param position - position in the array
     * @return the function indexed by position
     */
    @Override
    public ListModel getItem(int position) {
        return dataSet.get(position);
    }

    /**
     * Generate the list of function text + radio button in the view
     *
     *based on example code: https://www.journaldev.com/14171/android-checkbox
     *
     * @param position - position of the function in the data arraylist
     * @param convertView - the view for the list
     * @param parent - parent viewgroup
     * @return the particular view of the text+ radio indexed by the position
     */
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;
        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
            viewHolder.func =  convertView.findViewById(R.id.list_row_func_name);
            viewHolder.checked = convertView.findViewById(R.id.list_row_func_check);
            result = convertView;
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        ListModel item = getItem(position);
        viewHolder.func.setText(item.func.show());
        viewHolder.func.setTextColor(CalcChart.getPallet(position));
        viewHolder.checked.setChecked(item.checked);
        return result;
    }

}
