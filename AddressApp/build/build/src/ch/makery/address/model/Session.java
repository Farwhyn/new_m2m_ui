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
	public void setSessionInfo(String bestTap, String meanTap, String medianTap, String stdDeviationTap,
			String bestSpin, String meanSpin, String medianSpin, String stdDeviationSpin,
			String bestSqueeze, String meanSqueeze, String medianSqueeze, String stdDeviationSqueeze) {
		//TODO handle null values - set default in db to 0.0, not null ? 
		//set Tap info
		this.bestTap = Double.parseDouble(bestTap);
		this.meanTap = Double.parseDouble(meanTap);	
		this.medianTap = Double.parseDouble(medianTap);	
		this.stdDeviationTap = Double.parseDouble(stdDeviationTap);
		
		//set Spin info
		this.bestSpin = Double.parseDouble(bestSpin);
		this.meanSpin = Double.parseDouble(meanSpin);	
		this.medianSpin = Double.parseDouble(medianSpin);	
		this.stdDeviationSpin = Double.parseDouble(stdDeviationSpin);
		
		//set Squeeze info
		this.bestSqueeze = Double.parseDouble(bestSqueeze);
		this.meanSqueeze = Double.parseDouble(meanSqueeze);	
		this.medianSqueeze = Double.parseDouble(medianSqueeze);	
		this.stdDeviationSqueeze = Double.parseDouble(stdDeviationSqueeze);
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


	public String getBestTap() {
		return Double.toString(this.bestTap);
	}
	
	
	public String getMeanTap() {
		return Double.toString(this.meanTap);
	}
	
	
	public String getMedianTap() {
		return Double.toString(this.medianTap);
	}
	
	public String getStdDeviationTap() {
		return Double.toString(this.stdDeviationTap);
	}

	public String getBestSpin() {
		return Double.toString(this.bestSpin);
	}
	
	
	public String getMeanSpin() {
		return Double.toString(this.meanSpin);
	}
	
	
	public String getMedianSpin() {
		return Double.toString(this.medianSpin);
	}
	
	public String getStdDeviationSpin() {
		return Double.toString(this.stdDeviationSpin);
	}
	
	public String getBestSqueeze() {
		return Double.toString(this.bestSqueeze);
	}
	
	
	public String getMeanSqueeze() {
		return Double.toString(this.meanSqueeze);
	}
	
	
	public String getMedianSqueeze() {
		return Double.toString(this.medianSqueeze);
	}
	
	public String getStdDeviationSqueeze() {
		return Double.toString(this.stdDeviationSqueeze);
	}
	
}
