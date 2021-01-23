package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
	
	protected final int MIN_SIZE = 10;
	protected final int MAX_SIZE = 50;
	protected int size;
	protected int bombCount;
	protected Case[][] cases;
	
	public Board(int s) {
		if(s < MIN_SIZE) size = MIN_SIZE;
		else if(s > MAX_SIZE) size = MAX_SIZE;
		else size = s;
		bombCount = size + 10;
		cases = new Case[size][size];
		initiateBoard();
	}
	
	public int getSize() {
		return size;
	}
	
	private void initiateBoard() {
		int bc = 0;
		Random rdm = new Random();
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(rdm.nextInt(10) == 1 && bc < bombCount) {
					cases[i][j] = new Case(true, i, j);
					bc++;
				} else cases[i][j] = new Case(false, i, j);
			}
		}
		while(bc < bombCount) {
			for(int i = 0; i < size; i++) {
				for(int j = 0; j < size; j++) {
					if(rdm.nextInt(10) == 1 && bc < bombCount) {
						if(!cases[i][j].hasBomb()) {
							cases[i][j] = new Case(true, i, j);
							bc++;
						}
					}
				}
			}
		}
		
	}
	
	public Case getCase(int x, int y) {
		return cases[x][y];
	}
	
	public int checkBombs(int x, int y) {
		int total = 0;
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				int newX = x+i;
				int newY = y+j;
				if(newX >= 0 && newX < size && newY >= 0 && newY < size) {
					if(cases[newX][newY].hasBomb()) total++;
				}
			}
		}
		return total;
	}
	
	public List<int[]> findEmpty(int x, int y){
		List<int[]> res = new ArrayList<>();
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				int newX = x+i;
				int newY = y+j;
				if(newX >= 0 && newX < size && newY >= 0 && newY < size) {
					if(cases[newX][newY].check() == 0 && checkBombs(newX, newY) == 0) {
						int[] tab = new int[2]; tab[0] = newX; tab[1] = newY;
						res.add(tab);
						res.addAll(findNeighbours(newX, newY));
					}
				}
			}
		}
		return res;
	}
	
	private List<int[]> findNeighbours(int x, int y){
		List<int[]> res = new ArrayList<>();
		for(int i = -1; i < 2; i++) {
			for(int j = -1; j < 2; j++) {
				int newX = x+i;
				int newY = y+j;
				if(newX >= 0 && newX < size && newY >= 0 && newY < size) {
					if(cases[newX][newY].check() == 0 && checkBombs(newX, newY) == 0) {
						int[] tab = new int[2]; tab[0] = newX; tab[1] = newY;
						res.add(tab);
					}
				}
			}
		}
		return res;
	}
	
	public int flagCount() {
		int total = 0;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(cases[i][j].isFlagged()) total++;
			}
		}
		return total;
	}
	
	public int bombCount() {
		int total = 0;
		for(int i = 0; i < size; i++) {
			for(int j = 0; j < size; j++) {
				if(cases[i][j].hasBomb()) total++;
			}
		}
		return total;
	}
	
	public boolean allChecked() {
		for(int i = 0; i < cases.length; i++) {
			for(int j = 0; j < cases.length; j++) {
				Case current = cases[i][j];
				if(!current.hasBomb()) {
					if(!current.hasBeenChecked()) return false;
				}
			}
		}
		return true;
	}

}
