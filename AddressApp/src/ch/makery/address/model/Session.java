package ch.makery.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Session {
	
	private final StringProperty sessionID;
	private final StringProperty sessionDate;
	private final String patientID;
	private final String sessionDateStr; 
	
	double bestTap = 0.0;
	double meanTap = 0.0;
	double medianTap = 0.0;
	double stdDeviationTap = 0.0;
	double bestSpin = 0.0;
	double meanSpin = 0.0;
	double medianSpin = 0.0;
	double stdDeviationSpin = 0.0;
	double bestSqueeze = 0.0;
	double meanSqueeze = 0.0;
	double medianSqueeze = 0.0;
	double stdDeviationSqueeze = 0.0;
	
	//private final StringProperty gameMode;
	
	 
    

	/**
	 * Default constructor.
	 */

	public Session() {
		this(null, null, null);
	}
	
	/**
	 * Creates a new session object with session ID, Date and Patient ID
	 * @param sessionID
	 * @param sessionDate
	 * @param patientID
	 */
	public Session(String sessionID, String sessionDate, String patientID/*, String gameMode*/) {
		this.sessionID = new SimpleStringProperty(sessionID);
		this.sessionDate = new SimpleStringProperty(sessionDate);
		this.sessionDateStr = sessionDate; 
		this.patientID = patientID;
		//this.gameMode = new SimpleStringProperty(gameMode);
	}

	/**
	 * Sets up session data for Session Info Page
	 */
	public void setSessionInfo(String bestTap) {
		this.bestTap = Double.parseDouble(bestTap);
	}
	
	
	/**
	 * Returns the session ID String Property
	 * @return
	 */
	public StringProperty sessionIDProperty() {
		return sessionID; 
	}

	/**
	 * Returns the session date String Property
	 * @return
	 */
	public StringProperty sessionDateProperty() {
		return sessionDate; 
	}

	/**
	 * Returns the session data as a String
	 */
	public String getSessionDateAsString() {
		
		//TODO Make date pretty  - 6th June 2017
		return sessionDateStr; 
	}
	
	/**
	 * Returns session ID
	 * @return sessionID
	 */
	public String getID () {
		return this.sessionID.get(); 
	}

	/**
	 * Returns best tap value
	 * @return bestTap
	 */
	public String getBestTap() {
		return Double.toString(this.bestTap);
	}
}
