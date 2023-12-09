// Controller.java
package com.example.project;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.project.LandingPage.*;

public class EndgamePage {

    @FXML
    public void onReviveButtonClicked(MouseEvent mouseEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("play-page.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Play Page");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onExitButtonClicked(MouseEvent mouseEvent) {
        System.exit(0);
    }

    @FXML
    public void onRestartButtonClicked(MouseEvent mouseEvent) throws IOException {
        // Load the landing page FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        Controller cont = loader.getController();
        cont.update_score(String.valueOf(high_score));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((javafx.scene.Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

}
