package com.example.project;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.*;
import javafx.util.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class PlayPageController implements Initializable {
    @FXML
    public Text score_count;
    @FXML
    public Text cherry_count;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Rectangle stick;
    @FXML
    private ImageView player;
    @FXML
    private ImageView cherry;
    private int current_score = 0;
    private int current_cherry_count = 0;
    private double temp = 0;

    public PlayerController player_controller = new PlayerController();
    public PillarHandler pillar1_controller = new PillarHandler();
    public PillarHandler pillar2_controller = new PillarHandler();
    private PillarHandler stick_handler = new PillarHandler();

    public double stickMaxY = 400;
    public int times_key_pressed = 0;
    public Boolean avatar_moved = false;
    public int level_number = 1;
    Timeline timeline;
    Timeline collect_cherry;
    public ArrayList<Rectangle> pillar_list = new ArrayList<>();
    public Boolean reverse = false;
    public Boolean cherry_collected = false;

    public void AdjustPillarHandler(PillarHandler temp, Rectangle rect) {
        temp.setPillar_height(rect.getHeight());
        temp.setPillar_width(rect.getWidth());
        temp.setPillar_position_x(rect.getLayoutX());
        temp.setPillar_position_y(rect.getLayoutY());
    }

    public void AdjustStick(PillarHandler temp, Rectangle rect) {
        rect.setLayoutX(temp.getPillar_position_x());
        rect.setLayoutY(temp.getPillar_position_y());
    }

    /*
    public void collect_cherry_func(){
        collect_cherry = new Timeline(new KeyFrame(Duration.millis(75), e -> {

        }));
        collect_cherry.setCycleCount(Animation.INDEFINITE);
        collect_cherry.play();
    }

     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle first_pillar = new Rectangle(0, 403, 67, 316);
        Rectangle second_pillar = new Rectangle(200, 403, 100, 316);
        cherry.setX((first_pillar.getX()+second_pillar.getX())/2);
        anchorPane.getChildren().add(first_pillar);
        anchorPane.getChildren().add(second_pillar);
        pillar_list.add(first_pillar);
        pillar_list.add(second_pillar);

        anchorPane.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                timeline = new Timeline(new KeyFrame(Duration.millis(40), e -> {
                    stick.setHeight(stick.getHeight() + 10);
                    stick.setY(stick.getY() - 10);
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            }
            else if (event.getButton() == MouseButton.SECONDARY) {
                if(reverse == false){
                    System.out.println("down");
                    player.setRotate(player.getRotate() + 180);
                    player.setY(player.getY() + 30 );
                    player.setScaleX(player.getScaleX() * -1);
                    reverse = true;
                }
                else{
                    System.out.println("up");
                    player.setRotate(player.getRotate() + 180);
                    player.setY(player.getY() - 30 );
                    player.setScaleX(player.getScaleX() * -1);
                    reverse = false;
                }
            }
        });

        anchorPane.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                timeline.stop();
                try {
                    dropStick();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //collect_cherry_func();
    }

    private void dropStick() throws InterruptedException {
        stick.setWidth(stick.getHeight());
        stick.setHeight(3);
        stick.setY(stick.getY()+stick.getWidth());
        move_avatar_ahead(stick.getWidth());
    }

    private void move_avatar_ahead(double distance) throws InterruptedException {
        temp = player.getX();
        // the function below moves the avatar from the start pillar to the end pillar
        Timeline moving_avatar = new Timeline(new KeyFrame(Duration.millis(75), e -> {
            System.out.println(player.getX() - temp);

            if(level_number == 1){
                if (cherry_collected == false && reverse && player.getX() <= 120 && player.getX()>=100 ) {
                    System.out.println("check");
                    cherry_collected = true;
                    cherry.setVisible(false);
                }
            }
            else{
                if (cherry_collected == false && reverse && player.getX() - temp <= 177 && player.getX()- temp>=150 ) {
                    System.out.println("check");
                    cherry_collected = true;
                    cherry.setVisible(false);
                }
                else{
                    System.out.println("jhfkudh");
                }
            }

            player.setX(player.getX() + (distance + 20)/20);

        }));


        moving_avatar.setCycleCount(20);
        moving_avatar.play();
        avatar_moved = true;
        moving_avatar.setOnFinished(e->{
            if(reverse == false){
                // DO CORRECTLY
                // Check if any part of the player is within the horizontal bounds of pillar2
                if (pillar_list.get(level_number).getX() <= distance + 20 && pillar_list.get(level_number).getX() + pillar_list.get(level_number).getWidth() + 5 >= distance + 20) {
                    // the player has survived
                    System.out.println("You have survived");
                    go_to_next_level(distance);
                }
                else {
                    // the player has died
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Game Over");
                    alert.setHeaderText(null);
                    alert.setContentText("You have died. Game Over.");
                    alert.show();
                    System.exit(0);
                }
            }
            else if(reverse == true){
                // the player has died
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Game Over");
                alert.setHeaderText(null);
                alert.setContentText("You have died. Game Over.");
                alert.show();
                System.exit(0);
            }
        });
    }

    private void go_to_next_level(double distance) {
        TranslateTransition move_level1 = new TranslateTransition(Duration.seconds(3), pillar_list.get(level_number));
        move_level1.setDuration(Duration.millis(1000));
        pillar_list.get(level_number-1).setVisible(false);

        //move_level1.setByX(-1*distance);
        move_level1.setToX(-1*pillar_list.get(level_number).getX());

        TranslateTransition move_level2 = new TranslateTransition(Duration.seconds(3), player);
        move_level2.setDuration(Duration.millis(1000));
        move_level2.setByX(-1*distance-20);
        //move_level2.setToX(0);

        move_level1.play();
        move_level2.play();
        move_level2.setOnFinished(e->{
            Random random = new Random();
            Rectangle next_pillar = new Rectangle( random.nextInt(200,319), 403, random.nextInt(50,100),316);
            anchorPane.getChildren().add(next_pillar);
            pillar_list.add(next_pillar);
            level_number++;
            System.out.println(level_number);
            score_count.setText(String.valueOf(current_score + 100));
            current_score = current_score + 100;

            if(cherry_collected){
                current_cherry_count++;
                score_count.setText(String.valueOf(current_score + 500));
                cherry_count.setText(String.valueOf(current_cherry_count));
                current_score = current_score + 500;
                cherry_collected = false;
            }

            stick.setHeight(0);
            stick.setWidth(3);
            stick.setX(20);
            stick.setY(400);
            cherry.setX(150);
            cherry.setVisible(true);
            //collect_cherry_func();
        });
    }
}