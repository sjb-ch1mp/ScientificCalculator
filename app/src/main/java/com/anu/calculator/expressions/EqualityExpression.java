package com.anu.calculator.expressions;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;

/**
 * EqualityExpression: This class is used to represent equality and variable/function assignment
 *
 * @author: Michael Betterton (u6797866)
 * @modified: Samuel Brookes (u5380100)
 *  - 07/09/2019: added throw clause
 *  - 19/09/2019: added isSameVariable() and getExpression()
 *  @modified: Howard Chao (u7022787)
 */

public class EqualityExpression implements Expression {

    private char variable;
    private Expression expression;
    private Integer precision;

    public EqualityExpression(char variable, Expression expression) {
        this.expression = expression;
        this.variable = variable;
    }

    @Override
    public String show() {
        return variable + "=" + expression.show();
    }

    @Override
    public double evaluate() throws ParserException {
        //check if this expression is the root of the parsing tree
        if(precision != null) return Double.parseDouble(String.format("%." + precision + "f", expression.evaluate()));
        else return expression.evaluate();
    }

    /**
     * Getter method for expression
     * @return Expression
     */
    public Expression getExpression()
    {
        return expression;
    }

    @Override
    public void updatePrecision(Integer precision)
    {
        this.precision = precision;
    }

}