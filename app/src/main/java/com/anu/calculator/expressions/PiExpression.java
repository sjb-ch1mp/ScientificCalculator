package com.anu.calculator.expressions;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.utilities.Scripts;

/**
 * PiExpression: This class is used to represent PI
 *
 * @author: Samuel Brookes (u5380100)
 * @modified: Michael Betterton (u6797866)
 * 	- 05/09/2019: Refactored class to implement Expression interface
 * 	- 05/09/2019: Refactored name from Exp to Expression
 */

public class PiExpression implements Expression {

	private Integer precision;

	@Override
	public String show() {
		return "" + Scripts.Operator.PI.getUnicode();
	}

	@Override
	public double evaluate() throws ParserException {
		//check if this expression is the root of the parsing tree
		if(precision != null) return Double.parseDouble(String.format("%." + precision + "f", Math.PI));
		else return Math.PI;
	}

	@Override
	public void updatePrecision(Integer precision)
	{
		this.precision = precision;
	}

}
