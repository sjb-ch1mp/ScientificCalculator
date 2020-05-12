package com.anu.calculator.parsers;

import com.anu.calculator.Expression;
import com.anu.calculator.Parser;
import com.anu.calculator.ParserException;
import com.anu.calculator.exceptions.InfinityException;
import com.anu.calculator.exceptions.MathematicalSyntaxException;
import com.anu.calculator.expressions.*;
import com.anu.calculator.utilities.ExpressionChecker;
import com.anu.calculator.utilities.History;
import com.anu.calculator.utilities.HistoryItem;
import com.anu.calculator.utilities.Scripts;
import com.anu.calculator.utilities.Token;
import com.anu.calculator.utilities.Tokenizer;

import java.util.HashMap;


/**
 * Parser: The primary parser for evaluating the mathematical statements entered by the user.
 * The parser parses mathematical expressions in reverse to ensure that equivalent operators
 * (e.g. ADD and SUBTRACT, or MULTIPLY and DIVIDE) are evaluated left to right.
 * The parser uses the following grammar.
 *
 * <exp> ::= <term> | <exp> + <term> | <exp> − <term>
 * <term> ::= <operation> | <term> × <operation> | <term> ÷ <operation>
 * <operation> ::= sin<exp> | sin⁻¹<exp> | cos<exp> | cos⁻¹<exp> |
 *      tan<exp> | tan⁻¹<exp> | log₁₀<exp> | ln<exp> | <exp>! | √<exp> |
 *      ∛<exp> | <exp>nPr<exp> | <exp>nCr<exp> | <exp>^<exp> | <exp>² |
 *      <exp>³ | <exp>% | (<exp>) | <literal>
 * <literal> ::= π | e | rand | double | -double | <unknown variable>
 * <unknown variable> ::= a-d, f-z | ɑ | β | ɣ | Δ
 *
 * @author: Samuel Brookes (u5380100)
 * @modified: Michael Betterton (u6797866)
 *  - 05/09/2019: Refactored class to implement ExpressionParser interface
 * 	- 05/09/2019: Refactored name from ExpressionParser to Parser
 */

public class ExpressionParser implements Parser
{
    private static final String TAG = "EXPRESSION_PARSER";
    private Tokenizer _tokenizer;
    private Boolean degrees;
    private History history;
    private HashMap<Character, HistoryItem> rawHistory;

    /**
     * @author Samuel Brookes (u5380100)
     */
    @Override
    public Expression parse(String expression, Boolean degrees, Integer precision, History history) throws ParserException
    {
        //Check that the expression is valid
        new ExpressionChecker(expression).checkExpression();

        //Initialise global variables
        this.history = history;
        this.degrees = degrees;
        _tokenizer = new Tokenizer(expression);

        //Evaluate everything on the right-hand side of the equation (if there is an equals sign),
        //then check whether this is an equality expression
        return checkForEqualityExpression(parseExp(), precision);
    }

    /**
     * Parse method for the history class. This parse method is used by the History class to
     * process an ordered history and assign values to variables. This is the only method that assigns
     * values to variables.
     *
     * @param expression : the expression to be parsed
     * @param rawHistory : a HashMap of history items used to assign values to variables in the expression
     * @return Expression : a parsed Expression object
     * @author Samuel Brookes (u5380100)
     */
    public Expression parseHistory(String expression, Boolean degrees, HashMap<Character, HistoryItem> rawHistory) throws ParserException
    {
        //Check that the expression is valid
        new ExpressionChecker(expression).checkExpression();

        //Initialise the variables
        this.degrees = degrees;
        this.rawHistory = rawHistory;
        _tokenizer = new Tokenizer(expression);

        //Parse the expression
        return checkForEqualityExpression(parseExp(), null);
    }

    /**
     * This method checks for whether there is a hanging equals sign once
     * the parseExp() method is completed. If there is a hanging equals sign,
     * an EqualityExpression is returned, otherwise the parsed expression
     * is returned unchanged.
     *
     * @param exp : the parsed expression
     * @param precision : the user-defined precision for evaluating the root of the parsing tree
     * @return Expression : either the unchanged parsed expression, or an EqualityExpression
     * @author Samuel Brookes (u5380100)
     */
    private Expression checkForEqualityExpression(Expression exp, Integer precision) throws ParserException
    {
        EqualityExpression equality = null;

        //check if there is a hanging equals sign
        if(_tokenizer.hasNext())
        {
            if(_tokenizer.current().type() == Token.Type.EQUALS)
            {
                //If the current token is EQUALS, this is an equality expression
                _tokenizer.next();
                equality = new EqualityExpression(_tokenizer.current().token().charAt(0), exp);
                if(precision != null) equality.updatePrecision(precision);
            }
            else throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage()); //"Syntax error: missing operators"
        }
        else
        {
            //Otherwise it is another kind of expression
            if(precision != null) exp.updatePrecision(precision);
        }

        return (equality == null)? exp : equality;
    }

    /**
     * parseExp: The top level parsing method, ensuring that addition and subtraction are done
     * last (i.e. BODM[AS]).
     *
     * Grammar: <exp> ::= <term> | <exp> + <term> | <exp> − <term>
     *
     * @return type: Expression
     * @throws ParserException if parseTerm call returns null
     * @author Samuel Brookes (u5380100)
     */
    private Expression parseExp() throws ParserException
    {
        Expression term = parseTerm();
        if(term == null)
            throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());

        //check whether there this is an addition or subtraction
        if(_tokenizer.hasNext() && (_tokenizer.current().type() == Token.Type.ADD ||
                _tokenizer.current().type() == Token.Type.SUBTRACT))
        {
            Token holdToken = _tokenizer.current();
            _tokenizer.next();
            Expression expression = parseExp();
            return (holdToken.type() == Token.Type.ADD) ?
                    new AddExpression(expression, term) :
                    new SubtractExpression(expression, term);
        }
        else return term;
    }

    /**
     * parseTerm: The second level parsing method, ensuring that division and multiplication
     * are done second last (i.e. BO[DM]AS).
     *
     * Grammar: <term> ::= <operation> | <term> × <operation> | <term> ÷ <operation>
     *
     * @return type: Expression
     * @throws ParserException if parseOperation call returns null
     * @author Samuel Brookes (u5380100)
     */
    private Expression parseTerm() throws ParserException
    {
        Expression operation = parseOperation();
        if(operation == null)
            throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());

        //check if this is a division or multiplication
        if(_tokenizer.hasNext() && (_tokenizer.current().type() == Token.Type.DIVIDE ||
                _tokenizer.current().type() == Token.Type.MULTIPLY))
        {
            Token holdToken = _tokenizer.current();
            _tokenizer.next();
            Expression expression = parseTerm();
            return (holdToken.type() == Token.Type.DIVIDE) ?
                    new DivideExpression(expression, operation) :
                    new MultiplyExpression(expression, operation);
        }
        else return operation;
    }

    /**
     * parseOperation: The third level parsing method, ensuring that defined operations are
     * evaluated second (i.e. B[O]DMAS). This method handles operators differently depending
     * upon whether they are leading, trailing, or both (see Token class for more information).
     *
     * Uses grammar: <operation> ::= sin<exp> | sin⁻¹<exp> | cos<exp> | cos⁻¹<exp> |
     *                  tan<exp> | tan⁻¹<exp> | log₁₀<exp> | ln<exp> | <exp>! | √<exp> |
     *                  ∛<exp> | <exp>nPr<exp> | <exp>nCr<exp> | <exp>^<exp> | <exp>² |
     *                  <exp>³ | <exp>% | (<exp>) | <literal>
     *
     * @return type: Expression
     * @throws ParserException if parseLiteral call returns null
     * @author Samuel Brookes (u5380100)
     */
    private Expression parseOperation() throws ParserException
    {
        Expression literal;
        Token holdToken;

        //check if the current token is a trailing token
        if(_tokenizer.current().type().isTrailing())
        {
            //if it is - save the token, get the expression to which it applies and
            //return the associated Expression object
            holdToken = _tokenizer.current();
            _tokenizer.next();
            literal = parseLiteral();
            if(literal == null)
                throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());

            switch(holdToken.type())
            {
                case PERCENT: return new PercentExpression(literal);
                case FACTORIAL: return new FactorialExpression(literal);
                case SQUARE: return new PowerExpression(literal, new DoubleExpression(2));
                case CUBE: return new PowerExpression(literal, new DoubleExpression(3));
            }
        }

        //If the token is not a trailing token - get the next Expression
        literal = parseLiteral();
        if(literal == null)
            throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());

        //Check if the current token is leading
        if(_tokenizer.hasNext() && _tokenizer.current().type().isLeading())
        {
            //if it is, return the applicable Expression object
            holdToken = _tokenizer.current();
            _tokenizer.next();
            switch(holdToken.type())
            {
                case SINE: return new SineExpression(literal, degrees);
                case ARC_SINE: return new ArcSineExpression(literal, degrees);
                case COSINE: return new CosineExpression(literal, degrees);
                case ARC_COSINE: return new ArcCosineExpression(literal, degrees);
                case TANGENT: return new TangentExpression(literal, degrees);
                case ARC_TANGENT: return new ArcTangentExpression(literal, degrees);
                case LOG_NATURAL: return new LogNaturalExpression(literal);
                case LOG_TEN: return new LogTenExpression(literal);
                case SQUARE_ROOT: return new SquareRootExpression(literal);
                case CUBED_ROOT: return new CubedRootExpression(literal);
            }
        }
        //check if the current token is both leading and trailing
        else if(_tokenizer.hasNext() && _tokenizer.current().type().isLeadingAndTrailing())
        {
            //if it is, get the next Expression and return the applicable Expression type
            holdToken = _tokenizer.current();
            _tokenizer.next();
            Expression expression = parseLiteral();
            if(expression == null)
                throw new MathematicalSyntaxException(TAG, Scripts.ErrorMessage.SYNTAX_ERROR.getMessage());

            switch(holdToken.type())
            {
                case POWER: return new PowerExpression(expression, literal);
                case PERMUTATION: return new PermutationExpression(expression, literal);
                case COMBINATION: return new CombinationExpression(expression, literal);
                case SCIENTIFIC_NOTATION: return new ScientificNotationExpression(expression, literal);
            }
        }

        return literal;
    }

    /**
     * parseLiteral: The lowest level parsing method, returns literals and ensures that parentheses
     * are evaluated first (i.e. [B]ODMAS). This method handles shorthand multiplication (e.g.
     * 2x or 2(10)) by appending the buffer in the tokenizer with a multiplication symbol to
     * ensure that it is processed correctly.
     *
     * Uses grammar: <literal> ::= π | e | rand | double | -double | <unknown variable>
     *               <unknown variable> ::= a-d, f-z | ɑ | β | ɣ | Δ
     *
     * @return type: Expression
     * @throws ParserException if parseExp call throws ParserException
     * @author Samuel Brookes
     */
    private Expression parseLiteral() throws ParserException
    {
        Expression literal = null;

        if(_tokenizer.current() == null)
            return null;
        else if(_tokenizer.current().type() == Token.Type.RANDOM_NUMBER)
            literal = new RandomNumberExpression();
        else if(_tokenizer.current().type() == Token.Type.PI)
        {
            literal = new PiExpression();

            //check for the use of shorthand multiplication
            Token next = _tokenizer.checkAhead(1);
            if(next != null &&
                    (next.type() == Token.Type.DOUBLE ||
                     next.type() == Token.Type.PI ||
                     next.type() == Token.Type.E ||
                     next.type() == Token.Type.UNKNOWN_VARIABLE))
                _tokenizer.appendMultiply();
        }
        else if(_tokenizer.current().type() == Token.Type.E)
        {
            literal =  new EExpression();

            //check for the use of shorthand multiplication
            Token next = _tokenizer.checkAhead(1);
            if(next != null &&
                    (next.type() == Token.Type.DOUBLE ||
                     next.type() == Token.Type.PI ||
                     next.type() == Token.Type.E ||
                     next.type() == Token.Type.UNKNOWN_VARIABLE))
                _tokenizer.appendMultiply();
        }
        else if(_tokenizer.current().type() == Token.Type.UNKNOWN_VARIABLE)
        {
            char variable = _tokenizer.current().token().charAt(0);

            if(history != null)
            {
                if(history.hasVariable(variable))
                    literal = new UnknownVariableExpression(variable, history.getExpression(variable));
            }
            else if(rawHistory != null)
            {
                literal = new UnknownVariableExpression(variable, rawHistory.get(variable).getExpression());
            }

            if(literal == null)
                literal = new UnknownVariableExpression(variable);

            //check for the use of shorthand multiplication
            //for unknown variables, shorthand multiplication is valid if the next
            //token is either a double OR an unknown variable
            Token next = _tokenizer.checkAhead(1);
            if(next != null &&
                    (next.type() == Token.Type.DOUBLE ||
                     next.type() == Token.Type.PI ||
                     next.type() == Token.Type.E ||
                     next.type() == Token.Type.UNKNOWN_VARIABLE))
                _tokenizer.appendMultiply();
        }
        else if(_tokenizer.current().type() == Token.Type.RIGHT_PARENTHESIS ||
                _tokenizer.current().type() == Token.Type.RIGHT_BRACE ||
                _tokenizer.current().type() == Token.Type.RIGHT_BRACKET)
        {
            _tokenizer.next();
            literal = parseExp();

            //check for the use of shorthand multiplication
            Token next = _tokenizer.checkAhead(1);
            if(next != null &&
                    (next.type() == Token.Type.DOUBLE ||
                     next.type() == Token.Type.PI ||
                     next.type() == Token.Type.E))
                _tokenizer.appendMultiply();
        }
        else if(_tokenizer.current().type() == Token.Type.DOUBLE)
        {
            //returns either a 'negative double' or double
            DoubleExpression doubleValue = new DoubleExpression(Double.parseDouble(_tokenizer.current().token()));
            boolean negative = false;

            //check whether the double is a negative number
            Token next = _tokenizer.checkAhead(1);
            Token afterNext = _tokenizer.checkAhead(2);
            if(next != null && next.type() == Token.Type.SUBTRACT)
            {
                if( afterNext == null ||
                        afterNext.type() == Token.Type.SUBTRACT ||
                        afterNext.type() == Token.Type.ADD ||
                        afterNext.type() == Token.Type.MULTIPLY ||
                        afterNext.type() == Token.Type.DIVIDE ||
                        afterNext.type() == Token.Type.LEFT_PARENTHESIS ||
                        afterNext.type() == Token.Type.LEFT_BRACE ||
                        afterNext.type() == Token.Type.LEFT_BRACKET ||
                        afterNext.type() == Token.Type.EQUALS)
                {
                    //if it's negative, return the number subtracted from zero
                    literal = new SubtractExpression(new DoubleExpression(0d), doubleValue);
                    negative = true;
                    _tokenizer.next();
                }
            }

            //if it's not negative, return the double
            if(!negative)
                literal = doubleValue;
        }
        else return null;

        _tokenizer.next();
        return literal;
    }
}
