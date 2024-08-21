package application.java.exercise;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Exercise {
	private int idEsercizio;
	private int grado;
	private int tipologia;
	private int numero;
	private String path;
	private String risposta1;
	private String risposta2;
	
	public Exercise(int idEsercizio, int grado, int tipologia, int numero, String path, String risposta1, String risposta2) {
		this.idEsercizio = idEsercizio;
		this.grado = grado;
		this.tipologia = tipologia;
		this.numero = numero;
		this.path = path;
		this.risposta1 = risposta1;
		this.risposta2 = risposta2;
	}
	
	public int getIdEsercizio() {
		return this.idEsercizio;
	}
	
	public int getNumero() {
		return this.numero;
	}
	public String getRisposta1() {
		return this.risposta1;
	}
	
	public String getRisposta2() {
		return this.risposta2;
	}
	
	public String getText() {
		File f = new File("src/application/resources/exercise/text/findError/es1.txt");
		String text = "";
		
		try {
			Scanner scan = new Scanner(f);
			while (scan.hasNextLine()) {
				text += scan.nextLine() + "\n";
			}
			scan.close();
		}
		catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, LocalDateTime.now() + "Errore File durante lettura testo" + "\nMessaggio di errore: " + e.getMessage());
		}
		
		return text;
	}
	
}
