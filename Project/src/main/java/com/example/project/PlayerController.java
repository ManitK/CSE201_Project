package com.example.project;

public class PlayerController implements HandlerType{
    private double player_position_x;
    private double player_position_y;

    public double getPlayer_position_x() {
        return player_position_x;
    }

    public void setPlayer_position_x(double player_position_x) {
        this.player_position_x = player_position_x;
    }

    public double getPlayer_position_y() {
        return player_position_y;
    }

    public void setPlayer_position_y(double player_position_y) {
        this.player_position_y = player_position_y;
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

