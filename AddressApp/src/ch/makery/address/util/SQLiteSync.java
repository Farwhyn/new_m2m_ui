package ch.makery.address.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ch.makery.address.model.Patient;
import ch.makery.address.model.Session;
import javafx.collections.ObservableList;

public class SQLiteSync {

	private static Connection con;
	private static boolean hasData = false;

	// Define table names
	private static final String PATIENTS_TBL = "patients";
	private static final String SESSIONS_TBL = "sessions";
	private static final String SESSION_DATA_TBL = "sessionData";
	private static final String THERAPISTS_TBL = "therapists";

	public ResultSet displayUsers() throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}

		Statement state = con.createStatement();
		String sql = "SELECT patientID, firstName, lastName, regDate FROM " + PATIENTS_TBL;
		ResultSet res = state.executeQuery(sql);

		return res;
	}
//TODO make an all sessions button that uses this query? 
	public ResultSet displaySessions() throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}

		Statement state = con.createStatement();
		// String sql = "SELECT id, pid, fname, lname, vdate, FROM sess" ;
		String sql = "SELECT sessionID, patientID, sessionDate FROM " + SESSIONS_TBL;
		ResultSet res = state.executeQuery(sql);
		return res;
	}

	/**
	 * Returns all sessions for a patient
	 * 
	 * @param patient
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */

	public ObservableList<Session> displaySessionsForPatient(Patient patient, ObservableList<Session> data) {

		PreparedStatement ps = null;
		ResultSet res = null;

		try {
			
			if (con == null) {
				getConnection();
			}
			
			String sql = "SELECT sessionID, patientID, sessionDate FROM " + SESSIONS_TBL + " WHERE patientID = ? ORDER BY sessionDate ";
			ps = con.prepareStatement(sql);
			String patientID = patient.getID(); 
			ps.setString(1, patientID);
			res = ps.executeQuery();
			
			while (res.next()){
				data.add(new Session(res.getString("sessionID"), res.getString("sessionDate"), res.getString("patientID"))); 
			}
			
		} catch (Exception e) {

			e.printStackTrace();
			
		} finally {

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (res != null) {
				try {
					res.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return data;
	}

	/**
	 * Fetches all session info such as best , mean tap etc
	 * @param session
	 * @return
	 */
	public Session getSessionInfo(Session session) {
		
		
		PreparedStatement ps = null;
		ResultSet res = null;

		try {
			
			if (con == null) {
				getConnection();
			}
			
			String sql = "SELECT bestTap, meanTap, medianTap, stdDeviationTap, " + " bestSpin,  meanSpin,  medianSpin, stdDeviationSpin, " + 
			" bestSqueeze, meanSqueeze,  medianSqueeze, stdDeviationSqueeze FROM " + SESSION_DATA_TBL + " WHERE sessionID = ? ";
			
			ps = con.prepareStatement(sql);
			String sessionID = session.getID(); 
			ps.setString(1, sessionID);
			res = ps.executeQuery();
			
			while (res.next()){
				session.setSessionInfo((res.getString("bestTap")), (res.getString("meanTap")), (res.getString("medianTap")),(res.getString("stdDeviationTap")),
						(res.getString("bestSpin")), (res.getString("meanSpin")), (res.getString("medianSpin")),(res.getString("stdDeviationSpin")),
						(res.getString("bestSqueeze")), (res.getString("meanSqueeze")), (res.getString("medianSqueeze")),(res.getString("stdDeviationSqueeze")));
			}
			
		} catch (Exception e) {

			e.printStackTrace();
			
		} finally {

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (res != null) {
				try {
					res.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return session; 
		
	}
	private void getConnection() throws ClassNotFoundException, SQLException {

		Class.forName("org.sqlite.JDBC"); // find the sqlite JDBC driver
		//Eclipse path
		con = DriverManager.getConnection("jdbc:sqlite:Resources/Database/m2m.db"); // connect
																					// to
																					// the
																					// database
		//Jar file path
		//con = DriverManager.getConnection("jdbc:sqlite:Database/m2m.db");
		initialise();
	}

	private void initialise() throws SQLException {

		if (!hasData) {
			hasData = true;
		}

		Statement state = con.createStatement();
		ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = 'patients'");
	
	}

	public void addPatient(Patient patient) throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}

		PreparedStatement ps = con.prepareStatement("INSERT INTO patients values(?, ?, ?, ?, ?, ?, ?)");
		ps.setString(2, patient.getFirstName());
		ps.setString(3, patient.getLastName());
		ps.setString(4, patient.getStreet());
		ps.setString(5, patient.getCity());
		ps.setString(6, patient.getCardCode());
		ps.setString(7, patient.getVisitDate());
		ps.execute();
	}

	public void deletePatient(String patient_id) throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}

		PreparedStatement ps = con.prepareStatement("DELETE FROM patients WHERE patientID = ?");
		ps.setString(1, patient_id);
		ps.execute();
	}
	
	public boolean login(String username, String password) throws ClassNotFoundException, SQLException {
		if (con == null) {
			getConnection();
		}

		PreparedStatement ps = con.prepareStatement("SELECT * FROM " + THERAPISTS_TBL + " WHERE username = ? AND password = ?");
		ps.setString(1, username);
		ps.setString(2, password);
		ResultSet rs = ps.executeQuery();
		boolean success = rs.next(); 
		
		return success; 
		
		
	}
	
	/**
	 * Creates a new user in the therapists table
	 * TODO 
	 **/
	public boolean create_user() {
		return true;
	}
}
