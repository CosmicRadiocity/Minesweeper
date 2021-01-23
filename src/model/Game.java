package model;

import java.util.List;

public class Game {

	protected Board board;
	protected boolean win;
	protected boolean done;
	
	public Game(Board b) {
		board = b;
		win = false;
		done = false;
	}
	
	public boolean isDone() {
		return done;
	}
	
	public boolean isWon() {
		return win;
	}
	
	public int getSize() {
		return board.getSize();
	}
	
	public void flag(int x, int y) {
		board.getCase(x, y).flag();
	}
	
	public int check(int x, int y) {
		return board.getCase(x, y).check();
	}
	
	public boolean isFlagged(int x, int y) {
		return board.getCase(x, y).isFlagged();
	}
	
	public boolean hasBeenChecked(int x, int y) {
		return board.getCase(x, y).hasBeenChecked();
	}
	
	public int checkBombs(int x, int y) {
		return board.checkBombs(x, y);
	}
	
	public boolean hasBomb(int x, int y) {
		return board.getCase(x, y).hasBomb();
	}
	
	public boolean winCondition() {
		if(board.allChecked()) {
			win = true;
			return true;
		}
		return false;
	}
	
	public List<int[]> findEmpty(int x, int y){
		return board.findEmpty(x, y);
	}
	public int flagCount() {
		return board.flagCount();
	}
	
	public int bombCount() {
		return board.bombCount();
	}
}
