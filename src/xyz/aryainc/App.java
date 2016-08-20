package xyz.aryainc;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.PrintStream;

/**
 * Created by ishan on 8/19/2016.
 */
public class App extends Application {
	
	String URL = null;
	TextField input;
	
	public static void main(String[] args) {
		launch(args);
	}
	public void setConsole(TextArea textArea){
		Console console = new Console(textArea);
		PrintStream ps = new PrintStream(console, true);
		System.setOut(ps);
		System.setErr(ps);
	}
	
	
	public void start(Stage primaryStage) {
		Stage stage = primaryStage;
		
		stage.setTitle("MP3 Master");
		
		//Basically the console
		TextArea console = new TextArea();
			console.setMinWidth(400);
			console.setMinHeight(300);
			console.setWrapText(true);
			setConsole(console);
		
		//Label
		Label label = new Label("Search: ");
		
		//Input song info here
		input = new TextField();
			input.setPromptText("song info");
			input.setOnKeyPressed(e -> {
				if(e.getCode().equals(KeyCode.ENTER))
					BackgroundWork.searchAction(input, URL);
			});
		
		//Takes input from TextField and attempts to download song
		Button button = new Button();
			button.setText("Get song!");
			button.setOnAction(e -> BackgroundWork.searchAction(input, URL));
		
		//Searchbar
		HBox searchBar = new HBox(10);
			searchBar.getChildren().addAll(label, input);
			searchBar.setHgrow(input, Priority.ALWAYS);
			
		
		//layout
		VBox layout = new VBox(20);
			layout.setPadding(new Insets(10));
			layout.getChildren().addAll(searchBar, button, console);
			layout.setAlignment(Pos.CENTER);
		
		//set scene and display
		Scene scene = new Scene(layout, 450, 450);
		stage.setScene(scene);
		stage.show();
	}
}
