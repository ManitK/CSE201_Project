package com.example.project;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.util.*;
import java.net.*;
import java.util.ResourceBundle;

public class PlayPageController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Rectangle pillar1;
    public PillarHandler pillar1_controller;

    @FXML
    private Rectangle pillar2;
    public PillarHandler pillar2_controller;

    @FXML
    private Rectangle stick;

    @FXML
    private ImageView player;
    public PlayerController player_controller;

    private boolean spacePressed = false;
    private AnimationTimer gameLoop;
    public double stickMaxY = 400;
    public int times_key_pressed = 0;
    private long start_time;
    public Boolean avatar_moved = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pillar1_controller = new PillarHandler();
        pillar2_controller = new PillarHandler();
        player_controller = new PlayerController();
        pillar1_controller.setPillar_width(pillar1.getWidth());
        pillar1_controller.setPillar_height(pillar1.getHeight());
        pillar2_controller.setPillar_width(pillar2.getWidth());
        pillar2_controller.setPillar_height(pillar2.getHeight());
        pillar1_controller.setPillar_position_x(pillar1.getLayoutX());
        pillar1_controller.setPillar_position_y(pillar1.getLayoutY());
        pillar2_controller.setPillar_position_x(pillar2.getLayoutX());
        pillar2_controller.setPillar_position_y(pillar2.getLayoutY());
        player_controller.setPlayer_position_x(player.getLayoutX());
        player_controller.setPlayer_position_y(player.getLayoutY());
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE && times_key_pressed == 0) {
            avatar_moved = false;
            times_key_pressed = 1;
            startStickExpansion();
        }
    }
    @FXML
    public void handleKeyRelease(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE) {
            stopStickExpansion();
        }
    }

    public void startStickExpansion() {
        if (!spacePressed) {
            spacePressed = true;
            start_time = System.currentTimeMillis();
            startGameLoop();
        }
    }

    public void stopStickExpansion() {
        spacePressed = false;
        dropStick();
        if(avatar_moved){
            check_survival();
        }
        stopGameLoop();
    }

    private void startGameLoop() {
        // Initialize the game loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (spacePressed) {
                    updateStickHeight();
                }
            }
        };
        gameLoop.start();
    }

    private void stopGameLoop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
    }


    private void updateStickHeight() {
        double current_time = System.currentTimeMillis();
        double elapsed_time = current_time - start_time;
        double newHeight = (elapsed_time/100.0)*10;
        // error checking for max height

        // Update the height of the stick & stick elongates in the reverse direction
        stick.setHeight(newHeight);
        stick.setLayoutY(282 - newHeight);
    }

    // the function below rotates the rectangle stick 90 degrees to right about its bottom right corner
    private void dropStick(){
        RotateTransition rotate_stick = new RotateTransition(Duration.seconds(3), stick);
        Rotate rotate = new Rotate(90, stick.getWidth(), stick.getHeight());
        stick.getTransforms().add(rotate);
        move_avatar_ahead(stick.getHeight());
        rotate_stick.play();
    }

    private void move_avatar_ahead(double distance){
        // the function below moves the avatar from the start pillar to the end pillar
        TranslateTransition move_avatar = new TranslateTransition(Duration.seconds(3), player);
        move_avatar.setByX(distance + 20);
        move_avatar.setOnFinished(event -> {
            player_controller.setPlayer_position_x(player_controller.getPlayer_position_x() + distance + 20);
            player.setLayoutX(player_controller.getPlayer_position_x());
            avatar_moved = true;
        });
        move_avatar.play();
    }


    private void check_survival() {
        double playerX = player_controller.getPlayer_position_x();
        double pillar2X = pillar2_controller.getPillar_position_x();
        double pillar2Width = pillar2_controller.getPillar_width();

        if (playerX >= pillar2X && playerX <= pillar2X + pillar2Width) {
            // the player has survived
            System.out.println("You have survived");
        } else {
            // the player has died
            System.out.println("You have died");
            // the player falls down
            TranslateTransition fall_down = new TranslateTransition(Duration.seconds(3), player);
            fall_down.setByY(300);
            fall_down.play();
        }
    }
}