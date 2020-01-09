package com.company;

public class Square extends Shape {

    @Override
    public double getArea(double d) {
        return Math.pow(d, 2);
    }

    @Override
    public double getPerimeter(double d) {
        return 4 * d;
    }
}
