package onewhohears.Tournament_Manager.Data;

import java.util.ArrayList;

public class Player {
	
	public String name = "";
	public String discord = "";
	public ArrayList<String> altNames = new ArrayList<String>();
	
	public int score = 0;
	public int rank = 0;
	
	public Player() {
		
	}
	
	public Player(String n) {
		name = n;
	}
	
	public Player(String n, int r) {
		name = n;
		rank = r;
	}
	
	public Player(String n, int r, int s) {
		name = n;
		score = s;
		rank = r;
	}
	
	public Player(String n, String d, int r, int s) {
		name = n;
		discord = d;
		score = s;
		rank = r;
	}
	
	public void setDiscord(String s) {
		discord = s;
	}
	
	public void setScore(int s) {
		score = s;
	}
	
	public void setRank(int r) {
		rank = r;
	}
	
	public void addAltName(String n) {
		altNames.add(n);
	}
	
	public boolean hasAltName(String n) {
		if (name.equals(n)) return true;
		if (discord.equals(n)) return true;
		if (altNames.contains(n)) return true; 
		return false;
	}
	
}
