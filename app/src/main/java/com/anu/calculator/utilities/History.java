package com.anu.calculator.utilities;

import android.content.Context;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.exceptions.FunctionLoopException;
import com.anu.calculator.expressions.DoubleExpression;
import com.anu.calculator.parsers.ExpressionParser;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * This class processes and saves any value assignments to variables that
 * have occurred during the session. This is done so that the user can build re-useable
 * functions. To process the history, the History class 'strips' (i.e. removes it from
 * the context of a parsing tree by calling the show function of the root node), orders it in
 * such a way that all variables are assigned a value consecutively, and then re-evaluates each
 * expression and assigns the correct value to each variable. It then re-saves the History in a
 * new Stack so that it is processed in the correct order.
 *
 * @author Samuel Brookes (u5380100)
 * @modified Michael Betterton (u6797866)
 * - added load() and save() methods
 */

public class History implements Serializable {
    private static final long serialVersionUID = 21071992L;
    private static final String fileName = "history.dat";

    private final String EQUALS = "=";
    private final String TAG = "HISTORY";

    private Stack<HistoryItem> savedHistory;
    private ArrayList<String> strippedHistory;
    private LinkedList<String> orderedHistory;
    private HashMap<Character, HistoryItem> processedHistory;

    private History(Stack<HistoryItem> history) {
        this.savedHistory = history;
    }

    /**
     * Returns an empty instance of a History object, can be used for testing or in the load method
     * when no saved history file exists.
     *
     * @return History (empty)
     * @author: Samuel Brookes (u5380100)
     */
    public static History getInstance() {
        return new History(new Stack<HistoryItem>());
    }

    /**
     * Loads a history class from the file system and returns it to the application. The file is
     * saved as a serialized version of this class and decoded as such.
     * <p>
     * If the file doesn't exist in the file system, a empty history object is returned.
     *
     * @return A History class loaded from file or empty if none exist.
     * @author: Michael Betterton (u6797866)
     */
    public static History load(Context ctx) {
        try {
            FileInputStream fileInputStream = ctx.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            History history = (History) objectInputStream.readObject();
            objectInputStream.close();
            return history;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return getInstance();
        }
    }

    /**
     * Helper function that returns only graphable functions from the history object. Only functions
     * with a single variable on the RHS of the function are graphable according to our arbitrary
     * rules.
     *
     * @author: Michael Betterton (u6797866)
     * @param ctx The current applications context.
     * @return A Map of Key-Values of Character - HistoryItem values that can be graphed.
     */
    public static Map loadGraphableHistory(Context ctx){
        History history = load(ctx);
        Map<Character, HistoryItem> rtn = new HashMap<Character, HistoryItem>(0);
        for(Character exp : history.processedHistory.keySet())
        {
            if(Objects.requireNonNull(history.processedHistory.get(exp)).isGraphable()){
                rtn.put(exp, history.processedHistory.get(exp));
            }
        }
        return rtn;
    }

    /**
     * Takes this (class) and saves it to the file system under 'fileName'.dat in the internal
     * storage of the device. The file format is a serialized version of this class and therefore it
     * should not be interrogated outside of the save and load methods.
     *
     * @author: Michael Betterton (u6797866)
     */
    public void save(Context ctx) {
        try {
            FileOutputStream fileOutputStream = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes an expression and puts it into the History stack in the appropriate location. This is
     * to enable retroactive value assignment to variables (i.e. rather than simply appending).
     *
     * @param expression : the expression to be added to the History.
     * @throws ParserException : thrown by the parseHistory() method.
     * @author Samuel Brookes (u5380100)
     */
    public void put(Expression expression, Boolean degrees) throws ParserException {
        savedHistory.push(new HistoryItem(false, expression));
        stripHistory();
        orderHistory();
        processHistory(degrees);
        saveHistory();
    }

    /**
     * This method gets the first instance of each unique variable in the
     * history Stack and puts it unordered into strippedHistory after 'stripping' it
     * (i.e. removing it from a parsing tree by calling show on the root)
     *
     * @author Samuel Brookes
     */
    private void stripHistory() {
        strippedHistory = new ArrayList<>(0);

        String raw;

        //stores any variables that have been added to strippedHistory to ensure they are unique
        HashSet<String> storedVariables = new HashSet<>(0);
        while (!savedHistory.empty()) {
            raw = savedHistory.pop().getExpression().show();

            //if the stripped expression is an equality expression
            if (raw.contains(EQUALS)) {
                String variable = raw.split(EQUALS)[0].trim();
                if (!storedVariables.contains(variable)) {

                    //store the variable and add the expression to strippedHistory
                    storedVariables.add(variable);
                    strippedHistory.add(raw);
                }
            }
        }
    }

    /**
     * This method reorders the history stack into orderedHistory to ensure
     * that when processedHistory is built, there is no instance in which
     * a variable has not already been assigned a value when it is being parsed.
     * It checks for the occurrence of loops and throws an Exception if they are
     * detected.
     *
     * @throws ParserException A exception was encountered when parsing the history.
     * @author Samuel Brookes (u5380100)
     */
    private void orderHistory() throws ParserException {
        orderedHistory = new LinkedList<>();
        HashSet<String> definedVariables = new HashSet<>(0);
        Tokenizer tokenizer;

        //Do a quick sweep to search for DoubleExpressions as these immediately define a variable
        String variable = "", expression;
        for (String raw : strippedHistory) {
            variable = raw.split(EQUALS)[0].trim();
            expression = raw.split(EQUALS)[1].trim();
            if (new ExpressionParser().parseHistory(expression, true, null) instanceof DoubleExpression) {
                definedVariables.add(variable);
                orderedHistory.add(raw);
            }
        }

        //Do another sweep to add more complex expressions to orderedHistory
        boolean allDefined = false;
        int prevSize = orderedHistory.size();
        while (!allDefined) {
            allDefined = true;
            for (String raw : strippedHistory) {
                variable = raw.split(EQUALS)[0].trim();
                expression = raw.split(EQUALS)[1].trim();
                if (!definedVariables.contains(variable)) { //this variable has not yet been 'defined'
                    //check the expression for unknown variables and see if they have been 'defined'
                    tokenizer = new Tokenizer(expression);
                    boolean expressionDefined = true;
                    while (tokenizer.hasNext()) {
                        if (tokenizer.current().type() == Token.Type.UNKNOWN_VARIABLE) {
                            if (!definedVariables.contains(tokenizer.current().token())) {
                                expressionDefined = false;
                                break;
                            }
                        }
                        tokenizer.next();
                    }

                    if (expressionDefined) { //all of the variables in the expression are 'defined'
                        definedVariables.add(variable);
                        orderedHistory.add(raw);
                    } else { //there are still undefined variables in the expression - continue loop
                        allDefined = false;
                    }
                }
            }

            /*
                if there are still undefined expression in strippedHistory and
                no expression was added to orderedHistory after this iteration,
                it means that there is an expression which cannot be defined
                (i.e. a loop).
             */
            if (!allDefined && prevSize == orderedHistory.size())
                throw new FunctionLoopException(TAG, variable + Scripts.ErrorMessage.UNASSIGNED_VARIABLE.getMessage());
            else
                prevSize = orderedHistory.size();
        }
    }

    /**
     * If the reordering method is successful, this method should be able to go through
     * orderedHistory (from end to beginning) and successfully parse each expression
     * as each variable should have been previously given a value.
     * Once it has parsed each value - it stores them into processedHistory.
     *
     * @param degrees : whether the user is using degrees or radians. This is necessary
     *                in case the expression contains trigonometry expressions
     * @throws ParserException A exception was encountered when parsing the history.
     * @author Samuel Brookes (u5380100)
     */
    private void processHistory(Boolean degrees) throws ParserException {
        processedHistory = new HashMap<>(0);

        String raw;
        while (!orderedHistory.isEmpty()) {
            raw = orderedHistory.remove(0);
            Character variable = raw.split(EQUALS)[0].trim().charAt(0);
            Expression exp = new ExpressionParser().parseHistory(raw, degrees, processedHistory);
            processedHistory.put(variable, new HistoryItem(isGraphable(exp), exp));
        }
    }

    /**
     * Returns the processed HistoryItems to a Stack. This is done so that when a new
     * expression is added to the history, it is the FIRST expression evaluated, otherwise
     * a previous value for the variable may be evaluated first and the new value will be
     * ignored.
     *
     * @author Samuel Brookes (u5380100)
     */
    private void saveHistory() {
        savedHistory = new Stack<>();
        for (Map.Entry<Character, HistoryItem> mapEntry : processedHistory.entrySet()) {
            savedHistory.push(mapEntry.getValue());
        }
    }

    /**
     * Checks whether an expression is able to be graphed.
     * An expression is able to be graphed if it has one or less UNIQUE variables
     * in the RHS of the equation, e.g. a = 2x + y is not graphable, b = 2x + x is graphable.
     *
     * @param expression : the expression to be check for 'graph-ability'
     * @return boolean : whether the expression can be graphed or not
     * @author Samuel Brookes (u5380100)
     */
    private boolean isGraphable(Expression expression) {

        //get the RHS of the equality expression
        String expStr = expression.show().split("=")[1];
        Tokenizer tokenizer = new Tokenizer(expStr);

        char unkVar = '$';
        while (tokenizer.hasNext()) {
            if (tokenizer.current().type() == Token.Type.UNKNOWN_VARIABLE) {
                if(unkVar == '$')
                {
                    //if this is the first variable, assign it to unkVar
                    unkVar = tokenizer.current().token().charAt(0);
                }
                else
                {
                    //if there is another variable in the expression, it is not graphable
                    if(tokenizer.current().token().charAt(0) != unkVar) return false;
                }
            }
            tokenizer.next();
        }

        return true;
    }

    /**
     * Checks whether this History object contains the given variable.
     *
     * @param variable : the variable in question
     * @return boolean : whether the History has the variable or not
     * @author Samuel Brookes (u5380100)
     */
    public boolean hasVariable(Character variable) {
        return processedHistory != null && processedHistory.containsKey(variable);
    }

    /**
     * Gets the expression (i.e. value) for the given variable.
     *
     * @param variable : the variable in question
     * @return Expression : the expression (value) of the variable
     * @author Samuel Brookes (u5380100)
     */
    public Expression getExpression(Character variable) {
        return processedHistory.get(variable).getExpression();
    }
}
