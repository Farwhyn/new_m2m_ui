package ch.makery.address.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ch.makery.address.model.Patient;

public class SQLiteSync {
    
    private static Connection con;
    private static boolean hasData = false;
    
    public ResultSet displayUsers() throws ClassNotFoundException, SQLException {
        if (con == null) {
            getConnection();
        }
        
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT patientID, firstName, lastName, street, city, careCardNum, regDate FROM patients");

        
            
        return res;
    }
    public ResultSet displaySessions() throws ClassNotFoundException, SQLException{
    	 if (con == null) {
             getConnection();
         }
    	 
    	Statement state4 = con.createStatement();
    	ResultSet rs2 = state4.executeQuery("SELECT id, pid, fname, lname, vdate, FROM sess");
    	return rs2;
    }

    private void getConnection() throws ClassNotFoundException, SQLException {
        
        Class.forName("org.sqlite.JDBC"); //find the sqlite JDBC driver
        con = DriverManager.getConnection("jdbc:sqlite:Resources/Database/m2m.db"); //connect to the database
        initialise();
    }

    private void initialise() throws SQLException {
      
        if(!hasData) {
            hasData = true;
        }
        
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = 'patients'");
/*
        if(!res.next()) {
            System.out.println("Initializing the patient table");
            
            //build the table
            Statement state2 = con.createStatement();
            state2.execute("CREATE TABLE user(id integer," + "fName varchar(60)," + "lName varchar(60)," + "sName varchar(60),"
                    + "primary key(id));");
            
            //insert some sample data
            PreparedStatement prep = con.prepareStatement("INSERT INTO user values(?, ?, ?, ?)");
            prep.setString(2, "James");
            prep.setString(3, "Zhou");
            prep.setString(4, "August 11, 2016");
            prep.execute();
            
            
            PreparedStatement prep2 = con.prepareStatement("INSERT INTO user values(?, ?, ?, ?)");
            prep2.setString(2, "Andrew");
            prep2.setString(3, "Yan");
            prep2.setString(4, "September 8, 2016");
            prep2.execute();
            
            PreparedStatement prep3 = con.prepareStatement("INSERT INTO user values(?, ?, ?, ?)");
            prep3.setString(2, "May");
            prep3.setString(3, "Liang");
            prep3.setString(4, "October 6, 2016");
            prep3.execute();
            
        }
        
        
     */   
    }
    
    public void addPatient(Patient patient) throws ClassNotFoundException, SQLException {
        if(con == null) {
            getConnection();
        }
        
        PreparedStatement prep = con.prepareStatement("INSERT INTO patients values(?, ?, ?, ?, ?, ?, ?)");
        prep.setString(2, patient.getFirstName());
        prep.setString(3, patient.getLastName());
        prep.setString(4, patient.getStreet());
        prep.setString(5, patient.getCity());
        prep.setString(6, patient.getCardCode());
        prep.setString(7, patient.getVisitDate());
        prep.execute();
    }
    
    public void deletePatient(String patient_id) throws ClassNotFoundException, SQLException {
    	if(con ==null){
    		getConnection();
    	}
    	
    	PreparedStatement prep = con.prepareStatement("DELETE FROM patients WHERE patientID = ?");
    	prep.setString(1, patient_id);
    	prep.execute();
    }
}
