package com.anu.calculator.exceptions;

import com.anu.calculator.ParserException;

/**
 * An exception that handles the case in which an unknown variable has no value assigned to it
 * @author Samuel Brookes (u5380100)
 */

public class UnassignedVariableException extends ParserException {

    /**
     * Exception constructor. Passes the source tag and error message
     * to the super class ParserException.
     * @author Samuel Brookes (u5380100)
     * @param source : the source of the exception (TAG)
     * @param errorMessage : the error message to be shown in the calculator UI
     */
    public UnassignedVariableException(String source, String errorMessage)
    {
        super(source, errorMessage);
    }
}
