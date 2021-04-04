package onewhohears.Tournament_Manager.Data;

import java.util.ArrayList;

public class PlayerManager {
	
	public static ArrayList<Player> players = new ArrayList<Player>();
	
	public static void addPlayer(Player p) {
		if (!nameRepeat(p.name)) { players.add(p); System.out.println("Added player: " + p.name); }
	}
	
	public static void clearPlayers() {
		players.clear();
	}
	
	public static boolean nameRepeat(String n) {
		for (int i = 0; i < players.size(); ++i) {
			if (players.get(i).hasAltName(n)) {
				return true;
			}
		}
		return false;
	}
	
	public static Player getPlayerByName(String name) {
		int playerNum = players.size();
		for (int i = 0; i < playerNum; ++i) {
			if (players.get(i).hasAltName(name)) {
				return players.get(i);
			}
		}
		return null;
	}
	
}
