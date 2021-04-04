package onewhohears.Tournament_Manager.Data;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import onewhohears.Tournament_Manager.Connection.ChallongeDataManager;

public class ChallongePlayerManager extends PlayerManager{
	
	public static void refreshChallongePlayers() { 
		//add unloaded names
		ChallongeDataManager.refreshParticipants();
		JSONArray participants = ChallongeDataManager.participants;
		for (int i = 0; i < participants.size(); ++i) {
			JSONObject participant = (JSONObject)((JSONObject)participants.get(i)).get("participant");
			String cName = (String)participant.get("name");
			addPlayer(new Player(cName));
		}
	}
	
	public static String getChallongePlayerDataStringByID(int id, String data) {
		JSONArray participants = ChallongeDataManager.participants;
		for (int i = 0; i < participants.size(); ++i) {
			JSONObject participant = (JSONObject)((JSONObject)participants.get(i)).get("participant");
			if ((int)((long)participant.get("id")) == id) {
				return (String)participant.get(data);
			}
		}
		System.out.println("ERROR: id "+id+" not found in Challonge Participants.");
		return null;
	}
	
	public static int getChallongePlayerDataIntByID(int id, String data) {
		JSONArray participants = ChallongeDataManager.participants;
		for (int i = 0; i < participants.size(); ++i) {
			JSONObject participant = (JSONObject)((JSONObject)participants.get(i)).get("participant");
			if ((int)((long)participant.get("id")) == id) {
				return (int)((long)participant.get(data));
			}
		}
		System.out.println("ERROR: id "+id+" not found in Challonge Participants.");
		return -1;
	}
	
	public static int getChallongePlayerIDByName(String name) {
		JSONArray participants = ChallongeDataManager.participants;
		for (int i = 0; i < participants.size(); ++i) {
			JSONObject participant = (JSONObject)((JSONObject)participants.get(i)).get("participant");
			if (((String)participant.get("name")).equals(name)) {
				return (int)(long)participant.get("id");
			}
		}
		System.out.println("ERROR: name "+name+" not found in Challonge Participants.");
		return -1;
	}
}
