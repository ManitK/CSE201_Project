package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Paths;

public class Controller {
    private MediaPlayer mediaPlayer;

    @FXML
    public void onStartButtonClick(MouseEvent mouseEvent) throws IOException {
        // Load FXML in separate thread
        Thread loadFxmlThread = new Thread(() -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("play-page.fxml"));
                Parent root = fxmlLoader.load();
                javafx.application.Platform.runLater(() -> {
                    music();
                    Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Play Page");
                    stage.setScene(scene);
                    stage.show();
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        loadFxmlThread.start();
    }

    public void music() {
        // music in separate thread
        Thread musicThread = new Thread(() -> {
            String s = "game_sound.mp3";
            Media h = new Media(Paths.get(s).toUri().toString());
            mediaPlayer = new MediaPlayer(h);
            mediaPlayer.setOnEndOfMedia(() -> mediaPlayer.seek(Duration.ZERO));
            mediaPlayer.play();
        });
        musicThread.start();
    }
}
