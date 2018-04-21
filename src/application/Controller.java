package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Random;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller {

	// submit button
	@FXML
	private Button submitButton;
	@FXML
	private Button submitNumOfFloors;
	@FXML
	private Button submitRidersPerFloor;
	@FXML
	private Button submitHomedPerFloor;
	@FXML
	private Button submitVip;
	@FXML
	private Button resetButton;

	// label (for setting visibility)
	@FXML
	private Label toNumOfRidersLabel;
	@FXML
	private Label toNumOfHomedLabel;
	@FXML
	private Label toVipPercentage;
	@FXML
	private Label numOfRidersLabel;
	@FXML
	private Label numOfRidersHomesLabel;
	@FXML
	private Label percentageOfVipsLabel;

	@FXML
	private Label currentFloorLabel;

	// text fields
	@FXML
	private TextField numOfFloors;
	@FXML
	private TextField numOfRidersStatic;
	@FXML
	private TextField numOfRidersMin;
	@FXML
	private TextField numOfRidersMax;
	@FXML
	private TextField numOfHomedStatic;
	@FXML
	private TextField numOfHomedMin;
	@FXML
	private TextField numOfHomedMax;

	@FXML
	private TextField vipPercentStatic;
	@FXML
	private TextField vipPercentMin;
	@FXML
	private TextField vipPercentMax;

	// text output to GUI
	@FXML
	private TextArea output;

	// choice boxes
	@FXML
	private ChoiceBox numofRidersChoice;
	@FXML
	private ChoiceBox numofHomedChoice;
	@FXML
	private ChoiceBox vipChoice;

	// Non fxml variables
	private String textOutput = "";
	private Boolean numOfRidersIsRandom;
	private Boolean numOfHomedIsRandom;
	private Boolean vipPercentIsRandom;
	private int floors;
	private int vipPercent;
	private int currentFloor = 0;
	private int ridersPerFloor;
	private HashMap<Integer, Integer> homedPerFloor = new HashMap<>();

	@FXML
	protected void initialize() {

		submitNumOfFloors.setVisible(true);
		resetButton.setVisible(false);
		submitButton.setVisible(false);
		vipChoice.setVisible(false);

		numOfRidersMin.setVisible(false);
		toNumOfRidersLabel.setVisible(false);
		numOfRidersMax.setVisible(false);
		numOfRidersStatic.setVisible(false);
		submitHomedPerFloor.setVisible(false);

		numOfHomedMin.setVisible(false);
		toNumOfHomedLabel.setVisible(false);
		numOfHomedMax.setVisible(false);
		numOfHomedStatic.setVisible(false);

		numOfRidersLabel.setVisible(false);
		numOfRidersHomesLabel.setVisible(false);
		percentageOfVipsLabel.setVisible(false);
		numofRidersChoice.setVisible(false);

		submitRidersPerFloor.setVisible(false);
		currentFloorLabel.setVisible(false);
		numofHomedChoice.setVisible(false);

		vipPercentMin.setVisible(false);
		vipPercentMax.setVisible(false);
		vipPercentStatic.setVisible(false);
		toVipPercentage.setVisible(false);
		submitVip.setVisible(false);

		numofRidersChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {

				if ((numofRidersChoice.getItems().get((Integer) number2) == "Random")) {
					numOfRidersMin.setVisible(true);
					toNumOfRidersLabel.setVisible(true);
					numOfRidersMax.setVisible(true);
					numOfRidersStatic.setVisible(false);
					submitRidersPerFloor.setVisible(true);
					numOfRidersIsRandom = true;
				}

				if ((numofRidersChoice.getItems().get((Integer) number2) == "Not Random")) {
					numOfRidersStatic.setVisible(true);
					numOfRidersMin.setVisible(false);
					toNumOfRidersLabel.setVisible(false);
					numOfRidersMax.setVisible(false);
					submitRidersPerFloor.setVisible(true);
					numOfRidersIsRandom = false;
				}

			}
		});

		numofHomedChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {

				if ((numofRidersChoice.getItems().get((Integer) number2) == "Random")) {
					numOfHomedMin.setVisible(true);
					toNumOfHomedLabel.setVisible(true);
					numOfHomedMax.setVisible(true);
					numOfHomedStatic.setVisible(false);
					submitHomedPerFloor.setVisible(true);
					numOfHomedIsRandom = true;
				}

				if ((numofRidersChoice.getItems().get((Integer) number2) == "Not Random")) {
					numOfHomedStatic.setVisible(true);
					numOfHomedMin.setVisible(false);
					toNumOfHomedLabel.setVisible(false);
					numOfHomedMax.setVisible(false);
					submitHomedPerFloor.setVisible(true);
					numOfHomedIsRandom = false;
				}

			}
		});

		vipChoice.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {

				if ((vipChoice.getItems().get((Integer) number2) == "Random")) {
					vipPercentMin.setVisible(true);
					toVipPercentage.setVisible(true);
					vipPercentMax.setVisible(true);
					vipPercentStatic.setVisible(false);
					submitVip.setVisible(true);
					vipPercentIsRandom = true;
				}

				if ((vipChoice.getItems().get((Integer) number2) == "Not Random")) {
					vipPercentStatic.setVisible(true);
					vipPercentMin.setVisible(false);
					toVipPercentage.setVisible(false);
					vipPercentMax.setVisible(false);
					submitVip.setVisible(true);
					vipPercentIsRandom = false;
				}

			}
		});

	}

	@FXML
	protected void handleVipButton(ActionEvent event) {

		if (vipPercentIsRandom) {
			if (vipPercentMin.getText().isEmpty() || vipPercentMax.getText().isEmpty()) {
				output.setText("Must enter minimum and maximum numbers!");
				return;
			}
			if (Integer.parseInt(vipPercentMin.getText()) < 0 || Integer.parseInt(vipPercentMax.getText()) < 0) {
				output.setText("Can't have negative percent!");
				return;
			}

			if (Integer.parseInt(vipPercentMin.getText()) > 100 || Integer.parseInt(vipPercentMax.getText()) > 100) {
				output.setText("Can't be over 100 percent!");
				return;
			}

			if (Integer.parseInt(vipPercentMin.getText()) > Integer.parseInt(vipPercentMax.getText())) {
				output.setText("The minimum number cannot be greater than the maximum number!");
				return;
			}

			int min = Integer.parseInt(vipPercentMin.getText());
			int max = Integer.parseInt(vipPercentMax.getText());

			Random r = new Random();
			vipPercent = r.nextInt((max - min) + 1) + min;

		}

		if (!vipPercentIsRandom) {

			if (vipPercentStatic.getText().isEmpty()) {
				output.setText("Must enter a percentage!");
				return;
			}
			if (Integer.parseInt(vipPercentStatic.getText()) < 0) {
				output.setText("Can't have negative percent!");
				return;
			}
			if (Integer.parseInt(vipPercentStatic.getText()) > 100) {
				output.setText("Can't be over 100 percent!");
				return;
			}

			vipPercent = Integer.parseInt(vipPercentStatic.getText());

		}

		output.setText(vipPercent + "% of riders are VIPs");

		submitButton.setVisible(true);

	}

	@FXML
	protected void handleSubmitRidersPerFloor(ActionEvent event) {

		if (numOfRidersIsRandom) {

			if (numOfRidersMin.getText().isEmpty() || numOfRidersMax.getText().isEmpty()) {
				output.setText("Must enter minimum and maximum numbers!");
				return;
			}
			if (Integer.parseInt(numOfRidersMin.getText()) < 0 || Integer.parseInt(numOfRidersMax.getText()) < 0) {
				output.setText("Can't have negative passengers!");
				return;
			}

			if (Integer.parseInt(numOfRidersMin.getText()) > Integer.parseInt(numOfRidersMax.getText())) {
				output.setText("The minimum number cannot be greater than the maximum number!");
				return;
			}
			if (Integer.parseInt(numOfRidersMax.getText()) > 500) {
				output.setText("That's too many people! Chose a maximum number less than 500");
				return;
			}

			int min = Integer.parseInt(numOfRidersMin.getText());
			int max = Integer.parseInt(numOfRidersMax.getText());

			Random r = new Random();
			ridersPerFloor = r.nextInt((max - min) + 1) + min;

			output.setText("There will be " + ridersPerFloor + " riders added to elevator per floor");

		}

		if (!numOfRidersIsRandom) {

			if (numOfRidersStatic.getText().isEmpty()) {
				output.setText("Must enter a number of passengers!");
				return;
			}
			if (Integer.parseInt(numOfRidersStatic.getText()) < 0) {
				output.setText("Can't have negative passengers!");
				return;
			}
			if (Integer.parseInt(numOfRidersStatic.getText()) > 500) {
				output.setText("That's too many people! Chose a number less than 500");
				return;
			}

			ridersPerFloor = Integer.parseInt(numOfRidersStatic.getText());
			output.setText("There will be " + ridersPerFloor + " riders added to elevator per floor");

		}

		numOfRidersHomesLabel.setVisible(true);
		currentFloorLabel.setVisible(true);
		numofHomedChoice.setVisible(true);
		numofHomedChoice.getItems().add("Random");
		numofHomedChoice.getItems().add("Not Random");

	}

	@FXML
	protected void handleSubmitHomedPerFloor() {

		if (numOfHomedIsRandom) {

			if (numOfHomedMin.getText().isEmpty() || numOfHomedMax.getText().isEmpty()) {
				output.setText("Must enter minimum and maximum numbers!");
				return;
			}
			if (Integer.parseInt(numOfHomedMin.getText()) < 0 || Integer.parseInt(numOfHomedMax.getText()) < 0) {
				output.setText("Can't have negative passengers!");
				return;
			}

			if (Integer.parseInt(numOfHomedMin.getText()) > Integer.parseInt(numOfHomedMax.getText())) {
				output.setText("The minimum number cannot be greater than the maximum number!");
				return;
			}
			if (Integer.parseInt(numOfHomedMax.getText()) > 500) {
				output.setText("That's too many people! Chose a maximum number less than 500");
				return;
			}

			int min = Integer.parseInt(numOfHomedMin.getText());
			int max = Integer.parseInt(numOfHomedMax.getText());

			Random r = new Random();

			int ranValue = r.nextInt((max - min) + 1) + min;

			homedPerFloor.put(currentFloor, ranValue);

			textOutput += "There will be " + ranValue + " riders homed on floor " + currentFloor + '\n';

			output.setText(textOutput);

			currentFloor++;

			currentFloorLabel.setText("" + currentFloor);

			// output.setText("There will be " + ridersPerFloor + " riders added to elevator
			// per floor");

		}

		if (!numOfHomedIsRandom) {

			if (numOfHomedStatic.getText().isEmpty()) {
				output.setText("Must enter a number of passengers!");
				return;
			}
			if (Integer.parseInt(numOfHomedStatic.getText()) < 0) {
				output.setText("Can't have negative passengers!");
				return;
			}
			if (Integer.parseInt(numOfHomedStatic.getText()) > 500) {
				output.setText("That's too many people! Chose a number less than 500");
				return;
			}

			int staticValue = Integer.parseInt(numOfHomedStatic.getText());

			homedPerFloor.put(currentFloor, staticValue);

			textOutput += "There will be " + staticValue + " riders homed on floor " + currentFloor + '\n';

			output.setText(textOutput);

			currentFloor++;

			currentFloorLabel.setText("" + currentFloor);
		}

		if (this.currentFloor == this.floors + 1) {

			submitHomedPerFloor.setVisible(false);

			percentageOfVipsLabel.setVisible(true);
			vipChoice.setVisible(true);

			System.out.print(homedPerFloor);

			vipChoice.getItems().add("Random");
			vipChoice.getItems().add("Not Random");

			currentFloorLabel.setText("" + (currentFloor - 1));

			return;

		}

	}

	@FXML
	protected void handleSubmitNumOfFloors(ActionEvent event) {

		if (numOfFloors.getText().isEmpty()) {
			output.setText("Must enter a number of floors");
			return;
		}

		if (Integer.parseInt(numOfFloors.getText()) > 50) {
			output.setText("Thats a lot of floors! Enter a number between 1 and 50");
			return;
		}

		if (Integer.parseInt(numOfFloors.getText()) == 0) {
			output.setText("A building can't have 0 floors! Enter a number between 1 and 10");
			return;
		}

		if (Integer.parseInt(numOfFloors.getText()) < 0) {
			output.setText("A building can't have negative floors! Enter a number between 1 and 10");
			return;
		}

		this.floors = Integer.parseInt(numOfFloors.getText());
		System.out.print(floors);

		output.setText("The building has " + floors + " floors!");

		numofRidersChoice.getItems().add("Random");
		numofRidersChoice.getItems().add("Not Random");

		numOfRidersLabel.setVisible(true);
		numofRidersChoice.setVisible(true);

	}

	@FXML
	protected void handleSubmit(ActionEvent event) {

		/**
		 * Variables from user:
		 * 
		 * floors (int)
		 * 
		 * vipPercent (int)
		 * 
		 * ridersPerFloor (int)
		 * 
		 * homedPerFloor (HashMap<floor(int), people homed at that floor (int) >)
		 */

		textOutput = "RESULTS: \n";

		// Setup Building
		PriorityQueue<ElevatorRider>[] building = new PriorityQueue[5];
		for (int i = 0; i < building.length; i++)
			building[i] = new PriorityQueue();

		// Setup riders
		ArrayList<ElevatorRider> riders = new ArrayList();

		addRiders(110, 5, riders);
		addRiders(75, 4, riders);
		addRiders(65, 3, riders);
		addRiders(100, 2, riders);

		Collections.shuffle(riders);

		// Make VIPs
		for (int i = 0; i < riders.size() / 10; i++) {
			riders.get(i).promote();
		}

		Collections.shuffle(riders);

		// Setup Elevator
		Elevator elevator = new Elevator();

		// Test code to make sure that the riders worked!
		/*
		 * for (ElevatorRider rider: riders) System.out.println(rider);
		 */

		// Morning Mode
		int currentRider = 0;
		do {
			// New rider!
			if (currentRider < riders.size()) {
				// 10 new riders
				for (int index = 0; index < 10; index++) {
					building[0].add(riders.get(currentRider++));
				}
			}

			// Pop riders for elevator's current floor
			while (elevator.peek() != null && elevator.peek().getHomeFloor() == elevator.getCurrentFloor()) {
				building[elevator.getCurrentFloor() - 1].add(elevator.pop());
			}

			// System.out.println("AFTER POPS" + elevator);

			// Frustrate
			elevator.frustrate();

			// Push riders from current floor to elevator
			while (elevator.getCurrentFloor() == 1 && !elevator.isFull()
					&& !building[elevator.getCurrentFloor() - 1].isEmpty())
				elevator.push(building[elevator.getCurrentFloor() - 1].remove());

			// System.out.println("ELEVATOR BEFORE MOVE: " + elevator);

			// Move elevator
			if (elevator.peek() != null)
				elevator.setCurrentFloor(elevator.peek().getHomeFloor());
			else
				elevator.setCurrentFloor(1);

			System.out.println("ELEVATOR AT END: " + elevator);
		} while (currentRider < riders.size() || elevator.peek() != null || building[0].peek() != null);

		// Output
		int result = 0;
		int resultVIP = 0;
		int resultNoVIP = 0;
		for (ElevatorRider rider : riders) {
			result += rider.getFrustration();
			if (rider.isVIP())
				resultVIP += rider.getFrustration();
			else
				resultNoVIP += rider.getFrustration();
		}

		System.out.println("AM MODE:\n\tAverage (MEAN) Frustration Level is: " + ((double) result / riders.size()));

		textOutput += "(AM Mode): Average (MEAN) Frustration Level is: " + ((double) result / riders.size()) + "\n";

		System.out.println(
				"AM MODE:\n\tAverage (MEAN) VIP Frustration Level is: " + ((double) resultVIP / (riders.size() / 10)));

		textOutput += "(AM Mode): Average (MEAN) VIP Frustration Level is: "
				+ ((double) resultVIP / (riders.size() / 10)) + "\n";

		System.out.println("AM MODE:\n\tAverage (MEAN) Non-VIP Frustration Level is: "
				+ ((double) resultNoVIP / (riders.size() * .9)));

		textOutput += "(AM Mode): Average (MEAN) Non-VIP Frustration Level is: "
				+ ((double) resultNoVIP / (riders.size() / 10)) + "\n";

		output.setText(textOutput);
		resetButton.setVisible(true);

	}

	@FXML
	protected void handleAbout(ActionEvent event) {

		Stage about = new Stage();

		about.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("application/icon.png")));
		about.setTitle("About Elevator App");
		FXMLLoader appLoad = new FXMLLoader(getClass().getResource("about.fxml"));
		Parent root = null;
		try {
			root = appLoad.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene(root);
		about.setScene(scene);

		about.show();

	}

	public static void addRiders(int quantity, int floor, ArrayList<ElevatorRider> list) {
		for (int i = 0; i < quantity; i++)
			list.add(new ElevatorRider(floor));
	}

}
