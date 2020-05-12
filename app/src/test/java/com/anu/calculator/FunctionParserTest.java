package com.anu.calculator;

import com.anu.calculator.exceptions.FunctionLoopException;
import com.anu.calculator.exceptions.UnassignedVariableException;
import com.anu.calculator.parsers.ExpressionParser;
import com.anu.calculator.utilities.History;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;

/**
 * This test class tests the functionality of the ExpressionParser when parsing functions
 * (i.e. EqualityExpressions) and when utilising a History object to assign values
 * to UnknownVariableExpressions.
 */
public class FunctionParserTest {

    private History history;

    @Before
    public void loadVariables()
    {
        history = History.getInstance();
    }

    /**
     * Simple test case that evaluates a single variable assignment to a expression.
     */
    @Test
    public void testSimpleAssignment() throws ParserException {
        // Declare the test case and an empty history stack
        String test = "x=4+1";

        // Instantiate a parser
        ExpressionParser fp = new ExpressionParser();
        Expression exp = fp.parse(test, true, 0, history);

        // Assert the first return as the literal evaluation of the input
        assertEquals(exp.evaluate(),5d);

        // Push the expression we just parsed onto a stack to use as history
        history.put(exp, true);

        // Create a new test case to use as the recall.
        test = "x";
        exp = fp.parse(test, true, 0, history);
        assertEquals(exp.evaluate(),5d);
        history.put(exp, true);

        // Test the recall without an equality expression
        test = "2x";
        exp = fp.parse(test, true, 0, history);
        assertEquals(exp.evaluate(),10d);
    }

    /**
     * Tests the fact a variable can be assigned to a new value. The parser should return the latest
     * value to the user.
     */
    @Test
    public void testVariableUpdating() throws ParserException {
        // Declare the test case and an empty history stack
        String test = "x=5-1";

        // Instantiate a parser
        ExpressionParser fp = new ExpressionParser();
        Expression exp = fp.parse(test, true, 0, history);

        // Assert the first return as the literal evaluation of the input
        assertEquals(exp.evaluate(),4d);

        // Push the expression we just parsed onto a stack to use as history
        history.put(exp, true);

        // Create a new test case that updates the value of the variable
        test = "x=5+1";
        exp = fp.parse(test,true, 0,  history);
        assertEquals(exp.evaluate(),6d);
        history.put(exp, true);

        test = "x=5+3";
        exp = fp.parse(test, true, 0,  history);
        assertEquals(exp.evaluate(),8d);
        history.put(exp, true);

        test = "x=5+10";
        exp = fp.parse(test,true, 0,   history);
        assertEquals(exp.evaluate(),15d);
        history.put(exp, true);

        // Create a new test case to use as the recall.
        test = "x";
        exp = fp.parse(test,true, 0,  history);
        assertEquals(exp.evaluate(),15d);
        history.put(exp, true);

        // Test the recall without an equality expression
        test = "5x";
        exp = fp.parse(test, true, 0, history);
        assertEquals(exp.evaluate(),75d);
    }

    /**
     * This test checks whether a variable can be assigned, then referenced by another expression.
     * This is the general use-case of the function system.
     */
    @Test
    public void testVariableReferencing() throws ParserException {
        // Declare the test case and an empty history stack
        String test1 = "x=5";
        String test2 = "y=2x";
        String test3 = "z=3y";

        // Instantiate a parser, evaluate each test cash, pushing the parsed expression onto the
        // stack as we go.
        ExpressionParser fp = new ExpressionParser();
        Expression exp = fp.parse(test1,true, 0,  history);
        history.put(exp, true);

        exp = fp.parse(test2, true, 0,  history);
        history.put(exp, true);

        exp = fp.parse(test3, true, 0,  history);
        assertEquals(exp.evaluate(),30.0);
    }

    /**
     * Tests retroactive assignment
     * @author: Samuel Brookes (u5380100)
     */
    @Test
    public void retroactiveAssignment() throws ParserException
    {
        ExpressionParser parser = new ExpressionParser();
        String testCase1 = "x=2", testCase2 = "y=2x", testCase3 = "x=5", testCase4 = "y";
        Expression exp;

        exp = parser.parse(testCase1, true, 0, history);
        assertEquals(2d, exp.evaluate());
        history.put(exp, true);

        exp = parser.parse(testCase2, true, 0, history);
        assertEquals(4d, exp.evaluate());
        history.put(exp, true);

        exp = parser.parse(testCase3, true, 0, history);
        assertEquals(5d, exp.evaluate());
        history.put(exp, true);

        //This tests whether a retroactive assignment evaluates correctly
        exp = parser.parse(testCase4, true, 0, history);
        assertEquals(10d, exp.evaluate());
        history.put(exp, true);
    }


    /**
     * This test checks whether a variable can be assigned, then referenced by another expression
     * alongside another variable. This is the more complex use case of the parser.
     *
     * @author: Howard Chao
     * @modified: Samuel Brookes (u5380100)
     *  - 19/09/2019: cleaned up after merge
     */
    @Test
    public void testComplexReferencing() throws ParserException {
        ExpressionParser fp = new ExpressionParser();
        Expression exp;

        // Declare the test case and an empty history stack
        String test1 = "x=5"; //5
        String test2 = "y=2x"; //10
        String test3 = "z=3y+y"; //40
        String test4 = "w=z+y-x"; //40+10-5= 45
        String test5 = "z=2y-x"; // 20-5=15. w is now 15+10-5=20
        String test6 = "y=x+3"; // y = 5 + 3 = 8
        String test7 = "z=2y-x"; //11
        String test8 = "w=z+y-x"; //21

        // Instantiate a parser, evaluate each test case, pushing the parsed expression onto the
        // stack as we go.
        exp = fp.parse(test1, true, 0,  history);
        assertEquals(exp.evaluate(),5d);
        history.put(exp, true);

        exp = fp.parse(test2, true, 0,  history);
        assertEquals(exp.evaluate(),10d);
        history.put(exp, true);

        exp = fp.parse(test3, true, 0,  history);
        assertEquals(exp.evaluate(),40d);
        history.put(exp, true);

        exp = fp.parse(test4, true, 0,  history);
        assertEquals(exp.evaluate(),45d);
        history.put(exp, true);

        exp = fp.parse(test5, true, 0,  history);
        assertEquals(exp.evaluate(),15d);
        history.put(exp, true);

        exp = fp.parse(test6, true, 0,  history);
        assertEquals(exp.evaluate(),8d);
        history.put(exp, true);

        exp = fp.parse(test7, true, 0,  history);
        assertEquals(exp.evaluate(),11d);
        history.put(exp, true);

        exp = fp.parse(test8, true, 0,  history);
        assertEquals(exp.evaluate(),14d);
    }

    /**
     * Confirms that when a unknown variable is referenced, a exception is returned.
     */
    @Test(expected = UnassignedVariableException.class)
    public void testExceptionSimple() throws ParserException {
        // Declare the test case and an empty history stack
        String test1 = "x=y";

        // Instantiate a parser, and try and generate an exception.
        ExpressionParser fp = new ExpressionParser();
        Expression exp = fp.parse(test1, true, 0, history);
        exp.evaluate(); //evaluate throws exception, not parse
    }

    /**
     * Tests for function loops.
     *
     * @author Samuel Brookes
     * @throws ParserException
     */
    @Test(expected = FunctionLoopException.class)
    public void testForLoops() throws ParserException
    {
        String test1 = "x=5";
        String test2 = "y=2x";
        String test3 = "x=y";
        String test4 = "x";

        ExpressionParser parser = new ExpressionParser();
        Expression exp = parser.parse(test1, true, 0, history);
        assertEquals(5d, exp.evaluate());
        history.put(exp, true);

        exp = parser.parse(test2, true, 0, history);
        assertEquals(10d, exp.evaluate());
        history.put(exp, true);

        //should throw error - but doesn't
        exp = parser.parse(test3, true, 0, history);
        history.put(exp, true);

        //this one throws the correct error - but the previous one should
        exp = parser.parse(test4, true, 0, history);
        history.put(exp, true);
    }

    /**
     * Simple test case that evaluates a single variable assignment to a expression.
     *
     * @author Mike Betterton
     * @modified Samuel Brookes
     *  - added more test cases and loop
     *  - added TestCase class
     */
    @Test
    public void testOperatorsInFunctions() throws ParserException {

        ArrayList<TestCase> tests = new ArrayList<>(0);

        //single variable test cases
        tests.add(new TestCase("a=sin30", 0.5));
        tests.add(new TestCase("b=cos60", 0.5));
        tests.add(new TestCase("c=tan45", 1d));
        tests.add(new TestCase("d=-15", -15d));
        tests.add(new TestCase("f=5!", 120d));
        tests.add(new TestCase("g=sin⁻¹0.5", 30d));
        tests.add(new TestCase("h=cos⁻¹0.5", 60d));
        tests.add(new TestCase("i=tan⁻¹1", 45d));
        tests.add(new TestCase("j=log₁₀10", 1d));
        tests.add(new TestCase("k=ln15", 2.708050201));
        tests.add(new TestCase("l=√100", 10d));
        tests.add(new TestCase("m=∛47", 3.60882608));
        tests.add(new TestCase("n=10nPr3", 720d));
        tests.add(new TestCase("o=15nCr2", 105d));
        tests.add(new TestCase("p=5^3", 125d));
        tests.add(new TestCase("q=5²", 25d));
        tests.add(new TestCase("r=3³", 27d));
        tests.add(new TestCase("s=2π", 6.283185307));
        tests.add(new TestCase("t=5e", 13.59140914));

        //composite test cases
        tests.add(new TestCase("u=a²", 0.25));
        tests.add(new TestCase("v=b+c", 1.5));
        tests.add(new TestCase("w=f÷d", -8d));
        tests.add(new TestCase("x=h-g", 30d));
        tests.add(new TestCase("y=i×j", 45d));
        tests.add(new TestCase("z=l^k", 510.5640138));
        tests.add(new TestCase("ɑ=n-m", 716.3911739));
        tests.add(new TestCase("β=o+p", 230d));
        tests.add(new TestCase("ɣ=rnCrq", 351d));
        tests.add(new TestCase("Δ=s²+t³", 2550.170533));

        //shorthand multiplication check
        tests.add(new TestCase("t=abcf", 30d));
        tests.add(new TestCase("z=vw", -12d));
        tests.add(new TestCase("ɑ=2tz", -720d));
        tests.add(new TestCase("a=2xπ", 188.495559));
        tests.add(new TestCase("b=2πx", 188.495559));
        tests.add(new TestCase("c=2ex", 163.0969097));
        tests.add(new TestCase("d=2xe", 163.0969097));
        tests.add(new TestCase("f=2π(10)", 62.831853));
        tests.add(new TestCase("f=2e(10)", 54.365637));

        ExpressionParser fp;
        Expression exp;
        try
        {
            for(TestCase test : tests)
            {
                fp = new ExpressionParser();
                exp = fp.parse(test.getTest(), true, 5, history);
                assertEquals(test.getAnswer(), exp.evaluate(), 0.00001d);
                history.put(exp, true);
            }
        }
        catch(UnassignedVariableException e)
        {
            System.out.println(e.getErrorMessage());
            throw e;
        }
    }

    /**
     * A container class for test cases
     * @author Samuel Brookes
     */
    class TestCase
    {
        String test;
        Double ans;

        TestCase(String test, Double ans)
        {
            this.test = test;
            this.ans = ans;
        }

        String getTest()
        {
            return test;
        }

        Double getAnswer()
        {
            return ans;
        }
    }
}
