package ch.makery.address.model;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
 * Class: Patient
 * This class sets up a new "Patient" with 4 properties.
 */

public class Patient{
    private	final String patient_id;
    private final StringProperty lastName;
    private final StringProperty firstName;
    private final StringProperty street;
    private final StringProperty city;
    //private final ObjectProperty<LocalDate> visitDate;
    private final StringProperty careCardNum;
    private final StringProperty visitDate;
    
    /*
     * The sessions as an observable list of Sessions
     */
    private ObservableList<Session> sessionData = FXCollections.observableArrayList();
    
    /**
     * Default constructor.
     */
    
    public Patient() {
        this(null, null, null, null, null, null, null);
    }
    
    
    public Patient(String id, String lName, String fName, String street, String cardNum, String city, String vDate){
    	this.patient_id = id;
        this.lastName = new SimpleStringProperty(lName);
        this.firstName = new SimpleStringProperty(fName);
        this.visitDate = new SimpleStringProperty(vDate);
        this.street = new SimpleStringProperty(street);
        this.careCardNum = new SimpleStringProperty(cardNum);
        this.city = new SimpleStringProperty(city);
    }
    
   

	public String getID(){
        return this.patient_id;
    }
    public String getLastName(){
        return lastName.get();
    }
    public void setLastName(String lName){  
        lastName.set(lName);
    }
    
    public StringProperty lastNameProperty() {
        return lastName;
    }
    
    
    public String getFirstName(){
        return firstName.get();
    }
    public void setFirstName(String fName){ 
        firstName.set(fName);
    }
    
    public StringProperty firstNameProperty() {
        return firstName;
    }
    
    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }
    
    public String getCardCode() {
        return careCardNum.get();
    }

    public void setCard(String card) {
        this.careCardNum.set(card);
    }

    public StringProperty cardProperty() {
        return careCardNum;
    }
    
    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    
    public String getVisitDate(){
        return visitDate.get();
    }
    public void setVisitDate(String vDate){ 
        this.visitDate.set(vDate);
    }
    
    public StringProperty dateProperty() {
        return visitDate;
    }
    
    /**
     * Returns the session data as an observable list of Sessions
     */
    public ObservableList<Session> getSessionData() {
 	   return sessionData; 
    }
}