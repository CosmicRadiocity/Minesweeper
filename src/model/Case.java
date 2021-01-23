package model;

public class Case {
	
	protected boolean trapped;
	protected boolean flagged;
	protected boolean checked;
	protected int x, y;
	
	public Case(boolean bomb, int coordx, int coordy) {
		trapped = bomb;
		flagged = false;
		x = coordx;
		y = coordy;
	}
	
	public boolean hasBomb() {
		return trapped;
	}
	
	public boolean isFlagged() {
		return flagged;
	}
	
	public boolean hasBeenChecked() {
		return checked;
	}
	
	public int[] getCoords() {
		return new int[] {x, y};
	}
	
	public int check() {
		if(hasBomb() && !flagged) return -1;
		else if(!checked && !flagged) {
			checked = true;
			return 0;
		}
		else if(checked && !flagged) return 1;
		else return 2;
	}
	
	public void flag() {
		if(!flagged) flagged = true;
		else {
			flagged = false;
		}
		checked = false;
	}

}
