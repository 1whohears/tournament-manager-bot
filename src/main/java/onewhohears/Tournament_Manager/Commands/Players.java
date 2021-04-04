package onewhohears.Tournament_Manager.Commands;

import java.util.List;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import onewhohears.Tournament_Manager.Bot_Main;
import onewhohears.Tournament_Manager.Connection.ChallongeDataManager;
import onewhohears.Tournament_Manager.Data.ChallongePlayerManager;
import onewhohears.Tournament_Manager.Data.Player;
import onewhohears.Tournament_Manager.Data.PlayerManager;

public class Players {
	
	public static String command = "players";
	
	public static void runCommand(GuildMessageReceivedEvent event, String[] args) {
		
		//String[] args = Functions.createArgsRespectingQuotes(event.getMessage().getContentRaw());
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
			} else if (ChallongeDataManager.USERNAME.equals("") || ChallongeDataManager.USERNAME == null) {
				info.setTitle("ERROR");
				info.addField("Problem","You need to set the username with "+Bot_Main.PREFIX+"username",false);
				info.setColor(0xff0000);
				event.getChannel().sendMessage(info.build()).queue();
				info.clear();
			} else { 
				if (!(ChallongeDataManager.TOURNEY_URL.equals("") || ChallongeDataManager.TOURNEY_URL == null)) ChallongePlayerManager.refreshChallongePlayers();
				if (args.length == 1) {
					//list all current players
					String gay = "";
					if (PlayerManager.players.size() > 0) { gay = PlayerManager.players.get(0).name; }
					for (int i = 1; i < PlayerManager.players.size(); ++i) {
						gay += ", " + PlayerManager.players.get(i).name;
					}
					if (gay.equals("")) {
						event.getChannel().sendMessage("There are no Players to list.").queue();
					} else {
						event.getChannel().sendMessage("Listing Current Players in Bracket order.").queue();
						event.getChannel().sendMessage(gay).queue();
					}
				} else if (args.length > 2) {
					if (args[1].equals("add")) {
						//add players manually
						for (int i = 2; i < args.length; ++i) {
							String name = args[i];
							if (!PlayerManager.nameRepeat(name)) {
								PlayerManager.addPlayer(new Player(name));
								ChallongeDataManager.addParticipant(name);
							}
						}
					} else if (args[1].equals("addrole")) {
						//add players via their role
						Guild guild = event.getGuild();
						List<Role> roles = guild.getRolesByName(args[2], false);
						if (roles == null || roles.size() == 0) { event.getChannel().sendMessage("Role "+args[2]+" not found.").queue(); return; }
						List<Member> members = guild.getMembersWithRoles(roles);
						if (roles == null || roles.size() == 0) { event.getChannel().sendMessage("No members found in role "+args[2]).queue(); return; }
						for (int i = 0; i < members.size(); ++i) {
							String name = members.get(i).getUser().getName();
							if (!PlayerManager.nameRepeat(name)) {
								PlayerManager.addPlayer(new Player(name));
								ChallongeDataManager.addParticipant(name);
							}
						}
						event.getChannel().sendMessage("Added players in role "+args[2]).queue();
					} else {
						//error
						info.setTitle("ERROR");
						info.addField("Problem","Invalid usage.",false);
						info.addField("Correct Usage",Bot_Main.PREFIX+command+" add/addrole 'player name/role' 'player name' ...",false);
						info.setColor(0xff0000);
						event.getChannel().sendMessage(info.build()).queue();
						info.clear();
					}
				} else {
					//error
					info.setTitle("ERROR");
					info.addField("Problem","Invalid usage.",false);
					info.addField("Correct Usage",Bot_Main.PREFIX+command+" add/addrole 'player name/role' 'player name' ...",false);
					info.setColor(0xff0000);
					event.getChannel().sendMessage(info.build()).queue();
					info.clear();
				}
			}
		//}
	
	}
}
