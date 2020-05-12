package com.anu.calculator;

import org.junit.Test;

import com.anu.calculator.utilities.Token;
import com.anu.calculator.utilities.Tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * This test class tests the functionality of the Tokenizer class.
 */
public class TokenizerTest {
    // Tokenizer test class, each test case will instantiate this class.
    private class TestCase {
        private String testcase;
        private List<Token.Type> tokens;

        TestCase(String testcase, List<Token.Type> tokens) {
            this.testcase = testcase;
            this.tokens = tokens;
        }
    }

    // Instantiate a tokenizer to use in the tests.
    private static Tokenizer tokenizer;

    /**
     * Simple unit tests to make sure basic addition works.
     *
     * @author: Michael Betterton (u6797866)
     * @modified: Samuel Brookes (u5380100)
     *  - 06/09/2019: Reversed token orders where applicable
     */
    @Test
    public void testAddition() {
        // Declare each of the test cases
        ArrayList<TestCase> testCases = new ArrayList<>(0);
        testCases.add(new TestCase("1+1",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.ADD, Token.Type.DOUBLE)));
        testCases.add(new TestCase("5+",
                Arrays.asList(Token.Type.ADD, Token.Type.DOUBLE)));
        testCases.add(new TestCase("1000+1+1+",
                Arrays.asList( Token.Type.ADD, Token.Type.DOUBLE, Token.Type.ADD, Token.Type.DOUBLE, Token.Type.ADD, Token.Type.DOUBLE)));

        // Run each test case programmatically by looping over the cases
        for (int i = 0; i < testCases.size(); i++) {
            tokenizer = new Tokenizer(testCases.get(i).testcase);
            for (Token.Type token : testCases.get(i).tokens) {
                assertEquals("wrong token type", token, tokenizer.current().type());
                tokenizer.next();
            }
        }
    }

    /**
     * Simple unit tests to make sure basic subtraction works.
     *
     * @author: Michael Betterton (u6797866)
     * @modified: Samuel Brookes (u5380100)
     *  - 06/09/2019: Reversed token orders where applicable
     */
    @Test
    public void testSubtraction() {
        // Declare each of the test cases
        ArrayList<TestCase> testCases = new ArrayList<>(0);
        testCases.add(new TestCase("8-1",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.SUBTRACT, Token.Type.DOUBLE)));
        testCases.add(new TestCase("5--",
                Arrays.asList(Token.Type.SUBTRACT, Token.Type.SUBTRACT, Token.Type.DOUBLE)));
        testCases.add(new TestCase("1124-124-1",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.SUBTRACT, Token.Type.DOUBLE, Token.Type.SUBTRACT, Token.Type.DOUBLE)));

        // Run each test case programmatically by looping over the cases
        for (int i = 0; i < testCases.size(); i++) {
            tokenizer = new Tokenizer(testCases.get(i).testcase);
            for (Token.Type token : testCases.get(i).tokens) {
                assertEquals("wrong token type", token, tokenizer.current().type());
                tokenizer.next();
            }
        }
    }

    /**
     * Tests whether all operation tokens are being appropriately tokenized
     *
     * @author: Samuel Brookes (u5380100)
     */
    @Test
    public void testSimpleOperations()
    {
        ArrayList<TestCase> testCases = new ArrayList<>(0);
        testCases.add(new TestCase("(0.5)%",
                Arrays.asList(Token.Type.PERCENT, Token.Type.RIGHT_PARENTHESIS, Token.Type.DOUBLE, Token.Type.LEFT_PARENTHESIS)));
        testCases.add(new TestCase("10.859÷4",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.DIVIDE, Token.Type.DOUBLE)));
        testCases.add(new TestCase("2.45×5",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.MULTIPLY, Token.Type.DOUBLE)));
        testCases.add(new TestCase("ɑ+β+ɣ+Δ",
                Arrays.asList(Token.Type.UNKNOWN_VARIABLE, Token.Type.ADD, Token.Type.UNKNOWN_VARIABLE, Token.Type.ADD,
                        Token.Type.UNKNOWN_VARIABLE, Token.Type.ADD, Token.Type.UNKNOWN_VARIABLE)));
        testCases.add(new TestCase("w-x-y-z",
                Arrays.asList(Token.Type.UNKNOWN_VARIABLE, Token.Type.SUBTRACT, Token.Type.UNKNOWN_VARIABLE, Token.Type.SUBTRACT,
                        Token.Type.UNKNOWN_VARIABLE, Token.Type.SUBTRACT, Token.Type.UNKNOWN_VARIABLE)));
        testCases.add(new TestCase("sin(45)",
                Arrays.asList(Token.Type.RIGHT_PARENTHESIS, Token.Type.DOUBLE, Token.Type.LEFT_PARENTHESIS, Token.Type.SINE)));
        testCases.add(new TestCase("cos(90)",
                Arrays.asList(Token.Type.RIGHT_PARENTHESIS, Token.Type.DOUBLE, Token.Type.LEFT_PARENTHESIS, Token.Type.COSINE)));
        testCases.add(new TestCase("tan(18)",
                Arrays.asList(Token.Type.RIGHT_PARENTHESIS, Token.Type.DOUBLE, Token.Type.LEFT_PARENTHESIS, Token.Type.TANGENT)));
        testCases.add(new TestCase("ln(45)",
                Arrays.asList(Token.Type.RIGHT_PARENTHESIS, Token.Type.DOUBLE, Token.Type.LEFT_PARENTHESIS, Token.Type.LOG_NATURAL)));
        testCases.add(new TestCase("log₁₀(576)",
                Arrays.asList(Token.Type.RIGHT_PARENTHESIS, Token.Type.DOUBLE, Token.Type.LEFT_PARENTHESIS, Token.Type.LOG_TEN)));
        testCases.add(new TestCase("(25.78)²",
                Arrays.asList(Token.Type.SQUARE, Token.Type.RIGHT_PARENTHESIS, Token.Type.DOUBLE, Token.Type.LEFT_PARENTHESIS)));
        testCases.add(new TestCase("135.5^5.2",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.POWER, Token.Type.DOUBLE)));
        testCases.add(new TestCase("(57)³",
                Arrays.asList(Token.Type.CUBE, Token.Type.RIGHT_PARENTHESIS, Token.Type.DOUBLE, Token.Type.LEFT_PARENTHESIS)));
        testCases.add(new TestCase("57nPr5",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.PERMUTATION, Token.Type.DOUBLE)));
        testCases.add(new TestCase("100nCr4",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.COMBINATION, Token.Type.DOUBLE)));
        testCases.add(new TestCase("25.12×π",
                Arrays.asList(Token.Type.PI, Token.Type.MULTIPLY, Token.Type.DOUBLE)));
        testCases.add(new TestCase("47×e",
                Arrays.asList(Token.Type.E, Token.Type.MULTIPLY, Token.Type.DOUBLE)));
        testCases.add(new TestCase("24+√45",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.SQUARE_ROOT, Token.Type.ADD, Token.Type.DOUBLE)));
        testCases.add(new TestCase("sin⁻¹97.4",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.ARC_SINE)));
        testCases.add(new TestCase("cos⁻¹24",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.ARC_COSINE)));
        testCases.add(new TestCase("tan⁻¹105.3",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.ARC_TANGENT)));
        testCases.add(new TestCase("24.5+∛475",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.CUBED_ROOT, Token.Type.ADD, Token.Type.DOUBLE)));
        testCases.add(new TestCase("24.5+rand×100",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.MULTIPLY, Token.Type.RANDOM_NUMBER, Token.Type.ADD, Token.Type.DOUBLE)));
        testCases.add(new TestCase("24-!72.45",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.FACTORIAL, Token.Type.SUBTRACT, Token.Type.DOUBLE)));
        testCases.add(new TestCase("67.9%",
                Arrays.asList(Token.Type.PERCENT, Token.Type.DOUBLE)));
        testCases.add(new TestCase("-(45)-108.2",
                Arrays.asList(Token.Type.DOUBLE, Token.Type.SUBTRACT, Token.Type.RIGHT_PARENTHESIS, Token.Type.DOUBLE, Token.Type.LEFT_PARENTHESIS, Token.Type.SUBTRACT)));
        /*
        testCases.add(new TestCase(,
               Arrays.asList()));
        * */
        int i = 0;
        int tokenCount;
        try
        {
            for (i = 0; i < testCases.size(); i++) {
                tokenizer = new Tokenizer(testCases.get(i).testcase);
                tokenCount = 1;
                for (Token.Type token : testCases.get(i).tokens) {
                    assertEquals("Wrong type at token " + tokenCount + " in test case \"" + testCases.get(i).testcase + "\"", token, tokenizer.current().type());
                    tokenizer.next();
                    tokenCount++;
                }
            }
        }
        catch(NullPointerException e)
        {
            System.out.println("NullPointException @ test case \"" + testCases.get(i).testcase + "\"");
            fail();
        }
    }

    /**
     * This is a quick test to make sure the .checkAhead() method in the Tokenizer
     * is functioning correctly.
     *
     * @author: Samuel Brookes (u5380100)
     */
    @Test
    public void testCheckAhead()
    {
        String testCase = "10.0×14.0-100÷π+e";
        Token.Type tokens[] = {Token.Type.E, Token.Type.ADD, Token.Type.PI,
                                    Token.Type.DIVIDE, Token.Type.DOUBLE, Token.Type.SUBTRACT,
                                    Token.Type.DOUBLE, Token.Type.MULTIPLY, Token.Type.DOUBLE};

        Tokenizer tokenizer = new Tokenizer(testCase);
        int current = 0, next = 1, afterNext = 2;
        while(tokenizer.hasNext() && tokenizer.checkAhead(2) != null)
        {
            assertEquals(tokenizer.current().type(), tokens[current++]);
            assertEquals(tokenizer.checkAhead(1).type(), tokens[next++]);
            assertEquals(tokenizer.checkAhead(2).type(), tokens[afterNext++]);
            tokenizer.next();
        }
    }
}
