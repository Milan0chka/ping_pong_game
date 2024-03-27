package com.example.ping_pong.model;

/**
 * An interface representing objects that can be resized along the X and Y axes.
 */
public interface Resizable {
    /**
     * Resizes the object along the X-axis by a given factor.
     * @param factor The factor by which to resize the object.
     */
    public void resizeX(double factor);

    /**
     * Resizes the object along the Y-axis by a given factor.
     * @param factor The factor by which to resize the object.
     */
    public void resizeY(double factor);
}
