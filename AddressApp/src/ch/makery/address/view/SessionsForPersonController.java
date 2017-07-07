package ch.makery.address.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import ch.makery.address.MainApp;
import ch.makery.address.model.Patient;
import ch.makery.address.model.Session;
import ch.makery.address.util.SQLiteSync;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SessionsForPersonController {

    @FXML
    private TableView<Session> sessionsTable;
    @FXML
    private TableColumn<Session, String> sessionIDColumn;
    @FXML
    private TableColumn<Session, String> sessionDateColumn;

    
    //Reference to the main application.
    private MainApp mainApp;
    private SQLiteSync db;

    //Reference to person
    private Patient patient; 
   
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SessionsForPersonController() {
    	
    }
    
    @FXML
    private void initialize() {
    	//Initialize the Sessions Column
    	sessionIDColumn.setCellValueFactory(cellData -> cellData.getValue().sessionIDProperty());
        sessionDateColumn.setCellValueFactory(cellData -> cellData.getValue().sessionDateProperty());
    }

 
   
    
    /**
     * Gives a reference back to the patient
     * @param patient: the patient who's sessions need to be shown
     */
    public void setPatient(Patient patient) {
    	this.patient = patient; 
    }
    
    /**
     * Gives a reference back to the Main App. 
     * Also initializes a database instance
     * @param mainApp - the mainApp
     */
    public void setMainApp(MainApp mainApp){
    	this.mainApp = mainApp; 
    	this.db = new SQLiteSync();
    //	TODO
    	setSessionData(mainApp.getSessionData()); 
    	sessionsTable.setItems(mainApp.getSessionData()); 
    	
    }
    
    /**
     * Clears the sessions table and regenerates session 
     * information from the database
     */
    public void setSessionData(ObservableList<Session> data) {
    	
    	data.clear();
        ResultSet rs = null;  
            
       // try {
           // rs = db.displaySessionsForPatient(patient);  
            db.displaySessionsForPatient(patient, data); 
         //   while(rs.next()) {
               // data.add(new Session(rs.getString("sessionID"), rs.getString("sessionDate"), rs.getString("patientID"))); 
          //  }
   //     } catch (SQLException e) {
       //     e.printStackTrace();
      //  }
       
    }
    
    
    /**
     * Fills all text fields to show details about the session.
     * If the specified session is null, all text fields are cleared.
     * 
     * @param session
     */
    private void showSessionDetails(Session session) {
        if (session != null) {
            // Fill the labels with info from the session object.
       
        } else {
            // Session is null, remove all the text.
       
        }
    }

	
}
