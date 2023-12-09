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

import static com.example.project.LandingPage.current_cherry_count;
import static com.example.project.LandingPage.current_score;

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

    public void onExitButtonClicked(MouseEvent mouseEvent) {
        System.exit(0);
    }

}
