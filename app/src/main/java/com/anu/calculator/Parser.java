package com.anu.calculator;

import com.anu.calculator.utilities.History;

/**
 * This interface described how a Expression Parser should behave.
 */
public interface Parser {

    /**
     * Takes a string representation of an expression and returns it as an Expression.
     * Takes a History object and user preferences as parameters.
     *
     * @param expression The expression (string) to evaluate in the parser.
     * @param degrees    Boolean if the parser should calculate trig functions using degrees. False
     *                   implies that radians should be used instead.
     * @param precision  The number of digits to round the result of the calculation to.
     * @param history    A History object that contains previously returned history expressions.
     *                   These history objects are used to determine the result of function
     *                   calculations.
     * @return Expression The tokenized and parsed expression, adhering to the Expression interface.
     * @throws ParserException A exception generated as a result of malformed input to the parser.
     */
    Expression parse(String expression, Boolean degrees, Integer precision, History history) throws ParserException;
}
