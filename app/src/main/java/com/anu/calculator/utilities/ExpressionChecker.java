package com.anu.calculator.utilities;

import com.anu.calculator.ParserException;
import com.anu.calculator.exceptions.ContainsErrorMessageException;
import com.anu.calculator.exceptions.MathematicalSyntaxException;
import com.anu.calculator.exceptions.NothingEnteredException;

import java.util.Stack;

/**
 * The ExpressionChecker class conducts simple tests of the user input to
 * catch any simple errors prior to parsing the expression.
 *
 * @author Samuel Brookes (u5380100)
 */
public class ExpressionChecker {

    private static final String TAG = "EXPRESSION_CHECKER";
    private String expression;

    public ExpressionChecker(String expression)
    {
        this.expression = expression;
    }

    /**
     * This is the public-facing method that is called to check that an expression
     * does not have any errors. This method does not return anything, but reports
     * the result of its check by throwing the appropriate ParserException.
     *
     * @throws ParserException : if something is wrong with the expression
     * @author Samuel Brookes (u5380100)
     */
    public void checkExpression() throws ParserException
    {
        //check for general syntax errors
        checkGeneral();

        //check for correct use of brackets
        checkBrackets();

        //if this is a function, check that the function syntax is correct
        if(expression.contains("=")) checkFunctions();
    }

    /**
     * This method checks for general syntax errors in the user input:
     *  - whether nothing was entered
     *  - whether the input contains an error message
     *
     * @throws ParserException : if something is wrong with the expression
     * @author Samuel Brookes (u5380100)
     */
    private void checkGeneral() throws ParserException
    {
        //check whether the user has entered something
        if(expression.equals(""))
            throw new NothingEnteredException(TAG, "");

        //check for error messages
        if(Scripts.ErrorMessage.SYNTAX_ERROR.containsErrorMessage(expression))
            throw new ContainsErrorMessageException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());
    }

    /**
     * This method tests that the user is using brackets correctly. For example,
     * whether they have forgotten to put in an open or closing bracket (e.g. "18+(2-5") or whether
     * they have incorrectly nested brackets (e.g. "{15+[8-9}+sin30]"). It does so by adding
     * brackets, braces and parentheses to a stack and removing them when the brackets pair
     * is encountered. If the tokenizer encounters a bracket, brace or parenthesis that is NOT
     * paired to the token on the top of the stack - the brackets are incorrectly nested.
     * If the stack is not empty at the end of the method, there are unpaired brackets.
     *
     * @throws ParserException : if brackets are used incorrectly
     * @author Samuel Brookes (u5380100)
     */
    private void checkBrackets() throws ParserException
    {
        //check for empty brackets
        if(expression.contains("()") || expression.contains("[]") || expression.contains("{}"))
            throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());

        //check the nesting of the brackets
        Stack<Token.Type> brackStack = new Stack<>();
        Tokenizer tokenizer = new Tokenizer(expression);
        while(tokenizer.hasNext())
        {//Remember: tokenizer moves right to left
            if(isBracket(tokenizer.current().type()))
            {//the current token is a bracket
                if(isLeft(tokenizer.current().type()))
                {
                    if(brackStack.empty() ||
                            !isPairTo(brackStack.pop(), tokenizer.current().type()))
                    {
                        throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());
                    }
                }
                else if(isRight(tokenizer.current().type()))
                {
                    brackStack.push(tokenizer.current().type());
                }
            }
            tokenizer.next();
        }
        if(!brackStack.empty())
            throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());
    }

    /**
     * This method is used by the checkBrackets() method to check whether a
     * token is a bracket, brace or parenthesis.
     *
     * @param token : the type of the current token
     * @return boolean : whether the token is a bracket, brace or parenthesis
     * @author Samuel Brookes (u5380100)
     */
    private boolean isBracket(Token.Type token)
    {
        return isLeft(token) || isRight(token);
    }

    /**
     * This method is used by the checkBrackets() method to check whether the
     * current token is a LEFT bracket, brace or parenthesis
     *
     * @param bracket : the current token (a bracket, brace or parenthesis)
     * @return boolean : whether the current token is a LEFT bracket, brace or parenthesis
     * @author Samuel Brookes (u5380100)
     */
    private boolean isLeft(Token.Type bracket)
    {
        return bracket == Token.Type.LEFT_BRACKET ||
                bracket == Token.Type.LEFT_BRACE ||
                bracket == Token.Type.LEFT_PARENTHESIS;
    }

    /**
     * This method is used by the checkBrackets() method to check whether the
     * current token is a RIGHT bracket, brace or parenthesis.
     *
     * @param bracket : the current token (a bracket, brace or parenthesis)
     * @return boolean : whether the current token is a RIGHT bracket, brace or parenthesis
     * @author Samuel Brookes (u5380100)
     */
    private boolean isRight(Token.Type bracket)
    {
        return bracket == Token.Type.RIGHT_BRACKET ||
                bracket == Token.Type.RIGHT_BRACE ||
                bracket == Token.Type.RIGHT_PARENTHESIS;
    }

    /**
     * This method is used by the checkBrackets() method to check whether the two brackets, braces
     * or parentheses are each other's pair, e.g. LEFT BRACE and RIGHT BRACE.
     * @param right : the token on the right side of an expression
     * @param left : the token on the left side of an expression
     * @return boolean : whether the tokens are each other's pair
     * @author Samuel Brookes (u5380100)
     */
    private boolean isPairTo(Token.Type right, Token.Type left)
    {
        switch(right)
        {
            case RIGHT_BRACE:
                return left == Token.Type.LEFT_BRACE;
            case RIGHT_BRACKET:
                return left == Token.Type.LEFT_BRACKET;
            case RIGHT_PARENTHESIS:
                return left == Token.Type.LEFT_PARENTHESIS;
            default:
                return false;
        }
    }

    /**
     * This method contains checks that are only valid for functions (equality expressions)
     *
     * @throws ParserException : if the function syntax is wrong
     * @author Samuel Brookes (u5380100)
     */
    private void checkFunctions() throws ParserException
    {
        //test that there is something on either side of the equals sign
        if(expression.length() < 3 || //if there's an equals sign, the expression must be at least 3 characters long
                expression.indexOf('=') == 0 || //if the equals sign is the first character
                expression.indexOf('=') == expression.length() - 1) //if the equals sign is the last character
            throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());

        //test that there is only one character on the LHS
        if(expression.split("=")[0].trim().length() > 1)
            throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.CANNOT_SOLVE.getMessage());

        //test that there is only one equals sign
        if(expression.indexOf('=') != expression.lastIndexOf('='))
            throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());

        //test that the left-hand side has only one variable and that it is an unknown var
        Token leftHandSide = new Tokenizer(expression.split("=")[0].trim()).current();
        if(leftHandSide == null)
            throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());
        else if(leftHandSide.type() != Token.Type.UNKNOWN_VARIABLE)
            throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.CANNOT_SOLVE.getMessage());

        //test that an unknown variable does not occur on either side of the equals sign
        char variable = expression.split("=")[0].trim().charAt(0);
        Tokenizer varCheck = new Tokenizer(expression.split("=")[1].trim());
        while(varCheck.hasNext())
        {
            if(varCheck.current().type() == Token.Type.UNKNOWN_VARIABLE && varCheck.current().token().charAt(0) == variable)
                throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.CANNOT_SOLVE.getMessage());
            varCheck.next();
        }
    }
}
