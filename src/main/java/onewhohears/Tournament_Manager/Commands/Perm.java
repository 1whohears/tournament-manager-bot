package onewhohears.Tournament_Manager.Commands;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import onewhohears.Tournament_Manager.Bot_Main;
import onewhohears.Tournament_Manager.Permissions;

public class Perm {
	
	public static String command = "perm";
	
	public static void runCommand(GuildMessageReceivedEvent event, String[] args) {
		
		//String[] args = event.getMessage().getContentRaw().split("\\s+");
		EmbedBuilder info = new EmbedBuilder();
		
		//if (args.length == 0) return;
		
		//if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + command)) {
			//check if user can use the command
			boolean to = false;
			if (!event.getMember().isOwner() && !Permissions.admin.equals("everyone")) {
				List<Role> roles = event.getMember().getRoles();
				int k = 0;
				while (k < roles.size()) {
					Role role = roles.get(k++);
					if (role.getName().equals(Permissions.admin)) {
						to = false;
						break;
					}
					if (role.getName().equals(Permissions.to)) {
						to = true;
					}
				}
				if (k == roles.size() && !to) {
					event.getChannel().sendMessage("You do not have permission to use this command.").queue();
					return;
				}
			}
			//run command
			if (args.length > 3 || args.length < 3) {
				//ERROR usage !create [tournament name] [tournament URL]
				info.setTitle("ERROR");
				info.addField("Problem","Invalid usage.",false);
				info.addField("Correct Usage",Bot_Main.PREFIX+command+" [perm_type] [role]",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else {
				if (!to) {
					if (args[1].equals("admin")) {
						Permissions.setAdmin(args[2]);
					} else if (args[1].equals("to")) {
						Permissions.setTO(args[2]);
					} else if (args[1].equals("report")) {
						Permissions.setReport(args[2]);
					} else {
						info.setTitle("ERROR");
						info.addField("Problem","Could not find that permission type. Use !info for more information.",false);
						info.setColor(0xff0000);
						event.getChannel().sendMessage(info.build()).queue();
						info.clear();
					}
				} else {
					if (args[1].equals("admin")) {
						event.getChannel().sendMessage("You do not have permission to use this command.").queue();
					} else if (args[1].equals("to")) {
						event.getChannel().sendMessage("You do not have permission to use this command.").queue();
					} else if (args[1].equals("report")) {
						Permissions.setReport(args[2]);
					} else {
						info.setTitle("ERROR");
						info.addField("Problem","Could not find that permission type. Use !info for more information.",false);
						info.setColor(0xff0000);
						event.getChannel().sendMessage(info.build()).queue();
						info.clear();
					}
				}
			}
		//}
		
	}
}
