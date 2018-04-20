package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		// just load fxml file and display it in the stage:

		primaryStage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("application/icon.png")));
		primaryStage.setTitle("Elevator App");
		FXMLLoader appLoad = new FXMLLoader(getClass().getResource("application.fxml"));
		Parent root = appLoad.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);

		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);

	}

}
