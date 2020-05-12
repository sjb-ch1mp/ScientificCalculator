package com.anu.calculator.expressions;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.exceptions.DivisionByZeroException;
import com.anu.calculator.exceptions.InfinityException;
import com.anu.calculator.exceptions.MathematicalSyntaxException;
import com.anu.calculator.utilities.Scripts;

/**
 * DivideExpression: This class is used to represent the expression of division
 *
 * @author: Michael Betterton (u6797866)
 * @modified: Samuel Brookes (u5380100)
 *  - Added the DIVIDE unicode to the show() method
 */

public class DivideExpression implements Expression {

	private static final String TAG = "DIVIDE_EXPRESSION";
	private Expression factor;
	private Expression term;
	private Integer precision;

	public DivideExpression(Expression factor, Expression term) {
		this.factor = factor;
		this.term = term;
	}

	@Override
	public String show() {
		return "(" + factor.show() + Scripts.Operator.DIVIDE.getUnicode() + term.show() + ")";
	}

	@Override
	public double evaluate() throws ParserException {
		try
		{
			//check that the user is not dividing by zero
			double checkForZero = term.evaluate();
			if(checkForZero == 0) throw new DivisionByZeroException(TAG, "Cannot divide by zero");
			else
			{
				//evaluate the expression
				double evaluation = factor.evaluate() / term.evaluate();

				//if the value of evaluation is too large for a double type, throw an infinity exception
				if(evaluation == Double.POSITIVE_INFINITY) throw new InfinityException(TAG, Scripts.ErrorMessage.NUMBER_TOO_LARGE.getMessage());

				//check if this expression is the root of the parsing tree
				if(precision != null) return Double.parseDouble(String.format("%." + precision + "f", evaluation));
				else return evaluation;
			}
		}
		catch(NullPointerException e)
		{
			throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());
		}
		catch(ParserException e)
		{
			throw e;
		}
	}

	@Override
	public void updatePrecision(Integer precision)
	{
		this.precision = precision;
	}

}