package com.example.project;

public class PillarHandler extends SticksPillars implements HandlerType{
    private double pillar_width;
    private double pillar_height;
    private double pillar_position_x;
    private double pillar_position_y;
    private int level_number;

    public int getLevel_number() {
        return level_number;
    }

    public void setLevel_number(int level_number) {
        this.level_number = level_number;
    }
    private double height;
    private double width;

    public double getPillar_position_x() {
        return pillar_position_x;
    }

    public void setPillar_position_x(double pillar_position_x) {
        this.pillar_position_x = pillar_position_x;
    }

    public double getPillar_position_y() {
        return pillar_position_y;
    }

    public void setPillar_position_y(double pillar_position_y) {
        this.pillar_position_y = pillar_position_y;
    }

    public double getPillar_width() {
        return pillar_width;
    }

    public void setPillar_width(double pillar_width) {
        this.pillar_width = pillar_width;
    }

    public double getPillar_height() {
        return pillar_height;
    }

    public void setPillar_height(double pillar_height) {
        this.pillar_height = pillar_height;
    }

    @Override
    public double getWidth(double a) {
        return this.pillar_width;
    }

    @Override
    public double getHeight(double b) {
        return this.pillar_height;
    }

    @Override
    public void setWidth(double a) {
        this.width=a;
    }

    @Override
    public void setStick_position_x(double x) {

    }

    @Override
    public void setStick_position_y(double y) {

    }

    @Override
    public void setHeight(double b) {
        this.height=b;
    }
}
