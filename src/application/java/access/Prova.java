package application.java.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class Prova {
	private final static String location = "jdbc:sqlite:database.db";
	
private static Connection connection;
	
	public static void main(String [] args) {
		checkDrivers();
		connection = connect(location);
		signUpUser("fbuccia", "prova", "Filippo", "Bucciarelli");
		
	}
	
	public static void signUpUser(String username, String password, String name, String surname) {
		
		PreparedStatement psInsert = null; 
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try {
			psCheckUserExists = connection.prepareStatement("SELECT * FROM Utenti WHERE username = ?");
			psCheckUserExists.setString(1, username);
			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst()) {  //username già usato
				System.out.println("Questo username è già stato scelto");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Questo username è già stato scelto!!");
				alert.show();
			}
			else {
				psInsert = connection.prepareStatement("INSERT INTO Utenti (username, password, nome, cognome) VALUES (? ? ? ?)");
				psInsert.setString(1, username);
				psInsert.setString(2, password);
				psInsert.setString(3, name);
				psInsert.setString(4, surname);
				psInsert.executeUpdate();
				
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (psCheckUserExists != null) {
				try {
					psCheckUserExists.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (psInsert != null) {
				try {
					psInsert.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if (connection != null) {
				try {
					connection.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	
	
	public static Connection connect(String location) {
		Connection connection;
		try {
			connection = DriverManager.getConnection(location);
		}
		catch (SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not connect to SQLite DB at " + location);
			return null;
		}
		
		return connection;
	}

	
	public static boolean checkDrivers(){
		try {
			Class.forName("org.sqlite.JDBC"); //controlla che sia presente sqlite-jdbc
			DriverManager.registerDriver(new org.sqlite.JDBC());
	        return true;
		}
		catch (ClassNotFoundException | SQLException classNotFoundException) {
	        Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + ": Could not start SQLite Drivers");
	        return false;
	    }
	}
	
	
	
}

