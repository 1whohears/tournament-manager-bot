package onewhohears.Tournament_Manager.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import onewhohears.Tournament_Manager.Bot_Main;
import onewhohears.Tournament_Manager.Connection.ChallongeDataManager;

public class Create {
	
	public static String command = "create";
	
	public static void runCommand(GuildMessageReceivedEvent event, String[] args) {
		
		//String[] args = Functions.createArgsRespectingQuotes(event.getMessage().getContentRaw());
		EmbedBuilder info = new EmbedBuilder();
		
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
			} else if (args.length > 3 || args.length < 3) {
				info.setTitle("ERROR");
				info.addField("Problem","Invalid usage.",false);
				info.addField("Correct Usage",Bot_Main.PREFIX+command+" 'tournament name' 'tournament_URL'",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else {
				ChallongeDataManager.createTournament(args[1], args[2]);
				info.setTitle("NEW TOURNAMENT");
				info.addField("Link","https://challonge.com/"+args[2],false);
				info.setColor(0x00ff00);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			}
		//}
	
	}
}
