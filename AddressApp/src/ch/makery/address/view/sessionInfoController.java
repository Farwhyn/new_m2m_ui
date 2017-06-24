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
import ch.makery.address.util.DateUtil;
import ch.makery.address.util.SQLiteSync;

public class sessionInfoController {
	@FXML
	private Label bestLabel;
	
	@FXML
	private Label meanLabel;
	
	@FXML
	private Label medianLabel;
	
	@FXML
	private Label stdDeviationLabel ;
	
	@FXML 
	private TabPane tabPane;
	
    // Reference to the main application.
    private MainApp mainApp;
    private SQLiteSync db;
    
    /**
     * The constructor.
     * This constructor is called before the initialize() method
     */
    
    public sessionInfoController(){
    	//stdDeviationLabel.setText("45");
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
	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
		
	}
	
	/**
	 * new tab trial
	 * @throws IOException 
	 */
	public void newTab(){
	
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("PersonSessionsTab.fxml"));
        Tab tab = new Tab("Some title");
        try {
			tab.setContent(loader.load());
		} catch (IOException e) {
		
			e.printStackTrace();
		}
        
		tabPane.getTabs().add(tab);
		tabPane.getSelectionModel().select(2);
		tab.setClosable(true);
		
	}
}
