package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import ch.makery.address.MainApp;
import ch.makery.address.model.Patient;
import ch.makery.address.model.Person;
import ch.makery.address.model.Session;
import ch.makery.address.util.DateUtil;
import ch.makery.address.util.SQLiteSync;

public class PersonOverviewController {
	@FXML
	private TableView<Patient> personTable;
	@FXML
	private TableColumn<Patient, String> firstNameColumn;
	@FXML
	private TableColumn<Patient, String> lastNameColumn;

	/*
	 * @FXML private Label firstNameLabel;
	 * 
	 * @FXML private Label lastNameLabel;
	 * 
	 * @FXML private Label streetLabel;
	 * 
	 * @FXML private Label careCardLabel;
	 * 
	 * @FXML private Label cityLabel;
	 * 
	 * @FXML private Label visitDateLabel;
	 */

	@FXML
	private BarChart<String, Integer> barChart;

	@FXML
	private CategoryAxis xAxis;

	@FXML
	private TabPane tabPane;

	private int numTabs = 0;

	private ObservableList<String> monthNames = FXCollections.observableArrayList();

	// List of sessions for a person
	private ObservableList<Session> personSessions = FXCollections.observableArrayList();

	// Reference to the main application.
	private MainApp mainApp;
	private SQLiteSync db;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public PersonOverviewController() {

	}

	/**
	 * Fills all text fields to show details about the person. If the specified
	 * person is null, all text fields are cleared.
	 * 
	 * @param person
	 *            the person or null
	 */
	/*
	 * private void showPersonDetails(Patient patient) { if (patient != null) {
	 * // Fill the labels with info from the person object.
	 * firstNameLabel.setText(patient.getFirstName());
	 * lastNameLabel.setText(patient.getLastName());
	 * streetLabel.setText(patient.getStreet());
	 * careCardLabel.setText(patient.getCardCode());
	 * cityLabel.setText(patient.getCity());
	 * 
	 * visitDateLabel.setText(patient.getVisitDate()); } else { // Person is
	 * null, remove all the text. firstNameLabel.setText("");
	 * lastNameLabel.setText(""); streetLabel.setText("");
	 * careCardLabel.setText(""); cityLabel.setText("");
	 * visitDateLabel.setText(""); } }
	 */

	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

		// Clear person details.
		// showPersonDetails(null);

		// Listen for selection changes and show the person details when
		// changed.

		// Get an array with the English month names.
		// String[] months =
		// DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };
		// Convert it to a list and add it to our ObservableList of months.
		monthNames.addAll(Arrays.asList(months));

		// Assign the month names as categories for the horizontal axis.
		xAxis.setCategories(monthNames);
		// xAxis.setLabel("Month");

		barChart.setLegendVisible(false);

		personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

			handleNewListSessionsTab(newValue);

		});

		// showPersonDetails(newValue));
		// handleNewListSessionsTab(null));
		// mainApp.showSessionInfo());

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.db = new SQLiteSync();

		setPersonData(mainApp.getPersonData());
		personTable.setItems(mainApp.getPersonData());
	}

	/*
	 * Function for clearing the patient table and regenerating the data from
	 * the database
	 */
	public void setPersonData(ObservableList<Patient> data) {
		data.clear();
		ResultSet rs;

		try {
			rs = db.displayUsers();
			while (rs.next()) {
				System.out.println(rs.getString("firstName") + " " + rs.getString("lastName"));
				System.out.println(rs.getString("patientID"));
				data.add(new Patient(rs.getString("patientID"), rs.getString("lastName"), rs.getString("firstName"),
						null, null, null, rs.getString("regDate")));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		refreshChart(data);
	}

	public void refreshChart(ObservableList<Patient> data) {
		int[] monthCounter = new int[12];
		for (Patient p : data) {
			int month = DateUtil.parse(p.getVisitDate()).getMonthValue() - 1;
			monthCounter[month]++;
		}

		XYChart.Series<String, Integer> series = new XYChart.Series<>();

		// Create a XYChart.Data object for each month. Add it to the series.
		for (int i = 0; i < monthCounter.length; i++) {
			series.getData().add(new XYChart.Data<>(monthNames.get(i), monthCounter[i]));
		}
		barChart.getData().clear();
		barChart.getData().add(series);
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			try {
				String id = personTable.getItems().get(selectedIndex).getID();
				db.deletePatient(id);

			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			setPersonData(mainApp.getPersonData());
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * Called when the user clicks the new button. Opens a dialog to edit
	 * details for a new person.
	 * 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void handleNewPerson() throws ClassNotFoundException, SQLException {
		Patient tempPatient = new Patient();
		boolean okClicked = mainApp.showPersonEditDialog(tempPatient, "New Patient");
		if (okClicked) {
			// mainApp.getPersonData().add(tempPatient);
			db.addPatient(tempPatient);
			setPersonData(mainApp.getPersonData());
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void handleEditPerson() {
		Patient selectedPatient = personTable.getSelectionModel().getSelectedItem();
		if (selectedPatient != null) {
			boolean okClicked = mainApp.showPersonEditDialog(selectedPatient, "Edit Patient");
			if (okClicked) {
				// showPersonDetails(selectedPatient);
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please select a person in the table.");

			alert.showAndWait();
		}
	}

	/**
	 * new tab trial
	 * 
	 * @throws IOException
	 */
	public void handleNewListSessionsTab(Patient patient) {

		if (patient != null) {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("SessionsForPerson.fxml")); 
																							
			Tab tab = new Tab("Sessions for " + patient.getFirstName()); 

			try {

				tab.setContent(loader.load());
				SessionsForPersonController controller = loader.getController();
				controller.setPatient(patient); // Give the controller access to
												// the patient
				controller.setMainApp(mainApp);

			} catch (IOException e) {

				e.printStackTrace();
			}

			tabPane.getTabs().add(tab);
			numTabs++;
			tabPane.getSelectionModel().select(numTabs); // change view to the
															// newly opened tab

		} else {
			System.out.println("patient is null");
		}

	}
}