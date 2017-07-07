package ch.makery.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Session {
	private final StringProperty sessionID;
	private final StringProperty sessionDate;
	private final String patientID;
	//private final StringProperty gameMode;

	/**
	 * Default constructor.
	 */

	public Session() {
		this(null, null, null);
	}
	
	/**
	 * Creates a new session object
	 * @param sessionID
	 * @param sessionDate
	 * @param patientID
	 */
	public Session(String sessionID, String sessionDate, String patientID/*, String gameMode*/) {
		this.sessionID = new SimpleStringProperty(sessionID);
		this.sessionDate = new SimpleStringProperty(sessionDate);
		this.patientID = patientID;
		//this.gameMode = new SimpleStringProperty(gameMode);
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

}
