package application.java.exercise;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.java.access.GraphicalAnswer;
import application.java.dashboard.UserScraper;
import application.java.general.DBUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DBExercise {
	private final static String location = "jdbc:sqlite:database.db";
	
	
	public static Exercise loadEx(int tipologia) {
		Connection connection = DBUtils.connect(location);
		
		if (connection == null)
			return null;
		
		int idUtente = UserScraper.getIdUtente();
		
		try {
			ArrayList<Exercise> exerciseList = extractExercise(connection, "SELECT * FROM Esercizi WHERE tipologia = ?", tipologia);
			discardExercise(connection, exerciseList, idUtente);
						
			return exerciseList.getFirst();
		}
		
		catch(SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante caricamento esercizi");
			DBUtils.showDBError(e);
			return null;
		}
	}
	
	public static ArrayList<Exercise> extractExercise(Connection connection, String statement, int number) throws SQLException{
		ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
		
		PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.setInt(1, number);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			Exercise ex = new Exercise(
					resultSet.getInt("idEsercizio"),
					resultSet.getInt("grado"),
					resultSet.getInt("tipologia"),
					resultSet.getInt("numero"),
					resultSet.getString("PathTesto"),
					resultSet.getString("risposta1"),
					resultSet.getString("risposta2"));
			exerciseList.add(ex);
		}
		
		return exerciseList;
	}
	
	public static void discardExercise(Connection connection, ArrayList<Exercise> exerciseList, int idUtente) throws SQLException{
		
		TreeSet<Integer> idSet = new TreeSet<Integer>();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM EserciziSvolti WHERE idUtente = ?");
		preparedStatement.setInt(1, idUtente);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			idSet.add(resultSet.getInt("idEsercizio")); 
		}
		
		exerciseList.removeIf(ex -> idSet.contains(ex.getIdEsercizio()));
	}
	
	public static boolean updateCompletedEx(int idEsercizio, int idUtente) {
		Connection connection = DBUtils.connect(location);
		
		if (connection == null)
			return false; 
		
		PreparedStatement psInsert = null; 
		
		try {
			psInsert = connection.prepareStatement("INSERT INTO EserciziSvolti (idEsercizio, idUtente) VALUES (?, ?)");
			psInsert.setInt(1, idEsercizio);
			psInsert.setInt(2, idUtente);
			psInsert.executeUpdate();
			return true;
		}
		catch (SQLException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore DB durante registrazione dati");
			DBUtils.showDBError(e);
			return false;
		}	
	}
	
}

