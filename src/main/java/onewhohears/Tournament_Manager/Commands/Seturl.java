package onewhohears.Tournament_Manager.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import onewhohears.Tournament_Manager.Bot_Main;
import onewhohears.Tournament_Manager.Connection.ChallongeDataManager;

public class Seturl {
	
	public static String command = "seturl";
	
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
			if (args.length > 2 || args.length < 2) {
				info.setTitle("ERROR");
				info.addField("Problem","Invalid usage.",false);
				info.addField("Correct Usage",Bot_Main.PREFIX+command+" [end_of_url]",false);
				info.addField("Example",Bot_Main.PREFIX+command+" yhsqrt",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else {
				ChallongeDataManager.setTourneyURL(args[1]);
				event.getChannel().sendMessage("Changed Defult URL!").queue();
			}
		//}
		
	}

}
