package com.anu.calculator.expressions;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.exceptions.InfinityException;
import com.anu.calculator.exceptions.MathematicalSyntaxException;
import com.anu.calculator.utilities.Scripts;

/**
 * ArcTangentExpression: This class is used to represent the expression of arctangent
 *
 * @author: Samuel Brookes (u5380100)
 * @modified: Michael Betterton (u6797866)
 * 	- 05/09/2019: Refactored class to implement Expression interface
 * 	- 05/09/2019: Refactored name from Exp to Expression
 */

public class ArcTangentExpression implements Expression {

	private static final String TAG = "ARCTANGENT_EXPRESSION";
	private Expression expression;
	private Boolean degrees;
	private Integer precision;

	public ArcTangentExpression(Expression expression, Boolean degrees)
	{
		this.expression = expression;
		this.degrees = degrees;
	}

	@Override
	public String show() {
		return "tan" +
				Scripts.SuperScript.MINUS.getUnicode() +
				Scripts.SuperScript.ONE.getUnicode() +
				"(" + expression.show() + ")";
	}

	@Override
	public double evaluate() throws ParserException {
		try
		{
			double evaluation;

			//if the preference is set to degrees, convert the result to degrees
			if(degrees) evaluation = Math.toDegrees(Math.atan(expression.evaluate()));
			else evaluation = Math.atan(expression.evaluate());

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
