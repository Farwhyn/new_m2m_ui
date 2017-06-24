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
    private TableView<Patient> sessionsTable;
    @FXML
    private TableColumn<Patient, String> sessionIDColumn;
    @FXML
    private TableColumn<Patient, String> sessionDateColumn;
    @FXML
    private TableColumn<Patient, String> sessionDurationColumn;
    
    // Reference to the main application.
    private MainApp mainApp;
    private SQLiteSync db;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public SessionsForPersonController() {
    	
    }
    
   
    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     * 
     * @param person the person or null
     */
    private void showSessionDetails(Session session) {
        if (session != null) {
            // Fill the labels with info from the session object.
       
        } else {
            // Session is null, remove all the text.
       
        }
    }

    @FXML
    private void initialize() {
      
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.db = new SQLiteSync();

 //       setSessionData(mainApp.getSessionData()); TODO
 //       sessionsTable.setItems(mainApp.getSessionData()); TODO
    }
    
    
    /*
     * Function for clearing the patient table and regenerating the data from the database
     */
    public void setSessionData(ObservableList<Session> data) {
    	data.clear();
        ResultSet rs;
        
            
        try {
            rs = db.displaySessions();
            while(rs.next()) {
                System.out.println(rs.getString("id"));
                System.out.println(rs.getString("pid"));
                data.add(new Session(rs.getString("id"), rs.getString("vdate"), rs.getString("pid"))); //TODO fix this
            }
        } catch (ClassNotFoundException e) {
        		e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

    }

	
}
