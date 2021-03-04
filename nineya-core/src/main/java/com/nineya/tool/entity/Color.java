package com.nineya.tool.entity;

public class Color {
    private int pixel;

    public Color(int pixel) {
        this.pixel = pixel;
    }

    public Color(short a, short r, short g, short b) {

    }

    public int getA() {
        return pixel >> 24;
    }

    private int getR() {
        return (pixel & 0xff0000) >> 16;
    }

    private int getG() {
        return (pixel & 0xff00) >> 8;
    }

    private int getB() {
        return pixel & 0xff;
    }

    public int getArgb() {
        return pixel;
    }

    public int getPixel() {
        return pixel;
    }
}
