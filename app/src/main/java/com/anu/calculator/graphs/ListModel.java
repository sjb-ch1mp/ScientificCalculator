package com.anu.calculator.graphs;

import com.anu.calculator.Expression;
/**
 * @author: Siwei Wu (u6735397)
 */

/**
 * Class contains the expression and boolean indicate whether its going to be displayed
 *
 * based on example code: https://www.journaldev.com/14171/android-checkbox
 */
public class ListModel {
    public Expression func;
    public boolean checked;

    /**
     * contructor
     *
     * @param func the expression
     * @param checked whether it'll be graphed
     */
    public ListModel(Expression func, boolean checked) {
        this.func = func;
        this.checked = checked;
    }
}
