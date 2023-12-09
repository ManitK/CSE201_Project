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
import static com.example.project.LandingPage.high_score;

public class ExitPage {
    @FXML
    public void onExitButtonClicked(MouseEvent mouseEvent) throws IOException {
        System.exit(0);
    }

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
