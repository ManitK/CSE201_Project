package com.example.project;

public abstract class SticksPillars {
    private double position_x;
    private double position_y;
    public double getPosition_x() {
        return position_x;}
    public void setPosition_x(double position_x) {
        this.position_x = position_x;}
    public double getPosition_y() {
        return position_y;}
    public void setPosition_y(double position_y) {
        this.position_y = position_y;}

    public abstract double getWidth(double a);
    public abstract double getHeight(double b);

    public abstract void setWidth(double a);
    public abstract void setHeight(double b);

}

