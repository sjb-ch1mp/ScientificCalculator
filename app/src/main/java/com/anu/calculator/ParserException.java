package com.anu.calculator;

import android.util.Log;

/**
 * An abstract Exception class for all exceptions thrown by the Parser class
 *
 * @author Samuel Brookes (u5380100)
 */

public abstract class ParserException extends Exception {

    private String tag;
    private String errorMessage;

    public ParserException(String tag, String errorMessage)
    {
        this.errorMessage = errorMessage;
        this.tag = tag;
        //logMe();
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    /**
     * Default logging method for ParserException. This can be used to
     * log the occurrence of an exception to the Android log.
     */
    public void logMe()
    {
        Log.d(tag, getErrorMessage(), this);
    }
}
