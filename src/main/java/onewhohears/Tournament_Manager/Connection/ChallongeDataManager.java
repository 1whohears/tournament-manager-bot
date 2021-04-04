package onewhohears.Tournament_Manager.Connection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ChallongeDataManager extends HttpConnect {
	
	public static JSONArray participants = new JSONArray();
	public static JSONArray matches = new JSONArray();
	
	public static String TOURNEY_URL = "";
	public static String API_KEY = "";
	public static String USERNAME = "";
	
	public static void addParticipant(String name) {
		String urlString = "https://api.challonge.com/v1/tournaments/"+TOURNEY_URL+"/participants.json";
		String parameters = "api_key="+ChallongeDataManager.API_KEY+"&participant[name]="+name;
		custom_connect("POST", urlString, parameters);
	}
	
	public static void createTournament(String name, String url) {
		String urlString = "https://api.challonge.com/v1/tournaments.json";
		String parameters = "api_key="+API_KEY+"&"+
							"tournament[name]="+name+"&"+
							"tournament[tournament_type]=double elimination&"+
							"tournament[url]="+url+"&"+
							"tournament[ranked_by]=match wins";
		custom_connect("POST", urlString, parameters);
		
		TOURNEY_URL = url;
	}
	
	public static void startTournament() {
		String urlString = "https://api.challonge.com/v1/tournaments/"+TOURNEY_URL+"/start.json";
		String parameters = "api_key="+API_KEY;
		custom_connect("POST", urlString, parameters);
	}
	public static void startTournament(String url) {
		TOURNEY_URL = url;
		startTournament();
	}
	
	public static void finishTournament() {
		String urlString = "https://api.challonge.com/v1/tournaments/"+TOURNEY_URL+"/finalize.json";
		String parameters = "api_key="+API_KEY;
		custom_connect("POST", urlString, parameters);
	}
	public static void finishTournament(String url) {
		TOURNEY_URL = url;
		finishTournament();
	}
	
	public static void deleteTournament() {
		String urlString = "https://api.challonge.com/v1/tournaments/"+TOURNEY_URL+".json";
		String parameters = "api_key="+API_KEY;
		custom_connect("DELETE", urlString, parameters);
	}
	public static void deleteTournament(String url) {
		TOURNEY_URL = url;
		deleteTournament();
	}
	
	public static void refreshParticipants() {
		String urlString = "https://api.challonge.com/v1/tournaments/"+TOURNEY_URL+"/participants.json";
		//String parameters = "api_key="+API_KEY;
		//participants = getJsonArray(urlString, parameters);
		participants = getJsonArrayUserPass(urlString, USERNAME, API_KEY);
	}
	public static void removeParticipant(int id) {
		String urlString = "https://api.challonge.com/v1/tournaments/"+TOURNEY_URL+"/participants/"+id+".json";
		String parameters = "api_key="+API_KEY;
		custom_connect("DELETE", urlString, parameters);
	}
	public static void clearParticipants(String url) {
		TOURNEY_URL = url;
		clearParticipants();
	}
	public static void clearParticipants() {
		String urlString = "https://api.challonge.com/v1/tournaments/"+TOURNEY_URL+"/participants/clear.json";
		String parameters = "api_key="+API_KEY;
		custom_connect("DELETE", urlString, parameters);
	}
	public static void changeParticipantSeed(int id, int seed) {
		String urlString = "https://api.challonge.com/v1/tournaments/"+TOURNEY_URL+"/participants/"+id+".json";
		String parameters = "_method=put&api_key="+API_KEY;
		custom_connect("POST", urlString, parameters);
	}
	
	public static void refreshMatches() {
		String urlString = "https://api.challonge.com/v1/tournaments/"+TOURNEY_URL+"/matches.json";
		//String parameters = "api_key="+API_KEY;
		//matches = getJsonArray(urlString, parameters);
		matches = getJsonArrayUserPass(urlString, USERNAME, API_KEY);
	}
	public static void updateMatchScore(int matchID, int score1, int score2, boolean complete) {
		String urlString = "https://api.challonge.com/v1/tournaments/"+TOURNEY_URL+"/matches/"+matchID+".json";
		String parameters = "_method=put&api_key="+API_KEY+"&match[scores_csv]="+csvConvert(score1,score2);
		if (complete) { 
			int winID = getWinnerID(matchID,score1,score2);
			if (winID != -1) parameters += "&match[winner_id]="+winID; 
		}
		custom_connect("POST", urlString, parameters);
	}
	public static int getWinnerID(int matchID, int score1, int score2) {
		JSONObject match = getMatchByID(matchID);
		if (match == null) return -1;
		if (score1 > score2) {
			return (int)((long)match.get("player1_id"));
		} else if (score1 < score2) {
			return (int)((long)match.get("player2_id"));
		} else {
			return -1;
		}
	}
	public static JSONObject getMatchByID(int matchID) {
		for (int i = 0; i < matches.size(); ++i) {
			JSONObject match = (JSONObject)((JSONObject)matches.get(i)).get("match");
			if ((int)((long)match.get("id")) == matchID) {
				return match;
			}
		}
		return null;
	}
	public static int getOpenMatchIDByParticipantIDs(int id1, int id2) {
		for (int i = 0; i < matches.size(); ++i) {
			JSONObject match = (JSONObject)((JSONObject)matches.get(i)).get("match");
			if (((String)match.get("state")).equals("open")) {
				if ((int)((long)match.get("player1_id")) == id1 || (int)((long)match.get("player2_id")) == id1) {
					if ((int)((long)match.get("player1_id")) == id2 || (int)((long)match.get("player2_id")) == id2) {
						return (int)((long)match.get("id"));
					}
				}
			}
		}
		
		return -1;
	}
	public static int getMatchDataIntByID(int matchID, String type) {
		return (int)((long)getMatchByID(matchID).get(type));
	}
	
	public static String getTournamentDataString(String type) {
		String urlString = "https://api.challonge.com/v1/tournaments/"+TOURNEY_URL+".json";
		//String parameters = "api_key="+API_KEY;
		//JSONObject tournament = (JSONObject)getJsonObject(urlString,parameters).get("tournament");
		JSONObject tournament = (JSONObject)getJsonObjectUserPass(urlString,USERNAME,API_KEY).get("tournament");
		return (String)tournament.get(type);
	}
	
	public static void setTourneyURL(String url) {
		TOURNEY_URL = url;
	}
	
	public static void setApiKey(String api) {
		API_KEY = api;
	}
	
	public static void setUserName(String user) {
		USERNAME = user;
	}
	
	//TODO check that it still works with negatives
	public static int[] separateCSV(String csv) {
		int[] r = new int[2]; int index = 0;
		r[0] = 0; r[1] = 0;
		String [] s = csv.split(",");
		for (int i = 0; i < s.length; ++i) {
			if (s[i].charAt(0) == '-') { 
				r[0] += Integer.parseInt(s[i].substring(0, s[i].indexOf("-", 1)));
				if (s[i].charAt(s[i].indexOf("-", 1) + 1) == '-') { r[1] += Integer.parseInt(s[i].substring(s[i].indexOf("-", 2), s[i].length())); }
				else { r[1] += Integer.parseInt(s[i].substring(s[i].indexOf("-", 1) + 1, s[i].length())); }
			} else {
				index = s[i].indexOf("-");
				if (index < 0) { 
					System.out.println("ERROR: " + csv + " does not have a '-' meaning this CSV was likely formatted wrong."); 
					System.out.println("Going to return 0-0.");
					r[0] = 0; r[1] = 0;
					return r;
				}
				r[0] += Integer.parseInt(s[i].substring(0, index));
				if (s[i].charAt(s[i].indexOf("-") + 1) == '-') { r[1] += Integer.parseInt(s[i].substring(s[i].indexOf("-") + 1, s[i].length())); }
				else { r[1] += Integer.parseInt(s[i].substring(s[i].indexOf("-") + 1, s[i].length())); }
			}
		}
		return r;
	}
	public static String csvConvert(int score1, int score2) {
		return score1 + "-" + score2;
	}
}
