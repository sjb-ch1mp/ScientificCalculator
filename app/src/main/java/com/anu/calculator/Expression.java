package com.anu.calculator;

import java.io.Serializable;

/**
 * Interface for Expressions, any function or operation implemented in the calculator should
 * implement this interface.
 */
public interface Expression extends Serializable {

	/**
	 * Show the expression in a text representation.
	 *
	 * @return The expression as a string, it should be formatted "prettily"
	 */
	String show();

	/**
	 * Evaluate the expression, returning its value as a double.
	 *
	 * @return The expressions literal result as a double.
	 */
	double evaluate() throws ParserException;

	/**
	 * Updates the precision field so that when evaluate() is called,
	 * the correct number of significant figures are returned. If precision
	 * is null, evaluate returns the evaluation unaltered.
	 * @param precision The precision to show the result as.
	 */
	void updatePrecision(Integer precision);
}
