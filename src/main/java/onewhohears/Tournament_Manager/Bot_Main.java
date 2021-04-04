package onewhohears.Tournament_Manager;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import onewhohears.Tournament_Manager.Commands.Functions;

public class Bot_Main {
	
	//TODO Allow commands to be done in certain channels
	//TODO Add update ranks command
	//TODO implement ranking system for auto seeding
	
	public static JDA jda;
	
	public static String PREFIX = "~";
	public static String TOKEN = "ODI3MjUzNTIzMTk5NTU3NjYy.YGYV4Q.jOvKaZke8NOVRPKAGmu6hTRhm60";
	
	public static void main(String [] arg) {
		try {
			jda = JDABuilder.createLight(TOKEN, GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MEMBERS)
					.setMemberCachePolicy(MemberCachePolicy.ALL)
					.setChunkingFilter(ChunkingFilter.ALL)
					.addEventListeners(new Functions())
					//.addEventListeners(new Apikey())
					//.addEventListeners(new Clear())
					//.addEventListeners(new Config())
					//.addEventListeners(new Create())
					//.addEventListeners(new Finish())
					//.addEventListeners(new Info())
					//.addEventListeners(new Perm())
					//.addEventListeners(new Players())
					//.addEventListeners(new Report())
					//.addEventListeners(new Seturl())
					//.addEventListeners(new Start())
					//.addEventListeners(new Username())
					.setActivity(Activity.playing("these fools"))
					.build();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
