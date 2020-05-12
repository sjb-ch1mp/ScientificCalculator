package com.anu.calculator.expressions;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.exceptions.InfinityException;
import com.anu.calculator.exceptions.MathematicalSyntaxException;
import com.anu.calculator.utilities.Scripts;

/**
 * SineExpression: This class is used to represent the expression of sine
 *
 * @author: Samuel Brookes (u5380100)
 * @modified: Michael Betterton (u6797866)
 * 	- 05/09/2019: Refactored class to implement Expression interface
 * 	- 05/09/2019: Refactored name from Exp to Expression
 */

public class SineExpression implements Expression {

	private final String TAG = "SINE_EXPRESSION";
	private Expression expression;
	private Boolean degrees;
	private Integer precision;

	public SineExpression(Expression expression, Boolean degrees)
	{
		this.expression = expression;
		this.degrees = degrees;
	}

	@Override
	public String show() {
		return "sin(" + expression.show() + ")";
	}

	@Override
	public double evaluate() throws ParserException {
		try
		{
			double evaluation;

			//if the preference is set to degrees, convert the value to radians before passing to Math.sin
			if(degrees) evaluation = Math.sin(Math.toRadians(expression.evaluate()));
			else evaluation = Math.sin(expression.evaluate());

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
