package application.java.general;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/application/resources/access/fxml/EnterScene.fxml"));
			//Parent root = FXMLLoader.load(getClass().getResource("/application/resources/exercise/fxml/EsScene.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/application/resources/general/application.css").toExternalForm());
			
			stage.setTitle("SPT - JFXPRJCT");
			Image logo = new Image("/application/resources/general/logo.png");
			stage.getIcons().add(logo);
			stage.setResizable(false);
			
			stage.setScene(scene);
			stage.show();
			
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}




