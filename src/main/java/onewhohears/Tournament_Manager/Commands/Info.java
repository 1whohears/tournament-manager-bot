package onewhohears.Tournament_Manager.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Info {
	
	public static String command = "info";
	
	public static void runCommand(GuildMessageReceivedEvent event, String[] args) {
		
		//String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		//if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + command)) {
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Tournament Manager Information Link");
			info.setDescription("https://docs.google.com/document/d/1C75tv4afgwK3jQbxWE9K1QxdrQ9Gag6D9nt5riQjAh4/edit?usp=sharing");
			info.addField("Creator", "1whohears", false);
			info.addField("Special Message", "ur bad becuase this is currently not finished", false);
			info.setColor(0x9532a8);
			event.getChannel().sendMessage(info.build()).queue();
			info.clear();
		//}
	}
}
