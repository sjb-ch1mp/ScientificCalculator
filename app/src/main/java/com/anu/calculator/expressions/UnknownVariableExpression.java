package com.anu.calculator.expressions;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.exceptions.InfinityException;
import com.anu.calculator.exceptions.UnassignedVariableException;
import com.anu.calculator.utilities.Scripts;

/**
 * UnknownVariableExpression: This class is used to represent a variable with an unknown value,
 * i.e. for formulas.
 *
 * @author: Samuel Brookes (u5380100)
 * @modified: Michael Betterton (u6797866)
 * 	- 05/09/2019: Refactored class to implement Expression interface
 * 	- 05/09/2019: Refactored name from Exp to Expression
 */

public class UnknownVariableExpression implements Expression {

	private final String TAG = "UNKNOWN_VARIABLE_EXPRESSION";
	private char variable;
	private Expression value;
	private Integer precision;

	/**
	 * The default constructor of the expression, in which no
	 * value is assigned to the variable
	 * @param variable : the character representing the variable
	 */
	public UnknownVariableExpression(char variable) {
		this.variable = variable;
		this.value = null;
	}

	/**
	 * An alternative constructor which assigns a value to the unknown variable.
	 * @param variable : the character representing the variable
	 * @param value : the value of the variable
	 */
	public UnknownVariableExpression(char variable, Expression value)
	{
		this.variable = variable;
		this.value = value;
	}

	@Override
	public String show() {
		return "" + variable;
	}

	@Override
	public double evaluate() throws ParserException {
		if(hasValue())
		{
			//if the value of evaluation is too large for a double type, throw an infinity exception
			if(value.evaluate() == Double.POSITIVE_INFINITY) throw new InfinityException(TAG, Scripts.ErrorMessage.NUMBER_TOO_LARGE.getMessage());

			//check if this expression is the root of the parsing tree
			if(precision != null) return Double.parseDouble(String.format("%." + precision + "f", value.evaluate()));
			else return value.evaluate();
		}
		else throw new UnassignedVariableException(TAG, variable + Scripts.ErrorMessage.UNASSIGNED_VARIABLE.getMessage());
	}

	/**
	 * Checks if the variable has a value assigned to it.
	 * @return boolean : whether or not the variable has a value assigned to it
	 */
	public boolean hasValue()
	{
		return value != null;
	}

	@Override
	public void updatePrecision(Integer precision)
	{
		this.precision = precision;
	}

}
