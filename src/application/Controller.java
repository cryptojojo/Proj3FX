package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Controller {

	// submit button
	@FXML
	private Button submitButton;

	// text fields
	@FXML
	private TextField numOfFloors;
	@FXML
	private TextField numOfRidersMin;
	@FXML
	private TextField numOfRidersMax;
	@FXML
	private TextField vip;

	// text output to GUI
	@FXML
	private TextArea output;

	private String textOutput = "";
	
	// choice boxes
	@FXML
	private ChoiceBox numofRidersChoice;


	@FXML
	protected void initialize() {
		numofRidersChoice.getItems().add("Random");
		numofRidersChoice.getItems().add("Not Random");
	}

	@FXML
	protected void handleSubmit(ActionEvent event) {

		// check that everything is filled out


		
		Boolean notFilled = false;

		if (numOfFloors.getText().isEmpty()) {
			textOutput += "NEED AN ENTRY FOR NUMBER OF FLOORS\n";
			notFilled = true;
		}
		if (numOfRidersMin.getText().isEmpty()) {
			textOutput += "NEED AN ENTRY FOR NUMBER OF RIDERS MINIMUM\n";
			notFilled = true;
		}
		if (numOfRidersMax.getText().isEmpty()) {
			textOutput += "NEED AN ENTRY FOR NUMBER OF RIDERS MAXIMUM\n";
			notFilled = true;
		}
		if (vip.getText().isEmpty()) {
			textOutput += "NEED AN ENTRY FOR PERCENTAGE OF VIPS\n";
			notFilled = true;
		}
		if (vip.getText().isEmpty()) {
			textOutput += "NEED AN ENTRY FOR PERCENTAGE OF VIPS\n";
			notFilled = true;
		}

		if (notFilled) {
			output.setText(textOutput);
			textOutput = "";
			return;
		}

		//
		//
		// SCOTTS CODE
		//
		//

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

		int vipI = Integer.parseInt(vip.getText());
		for (int i = 0; i < riders.size() / vipI; i++) {
			riders.get(i).promote();
		}

		textOutput += "\n\n\n" + vipI + "\n" + riders.size() + "\n\n\n";

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

			textOutput += "ELEVATOR AT END: " + elevator + '\n';

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

		System.out.println("AM MODE:\n   Average (MEAN) Frustration Level is: " + ((double) result / riders.size()));
		textOutput += "AM MODE:\n   Average (MEAN) Frustration Level is: " + ((double) result / riders.size()) + '\n';

		System.out.println(
				"AM MODE:\n   Average (MEAN) VIP Frustration Level is: " + ((double) resultVIP / (riders.size() / 10)));
		textOutput += "AM MODE:\n   Average (MEAN) VIP Frustration Level is: "
				+ ((double) resultVIP / (riders.size() / 10)) + '\n';

		System.out.println("AM MODE:\n   Average (MEAN) Non-VIP Frustration Level is: "
				+ ((double) resultNoVIP / (riders.size() * .9)));
		textOutput += "AM MODE:\n   Average (MEAN) Non-VIP Frustration Level is: "
				+ ((double) resultNoVIP / (riders.size() * .9)) + '\n';

		// Evening Mode
		// Reset Starting Conditions
		currentRider = 0;
		for (int i = 0; i < riders.size(); i++)
			riders.get(i).setFrustration(0);
		for (int i = 0; i < building.length; i++)
			while (!building[i].isEmpty())
				building[i].remove();
		do {
			// New rider!
			if (currentRider < riders.size()) {
				// 10 new riders
				for (int index = 0; index < 10; index++) {
					building[riders.get(currentRider).getHomeFloor() - 1].add(riders.get(currentRider));
					riders.get(currentRider).setHomeFloor(1);
					currentRider++;
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
			while (elevator.getCurrentFloor() != 1 && !elevator.isFull()
					&& !building[elevator.getCurrentFloor() - 1].isEmpty())
				elevator.push(building[elevator.getCurrentFloor() - 1].remove());

			// System.out.println("ELEVATOR BEFORE MOVE: " + elevator);

			// Move elevator
			Random gen = new Random();
			if (elevator.peek() != null)
				elevator.setCurrentFloor(elevator.peek().getHomeFloor());
			else
				elevator.setCurrentFloor(gen.nextInt(4) + 2);

			System.out.println("ELEVATOR AT END: " + elevator);
			textOutput += "ELEVATOR AT END: " + elevator + '\n';
			// System.out.println(building[1]);
		} while (currentRider < riders.size() || elevator.peek() != null || building[1].peek() != null
				|| building[2].peek() != null || building[3].peek() != null || building[4].peek() != null);

		// Output
		result = 0;
		resultVIP = 0;
		resultNoVIP = 0;
		for (ElevatorRider rider : riders) {
			result += rider.getFrustration();
			if (rider.isVIP())
				resultVIP += rider.getFrustration();
			else
				resultNoVIP += rider.getFrustration();
		}

		System.out.println(
				"PM MODE:\n   Average (MEAN) Total Frustration Level is: " + ((double) result / riders.size()));
		textOutput += "PM MODE:\n   Average (MEAN) Total Frustration Level is: " + ((double) result / riders.size())
				+ '\n';

		System.out.println(
				"PM MODE:\n   Average (MEAN) VIP Frustration Level is: " + ((double) resultVIP / (riders.size() / 10)));
		textOutput += "PM MODE:\n   Average (MEAN) VIP Frustration Level is: "
				+ ((double) resultVIP / (riders.size() / 10)) + '\n';

		System.out.println("PM MODE:\n   Average (MEAN) Non-VIP Frustration Level is: "
				+ ((double) resultNoVIP / (riders.size() * .9)));
		textOutput += "PM MODE:\n   Average (MEAN) Non-VIP Frustration Level is: "
				+ ((double) resultNoVIP / (riders.size() * .9)) + '\n';

		// puts the output in the GUI
		output.setText(textOutput);
		textOutput = "";

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
