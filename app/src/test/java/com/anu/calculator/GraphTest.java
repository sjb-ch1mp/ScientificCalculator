package com.anu.calculator;

import android.graphics.Canvas;
import android.util.Range;

import com.anu.calculator.graphs.CalcChart;
import com.anu.calculator.graphs.ChartVect;
import com.anu.calculator.graphs.GraphRange;
import com.anu.calculator.graphs.ListModel;
import com.anu.calculator.graphs.graphParser;
import com.anu.calculator.parsers.ExpressionParser;
import com.anu.calculator.utilities.History;
import com.anu.calculator.utilities.HistoryItem;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class GraphTest {

    public static float[] res1 = new float[]{-20.0f, 5.0f, -19.6f, 5.0f, -19.2f, 5.0f, -18.8f, 5.0f, -18.4f, 5.0f, -18.0f, 5.0f, -17.6f, 5.0f, -17.2f, 5.0f, -16.8f, 5.0f, -16.4f, 5.0f, -16.0f, 5.0f, -15.6f, 5.0f, -15.2f, 5.0f, -14.8f, 5.0f, -14.4f, 5.0f, -14.0f, 5.0f, -13.6f, 5.0f, -13.2f, 5.0f, -12.8f, 5.0f, -12.4f, 5.0f, -12.0f, 5.0f, -11.6f, 5.0f, -11.2f, 5.0f, -10.8f, 5.0f, -10.4f, 5.0f, -10.0f, 5.0f, -9.6f, 5.0f, -9.2f, 5.0f, -8.8f, 5.0f, -8.4f, 5.0f, -8.0f, 5.0f, -7.6000004f, 5.0f, -7.2f, 5.0f, -6.8f, 5.0f, -6.3999996f, 5.0f, -6.0f, 5.0f, -5.6000004f, 5.0f, -5.2f, 5.0f, -4.8f, 5.0f, -4.3999996f, 5.0f, -4.0f, 5.0f, -3.6000004f, 5.0f, -3.2000008f, 5.0f, -2.7999992f, 5.0f, -2.3999996f, 5.0f, -2.0f, 5.0f, -1.6000004f, 5.0f, -1.2000008f, 5.0f, -0.79999924f, 5.0f, -0.39999962f, 5.0f, 0.0f, 5.0f, 0.39999962f, 5.0f, 0.79999924f, 5.0f, 1.2000008f, 5.0f, 1.6000004f, 5.0f, 2.0f, 5.0f, 2.3999996f, 5.0f, 2.7999992f, 5.0f, 3.2000008f, 5.0f, 3.6000004f, 5.0f, 4.0f, 5.0f, 4.3999996f, 5.0f, 4.799999f, 5.0f, 5.200001f, 5.0f, 5.6000004f, 5.0f, 6.0f, 5.0f, 6.3999996f, 5.0f, 6.799999f, 5.0f, 7.200001f, 5.0f, 7.6000004f, 5.0f, 8.0f, 5.0f, 8.4f, 5.0f, 8.799999f, 5.0f, 9.200001f, 5.0f, 9.6f, 5.0f, 10.0f, 5.0f, 10.4f, 5.0f, 10.799999f, 5.0f, 11.200001f, 5.0f, 11.6f, 5.0f, 12.0f, 5.0f, 12.400002f, 5.0f, 12.799999f, 5.0f, 13.200001f, 5.0f, 13.599998f, 5.0f, 14.0f, 5.0f, 14.400002f, 5.0f, 14.799999f, 5.0f, 15.200001f, 5.0f, 15.599998f, 5.0f, 16.0f, 5.0f, 16.400002f, 5.0f, 16.8f, 5.0f, 17.2f, 5.0f, 17.599998f, 5.0f, 18.0f, 5.0f, 18.400002f, 5.0f, 18.8f, 5.0f, 19.2f, 5.0f, 19.599998f, 5.0f};
    public static float[] res2 = new float[]{-20.0f, -40.0f, -19.6f, -39.2f, -19.2f, -38.4f, -18.8f, -37.6f, -18.4f, -36.8f, -18.0f, -36.0f, -17.6f, -35.2f, -17.2f, -34.4f, -16.8f, -33.6f, -16.4f, -32.8f, -16.0f, -32.0f, -15.6f, -31.2f, -15.2f, -30.4f, -14.8f, -29.6f, -14.4f, -28.8f, -14.0f, -28.0f, -13.6f, -27.2f, -13.2f, -26.4f, -12.8f, -25.6f, -12.4f, -24.8f, -12.0f, -24.0f, -11.6f, -23.2f, -11.2f, -22.4f, -10.8f, -21.6f, -10.4f, -20.8f, -10.0f, -20.0f, -9.6f, -19.2f, -9.2f, -18.4f, -8.8f, -17.6f, -8.4f, -16.8f, -8.0f, -16.0f, -7.6000004f, -15.2f, -7.2f, -14.4f, -6.8f, -13.6f, -6.3999996f, -12.8f, -6.0f, -12.0f, -5.6000004f, -11.2f, -5.2f, -10.4f, -4.8f, -9.6f, -4.3999996f, -8.8f, -4.0f, -8.0f, -3.6000004f, -7.2f, -3.2000008f, -6.4f, -2.7999992f, -5.6f, -2.3999996f, -4.8f, -2.0f, -4.0f, -1.6000004f, -3.2f, -1.2000008f, -2.4f, -0.79999924f, -1.6f, -0.39999962f, -0.8f, 0.0f, 0.0f, 0.39999962f, 0.8f, 0.79999924f, 1.6f, 1.2000008f, 2.4f, 1.6000004f, 3.2f, 2.0f, 4.0f, 2.3999996f, 4.8f, 2.7999992f, 5.6f, 3.2000008f, 6.4f, 3.6000004f, 7.2f, 4.0f, 8.0f, 4.3999996f, 8.8f, 4.799999f, 9.6f, 5.200001f, 10.4f, 5.6000004f, 11.2f, 6.0f, 12.0f, 6.3999996f, 12.8f, 6.799999f, 13.6f, 7.200001f, 14.4f, 7.6000004f, 15.2f, 8.0f, 16.0f, 8.4f, 16.8f, 8.799999f, 17.6f, 9.200001f, 18.4f, 9.6f, 19.2f, 10.0f, 20.0f, 10.4f, 20.8f, 10.799999f, 21.6f, 11.200001f, 22.4f, 11.6f, 23.2f, 12.0f, 24.0f, 12.400002f, 24.8f, 12.799999f, 25.6f, 13.200001f, 26.4f, 13.599998f, 27.2f, 14.0f, 28.0f, 14.400002f, 28.8f, 14.799999f, 29.6f, 15.200001f, 30.4f, 15.599998f, 31.2f, 16.0f, 32.0f, 16.400002f, 32.8f, 16.8f, 33.6f, 17.2f, 34.4f, 17.599998f, 35.2f, 18.0f, 36.0f, 18.400002f, 36.8f, 18.8f, 37.6f, 19.2f, 38.4f, 19.599998f, 39.2f};
    public static float[] res3 = new float[]{-20.0f, 0.408f, -19.6f, 0.731f, -19.2f, 0.939f, -18.8f, 0.999f, -18.4f, 0.901f, -18.0f, 0.66f, -17.6f, 0.316f, -17.2f, -0.079f, -16.8f, -0.461f, -16.4f, -0.77f, -16.0f, -0.958f, -15.6f, -0.994f, -15.2f, -0.874f, -14.8f, -0.615f, -14.4f, -0.26f, -14.0f, 0.137f, -13.6f, 0.512f, -13.2f, 0.806f, -12.8f, 0.973f, -12.4f, 0.986f, -12.0f, 0.844f, -11.6f, 0.568f, -11.2f, 0.203f, -10.8f, -0.194f, -10.4f, -0.561f, -10.0f, -0.839f, -9.6f, -0.985f, -9.2f, -0.975f, -8.8f, -0.811f, -8.4f, -0.519f, -8.0f, -0.146f, -7.6000004f, 0.251f, -7.2f, 0.608f, -6.8f, 0.869f, -6.3999996f, 0.993f, -6.0f, 0.96f, -5.6000004f, 0.776f, -5.2f, 0.469f, -4.8f, 0.087f, -4.3999996f, -0.307f, -4.0f, -0.654f, -3.6000004f, -0.897f, -3.2000008f, -0.998f, -2.7999992f, -0.942f, -2.3999996f, -0.737f, -2.0f, -0.416f, -1.6000004f, -0.029f, -1.2000008f, 0.362f, -0.79999924f, 0.697f, -0.39999962f, 0.921f, 0.0f, 1.0f, 0.39999962f, 0.921f, 0.79999924f, 0.697f, 1.2000008f, 0.362f, 1.6000004f, -0.029f, 2.0f, -0.416f, 2.3999996f, -0.737f, 2.7999992f, -0.942f, 3.2000008f, -0.998f, 3.6000004f, -0.897f, 4.0f, -0.654f, 4.3999996f, -0.307f, 4.799999f, 0.087f, 5.200001f, 0.469f, 5.6000004f, 0.776f, 6.0f, 0.96f, 6.3999996f, 0.993f, 6.799999f, 0.869f, 7.200001f, 0.608f, 7.6000004f, 0.251f, 8.0f, -0.146f, 8.4f, -0.519f, 8.799999f, -0.811f, 9.200001f, -0.975f, 9.6f, -0.985f, 10.0f, -0.839f, 10.4f, -0.561f, 10.799999f, -0.194f, 11.200001f, 0.203f, 11.6f, 0.568f, 12.0f, 0.844f, 12.400002f, 0.986f, 12.799999f, 0.973f, 13.200001f, 0.806f, 13.599998f, 0.512f, 14.0f, 0.137f, 14.400002f, -0.26f, 14.799999f, -0.615f, 15.200001f, -0.874f, 15.599998f, -0.994f, 16.0f, -0.958f, 16.400002f, -0.77f, 16.8f, -0.461f, 17.2f, -0.079f, 17.599998f, 0.316f, 18.0f, 0.66f, 18.400002f, 0.901f, 18.8f, 0.999f, 19.2f, 0.939f, 19.599998f, 0.731f};
    public static ArrayList<float[]> results = new ArrayList<>();

    @Test
    public void SimpleGraphTest() throws ParserException {
        ArrayList<ListModel> functionList = new ArrayList<>();
        String test1 = "x=5";
        String test2 = "y=2x";
        String test3 = "z=cos(x)";

        results.add(res1);
        results.add(res2);
        results.add(res3);
        GraphRange range = new GraphRange(new ChartVect(-20, -20), new ChartVect(20, 20));
        History history_1 = History.getInstance();
        ExpressionParser fp = new ExpressionParser();
        functionList.add(new ListModel(fp.parse(test1, true, 0, history_1), false));
        functionList.add(new ListModel(fp.parse(test2, true, 0, history_1), false));
        functionList.add(new ListModel(fp.parse(test3, true, 0, history_1), false));

        for (int i = 0; i < functionList.size(); i++) {
            graphParser g = new graphParser(functionList.get(i).func);
            float[] points = g.genData(range);
            assertArrayEquals(points, results.get(i),0.0f);
        }
        
    }

    @Test
    public void GraphRangeTest() throws ParserException{
        ChartVect v1 = new ChartVect(1.0f, 1.0f);
        ChartVect v2 = new ChartVect(2.0f, 2.0f);
        ChartVect span = new ChartVect(1.0f, 1.0f);
        ChartVect rescaled = new ChartVect(10.0f, 10.0f);
        ChartVect moved = new ChartVect(13.0f, 13.0f);

        GraphRange r = new GraphRange(v1, v2);
        assertEquals(r.span.x, span.x, 0.0f);
        assertEquals(r.span.y,span.y,0.0f);
        r.rescale_x(10.0f);
        r.rescale_y(10.0f);
        assertEquals(r.span.x,rescaled.x,0.0f);
        assertEquals(r.span.y,rescaled.y,0.0f);
        r.move_x(3.0f);
        r.move_y(3.0f);
        assertEquals(r.min.x,moved.x,0.0f);
        assertEquals(r.min.y,moved.y,0.0f);
    }
}