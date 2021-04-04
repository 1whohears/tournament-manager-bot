package onewhohears.Tournament_Manager.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import onewhohears.Tournament_Manager.Bot_Main;
import onewhohears.Tournament_Manager.Configs;
import onewhohears.Tournament_Manager.Connection.ChallongeDataManager;
import onewhohears.Tournament_Manager.Data.ChallongePlayerManager;

public class Report {
	
	public static String command = "report";
	
	public static void runCommand(GuildMessageReceivedEvent event, String[] args) {
		
		//String[] args = Functions.createArgsRespectingQuotes(event.getMessage().getContentRaw());        
		EmbedBuilder info = new EmbedBuilder();
		
		//if (args.length == 0) return;
		
		//if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + command)) {
			//check if user can use the command
			if (!Functions.passPermCheck(event, "report")) {
				event.getChannel().sendMessage("You do not have permission to use this command.").queue();
				return;
			}
			if (ChallongeDataManager.API_KEY.equals("") || ChallongeDataManager.API_KEY == null) {
				info.setTitle("ERROR");
				info.addField("Problem","You need to input your challonge api key through "+Bot_Main.PREFIX+"apikey",false);
				info.addField("What the heck is an api key?","https://challonge.com/settings/developer",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else if (ChallongeDataManager.TOURNEY_URL.equals("") || ChallongeDataManager.TOURNEY_URL == null) {
				info.setTitle("ERROR");
				info.addField("Problem","You need to create a tournament with a new URL or use "+Bot_Main.PREFIX+"seturl or specify the current url via "+Bot_Main.PREFIX+"seturl [tournament_url]",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else if (ChallongeDataManager.USERNAME.equals("") || ChallongeDataManager.USERNAME == null) {
				info.setTitle("ERROR");
				info.addField("Problem","You need to set the username with "+Bot_Main.PREFIX+"username",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else if (!ChallongeDataManager.getTournamentDataString("state").equals("underway")) { 
				info.setTitle("ERROR");
				info.addField("Problem","This tournament is not in a state where you can submit scores.",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else if (args.length > 5 || args.length < 5) {
				//ERROR usage !create [tournament name] [tournament URL]
				info.setTitle("ERROR");
				info.addField("Problem","Invalid usage.",false);
				info.addField("Correct Usage",Bot_Main.PREFIX+command+" 'your name' 'your_score' 'opponent name' 'opponent_score'",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else {
				if (Configs.only_users_in_match_report && !event.getAuthor().getName().equals(args[1])) {
					info.setTitle("ERROR");
					info.addField("Problem","Invalid usage. You didn't put your name.",false);
					info.addField("Correct Usage",Bot_Main.PREFIX+command+" 'your name' 'your_score' 'opponent name' 'opponent_score'",false);
					info.setColor(0xff0000);
					event.getChannel().sendMessage(info.build()).queue();
					info.clear();
				} else {
					//TODO make sure submitted scores are decent integers
					int score1 = 0, score2 = 0;
					try {
						score1 = Integer.parseInt(args[2]);
						score2 = Integer.parseInt(args[4]);
					} catch (NumberFormatException e) {
						info.setTitle("ERROR");
						info.addField("Problem","A score you inputted was not an integer!",false);
						info.setColor(0xff0000);
						event.getChannel().sendMessage(info.build()).queue();
						info.clear();
						return;
					}
					//TODO get their player data and throw an error if either doesn't exist
					ChallongePlayerManager.refreshChallongePlayers();
					String name1 = args[1], name2 = args[3];
					int id1 = ChallongePlayerManager.getChallongePlayerIDByName(name1);
					if (id1 == -1) { 
						info.setTitle("ERROR");
						info.addField("Problem","The first name you entered is not currently in the tournament or it was spelled wrong. Make sure its the name used in the bracket!",false);
						info.setColor(0xff0000);
						event.getChannel().sendMessage(info.build()).queue();
						info.clear();
						return;
					}
					int id2 = ChallongePlayerManager.getChallongePlayerIDByName(name2);
					if (id2 == -1) { 
						info.setTitle("ERROR");
						info.addField("Problem","The second name you entered is not currently in the tournament or it was spelled wrong. Make sure its the name used in the bracket!",false);
						info.setColor(0xff0000);
						event.getChannel().sendMessage(info.build()).queue();
						info.clear();
						return;
					}
					//TODO verify match exists
					ChallongeDataManager.refreshMatches();
					int matchID = ChallongeDataManager.getOpenMatchIDByParticipantIDs(id1, id2);
					if (matchID == -1) {
						info.setTitle("ERROR");
						info.addField("Problem","The match you tried to update either doesn't exist or currently can't be updated.",false);
						info.setColor(0xff0000);
						event.getChannel().sendMessage(info.build()).queue();
						info.clear();
						return;
					}
					//TODO flip id and score if needed
					if (ChallongeDataManager.getMatchDataIntByID(matchID,"player1_id") != id1) {
						int temp = id1;
						id1 = id2;
						id2 = temp;
						temp = score1;
						score1 = score2;
						score2 = temp;
						String temp2 = name1;
						name1 = name2;
						name2 = temp2;
					}
					//TODO do the double verify check
					if (Configs.both_players_verify) {
						
					}
					//TODO upload results
					ChallongeDataManager.updateMatchScore(matchID, score1, score2, true);
					
					info.setTitle("SET COMPLETE");
					info.setDescription(name1+" and "+name2+" have completed a set!");
					//info.addField("Winner","didn't set up yet",false);
					info.addField("Score",score1+"-"+score2,false);
					info.setColor(0x00ff00);
					event.getChannel().sendMessage(info.build()).queue();
					info.clear();
				}
			}
		//}
		
	}

}
