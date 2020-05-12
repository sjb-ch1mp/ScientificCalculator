package com.anu.calculator.graphs;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.R;
import com.anu.calculator.utilities.History;
import com.anu.calculator.utilities.Token;
import com.anu.calculator.utilities.Tokenizer;
import com.anu.calculator.parsers.ExpressionParser;
import com.google.android.gms.common.util.ArrayUtils;

import java.util.ArrayList;

import static java.lang.Float.NaN;
/**
 * @author: Siwei Wu (u6735397)
 */

/**
 * Generate the plot points based on an expression and the range of the grpah
 */
public class graphParser {
    Expression expression;
    public static int NUM_POINTS = 200;
    public graphParser(Expression expression) {
        this.expression = expression;
    }


    /**
     *
     * @param x - x position
     * @return y position returned from the expression parsing
     */
    public float get_point(double x){
        String expStr = expression.show();
        History temp_history;
        Expression temp_exp;
        Expression new_exp;
        ExpressionParser temp_parser = new ExpressionParser();
        String temp_str ="";
        Tokenizer checkExp = new Tokenizer(expStr);
        Boolean stop = false;
        Boolean hasVari = false;
        temp_history = History.getInstance();
        String x_str;
        if (x < 0.0) {
            x_str = String.valueOf(x);
        } else {
            x_str = String.valueOf(x);
        }

        while (checkExp.hasNext()) {
            if (checkExp.current().type() == Token.Type.EQUALS){
                    stop = true;
            }
            if (!stop) {

                if (checkExp.current().type() == Token.Type.UNKNOWN_VARIABLE) {
                    hasVari = true;
                    temp_str = checkExp.current().token();
                }
            }
            checkExp.next();
        }

        if (hasVari) {
            temp_str = temp_str + "=" + x_str;
            try {
                temp_exp = temp_parser.parse(temp_str, false, 3, temp_history);
                temp_history.put(temp_exp,false);
            } catch(ParserException e){};
        }
        try {
            new_exp = temp_parser.parse(expStr, false, 3, temp_history);
            return (float) (new_exp.evaluate());
        } catch(ParserException e){
            return NaN;
        }
    }

    /**
     *
     * @param range - the range of the graph (x,y min and max) in view
     * @return array of floating point coordinates generated based on the range of view and expression
     */
    public float[] genData(GraphRange range){
        ArrayList<Float> points = new ArrayList<>();
        float x,y = 0.0f;
        for (int i = 0; i < NUM_POINTS- 1; i = i + 2) {

            x = range.min.x + i * range.span.x/ NUM_POINTS;
            y = get_point((double)x);
            if (y != NaN) {
                points.add(x);
                points.add(y);
            }
        }

        float[] ret = new float[points.size()];
        for (int i = 0; i < points.size(); i++){
            ret[i] = (float) points.get(i);
        }

        return ret;
    }
}
