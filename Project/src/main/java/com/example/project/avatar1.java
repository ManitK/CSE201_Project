package com.example.project;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class avatar1 implements avatar{

    private ImageView stickmanImageView;
    public avatar1(ImageView stickmanImageView) {
        this.stickmanImageView = stickmanImageView;
    }

    @Override
    public void setWidth(double a) {}
    @Override
    public void setHeight(double b) {}


    public void setImage2() {
        // Initial stick-hero image
        Image initialImage = new Image("/Images/avatar.png");
        this.stickmanImageView = new ImageView(initialImage);}
    public void changeCharacter(int x) {
        this.setImage2();
        if (x == 2) {
            Image changedImage = new Image("/Images/avatar2.png");
            this.stickmanImageView.setImage(changedImage);
            this.stickmanImageView.setVisible(true);

        }


        else if (x == 3) {
            Image changedImage = new Image("/Images/avatar3.png");
            this.stickmanImageView= new ImageView(changedImage);
        }

    }


    public ImageView getStickmanImageView() {
        return stickmanImageView;
    }

    public void setStickmanImageView(ImageView stickmanImageView) {
        this.stickmanImageView = stickmanImageView;
    }

        }

