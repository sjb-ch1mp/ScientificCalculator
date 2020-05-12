package com.anu.calculator.expressions;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.exceptions.InfinityException;
import com.anu.calculator.exceptions.MathematicalSyntaxException;
import com.anu.calculator.utilities.Scripts;

/**
 * CubedRootExpression: This class is used to represent the expression of cubed root
 *
 * @author: Samuel Brookes (u5380100)
 * @modified: Michael Betterton (u6797866)
 * 	- 05/09/2019: Refactored class to implement Expression interface
 * 	- 05/09/2019: Refactored name from Exp to Expression
 */

public class CubedRootExpression implements Expression {
    private static final String TAG = "CUBED_ROOT_EXPRESSION";
    private Expression expression;
    private Integer precision;

    public CubedRootExpression(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public String show()
    {
        return Scripts.Operator.CUBE_ROOT.getUnicode() + "(" + expression.show() + ")";
    }

    @Override
    public double evaluate() throws ParserException
    {
        try
        {
            //evaluate the expression
            double evaluation = Math.cbrt(expression.evaluate());

            //if the value of evaluation is too large for a double type, throw an infinity exception
            if(evaluation == Double.POSITIVE_INFINITY) throw new InfinityException(TAG, Scripts.ErrorMessage.NUMBER_TOO_LARGE.getMessage());

            //check if this expression is the root of the parsing tree
            if(precision != null) return Double.parseDouble(String.format("%." + precision + "f", evaluation));
            else return evaluation;
        }
        catch(NullPointerException e)
        {
            throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());
        }
    }

    @Override
    public void updatePrecision(Integer precision)
    {
        this.precision = precision;
    }

}
