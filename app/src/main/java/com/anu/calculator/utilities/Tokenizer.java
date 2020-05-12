package com.anu.calculator.utilities;

/**
 * Tokenizer: This is the tokenizer that converts components of a mathematical expression in
 * String form into tokens for the parser. The Tokenizer next() method works in reverse.
 *
 * @author: Michael Betterton (u6797866)
 * @modified: Samuel Brookes (u5380100)
 *  - Wrote the next() method
 *  - Wrote the checkAhead() method
 *  - Wrote the appendMultiply() method
 */

public class Tokenizer {

    private String _buffer;
    private Token currentToken;

    public Tokenizer(String text)
    {
        _buffer = text;
        next();
    }

    public Token current()
    {
        return currentToken;
    }

    public boolean hasNext()
    {
        return currentToken != null;
    }

    /**
     * Parsing trees are built recursively. Therefore, to ensure that operators with equal
     * precedence (e.g. addition and subtraction, multiplication and division) are evaluated
     * from left to right, an expression must be parsed BACKWARDS. The next() method therefore
     * tokenises a string from RIGHT TO LEFT.
     *
     * @author Samuel Brookes (u5380100)
     */
    public void next()
    {
        _buffer = _buffer.trim();

        //buffer is empty - set currentToken to null
        if(_buffer.equals(""))
        {
            currentToken = null;
            return;
        }

        char lastChar = _buffer.charAt(_buffer.length() - 1);

        if(lastChar == '+')
            currentToken = new Token("+", Token.Type.ADD);
        else if(lastChar == '-')
            currentToken = new Token("-", Token.Type.SUBTRACT);
        else if(lastChar == Scripts.Operator.DIVIDE.getUnicode())
            currentToken = new Token("" + Scripts.Operator.DIVIDE.getUnicode(), Token.Type.DIVIDE);
        else if(lastChar == '=')
            currentToken = new Token("=", Token.Type.EQUALS);
        else if(lastChar == '(')
            currentToken = new Token("(", Token.Type.LEFT_PARENTHESIS);
        else if(lastChar == '{')
            currentToken = new Token("{", Token.Type.LEFT_BRACE);
        else if(lastChar == '[')
            currentToken = new Token("[", Token.Type.LEFT_BRACKET);
        else if(lastChar == ')')
            currentToken = new Token(")", Token.Type.RIGHT_PARENTHESIS);
        else if(lastChar == '}')
            currentToken = new Token("}", Token.Type.RIGHT_BRACE);
        else if(lastChar == ']')
            currentToken = new Token("]", Token.Type.RIGHT_BRACKET);
        else if(lastChar == '!')
            currentToken = new Token("!", Token.Type.FACTORIAL);
        else if(lastChar == 'e')
            currentToken = new Token("e", Token.Type.E);
        else if(lastChar == 'E')
            currentToken = new Token("E", Token.Type.SCIENTIFIC_NOTATION);
        else if(lastChar == Scripts.Operator.MULTIPLY.getUnicode())
            currentToken = new Token("" + Scripts.Operator.MULTIPLY.getUnicode(), Token.Type.MULTIPLY);
        else if(lastChar == '%')
            currentToken = new Token("%", Token.Type.PERCENT);
        else if(lastChar == Scripts.Operator.PI.getUnicode())
            currentToken = new Token("" + Scripts.Operator.PI.getUnicode(), Token.Type.PI);
        else if(lastChar == Scripts.Operator.SQRT.getUnicode())
            currentToken = new Token("" + Scripts.Operator.SQRT.getUnicode(), Token.Type.SQUARE_ROOT);
        else if(lastChar == Scripts.Operator.CUBE_ROOT.getUnicode())
            currentToken = new Token("" + Scripts.Operator.CUBE_ROOT.getUnicode(), Token.Type.CUBED_ROOT);
        else if(lastChar == '^')
            currentToken = new Token("^", Token.Type.POWER);
        else if(lastChar == Scripts.SuperScript.TWO.getUnicode())
            currentToken = new Token("" + Scripts.SuperScript.TWO.getUnicode(), Token.Type.SQUARE);
        else if(lastChar == Scripts.SuperScript.THREE.getUnicode())
            currentToken = new Token("" + Scripts.SuperScript.THREE.getUnicode(), Token.Type.CUBE);
        else if(lastChar == Scripts.SubScript.ZERO.getUnicode())
            currentToken = new Token("log" +
                    Scripts.SubScript.ONE.getUnicode() +
                    Scripts.SubScript.ZERO.getUnicode(), Token.Type.LOG_TEN);
        else if(lastChar == 'd')
        {
            if(_buffer.length() > 2)
            {
                switch(_buffer.charAt(_buffer.length() - 2))
                {
                    case 'n': currentToken = new Token("rand", Token.Type.RANDOM_NUMBER); break;
                    default : currentToken = new Token("d", Token.Type.UNKNOWN_VARIABLE);
                }
            }
            else currentToken = new Token("d", Token.Type.UNKNOWN_VARIABLE);
        }
        else if(lastChar == 's')
        {
            if(_buffer.length() > 2)
            {
                switch(_buffer.charAt(_buffer.length() - 2))
                {
                    case 'o': currentToken = new Token("cos", Token.Type.COSINE); break;
                    default : currentToken = new Token("s", Token.Type.UNKNOWN_VARIABLE);
                }
            }
            else currentToken = new Token("s", Token.Type.UNKNOWN_VARIABLE);
        }
        else if(lastChar == 'n')
        {
            if(_buffer.length() > 2)
            {
                switch(_buffer.charAt(_buffer.length() - 2))
                {
                    case 'i': currentToken = new Token("sin", Token.Type.SINE); break;
                    case 'a': currentToken = new Token("tan", Token.Type.TANGENT); break;
                    case 'l': currentToken = new Token("ln", Token.Type.LOG_NATURAL); break;
                    default : currentToken = new Token("n", Token.Type.UNKNOWN_VARIABLE);
                }
            }
            else if(_buffer.length() == 2)
            {
                switch(_buffer.charAt(_buffer.length() - 2))
                {
                    case 'l': currentToken = new Token("ln", Token.Type.LOG_NATURAL); break;
                    default : currentToken = new Token("n", Token.Type.UNKNOWN_VARIABLE);
                }
            }
            else currentToken = new Token("n", Token.Type.UNKNOWN_VARIABLE);
        }
        else if(lastChar == 'r')
        {
            if(_buffer.length() > 2)
            {
                switch(_buffer.charAt(_buffer.length() - 2))
                {
                    case 'P': currentToken = new Token("nPr", Token.Type.PERMUTATION); break;
                    case 'C': currentToken = new Token("nCr", Token.Type.COMBINATION); break;
                    default : currentToken = new Token("r", Token.Type.UNKNOWN_VARIABLE);
                }
            }
            else currentToken = new Token("r", Token.Type.UNKNOWN_VARIABLE);
        }
        else if(lastChar == Scripts.SuperScript.ONE.getUnicode())
        {
            switch(_buffer.charAt(_buffer.length() - 4))
            {
                case 'i': currentToken = new Token("sin" + Scripts.SuperScript.MINUS.getUnicode() +
                        Scripts.SuperScript.ONE.getUnicode(), Token.Type.ARC_SINE); break;
                case 'o': currentToken = new Token("cos" + Scripts.SuperScript.MINUS.getUnicode() +
                        Scripts.SuperScript.ONE.getUnicode(), Token.Type.ARC_COSINE); break;
                case 'a': currentToken = new Token("tan" + Scripts.SuperScript.MINUS.getUnicode() +
                        Scripts.SuperScript.ONE.getUnicode(), Token.Type.ARC_TANGENT);
            }
        }
        else if(Character.isDigit(lastChar))
        {
            int i;
            for(i=_buffer.length() - 1; i>=0; i--)
            {
                if(!Character.isDigit(_buffer.charAt(i)))
                {
                    if(_buffer.charAt(i) != '.') break;
                }
            }
            currentToken = new Token(_buffer.substring(i + 1), Token.Type.DOUBLE);
        }
        else currentToken = new Token(_buffer.substring(_buffer.length() - 1), Token.Type.UNKNOWN_VARIABLE);

        // Remove the extracted token from buffer
        int tokenLen = currentToken.token().length();
        _buffer = _buffer.substring(0, _buffer.length() - tokenLen);
    }

    /**
     * This method allows the tokenizer to check a token beyond the current token
     * without affecting the current buffer.
     *
     * @param numTokens : the number of tokens ahead that the tokenizer should check
     * @return Token : the Token that is numTokens before the current token
     * @author Samuel Brookes (u5380100)
     */
    public Token checkAhead(int numTokens)
    {
        Tokenizer checker = new Tokenizer(_buffer);
        while(numTokens > 1 && checker.current() != null)
        {
            checker.next();
            numTokens--;
        }
        return checker.current();
    }

    /**
     * This method appends the buffer with a MULTIPLICATION operator. It does so to
     * allow the normal parseExp() method in the ExpressionParser to evaluate shorthand multiplication.
     *
     * @author Samuel Brookes (u5380100)
     */
    public void appendMultiply()
    {
        _buffer = _buffer + Scripts.Operator.MULTIPLY.getUnicode();
    }
}