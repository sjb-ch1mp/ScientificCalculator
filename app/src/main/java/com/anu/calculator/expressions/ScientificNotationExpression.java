package com.anu.calculator.expressions;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.exceptions.InfinityException;
import com.anu.calculator.utilities.Scripts;

/**
 * ScientificNotationExpression: This class is used to scientific notation
 *
 * @author: Samuel Brookes (u5380100)
 */

public class ScientificNotationExpression implements Expression {

	private static final String TAG = "SCIENTIFIC_NOTATION_EXPRESSION";
	private Expression base;
	private Expression exponent;
	private Integer precision;

	public ScientificNotationExpression(Expression base, Expression exponent)
	{
		this.base = base;
		this.exponent = exponent;
	}

	@Override
	public String show() {
		return base + "E" + exponent;
	}

	@Override
	public double evaluate() throws ParserException
	{
		double evaluation = base.evaluate() * Math.pow(10, exponent.evaluate());

		//if the value of evaluation is too large for a double type, throw an infinity exception
		if(evaluation == Double.POSITIVE_INFINITY) throw new InfinityException(TAG, Scripts.ErrorMessage.NUMBER_TOO_LARGE.getMessage());

		//check if this expression is the root of the parsing tree
		if(precision != null) return Double.parseDouble(String.format("%." + precision + "f", evaluation));
		else return evaluation;
	}

	@Override
	public void updatePrecision(Integer precision)
	{
		this.precision = precision;
	}

}
