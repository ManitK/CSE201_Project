package com.example.project;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.*;
import javafx.stage.Stage;
import javafx.util.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import static com.example.project.LandingPage.current_cherry_count;
import static com.example.project.LandingPage.current_score;


public class PlayPageController implements Initializable {
    @FXML
    public Text score_count;
    @FXML
    public Text cherry_count;
    @FXML
    public Button save_button;
    public Text char_upgraded;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Rectangle stick;
    @FXML
    private ImageView player;
    @FXML
    private ImageView cherry;
    public static int current_score = 0;
    public static int current_cherry_count = 0;
    private double temp = 0;

    // DESIGN PATTERNS USED -
    // 1. FACTORY : TO GET DIFFERENT HANDLERS FOR DIFFERENT OBJECTS
    // 2. FLYWEIGHT : FOR CREATING UNIQUE PILLARS OF DIFFERENT DIMENSION FOR EACH NEW LEVEL
    // 3. ADAPTER
    
    HandlerFactory FACTORY = new HandlerFactory();
    Flyweight PillarFlyweight = new Flyweight();

    public PlayerController player_controller = new PlayerController();
    public HandlerType pillar1_controller;
    public HandlerType pillar2_controller;
    public HandlerType stick_controller;

    public double stickMaxY = 400;
    public int times_key_pressed = 0;
    public Boolean avatar_moved = false;
    public int level_number = 1;
    Timeline timeline;
    Timeline collect_cherry;
    Timeline sound;
    public ArrayList<Rectangle> pillar_list = new ArrayList<>();
    public Boolean reverse = false;
    public Boolean cherry_collected = false;
    public Boolean avatar_changed1 = false;
    public Boolean avatar_changed2 = false;

    public void AdjustPillarHandler(PillarHandler temp, Rectangle rect) {
        temp.setPillar_height(rect.getHeight());
        temp.setPillar_width(rect.getWidth());
        temp.setPillar_position_x(rect.getLayoutX());
        temp.setPillar_position_y(rect.getLayoutY());
    }

    public void AdjustStick(StickHandler temp, Rectangle rect) {
        temp.setStick_position_x(rect.getX());
        temp.setStick_position_y(rect.getY());
    }

    @FXML
    public void saveButtonPressed(ActionEvent actionEvent) throws IOException {
        // Load the landing page FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        testrunner runner = new testrunner();

        pillar1_controller = FACTORY.getHandler("PILLAR");
        pillar2_controller = FACTORY.getHandler("PILLAR");
        stick_controller = FACTORY.getHandler("STICK");

        score_count.setText(String.valueOf(current_score));
        cherry_count.setText(String.valueOf(current_cherry_count));

        Rectangle first_pillar = new Rectangle(0, 403, 67, 316);
        Rectangle second_pillar = new Rectangle(200, 403, 100, 316);
        cherry.setX((first_pillar.getX()+second_pillar.getX())/2);
        anchorPane.getChildren().add(first_pillar);
        anchorPane.getChildren().add(second_pillar);
        pillar_list.add(first_pillar);
        pillar_list.add(second_pillar);

        pillar1_controller.setHeight(first_pillar.getHeight());
        pillar2_controller.setHeight(second_pillar.getHeight());
        pillar1_controller.setWidth(first_pillar.getWidth());
        pillar2_controller.setWidth(second_pillar.getWidth());

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
                    //System.out.println("down");
                    player.setRotate(player.getRotate() + 180);
                    player.setY(player.getY() + 30 );
                    player.setScaleX(player.getScaleX() * -1);
                    reverse = true;
                }
                else{
                    //System.out.println("up");
                    player.setRotate(player.getRotate() + 180);
                    player.setY(player.getY() - 30 );
                    player.setScaleX(player.getScaleX() * -1);
                    reverse = false;
                }
            }

            stick_controller.setStick_position_x(stick.getX());
            stick_controller.setStick_position_y(stick.getY());
            AdjustPillarHandler((PillarHandler) pillar1_controller, pillar_list.get(0));
            AdjustPillarHandler((PillarHandler) pillar2_controller, pillar_list.get(1));
            AdjustStick((StickHandler) stick_controller, stick);
        });

        anchorPane.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                timeline.stop();
                try {
                    dropStick();
                } catch (InterruptedException | Stick_Out_Of_Bounds e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void dropStick() throws InterruptedException,Stick_Out_Of_Bounds{
        try{
            if(stick.getHeight() >= 400){
                throw new Stick_Out_Of_Bounds();
            }
            else{
                stick.setWidth(stick.getHeight());
                stick.setHeight(3);
                stick.setY(stick.getY()+stick.getWidth());
                move_avatar_ahead(stick.getWidth());
            }
        }
        catch(Stick_Out_Of_Bounds e){
            System.out.println(e.getMessage());
            FXMLLoader fxmlLoader = new FXMLLoader(LandingPage.class.getResource("exit-page.fxml"));
            Parent root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Scene scene = new Scene(root, 500, 550);
            Stage currentStage = (Stage) anchorPane.getScene().getWindow();
            currentStage.setScene(scene);
            currentStage.show();
        }
    }

    private void move_avatar_ahead(double distance) throws InterruptedException {
        temp = player.getX();
        // the function below moves the avatar from the start pillar to the end pillar
        Timeline moving_avatar = new Timeline(new KeyFrame(Duration.millis(75), e -> {
            //System.out.println(player.getX() - temp);

            if(level_number == 1){
                if (cherry_collected == false && reverse && player.getX() <= 120 && player.getX()>=100 ) {
                    //System.out.println("check");
                    cherry_collected = true;
                    cherry.setVisible(false);
                }
            }
            else{
                if (cherry_collected == false && reverse && player.getX() - temp <= 177 && player.getX()- temp>=150 ) {
                    //System.out.println("check");
                    cherry_collected = true;
                    cherry.setVisible(false);
                }
            }
            player.setX(player.getX() + (distance + 20)/20);
        }));


        moving_avatar.setCycleCount(20);
        moving_avatar.play();
        avatar_moved = true;
        moving_avatar.setOnFinished(e->{

            player_controller.setPlayer_position_x(distance);
            player_controller.setPlayer_position_y(383);
            pillar1_controller.setLevel_number(level_number+1);
            pillar2_controller.setLevel_number(level_number+1);

            if(reverse == false){
                // Check if any part of the player is within the horizontal bounds of pillar2
                if (pillar_list.get(level_number).getX() <= distance + 23 && pillar_list.get(level_number).getX() + pillar_list.get(level_number).getWidth() >= distance + 20) {
                    // the player has survived
                    //System.out.println("You have survived");
                    go_to_next_level(distance);
                }
                else {
                    if(current_cherry_count>=2){
                        current_cherry_count = current_cherry_count - 1;
                        System.out.println("dead-1");
                        FXMLLoader fxmlLoader = new FXMLLoader(LandingPage.class.getResource("revive-page.fxml"));
                        Parent root = null;
                        try {
                            root = fxmlLoader.load();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        Scene scene = new Scene(root, 500, 550);
                        Stage currentStage = (Stage) anchorPane.getScene().getWindow();
                        currentStage.setScene(scene);
                        currentStage.show();
                    }
                    else{
                        FXMLLoader fxmlLoader = new FXMLLoader(LandingPage.class.getResource("exit-page.fxml"));
                        Parent root = null;
                        try {
                            root = fxmlLoader.load();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        Scene scene = new Scene(root, 500, 550);
                        Stage currentStage = (Stage) anchorPane.getScene().getWindow();
                        currentStage.setScene(scene);
                        currentStage.show();
                    }
                }
            }
            else if(reverse == true){
                if(current_cherry_count>=2){
                    current_cherry_count = current_cherry_count - 1;
                    FXMLLoader fxmlLoader = new FXMLLoader(LandingPage.class.getResource("revive-page.fxml"));
                    Parent root = null;
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Scene scene = new Scene(root, 500, 550);
                    Stage currentStage = (Stage) anchorPane.getScene().getWindow();
                    currentStage.setScene(scene);
                    currentStage.show();
                }
                else{
                    // the player has died
                    FXMLLoader fxmlLoader = new FXMLLoader(LandingPage.class.getResource("exit-page.fxml"));
                    Parent root = null;
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    Scene scene = new Scene(root, 500, 550);
                    Stage currentStage = (Stage) anchorPane.getScene().getWindow();
                    currentStage.setScene(scene);
                    currentStage.show();
                }
            }
        });
    }

    private void go_to_next_level(double distance) {
        TranslateTransition move_level1 = new TranslateTransition(Duration.seconds(3), pillar_list.get(level_number));
        move_level1.setDuration(Duration.millis(1000));
        pillar_list.get(level_number-1).setVisible(false);

        pillar1_controller.setLevel_number(level_number-1);
        pillar2_controller.setLevel_number(level_number-1);

        //move_level1.setByX(-1*distance);
        move_level1.setToX(-1*pillar_list.get(level_number).getX());

        TranslateTransition move_level2 = new TranslateTransition(Duration.seconds(3), player);
        move_level2.setDuration(Duration.millis(1000));
        move_level2.setByX(-1*distance-20);
        //move_level2.setToX(0);

        move_level1.play();
        move_level2.play();
        move_level2.setOnFinished(e->{

            //Random random = new Random();
            //Rectangle next_pillar = new Rectangle( random.nextInt(200,319), 403, random.nextInt(50,100),316);
            Rectangle next_pillar =  PillarFlyweight.getNextPillar(level_number);
            anchorPane.getChildren().add(next_pillar);
            pillar_list.add(next_pillar);
            level_number++;

            pillar1_controller.setLevel_number(level_number);
            pillar2_controller.setLevel_number(level_number);

            //System.out.println(level_number);
            if(!avatar_changed1 && !avatar_changed2){
                score_count.setText(String.valueOf(current_score + 100));
                current_score = current_score + 100;
            }
            else if(avatar_changed1 && !avatar_changed2){
                score_count.setText(String.valueOf(current_score + 200));
                current_score = current_score + 200;
            }
            else if(avatar_changed2){
                score_count.setText(String.valueOf(current_score + 300));
                current_score = current_score + 300;
            }

            if(cherry_collected){
                current_cherry_count++;
                if(!avatar_changed1 && !avatar_changed2){
                    score_count.setText(String.valueOf(current_score + 300));
                    cherry_count.setText(String.valueOf(current_cherry_count));
                    current_score = current_score + 300;
                }
                else if(avatar_changed1 && !avatar_changed2){
                    score_count.setText(String.valueOf(current_score + 2*300));
                    cherry_count.setText(String.valueOf(current_cherry_count));
                    current_score = current_score + 2*300;
                }
                else if(avatar_changed2){
                    score_count.setText(String.valueOf(current_score + 3*300));
                    cherry_count.setText(String.valueOf(current_cherry_count));
                    current_score = current_score + 3*300;
                }
                cherry_collected = false;
            }

            if(current_score>=1200){
                avatar2 playerimage2= new avatar2(player);
                String imagePath2 = "/Images/avatar3.png";
                change_of_character(imagePath2);
                if(avatar_changed1){
                    char_upgraded.setVisible(false);
                }
                if(!avatar_changed1){
                    avatar_changed1 = true;
                    char_upgraded.setVisible(true);
                }

            }
            else if(current_score>=500){
                avatar1 playerimage= new avatar1(player);
                String imagePath = "/Images/avatar2.png";
                change_of_character(imagePath);
                if(avatar_changed2){
                    char_upgraded.setVisible(false);
                }
                if(!avatar_changed2){
                    avatar_changed2 = true;
                    char_upgraded.setVisible(true);
                }
            }
            else{
                avatar3 playerimage3= new avatar3(player);
                String imagePath3 = "/Images/avatar.png";
                change_of_character(imagePath3);
            }

            stick.setHeight(0);
            stick.setWidth(3);
            stick.setX(20);
            stick.setY(400);
            cherry.setX(150);
            cherry.setVisible(true);

            stick_controller.setStick_position_x(stick.getX());
            stick_controller.setStick_position_y(stick.getY());
        });
    }

    private void change_of_character(String character_to_become){
        try {
            InputStream imageStream = getClass().getResourceAsStream(character_to_become);
            if (imageStream != null) {
                Image newImage = new Image(imageStream);
                //System.out.println("0");
                player.setImage(newImage);
                player.setFitHeight(27);
                player.setFitWidth(18);}
            else {System.out.println("Image not found: " + character_to_become);}
        }
        catch (Exception q) {q.printStackTrace();}
    }


}