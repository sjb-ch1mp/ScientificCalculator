package com.anu.calculator.utilities;

import com.anu.calculator.Expression;

import java.io.Serializable;

/**
 * This class is a simple container that stores an expression and a flag that indicates whether or not
 * it is 'graphable'. It is used to store expressions in the History class.
 *
 * @author Samuel Brookes (u5380100)
 */
public class HistoryItem implements Serializable {
    private static final long serialVersionUID = 21071992L;
    private boolean graphable;
    private Expression expression;

    HistoryItem(boolean graphable, Expression expression) {
        this.graphable = graphable;
        this.expression = expression;
    }

    boolean isGraphable() {
        return graphable;
    }

    public Expression getExpression() {
        return expression;
    }
}
