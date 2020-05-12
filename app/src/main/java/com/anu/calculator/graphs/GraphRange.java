package com.anu.calculator.graphs;
/**
 * @author: Siwei Wu (u6735397)
 */


/**
 * Vector object for the graph consists of two points (min, max) and the span information (distance vector between the two points)
 *
 */
public class GraphRange {
    public ChartVect min;
    public ChartVect max;
    public ChartVect span;

    /**
     * Constructor - based on the two points and derive the span 'point'
     * @param min - starting position
     * @param max - end position
     */
    public GraphRange(ChartVect min, ChartVect max){
        this.min = min;
        this.max = max;
        span = new ChartVect(max.x - min.x, max.y - min.y);
    }

    /**
     * Rescale the x axis vector by a factor (based on user rescale gesture)
     * span is recalculated
     * @param scale the user generated scale factor
     */
    public void rescale_x(float scale){
        this.min.x = min.x * scale;
        this.max.x = max.x * scale;
        span.x = max.x - min.x;
    }

    /**
     * Rescale the y axis vector by a factor (based on user rescale gesture)
     * span is recalculated
     * @param scale the user generated scale factor
     */
    public void rescale_y(float scale){
        this.min.y = min.y * scale;
        this.max.y = max.y* scale;
        span.y = max.y - min.y;
    }

    /**
     * Move the vector by the x axis
     * span does not need be to recalculated since it remains the same
     * @param moved - x position moved
     */

    public void move_x(float moved){
        this.min.x = this.min.x + moved;
        this.max.x = this.max.x + moved;
    }

    /**
     * Move the vector by the y axis
     * span does not need be to recalculated since it remains the same
     * @param moved - y position moved
     */
    public void move_y(float moved){
        this.min.y = this.min.y + moved;
        this.max.y = this.max.y + moved;
    }
}
