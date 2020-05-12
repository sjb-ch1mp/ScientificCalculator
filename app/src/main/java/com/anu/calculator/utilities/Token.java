package com.anu.calculator.utilities;

/**
 * Token: The class that defines the tokens used by the Tokenizer.
 * Each token type is included in the Type enumeration.
 * Each token has a 'position' relative to the parameters of its operation. These can be
 *  l (leading), t (trailing), b (both), or n (not applicable). For example, the parameters
 *  for the SINE operation come after the token, therefore SINE is considered 'leading'. Conversely,
 *  the parameter for the PERCENT operation comes before the token, therefore PERCENT is considered
 *  'trailing'.
 * Each token has a String representation associated with it - these are defined in values.strings.xml
 *
 * @author: Samuel Brookes (u5380100)
 */

public class Token {

    public enum Type {
        ADD('n'),
        ARC_COSINE('l'),
        ARC_SINE('l'),
        ARC_TANGENT('l'),
        COSINE('l'),
        COMBINATION('b'),
        CUBE('t'),
        CUBED_ROOT('l'),
        DIVIDE('n'),
        DOUBLE('n'),
        E('n'),
        EQUALS('n'),
        FACTORIAL('t'),
        LEFT_PARENTHESIS('n'),
        LEFT_BRACE('n'),
        LEFT_BRACKET('n'),
        LOG_TEN('l'),
        LOG_NATURAL('l'),
        MULTIPLY('n'),
        PERCENT('t'),
        PERMUTATION('b'),
        PI('n'),
        POWER('b'),
        RANDOM_NUMBER('n'),
        RIGHT_PARENTHESIS('n'),
        RIGHT_BRACE('n'),
        RIGHT_BRACKET('n'),
        SCIENTIFIC_NOTATION('b'),
        SINE('l'),
        SUBTRACT('n'),
        SQUARE('t'),
        SQUARE_ROOT('l'),
        TANGENT('l'),
        UNKNOWN_VARIABLE('n');

        private char position; //tokenType: l = leading, t = trailing, b = both, n = not applicable

        Type(char position)
        {
            this.position = position;
        }

        public boolean isLeading(){ return position == 'l'; }
        public boolean isTrailing(){ return position == 't'; }
        public boolean isLeadingAndTrailing() { return position == 'b'; }
    }

    private String _token;
    private Type _type;
    
    public Token(String token, Type type) {
        _token = token;
        _type = type;
    }
    
    public String token() {
        return _token;
    }
    
    public Type type() {
        return _type;
    }
}
