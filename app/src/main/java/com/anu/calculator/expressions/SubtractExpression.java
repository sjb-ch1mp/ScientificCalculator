package com.anu.calculator.expressions;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.exceptions.InfinityException;
import com.anu.calculator.exceptions.MathematicalSyntaxException;
import com.anu.calculator.utilities.Scripts;

/**
 * SubtractExpression: This class is used to represent the expression of subtraction
 *
 * @author: Michael Betterton (u6797866)
 * @modified: Samuel Brookes (u5380100)
 *  - Added the SUBTRACT unicode to the show() method
 */

public class SubtractExpression implements Expression {

	private final String TAG = "SUBTRACT_EXPRESSION";
	private Expression expression;
	private Expression term;
	private boolean negative;
	private Integer precision;

	public SubtractExpression(Expression term, Expression expression) {
		this.term = term;
		this.expression = expression;
		try
		{
			if(term instanceof DoubleExpression && term.evaluate() == 0)
			{
				negative = true;
			}
		}
		catch(ParserException e)
		{
			negative = false;
		}
	}

	@Override
	public String show() {
		if(negative)
		{
			return "(-" + expression.show() + ")";
		}
		else return "(" + term.show() + "-" + expression.show() + ")";
	}

	@Override
	public double evaluate() throws ParserException {
		try
		{
			//evaluate the expression
			double evaluation = term.evaluate() - expression.evaluate();

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
