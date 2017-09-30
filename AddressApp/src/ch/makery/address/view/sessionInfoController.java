package ch.makery.address.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	
	@FXML
    private LineChart<String, Double> tapChart;
	
	@FXML
    private LineChart<String, Double> squeezeChart;
	
	@FXML
    private LineChart<String, Double> spinChart;

    @FXML
    private CategoryAxis xTapAxis;
    
    @FXML
    private NumberAxis yTapAxis;
    
    @FXML
    private CategoryAxis xSqueezeAxis;
    
    @FXML
    private NumberAxis ySqueezeAxis;
    
    @FXML
    private CategoryAxis xSpinAxis;
    
    @FXML
    private NumberAxis ySpinAxis;

    //private ObservableList<String> monthNames = FXCollections.observableArrayList();
	
	
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
		//xTapAxis = new NumberAxis();
        //yTapAxis = new NumberAxis();
        //xSqueezeAxis = new NumberAxis();
        //ySqueezeAxis = new NumberAxis();
        //xSpinAxis = new NumberAxis();
        //ySpinAxis = new NumberAxis();
       
		
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
		XYChart.Series<String, Double> seriesTap = new XYChart.Series<>();
		XYChart.Series<String, Double> seriesSqueeze = new XYChart.Series<>();
		XYChart.Series<String, Double> seriesSpin = new XYChart.Series<>();
		
		
	       

		String csvFile = "C:/Users/james/Desktop/testbook3.csv";
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";

	        try {

	            br = new BufferedReader(new FileReader(csvFile));
	            br.readLine();
	            int count = 0;
	            while ((line = br.readLine()) != null) {
	            	if (count != 0) {
	            		String[] data = line.split(cvsSplitBy);
	 	                //System.out.println(Integer.parseInt(data[0]));
	 	                seriesTap.getData().add(new XYChart.Data<>(data[0], Double.parseDouble(data[1])));
	 	                seriesSqueeze.getData().add(new XYChart.Data<>(data[0], Double.parseDouble(data[2])));
	 	                seriesSpin.getData().add(new XYChart.Data<>(data[0], Double.parseDouble(data[3])));
	            	}
	                count++;
	            }
	           tapChart.getData().add(seriesTap);
	           spinChart.getData().add(seriesSpin);
	           squeezeChart.getData().add(seriesSqueeze);

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }

	    }

    
    
    
    /**
     * Clears the sessions table and regenerates session 
     * information from the database
     */
    public void setSessionInfo() {
    	
    	db.getSessionInfo(session);
    	
    	bestLabelTap.setText(session.getBestTap());
    	meanLabelTap.setText(session.getMeanTap());
    	medianLabelTap.setText(session.getMedianTap());
    	stdDeviationLabelTap.setText(session.getStdDeviationTap());
    	
    	bestLabelSpin.setText(session.getBestSpin());
    	meanLabelSpin.setText(session.getMeanSpin());
    	medianLabelSpin.setText(session.getMedianSpin());
    	stdDeviationLabelSpin.setText(session.getStdDeviationSpin());
    	
    	bestLabelSqueeze.setText(session.getBestSqueeze());
    	meanLabelSqueeze.setText(session.getMeanSqueeze());
    	medianLabelSqueeze.setText(session.getMedianSqueeze());
    	stdDeviationLabelSqueeze.setText(session.getStdDeviationSqueeze());
   
    }
	
}
