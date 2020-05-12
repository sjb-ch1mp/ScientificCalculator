package com.anu.calculator.expressions;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.exceptions.InfinityException;
import com.anu.calculator.exceptions.MathematicalSyntaxException;
import com.anu.calculator.utilities.Scripts;

/**
 * PermutationExpression: This class is used to represent an expression of a permutation
 *
 * @author: Samuel Brookes (u5380100)
 * @modified: Michael Betterton (u6797866)
 * 	- 05/09/2019: Refactored class to implement Expression interface
 * 	- 05/09/2019: Refactored name from Exp to Expression
 */

public class PermutationExpression implements Expression {

	private static final String TAG = "PERMUTATION_EXPRESSION";
	private Expression n;
	private Expression r;
	private Integer precision;

	public PermutationExpression(Expression n, Expression r) {
		this.n = n;
		this.r = r;
	}

	@Override
	public String show() {
		return "(" + n.show() + "nPr" + r.show() + ")";
	}

	@Override
	public double evaluate() throws ParserException {
		try
		{
			//construct a permutation equation using expressions
			FactorialExpression numerator = new FactorialExpression(n);
			FactorialExpression denominator = new FactorialExpression(new SubtractExpression(n, r));

			//evaluate the equation
			double evaluation = numerator.evaluate() / denominator.evaluate();

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
