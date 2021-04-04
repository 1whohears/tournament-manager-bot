package onewhohears.Tournament_Manager;

public class Permissions {
	
	//what commands certain roles have access to
	
	//config(all), perm(all)
	public static String admin = "";
	//username, apikey, create, seturl, players, clear, start, report, finish, perm(report) 
	public static String to = "everyone";
	//report
	public static String report = "everyone";
	
	public static void setAdmin(String r) {
		admin = r;
	}
	
	public static void setTO(String r) {
		to = r;
	}
	
	public static void setReport(String r) {
		report = r;
	}
}
