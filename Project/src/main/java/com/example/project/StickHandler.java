package com.example.project;

public class StickHandler extends SticksPillars implements HandlerType{

    private double stick_position_x;
    private double stick_position_y;
    private double height;
    private double width;

    public boolean getIf_dropped() {
        return if_dropped;
    }

    public void setIf_dropped(boolean if_dropped) {
        this.if_dropped = if_dropped;
    }

    private boolean if_dropped;

    public double getStick_position_x() {
        return stick_position_x;
    }

    public void setStick_position_x(double stick_position_x) {
        this.stick_position_x = stick_position_x;
    }

    public double getStick_position_y() {
        return stick_position_y;
    }

    public void setStick_position_y(double stick_position_y) {
        this.stick_position_y = stick_position_y;
    }

    @Override
    public void setLevel_number(int i) {

    }
    @Override
    public double getWidth(double a) {
        return 0;
    }

    @Override
    public double getHeight(double b) {
        return 0;
    }

    @Override
    public void setWidth(double a) {
        this.width=a;
    }

    @Override
    public void setHeight(double b) {
        this.height=b;
    }


}

