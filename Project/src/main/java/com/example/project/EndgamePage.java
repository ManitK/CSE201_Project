// Controller.java
package com.example.project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import java.io.IOException;

import static com.example.project.LandingPage.current_cherry_count;

public class EndgamePage {
    @FXML
    public void onReviveButtonClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("play-page.fxml"));
        Parent root = fxmlLoader.load();
        // Get the Stage from the ActionEvent source
        Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Play Page");
        stage.setScene(scene);
        stage.show();
    }
}
