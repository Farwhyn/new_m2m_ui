package ch.makery.address.view;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.print.DocFlavor.URL;

import ch.makery.address.MainApp;
import ch.makery.address.graphs.Main;
import ch.makery.address.model.Patient;
import ch.makery.address.model.Person;
import ch.makery.address.model.Session;
import ch.makery.address.util.DateUtil;
import ch.makery.address.util.SQLiteSync;


public class LoginController {

	// Reference to the main application.
	private MainApp mainApp;
	private SQLiteSync db;
	
	//Main Graph 
	private static Main maing;
	
	//FXML elements
	@FXML
	private TextField username; 
	@FXML
	private PasswordField password; 
	@FXML
	private Button loginButton;
	@FXML
	private AnchorPane anchorpane; 
	
	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public LoginController() {

	}


	/**
	 * Tries to log in user with the entered credentials. 
	 */
	@FXML
	private void handleLoginRequest(ActionEvent event){
		
		String usernameEntered = username.getText(); 
		String passwordEntered = password.getText(); 
		boolean success = false; 
		
		try {
			success = db.login(usernameEntered, passwordEntered);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		if(success){
			showPersonOverview(event); 
		}
		else {
			//print out an error message
			//password recovery mechanism 
		}
	}
	
    /**
     * Shows list of patients and graph of no. of patients on the side. 
     * TODO change name to patients overview
     */
    public void showPersonOverview(ActionEvent event) {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            
            Parent personOverview = loader.load(); 
            Scene personOverviewScene = new Scene(personOverview); 
            
            //This gets the stage information 
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow(); 
            window.setScene(personOverviewScene);
            window.show(); 
       
            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this.mainApp);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Is called by the main application to give a reference back to itself.
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		this.db = new SQLiteSync();
	}
	
}