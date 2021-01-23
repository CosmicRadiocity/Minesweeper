package view;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Board;
import model.Game;

public class ChoiceView extends Stage{
	
	public ChoiceView() {
		ComboBox choiceSize = new ComboBox();
		choiceSize.getItems().add("Easy (10x10)"); choiceSize.getItems().add("Medium (25x25)"); choiceSize.getItems().add("Hard (50x50)");
		Label debug = new Label();
		Button select = new Button("Create game");
		HBox hbox = new HBox(choiceSize, select, debug);
		Scene scene = new Scene(hbox, 400, 30);
		this.setScene(scene);
		this.show();
		
		choiceSize.setOnAction(e -> {
			try {
				debug.setText((String) choiceSize.getValue());
			} catch (NullPointerException exc) {
				exc.printStackTrace();
			}
		});
		
		select.setOnAction(e->{
			try {
				String value = (String) choiceSize.getValue();
				System.out.println(value);
				if(value.equals("Easy (10x10)")) {
					Game g = new Game(new Board(10));
					new GameView(g);
					this.close();
				} else if(value.equals("Medium (25x25)")) {
					Game g = new Game(new Board(25));
					new GameView(g);
					this.close();
				} else if(value.equals("Hard (50x50)")) {
					Game g  = new Game(new Board(50));
					new GameView(g);
					this.close();
				} else {
					Alert error = new Alert(AlertType.ERROR);
					error.setContentText("????"); error.setHeaderText("Unknown error!"); error.setTitle("Error");
					error.showAndWait();
					this.close();
				}
			} catch(NullPointerException exc) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Please choose a difficulty!"); alert.setTitle("Warning"); alert.setHeaderText(null);
				alert.showAndWait();
				exc.printStackTrace();
			}
			
		});
	}

}
