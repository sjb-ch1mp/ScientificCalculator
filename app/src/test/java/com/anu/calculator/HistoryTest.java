package com.anu.calculator;

import android.content.Context;

import com.anu.calculator.parsers.ExpressionParser;
import com.anu.calculator.utilities.History;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * This test class tests the functionality of the History class.
 */
public class HistoryTest {

    /**
     * This test checks whether a variable can be assigned, then referenced by another expression.
     * This is the general use-case of the function system.
     */
    @Test
    public void testSimpleHistory() throws ParserException {
        // Declare the test case and an empty history stack
        String test1 = "x=5";
        String test2 = "y=2x";
        String test3 = "z=3y";

        // Instantiate a parser, evaluate each test cash, pushing the parsed expression onto the
        // stack as we go.
        History history_1 = History.getInstance();
        ExpressionParser fp = new ExpressionParser();
        Expression exp = fp.parse(test1,true, 0,  history_1);
        history_1.put(exp, false);
        //history_1.save();

        // Load the history back in, run a expression through the parser and save it
        //History history_2 = History.load();
        //exp = fp.parse(test2, true, 0,  history_2);
        //history_2.put(exp, false);
        //history_2.save();

        // Do the same thing for a third time.
        //History history_3  = History.load();
        //exp = fp.parse(test3, true, 0,  history_3);
        //assertEquals(exp.evaluate(),30.0);
    }
}
