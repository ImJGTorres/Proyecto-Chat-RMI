package views;

import java.rmi.RemoteException;
import java.util.Scanner;

import controller.pantallaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import serverUser.UserServer;


public class main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
            // Cargar el archivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pantalla.fxml"));
            Parent root = loader.load();
            // Crear la escena con el contenido cargado
            Scene scene = new Scene(root);
            // Configurar el escenario (stage)
            primaryStage.setTitle("Bienvenido");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	
}
