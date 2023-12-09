package com.example.project;

public class CherryHandler implements HandlerType{
    private int distance_from_start_pillar;
    private Boolean if_on_end_pillar;

    public int getDistance_from_start_pillar() {
        return distance_from_start_pillar;
    }

    public void setDistance_from_start_pillar(int distance_from_start_pillar) {
        this.distance_from_start_pillar = distance_from_start_pillar;
    }

    public Boolean getIf_on_end_pillar() {
        return if_on_end_pillar;
    }

    public void setIf_on_end_pillar(Boolean if_on_end_pillar) {
        this.if_on_end_pillar = if_on_end_pillar;
    }

    @Override
    public void setHeight(double height) {

    }

    @Override
    public void setWidth(double width) {

    }

    @Override
    public void setStick_position_x(double x) {

    }

    @Override
    public void setStick_position_y(double y) {

    }

    @Override
    public void setLevel_number(int i) {

    }
}
