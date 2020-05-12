package com.anu.calculator.utilities;

/**
 *  Scripts: contains three enumerations (SuperScript, SubScript and Operator)
 *  that make it easier to retrieve the unicode characters used by the calculator
 *
 * @author: Samuel Brookes (u5380100)
 */

public class Scripts {

    /**
     * This enumeration contains superscripts (numbers, minus sign and parentheses).
     * @author Samuel Brookes (u5380100)
     */
    public enum SuperScript
    {
        ONE('\u00b9'),
        TWO('\u00b2'),
        THREE('\u00b3'),
        FOUR('\u2074'),
        FIVE('\u2075'),
        SIX('\u2076'),
        SEVEN('\u2077'),
        EIGHT('\u2078'),
        NINE('\u2079'),
        ZERO('\u2070'),
        MINUS('\u207b'),
        LEFT_PARENTHESIS('\u207d'),
        RIGHT_PARENTHESIS('\u207e');

        private char unicode;

        SuperScript(char unicode)
        {
            this.unicode = unicode;
        }

        public char getUnicode() { return unicode; }
    }

    /**
     * This enumeration contains subscripts (numbers, minus sign and parentheses).
     * @author Samuel Brookes (u5380100)
     */
    public enum SubScript
    {
        ONE('\u2081'),
        TWO('\u2082'),
        THREE('\u2083'),
        FOUR('\u2084'),
        FIVE('\u2085'),
        SIX('\u2086'),
        SEVEN('\u2087'),
        EIGHT('\u2088'),
        NINE('\u2089'),
        ZERO('\u2080'),
        LEFT_PARENTHESIS('\u208d'),
        RIGHT_PARENTHESIS('\u208e');

        private char unicode;

        SubScript(char unicode)
        {
            this.unicode = unicode;
        }

        public char getUnicode() { return unicode; }
    }

    /**
     * This enumeration contains mathematical operators.
     *
     * @author Samuel Brookes (u5380100)
     */
    public enum Operator
    {
        SQRT('\u221a'),
        CUBE_ROOT('\u221b'),
        PI('\u03c0'),
        MULTIPLY('\u00d7'),
        DIVIDE('\u00f7');

        private char unicode;

        Operator(char unicode)
        {
            this.unicode = unicode;
        }

        public char getUnicode(){ return unicode; }
    }

    /**
     * This enumeration contains all error messages
     * returned by ParserExceptions so that they are easier to
     * detect by the ExpressionParser, and are homogenised.
     *
     * @author Samuel Brookes (u5380100)
     */
    public enum ErrorMessage
    {
        SYNTAX_ERROR("SYNTAX ERROR!"),
        ZERO_DIVISION("DIVISION BY ZERO!"),
        NUMBER_TOO_LARGE("NUMBER TOO LARGE!"),
        NOTHING_ENTERED(""),
        UNASSIGNED_VARIABLE(" HAS NO VALUE!"),
        CANNOT_SOLVE("CANNOT SOLVE!");

        private String msg;

        ErrorMessage(String msg)
        {
            this.msg = msg;
        }

        public String getMessage()
        {
            return msg;
        }

        public boolean containsErrorMessage(String s)
        {
            for(ErrorMessage msg : ErrorMessage.values())
            {
                if(msg != NOTHING_ENTERED)
                {
                    if(s.contains(msg.getMessage()))
                        return true;
                }
            }
            return false;
        }
    }
}
