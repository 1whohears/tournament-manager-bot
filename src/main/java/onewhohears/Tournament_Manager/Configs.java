package onewhohears.Tournament_Manager;

public class Configs {
	//config 1
	public static boolean only_users_in_match_report = false;
	//config 2
	public static boolean both_players_verify = true;
	
	public static void setConfig1(boolean c) {
		only_users_in_match_report = c;
	}
	
	public static void setConfig2(boolean c) {
		both_players_verify = c;
	}
}
