package onewhohears.Tournament_Manager.Commands;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import onewhohears.Tournament_Manager.Bot_Main;
import onewhohears.Tournament_Manager.Permissions;

public class Functions extends ListenerAdapter {
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		
		String[] args = createArgsRespectingQuotes(event.getMessage().getContentRaw());
		if (args.length == 0) return;
		
		if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Apikey.command)) {
			Apikey.runCommand(event, args);
		} else if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Clear.command)) {
			Clear.runCommand(event, args);
		} else if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Config.command)) {
			Config.runCommand(event, args);
		} else if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Create.command)) {
			Create.runCommand(event, args);
		} else if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Finish.command)) {
			Finish.runCommand(event, args);
		} else if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Info.command)) {
			Info.runCommand(event, args);
		} else if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Perm.command)) {
			Perm.runCommand(event, args);
		} else if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Players.command)) {
			Players.runCommand(event, args);
		} else if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Report.command)) {
			Report.runCommand(event, args);
		} else if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Seturl.command)) {
			Seturl.runCommand(event, args);
		} else if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Start.command)) {
			Start.runCommand(event, args);
		} else if (args[0].equalsIgnoreCase(Bot_Main.PREFIX + Username.command)) {
			Username.runCommand(event, args);
		} 
		
	}
	
	//Stolen from stack overflow
	//https://stackoverflow.com/questions/14654540/how-do-i-break-a-string-into-arguments-respecting-quotes
	public static String[] createArgsRespectingQuotes(String args) {
		
		List<String> tokens = new ArrayList<String>();
		StringBuilder sb = new StringBuilder();

		boolean insideQuote = false;

		for (char c : args.toCharArray()) {

		    if (c == '"')
		        insideQuote = !insideQuote;

		    if (c == ' ' && !insideQuote) {//when space is not inside quote split..
		        tokens.add(sb.toString()); //token is ready, lets add it to list
		        sb.delete(0, sb.length()); //and reset StringBuilder`s content
		    } else 
		        sb.append(c);//else add character to token
		}
		//lets not forget about last token that doesn't have space after it
		tokens.add(sb.toString());
		String[] array=tokens.toArray(new String[0]);
		for (int i = 0; i < array.length; ++i) {
			array[i] = removeChar(array[i],'"');
		}
		array = removeEmpty(array);
		return array;
	}
	
	public static String removeChar(String s, char c) {
        int k = 0;
        while (true) {
            int index = s.indexOf(c,k);
            if (index == -1) {
                return s;
			}
            s = s.substring(0, index) + s.substring(index+1);
            k = index;
		}
	}
	
	public static String[] removeEmpty(String[] array) {
		int empty_count = 0, i = 0;
		for (String a : array) if (a.equals("")) ++empty_count;
		String[] r = new String[array.length - empty_count];
		for (String a : array) if (!a.equals("")) r[i++] = a;
		return r;
	}
	
	public static boolean passPermCheck(GuildMessageReceivedEvent event, String type) {
		if (event.getMember().isOwner() || Permissions.admin.equals("everyone")) {
			return true;
		}
		int level = 0;
		if (type.equals("admin")) level = 10;
		if (type.equals("to")) level = 8;
		if (type.equals("report")) level = 3;
		List<Role> roles = event.getMember().getRoles();
		int k = 0;
		while (k < roles.size()) {
			Role role = roles.get(k++);
			if (level <= 10 && role.getName().equals(Permissions.admin)) {
				return true;
			}
			if (level <= 8 && role.getName().equals(Permissions.to)) {
				return true;
			}
			if (level <= 3 && role.getName().equals(Permissions.report)) {
				return true;
			}
		}
		if (k == roles.size()) {
			return false;
		}	
		return false;
	}
	
}
