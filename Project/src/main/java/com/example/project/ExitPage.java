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

public class ExitPage {
    @FXML
    public void onExitButtonClicked(MouseEvent mouseEvent) throws IOException {
        System.exit(0);
    }
}
