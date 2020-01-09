package com.company;

public class Circle extends Shape {

    @Override
    public double getArea(double d) {
        return Math.pow(d, 2) * Math.PI;
    }

    @Override
    public double getPerimeter(double d) {
        return 2 * d * Math.PI;
    }
}
