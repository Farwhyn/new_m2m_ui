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

public class sessionInfoController {
	@FXML
	private Label bestLabelTap;
	@FXML
	private Label bestLabelSpin;
	@FXML
	private Label bestLabelSqueeze;
	
	@FXML
	private Label meanLabelTap;
	@FXML
	private Label meanLabelSpin;
	@FXML
	private Label meanLabelSqueeze;
	
	@FXML
	private Label medianLabelTap;
	@FXML
	private Label medianLabelSpin;
	@FXML
	private Label medianLabelSqueeze;
	
	@FXML
	private Label stdDeviationLabelTap;
	@FXML
	private Label stdDeviationLabelSpin;
	@FXML
	private Label stdDeviationLabelSqueeze;
	
	
	@FXML 
	private TabPane tabPane;
	
	
    // Reference to the main application.
    private Session session;
    private SQLiteSync db;
    
    /**
     * The constructor.
     * This constructor is called before the initialize() method
     */
    
    public sessionInfoController(){
    	
    }
    
    
    /**
     * Initializes the controller class.
     * This method is automatically called after the fxml file is loaded
     */
	@FXML
	private void initialize() {
		
	}

	/**
	 * This method is called by the mainApp to give a reference
	 * back to itself
	 * @param mainApp
	 */
	public void setSession(Session session){
		this.session = session;
		this.db = new SQLiteSync(); 
		setSessionInfo(); 
    }
    
    
    /**
     * Clears the sessions table and regenerates session 
     * information from the database
     */
    public void setSessionInfo() {
    	
    	db.getSessionInfo(session);
    	bestLabelTap.setText(session.getBestTap());
      
    }
	
}
