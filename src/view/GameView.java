package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Game;

public class GameView extends Stage {
	
	protected Game game;
	protected Label flags;
	protected Rectangle[][] gameBoard;
	
	public GameView(Game g) {
		game = g;
		VBox vbox = new VBox();
		Label bombs = new Label("Bombs: " + game.bombCount());
		flags = new Label("Flags: " + game.flagCount());
		HBox labels = new HBox(bombs, flags);
		vbox.getChildren().add(labels);
		int size = game.getSize();
		gameBoard = new Rectangle[size][size];
		vbox.getChildren().addAll(createBoard());
		
		Scene scene = new Scene(vbox, 500, 500);
		this.setScene(scene);
		this.show();
		
	}
	
	private List<HBox> createBoard() {
		int size = game.getSize();
		List<HBox> rows = new ArrayList<>();
		for(int i = 0; i < size; i++) {
			HBox hb = new HBox();
			for(int j = 0; j < size; j++) {
				Rectangle rec = new Rectangle(32, 32);
				setImage(rec, "bunp");
				setEvents(rec, i, j);
				gameBoard[i][j] = rec;
				hb.getChildren().add(rec);
			}
			rows.add(hb);
		}
		return rows;
	}
	
	private void setEvents(Rectangle rec, int x, int y){
		rec.setOnMousePressed(e->{
			if(e.isPrimaryButtonDown()) {
				int check = game.check(x, y);
				if(check == -1) {
					setImage(rec, "bombs");
					revealBombs();
					Alert lose = new Alert(AlertType.CONFIRMATION);
					lose.setTitle("You lost :("); lose.setHeaderText("Oh no! You lost."); lose.setContentText("Do you wanna try a new game?");
					Optional<ButtonType> result = lose.showAndWait();
					if(result.get() == ButtonType.OK) {
						new ChoiceView();
					}
					this.close();
				} else if(check == 0) {
					setImage(rec, Integer.toString(game.checkBombs(x, y)));
					updateChecked(x, y);
				}
					
			} else if(e.isSecondaryButtonDown()) {
				int check = game.check(x, y);
				if(check == 0 | check == -1) {
					setImage(rec, "flag");
					game.flag(x, y);
					updateFlagCount();
				} else if(check == 2) {
					setImage(rec, "bunp");
					game.flag(x, y);
					updateFlagCount();
				}
			}
			if(game.winCondition()) {
				Alert win = new Alert(AlertType.CONFIRMATION);
				win.setTitle("You win!"); win.setHeaderText("You won the game! Congratulations!"); win.setContentText("Want to play again?");
				Optional<ButtonType> result = win.showAndWait();
				if(result.get() == ButtonType.OK) {
					new ChoiceView();
				}
				this.close();
			}
		});
	}
	
	private void revealBombs() {
		for(int i = 0; i < gameBoard.length; i++) {
			for(int j = 0; j < gameBoard.length; j++) {
				if(game.hasBomb(i, j)) setImage(gameBoard[i][j], "bombs");
			}
		}
	}
	
	private void setImage(Rectangle rec, String image) {
		try {
			FileInputStream file = new FileInputStream("resources/" + image + ".jpg");
			Image img = new Image(file);
			rec.setFill(new ImagePattern(img));
			file.close();
		} catch (FileNotFoundException exc) {
			exc.printStackTrace();
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}
	
	private void updateFlagCount() {
		flags.setText("Flags: " + game.flagCount());
	}
	
	private void updateChecked(int x, int y) {
		game.findEmpty(x, y);
		for(int i = 0; i < gameBoard.length; i++) {
			for(int j = 0; j < gameBoard.length; j++) {
				if(game.hasBeenChecked(i, j) == true) setImage(gameBoard[i][j], Integer.toString(game.checkBombs(i, j)));
			}
		}
	}

}
