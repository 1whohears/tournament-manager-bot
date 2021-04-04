package onewhohears.Tournament_Manager.Commands;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import onewhohears.Tournament_Manager.Bot_Main;
import onewhohears.Tournament_Manager.Connection.ChallongeDataManager;

public class Username {
	
	public static String command = "username";
	
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
				//ERROR usage !create [tournament name] [tournament URL]
				info.setTitle("ERROR");
				info.addField("Problem","Invalid usage.",false);
				info.addField("Correct Usage",Bot_Main.PREFIX+command+" 'username'",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else {
				ChallongeDataManager.setUserName(args[1]);
				List<Message> messages = event.getChannel().getHistory().retrievePast(2).complete();
	            event.getChannel().deleteMessages(messages).queue();
				event.getChannel().sendMessage("Thank you! Deleting that message for security.").queue();
			}
		//}
		
	}

}
