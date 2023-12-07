package com.example.project;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    @FXML
    private Rectangle pillar2;
    @FXML
    private Rectangle pillar3;
    @FXML
    private Rectangle pillar4;
    @FXML
    private Rectangle pillar5;
    @FXML
    private Rectangle pillar6;
    @FXML
    private Rectangle stick;
    @FXML
    private ImageView player;
    public PlayerController player_controller;
    public PillarHandler pillar1_controller = new PillarHandler();
    public PillarHandler pillar2_controller = new PillarHandler();
    private PillarHandler stick_handler = new PillarHandler();

    private boolean spacePressed = false;
    private AnimationTimer levelloop;
    private AnimationTimer startgameloop;
    public double stickMaxY = 400;
    public int times_key_pressed = 0;
    private long start_time;
    public Boolean avatar_moved = false;
    public int level_number = 1;

    public void AdjustPillarHandler(PillarHandler temp, Rectangle rect){
        temp.setPillar_height(rect.getHeight());
        temp.setPillar_width(rect.getWidth());
        temp.setPillar_position_x(rect.getLayoutX());
        temp.setPillar_position_y(rect.getLayoutY());
    }

    public void AdjustStick(PillarHandler temp, Rectangle rect){
        rect.setLayoutX(temp.getPillar_position_x());
        rect.setLayoutY(temp.getPillar_position_y());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player_controller = new PlayerController();
        player_controller.setPlayer_position_x(player.getLayoutX());
        player_controller.setPlayer_position_y(player.getLayoutY());
        game_loop(pillar1,pillar2);
    }

    @FXML
    public void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.SPACE && times_key_pressed == 1) {
            avatar_moved = false;
            times_key_pressed = 0;
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
            level_loop();
        }
    }
    public void stopStickExpansion() {
        spacePressed = false;
        dropStick();
        stoplevelloop();
    }
    public PillarHandler pillar3_controller = new PillarHandler();
    private void game_loop(Rectangle c1, Rectangle c2) {
        // Initialize the game loop
        startgameloop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                AdjustPillarHandler(pillar1_controller, c1);
                AdjustPillarHandler(pillar2_controller, c2);

                if (level_number > 1) {
                    AdjustPillarHandler(pillar3_controller, pillar3);
                    pillar1.setVisible(false); // Adjust visibility as needed
                }

                stick_handler.setPillar_width(3);
                stick_handler.setPillar_height(0);
                stick_handler.setPillar_position_x(pillar1_controller.getPillar_position_x() + pillar1_controller.getPillar_width() - 2);
                stick_handler.setPillar_position_y(pillar1_controller.getPillar_position_y());
                AdjustStick(stick_handler, stick);
                times_key_pressed = 1;
            }
        };
        startgameloop.start();
    }

    private void game_loop_end() {
            if (startgameloop != null) {
                startgameloop.stop();
            }
    }

    private void level_loop() {
        // Initialize the level loop
        levelloop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (spacePressed) {
                    updateStickHeight();
                }
            }
        };
        levelloop.start();
    }

    private void stoplevelloop() {
        if (levelloop != null) {
            levelloop.stop();
        }
    }

    private void updateStickHeight() {
        double current_time = System.currentTimeMillis();
        double elapsed_time = current_time - start_time;
        double newHeight = (elapsed_time/100.0)*10;
        // error checking for max height
        // Update the height of the stick & stick elongates in the reverse direction
        stick.setHeight(newHeight);
        stick.setLayoutY(stick_handler.getPillar_position_y() - newHeight);
    }

    // the function below rotates the rectangle stick 90 degrees to right about its bottom right corner
    private void dropStick(){
        RotateTransition rotate_stick = new RotateTransition(Duration.seconds(3), stick);
        Rotate rotate = new Rotate(-90, stick.getWidth(),0);
        stick.getTransforms().add(rotate);
        move_avatar_ahead(stick.getHeight());
        rotate_stick.play();
    }

    private void move_avatar_ahead(double distance){
        // the function below moves the avatar from the start pillar to the end pillar
        TranslateTransition move_avatar = new TranslateTransition(Duration.seconds(3), player);
        move_avatar.setByX(distance + 25);
        player_controller.setPlayer_position_x(player_controller.getPlayer_position_x()+distance+25);
        player_controller.setPlayer_position_y(stick.getLayoutY());
        avatar_moved = true;
        move_avatar.play();
        check_survival();
    }

    private void check_survival() {

        // Check if any part of the player is within the horizontal bounds of pillar2
        System.out.println(player_controller.getPlayer_position_x());
        if (pillar2.getLayoutX() <= player_controller.getPlayer_position_x() && pillar2.getLayoutX() + pillar2_controller.getPillar_position_x() + pillar2_controller.getPillar_width() >= player_controller.getPlayer_position_x()) {
            // the player has survived
            System.out.println("You have survived");
            level_number++;
            game_loop(pillar2,pillar3);
        }
        else {
            // the player has died
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText(null);
            alert.setContentText("You have died. Game Over.");
            alert.showAndWait();
            game_loop_end();
            System.exit(0);
        }
    }
}



    /*
    private void drop_avatar(){
        if(avatar_moved){
            // the function below moves the avatar from the start pillar to the end pillar
            TranslateTransition move_avatar = new TranslateTransition(Duration.seconds(3), player);
            move_avatar.setByY(500);
            player_controller.setPlayer_position_x(stick.getLayoutX());
            player_controller.setPlayer_position_y(stick.getLayoutY());
            move_avatar.play();
        }
    }
    */