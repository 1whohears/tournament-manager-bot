package onewhohears.Tournament_Manager.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import onewhohears.Tournament_Manager.Bot_Main;
import onewhohears.Tournament_Manager.Connection.ChallongeDataManager;

public class Start {
	
	public static String command = "start";
	
	public static void runCommand(GuildMessageReceivedEvent event, String[] args) {
		
		//String[] args = event.getMessage().getContentRaw().split("\\s+");
		EmbedBuilder info = new EmbedBuilder();
		
		//if (args.length == 0) return;
		
		//if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + command)) {
			//check if user can use the command
			if (!Functions.passPermCheck(event, "to")) {
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
			} else if (args.length > 2) {
				info.setTitle("ERROR");
				info.addField("Problem","Invalid usage.",false);
				info.addField("Correct Usage",Bot_Main.PREFIX+command+" [tournament_URL(optional)]",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else if (args.length == 2){
				ChallongeDataManager.startTournament(args[1]);
				
				info.setTitle("TOURNAMENT STARTED");
				info.addField("Link","https://challonge.com/"+args[1],false);
				info.setColor(0x00ff00);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else {
				if (ChallongeDataManager.TOURNEY_URL.equals("") || ChallongeDataManager.TOURNEY_URL == null) {
					info.setTitle("ERROR");
					info.addField("Problem","You need to create a tournament with a new URL or use "+Bot_Main.PREFIX+"seturl or specify the current url via "+Bot_Main.PREFIX+"finish [tournament_url]",false);
					info.addField("What the heck is an api key?","https://challonge.com/settings/developer",false);
					info.setColor(0xff0000);
					event.getChannel().sendMessage(info.build()).queue();
					info.clear();
				} else { 
					ChallongeDataManager.startTournament();
					
					info.setTitle("TOURNAMENT STARTED");
					info.addField("Link","https://challonge.com/"+ChallongeDataManager.TOURNEY_URL,false);
					info.setColor(0x00ffff);
					event.getChannel().sendMessage(info.build()).queue();
					info.clear();
				}
			}
		//}
	
	}
}
