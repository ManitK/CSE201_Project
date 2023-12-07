// Controller.java
package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import java.io.IOException;

public class Controller {
    @FXML
    public void onStartButtonClick(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("play-page.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root,700, 600);
        scene.getRoot().requestFocus();

        // Get the Stage from the ActionEvent source
        Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
        // Set the new scene on the stage
        stage.setTitle("Play Page");
        stage.setScene(scene);
        stage.show();
    }
}
