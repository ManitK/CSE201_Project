package com.example.project;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class avatar3 implements avatar{

    private static ImageView stickmanImageView;
    public avatar3(ImageView stickmanImageView) {
        this.stickmanImageView = stickmanImageView;
    }

    @Override
    public void setWidth(double a) {}
    @Override
    public void setHeight(double b) {}


    public void setImage2() {
        // Initial stickman image
        Image initialImage = new Image("/Images/avatar3.png");
        this.stickmanImageView = new ImageView(initialImage);}
    public void changeCharacter(int x) {

        if (x==1){stickmanImageView.setImage(new Image("/Images/avatar.png"));}

        else if(x==2){stickmanImageView.setImage(new Image("/Images/avatar2.png"));}
    }


    public ImageView getStickmanImageView() {
        return stickmanImageView;
    }

    public void setStickmanImageView(ImageView stickmanImageView) {
        this.stickmanImageView = stickmanImageView;
    }

}

