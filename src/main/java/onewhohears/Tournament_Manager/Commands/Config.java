package onewhohears.Tournament_Manager.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import onewhohears.Tournament_Manager.Bot_Main;
import onewhohears.Tournament_Manager.Configs;

public class Config {
	
	public static String command = "config";
	
	public static void runCommand(GuildMessageReceivedEvent event, String[] args) {
		
		//String[] args = event.getMessage().getContentRaw().split("\\s+");
		EmbedBuilder info = new EmbedBuilder();
		
		//if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + command)) {
			//check if user can use the command
			if (!Functions.passPermCheck(event, "admin")) {
				event.getChannel().sendMessage("You do not have permission to use this command.").queue();
				return;
			}
			//run command
			if (args.length > 3 || args.length < 3) {
				info.setTitle("ERROR");
				info.addField("Problem","Invalid usage.",false);
				info.addField("Correct Usage",Bot_Main.PREFIX+command+" [setting] [change]",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else {
				if (args[1].equals("only_users_in_match_report")) {
					if (args[2].equals("true")) {
						Configs.setConfig1(true);
					} else if (args[2].equals("false")) {
						Configs.setConfig1(false);
					} else {
						info.setTitle("ERROR");
						info.addField("Problem","Invalid usage.",false);
						info.addField("Correct Usage","This setting type only accepts true or false.",false);
						info.setColor(0xff0000);
						event.getChannel().sendMessage(info.build()).queue();
						info.clear();
					}
				} else if (args[1].equals("both_players_verify")) {
					if (args[2].equals("true")) {
						Configs.setConfig2(true);
					} else if (args[2].equals("false")) {
						Configs.setConfig2(false);
					} else {
						info.setTitle("ERROR");
						info.addField("Problem","Invalid usage.",false);
						info.addField("Correct Usage","This setting type only accepts true or false.",false);
						info.setColor(0xff0000);
						event.getChannel().sendMessage(info.build()).queue();
						info.clear();
					}
				} else {
					info.setTitle("ERROR");
					info.addField("Problem","Could not find that setting type. Use !info for more information.",false);
					info.setColor(0xff0000);
					event.getChannel().sendMessage(info.build()).queue();
					info.clear();
				}
			}
		//}
		
	}

}
