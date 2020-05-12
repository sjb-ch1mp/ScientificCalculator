package com.anu.calculator.exceptions;

import com.anu.calculator.ParserException;

/**
 * An exception class that handles user input that contains an error message.
 * @author Samuel Brookes (u5380100)
 */

public class ContainsErrorMessageException extends ParserException {
    /**
     * Exception constructor. Passes the source tag and error message
     * to the super class ParserException.
     * @author Samuel Brookes (u5380100)
     * @param source : the source of the exception (TAG)
     * @param errorMessage : the error message to be shown in the calculator UI
     */
    public ContainsErrorMessageException(String source, String errorMessage)
    {
        super(source, errorMessage);
    }
}
