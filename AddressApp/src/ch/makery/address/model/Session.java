package ch.makery.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Session {
	private final StringProperty session_id;
	private final StringProperty session_date;
	private final String patient_id;
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
		this.session_id = new SimpleStringProperty(sessionID);
		this.session_date = new SimpleStringProperty(sessionDate);
		this.patient_id = patientID;
		//this.gameMode = new SimpleStringProperty(gameMode);
	}

}
