package com.anu.calculator;

import com.anu.calculator.exceptions.InfinityException;
import com.anu.calculator.expressions.EExpression;
import com.anu.calculator.parsers.ExpressionParser;
import com.anu.calculator.expressions.UnknownVariableExpression;
import com.anu.calculator.utilities.Scripts;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * This test class tests the functionality of the ExpressionParser.
 */
public class ExpressionParserTest {

    /**
     * This class encapsulates test cases for the expression parser within the test suite. It takes
     * a string as the input expression, the expected output of the string as an equation and a
     * delta figure. The delta is an allowable difference between the input and parsed expression
     * output.
     *
     * @author: Michael Betterton (u6797866)
     */
    private class TestCase {

        String input;
        Double expected;
        Double delta;

        /**
         * The default constructor for a test case.
         *
         * @param input    The input expression as a string. It should conform to the known tokens of
         *                 the application. Token parsing is tested in a different test suite.
         * @param expected The expected output of the expression as a double. Use a known working
         *                 calculator to generate this figure.
         * @param delta    The allowable difference between the input after parsing and the expected
         *                 output. This is to allow some trig functions and constants to be rounded
         *                 appropriately.
         */
        TestCase(String input, Double expected, Double delta) {
            this.input = input;
            this.expected = expected;
            this.delta = delta;
        }
    }

    /**
     * Dynamically runs all test cases added to the testCases ArrayList inside the method. Each test
     * case has an allowable margin of error in the delta attribute.
     * To add test cases to the test suite, add them to the array list by appending them in the
     * commented area.
     *
     * @author: Michael Betterton (u6797866)
     * @modified: Samuel Brookes (u5380100)
     *  - 04/09/2019: Add operator tests
     *  - 06/09/2019: Add operator tests
     */
    @Test
    public void runTests() {
        ArrayList<TestCase> testCases = new ArrayList<>(0);

        // Add test cases here by adding them to the test case array
        // This section contains some simple operations to confirm each operation is working as
        // expected.
        testCases.add(new TestCase("5", (double) 5, (double) 0));
        testCases.add(new TestCase("1.5", (double) 1.5, (double) 0));
        testCases.add(new TestCase("1.0+100", (double) 101, (double) 0));
        testCases.add(new TestCase("1.1×2.2", 2.42, 0.0001));
        testCases.add(new TestCase("12÷6", (double) 2, (double) 0));
        testCases.add(new TestCase("15.1-0.1", (double) 15, (double) 0));
        testCases.add(new TestCase("100×17.5%", 17.5, (double) 0));
        testCases.add(new TestCase("100×17.5%", 17.5, (double) 0));
        testCases.add(new TestCase("-1-5", (double) -6, (double) 0));

        //This section tests the operators
        testCases.add(new TestCase("sin45", 0.7071, 0.002));
        testCases.add(new TestCase("cos5", 0.9961, 0.002));
        testCases.add(new TestCase("tan12", 0.2125, 0.002));
        testCases.add(new TestCase("cos⁻¹0.7071", 45d, 0.002));
        testCases.add(new TestCase("sin⁻¹0.7071", 45d, 0.002));
        testCases.add(new TestCase("tan⁻¹0.2", 11.3099, 0.002));
        testCases.add(new TestCase("10nPr5", 30240d, 0d));
        testCases.add(new TestCase("15nCr7", 6435d, 0d));
        testCases.add(new TestCase("ln400", 5.99146, 0.0002));
        testCases.add(new TestCase("log₁₀876", 2.9425, 0.002));
        testCases.add(new TestCase("25^4", 390625d, 0d));
        testCases.add(new TestCase("10!", 3628800d, 0d));
        testCases.add(new TestCase("√26", 5.099, 0.002));
        testCases.add(new TestCase("∛50", 3.684, 0.002));
        testCases.add(new TestCase("15%", 0.15, 0d));
        testCases.add(new TestCase("180×e-π", 486.1491365, 0.000002));
        testCases.add(new TestCase("25²+5³", 750d, 0d));

        //This section tests shorthand multiplication
        testCases.add(new TestCase("2(10+2)" , 24d, 0d));
        testCases.add(new TestCase("2+15(30)" , 452d, 0d));

        // This section is for more complex test cases demonstrating BODMAS/BOMDAS function ordering
        testCases.add(new TestCase("55.888×1000.0÷80.1", 697.7278402, 0.00000002));
        testCases.add(new TestCase("45%×0.587+15nPr3", 2730.26415, 0d));
        testCases.add(new TestCase("sin(25×2+14)+sin5÷cos4", 0.9861626145, 0.0000000002));
        testCases.add(new TestCase("ln45+5×100-12.0008+log₁₀56", 493.5540505, 0.0000002));
        testCases.add(new TestCase("cos⁻¹0.2-sin⁻¹0.5+10nPr5-50", 30238.46304, 0.00002));
        testCases.add(new TestCase("√10+∛27-67%×e", 4.341028835, 0.000000002));
        testCases.add(new TestCase("4³×10²-(6!÷2)", 6040d, 0d));
        testCases.add(new TestCase("ln57-100×4^10+5!", -104857476d, 0.1d));
        testCases.add(new TestCase("2×sin30", 1d, 0.0001d));

        //testCases.add(new TestCase( , , ));

        // End of test case area, do not modify the code below.
        for (TestCase testCase : testCases) {
            try
            {
                Expression exp = new ExpressionParser().parse(testCase.input, true, 20, null);
                String assetString = String.format("Expression Parser Error, raw equation: %s; parsed equation: %s", testCase.input, exp.show());
                assertEquals(assetString, testCase.expected, exp.evaluate(), testCase.delta);
            }
            catch(ParserException e)
            {
                fail("ParserException: " + e.getErrorMessage());
            }
        }
    }

    /**
     * Generates an overflow by asking the factorial of a large number. Then squares that number.
     * The parser should return a exception rather than crashing.
     *
     * @author: Michael Betterton (u6797866)
     * @modified: Samuel Brookes (u5380100)
     *  - 06/09/2019: added Exception expectation, corrected spelling
     */
    @Test (expected = InfinityException.class)
    public void testInfinity() throws ParserException {
        // First generate a obscenely large number
        String infinity_expression = "625!";
        Expression exp = new ExpressionParser().parse(infinity_expression, true, 0, null);
        double infinity = exp.evaluate();

        // Concatenate that number with it to the power of 3.
        String exception_expression = infinity+"^3";
        exp = new ExpressionParser().parse(exception_expression, true, 0, null);
        double exception = exp.evaluate();
    }

    /**
     * Tests whether the RandomNumberExpression truly generates random numbers.
     *
     * @author Samuel Brookes (u5380100)
     */
    @Test
    public void testRandomNumber()
    {
        try
        {
            /*
             * Test whether random numbers are generated
             */
            ArrayList<Expression> randomNumbers = new ArrayList<>(0);
            for(int i=0; i<500; i++)
            {
                randomNumbers.add(new ExpressionParser().parse("rand", true, 10, null));
            }

            for(int i=0; i<500; i++)
            {
                for(int j=i+1; j<500; j++)
                {
                        assertNotEquals(randomNumbers.get(i).evaluate(),
                                randomNumbers.get(j).evaluate());

                }
            }
        }
        catch(ParserException e)
        {
            fail("ParserException: " + e.getErrorMessage());
        }
    }

    /**
     * Tests whether the precision functionality is working as intended.
     *
     * @author Samuel Brookes (u5380100)
     */
    @Test
    public void testPrecision()
    {
        try
        {
            double[] values = new double[15];
            for(int i=0; i<15; i++)
            {
                values[i] = new ExpressionParser().parse("π", false, i, null).evaluate();
            }

            for(int i=0; i<values.length; i++)
            {
                String value = String.valueOf(values[i]);
                assertEquals(value.substring(value.indexOf('.') + 1).length(), i, 1d); //delta of one required for rounding
            }
        }
        catch(ParserException e)
        {
            fail("ParserException: " + e.getErrorMessage());
        }
    }

    /**
     * Tests whether all unknown variables are correctly identified by the parser.
     *
     * @author Samuel Brookes
     */
    @Test
    public void testAllUnknownVariables()
    {
        String[] zorbaChars = {"ɑ", "β", "ɣ", "Δ"};
        Expression actual;
        try
        {
            //first, test every letter of the alphabet
            for(char variable = 'a'; variable <= 'z'; variable++)
            {
                actual = new ExpressionParser().parse("" + variable, false, 20, null);
                if(variable != 'e')
                {
                    assertEquals(UnknownVariableExpression.class, actual.getClass());
                }
                else
                {
                    assertEquals(EExpression.class, actual.getClass());
                }
            }

            //then test all the greek characters
            for(int i=0; i<zorbaChars.length; i++)
            {
                actual = new ExpressionParser().parse("" + zorbaChars[i], false, 20, null);
                assertEquals(UnknownVariableExpression.class, actual.getClass());
            }

        }
        catch(ParserException e)
        {
            fail("ParserException: " + e.getErrorMessage());
        }
    }

    /**
     * Tests whether the parser is correctly inserting a multiplication symbol
     * when parsing expressions that use unknown variables with shorthand multiplication
     *
     * @author Samuel Brookes (u5380100)
     */
    @Test
    public void testShorthandMultiplicationWithUnknowns()
    {
        try
        {
            for(char variable = 'a'; variable <= 'z'; variable++) {
                //testCases
                assertEquals(new ExpressionParser().parse("2" + variable + "+15(30)", false, 20, null).show(),
                        "((2.0×" + variable + ")+(15.0×30.0))");
                assertEquals(new ExpressionParser().parse("14+3" + variable + "²-100", false, 20, null).show(),
                        "((14.0+(3.0×(" + variable + "^2.0)))-100.0)");
            }
        }
        catch(ParserException e)
        {
            fail("Parser Exception: " + e.getErrorMessage());
        }
    }

    /**
     * Tests whether the ExpressionChecker is catching simple errors as expected
     *
     * @author Samuel Brookes (u5380100)
     */
    @Test
    public void testExpressionChecker()
    {
        ArrayList<String> testCases = new ArrayList<>(0);

        //user enters nothing
        testCases.add("");

        //Error messages
        for(Scripts.ErrorMessage error : Scripts.ErrorMessage.values())
        {
            if(error == Scripts.ErrorMessage.UNASSIGNED_VARIABLE)
                testCases.add("x" + error.getMessage());
            else
                testCases.add(error.getMessage());
        }

        //incorrect bracket nesting
        testCases.add("8+(15-[10×4)+25]");
        testCases.add("{25+(8-6}+14)×[35-2]");
        testCases.add("(100+5)×[12×2]+(18+[12-5)×2]");

        //missing bracket
        testCases.add("(8+15");
        testCases.add("12-14]");
        testCases.add("15-{6×2}-[12");

        //empty brackets
        testCases.add("2+10()-15");
        testCases.add("15-5+[]+4");
        testCases.add("8-{}+10");

        //incorrect use of functions
        testCases.add("x=x");
        testCases.add("=2y");
        testCases.add("x=2=5y");
        testCases.add("5=25x");
        testCases.add("10x=");
        testCases.add("=");

        assertEquals(testCases.size(), recursivelyCheckTestCases(testCases, 0, 0, false));

        //these should not throw an exception
        testCases = new ArrayList<>(0);
        testCases.add("8+[(2×15)-10]+4-5");
        testCases.add("(14+[8×25]-{2×100}-15)");
        testCases.add("(14+[8×{25-2}×100]-15)");

        assertEquals(0, recursivelyCheckTestCases(testCases, 0, 0, false));
    }

    /**
     * Test erroneous user input to see if it is
     * handled by the ParserExceptions.
     *
     * @author Samuel Brookes (u5380100)
     */
    @Test
    public void testExceptions()
    {
        ArrayList<String> testCases = new ArrayList<>(0);

        //Incorrect use of shorthand notation
        testCases.add("x5");
        testCases.add("2e2");
        testCases.add("2π2");
        testCases.add("xy6");

        //operators with no variables or numbers
        testCases.add("+");
        testCases.add("-");
        testCases.add("×");
        testCases.add("÷");
        testCases.add("sin");
        testCases.add("cos");
        testCases.add("tan");
        testCases.add("sin⁻¹");
        testCases.add("cos⁻¹");
        testCases.add("tan⁻¹");
        testCases.add("log₁₀");
        testCases.add("ln");
        testCases.add("!");
        testCases.add("√");
        testCases.add("∛");
        testCases.add("nPr");
        testCases.add("nCr");
        testCases.add("^");
        testCases.add("²");
        testCases.add("³");

        //garbage input
        testCases.add("12304987sdalkfhacvljasdfa908dr723jh");
        testCases.add("zxcvklzcpvoasiudt09q23845ojadslfa");
        testCases.add("+-×÷eπsincostansin⁻¹cos⁻¹tan⁻¹log₁₀ln!√∛nPrnCr^²³");

        //Each string in testCases should throw an Exception
        assertEquals(testCases.size(), recursivelyCheckTestCases(testCases, 0, 0, false));
    }

    /**
     * Tests that incorrect input for factorials is caught by exception handlers
     * @author Samuel Brookes (u5380100)
     */
    @Test
    public void testFactorialExceptions()
    {
        ArrayList<String> testCases = new ArrayList<>(0);
        testCases.add("1.5!");
        testCases.add("30000000000!");

        assertEquals(testCases.size(), recursivelyCheckTestCases(testCases, 0, 0, true));
    }

    /**
     * This method is used by the testExpressionChecker() method to recursively
     * iterate through the test cases in the ArrayList and count how many ParserExceptions
     * are thrown.
     *
     * @author Samuel Brookes (u5380100)
     *
     * @param testCases : an ArrayList of test case Strings
     * @param idx : the current index in testCases
     * @param errors : the number of errors
     * @return int : the number of errors thrown
     */
    private int recursivelyCheckTestCases(ArrayList<String> testCases, int idx, int errors, boolean evaluate)
    {
        //if the idx has reached the end of the ArrayList return the number of errors that occurred
        if(idx >= testCases.size()) return errors;
        else
        {
            try
            { //otherwise attempt to parse the incorrect expression
                Expression exp = new ExpressionParser().parse(testCases.get(idx), true, 5, null);
                if(evaluate) exp.evaluate();
            }
            catch(ParserException e)
            {
                return recursivelyCheckTestCases(testCases, idx + 1, errors + 1, evaluate);
            }
            catch(Exception e)
            { //if any other exception is thrown, abort
                return errors;
            }
            //if this statement is reached then no error was thrown, abort
            return errors;
        }
    }

    /**
     * Tests whether parser can properly evaluate scientific notation
     * @author Samuel Brookes (u5380100)
     */
    @Test
    public void testScientificNotation() throws ParserException
    {
        ArrayList<TestCase> testCases = new ArrayList<>(0);
        testCases.add(new TestCase("5.024E3×5.024E3", 25240576d, 0d));
        testCases.add(new TestCase("1.23456789E8÷200000π",1939.25471, 0.00002));
        testCases.add(new TestCase("1.2345E4(9.8765E4)", 1219253925d, 0d));

        for(TestCase test : testCases)
        {
            assertEquals(test.expected, new ExpressionParser().parse(test.input, true, 20, null).evaluate(), test.delta);
        }
    }
}


