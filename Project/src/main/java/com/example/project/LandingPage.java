// LandingPage.java
package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.IOException;

public class LandingPage extends Application {
    public static int current_score = 0;
    public static int current_cherry_count = 0;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LandingPage.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 550);
        stage.setTitle("Stick Hero Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        Result result = JUnitCore.runClasses(tests.class);
        for (Failure i : result.getFailures()) {
            System.out.println(i.toString());
        }
        System.out.println("ALL TESTS PASSED - " + result.wasSuccessful());

        launch();
    }
}
