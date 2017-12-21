package ch.makery.address.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import ch.makery.address.MainApp;
import ch.makery.address.graphs.Main;
import ch.makery.address.model.Patient;
import ch.makery.address.model.Session;
import ch.makery.address.util.SQLiteSync;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SessionsForPersonController {

    //CONSTANTS
	private static final String SESSIONINFO = "Session Info";
	
    @FXML
    private TableView<Session> sessionsTable;
    @FXML
    private TableColumn<Session, String> sessionIDColumn;
    @FXML
    private TableColumn<Session, String> sessionDateColumn;

	//Main Graph 
	private static Main maing;
	
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
   
     /*   sessionsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

			showSessionInfo(newValue);

		});*/
        
    	sessionsTable.setRowFactory( tv -> {
			TableRow<Session> row = new TableRow<>(); 
			row.setOnMouseClicked(event -> {
				if(event.getClickCount() == 2 && (!row.isEmpty())) {
					Session selectedSession = row.getItem(); 
					showSessionInfo(selectedSession);  
				}
			});
			return row; 
		});
    }

 
   
    
    /**
     * Gives a reference back to the patient
     * Also initializes a database instance and sets up table data
     * @param patient: the patient who's sessions need to be shown
     */
    public void setPatient(Patient patient) {
    	this.patient = patient; 
    	this.db = new SQLiteSync();
    	setSessionData(patient.getSessionData()); 
    	sessionsTable.setItems(patient.getSessionData()); 
    }
    
    
    /**
     * Clears the sessions table and regenerates session 
     * information from the database
     */
    public void setSessionData(ObservableList<Session> data) {
    	
    	data.clear();
        data =  db.displaySessionsForPatient(patient, data); 
      
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

    /**
     * Creates a new session info window.
     * @param session: the session for which info is required. 
     * @param title: title of the window
     * TODO cleanup references?  
     */
    public void showSessionInfo(Session session) {
    	try {
    		
    		// Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SessionInfo.fxml"));
            AnchorPane sessionInfo = (AnchorPane) loader.load();
    		String title = session.getSessionDateAsString();
          
    		//Create the new window stage
            Stage sessionInfoStage = new Stage();
            sessionInfoStage.setTitle(SESSIONINFO + " (" + title + ")");
            sessionInfoStage.initModality(Modality.NONE);
       
            //TODO do we really need this? sessionInfoStage.initOwner();
            
            Scene scene = new Scene(sessionInfo);
            sessionInfoStage.setScene(scene);
            
            //Give the controller access to the main app.
            sessionInfoController controller = loader.getController();
            controller.setSession(session);
            sessionInfoStage.show(); 
            
    	} catch (Exception e){
    		e.printStackTrace();
    	}
    }
    
    @FXML
	void handleNewSessionOpen() {
		 if(maing != null) {
		        maing.show(); 
		    }
		    else{
		        maing = new Main();
		        maing.show();
		    }
	}
	
}
