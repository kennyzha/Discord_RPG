import config.ApplicationConstants;
import listeners.MessageListener;
import net.dv8tion.jda.bot.sharding.DefaultShardManager;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import utils.PostStats;

import javax.security.auth.login.LoginException;

public class Rpg {
    public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException{
        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken(ApplicationConstants.TOKEN);
        builder.addEventListeners(new MessageListener());
        ShardManager shardManager = builder.build();
        System.out.println(shardManager.getShards().size());
        int total = 0;

        for(JDA jda : shardManager.getShards()){
            total += jda.getGuildCache().size();
        }

        System.out.println(total);

/*        JDA jda = new JDABuilder(AccountType.BOT).setToken(ApplicationConstants.TEST_TOKEN).buildBlocking();

//        PostStats.toDiscordBots(jda, ApplicationConstants.DISCORD_BOT_AUTH_TOKEN);

        jda.addEventListener(new MessageListener());*/
    }
}
